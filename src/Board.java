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

    private int player_ = 0;

    public void handle(ActionEvent e) {
        Stage secondary_stage = new Stage();
        secondary_stage.setTitle("Gomoku");

        GridPane root = new GridPane();
        final int size = 15;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col ++) {
                StackPane square = new StackPane();
                String color;
                if ((row + col) % 2 == 0) {
                    color = "#cd8038"; //Color.rgb(205, 128, 56);
                } else {
                    color = "#ffc890"; //Color.rgb(255, 200, 144);
                }
                square.setStyle("-fx-background-color: "+color+";");
                //Chess chess = new Chess(row, col, root);
                //square.setOnMouseClicked(chess);
                root.add(square, col, row);
                Chess chess = new Chess(row, col, root, secondary_stage, player_);
                square.setOnMouseClicked(chess);
                player_ = 1 - player_;
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
