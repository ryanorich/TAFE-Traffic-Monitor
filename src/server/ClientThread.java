package server;

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
        
        //upon creation, send the loction to the client.
        streamOut.writeUTF(""+location);


    }

    public void run()
    {
        String reading = "";
        while (!exit)
        {
            try
            {
                Thread.sleep(100);
                reading = streamIn.readUTF();
                if (reading.contentEquals("Location"))
                {// This case is not used - Location send prior to creating listener thread.
                    
                    streamOut.writeUTF(""+location);

                }
                else
                {
                    manager.makeReading(reading);
                }

            } catch (IOException e)
            {
                manager.server.addNotification("Client Disconnection Detected. Poll station to check status");
                //Stop the listening loop
                break;
            } catch (InterruptedException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
