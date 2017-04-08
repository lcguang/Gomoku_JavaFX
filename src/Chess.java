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
import javafx.scene.Node;

public class Chess implements EventHandler<MouseEvent> {

    private int row_;
    private int col_;
    private GridPane root_;
    private Stage stage_;
    private int player_ = 0;

    public Chess(int row, int col, GridPane root, Stage stage, int player) {
        row_ = row;
        col_ = col;
        root_ = root;
        stage_ = stage;
        player_ = player;
    }

    public Chess(GridPane root, Stage stage) {
        root_ = root;
        stage_ = stage;
    }

    public void handle(MouseEvent e) {
        Circle circle = new Circle();
        circle.setRadius(15);
        if (player_ == 0) {
            circle.setFill(Color.BLACK);
        } else {
            circle.setFill(Color.WHITE);
        }

        player_ = 1 - player_;

        Integer row_index = new Double(e.getY() / 30).intValue();
        Integer col_index = new Double(e.getX() / 30).intValue();
        root_.add(circle, col_index, row_index);
        stage_.setScene(new Scene(root_, 450, 450));
        stage_.show();
    }
}
