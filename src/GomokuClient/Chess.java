package GomokuClient;

/**
 * Created by chenguangliu on 4/18/17.
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
    private int player_; // people for 0, ai for 1

    private int[][] chessboard_;

    private AI ai;

    public Chess(GridPane root, Stage stage) {
        root_ = root;
        stage_ = stage;
        player_ = 0;
        chessboard_ = new int[15][15];
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                chessboard_[i][j] = -1;
            }
        }
        ai = new AI();
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
        } else {
            circle.setFill(Color.WHITE);
        }

        chessboard_[row_index][col_index] = player_;
        ai.updateBoard(row_index, col_index, player_);

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

        player_ = 1 - player_;

        int pos = ai.calculatePosition();
        int ai_row_index = pos / 15;
        int ai_col_index = pos % 15;
        Circle circle_ai = new Circle();
        circle_ai.setRadius(15);
        if (player_ == 0) {
            circle_ai.setFill(Color.BLACK);
        } else {
            circle_ai.setFill(Color.WHITE);
        }

        chessboard_[ai_row_index][ai_col_index] = player_;
        ai.updateBoard(ai_row_index, ai_col_index, player_);

        if (checkWin(ai_row_index, ai_col_index)) {
            Stage win_stage = new Stage();
            win_stage.setTitle("HaHa!");
            Button win_button = new Button();
            win_button.setText("AI Win!");
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

        player_ = 1 - player_;

        root_.add(circle, col_index, row_index);
        root_.add(circle_ai, ai_col_index, ai_row_index);
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