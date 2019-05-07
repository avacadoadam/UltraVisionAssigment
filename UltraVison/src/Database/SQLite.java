package Database;

import Conversions.TimeConversions;
import Customer.AccessPlans.AccessPlan;
import Customer.Card.Card;
import Customer.Customer;
import Rental.Rental;
import Titles.ProductType;
import Titles.Title;
import errors.InvalidCard;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.*;
import java.text.ParseException;
import java.util.Date;

public class SQLite implements BaseDatabase {

    private Connection connection = null;

    public SQLite() throws Exception {

        if (!this.connectToDatabase())
            throw new Exception("Can not connect to database");
    }


    @Override
    public Customer getCustomerData(int customerID) throws SQLException, InvalidCard {
        Statement statement = this.connection.createStatement();
        statement.setQueryTimeout(30);
        ResultSet rs = statement.executeQuery(DatabaseCommands.getNumberOfActiveRentals(customerID));
        int NumOfRentals = rs.getInt("NumOfRentals");
        ResultSet rs1 = statement.executeQuery(DatabaseCommands.getCustomerInformation(customerID));

        Customer customer = new Customer(rs1.getString("lName"),
                rs1.getString("DOB"),
                rs1.getString("address"),
                rs1.getInt("ID"),
                rs1.getString("fName"),
                NumOfRentals,
                AccessPlan.valueOf(rs1.getString("accessPlan")),
                Card.CardFactory(rs1.getString("cardType"), new Long(rs1.getString("cardNumber"))),
                rs1.getInt("loyaltyPoints"));
        return customer;
    }

    @Override
    public Title getTitleData(int productID) throws SQLException, ParseException {
        Statement statement = this.connection.createStatement();
        statement.setQueryTimeout(30);
        ResultSet rs = statement.executeQuery(DatabaseCommands.getProductInformation(productID));
        boolean rented = (rs.getInt("rented") == 1);
        return new Title(rs.getInt("ID"),
                rs.getString("titleName"),
                TimeConversions.ConvertDOB(rs.getString("yearOfRelease")),
                ProductType.IdentifyFromString(rs.getString("typeOfMovie")),
                rented);
    }

    @Override
    public Rental getRentalData(int rentalID) throws SQLException, ParseException {
        Statement statement = this.connection.createStatement();
        statement.setQueryTimeout(30);
        ResultSet rs = statement.executeQuery("SELECT * FROM rentals WHERE ID = " + rentalID + ";");
        Date dateRented = TimeConversions.ConvertDOB(rs.getString("dateRented"));
        Date returnedDate = TimeConversions.ConvertDOB(rs.getString("dateReturned"));
        boolean returned = (rs.getInt("returned") == 1);
        return new Rental(rs.getInt("ID"), dateRented, returnedDate, returned
                , rs.getInt("title_ID"), rs.getInt("customer_ID"));
    }


    @Override
    public Title[] ListAvailableTitles() throws SQLException {
        Statement statement = this.connection.createStatement();
        statement.setQueryTimeout(30);
        ResultSet rs = statement.executeQuery(DatabaseCommands.getAllAvaibleTitles());
        return parseTitlesFromDatabse(rs);
    }


    @Override
    public Title[] ListAvailableTitles(ProductType type) throws SQLException {
        Statement statement = this.connection.createStatement();
        statement.setQueryTimeout(30);
        ResultSet rs = statement.executeQuery(DatabaseCommands.getAllAvaibleTitles(type.getType()));
        return parseTitlesFromDatabse(rs);
    }

    /**
     * Parses the object ResultSet from the database which contains Raw String and int into a list of titles
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    private Title[] parseTitlesFromDatabse(ResultSet rs) throws SQLException {
        int counter = 0;
        Title[] title = new Title[BaseDatabase.MAX_NUMBER_OF_DISPLAYED_TITLES];
        while (rs.next()) {
            try {
                counter++;
                if (counter > BaseDatabase.MAX_NUMBER_OF_DISPLAYED_TITLES) break;

                boolean rented = (rs.getInt("rented") == 1);

                title[counter] = new Title(rs.getInt("ID"),
                        rs.getString("titleName"),
                        TimeConversions.ConvertDOB(rs.getString("yearOfRelease")),
                        ProductType.IdentifyFromString(rs.getString("typeOfMovie")),
                        rented);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return title;
    }

    private boolean connectToDatabase() {
        try {
            // create a database connection
            this.connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\avaca\\Desktop\\Projects\\Ultra Vision Assigment\\UltraVison\\ultraVision.db");

                    connection.prepareStatement("CREATE TABLE IF NOT EXISTS customer\n" +
                            "(\n" +
                            "  ID         INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                            "  fName      TEXT,\n" +
                            "  lName      TEXT,\n" +
                            "  DOB        text,\n" +
                            "  address    TEXT,\n" +
                            "  accessPlan TEXT,\n" +
                            "  cardType   TEXT,\n" +
                            "  cardNumber TEXT\n" +
                            ");").executeUpdate();
                    connection.prepareStatement("CREATE TABLE IF NOT EXISTS title\n" +
                            "(\n" +
                            "  ID            INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                            "  titleName     TEXT,\n" +
                            "  typeOfMovie   TEXT,\n" +
                            "  yearOfRelease TEXT\n" +
                            ");").executeUpdate();
                    connection.prepareStatement("CREATE TABLE IF NOT EXISTS rentals\n" +
                            "(\n" +
                            "  ID           INTEGER PRIMARY KEY,\n" +
                            "  customer_ID  INTEGER,\n" +
                            "  title_ID     INTEGER,\n" +
                            "  dateRented   TEXT,\n" +
                            "  dateReturned TEXT,\n" +
                            "  returned     INTEGER,\n" +
                            "  FOREIGN KEY (customer_ID) REFERENCES customer (ID),\n" +
                            "  FOREIGN KEY (title_ID) REFERENCES title (ID)\n" +
                            ");");
            return true;
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }
        return false;
    }


    private boolean closeConnection() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public ResultSet excuteQuery(String SQL) throws SQLException {
        Statement statement = this.connection.createStatement();
        statement.setQueryTimeout(30);
        return statement.executeQuery(SQL);
    }

    @Override
    public int executeCommand(String SQL) throws SQLException {
        Statement statement = this.connection.createStatement();
        statement.setQueryTimeout(30);
        return statement.executeUpdate(SQL);
    }

    @Override
    public int getLastInsertRow() throws SQLException {
        return connection.createStatement().executeQuery("SELECT last_insert_rowid() as LastID;").getInt(1);
    }
}
