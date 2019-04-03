package Database;

import Customer.AccessPlans.AccessPlan;
import Customer.Card.Card;
import Customer.Customer;
import Rental.Rental;
import Titles.ProductType;
import Titles.Title;
import Conversions.TimeConversions;


import java.sql.*;

public class SQLite extends BaseDatabase {

    private Connection connection = null;

    public SQLite() throws Exception {
        if (!this.connectToDatabase()) throw new Exception("Can not connect to database");
    }


    @Override
    public boolean returnRental(Title title) throws SQLException {
        Statement statement = this.connection.createStatement();
        statement.setQueryTimeout(30);
        statement.executeUpdate(this.commands.updateTitleRented(title.getTitleID(), false));
        statement.executeUpdate(this.commands.updateRental(title.getTitleID()));
        return true;
    }

    @Override
    public boolean rent(Title title, Customer customer) throws SQLException {
        Statement statement = this.connection.createStatement();
        statement.setQueryTimeout(30);  // set timeout to 30 sec.
        statement.executeUpdate(this.commands.createRental(customer.getCustomerID(), title.getTitleID()));
        statement.executeUpdate(this.commands.updateTitleRented(title.getTitleID(), true));
        return true;
    }

    @Override
    public int registerCustomer(String fname, String lname, String DOB, String address, Card card, AccessPlan accessPlan) throws SQLException {
        Statement statement = this.connection.createStatement();
        statement.setQueryTimeout(30);
        return statement.executeUpdate(this.commands.createCustomer(fname, lname, DOB, address, accessPlan, card));
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
                        TimeConversions.convertString(rs.getString("typeOfMovie")),
                        ProductType.IndentifyFromString(rs.getString("yearOfRelease")),
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
            this.connection = DriverManager.getConnection("jdbc:sqlite:UltraVision.db");
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
