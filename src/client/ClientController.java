package client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.stage.Modality;
import javafx.stage.Stage;
import library.Reading;
import java.io.IOException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.regex.Pattern;

import org.controlsfx.control.StatusBar;
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

    @FXML
    private Label txtTitle;
    @FXML
    private TextField txfTime;
    @FXML
    private TextField txfLocation;
    @FXML
    private TextField txfNoOfLanes;
    @FXML
    private TextField txfTotalNoOfVehicles;
    @FXML
    private TextField txfAverageNoOfVehicles;
    @FXML
    private TextField txfAverageVelocity;
    @FXML
    private CheckBox chkTime;
    @FXML
    private CheckBox chkLocation;
    @FXML
    private CheckBox chkAverageNoVehicles;
    @FXML
    private Button btnSubmit;
    @FXML
    private Button btnExit;
    @FXML
    private Button btnConnect;
    @FXML
    private StatusBar stbStatus;

    /**
     * Toggles between automatic and manual entry of the Time field
     */
    @FXML
    protected void chkTimeToggled()
    {
        if (chkTime.isSelected())
        {// Checkbox Selected - Manual Entry
            txfTime.setDisable(false);
            updateTime();
        } else
        {// Checkbox not selectd - Automatic Entry
            txfTime.setDisable(true);
            updateTime();
        }
    }

   /**
    * Toggles between automatic and manual entry of the Location field
    */
    @FXML
    protected void chkLocationToggled()
    {
        if (chkLocation.isSelected())
        {// Checkbox Selected - Manual Entry

            if (client.isConnected)
            {// connected, threfore ensure that the location is set
                txfLocation.setText("-");
            } else
            {// not connected, leave blank
                txfLocation.setText("");
            }
            txfLocation.setDisable(false);
        } else
        {// Checkbox not selectd - Automatic Entry
            if (client.isConnected)
            {
                txfLocation.setText("" + client.location);
            } else
            {
                txfLocation.setText("-");
            }
            txfLocation.setDisable(true);
        }

    }

    /**
     * Toggles between automatica and manual entry o the Average Numner of Vehicles field
     * @param e
     */
    @FXML
    protected void chkAverageNoVehiclesToggled()
    {
        if (chkAverageNoVehicles.isSelected())
        {// Checkbox Selected - Manual Entry
            txfAverageNoOfVehicles.setDisable(false);
            txfAverageNoOfVehicles.setText("");
        } else
        {// Checkbox not selectd - Automatic Entry
            txfAverageNoOfVehicles.setDisable(true);
            txfAverageNoOfVehicles.setText("-");
        }
    }

    /**
     * Exits the application
     */
    @FXML
    protected void exit()
    {
        System.exit(0);
    }

    /**
     * Validates, then sends reading to the connected server
     */
    @SuppressWarnings("deprecation")
    @FXML
    protected void submitReading()
    {
        Time time = null;
        int loc = 0, nolns = 0, tnv = 0, anv = 0, avel = 0;

        boolean isValid = true;
        // Validation Checks

        Decorator.removeAllDecorations(txfTime);
        Decorator.removeAllDecorations(txfLocation);
        Decorator.removeAllDecorations(txfNoOfLanes);
        Decorator.removeAllDecorations(txfTotalNoOfVehicles);
        Decorator.removeAllDecorations(txfAverageNoOfVehicles);
        Decorator.removeAllDecorations(txfAverageVelocity);
        if (!chkTime.isSelected())
        {// Automatic Time

            LocalTime lt = LocalTime.now();
            time = new Time(lt.getHour(), lt.getMinute(), lt.getSecond());
            txfTime.setText(time.toString());

        } else
        {// Manual Entry and Validation
         // Decorator.removeAllDecorations(txfTime);
            if (Pattern.matches("^[0-2][0-9]:[0-5][0-9]:[0-5][0-9]$", txfTime.getText()))
            {
                String t[] = txfTime.getText().split(":");
                time = new Time(Integer.parseInt(t[0]), Integer.parseInt(t[1]), Integer.parseInt(t[2]));

            } else
            {// Invalid entry

                isValid = false;

                Node tri = new Polygon(0, 0, 10, 0, 0, 10);
                tri.setStyle("-fx-fill: RED");
                Tooltip tt = new Tooltip("Required 24h Date Format: hh:mm:ss");
                Tooltip.install(tri, tt);
                Decorator.addDecoration(txfTime, new GraphicDecoration(tri, Pos.TOP_LEFT));
            }

        }

        if (!chkLocation.isSelected())
        { // Automatic Location
            loc = client.location;
        } else
        { // Manual Selection
            if (Pattern.matches("^\\d+$", txfLocation.getText()))
            {// entry is valid
                loc = Integer.parseInt(txfLocation.getText());
            } else
            {// Invalid entry -
                isValid = false;
                Node tri = new Polygon(0, 0, 10, 0, 0, 10);
                tri.setStyle("-fx-fill: RED");
                Tooltip tt = new Tooltip("Requires positive integer value");
                Tooltip.install(tri, tt);
                Decorator.addDecoration(txfLocation, new GraphicDecoration(tri, Pos.TOP_LEFT));
            }
        }

        if (Pattern.matches("^(?!0+$)\\d+$", txfNoOfLanes.getText()))
        {// Valid Entry
            nolns = Integer.parseInt(txfNoOfLanes.getText());
        } else
        {// Invalid Entry
            isValid = false;

            Node tri = new Polygon(0, 0, 10, 0, 0, 10);
            tri.setStyle("-fx-fill: RED");
            Tooltip tt = new Tooltip("Requires positive integer value greater than 0");
            Tooltip.install(tri, tt);
            Decorator.addDecoration(txfNoOfLanes, new GraphicDecoration(tri, Pos.TOP_LEFT));
        }

        if (Pattern.matches("^\\d+$", txfTotalNoOfVehicles.getText()))
        {// Valid Entry
            tnv = Integer.parseInt(txfTotalNoOfVehicles.getText());
        } else
        {// Invalid Entry
            isValid = false;
            Node tri = new Polygon(0, 0, 10, 0, 0, 10);
            tri.setStyle("-fx-fill: RED");
            Tooltip tt = new Tooltip("Requires positive integer value");
            Tooltip.install(tri, tt);
            Decorator.addDecoration(txfTotalNoOfVehicles, new GraphicDecoration(tri, Pos.TOP_LEFT));
        }

        if (!chkAverageNoVehicles.isSelected())
        { // Automatic Value
            if (tnv == 0 || nolns == 0)
            { // Either no vehicles, or an error in number of lanes.
                anv = 0;
                txfAverageNoOfVehicles.setText("0");
            } else
            {
                anv = tnv / nolns;
                txfAverageNoOfVehicles.setText(""+anv);
            }
        } else
        { // Manual Entry

            if (Pattern.matches("^\\d+$", txfAverageNoOfVehicles.getText()))
            {// Valid entry
                anv = Integer.parseInt(txfAverageNoOfVehicles.getText());
            } else
            {// Invalid Entry
                isValid = false;
                Node tri = new Polygon(0, 0, 10, 0, 0, 10);
                tri.setStyle("-fx-fill: RED");
                Tooltip tt = new Tooltip("Requires positive integer value");
                Tooltip.install(tri, tt);
                Decorator.addDecoration(txfAverageNoOfVehicles, new GraphicDecoration(tri, Pos.TOP_LEFT));
            }

        }

        if (Pattern.matches("^\\d+$", txfAverageVelocity.getText()))
        {// Valid Entry
            avel = Integer.parseInt(txfAverageVelocity.getText());
        } else
        {// Invalid Entry
            isValid = false;
            Node tri = new Polygon(0, 0, 10, 0, 0, 10);
            tri.setStyle("-fx-fill: RED");
            Tooltip tt = new Tooltip("Requires positive integer value");
            Tooltip.install(tri, tt);
            Decorator.addDecoration(txfAverageVelocity, new GraphicDecoration(tri, Pos.TOP_LEFT));
        }

        if (isValid)
        {
            Reading reading = new Reading(time, loc, nolns, tnv, anv, avel);
            client.SendReading(reading.toString());
            stbStatus.setText("Reading Submitted");
        } else
        {
            stbStatus.setText("Invalid Reading!!");
        }

    }

    /**
     * Disconnection from the server, and GUI update
     */
    protected void setDisconnect()
    {
        client.disconnectFromServer();
        btnConnect.setText("Connect");
        stbStatus.setText("Disconnected from Server");
        btnSubmit.setDisable(true);
        client.isConnected = false;
        txfLocation.setText("");
    }

    /**
     * Connects or Disconnects to the server, and updates GUI, based on the current connection state.
     */
    @FXML
    protected void Connect()
    {
        if (client.isConnected)
        {// Currently connected - disconnect
            client.disconnectFromServer();
            btnConnect.setText("Connect");
            stbStatus.setText("Disconnected from Server");
            btnSubmit.setDisable(true);
            client.isConnected = false;
            txfLocation.setText("");
        } else
        {// Not currently connected - connect
            stbStatus.setText("Conecting to Server");

            // Dialogue to set the connection
            // stage.setTitle("Traffic Station");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ConnectionDialog.fxml"));
            Pane root = null;
            try
            {
                root = loader.load();
            } catch (IOException e1)
            {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            ConnectionDialogController controller = (ConnectionDialogController) loader.getController();
            controller.passConnection(client.serverConnection);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setOnCloseRequest(e1 -> stage.close());
            stage.showAndWait();

            if (controller.isValidConnection)
            {// details entered, and are OK
                if (client.connectToServer())
                {// connected OK
                    btnConnect.setText("Disconnect");
                    stbStatus.setText("Connected to " + client.serverConnection);
                    btnSubmit.setDisable(false);
                    client.isConnected = true;
                } else
                {// exception raised during connection
                    stbStatus.setText("Connection Failed!");
                    client.isConnected = false;
                }
            }
        }
    }

    /**
     * Updates the Time Text Field to dispoaly the current time
     */
    private void updateTime()
    {
        LocalTime lt = LocalTime.now();
        @SuppressWarnings("deprecation")
        Time time = new Time(lt.getHour(), lt.getMinute(), lt.getSecond());
        txfTime.setText(time.toString());
    }

    /**
     * Updates the TextField with the given location number
     * @param loc the location number to display
     */
    protected void updateLocation(int loc)
    {
        txfLocation.setText("" + loc);
    }

    /**
     * Passes a reference to the Client instance that creats the GUI.
     * @param client Reference to the Client instance
     */
    protected void passReference(Client client)
    {
        this.client = client;
    }

    /**
     * Set-up for GUI element
     */
    public void initialize()
    {
        updateTime();
        txfTime.setDisable(true);
        txfLocation.setText("-");
        txfLocation.setDisable(true);
        txfAverageNoOfVehicles.setText("-");
        txfAverageNoOfVehicles.setDisable(true);
        btnSubmit.setDisable(true);
        stbStatus.getLeftItems().add(btnConnect);
        Separator sep = new Separator();
        sep.setOrientation(Orientation.VERTICAL);
        stbStatus.getLeftItems().add(sep);
    }
}
