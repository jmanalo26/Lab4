package main.games.MazeGame;



import javafx.stage.Stage;
import main.games.shooter.LBossLevel.BossLevel;
import main.games.shooter.LBossLevel2.BossLevel2;
import main.games.shooter.level2.Level2;

import java.util.Arrays;
import java.util.Random;

public class BoardData2 {
    private Mazelvl2 maze;
    private String[][] board;
    private Player player;
    private Zombielvl2[] zombies;
    private int computerCounter = 0;

    public BoardData2(int size_x, int size_y) {
        maze = Mazelvl2.getInstance();
        board = new String[size_x][size_y];
        for(String[] row: board) {
            Arrays.fill(row, "X");
        }

    }
    public void print_board() {
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                System.out.print(board[j][i] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public int[] getPlayer() {
        int[] pos = {player.getPosX(), player.getPosY()};
        return pos;
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




    public void add_enemies(int num) {
        for(int i = 0; i < num; i++) {
            int[] pos = getRandomSquare();
            maze.addGraphic(new Zombielvl2(pos[0], pos[1]), pos[0], pos[1]);
            board[pos[0]][pos[1]] = "Z";
        }
    }

    public void add_player() {
        player = new Player(1, 1);
        maze.addGraphic(player);
        board[1][1] = "P";
    }

    public void add_perk(int num) {
        for(int i =0; i < num; i++) {
            int[] location = getRandomSquare();
            Perk p = new Perk(location[0], location[1]);
            maze.addGraphic(p, location[0], location[1]);
            board[location[0]][location[1]] = "?";
        }

    }

    public void move_player(String direction) {
        int currentX = player.getPosX();
        int currentY = player.getPosY();

        if(direction.equals("DOWN")) {
            int newX = player.getPosX();
            int newY = player.getPosY() + 1;
            if(board[newX][newY].equals("X")) {
                board[currentX][currentY] = "X";
                maze.removeGraphic(currentX, currentY);
                player.setPosX(newX);
                player.setPosY(newY);
                board[newX][newY] = "P";
            }else if(board[newX][newY].equals("?")) {
                Perk p = new Perk(1,1);
                String perk = p.getRandomPerk();
                System.out.println(perk);
                if (perk.equals("FV")) {
                    maze.full_visibility();
                }
                if (perk.equals("RZ")) {
                    remove_zombies();
                }
                if(perk.equals("MZ")) {
                    add_enemies(5);
                }
                maze.removeGraphic(player.getPosX(), player.getPosY());
                board[player.getPosX()][player.getPosY()] = "X";
                player.setPosX(newX);
                player.setPosY(newY);
                board[player.getPosX()][player.getPosY()] = "P";


            }else if(board[newX][newY].equals("E")) {
                maze.getStage().close();
                BossLevel2 bossLevel = new BossLevel2();
                Stage bossStage = new Stage();
                try {
                    bossLevel.start(bossStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }else if(board[newX][newY].equals("Z")) {
                Level2 miniGame = new Level2();
                Stage lvl2MiniGame = new Stage();
                try {
                    miniGame.start(lvl2MiniGame);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                board[currentX][currentY] = "X";
                maze.removeGraphic(currentX, currentY);
                maze.removeGraphic(newX,newY);
                player.setPosX(newX);
                player.setPosY(newY);
                board[newX][newY] = "P";


            }else if(board[newX][newY].equals("C")) {
                if(computerCounter != 2) {
                    computerCounter++;
                }else {
                    System.out.println("Easter Egg Complete");
                    maze.full_visibility();
                    for(int i = 0; i < board.length; i++) {
                        for(int j = 0; j < board[i].length; j++) {
                            if(board[i][j].equals("Z")) {
                                maze.setGraphic(new Person(i,j), i,j);
                            }
                        }
                    }
                }
                maze.removeGraphic(player.getPosX(), player.getPosY());
                board[player.getPosX()][player.getPosY()] = "X";
                player.setPosX(newX);
                player.setPosY(newY);
                board[player.getPosX()][player.getPosY()] = "P";
            }
        }
        if(direction.equals("UP")) {
            int newX = player.getPosX();
            int newY = player.getPosY() - 1;
            if(board[newX][newY].equals("X")) {
                board[currentX][currentY] = "X";
                maze.removeGraphic(currentX, currentY);
                player.setPosX(newX);
                player.setPosY(newY);
                board[newX][newY] = "P";
            } else if(board[newX][newY].equals("?")) {
                Perk p = new Perk(1,1);
                String perk = p.getRandomPerk();
                System.out.println(perk);
                if (perk.equals("FV")) {
                    maze.full_visibility();
                }
                if (perk.equals("RZ")) {
                    remove_zombies();
                }
                if(perk.equals("MZ")) {
                    add_enemies(5);
                }
                maze.removeGraphic(player.getPosX(), player.getPosY());
                board[player.getPosX()][player.getPosY()] = "X";
                player.setPosX(newX);
                player.setPosY(newY);
                board[player.getPosX()][player.getPosY()] = "P";


            }else if(board[newX][newY].equals("E")) {
                maze.getStage().close();
                BossLevel2 bossLevel = new BossLevel2();
                Stage bossStage = new Stage();
                try {
                    bossLevel.start(bossStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }else if(board[newX][newY].equals("Z")) {
                Level2 miniGame = new Level2();
                Stage lvl2MiniGame = new Stage();
                try {
                    miniGame.start(lvl2MiniGame);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                board[currentX][currentY] = "X";
                maze.removeGraphic(currentX, currentY);
                maze.removeGraphic(newX,newY);
                player.setPosX(newX);
                player.setPosY(newY);
                board[newX][newY] = "P";



            }else if(board[newX][newY].equals("C")) {
                if(computerCounter != 2) {
                    computerCounter++;
                }else {
                    for(int i = 0; i < board.length; i++) {
                        for(int j = 0; j < board[i].length; j++) {
                            maze.full_visibility();
                            if(board[i][j].equals("Z")) {
                                maze.setGraphic(new Person(i,j), i,j);
                            }
                        }
                    }
                }
                maze.removeGraphic(player.getPosX(), player.getPosY());
                board[player.getPosX()][player.getPosY()] = "X";
                player.setPosX(newX);
                player.setPosY(newY);
                board[player.getPosX()][player.getPosY()] = "P";
            }

        }
        if(direction.equals("LEFT")) {
            int newX = player.getPosX() - 1;
            int newY = player.getPosY();
            if(board[newX][newY].equals("X")) {
                board[currentX][currentY] = "X";
                maze.removeGraphic(currentX, currentY);
                player.setPosX(newX);
                player.setPosY(newY);
                board[newX][newY] = "P";
            }else if(board[newX][newY].equals("?")) {
                Perk p = new Perk(1,1);
                String perk = p.getRandomPerk();
                System.out.println(perk);
                if (perk.equals("FV")) {
                    maze.full_visibility();
                }
                if (perk.equals("RZ")) {
                    remove_zombies();
                }
                if(perk.equals("MZ")) {
                    add_enemies(5);
                }
                maze.removeGraphic(player.getPosX(), player.getPosY());
                board[player.getPosX()][player.getPosY()] = "X";
                player.setPosX(newX);
                player.setPosY(newY);
                board[player.getPosX()][player.getPosY()] = "P";


            }else if(board[newX][newY].equals("E")) {
                maze.getStage().close();
                BossLevel2 bossLevel = new BossLevel2();
                Stage bossStage = new Stage();
                try {
                    bossLevel.start(bossStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }else if(board[newX][newY].equals("Z")) {
                Level2 miniGame = new Level2();
                Stage lvl2MiniGame = new Stage();
                try {
                    miniGame.start(lvl2MiniGame);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                board[currentX][currentY] = "X";
                maze.removeGraphic(currentX, currentY);
                maze.removeGraphic(newX,newY);
                player.setPosX(newX);
                player.setPosY(newY);
                board[newX][newY] = "P";



            } else if(board[newX][newY].equals("C")) {
                if(computerCounter != 2) {
                    computerCounter++;
                }else {
                    for(int i = 0; i < board.length; i++) {
                        for(int j = 0; j < board[i].length; j++) {
                            maze.full_visibility();
                            if(board[i][j].equals("Z")) {
                                maze.setGraphic(new Person(i,j), i,j);
                            }
                        }
                    }
                }
                maze.removeGraphic(player.getPosX(), player.getPosY());
                board[player.getPosX()][player.getPosY()] = "X";
                player.setPosX(newX);
                player.setPosY(newY);
                board[player.getPosX()][player.getPosY()] = "P";
            }

        }
        if(direction.equals("RIGHT")) {
            int newX = player.getPosX() + 1;
            int newY = player.getPosY();
            if(board[newX][newY].equals("X")) {
                board[currentX][currentY] = "X";
                maze.removeGraphic(currentX, currentY);
                player.setPosX(newX);
                player.setPosY(newY);
                board[newX][newY] = "P";
            }else if(board[newX][newY].equals("?")) {
                Perk p = new Perk(1,1);
                String perk = p.getRandomPerk();
                System.out.println(perk);
                if (perk.equals("FV")) {
                    maze.full_visibility();
                }
                if (perk.equals("RZ")) {
                    remove_zombies();
                }
                if(perk.equals("MZ")) {
                    add_enemies(5);
                }
                maze.removeGraphic(player.getPosX(), player.getPosY());
                board[player.getPosX()][player.getPosY()] = "X";
                player.setPosX(newX);
                player.setPosY(newY);
                board[player.getPosX()][player.getPosY()] = "P";
            } else if(board[newX][newY].equals("E")) {
                maze.getStage().close();
                BossLevel2 bossLevel = new BossLevel2();
                Stage bossStage = new Stage();
                try {
                    bossLevel.start(bossStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }else if(board[newX][newY].equals("Z")) {
                Level2 miniGame = new Level2();
                Stage lvl2MiniGame = new Stage();
                try {
                    miniGame.start(lvl2MiniGame);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                board[currentX][currentY] = "X";
                maze.removeGraphic(currentX, currentY);
                maze.removeGraphic(newX,newY);
                player.setPosX(newX);
                player.setPosY(newY);
                board[newX][newY] = "P";



            }else if(board[newX][newY].equals("C")) {
                if(computerCounter != 2) {
                    computerCounter++;
                }else {
                    for(int i = 0; i < board.length; i++) {
                        for(int j = 0; j < board[i].length; j++) {
                            maze.full_visibility();
                            if(board[i][j].equals("Z")) {
                                maze.setGraphic(new Person(i,j), i,j);
                            }
                        }
                    }
                }
                maze.removeGraphic(player.getPosX(), player.getPosY());
                board[player.getPosX()][player.getPosY()] = "X";
                player.setPosX(newX);
                player.setPosY(newY);
                board[player.getPosX()][player.getPosY()] = "P";
            }
        }
        maze.addGraphic(player);
        print_board();
    }
    public void remove_zombies() {
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                if (board[i][j].equals("Z")) {
                    maze.removeGraphic(i,j);
                    board[i][j] = "X";
                }
            }
        }
    }
    public void add_obstacles(int[][] arr) {
        for(int i = 0; i < arr.length; i++) {
            for(int j = 0; j < arr[i].length; j++) {
                if(arr[i][j] == 1) {
                    board[i][j] = "O";
                    maze.addGraphic(new Obstaclelvl2(i,j), i, j);
                } else if(arr[i][j] == -1){
                    board[i][j] = "E";
                } else if(arr[i][j] == -2) {
                    board[i][j] = "C";
                    maze.addGraphic(new Computer(i,j), i, j);
                }
            }
        }
    }
}
