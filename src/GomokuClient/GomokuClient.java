package GomokuClient;

/**
 * Created by chenguangliu on 4/18/17.
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class GomokuClient extends Application {

    public void start(Stage primary_stage) {
        primary_stage.setTitle("Gomoku");
        Pane root = new Pane();
        root.setPrefWidth(100);

        Button start_button = new Button("start");
        Button exit_button = new Button("exit");

        start_button.setLayoutX(100);
        start_button.setLayoutY(50);
        start_button.setMinWidth(root.getPrefWidth());
        Board board = new Board();
        start_button.setOnAction(board);

        exit_button.setLayoutX(100);
        exit_button.setLayoutY(100);
        exit_button.setMinWidth(root.getPrefWidth());
        exit_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });

        root.getChildren().add(start_button);
        root.getChildren().add(exit_button);

        Scene scene = new Scene(root, 300, 180);
        primary_stage.setScene(scene);
        primary_stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

