package client;


import java.util.regex.Pattern;
import org.controlsfx.control.decoration.Decorator;
import org.controlsfx.control.decoration.GraphicDecoration;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

/**
 * Controller for the Connection Dialogue
 * @author Ryan Rich
 *
 */
public class ConnectionDialogController
{
    Connection serverConn;
    
    Boolean OK = false;
    
    @FXML private TextField txfServer;
    @FXML private TextField txfPort;
    @FXML private Button btnConnOK;
    @FXML private Button btnConnCancel;
    
    @FXML private void OKButtonPressed()
    {
        //Validation
        Boolean isValid = true;
        
        Decorator.removeAllDecorations(txfServer);
        Decorator.removeAllDecorations(txfPort);
        //Regex Reference - https://stackoverflow.com/questions/106179/regular-expression-to-match-dns-hostname-or-ip-address
        if (Pattern.matches("^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$",txfServer.getText()) 
                || Pattern.matches("^([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])(\\.([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9]))*$", txfServer.getText()))
        {//Valid
            serverConn.server = txfServer.getText();
        }
        else
        {//Invalid
            isValid = false;
            Node tri = new Polygon(0,0,10,0,0,10);
            tri.setStyle("-fx-fill: RED");
            Tooltip tt = new Tooltip("Invalid IP or server name");
            Tooltip.install(tri, tt);
            Decorator.addDecoration( txfServer, new GraphicDecoration( tri, Pos.TOP_LEFT));
        }
        
        
        // Regex Reference - https://stackoverflow.com/questions/12968093/regex-to-validate-port-number
        if (Pattern.matches("^([1-9][0-9]{0,3}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5])$", txfPort.getText()))
        {//Valid
            serverConn.port = Integer.parseInt(txfPort.getText());
       
        }
        else
        {// invalid
            isValid = false;
            Node tri = new Polygon(0,0,10,0,0,10);
            tri.setStyle("-fx-fill: RED");
            Tooltip tt = new Tooltip("Reauired Port # 1-65535");
            Tooltip.install(tri, tt);
            Decorator.addDecoration( txfPort, new GraphicDecoration( tri, Pos.TOP_LEFT));
        }
        
        if (isValid == true)
        {
            OK=true;
            Stage stage = (Stage) btnConnCancel.getScene().getWindow();
            stage.close();
        }
        
  
    }
    
    /**
     * Closes the connection window
     */
    @FXML private void CancelButtonPressed()
    {
        Stage stage = (Stage) btnConnCancel.getScene().getWindow();
        stage.close();
    }
    
    /**
     * Populates the GUI with the current server details
     * @param conn the current server connection
     */
    protected void passConnection(Connection conn)
    {
        serverConn = conn;
        txfServer.setText(serverConn.server);
        txfPort.setText(""+serverConn.port);
    }
}
