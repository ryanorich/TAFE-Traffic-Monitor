package server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javafx.fxml.FXML;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import library.Reading;

/**
 * Controller for the Binary Tree diagram display
 * 
 * @author Ryan Rich
 */
public class TreeDisplayController
{
    @FXML
    public AnchorPane ap = new AnchorPane();
    private HashMap<Integer, Reading> indexedBT;
    private int MAXINDEX;
    private int MAXDEPTH;
    private int NODESIZE = 30;
    private int NODERAD = NODESIZE / 2;
    private int LEVELSPACING = 100;
    private int NODESPACING = 35;
    private int PADDING = 20;

    /**
     * Initilises the data, and draws the tree.
     * 
     * @param iBT The indexed Binary Tree data
     */
    public void initiliseData(HashMap<Integer, Reading> iBT)
    {
        // Sets the delays and durations for tooltops.
        library.FXHelper.setupCustomTooltipBehavior(5, 100000, 5);
        // Assign the indexed binary tree
        this.indexedBT = iBT;

        // create the general parameters for the tree data.
        
        //Get the largest keyed index.
        MAXINDEX = Collections.max(indexedBT.keySet());
        
        // Get the maximum tree depth, based on the largest index of the array.
        this.MAXDEPTH = depth(MAXINDEX);

        // Adding Lines
        // Since leaves have no decentants, only need to check up to the first node on
        // the deepest level, which is as 2^depth-1
        //int maxIndex = intPow(2, MAXDEPTH) - 1, j;
        int j;
        // Start at each node
        for (int i = 0; i < MAXINDEX; i++)
        {
            if (indexedBT.get(i) != null)
            {// There is a node - Check for branches.
             // Index of Left Node is always at index*2+1
                j = i * 2 + 1;
                if (indexedBT.get(j) != null)
                {// There will be a left node here - make a line between index i and j
                    ap.getChildren().addAll(new Line(getNodeX(i) + NODERAD, getNodeY(i) + NODERAD,
                            getNodeX(j) + NODERAD, getNodeY(j) + NODERAD));
                }
                // Index of Right Node is always at index*2+2
                j = i * 2 + 2;
                if (indexedBT.get(j) != null)
                {// There will be a right node here - make a line between index i and j
                    ap.getChildren().addAll(new Line(getNodeX(i) + NODERAD, getNodeY(i) + NODERAD,
                            getNodeX(j) + NODERAD, getNodeY(j) + NODERAD));
                }
            }
        }

        // Adding Nodes
        for (int i = 0; i <= MAXINDEX; i++)
        {
            Reading r = indexedBT.get(i);
            if (r != null)
            {
                DisplayNode n = new DisplayNode("" + r.getAverageVelocity(), getNodeX(i), getNodeY(i));
                String s = "Location: " + r.getLocation() + "\nTime: " + r.getTime() + "\nLanes: " + r.getNoOfLanes()
                        + "\nTotal Vehicles: " + r.getTotalNoVehicles() + "\nAverage Vehicles: "
                        + r.getAverageNoVehicles() + "\nAverage Velocity: " + r.getAverageVelocity();
                Tooltip t = new Tooltip(s);

                Tooltip.install(n, t);

                ap.getChildren().addAll(n);
            }
        }

    }

    /**
     * Simple Bit-Wise operation to determine the level of the current index.
     * 
     * @param n The index of the node to check
     * @return The level of the node - level 0 for index 0, level 1 for index 1,
     *         level 2 for index 3 etc.
     */
    private int depth(int n)
    {
        // For binary trees, the index of the first node for a depth is always a power
        // of 2 - 1
        // e.g. depth 2 (the third layer) start at index 3, which is 2^2 - 1. depth 3
        // starts with 7, which is 2^3-1
        // by adding 1 to n, then first nodes on new depths have a n of a power of 2
        // e.g. depth 2 (the third level) start at n+1 = 4 which is 2^2 level 3 with 8,
        // which is 2^3
        // Bitwise, the first node will have one 1 bit, and all trailing 0's, while the
        // last on the level will consist of all 1's
        // Therefore, the depth of a node is related to the number of shifts required to
        // make 0
        // Example for n = 5
        // i n bits____ d
        // --------------
        // 0 6 00000110 0 //Initilisaiton, increment n
        // 1 _ 00000011 1 //First shit, n>0
        // 2 _ 00000001 2 //Second shift, n>0
        // 3 _ 00000000 _ //Third shift, n==0, so break and return
        // Break - return l = 2;
        // Note - may break for negative values.

        int d = 0;
        n++;
        while ((n = n >> 1) > 0)
        {
            d++;
        }
        return d;
    }

    @FXML
    public void initialize()
    {
        //Nothing to do untill data is passed
    }

    /**
     * Gets the node X co-ordinate
     * 
     * @param index The index of the node
     * @return The X co-ordinate
     */
    private int getNodeX(int index)
    {
        // TODO - Check spacing differences bottom two layers - some nodes look like
        // they line up.
        int depth = depth(index), x = 0;

        // Offset for the node from the beginning of the layer.
        // First node will always be 2^depth-1, therefore the offset is the difference
        // between this and the index
        int layerIndex = index - intPow(2, depth) + 1;
        int spacing = NODESPACING;
        
        spacing = spacing * intPow(2, (MAXDEPTH - depth));
        x = spacing * (layerIndex) + spacing / 2;

        return x + PADDING;
    }

    /**
     * Integer Power Operation
     * Reference - https://stackoverflow.com/questions/8071363/calculating-powers-of-integers
     * 
     * @param a Base
     * @param b Exponent
     * @return a^b
     */
    int intPow(int a, int b)
    {
        if (b == 0)
            return 1;
        if (b == 1)
            return a;
        if (b % 2 == 0)
            return intPow(a * a, b / 2); // even a=(a^2)^b/2
        else
            return a * intPow(a * a, b / 2); // odd a=a*(a^2)^b/2
    }

    /**
     * Gets the Node's Y co-ordinate
     * 
     * @param index The Node's index
     * @return The Node's Y co-ordinate
     */
    private int getNodeY(int index)
    {
        return PADDING + LEVELSPACING * depth(index);
    }

    /**
     * Class for displaying the node
     * Reference - https://stackoverflow.com/questions/40444966/javafx-making-an-object-with-a-shape-and-text
     * 
     * @author Ryan Rich
     *
     */
    public class DisplayNode extends StackPane
    {
        private final int SIZE = NODESIZE / 2;
        private final Circle base;
        private final Text text;

        /**
         * Constructor
         * 
         * @param text Text to display
         * @param x    X position co-ordinate
         * @param y    Y position co-ordinate
         */
        DisplayNode(String text, double x, double y)
        {
            this.text = new Text(text);
            base = new Circle(SIZE);

            base.setFill(Color.LIGHTYELLOW);
            base.setStrokeWidth(2);
            base.setStroke(Color.BLACK);
            getChildren().addAll(this.base, this.text);
            this.relocate(x, y);
        }

    }

}
