package API;

import Conversions.TimeConversions;
import Customer.AccessPlans.AccessPlan;
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
                    obj.put("success", "true");
                    obj.put("customerID", customerID);
                    output(obj);
                    break;
                } catch (JSONException e) {
                    sendError("Incorrect perimeters");
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
                    output(obj);
                } catch (InsuffientLoyaltyPoints e) {
                    sendError(e.getMessage());
                    break;
                } catch (SQLException e) {
                    sendError("Error in database");
                    break;
                } catch (InvalidCard invalidCard) {
                    sendError("Customer must update card");
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "rentOutWithAccessPlan":
                try{
                    String dueDate = presenter.rentOutWithAccessPlan(object.getInt("customerID"), object.getInt("productID"));
                    JSONObject obj = new JSONObject();
                    obj.put("success", "true");
                    obj.put("returnDate", dueDate);
                    output(obj);
                } catch (SQLException e) {
                    sendError("Error in database");
                } catch (AccessLevelCantRent accessLevelCantRent) {
                    sendError(accessLevelCantRent.getMessage());
                } catch (InvalidCard invalidCard) {
                    sendError("Customer must update card");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "returnrental":
                try {
                    if(presenter.returnProduct(object.getInt("productID"))){
                        JSONObject obj = new JSONObject();
                        obj.put("success", "true");
                        obj.put("returnDate", TimeConversions.returnTodayDate());
                        output(obj);
                    }else{
                        sendError("return false");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } catch (InvalidCard invalidCard) {
                    sendError("Could not process Customer Card");
                    invalidCard.printStackTrace();
                }
                break;
            case "createtitle":
                try {
                    presenter.createTitle(object.getString("titleName")
                            ,object.getString("typeOfMovie")
                            ,object.getString("yearOfRelease"));
                } catch (Exception e) {
                    sendError(e.getMessage());
                }
            case "ChangeUserDetails":
                int id = object.getInt("customerID");
                switch (object.getString("key")) {
                    case "accessPlan":
                        try {
                            presenter.updateCustomerAccessPlan(id, object.getString("accessPlan"));
                        } catch (InvalidCard | SQLException | CardSecurityError e) {
                            sendError(e.getMessage());
                            break;
                        }
                        output(new JSONObject().put("success", "true").put("updated", object.getString("key")));
                        break;
                    case "address":
                        try {
                            presenter.updateCustomerAddress(id, object.getString("address"));
                        } catch (SQLException e) {
                            sendError(e.getMessage());
                            break;
                        }
                        output(new JSONObject().put("success", "true").put("updated", object.getString("key")));
                        break;
                    case "card":
                        try {
                            presenter.updateCustomerCard(id, object.getString("cardType"),object.getString("cardNumber"));
                        } catch (SQLException | InvalidCard e) {
                            sendError(e.getMessage());
                            break;
                        }
                        output(new JSONObject().put("success", "true").put("updated", object.getString("key")));
                        break;
                    default:
                        sendError("Could not determine key or what is trying to be changed");
                }
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
