/**
 * Created by chenguangliu on 4/6/17.
 */

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.shape.*;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

public class Chess implements EventHandler<MouseEvent> {

    private int row_;
    private int col_;
    private GridPane root_;
    private Stage stage_;
    private int player_;

    public Chess(int row, int col, GridPane root, Stage stage, int player) {
        row_ = row;
        col_ = col;
        root_ = root;
        stage_ = stage;
        player_ = player;
    }

    public void handle(MouseEvent e) {
        Circle circle = new Circle();
        circle.setRadius(15);
        if (player_ == 0) {
            circle.setFill(Color.BLACK);
        } else {
            circle.setFill(Color.WHITE);
        }

        root_.add(circle, col_, row_);
        Scene scene = new Scene(root_, 450, 450);
        stage_.setScene(scene);
        stage_.show();
    }
}
