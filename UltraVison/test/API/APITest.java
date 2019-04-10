package API;

import Database.BaseDatabase;
import Database.SQLite;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class APITest {



    HashMap<String,String> commands = new HashMap<>();


    JSONObject inCorrectCommand = new JSONObject();
    JSONObject commandIsINT= new JSONObject();


    @Before
    public void initPermeters(){
        MockitoAnnotations.initMocks(this);
        commands.put("command","createcustomer");
        commands.put("command","rentwithloyaltypoints");

        inCorrectCommand.put("command","wrong");

        commandIsINT.put("command",1);

    }
    @Mock
    List<String> mockedList;

    @Test
    public void whenUseMockAnnotation_thenMockIsInjected() {
        mockedList.add("one");
        System.out.println(mockedList.size() + "size");
        System.out.println(mockedList.get(0) + "value at 0");
        verify(mockedList).add("one");
        assertEquals(0, mockedList.size());

        when(mockedList.size()).thenReturn(23);
        System.out.println(mockedList.size() + " size");
        assertEquals(23, mockedList.size());
    }

    @Mock
    JSONObject mockObj;
    @Mock BaseDatabase MockDB;
    @Mock Presenter MockPresenter;
    @Mock API mockAPI;
    @Test
    public void testIncorrectCommand(){
        //Arrange
        when(mockObj.getString("command")).thenReturn("invalidCommand");
        System.out.println(mockObj.getString("command"));
        doNothing().when(mockAPI).request(mockObj);
        //vertify
        verify(mockAPI, times(4)).sendError(anyString());
        verify(mockAPI, times(4)).output(anyObject());


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