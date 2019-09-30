package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * A Traffic Monitoring station that connects to the server and provides traffic
 * readings.
 * 
 * @author Ryan Rich
 */
public class Client extends Application
{
    DataOutputStream out = null;
    DataInputStream in = null;
    Socket socket;
    ServerListener listener = null;
    int location = 0;
    ClientController controller;
    //String server = "localhost";
    //int port = 8888;
    Connection serverCon = new Connection("localhost", 8888);

    public boolean isConnected = false;

    public static void main(String[] args)
    {
        launch(args);
    }

    /**
     * Application entry and initilisation
     */
    @Override
    public void start(Stage stage) throws Exception
    {
        // set the tool-tip timing
        library.FXHelper.setupCustomTooltipBehavior(10, 10000, 10);
        stage.setTitle("Traffic Station");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Client.fxml"));
        Pane root = loader.load();
        controller = (ClientController) loader.getController();
        controller.passReference(this);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(e -> System.exit(0));
    }

    /**
     * Creates a socket connection to the specified server
     * 
     * @return true if conected and able to send a message
     */
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

            // Requesting Locaiton # from server - possibly redundant
            out.writeUTF("Location");
        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            // return false due to exception
            return false;
        }
        // no exception occured, therefore return true
        return true;
    }

    /**
     * Shuts down streams and listener thread
     */
    public void disconnectFromServer()
    {
        try
        {
            if (in != null)
            {
                in.close();
                in = null;
            }
            if (out != null)
            {
                out.close();
                out = null;
            }
            if (socket != null)
            {
                socket.close();
                socket = null;
            }
            if (listener != null)
            {
                listener.exit = true;
                listener = null;
            }
            location = 0;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Send the reading as a string
     * 
     * @param str reading stored as a UTF String
     */
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

    /**
     * Listener for messages from the server
     * 
     * @author Ryan Rich
     *
     */
    public class ServerListener implements Runnable
    {
        boolean exit = false;

        @Override
        public void run()
        {
            String message;

            try
            {
                while (!exit)
                {
                    Thread.sleep(100);

                    // Wait for a message from the server
                    message = in.readUTF();
                    if (!message.contentEquals("ping"))
                    { // currently only send "ping", or the location number
                        location = Integer.parseInt(message);
                        controller.updateLocation(location);
                    }
                }
            } catch (Exception e)
            {
                // Exception happens after server disconnects

                // seperate threads cannot directly affect the GUI, so place request to run
                // later
                Platform.runLater(new callDisconnect());
            }
        }
    }

    /**
     * Threaded class used to carry out GUI modifications on disconnection from the
     * server.
     * 
     * @author Ryan Rich
     *
     */
    public class callDisconnect implements Runnable
    {
        public void run()
        {
            controller.setDisconnect();
        }
    }
}

/**
 * Stores server name and port number
 * 
 * @author Ryan Rich
 *
 */
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
        return server + ":" + port;
    }
}
