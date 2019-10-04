package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Listener class for connection stations.
 * @author Ryan Rich
 *
 */
public class ClientThread extends Thread
{
    int location;
    DataInputStream streamIn;
    DataOutputStream streamOut;
    ClientManager manager;
    boolean exit=false;

    ClientThread(ClientManager manager, Socket socket, int loc) throws IOException
    {
        this.manager = manager;

        streamOut = new DataOutputStream(socket.getOutputStream());
        streamIn = new DataInputStream(socket.getInputStream());
        location = loc;
        
        // upon creation, send the location to the station.
        streamOut.writeUTF(""+location);
    }

    /**
     * Thread Entry
     */
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
                {// this case is not used - Location send prior to creating listener thread.
                    
                    streamOut.writeUTF(""+location);

                }
                else
                {
                    manager.makeReading(reading);
                }

            } catch (IOException e)
            {
                manager.server.addNotification("Client Disconnection Detected. Poll station to check status");
                // stop the listening loop
                break;
            } catch (InterruptedException e)
            {
                // exception caused when sleep is interupted
                e.printStackTrace();
            }
        }
    }

}
