package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import library.Reading;
import server.ClientThread;

/**
 * Class for creating and managing socket connection to stations.
 * @author Ryan Rich
 *
 */
public class ClientManager extends Thread
{
    int port;
    ServerSocket serverSocket = null;
    Socket socket = null;
    ArrayList<ClientThread> clientThreads = new ArrayList<ClientThread>();
    Thread thread;
    int nextLocation = 1;
    boolean exit = false;
    Server server;

    /**
     * Closes down all client listeners, and the conneciton listener.
     */
    protected void exit()
    {
        exit = true;
        for (ClientThread ct : clientThreads)
        {
            ct.exit = true;
        }
    }

    /**
     * Constructor
     * @param server the server name
     * @param port the server connection port
     */
    protected ClientManager(Server server, int port)
    {
        this.server = server;
        this.port = port;
    }

    public void run()
    {// create a thread, and listen for connection.
        try
        {
            serverSocket = new ServerSocket(port);
            while (!exit)
            {
                Thread.sleep(100);
                // wait for connection request, then create
                socket = serverSocket.accept();
                ClientThread clientThread = null;
                clientThread = new ClientThread(this, socket, nextLocation);
                nextLocation++;
                clientThreads.add(clientThread);
                clientThread.start();
                server.addNotification("Station added at Location "+clientThread.location);
            }
        } catch (IOException | InterruptedException e)
        {
            // exception caused by thread operations, or by interupted sleep
            e.printStackTrace();
        }
    }

    /**
     * Cycles through all connection, and test by sending a message.
     */
    public void testConnections()
    {
        List<ClientThread> threadsToRemove = new LinkedList<ClientThread>();
        for (ClientThread ct : clientThreads)
        {
            try
            {
                // if sending message does not cause exception, then connection is still active
                ct.streamOut.writeUTF("ping");
            }
            catch (Exception e)
            {
                // exception when sending a message to the client, meaning the connection is not active. Start to remove.
                try
                {
                    //TODO - how to nicely destroy socket listening thread.
                    ct.streamIn.close();
                    ct.streamOut.close();
                    //ct.socket.close();
                    //ct.socket = null;
                    threadsToRemove.add(ct);
                } catch (IOException e1)
                {
                    // exception caused by trying to close streams
                    e1.printStackTrace();
                }
            }
        }

        if (threadsToRemove.size() > 0)
        {// there are threads to remove
            server.addNotification("Removing "+threadsToRemove.size()+" clients");
            for (ClientThread ct: threadsToRemove)
            {
                clientThreads.remove(ct);
            }
            server.addNotification("Connection count is now "+clientThreads.size());
        }
        else
        {// no threads to remove
            server.addNotification("All connection are active. Conneciton count is "+clientThreads.size());
        }
    }

    /**
     * Processes reading sent as a string message, and creates a reading object.
     * @param message
     */
    protected void makeReading(String message)
    {
        Reading reading = new Reading(message);
        
        
        server.addReading(reading);
    }
}
