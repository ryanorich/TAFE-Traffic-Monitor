package server;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class TreeDisplayController
{
    @FXML
    public AnchorPane ap = new AnchorPane();

    @FXML
    public void initialize()
    {
        Label l1 = new Label("Test");
        l1.relocate(50, 100);
        l1.setStyle("-fx-background-color:#FF8;-fx-border-width:2px;");

        Line line = new Line(0, 0, 500, 700);

        ap.getChildren().addAll(l1, line, new DisplayNode("66", 100, 200));
    }

    // Reference -
    // https://stackoverflow.com/questions/40444966/javafx-making-an-object-with-a-shape-and-text
    public class DisplayNode extends StackPane
    {
        private final int SIZE = 15;
        private final Circle base;
        private final Text text;

        DisplayNode(String text, double x, double y)
        {
            this.text = new Text(x, y, text);
            base = new Circle(x, y, SIZE);

            base.setFill(Color.LIGHTYELLOW);
            base.setStrokeWidth(2);
            base.setStroke(Color.BLACK);
            getChildren().addAll(this.base, this.text);
        }

    }

}
