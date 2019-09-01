package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import server.ClientThread;


// Crates a socket, listens for onnection, and instantiates client threads 


public class ClientManager extends Thread
{
    int port;
    ServerSocket serverSocket = null;
    Socket socket = null;
    ArrayList<ClientThread> clientThreads = new ArrayList<ClientThread>();
    Thread thread;
    int nextLocation = 1;
    
    protected ClientManager(int port)
    {
        
        System.out.println("This is the Constructur");
        this.port = port;
     
        
    }
    
    public void run()
    {// Create a thread, and listen for connection.
        
        System.out.println("This is the Run method");
        try
        {
            serverSocket = new ServerSocket(port);
        } catch (IOException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        
        while (true)
        {
            System.out.println("In main loop, waiting for connection");
            
            
            try
            {
                socket = serverSocket.accept();
                
            }
            catch (Exception e)
            {
                System.out.println("There was an error creating the socket!!");
            }
            
            ClientThread clientThread = null;
            try
            {
                clientThread = new ClientThread(this, socket);
            } catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            clientThreads.add(clientThread);
            clientThread.start();
            
            try
            {
                clientThread.streamOut.write(nextLocation);
                clientThread.location = nextLocation;
                nextLocation++;
            } catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            System.out.println("Thread added, location = "+clientThread.location+", count = "+clientThreads.size());
            
        
        }
     
       
    }
    
    public String getMessage()
    {
        return "--==MESSAGE==--";
    }
    
    public void testConnections()
    {
        List<ClientThread> threadsToRemove = new LinkedList<ClientThread>();
        for (ClientThread ct : clientThreads)
        {
            System.out.println("" + ct.getId()+", Connected:" +ct.socket.isConnected()
                                              +", Closed:" + socket.isClosed()
                                              +", Input Shut Down:" + socket.isInputShutdown()
                                              +", Output Shut Down:" + socket.isInputShutdown()
                                              
                                              
                                              
                                              );
            
            try
            {
                ct.streamOut.writeUTF("ping");
                System.out.println("Pinged OK");
            }
            catch (Exception e)
            {
                System.out.println("Ping Exception!!, closing loction "+ct.location);
                try
                {
                    //TODO - how to nicely destroy socket listening thread.
                    ct.streamIn.close();
                    ct.streamOut.close();
                    ct.socket.close();
                    ct.socket = null;
                    
                    threadsToRemove.add(ct);
                    
                    
                    
                } catch (IOException e1)
                {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
             
                
            }
            
        }
        System.out.println("Connection before "+clientThreads.size());
        for (ClientThread ct: threadsToRemove)
        {
            clientThreads.remove(ct);
        }
        System.out.println("Connection after "+clientThreads.size());
       
    
    }
}
