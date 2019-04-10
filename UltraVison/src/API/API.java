package API;

import errors.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;


/**
 * The class responsible for open a API and handle them requests. Theses request will also be string objects.
 */


public class API implements APIInterface {

    Presenter presenter;



    public API(Presenter presenter) {
        this.presenter = presenter;
    }

    /**
     * Handles request from API
     *
     * @param object
     */
    public void request(JSONObject object) {
        String command;
        try {
            command = object.getString("command");
        } catch (Exception e) {
            sendError("command isn't a string");
            return;
        }
        switch (command) {
            case "createcustomer":
                int customerID;
                try {
                    customerID = presenter.newCustomer(object.getString("fname"),
                            object.getString("lname"),
                            object.getString("DOB"),
                            object.getString("address"),
                            object.getString("cardType"),
                            object.getString("cardNumber"),
                            object.getString("accessPlan"));
                    //on success
                    JSONObject obj = new JSONObject();
                    obj.put("succes", "true");
                    obj.put("customerID", customerID);
                    break;
                } catch (JSONException e) {
                    sendError("Incorrect permeters");
                    break;
                } catch (CustomerAccountInformationError e) {
                    sendError(e.getMessage());
                    break;
                } catch (SQLException e) {
                    sendError("Error in database");
                    break;
                } catch (CardSecurityError cardSecurityError) {
                    sendError(cardSecurityError.getMessage());
                }

            case "rentwithloyaltypoints":
                try {
                    String dueDate = presenter.rentwithloyaltypoints(object.getInt("customerID"), object.getInt("productID"));
                    JSONObject obj = new JSONObject();
                    obj.put("success", "true");
                    obj.put("returnDate", dueDate);

                } catch (InsuffientLoyaltyPoints e) {
                    sendError(e.getMessage());
                    break;
                } catch (SQLException e) {
                    sendError("Error in database");
                    break;
                } catch (InvalidCard invalidCard) {
                    sendError("Customer must update card");
                    break;
                } catch (CouldNotFindAccessPlan couldNotFindAccessPlan) {
                    sendError("Error with customers accessPlan");
                    break;
                }
                break;

            default:
                sendError("unKnown command");
                break;
        }


    }

    public void sendError(String error) {
        JSONObject obj = new JSONObject();
        obj.put("success", false);
        obj.put("error", error);
        output(obj);
    }

    public void sendErrors(String[] error) {
        JSONObject obj = new JSONObject();
        JSONArray arrayOfErrors = new JSONArray();
        obj.put("success", false);
        for (int i = 0; i < error.length; i++) {
            arrayOfErrors.put(error[i]);
        }
        obj.put("errors", arrayOfErrors);
        output(obj);
    }

    public void output(JSONObject object) {


    }

}
