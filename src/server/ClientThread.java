package server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

// Holds a socket, listens, and transmits readings.

public class ClientThread extends Thread
{
    Socket socket;
    int location;
    DataInputStream streamIn;
    DataOutputStream streamOut;
    ClientManager manager;
    
    ClientThread(ClientManager manager, Socket socket) throws IOException
    {
        this.manager = manager;
        this.socket = socket;
        streamIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        streamOut = new DataOutputStream(socket.getOutputStream());
        
        System.out.println("This is the Constructor for the  Client Thread");
       
    }
    
    public void run()
    {
        System.out.println("This is the Run method for the Client Thread");
        String reading = "";
        while (true)
        {
        try
        {
            //System.out.println("Waiting for a connection");
        
                reading = streamIn.readUTF();
                System.out.println("Reading was " + reading);
                System.out.println("Response from Manager: " + manager.getMessage());
      
        } catch (IOException e)
        {
            // TODO Thread Closed - Stop listening?
            e.printStackTrace();
            //Stop the listening loop
            break;
        }
        }
    }

}
