package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import library.Reading;
import impl.org.controlsfx.control.validation.*;

import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.controlsfx.control.StatusBar;
import org.controlsfx.control.decoration.Decoration;
import org.controlsfx.control.decoration.Decorator;
import org.controlsfx.control.decoration.GraphicDecoration;



/**
 * Controller for the Client FXML GUI
 * 
 * @author Ryan Rich
 *
 */
public class ClientController
{
    Client client;
    
    Node decTime, decLocation, decNoOfLanes, decTotalNoVehicles, decAverageNoVehicles, decAverageVelocity;
    
    
    //Decoration decoration = new Decoration();
    
    @FXML private Label txtTitle;
    @FXML private TextField txfTime;
    @FXML private TextField txfLocation;
    @FXML private TextField txfNoOfLanes;
    @FXML private TextField txfTotalNoOfVehicles;
    @FXML private TextField txfAverageNoOfVehicles;
    @FXML private TextField txfAverageVelocity;
    @FXML private CheckBox chkTime;
    @FXML private CheckBox chkLocation;
    @FXML private CheckBox chkAverageNoVehicles;
    @FXML private Button btnSubmit;
    @FXML private Button btnExit;
    @FXML private Button btnConnect;
    @FXML private StatusBar stbStatus;

    @FXML protected void exit (ActionEvent e)
    {
        System.exit(0);
    }
    @FXML protected void submitReading (ActionEvent e)
    {
        
        
        
        Time time;
        int loc, nolns, tnv, anv, avel;
        
        
        boolean isValid = true;
        //Validation Checks
        
        if (!chkTime.isSelected())
        {//Automatic Time
       
            LocalTime lt = LocalTime.now();
            
            time = new Time(lt.getHour(), lt.getMinute(), lt.getSecond());
            
            txfTime.setText(time.toString());
            
        }
        else
        {//Manual Entry and Validation
            LocalTime lt = LocalTime.now();
            
            time = new Time(lt.getHour(), lt.getMinute(), lt.getSecond());
        }
        
        if (!chkLocation.isSelected())
        { //Automatic Location
            loc = client.location;
        }
        else
        { //Manual Selection
            loc = client.location;
        }
        
        nolns = Integer.parseInt(txfNoOfLanes.getText());
        
        tnv = Integer.parseInt(txfTotalNoOfVehicles.getText());
        if (!chkAverageNoVehicles.isSelected())
        { //Automatic Value
            anv = tnv/nolns;
        }
        else
        { //Manual Entry
            anv = tnv/nolns;
        }
        
        avel = Integer.parseInt(txfAverageVelocity.getText());
        
        Reading reading = new Reading (time, loc, nolns, tnv, anv, avel);
        
        System.out.println("Submitting Reading:");
        if (isValid)
        {
            client.SendReading(reading.toString());
        }
        //Node decoration = new Circle(10, Color.RED);

        Node tri = new Polygon(0,0,10,0,0,10);
        tri.setStyle("-fx-fill: RED");
        Tooltip tt = new Tooltip("Message");
        Tooltip.install(tri, tt);


        Decorator.addDecoration( txfLocation, new GraphicDecoration( tri, Pos.TOP_LEFT));

    }
    
    @FXML protected void Connect (ActionEvent e)
    {
        System.out.println("Connecting / Disconnecting");
   
        stbStatus.setText("Conecting to Server");
        if (client.connectToServer())
            {
            btnConnect.setText("Disconnect");
            stbStatus.setText("Connected to "+client.getServerDetails());
            btnSubmit.setDisable(false);
            }
        else
        {
            stbStatus.setText("Connection Failed!");
        }
        
    }
    
    protected void updateLocation(int loc)
    {
        txfLocation.setText(""+loc);
    }
    
    protected void passReference(Client client)
    {
        this.client = client;
    }
    
    public void initialize()
    {
        System.out.println("Initializing the Client Controller");
        LocalTime lt = LocalTime.now();
        Time time = new Time(lt.getHour(), lt.getMinute(), lt.getSecond());
        txfTime.setText(time.toString());
        txfTime.setDisable(true);
        txfLocation.setText("--TBA--");
        txfLocation.setDisable(true);
        txfAverageNoOfVehicles.setText("----");
        txfAverageNoOfVehicles.setDisable(true);
        
        btnSubmit.setDisable(true);
        
        stbStatus.getLeftItems().add(btnConnect);
        Separator sep = new Separator();
        sep.setOrientation(Orientation.VERTICAL);
        stbStatus.getLeftItems().add(sep);
        
    }
   
}
