package API;

public interface ViewInterface {



    public void newCustomer(String fname, String lname, String DOB,String cardnum, String AccessPlan);

    public void rentOut(String CustomerID,String ProductID);

    public void returnProduct(String ProductID);
}
