package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import library.RRBinaryTree;
import library.RRLinkedList;
import library.Reading;
import server.Server.sortOrder;

/**
 * The controller (C) between the FXML View(V) and the Server class (M) (in
 * MVC).
 * 
 * @author Ryan Rich
 *
 */
public class ServerController
{

    private Server server;
    //public int test;
    ObservableList<Reading> dispalyReadings;
    private Stage treeStage = null;
    TreeDisplayController treeDisplayController = null;

    @FXML
    private TableView<Reading> tbvRecords;
    @FXML
    private TextArea txaNotifications;
    @FXML
    private TextArea txaLinkedList;
    @FXML
    private TextArea txaBinaryTree;

    @FXML
    private void btnSortByTime()
    {
        
        txaNotifications.appendText("Sorting by Time, Location\n");
        server.sortReadings(sortOrder.TIME);
    }

    @FXML
    private void btnSortByVehicles()
    {
        
        txaNotifications.appendText("Sorting by total number of Vehicles\n");
        server.sortReadings(sortOrder.VEHICLES);

    }

    @FXML
    private void btnSortByVelocity()
    {
        txaNotifications.appendText("Sorting by Average Velocity, Time\n");
        server.sortReadings(sortOrder.VELOCITY);
    }
    
    @FXML
    private void btnSortByLocation()
    {
        txaNotifications.appendText("Sorting by Location, Time\n");
        server.sortReadings(sortOrder.LOCATION);
    }

    @FXML
    private void btnPollStations()
    {
        AddNotification("Polling Stations");
        server.testClientConnections();
    }

    @FXML
    private void btnClearNotifications()
    {
        txaNotifications.setText("");
    }
    
    protected void AddNotification(String notification)
    {
        txaNotifications.appendText(notification+"\n");
    }

    @FXML
    private void btnSaveInOrder()
    {

        server.saveJson(server.getBinaryTree().getInOrder());
    }
    @FXML
    private void btnSavePreOrder()
    {

        server.saveJson(server.getBinaryTree().getPreOrder());
    }

    @FXML
    private void btnSavePostOrder()
    {

        server.saveJson(server.getBinaryTree().getPostOrder());
    }
    
    protected void updateTree()
    {
        if (treeStage != null )
        {// If the tree display has been created
            if (treeStage.isShowing())
            {// and if it has not been closed
                // update the tree display
               treeDisplayController.drawTree(server.getIndexedBinaryTree());
            }
        }
    }

    @FXML
    private void btnDiagram() throws IOException
    {
        if (treeStage != null )
        {// If the tree display has been created
            if (treeStage.isShowing())
            {// and if it has not been closed
                // update the tree display
               treeDisplayController.drawTree(server.getIndexedBinaryTree());
               return;
            }
            
        }

        //txaBinaryTree.appendText("Showing Diagram\n");
        // Application.launch(testing.TestingCharts.class, new String[] {});

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TreeDisplay.fxml"));
        Parent parent = fxmlLoader.load();
        treeDisplayController = fxmlLoader.<TreeDisplayController>getController();
        // treeDisplayController.setAppMainObservableList(tvObservableList);

        // treeDisplayController.showTree();
        
        Scene treeScene = new Scene(parent, 300, 200);
        treeStage = new Stage();
        
        System.out.println(treeDisplayController);
        treeDisplayController.drawTree(server.getIndexedBinaryTree());
        
        //treeStage.initModality(Modality.APPLICATION_MODAL);
        treeStage.setScene(treeScene);
        treeStage.show();
        
        
        System.out.println(treeDisplayController);
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
     * Passes a reference to the Server instance, to help pass messages back and
     * forth.
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

    // Debug Buttons
    @FXML
    protected void btnDBGAddReading()
    {
        server.addRandomReading(server.getReadings());
    }

    @FXML
    protected void btnDBGAdd10Readings()
    {
        for (int i= 0; i<10; i++)
        {
            btnDBGAddReading();
        }
    }

    /**
     * Displays reading data in a linked list
     * 
     * @param rrll A linked list of Record objects
     */
    protected void DiplayLinkedList(RRLinkedList<Reading> rrll)
    {
        txaLinkedList.setText(" HEAD ⟺ ");
        Reading r;
        for (int i = 0; i < rrll.getCount(); i++)
        {
            r = rrll.get(i);
            txaLinkedList.appendText("" + r.getTime());
            txaLinkedList.appendText(" ⟺ ");
        }
        txaLinkedList.appendText("TAIL");
    }

    /**
     * Shows a graphical representation of a binary tree of Reading objects.
     * 
     * @param rrBT Binary Tree of Readings
     */
    protected void DisplayBinaryTree(RRBinaryTree<Reading> rrBT)
    {
        List<Reading> list;
        String str = "";
        Reading r;

        txaBinaryTree.setText("InOrder\t\t: ");
        list = rrBT.getInOrder();
        if (list.size() > 0)
        {// there are elements in the list to display
            r = list.get(0);
            str = "" + r.getAverageVelocity() + "~" + r.getTime();
            for (int i = 1; i < list.size(); i++)
            {
                r = list.get(i);
                str = str + ", " + r.getAverageVelocity() + "~" + r.getTime();
            }
            txaBinaryTree.appendText(str);
        }
        
        txaBinaryTree.appendText("\nPreOrder\t\t: ");
        list = rrBT.getPreOrder();
        if (list.size() > 0)
        {
            r = list.get(0);
            str = "" + r.getAverageVelocity() + "~" + r.getTime();
            for (int i = 1; i < list.size(); i++)
            {
                r = list.get(i);
                str = str + ", " + r.getAverageVelocity() + "~" + r.getTime();
            }
            txaBinaryTree.appendText(str);
        }

        txaBinaryTree.appendText("\nPostOrder\t: ");
        list = rrBT.getPostOrder();
        if (list.size() > 0)
        {
            r = list.get(0);
            str = "" + r.getAverageVelocity() + "~" + r.getTime();
            for (int i = 1; i < list.size(); i++)
            {
                r = list.get(i);
                str = str + ", " + r.getAverageVelocity() + "~" + r.getTime();
            }
            txaBinaryTree.appendText(str + "\n");
        }
    }
}
