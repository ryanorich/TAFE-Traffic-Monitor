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

public class Server extends Application
{
    enum sortOrder
    {
        TIME, VEHICLES, VELOCITY
    };

    private ArrayList<RRCompare<Reading>> SortOrderComparitors = new ArrayList();
    public ArrayList<Reading> readings = new ArrayList<Reading>();

    // Access for the Server Controller
    ServerController serverController;

    public ArrayList<Reading> getReadings()
    {
        return readings;

    }

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        stage.setTitle("Traffic Monitor");

        // Pane myPane = (Pane)FXMLLoader.load(
        // getClass().getResource("Server.fxml"));
        // Scene scene = new Scene(myPane);
        // stage.setScene(scene);
        // stage.show();

        // Creating and instance of the loader
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Server.fxml"));
        // Loading the fxml resources, and attachign to new pane
        Pane root = loader.load();

        // Grabs the Server Controller
        serverController = (ServerController) loader.getController();

        // Standad stag setup
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        populateDummyData(readings);
        populateSortOrderCompariotors(SortOrderComparitors);

        serverController.setupReadings(readings);
        serverController.passReference(this);
    }

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
        }

    }

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
            // return 0;
        });
    }

    protected void addRandomReading(ArrayList readings)
    {
        Random rnd = new Random();
        Reading r = new Reading(new Time(rnd.nextInt(24), rnd.nextInt(60), rnd.nextInt(60)), rnd.nextInt(20),
                rnd.nextInt(4), rnd.nextInt(1000), rnd.nextInt(500), rnd.nextInt(50) + 50);
        readings.add(r);

        System.out.println("Reading count is now " + readings.size());
        // Observable List for table does not refresh when underling list changes.
        serverController.updateTable(readings);
    }

    public void addReading()
    {
        readings.add(new Reading());

        System.out.println("Reading count is now " + readings.size());

        serverController.updateTable(readings);
    }
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
