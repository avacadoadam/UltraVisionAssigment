package Database;

import Customer.AccessPlans.AccessPlan;
import Customer.Card.Card;
import Customer.Customer;
import Titles.Title;

import javax.swing.table.TableRowSorter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLite extends BaseDatabase {

    private Connection connection = null;

    public SQLite() throws Exception {
        if(!this.connectToDatabase()) throw new Exception("Can not connect to database");
    }


    @Override
    public boolean returnRental(Title title) {
        try {
            Statement statement = this.connection.createStatement();
            statement.setQueryTimeout(30);
            statement.executeUpdate(this.commands.updateTitleRented(title.getTitleID(),false));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean rent(Title title, Customer customer) {
        return false;

        //            Statement statement = connection.createStatement();
//            statement.setQueryTimeout(30);  // set timeout to 30 sec.
//
//            statement.executeUpdate("drop table if exists person");
//            statement.executeUpdate("create table person (id integer, name string)");
//            statement.executeUpdate("insert into person values(1, 'leo')");
//            statement.executeUpdate("insert into person values(2, 'yui')");
//            ResultSet rs = statement.executeQuery("select * from person");
//            while(rs.next())
//            {
//                 read the result set
//                System.out.println("name = " + rs.getString("name"));
//                System.out.println("id = " + rs.getInt("id"));
//            }
    }

    @Override
    public int registerCustomer(String fname, String lname, String DOB, String address, Card card, AccessPlan accessPlan) {
        return 0;
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
