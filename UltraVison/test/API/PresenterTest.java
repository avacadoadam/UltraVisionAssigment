package API;

import Database.BaseDatabase;
import Database.SQLite;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class PresenterTest {


    HashMap<String,String> commands = new HashMap<>();
    JSONObject inCorrectCommand;
    JSONObject commandIsINT;

    BaseDatabase db = null;
    Presenter presenter;
    API api;


    @BeforeClass
    public void startUp(){
        System.out.println("worked");
        try {
            db = new SQLite();
        } catch (Exception e) {
            System.exit(2);
            e.printStackTrace();
        }
        presenter = new Presenter(db);

        api = new API(presenter);
    }


    @Before
    public void initPermeters(){
        commands.put("command","createcustomer");
        commands.put("command","rentwithloyaltypoints");
        inCorrectCommand = new JSONObject();
        inCorrectCommand.put("command","wrong");
        commandIsINT.put("command",1);

    }



}