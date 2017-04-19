package GomokuServer;

/**
 * Created by chenguangliu on 4/19/17.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Game {

    private Player[][] chessboard_;
    public Player current_player_;

    public Game() {
        chessboard_ = new Player[15][15];
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                chessboard_[i][j] = null;
            }
        }
    }

    public boolean checkWin(int x, int y) {
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

    public boolean checkFilledUp() {
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (chessboard_[i][j] == null) {
                    return false;
                }
            }
        }
        return true;
    }

    public synchronized boolean checkLegalMove(int x, int y, Player player) {
        if (player == current_player_ && chessboard_[x][y] == null) {
            chessboard_[x][y] = current_player_;
            current_player_ = current_player_.opponent_;
            current_player_.playerMoved(x, y);
            return true;
        }
        return false;
    }

    class Player extends Thread {
        char mark_;
        Player opponent_;
        Socket socket_;
        BufferedReader input_;
        PrintWriter output_;

        public Player(Socket socket, char mark) {
            socket_ = socket;
            mark_ = mark;
            try {
                input_ = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                output_ = new PrintWriter(socket.getOutputStream());
                output_.println("WELCOME " + mark);
                output_.println("MESSAGE Waiting for opponent to connect");
            } catch (IOException e) {
                System.out.println("Player died: " + e);
            }
        }

        public void setOpponent(Player opponent) {
            opponent_ = opponent;
        }

        public void playerMoved(int x, int y) {
            output_.println("OPPONENT_MOVED " + x + "_" + y);
            output_.println(checkWin(x, y) ? "DEFEAT" : checkFilledUp() ? "TIE" : "");
        }

        public void run() {
            try {
                output_.println("MESSAGE All players connected");

                if (mark_ == '0') {
                    output_.println("MESSAGE Your move");
                }

                while (true) {
                    String command = input_.readLine();
                    if (command.startsWith("MOVE")) {
                        String[] position = command.substring(5).split("_");
                        int x = Integer.parseInt(position[0]);
                        int y = Integer.parseInt(position[1]);
                        if (checkLegalMove(x, y, this)) {
                            output_.println("VALID_MOVE");
                            output_.println(checkWin(x, y) ? "VICTORY" : checkFilledUp() ? "TIE" : "");
                        } else {
                            output_.println("MESSAGE ?");
                        }
                    } else if (command.startsWith("QUIT")) {
                        return;
                    }
                }
            } catch (IOException e) {
                System.out.println("Player died: " + e);
            } finally {
                try {
                    socket_.close();
                } catch (IOException e) {

                }
            }
        }
    }
}
