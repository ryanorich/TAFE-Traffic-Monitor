package server;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Random;

import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolverSpi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn.SortType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import library.*;
import library.AllSorts.RRCompare;
import library.AllSorts.sortType;
/**
 * The server for the IT Town Traffic Monitoring System.
 * This server connects to various monitoring systems, and
 * recieves and stores readings.
 * This server also carries out some demonstrations of 
 * sorting, linked lists, and tree data structures.
 * @author Ryan Rich
 *
 */
public class Server extends Application
{
    enum sortOrder
    {
        TIME, VEHICLES, VELOCITY
    };

    private ArrayList<RRCompare<Reading>> SortOrderComparitors = new ArrayList();
    public ArrayList<Reading> readings = new ArrayList<Reading>();
    private RRLinkedList<Reading> LLReadings = new RRLinkedList<Reading>();

    // Access for the Server Controller
    ServerController serverController;

    /**
     * Access to the current readings list
     * @return  The readings
     */
    public ArrayList<Reading> getReadings()
    {
        return readings;
    }

    /**
     * Program Entry
     * @param args  not used
     */
    public static void main(String[] args)
    {
        launch(args);
    }

    /**
     * Main application initialisation.
     */
    @Override
    public void start(Stage stage) throws Exception
    {
        stage.setTitle("Traffic Monitor");

        // Creating and instance of the loader
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Server.fxml"));
        // Loading the fxml resources, and attachign to new pane
        Pane root = loader.load();

        // Grabs the Server Controller
        serverController = (ServerController) loader.getController();

        // standard stag setup
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        // generate sme initial data, for testing.
        populateDummyData(readings);
        // set up the rules for sorting
        populateSortOrderCompariotors(SortOrderComparitors);

        // prime the Server Controller
        serverController.setupReadings(readings);
        serverController.DiplayLinkedList(LLReadings);
        serverController.passReference(this);
    }

    
    /**
     * Populates the readings list with a bunch or random readings
     * @param readings  The list of reading stored by the server
     */
    void populateDummyData(ArrayList<Reading> readings)
    {
        System.out.println("Seeding Data");

        for (int i = 0; i < 10; i++)
        {
            addRandomReading(readings);
        }

        for (Reading r : readings)
        {
            System.out.println(r);
            LLReadings.add(r);
        }

    }

    /**
     * Crates the various rules for sorting the readigns list
     * @param soc   List of Sort Order Comparitors to be populated
     */
    private void populateSortOrderCompariotors(ArrayList<RRCompare<Reading>> soc)
    {
        soc.add(null);
        soc.set(sortOrder.TIME.ordinal(), (a, b) ->
        {
            
            int ret= a.getTime().compareTo(b.getTime());
            if (ret==0)
            {
                // TODO - Secondary Search
                return 0;
            }
            else
            {
                return ret;
            }
           // return 0;
        });
        
        soc.add(null);
        soc.set(sortOrder.VEHICLES.ordinal(), (a, b) ->
        {
            int an = a.getAverageNoVehicles(), bn = b.getAverageNoVehicles();
            
            
            int ret = an==bn?0: (an>bn?1:-1);
            if (ret == 0)
            {
                // TODO - Secondary Search
                return 0;
            }
            else
            {
                return ret;
            }
        });
        
        soc.add(null);
        soc.set(sortOrder.VELOCITY.ordinal(), (a, b) ->
        {
            int av = a.getAverageVelocity(), bv = b.getAverageVelocity();
            
            if (av == bv)
            {   // TODO - secondary sort here.
                return 0;
            }
            else
            {
                return av>bv?1:-1;
            }

        });
    }

    /**
     * For Testing - adds a reading consisting of random data
     * @param readings  The readings list stored by the server
     */
    protected void addRandomReading(ArrayList readings)
    {
        Random rnd = new Random();
        Reading r = new Reading(new Time(rnd.nextInt(24), rnd.nextInt(60), rnd.nextInt(60)), rnd.nextInt(20),
                rnd.nextInt(4), rnd.nextInt(1000), rnd.nextInt(500), rnd.nextInt(50) + 50);
        readings.add(r);
        LLReadings.add(r);

        System.out.println("Reading count is now " + readings.size());
        // Observable List for table does not refresh when underling list changes.
        serverController.updateTable(readings);
        serverController.DiplayLinkedList(LLReadings);
        
    }

    //TODO Check if this can be deleted.
    /**
     * For Debugging - Adds a blank reading to the list
     */
    public void addReading()
    {
        readings.add(new Reading());

        System.out.println("Reading count is now " + readings.size());

        serverController.updateTable(readings);
    }
    
    /**
     * Sorts the list based on a particular comparison between
     * readings.
     * @param so    A comparitor used for sorting the readings
     */
    protected void sortReadings(sortOrder so)
    {
        AllSorts.sortType st;
        switch (so)
        {
        case TIME:
            st = sortType.INSERT;
            break;
        case VEHICLES:
            st = sortType.SELECTION;
            break;
        case VELOCITY:
            st = sortType.BUBBLE;
            break;
            default:
                st=null;
                break;
        }
        
        readings = AllSorts.RRSort(readings, st, SortOrderComparitors.get(so.ordinal()));
        serverController.updateTable(readings);
        
    }
    

}
