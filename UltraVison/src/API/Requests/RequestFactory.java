package API.Requests;

import API.APIInterface;
import Database.BaseDatabase;

public class RequestFactory {

    /**
     * Creates a Request object based on the command string
     * @param apiInterface For request object ability to communicate back to user
     * @param command The command given by the View(MVP)
     * @return The correct Request or Null if command is not handled by system
     */
    public static Request Factory(APIInterface apiInterface, BaseDatabase BD, String command) {
        switch (command) {
            case "createcustomer":
                return new CreateUser(apiInterface,BD);
            case "rentwithloyaltypoints":
                return new RentWithLoyalty(apiInterface,BD);
            case "rentOutWithAccessPlan":
                return new RentWithAccess(apiInterface,BD);
            case "returnrental":
                return new ReturnRental(apiInterface,BD);
            case "createtitle":
                return new CreateTitle(apiInterface,BD);
            case "changeuseraddress":
                return new ChangeCustomerAddress(apiInterface,BD);
            case "changeuserCard":
                return new ChangeCustomerCard(apiInterface,BD);
            case "changeuseraccessplan":
                return new ChangeCustomerAccessPlan(apiInterface,BD);

        }
        return null;

    }
}

