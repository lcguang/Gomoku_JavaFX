/**
 * Created by chenguangliu on 4/4/17.
 */

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.util.PrimitiveIterator;
import java.util.Random;

public class Board implements EventHandler<ActionEvent> {

    public void handle(ActionEvent e) {
        Stage secondary_stage = new Stage();
        secondary_stage.setTitle("Gomoku");

        GridPane root = new GridPane();
        final int size = 15;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col ++) {
                StackPane square = new StackPane();
                String color ;
                if ((row + col) % 2 == 0) {
                    color = "white";
                } else {
                    color = "black";
                }
                square.setStyle("-fx-background-color: "+color+";");
                root.add(square, col, row);
            }
        }
        for (int i = 0; i < size; i++) {
            root.getColumnConstraints().add(new ColumnConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true));
            root.getRowConstraints().add(new RowConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, VPos.CENTER, true));
        }
        secondary_stage.setScene(new Scene(root, 500, 500));
        secondary_stage.show();
    }
}
