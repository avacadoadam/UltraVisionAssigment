package Database;

import Conversions.TimeConversions;
import Customer.AccessPlans.AccessPlan;
import Customer.Card.Card;
import Customer.Customer;
import Rental.Rental;
import Titles.ProductType;
import Titles.Title;
import errors.InvalidCard;

import java.sql.*;
import java.text.ParseException;
import java.util.Date;

public class SQLite implements BaseDatabase {

    private Connection connection = null;
    private DatabaseCommands commands;

    public SQLite() throws Exception {
        if (!this.connectToDatabase()) throw new Exception("Can not connect to database");
        this.commands = new DatabaseCommands();
    }




    @Override
    public Customer getCustomerData(int customerID) throws SQLException, InvalidCard {
        Statement statement = this.connection.createStatement();
        statement.setQueryTimeout(30);
        ResultSet rs = statement.executeQuery(DatabaseCommands.getNumberOfActiveRentals(customerID));
        int NumOfRentals = rs.getInt("NumOfRentals");
        ResultSet rs1 = statement.executeQuery(DatabaseCommands.getCustomerInformation(customerID));

        Customer customer = new Customer(rs.getString("lName"),
                rs.getString("DOB"),
                rs.getString("address"),
                rs.getInt("ID"),
                rs.getString("fName"),
                NumOfRentals,
                AccessPlan.valueOf(rs.getString("accessPlan")),
                Card.CardFactory(rs.getString("cardType"), new Long(rs.getString("cardNumber"))),
                rs.getInt("loyaltyPoints"));

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
            this.connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\avaca\\Desktop\\Ultra Vision Assigment\\UltraVison\\src\\ultraVision.db");
            return true;
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
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
        return rs;
    }

    @Override
    public void executeCommand(String SQL) throws SQLException {
        Statement statement = this.connection.createStatement();
        statement.setQueryTimeout(30);
        statement.executeUpdate(SQL);
    }
}
