import API.API;
import Database.BaseDatabase;
import Database.SQLite;
import org.json.JSONObject;

import java.util.Scanner;

public class Start {


    public static void main(String[] args) {

        try {
            BaseDatabase database = new SQLite();
            API api = new API(database);
            new Client(api);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(2);
        }



    }
}

class Client {

    private API api;

    Client(API api) {
        this.api = api;
        chooseOption();
    }

    private void chooseOption(){
        System.out.println("choose option");
        System.out.println("1 - create Customer");
        System.out.println("2 - rent");
        Scanner scanner = new Scanner(System.in);
        switch (scanner.nextInt()){
            case 1:createCustomer();
        }
    }

    private void createCustomer(){
        JSONObject obj = new JSONObject();
        obj.put("command",requestStringInformtion("createcustomer"));
        obj.put("fname",requestStringInformtion("fname"));
        obj.put("lname",requestStringInformtion("lname"));
        obj.put("dob",requestStringInformtion("dob"));
        obj.put("address",requestStringInformtion("address"));
        obj.put("accessPlan",requestStringInformtion("accessPlan"));
        obj.put("cardType",requestStringInformtion("cardType"));
        obj.put("cardNumber",requestStringInformtion("cardNumber"));
        api.request(obj);
    }

    private String requestStringInformtion(String request){
        System.out.println(request);
        Scanner scan = new Scanner(System.in);
        return scan.nextLine();
    }
    private int requestIntInformtion(String request){
        System.out.println(request);
        Scanner scan = new Scanner(System.in);
        return scan.nextInt();
    }

    public void response(JSONObject object) {
        System.out.println("response");
        Scanner scanner = new Scanner(System.in);
        switch (scanner.nextInt()){
            case 1:if(object.getBoolean("success")){
                System.out.println("success");
                System.out.println("customer ID " + object.getString("customerID"));
            }else{
                System.out.println("unsuccessfuly");
                System.out.println("error" + object.getString("error"));
            }
        }
    }
}
