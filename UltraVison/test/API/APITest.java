package API;

import Database.BaseDatabase;
import errors.CardSecurityError;
import errors.CustomerAccountInformationError;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.SQLException;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class APITest {



    HashMap<String,String> commands = new HashMap<>();


    JSONObject inCorrectCommand = new JSONObject();
    JSONObject commandIsINT= new JSONObject();
    @Mock
    JSONObject mockObj;
    @Mock BaseDatabase MockDB;




    @Before
    public void initPermeters(){
        MockitoAnnotations.initMocks(this);
        commands.put("command","createcustomer");
        commands.put("command","rentwithloyaltypoints");

        inCorrectCommand.put("command","wrong");

        commandIsINT.put("command",1);
    }


    @Test
    public void testIncorrectCommand(){
        //Arrange
        when(mockObj.getString("command")).thenReturn("invalidCommand");

    }

    @Test
    public void testIncorrectDataType(){
         when(mockObj.getString("command")).thenThrow(new JSONException("incorrect data type"));
    }
    @Captor
    ArgumentCaptor<JSONObject> captor;
    @Test
    public void testCreateCustomer(){
//        int newCustomerID= 3;
//        when(mockObj.getString("command")).thenReturn("createcustomer");
//        when(mockObj.getString("fname")).thenReturn("adam");
//        when(mockObj.getString("lname")).thenReturn("sever");
//        when(mockObj.getString("DOB")).thenReturn("1998/09/02");
//        when(mockObj.getString("address")).thenReturn("Castle Curragh park 45");
//        when(mockObj.getString("CardType")).thenReturn("credit");
//        when(mockObj.getString("cardNumber")).thenReturn("5555555555554444");
//        when(mockObj.getString("accessPlan")).thenReturn("Premium");
//        try {
//            when(mockPresenter.newCustomer(anyString(),anyString(),anyString(),anyString(),anyString(),anyString(),anyString())).thenReturn(newCustomerID);
//            api.request(mockObj);
////            verify(mockPresenter).newCustomer(mockObj.getString("fname"),
////                    mockObj.getString("lname"),
////                    mockObj.getString("DOB"),
////                    mockObj.getString("address"),
////                    mockObj.getString("CardType"),
////                    mockObj.getString("cardNumber"),
////                    mockObj.getString("accessPlan"));
//
//            verify(api).output(captor.capture());
//            assertEquals(captor.getValue().getString("success"), ("true"));
//            assertEquals(captor.getValue().getString("customerID"), ("newCustomerID"));
//
//        } catch (CustomerAccountInformationError customerAccountInformationError) {
//            customerAccountInformationError.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (CardSecurityError cardSecurityError) {
//            cardSecurityError.printStackTrace();
//        }
    }

    @Test
    public void request() {
    }

    @Test
    public void sendError() {
    }

    @Test
    public void sendErrors() {
    }

    @Test
    public void output() {
    }
}