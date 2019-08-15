package testing;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import de.tesis.dynaware.grapheditor.Commands;
import de.tesis.dynaware.grapheditor.GraphEditor;
import de.tesis.dynaware.grapheditor.core.DefaultGraphEditor;
import de.tesis.dynaware.grapheditor.model.GConnector;
import de.tesis.dynaware.grapheditor.model.GModel;
import de.tesis.dynaware.grapheditor.model.GNode;
import de.tesis.dynaware.grapheditor.model.GraphFactory;




public class TestingCharts extends Application
{
    static void main (String[] args)
    {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        GraphEditor graphEditor = new DefaultGraphEditor();
        
        Scene scene = new Scene(graphEditor.getView(), 800, 600);
        
        primaryStage.setScene(scene);
        primaryStage.show();
        
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
        
        
        
        output.setType("bottom-output");
        output2.setType("bottom-output");
        
        node.getConnectors().add(input);
        node.getConnectors().add(output);
        node.getConnectors().add(output2);

        return node;
    }
    
    private void addNodes(GModel model) {

        GNode firstNode = createNode();
        GNode secondNode = createNode();

        firstNode.setX(150);
        firstNode.setY(150);

        secondNode.setX(400);
        secondNode.setY(200);
        secondNode.setWidth(200);
        secondNode.setHeight(150);

        Commands.addNode(model, firstNode);
        Commands.addNode(model, secondNode);
        
        
    }
    
}
