package GomokuClient;

/**
 * Created by chenguangliu on 4/18/17.
 */

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Scene;


public class Board implements EventHandler<ActionEvent> {

    public void handle(ActionEvent e) {
        Stage secondary_stage = new Stage();
        secondary_stage.setTitle("Gomoku");

        GridPane root = new GridPane();
        final int size = 15;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Rectangle square = new Rectangle();
                square.setWidth(30);
                square.setHeight(30);
                if ((row + col) % 2 == 0) {
                    square.setFill(Color.rgb(205, 128, 56));
                } else {
                    square.setFill(Color.rgb(255, 200, 144));
                }
                root.add(square, col, row);
            }
        }

        Chess chess = new Chess(root, secondary_stage);
        root.setOnMouseClicked(chess);

        secondary_stage.setScene(new Scene(root, 450, 450));
        secondary_stage.show();
    }
}
