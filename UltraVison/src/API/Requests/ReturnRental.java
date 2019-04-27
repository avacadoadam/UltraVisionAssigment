package API.Requests;

import API.APIInterface;
import API.Presenter;
import Conversions.TimeConversions;
import errors.InvalidCard;
import org.json.JSONObject;

public class ReturnRental extends Request {
    public ReturnRental(APIInterface apiInterface, Presenter presenter) {
        super(apiInterface, presenter);
    }

    @Override
    public void perform(JSONObject parameters) {
        try {
            if(presenter.returnProduct(parameters.getInt("productID"))){
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
    }
}
