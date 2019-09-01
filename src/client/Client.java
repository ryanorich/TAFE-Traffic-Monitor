package client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

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
    DataOutputStream out = null;
    ServerListener listener = null;
    int location = 0;
    
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
        
        connectToServer();
        SendReading("Test 1");
        SendReading("Test 2");
    }
    
    private void connectToServer()
    {
        try
        {
            Socket socket = new Socket("ryan-laptop", 8888);
            listener = new ServerListener(socket);
            out = new DataOutputStream(socket.getOutputStream());
            
            
        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
    private void SendReading(String str)
    {
        try
        {
            out.writeUTF(str);
        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


public class ServerListener implements Runnable
{
    private Socket socket;
    
    ServerListener(Socket socket)
    {
        this.socket = socket;
    }
    
    @Override
    public void run()
    {
        
        
        try
        {
            InputStream inStream = socket.getInputStream();
            Scanner scanner = new Scanner(inStream);
            
            location = scanner.nextInt();
            
            while(scanner.hasNext())
            {
                System.out.println("Read -- "+scanner.next());
            }
            
            scanner.close();
            
        }
        catch (Exception e)
        {
            System.out.println("Esception - "+ e.toString());
        }
        

        
    }
    
}

}
