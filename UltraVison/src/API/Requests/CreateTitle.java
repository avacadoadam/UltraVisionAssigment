package API.Requests;

import API.ServerCallback;
import Conversions.TimeConversions;
import Database.BaseDatabase;
import Database.DatabaseCommands;
import Titles.ProductType;
import Titles.Title;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class CreateTitle extends Request {

    Title title;

    public CreateTitle(ServerCallback serverCallback, BaseDatabase databaseInterface) {
        super(serverCallback, databaseInterface);
    }


    @Override
    protected void decodeParameters(JSONObject parameters) {
        String title = "";
        String yearOfRelease = "";
        String typeOfMovie = "";
        LocalDate yearOfReleaseDate;
        try {
            title = parameters.getString("titleName");
            yearOfRelease = parameters.getString("yearOfRelease");
            typeOfMovie = parameters.getString("typeOfMovie");
            yearOfReleaseDate = TimeConversions.ConvertDOB(yearOfRelease);
        } catch (JSONException e) {
            sendError("titleName,yearOfRelease and typeOfMovie must be set and be string");return;
        } catch (ParseException | DateTimeParseException e) {
            sendError("yearOfRelease was not properly formatted use YYYY-MM-DD");return;
        }
        this.title = new Title(title,
                yearOfReleaseDate,
                ProductType.IdentifyFromString(typeOfMovie));

        isValidInput = true;
    }

    @Override
    protected boolean validate() {
        if(!isValidInput)return false;
        if(title.getTitleName().isEmpty()){
            sendError("Title Name is empty");
            return false;
        }
        if(title.getYearOfRelease() == null){
            sendError("Title Name is empty");
            return false;
        }
        return isValidInput;
    }

    @Override
    protected void perform() {
        try {
            this.databaseInterface.executeCommand(DatabaseCommands.createTitle(title.getTitleName(),
                    title.getProductType(),
                    title.getYearOfRelease().toString()));
            sendSuccess("Successful created " + title.getTitleName());
        } catch (SQLException e) {
            sendError("error connecting to database");
        }
    }
}
