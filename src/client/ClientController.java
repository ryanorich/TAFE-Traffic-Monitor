package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ClientController
{
    @FXML private Label txtTitle;
    @FXML private TextField txfTime;
    @FXML private TextField txfLocation;
    @FXML private TextField txfNoOfLanes;
    @FXML private TextField txfTotalNoOfVehicles;
    @FXML private TextField txfAverageNoOfVehicles;
    @FXML private TextField txfAverageVelocity;
    @FXML private Button btnSubmit;
    @FXML private Button btnExit;
    
    
    @FXML protected void exit (ActionEvent e)
    {
        System.exit(0);
    }
    @FXML protected void submitReading (ActionEvent e)
    {
        System.out.println("Submitting Reading:");
    }
}
