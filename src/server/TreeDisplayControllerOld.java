package server;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;



import de.tesis.dynaware.grapheditor.Commands;
import de.tesis.dynaware.grapheditor.GraphEditor;
import de.tesis.dynaware.grapheditor.core.DefaultGraphEditor;
import de.tesis.dynaware.grapheditor.core.connections.ConnectionCommands;
import de.tesis.dynaware.grapheditor.core.view.GraphEditorView;
import de.tesis.dynaware.grapheditor.model.GConnector;
import de.tesis.dynaware.grapheditor.model.GJoint;
import de.tesis.dynaware.grapheditor.model.GModel;
import de.tesis.dynaware.grapheditor.model.GNode;
import de.tesis.dynaware.grapheditor.model.GraphFactory;
import de.tesis.dynaware.grapheditor.model.GConnection;
import de.tesis.dynaware.grapheditor.model.impl.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class TreeDisplayControllerOld
{
    GraphEditor graphEditor;
    //public GraphEditorView gev;
    
    GNode firstNode ;
    GNode secondNode ;
    GNode thirdNode;

    @FXML public AnchorPane ap = new AnchorPane();
 
    public void showTree()
    {
   ap.getChildren().add(new Label("Adding to new ap"));
        
        graphEditor = new DefaultGraphEditor();
        //gev = (GraphEditorView) graphEditor.getView();
        
        ap.getChildren().add(graphEditor.getView());
        
        GModel model = GraphFactory.eINSTANCE.createGModel();
        graphEditor.setModel(model);
        addNodes(model);
        
        
    }
    

    
private GNode createNode() {
        
        GNode node = GraphFactory.eINSTANCE.createGNode();
        
     

        GConnector input = GraphFactory.eINSTANCE.createGConnector();
        
        
        GConnector output = GraphFactory.eINSTANCE.createGConnector();
        GConnector output2 = GraphFactory.eINSTANCE.createGConnector();
        input.setType("top-input");
        
        //input.setId("in");
        
       
        
        
        output.setType("bottom-output");
        output2.setType("bottom-output");
        
        node.getConnectors().add(input);
        node.getConnectors().add(output);
        node.getConnectors().add(output2);
        
        //node.getConnectors().

        return node;
    }

private void addNodes(GModel model) {

    
    /**
     firstNode = createNode();
     secondNode = createNode();
     thirdNode = createNode();

    firstNode.setX(110);
    firstNode.setY(10);

    secondNode.setX(10);
    secondNode.setY(150);

    thirdNode.setX(210);
    thirdNode.setY(150);
    
 
    
    GConnection con1 = GraphFactory.eINSTANCE.createGConnection();
    GJoint gj = GraphFactory.eINSTANCE.createGJoint();
    gj.setX(150);
    gj.setY(150);
    
    firstNode.getConnectors().get(0).getConnections().add(con1);
    thirdNode.getConnectors().get(0).getConnections().add(con1);
    
    con1.setSource(firstNode.getConnectors().get(0));
    con1.setTarget(thirdNode.getConnectors().get(0));
    //con1.setId("con1");
    

    
    con1.getJoints().add(gj);
    
   
    
    Commands.addNode(model, firstNode);
    Commands.addNode(model, secondNode);
    Commands.addNode(model, thirdNode);
    
    */
    
    GNode n1 = GraphFactory.eINSTANCE.createGNode();
    GNode n2 = GraphFactory.eINSTANCE.createGNode();
    
    n1.setHeight(100);
    n1.setWidth(100);
    n2.setHeight(100);
    n2.setWidth(100);
    n2.setY(200);
    
    

    GConnector c1 = GraphFactory.eINSTANCE.createGConnector();
    GConnector c2 = GraphFactory.eINSTANCE.createGConnector();
    c1.setType("bottom-output");
    c2.setType("top-input");
    n1.getConnectors().add(c1);
    n2.getConnectors().add(c2);
    
    //GConnection con1 = GraphFactory.eINSTANCE.createGConnection();
    
    
    GJoint j1 = GraphFactory.eINSTANCE.createGJoint();
    //j1.setConnection(con1);
    j1.setX(50);
    j1.setY(150);
     
    //con1.setSource(c1);
    //con1.setTarget(c2);
  
    //c1.getConnections().add(con1);
    //c2.getConnections().add(con1);
    
     
    
   //con1.setType("");
    
    List<GJoint> jl = new LinkedList<GJoint>();
    jl.add(j1);
    
    Commands.addNode(model,  n1);
    Commands.addNode(model,  n2);
    ConnectionCommands.addConnection(model, c1, c2, "",jl);
    
   

}
    


}
