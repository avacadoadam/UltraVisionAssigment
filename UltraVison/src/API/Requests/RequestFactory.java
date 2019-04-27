package API.Requests;

import API.APIInterface;
import API.Presenter;

public class RequestFactory {

    /**
     * Creates a Request object based on the command string
     * @param apiInterface For request object ability to communicate back to user
     * @param presenter For request object ability to communicate to System
     * @param command The command given by the View(MVP)
     * @return The correct Request or Null if command is not handled by system
     */
    public static Request Factory(APIInterface apiInterface, Presenter presenter, String command) {
        switch (command) {
            case "createcustomer":
                return new CreateUser(apiInterface, presenter);
            case "rentwithloyaltypoints":
                return new RentWithLoyalty(apiInterface, presenter);
            case "rentOutWithAccessPlan":
                return new RentWithAccess(apiInterface, presenter);
            case "returnrental":
                return new ReturnRental(apiInterface, presenter);
            case "createtitle":
                return new CreateTitle(apiInterface, presenter);
            case "changeuserdetails":
                return new ChangeUserDetails(apiInterface, presenter);
        }
        return null;

    }
}

