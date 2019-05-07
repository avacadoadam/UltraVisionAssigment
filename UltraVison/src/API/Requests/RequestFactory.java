package API.Requests;

import API.ServerCallback;
import Database.BaseDatabase;

public class RequestFactory {

    /**
     * Creates a Request object based on the command string
     * @param serverCallback For request object ability to communicate back to user
     * @param command The command given by the View(MVP)
     * @return The correct Request or Null if command is not handled by system
     */
    public static Request Factory(ServerCallback serverCallback, BaseDatabase BD, String command) {
        switch (command) {
            case "createcustomer":
                return new CreateUser(serverCallback,BD);
            case "rentwithloyaltypoints":
                return new RentWithLoyalty(serverCallback,BD);
            case "rentOutWithAccessPlan":
                return new RentWithAccess(serverCallback,BD);
            case "returnrental":
                return new ReturnRental(serverCallback,BD);
            case "createtitle":
                return new CreateTitle(serverCallback,BD);
            case "changeuseraddress":
                return new ChangeCustomerAddress(serverCallback,BD);
            case "changeuserCard":
                return new ChangeCustomerCard(serverCallback,BD);
            case "changeuseraccessplan":
                return new ChangeCustomerAccessPlan(serverCallback,BD);

        }
        return null;

    }
}

