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
import java.util.Date;

public class SQLite implements BaseDatabase {

    private Connection connection = null;
    private DatabaseCommands commands;

    public SQLite() throws Exception {
        if (!this.connectToDatabase()) throw new Exception("Can not connect to database");
        this.commands = new DatabaseCommands();
    }


    @Override
    public boolean returnRental(int productID) throws SQLException {
        Statement statement = this.connection.createStatement();
        statement.setQueryTimeout(30);
        statement.executeUpdate(this.commands.updateTitleRented(productID, false));
        statement.executeUpdate(this.commands.updateRentalToReturned(productID));
        return true;
    }

    @Override
    public void rent(int productID, int customerID, String dateRented, String due) throws SQLException {
        Statement statement = this.connection.createStatement();
        statement.setQueryTimeout(30);
        statement.executeUpdate(this.commands.createRental(customerID, productID));
        statement.executeUpdate(this.commands.updateTitleRented(productID, true));
    }

    @Override
    public void rentWithLoyaltyPoints(int productID, int customerID, String dateRented, String due) throws SQLException {
        Statement statement = this.connection.createStatement();
        statement.setQueryTimeout(30);
        statement.execute(this.commands.updateLoyaltyPoints(customerID, -100));
        rent(productID, customerID, dateRented, due);
    }

    @Override
    public int registerCustomer(String fname, String lname, String DOB, String address, Card card, AccessPlan accessPlan) throws SQLException {
        Statement statement = this.connection.createStatement();
        statement.setQueryTimeout(30);
        return statement.executeUpdate(this.commands.createCustomer(fname, lname, DOB, address, accessPlan, card));
    }

    @Override
    public Customer getCustomerData(int customerID) throws SQLException, InvalidCard {
        Statement statement = this.connection.createStatement();
        statement.setQueryTimeout(30);
        ResultSet rs = statement.executeQuery(commands.getNumberOfActiveRentals(customerID));
        int NumOfRentals = rs.getInt("NumOfRentals");
        ResultSet rs1 = statement.executeQuery(commands.getCustomerInformation(customerID));

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
    public Title getTitleInformation(int productID) throws Exception {
        Statement statement = this.connection.createStatement();
        statement.setQueryTimeout(30);
        ResultSet rs = statement.executeQuery(commands.getProductInformation(productID));
        boolean rented = (rs.getInt("rented") == 1);
        return new Title(rs.getInt("ID"),
                rs.getString("titleName"),
                TimeConversions.ConvertDOB(rs.getString("yearOfRelease")),
                ProductType.IdentifyFromString(rs.getString("typeOfMovie")),
                rented);
    }

    @Override
    public Rental getRentalInformation(int rentalID) throws Exception {
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
    public void updateCustomerLoyaltyPoints(int customerID, int amount) throws SQLException {
        Statement statement = this.connection.createStatement();
        statement.setQueryTimeout(30);
        statement.executeUpdate(commands.updateLoyaltyPoints(customerID, amount));
    }

    @Override
    public int createNewTitle(Title title) throws SQLException {
        Statement statement = this.connection.createStatement();
        statement.setQueryTimeout(30);
        return statement.executeUpdate(commands.createTitle(title.getTitleName(),title.getProductType(),title.getYearOfRelease().toString()));
    }

    @Override
    public boolean updateCustomerAccessPlan(int customerID, AccessPlan accessPlan) throws SQLException {
        Statement statement = this.connection.createStatement();
        statement.setQueryTimeout(30);
        statement.executeUpdate(commands.changeCustomerAccessPlan(customerID,accessPlan.getAccessPlanName()));
        return true;
    }


    @Override
    public boolean updateCustomerAddress(int customerID, String address) throws SQLException {
        Statement statement = this.connection.createStatement();
        statement.setQueryTimeout(30);
        statement.executeUpdate(commands.changeCustomerAddress(customerID,address));
        return true;
    }


    @Override
    public boolean updateCustomerCard(int customerID, Card card) throws SQLException {
        Statement statement = this.connection.createStatement();
        statement.setQueryTimeout(30);
        statement.executeUpdate(commands.changeCustomerCardType(customerID,card.getCardType()));
        statement.executeUpdate(commands.changeCustomerCardNumber(customerID,String.valueOf(card.getCardNumber())));
        return true;
    }

    @Override
    public Title[] ListAvailableTitles() throws SQLException {
        Statement statement = this.connection.createStatement();
        statement.setQueryTimeout(30);
        ResultSet rs = statement.executeQuery(this.commands.getAllAvaibleTitles());
        return parseTitlesFromDatabse(rs);
    }


    @Override
    public Title[] ListAvailableTitles(ProductType type) throws SQLException {
        Statement statement = this.connection.createStatement();
        statement.setQueryTimeout(30);
        ResultSet rs = statement.executeQuery(this.commands.getAllAvaibleTitles(type.getType()));
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


}
