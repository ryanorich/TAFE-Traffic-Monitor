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
import java.util.regex.Pattern;

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

    @FXML protected void chkTimeToggled(ActionEvent e)
    {
        
        if (chkTime.isSelected())
        {// Checkbox Selected - Manual Entry
            txfTime.setDisable(false);
            updateTime();
        }
        else
        {//Checkbox not selectd - Automatic Entry
            txfTime.setDisable(true);
            updateTime();

        }
    }
    
    @FXML protected void chkLocationToggled(ActionEvent e)
    {
        if (chkLocation.isSelected())
        {// Checkbox Selected - Manual Entry
            //Check if the default message is still entered
            // TODO use Regex to check this
            if (txfLocation.getText().equals("--TBA--"))
                txfLocation.setText("");
            txfLocation.setDisable(false);
        }
        else
        {//Checkbox not selectd - Automatic Entry
            // TODO - Check for conneciton, and set location.
            txfLocation.setDisable(true);
        }
        
    }
    
    @FXML protected void chkAverageNoVehiclesToggled(ActionEvent e)
    {
        if (chkAverageNoVehicles.isSelected())
        {// Checkbox Selected - Manual Entry
            txfAverageNoOfVehicles.setDisable(false);
        }
        else
        {//Checkbox not selectd - Automatic Entry
            // TODO - Use Regex to check if numeric value entered.
            System.out.println("Checkbox Av No Vehicles Unselected");
            txfAverageNoOfVehicles.setDisable(true);
        }
    }
    
    @FXML protected void exit (ActionEvent e)
    {
        System.exit(0);
    }
    @FXML protected void submitReading (ActionEvent e)
    {
        Time time = null;
        int loc=0, nolns=0, tnv=0, anv=0, avel=0;

        boolean isValid = true;
        //Validation Checks

        Decorator.removeAllDecorations(txfTime);
        Decorator.removeAllDecorations(txfLocation);
        Decorator.removeAllDecorations(txfNoOfLanes);
        Decorator.removeAllDecorations(txfTotalNoOfVehicles);
        Decorator.removeAllDecorations(txfAverageNoOfVehicles);
        Decorator.removeAllDecorations(txfAverageVelocity);
        if (!chkTime.isSelected())
        {//Automatic Time

            LocalTime lt = LocalTime.now();
            time = new Time(lt.getHour(), lt.getMinute(), lt.getSecond());
            txfTime.setText(time.toString());

        }
        else
        {//Manual Entry and Validation
           // Decorator.removeAllDecorations(txfTime);
            if (Pattern.matches("^[0-2][0-9]:[0-5][0-9]:[0-5][0-9]$", txfTime.getText()))
            {
                String t[] = txfTime.getText().split(":");
                time = new Time(Integer.parseInt(t[0]), 
                                Integer.parseInt(t[1]), 
                                Integer.parseInt(t[2]));
                System.out.println("Time format is OK");
            }
            else
            {// Invalid entry
      
                isValid = false;
                
                Node tri = new Polygon(0,0,10,0,0,10);
                tri.setStyle("-fx-fill: RED");
                Tooltip tt = new Tooltip("Required 24h Date Format: hh:mm:ss");
                Tooltip.install(tri, tt);
                Decorator.addDecoration( txfTime, new GraphicDecoration( tri, Pos.TOP_LEFT));
            }

        }

        if (!chkLocation.isSelected())
        { //Automatic Location
            loc = client.location;
        }
        else
        { //Manual Selection
            if (Pattern.matches("^\\d+$", txfLocation.getText()))
            {// entry is valid
                loc = Integer.parseInt(txfLocation.getText());
            }
            else
            {// Invalid entry - 
                isValid = false;
                Node tri = new Polygon(0,0,10,0,0,10);
                tri.setStyle("-fx-fill: RED");
                Tooltip tt = new Tooltip("Requires positive integer value");
                Tooltip.install(tri, tt);
                Decorator.addDecoration( txfLocation, new GraphicDecoration( tri, Pos.TOP_LEFT));
            }
            loc = client.location;
        }
        
        if (Pattern.matches("^(?!0+$)\\d+$", txfNoOfLanes.getText()))
        {// Valid Entry
            nolns = Integer.parseInt(txfNoOfLanes.getText());
        }
        else
        {// Invalid Entry
            isValid = false;
                    
            Node tri = new Polygon(0,0,10,0,0,10);
            tri.setStyle("-fx-fill: RED");
            Tooltip tt = new Tooltip("Requires positive integer value greater than 0");
            Tooltip.install(tri, tt);
            Decorator.addDecoration( txfNoOfLanes, new GraphicDecoration( tri, Pos.TOP_LEFT));
        }
        
        if (Pattern.matches("^\\d+$", txfTotalNoOfVehicles.getText()))
        {// Valid Entry
            tnv = Integer.parseInt(txfTotalNoOfVehicles.getText());
        }
        else
        {// Invalid Entry
             isValid = false;
            Node tri = new Polygon(0,0,10,0,0,10);
            tri.setStyle("-fx-fill: RED");
            Tooltip tt = new Tooltip("Requires positive integer value");
            Tooltip.install(tri, tt);
            Decorator.addDecoration( txfTotalNoOfVehicles, new GraphicDecoration( tri, Pos.TOP_LEFT));
        }
        

        if (!chkAverageNoVehicles.isSelected())
        { //Automatic Value
            if (tnv == 0 || nolns == 0)
            { // Either no vehicles, or an error in number of lanes.
                anv = 0;
            }
            else
            {
                anv = tnv/nolns;
            }
        }
        else
        { //Manual Entry
            
            if (Pattern.matches("^\\d+$", txfAverageNoOfVehicles.getText()))
            {// Valid entry
                anv = Integer.parseInt(txfAverageNoOfVehicles.getText());
            }
            else
            {// Invalid Entry
                isValid = false;
                Node tri = new Polygon(0,0,10,0,0,10);
                tri.setStyle("-fx-fill: RED");
                Tooltip tt = new Tooltip("Requires positive integer value");
                Tooltip.install(tri, tt);
                Decorator.addDecoration( txfAverageNoOfVehicles, new GraphicDecoration( tri, Pos.TOP_LEFT));
            }
        
        }
        
        if (Pattern.matches("^\\d+$", txfAverageVelocity.getText()))
        {// Valid Entry
            avel = Integer.parseInt(txfAverageVelocity.getText());
        }
        else
        {// Invalid Entry
            isValid = false;
            Node tri = new Polygon(0,0,10,0,0,10);
            tri.setStyle("-fx-fill: RED");
            Tooltip tt = new Tooltip("Requires positive integer value");
            Tooltip.install(tri, tt);
            Decorator.addDecoration( txfAverageVelocity, new GraphicDecoration( tri, Pos.TOP_LEFT));
        }
        

        if (isValid)
        {
        
        Reading reading = new Reading (time, loc, nolns, tnv, anv, avel);

        System.out.println("Submitting Reading:");
       
            client.SendReading(reading.toString());
            stbStatus.setText("Reading Submitted");
        }
        else
        {
            stbStatus.setText("Invalid Reading!!");
        }

    }

    @FXML protected void Connect (ActionEvent e)
    {
        System.out.println("Connecting / Disconnecting");

        if (client.isConnected)
        {// Currently connected - disconnect
            client.disconnectFromServer();
            btnConnect.setText("Connect");
            stbStatus.setText("Disconnected from Server");
            btnSubmit.setDisable(true);
            client.isConnected = false;
            txfLocation.setText("");
        }
        else
        {// Not currently connected - connect
        stbStatus.setText("Conecting to Server");

        if (client.connectToServer())
        {
            btnConnect.setText("Disconnect");
            stbStatus.setText("Connected to "+client.getServerDetails());
            btnSubmit.setDisable(false);
            client.isConnected = true;
        }
        else
        {
            stbStatus.setText("Connection Failed!");
            client.isConnected = false;
        }
        }

    }

    private void updateTime()
    {
        LocalTime lt = LocalTime.now();
        Time time = new Time(lt.getHour(), lt.getMinute(), lt.getSecond());
        txfTime.setText(time.toString());
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
        updateTime();
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
