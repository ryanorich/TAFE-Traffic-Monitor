package testing;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import de.tesis.dynaware.grapheditor.GraphEditor;
import de.tesis.dynaware.grapheditor.core.DefaultGraphEditor;




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
        
    }
    
}
