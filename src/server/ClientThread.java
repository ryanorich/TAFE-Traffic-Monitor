package server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

// Holds a socket, listens, and transmits readings.

public class ClientThread extends Thread
{
    //Socket socket;
    int location;
     DataInputStream streamIn;
     DataOutputStream streamOut;
    ClientManager manager;
    boolean exit=false;
    
    ClientThread(ClientManager manager, Socket socket, int loc) throws IOException
    {
        this.manager = manager;
        //this.socket = socket;
        
        streamOut = new DataOutputStream(socket.getOutputStream());
        streamIn = new DataInputStream(socket.getInputStream());
        location = loc;
        streamOut.writeUTF(""+location);
        
        
        
        System.out.println("This is the Constructor for the  Client Thread");
       
    }
    
    public void run()
    {
        System.out.println("This is the Run method for the Client Thread");
        String reading = "";
        while (!exit)
        {
        try
        {
            //System.out.println("Waiting for a connection");
        
                reading = streamIn.readUTF();
                if (reading.contentEquals("Location"))
                {
                    System.out.println("Sending Location Information");
                    //streamOut.writeUTF("7");
                   
                }
                else
                {
                System.out.println("Reading was " + reading);
                System.out.println("Response from Manager: " + manager.getMessage());
                manager.makeReading(reading);
                }
      
        } catch (IOException e)
        {
            // TODO Thread Closed - Stop listening?
            e.printStackTrace();
            //Stop the listening loop
            break;
        }
        System.out.println("Completed 1 Read/Write Loop");
        }
    }

}
