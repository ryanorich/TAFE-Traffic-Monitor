package client;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;




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
    boolean autoconnect = false;
    volatile DataOutputStream out = null;
    volatile DataInputStream in = null;
    ServerListener listener = null;
    int location = 0;
    ClientController controller;
    String server = "ryan-laptop";
    int port = 8888;
    Connection serverCon = new Connection("ryan-laptop",8888);
    
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        library.FXHelper.setupCustomTooltipBehavior(10, 10000, 10);
        stage.setTitle("Traffic Station");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Client.fxml"));
        Pane root = loader.load();
        controller = (ClientController) loader.getController();
        controller.passReference(this);
        /*
        Pane myPane = (Pane)FXMLLoader.load(
                getClass().getResource("Client.fxml"));*/
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(e -> System.exit(0));
        
        if (autoconnect) {
        connectToServer();
        SendReading("Test 1");
        SendReading("Test 2");
        }
    }
    
    public boolean connectToServer()
    {
        try
        {
            Socket socket = new Socket(serverCon.server, serverCon.port);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            Runnable listener = new ServerListener(socket);
            Thread thread = new Thread(listener);
            thread.start();
            
            System.out.println("Writing Out for location");
            
            out.writeUTF("Location");
     

        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        
        
        
        return true;
    }
    
    public String getServerDetails()
    {
        return ""+serverCon;
    }
    
    protected void SendReading(String str)
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
    
    
    boolean exit = false;
    
    ServerListener(Socket socket)
    {

        
    }
    
    @Override
    public void run()
    {
        String message;
        System.out.println("This is the ServerListener Run Method");
        try
        {
            while(!exit)
            {
                System.out.println("Waiting for Reading in UTF:");
                message = in.readUTF();
                if (!message.contentEquals("ping"))
                {
                    location = Integer.parseInt(message);
                    controller.updateLocation(location);
                }
                System.out.println(message);
            }
            

            
        }
        catch (Exception e)
        {
            System.out.println("Esception - "+ e.toString());
        }
        

    }
    
}

}

class Connection
{
    String server;
    int port;
    
    public Connection(String server, int port)
    {
        this.server = server;
        this.port = port;
    }
    
    public void setConnection(String server, int port)
    {
        this.server = server;
        this.port = port;
    }
    
    public String getServer()
    {
        return server;
    }
    public int getPort()
    {
        return port;
    }
    
    
    public String toString()
    {
        return server+":"+port;
    }
}
