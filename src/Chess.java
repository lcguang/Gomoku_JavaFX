/**
 * Created by chenguangliu on 4/6/17.
 */

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.shape.*;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.List;

public class Chess implements EventHandler<MouseEvent> {

    private GridPane root_;
    private Stage stage_;
    private int player_;

    private List<Position> player_0_;
    private List<Position> player_1_;

    private int[][] chessboard_;

    public Chess(GridPane root, Stage stage) {
        root_ = root;
        stage_ = stage;
        player_ = 0;
        player_0_ = new ArrayList<Position>();
        player_1_ = new ArrayList<Position>();
        chessboard_ = new int[15][15];
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                chessboard_[i][j] = -1;
            }
        }
    }

    public void handle(MouseEvent e) {
        Integer row_index = new Double(e.getY() / 30).intValue();
        Integer col_index = new Double(e.getX() / 30).intValue();

        // avoid chess falling at a taken position
        if (chessboard_[row_index][col_index] != -1) {
            return;
        }

        // draw chess
        Circle circle = new Circle();
        circle.setRadius(15);
        if (player_ == 0) {
            circle.setFill(Color.BLACK);
            player_0_.add(new Position(row_index, col_index));
        } else {
            circle.setFill(Color.WHITE);
            player_1_.add(new Position(row_index, col_index));
        }

        chessboard_[row_index][col_index] = player_;

        player_ = 1 - player_;

        if (checkWin(row_index, col_index)) {
            Stage win_stage = new Stage();
            win_stage.setTitle("Congratulations!");
            Button win_button = new Button();
            win_button.setText("You Win!");
            win_button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    win_stage.close();
                    stage_.close();
                }
            });
            StackPane pane = new StackPane();
            pane.getChildren().add(win_button);
            win_stage.setScene(new Scene(pane, 200, 100));
            win_stage.show();
        }

        root_.add(circle, col_index, row_index);
        stage_.setScene(new Scene(root_, 450, 450));
        stage_.show();
    }

    private boolean checkWin(int x, int y) {
        return direction1(x, y) || direction2(x, y) || direction3(x, y) || direction4(x, y);
    }

    private boolean direction1(int x, int y) {
        for (int i = 0; i < 5; i++) {
            if (y - i >= 0 &&
                y + 4 - i <= 0xF &&
                chessboard_[x][y - i] == chessboard_[x][y + 1 - i] &&
                chessboard_[x][y - i] == chessboard_[x][y + 2 - i] &&
                chessboard_[x][y - i] == chessboard_[x][y + 3 - i] &&
                chessboard_[x][y - i] == chessboard_[x][y + 4 - i])
                return true;
        }
        return false;
    }

    private boolean direction2(int x, int y) {
        for (int i = 0; i < 5; i++) {
            if (x - i >= 0 &&
                x + 4 - i <= 0xF &&
                chessboard_[x - i][y] == chessboard_[x + 1 - i][y] &&
                chessboard_[x - i][y] == chessboard_[x + 2 - i][y] &&
                chessboard_[x - i][y] == chessboard_[x + 3 - i][y] &&
                chessboard_[x - i][y] == chessboard_[x + 4 - i][y])
                return true;
        }
        return false;
    }

    private boolean direction3(int x, int y) {
        for (int i = 0; i < 5; i++) {
            if (x - i >= 0 &&
                y - i >= 0 &&
                x + 4 - i <= 0xF &&
                y + 4 - i <= 0xF &&
                chessboard_[x - i][y - i] == chessboard_[x + 1 - i][y + 1 - i] &&
                chessboard_[x - i][y - i] == chessboard_[x + 2 - i][y + 2 - i] &&
                chessboard_[x - i][y - i] == chessboard_[x + 3 - i][y + 3 - i] &&
                chessboard_[x - i][y - i] == chessboard_[x + 4 - i][y + 4 - i])
                return true;
        }
        return false;
    }

    private boolean direction4(int x, int y) {
        for (int i = 0; i < 5; i++) {
            if (x + i <= 0xF &&
                y - i >= 0 &&
                x - 4 + i >= 0 &&
                y + 4 - i <= 0xF &&
                chessboard_[x + i][y - i] == chessboard_[x - 1 + i][y + 1 - i] &&
                chessboard_[x + i][y - i] == chessboard_[x - 2 + i][y + 2 - i] &&
                chessboard_[x + i][y - i] == chessboard_[x - 3 + i][y + 3 - i] &&
                chessboard_[x + i][y - i] == chessboard_[x - 4 + i][y + 4 - i])
                return true;
        }
        return false;
    }
}
