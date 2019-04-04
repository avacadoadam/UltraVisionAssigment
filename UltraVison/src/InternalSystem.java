import API.SocketAPI;
import Customer.AccessPlans.AccessPlan;
import Customer.Card.CardType;
import Database.BaseDatabase;

public class InternalSystem {

    BaseDatabase DB;

    SocketAPI api;

    public InternalSystem(BaseDatabase DB, SocketAPI api) {
        this.DB = DB;
        this.api = api;
    }

    public void newCustomer(String fname, String lname,String address, String DOB,String  cardType,String cardNumber, String AccessPlan){


        DB.registerCustomer(fname,lname,DOB,address,CardType.IndentifyFromString(cardType),);


    }

    public void rentOut(int CustomerID,int ProductID);

    public void returnProduct(int ProductID);

    public void ChangeCustomerAccessPlan(int CustomerID, AccessPlan desiredAccessPlan);

    public void ChangeCustomerDOB(int CustomerID,String DOB);






}                                                                                                                                                                                                                                                                                                                                                                        