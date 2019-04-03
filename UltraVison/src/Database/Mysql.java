package Database;

import Customer.AccessPlans.AccessPlan;
import Customer.Card.Card;
import Customer.Customer;
import Titles.Title;

/**
 * A class that handles a connect to a MySQL database to work with ULTRA VISION system.
 *
 */

public class Mysql extends BaseDatabase {




    @Override
    public boolean returnRental(Title title) {
        return false;
    }

    @Override
    public boolean rent(Title title, Customer customer) {
        return false;
    }

    @Override
    public int registerCustomer(String fname, String lname, String DOB, String address, Card card, AccessPlan accessPlan) {
        return 0;
    }


}
