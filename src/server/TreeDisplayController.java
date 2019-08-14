package server;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import library.Reading;

public class TreeDisplayController
{
    @FXML
    public AnchorPane ap = new AnchorPane();
    private ArrayList<Reading> indexedBT;
    private int MAXDEPTH;
    
    private int NODESIZE = 30;
    private int LEVELSPACING = 100;
    private int NODESPACING = 35;
    
    private int PADDING = 20;

    public void initiliseData(ArrayList<Reading> iBT)
    {
        // Assign the indexed binary tree
        this.indexedBT = iBT;

        // create the general parameters for the tree data.
        
        // All trees s
        // Get the maximum tree depth, based on the largest index of the array.
        this.MAXDEPTH = depth(indexedBT.size()-1); 
        
        System.out.println("This is Initialise Data");
        
        
        // Adding Lines
        // Since leaves have no decentants, only need to check up to the first node on the deepest level, which is as 2^depth-1
        int maxIndex = intPow(2, MAXDEPTH)-1;
        
        System.out.println("Max Depth is"+MAXDEPTH);
        
        //Start at each node
        for (int i = 0; i<maxIndex; i++)
        {

            int j;
            //
            if (indexedBT.get(i)!=null)
            {// There is a node - Check for branches.
                // Index of Left Node is always at index*2+1
                j = i*2+1;
                if (indexedBT.get(j) != null)
                {//There will be a left node here - make a line between index i and j
                    
                    ap.getChildren().addAll(new Line(getNodeX(i)+NODESIZE/2,getNodeY(i)+NODESIZE/2,getNodeX(j)+NODESIZE/2,getNodeY(j)+NODESIZE/2 ));
                }
             // Index of Right Node is always at index*2+2
                j = i*2+2;
                if (indexedBT.get(j) != null)
                {//There will be a right node here - make a line between index i and j
                    ap.getChildren().addAll(new Line(getNodeX(i)+NODESIZE/2,getNodeY(i)+NODESIZE/2,getNodeX(j)+NODESIZE/2,getNodeY(j)+NODESIZE/2 ));
                }
            }
        }

        //Adding Nodes
        // TODO - Add i tool-tips for the remaining reading data.
        for (int i = 0; i<indexedBT.size(); i++)
        {
            Reading r = indexedBT.get(i);
            if (r!=null)
            {
                ap.getChildren().addAll(new DisplayNode(""+r.getAverageVelocity(), getNodeX(i), getNodeY(i)));
            }
        }
        
    }
    
    /**
     * Simpel Bit-Wise operatin to determine the level of the current index.
     * @param n The index of the node to check
     * @return The level of the node - level 0 for index 0, level 1 for index 1, level 2 for index 3 etc.
     */
    private int depth(  int n)
    {
        // For binary trees, the index of the first node for a depth is always a power of 2 - 1
        // e.g. depth 2 (the third layer) start at index 3, which is 2^2 - 1. depth 3 starts with 7, which is 2^3-1
        // by adding 1 to n, then first nodes on new depths have a n of a power of 2
        // e.g. depth 2 (the third level) start at n+1 = 4 which is 2^2  level 3  with 8, which is 2^3
        // Bitwise, the first node will have one 1 bit, and all trailing 0's, while the last on the level will consist of all 1's
        // Therefore, the depth of a node is related to the number of shifts required to make 0
        // Example for n = 5
        //  Iteration       n           bits            d
        // -------------------------------------------------
        //  0               6           00000110        0  //Initilisaiton
        //  1                           00000011        1  //First shit, n>0
        //  2                           00000001        2  //Second shift, n>0
        //  3                           00000000           //Third shift, n==0, so break and return
        //       Break - return l = 2;
        // Note - may break for negatie values.
        
        int d= 0;
        n++;
        while ((n=n>>1)>0)
        {
            d++;
        }
        return d;
    }
    
    @FXML
    public void initialize()
    {
        //ArrayList nodeList 
        
        System.out.println("This is Initialize");
        
        //Draw Lines
        
        
        
        //Draw Nodes
        /*
        for (int i = 0; i<indexedBT.size(); i++)
        {
            Reading r = indexedBT.get(i);
            if (r!=null)
            {
                ap.getChildren().addAll(new DisplayNode(""+r.getAverageVelocity(), getNodeX(i), getNodeY(i)));
            }
        }
        */
        /*
        Label l1 = new Label("Test");
        l1.relocate(50, 100);
        l1.setStyle("-fx-background-color:#FF8;-fx-border-width:2px;");

        Line line = new Line(0, 30, 100, 200);
        DisplayNode node1 = new DisplayNode("01", 200, 300);
        DisplayNode node2 = new DisplayNode("02", 0, 0);
        DisplayNode node3 = new DisplayNode("03", 10, 2);

        ap.getChildren().addAll(l1, line, new DisplayNode("01", 200, 300) , new DisplayNode("02", 0, 0), new DisplayNode("03", 10, 2));
        */
    }
    
    private int getNodeX(int index)
    {
        // TODO - Check spacing differences bottom two layers - some nodes look like they line up.
        int depth = depth(index), x=0;
        
        //Offset for the node from the beginning of the layer.
        //First node will always be 2^depth-1, therefore the offset is the difference betwen this and the index
        int layerIndex = index - intPow(2, depth) + 1;
        int spacing = NODESPACING;
        
        if (depth == MAXDEPTH)
        {//We are at the bottom of the tree
            x=spacing * layerIndex;
        }
        else
        {//We not at the bottom of the tree
        
            //spacing doubles for every layer above the bottom
            
            spacing = spacing*intPow(2, (MAXDEPTH-depth));
            
            //Node also starts with hapf the spacing
            
            x = spacing * (layerIndex ) + spacing/2;
            
        }
        return x + PADDING;
    }
    
    // Reference - https://stackoverflow.com/questions/8071363/calculating-powers-of-integers
    int intPow (int a, int b)
    {
        if ( b == 0)        return 1;
        if ( b == 1)        return a;
        if (b %2 == 0 )    return     intPow ( a * a, b/2); //even a=(a^2)^b/2
        else                return a * intPow ( a * a, b/2); //odd  a=a*(a^2)^b/2
    }
    
    private int getNodeY(int index)
    {
        return PADDING+LEVELSPACING*depth(index);
    }
    
    // Reference -
    // https://stackoverflow.com/questions/40444966/javafx-making-an-object-with-a-shape-and-text
    public class DisplayNode extends StackPane
    {
        private final int SIZE = NODESIZE/2;
        private final Circle base;
        private final Text text;

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
