import Customer.AccessPlans.AccessPlan;
import Customer.Card.Card;

public interface presenterInterface {



    public void newCustomer(String fname, String lname, String DOB,String address, Card card, String AccessPlan);

    public void rentOut(int CustomerID,int ProductID);

    public void returnProduct(int ProductID);

    public void ChangeCustomerAccessPlan(int CustomerID, AccessPlan desiredAccessPlan);

    public void ChangeCustomerDOB(int CustomerID,String DOB);

}
