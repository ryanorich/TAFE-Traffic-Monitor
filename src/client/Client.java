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
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Client extends Application
{
    DataOutputStream out = null;
    DataInputStream in = null;
    Socket socket;
    ServerListener listener = null;
    int location = 0;
    ClientController controller;
    String server = "ryan-laptop";
    int port = 8888;
    Connection serverCon = new Connection("ryan-laptop",8888);
    
    public boolean isConnected = false;
    
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

        
    }
    
    public boolean connectToServer()
    {
        try
        {
            socket = new Socket(serverCon.server, serverCon.port);
            
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            Runnable listener = new ServerListener();
            Thread thread = new Thread(listener);
            thread.start();
            
            //Requesting Locaiton # from server
            out.writeUTF("Location");
     

        } 
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        
        
        
        return true;
    }
    
    public void disconnectFromServer()
    {
        try
        {
            if(in!=null)
            {
                in.close();
                in = null;
            }
            
            if(out!=null)
            {
                out.close();
                out = null;
            }
            
            if (socket != null)
            {
                socket.close();
                socket = null;
            }
            
            if (listener!=null)
            {
                listener.exit = true;
                listener = null;
            }
            

            location  = 0;
            
        } 
        catch(Exception e)
        {
            e.printStackTrace();
        }
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

    @Override
    public void run()
    {
        String message;
        System.out.println("This is the ServerListener Run Method");
        try
        {
            while(!exit)
            {
                Thread.sleep(100);
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
            // Exception happens after server disconnects
            System.out.println("Exception - "+ e.toString());
            
            callDisconnect dc = new callDisconnect();
            
            Platform.runLater(dc);

        }
        
    }
    
}

public class callDisconnect implements Runnable
{
    public void run()
    {
        controller.setDisconnect();
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
    
    @Override
    public String toString()
    {
        return server+":"+port;
    }
}
