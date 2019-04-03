package API;

import Database.BaseDatabase;



/**
 * The class responsible for open a API and handle them requests. Theses request will also be string objects.
 */


public class API implements ViewInterface {

    private final BaseDatabase database;

    public API(BaseDatabase database) {
        this.database = database;
    }


    @Override
    public void newCustomer(String fname, String lname, String DOB, String cardnum, String AccessPlan) {

    }

    @Override
    public void rentOut(String CustomerID, String ProductID) {

    }

    @Override
    public void returnProduct(String ProductID) {

    }
}
