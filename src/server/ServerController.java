package server;

//import java.sql.Time;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import library.*;
import server.Server.sortOrder;

/**
 * The controller (C) between the FXML View(V) and the Server class (M) (in MVC).
 * 
 * @author Ryan Rich
 *
 */
public class ServerController
{

    private Server server;
    public int test;
    ObservableList<Reading> dispalyReadings;

    @FXML
    private TableView<Reading> tbvRecords;
    @FXML
    private TextArea txaNotifications;
    @FXML
    private TextArea txaLinkedList;
    @FXML
    private TextArea txaBinaryTree;

    @FXML
    private void btnSortByTime(ActionEvent e)
    {
        System.out.println("Sorting table by Time");
        txaNotifications.appendText("Sorting by Time\n");
        server.sortReadings(sortOrder.TIME);
    }

    @FXML
    private void btnSortByVehicles(ActionEvent e)
    {
        System.out.println("Sorting table by Location");
        txaNotifications.appendText("Sorting by Location\n");
        server.sortReadings(sortOrder.VEHICLES);
        
    }

    @FXML
    private void btnSortByVelocity(ActionEvent e)
    {
        System.out.println("Sorting table by Velocity");
        txaNotifications.appendText("Sorting by Velocity\n");
        server.sortReadings(sortOrder.VELOCITY);
    }

    @FXML
    private void btnPollStations(ActionEvent e)
    {
        System.out.println("Polling Stations");
        txaNotifications.appendText("Polling Stations\n");
    }

    @FXML
    private void btnClearNotifications(ActionEvent e)
    {
        System.out.println("Clearing Norificaiton Area");
        txaNotifications.setText("...\n");
    }

    @FXML
    private void btnDisplayPreOrder(ActionEvent e)
    {
        System.out.println("Displaying in Pre-Order");
        txaBinaryTree.appendText("Displaying in Pre-Order\n");
    }

    @FXML
    private void btnDisplayInOrder(ActionEvent e)
    {
        System.out.println("Displaying in In-Order");
        txaBinaryTree.appendText("Displaying in In-Order\n");
    }

    @FXML
    private void btnDisplayPostOrder(ActionEvent e)
    {
        System.out.println("Displaying in Post-Order");
        txaBinaryTree.appendText("Displaying in Post-Order\n");
    }

    @FXML
    private void btnSavePreOrder(ActionEvent e)
    {
        System.out.println("Saving in Pre-Order");
        txaBinaryTree.appendText("Saving in Pre-Order\n");
    }

    @FXML
    private void btnSaveInOrder(ActionEvent e)
    {
        System.out.println("Saving in In-Order");
        txaBinaryTree.appendText("Saving in In-Order\n");
    }

    @FXML
    private void btnSavePostOrder(ActionEvent e)
    {
        System.out.println("Saving in Post-Order");
        txaBinaryTree.appendText("Saving in Post-Order\n");
    }
    
    @FXML
    private void btnDiagram(ActionEvent e)
    {
        System.out.println("Showing Diagram");
        
        txaBinaryTree.appendText("Showing Diagram\n");
        Application.launch(testing.TestingCharts.class, new String[] {});
        
    }

    /**
     * Configures the TableView and populates the reading data.
     * 
     * @param readings The readings to display in the TableView
     */
    @SuppressWarnings("unchecked")
    protected void setupReadings(ArrayList<Reading> readings)
    {
        dispalyReadings = FXCollections.observableArrayList(readings);
        System.out.println("Set up display list - " + dispalyReadings.get(0));
        TableColumn<Reading, String> col;

        col = (TableColumn<Reading, String>) tbvRecords.getColumns().get(0);
        col.setCellValueFactory((r) -> new SimpleStringProperty(r.getValue().getTime().toString()));

        col = (TableColumn<Reading, String>) tbvRecords.getColumns().get(1);
        col.setCellValueFactory((r) -> new SimpleStringProperty("" + r.getValue().getLocation()));

        col = (TableColumn<Reading, String>) tbvRecords.getColumns().get(2);
        col.setCellValueFactory((r) -> new SimpleStringProperty("" + r.getValue().getAverageNoVehicles()));

        col = (TableColumn<Reading, String>) tbvRecords.getColumns().get(3);
        col.setCellValueFactory((r) -> new SimpleStringProperty("" + r.getValue().getAverageVelocity()));

        tbvRecords.setItems(dispalyReadings);
    }

    /**
     * Passes a reference to the Server instance, to help pass messages back and forth.
     * 
     * @param s The instance of the Server class.
     */
    protected void passReference(Server s)
    {
        server = s;
    }

    /**
     * Updates the readings displayed in the table
     * 
     * @param readings The readings to show in the Table View.
     */
    protected void updateTable(ArrayList<Reading> readings)
    {
        dispalyReadings = FXCollections.observableArrayList(readings);
        tbvRecords.setItems(dispalyReadings);
    }
    
    
    //Debug Buttons
    @FXML
    protected void btnDBGAddRecord()
    {
        txaNotifications.appendText("Adding Record. \n");
        server.addRandomReading(server.getReadings());
    }
    
    @FXML
    protected void btnDBGReloadTable()
    {
        txaNotifications.appendText("Reloading Table Data.\n");
        dispalyReadings = FXCollections.observableArrayList(server.getReadings());
        tbvRecords.setItems(dispalyReadings);
    }
    
    @FXML
    protected void btnDBGRefreshTable()
    {
        txaNotifications.appendText("Refreshing Table.\n");
        
        tbvRecords.refresh();
    }
    
    /**
     * Displays reading data in a linked list
     * 
     * @param rrll  A linked list of Record objects
     */
    protected void DiplayLinkedList(RRLinkedList<Reading> rrll)
    {
        //TODO- Better Format this list
        txaLinkedList.setText(" HEAD <==> ");
        for (int i = 0; i<rrll.getCount(); i++)
            txaLinkedList.appendText(rrll.get(i).toString()+" <==> ");
        txaLinkedList.appendText("TAIL");
    }
    

}
