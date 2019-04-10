import API.ViewInterface;

public class System implements ViewInterface {


    private System() {
    }

    public static void main(String args[]){



    }


    public static System getInstance() {
        System result = instance;
        if (result == null) {
            synchronized (mutex) {
                result = instance;
                if (result == null)
                    instance = result = new ASingleton();
            }
        }
        return result;
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
