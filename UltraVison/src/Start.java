import API.API;
import API.Presenter;
import Database.BaseDatabase;
import Database.SQLite;
import org.json.JSONObject;

import java.util.Scanner;

public class Start {


    public static void main(String[] args) {
        BaseDatabase database = null;
        try {
            database = new SQLite();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(2);
        }
        Presenter presenter = new Presenter(database);
        API api = new API(presenter);

        Clinet clinet = new Clinet(api);
    }



}


class Clinet implements ClinetInterface{

    private API api;

    public Clinet(API api) {
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



//        String fname, String lname, String DOB, String address, AccessPlan accessPlan, Card card






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

    @Override
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
