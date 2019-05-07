import Database.BaseDatabase;
import Database.SQLite;

/**
 * This class will represent options send via the command line and use theses options to tell the program what options to implement
 *
 */
public class OptionsFactory {


    private static String DatabaseOption = "";

    private static String APIOption = "";


    public static void CommandLineOptions(String[] args){
        for (int i = 0; i < args.length; i++) {
            switch(args[i]){
                case "-d": i++;setDatabaseOption(args[i]);break;
                case "-api":i++;setAPIOption(args[i]);break;

            }
        }

    }

    private static void setAPIOption(String arg) {


    }

    private static void setDatabaseOption(String key){

    }


    public static BaseDatabase getDatabaseOption() {

        return null;
    }





}
