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
import java.time.LocalDate;
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
        Customer c = new Customer(rs1.getString("lName"),
                rs1.getString("DOB"),
                rs1.getString("address"),
                rs1.getInt("ID"),
                rs1.getString("fName"),
                NumOfRentals,
                AccessPlan.accessPlanFactory(rs1.getString("accessPlan")),
                Card.CardFactory(rs1.getString("cardType"), new Long(rs1.getString("cardNumber"))),
                rs1.getInt("loyaltyPoints"));
        statement.close();
        return c;
    }

    @Override
    public Title getTitleData(int productID) throws SQLException, ParseException {
        Statement statement = this.connection.createStatement();
        statement.setQueryTimeout(30);
        ResultSet rs = statement.executeQuery(DatabaseCommands.getProductInformation(productID));
        Title t = new Title(rs.getInt("ID"),
                rs.getString("titleName"),
                TimeConversions.ConvertDOB(rs.getString("yearOfRelease")),
                ProductType.IdentifyFromString(rs.getString("typeOfMovie")),
                (rs.getInt("rented") == 1));
        statement.close();
        return t;
    }

    @Override
    public Rental getRentalData(int rentalID) throws SQLException, ParseException {
        Statement statement = this.connection.createStatement();
        statement.setQueryTimeout(30);
        ResultSet rs = statement.executeQuery("SELECT * FROM rentals WHERE ID = " + rentalID + ";");
        LocalDate dateRented = TimeConversions.ConvertDOB(rs.getString("dateRented"));
        String returnedDateSTR = rs.getString("dateReturned");
        LocalDate returnedDate = null;
        if (returnedDateSTR != null) {
            returnedDate = TimeConversions.ConvertDOB(returnedDateSTR);//may be null fix
        }
        boolean returned = (rs.getInt("returned") == 1);
        Rental r = new Rental(rs.getInt("ID"), dateRented, returnedDate, returned
                , rs.getInt("title_ID"), rs.getInt("customer_ID"));
        statement.close();
        return r;
    }


    @Override
    public Title[] ListAvailableTitles() throws SQLException {
        Statement statement = this.connection.createStatement();
        statement.setQueryTimeout(30);
        ResultSet rs = statement.executeQuery(DatabaseCommands.getAllAvaibleTitles());
        Title[] t = parseTitlesFromDatabse(rs);
        statement.close();
        return t;
    }


    @Override
    public Title[] ListAvailableTitles(ProductType type) throws SQLException {
        Statement statement = this.connection.createStatement();
        statement.setQueryTimeout(30);
        ResultSet rs = statement.executeQuery(DatabaseCommands.getAllAvaibleTitles(type.getType()));
        Title[] t = parseTitlesFromDatabse(rs);
        statement.close();
        return t;
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
                    "  cardNumber TEXT,\n" +
                    "  loyaltyPoints INTEGER DEFAULT 0\n" +
                    ");").executeUpdate();
            connection.prepareStatement("CREATE TABLE IF NOT EXISTS title\n" +
                    "(\n" +
                    "  ID            INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "  titleName     TEXT,\n" +
                    "  typeOfMovie   TEXT,\n" +
                    "  yearOfRelease TEXT,\n" +
                    "  rented     INTEGER DEFAULT 0\n" +
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
                    ");").executeUpdate();
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
        ResultSet rs = statement.executeQuery(SQL);
        statement.close();
        return rs;
    }

    @Override
    public int executeCommand(String SQL) throws SQLException {
        Statement statement = this.connection.createStatement();
        statement.setQueryTimeout(30);
        int rs = statement.executeUpdate(SQL);
        statement.close();
        return rs;
    }

    @Override
    public int getLastInsertRow() throws SQLException {
        return connection.createStatement().executeQuery("SELECT last_insert_rowid() as LastID;").getInt(1);
    }
}
