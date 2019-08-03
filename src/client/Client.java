package client;

/**
 * A Traffic Monitoring station, that connects to the server, and 
 * provides traffic readings.
 * 
 * @author Ryan Rich
 */
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Client extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        stage.setTitle("Traffic Station");
        Pane myPane = (Pane)FXMLLoader.load(
                getClass().getResource("Client.fxml"));
        Scene scene = new Scene(myPane);
        stage.setScene(scene);
        stage.show();
    }
}
