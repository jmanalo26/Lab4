package main.games.MazeGame;



import java.util.Arrays;
import java.util.Random;

public class BoardData {
    String[][] board;
    public BoardData(int size_x, int size_y) {
        board = new String[size_x][size_y];
        for(String[] row: board) {
            Arrays.fill(row, "X");
        }

    }
    public void print_board() {
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void add_player(int i, int j) {
        board[i][j] = "P";
    }
    public void add_enemy(int i, int j) {
        board[i][j] = "E";
    }

    public int[] getPlayer() {
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                if(board[i][j].equals("P")) {
                    int[] arr = {i,j};
                    return arr;
                }
            }
        }
        return new int[0];
    }
    public void movePlayer(int x, int y) {
        int[] arr = getPlayer();
        board[arr[0]][arr[1]] = "X";
        board[x][y] = "P";

    }

    public int[] getRandomSquare() {
        Random rand = new Random();
        int random_x = rand.nextInt(board.length);
        int random_y = rand.nextInt(board[0].length);

        while(!board[random_x][random_y].equals("X")) {
            random_x = rand.nextInt(board.length);
            random_y = rand.nextInt(board[0].length);
        }
        int[] coordinates = {random_x, random_y};
        return coordinates;
    }
}
