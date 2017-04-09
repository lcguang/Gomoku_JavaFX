/**
 * Created by chenguangliu on 4/8/17.
 */

import java.util.*;

public class AI {
    private int[][] chessboard_;

    private boolean[][][] win_matrix_;
    private int[][] player_score_;
    private int[][] ai_score_;
    private int[] player_win_;
    private int[] ai_win_;
    private Map<Position, Set<Integer>> pos_win_map_;
    private int win_count_;

    public AI() {
        chessboard_ = new int[15][15];
        win_matrix_ = new boolean[15][15][572];
        player_score_ = new int[15][15];
        ai_score_ = new int[15][15];
        pos_win_map_ = new HashMap<Position, Set<Integer>>();
        initWinMatrix();
        player_win_ = new int[win_count_];
        ai_win_ = new int[win_count_];

    }

    private void initWinMatrix() {
        win_count_ = 0;
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 11; j++) {
                for (int k = 0; k < 5; k++) {
                    win_matrix_[i][j + k][win_count_] = true;
                    Position pos = new Position(i, j + k);
                    if (!pos_win_map_.containsKey(pos)) {
                        pos_win_map_.put(pos, new HashSet<Integer>());
                    }
                    pos_win_map_.get(pos).add(win_count_);
                }
                win_count_++;
            }
        }
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 15; j++) {
                for (int k = 0; k < 5; k++) {
                    win_matrix_[i + k][j][win_count_] = true;
                    Position pos = new Position(i + k, j);
                    if (!pos_win_map_.containsKey(pos)) {
                        pos_win_map_.put(pos, new HashSet<Integer>());
                    }
                    pos_win_map_.get(pos).add(win_count_);
                }
                win_count_++;
            }
        }
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                for (int k = 0; k < 5; k++) {
                    win_matrix_[i + k][j + k][win_count_] = true;
                    Position pos = new Position(i + k, j + k);
                    if (!pos_win_map_.containsKey(pos)) {
                        pos_win_map_.put(pos, new HashSet<Integer>());
                    }
                    pos_win_map_.get(pos).add(win_count_);
                }
                win_count_++;
            }
        }
        for (int i = 0; i < 11; i++) {
            for (int j = 14; j > 3; j--) {
                for (int k = 0; k < 5; k++) {
                    win_matrix_[i + k][j - k][win_count_] = true;
                    Position pos = new Position(i + k, j - k);
                    if (!pos_win_map_.containsKey(pos)) {
                        pos_win_map_.put(pos, new HashSet<Integer>());
                    }
                    pos_win_map_.get(pos).add(win_count_);
                }
                win_count_++;
            }
        }
    }

    public void updateBoard(int x, int y, int value) {
        chessboard_[x][y] = value;
        Position pos = new Position(x, y);
        Iterator<Integer> it = pos_win_map_.get(pos).iterator();
        while (it.hasNext()) {
            if (value == 1) player_win_[it.next().intValue()]++;
            else ai_win_[it.next().intValue()]++;
        }
    }

    public Position calculatePosition() {
        int max = 0;
        int x = 0, y = 0;
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (0 == chessboard_[i][j]) {
                    for (int k = 0; k < win_count_; k++) {
                        if (win_matrix_[i][j][k]) {
                            if (1 == player_win_[k]) {
                                player_score_[i][j] += 20;     // oringinal: 20
                            } else if (2 == player_win_[k]) {
                                player_score_[i][j] += 40;     // oringinal: 40
                            } else if (3 == player_win_[k]) {
                                player_score_[i][j] += 200;    // oringinal: 200
                            } else if (4 == player_win_[k]) {
                                player_score_[i][j] += 1000;   // oringinal: 1000
                            }
                            if (1 == ai_win_[k]) {
                                ai_score_[i][j] += 32;         // oringinal: 32
                            } else if (2 == ai_win_[k]) {
                                ai_score_[i][j] += 42;         // oringinal: 42
                            } else if (3 == ai_win_[k]) {
                                ai_score_[i][j] += 420;        // oringinal: 420
                            } else if (4 == ai_win_[k]) {
                                ai_score_[i][j] += 2000;       // oringinal: 2000
                            }
                        }
                    }
                    if (player_score_[i][j] > max) {
                        max = player_score_[i][j];
                        x = i;
                        y = j;
                    } else if (player_score_[i][j] == max) {
                        if (ai_score_[i][j] > ai_score_[x][y]) {
                            x = i;
                            y = j;
                        }
                    }
                    if (ai_score_[i][j] > max) {
                        max = ai_score_[i][j];
                        x = i;
                        y = j;
                    } else if (ai_score_[i][j] == max) {
                        if (player_score_[i][j] > player_score_[x][y]) {
                            x = i;
                            y = j;
                        }
                    }
                }
            }
        }
        Position pos = new Position(x, y);
        return pos;
    }
}
