import API.API;
import Database.BaseDatabase;
import Database.SQLite;

public class Start {


    public static void main(String[] args) {

        try {
            BaseDatabase database = new SQLite();
            API api = new API(database);

            //test
//            database.executeCommand("");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(2);
        }



    }
}

