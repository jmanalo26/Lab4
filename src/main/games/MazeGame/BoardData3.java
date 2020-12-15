package main.games.MazeGame;



import javafx.stage.Stage;
import main.games.shooter.LBossLevel.BossLevel;
import main.games.shooter.LBossLevel3.BossLevel3;
import main.games.shooter.level3.Level3;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class BoardData3 {
    private Mazelvl3 maze;
    private String[][] board;
    private Player player;
    private Zombielvl3[] zombies;
    private int computerCounter = 0;
    private int coinCount = 0;
    private ArrayList<Zombielvl3> zList = new ArrayList<Zombielvl3>();
    private ArrayList<Person> pList = new ArrayList<Person>();
    private boolean easter_egg_complete = false;
    private boolean zombiesRemoved = false;

    public BoardData3(int size_x, int size_y) {
        maze = Mazelvl3.getInstance();
        board = new String[size_x][size_y];
        for(String[] row: board) {
            Arrays.fill(row, "X");
        }

    }

    public void setCoinCount(int coinCount) {
        this.coinCount = coinCount;
    }

    public int getCoinCount() {
        return coinCount;
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
            Zombielvl3 z = new Zombielvl3(pos[0], pos[1]);
            zList.add(z);
            maze.addGraphic(z, pos[0], pos[1]);
            board[pos[0]][pos[1]] = "Z";
        }
    }

    public void add_player() {
        player = new Player(1, 1);
        maze.addGraphic(player);
        board[1][1] = "P";
    }

    public void add_perk(int num) {
        for (int i = 0; i < num; i++) {
            int[] location = getRandomSquare();
            Perk p = new Perk(location[0], location[1]);
            maze.addGraphic(p, location[0], location[1]);
            board[location[0]][location[1]] = "?";
        }

    }

    public void move_player(String direction) {
        if(!easter_egg_complete && !zombiesRemoved && (direction.equals("DOWN") || direction.equals("UP") || direction.equals("LEFT") || direction.equals("RIGHT"))) {
            moveZombies(player);
        }
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
                BossLevel3 bossLevel = new BossLevel3();
                Stage bossStage = new Stage();
                try {
                    bossLevel.start(bossStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }else if(board[newX][newY].equals("Z")) {
                Level3 miniGame = new Level3();
                Stage lvl3MiniGame = new Stage();
                try {
                    miniGame.start(lvl3MiniGame);
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
                if(computerCounter == 0) {
                    maze.show_computer_level_1();
                } else if(computerCounter == 1) {
                    maze.show_computer_level_2();
                } else if(computerCounter == 2) {
                    maze.show_computer_level_3();
                }
                if(computerCounter != 2) {
                    computerCounter++;
                }else {
                    easter_egg_complete = true;
                    for(int i = 0; i < board.length; i++) {
                        for(int j = 0; j < board[i].length; j++) {
                            maze.full_visibility();
                            if(board[i][j].equals("Z")) {
                                maze.setGraphic(new Person(i,j), i,j);

                            } else if(board[i][j].equals("O")) {
                                maze.addGraphic("main/games/MazeGame/cake.jpg", i, j);
                            } else if(board[i][j].equals("?")) {
                                maze.addGraphic("main/games/MazeGame/soda.jpg", i, j);
                            }
                        }
                    }
                }
                maze.removeGraphic(player.getPosX(), player.getPosY());
                board[player.getPosX()][player.getPosY()] = "X";
                player.setPosX(newX);
                player.setPosY(newY);
                board[player.getPosX()][player.getPosY()] = "P";
            } else if (board[newX][newY].equals("M")) {
                maze.removeGraphic(player.getPosX(), player.getPosY());
                board[player.getPosX()][player.getPosY()] = "X";
                player.setPosX(newX);
                player.setPosY(newY);
                board[player.getPosX()][player.getPosY()] = "P";
                coinCount = coinCount + 1;
            }else if(board[newX][newY].equals("B")) {
                Perk p = new Perk(1,1);
                String perk = p.getRandomPerk();
                maze.add_perk_box_screen(coinCount);
                if (perk.equals("FV")) {
                    maze.full_visibility();
                }
                if (perk.equals("RZ")) {
                    remove_zombies();
                }
                if(perk.equals("MZ")) {
                    add_enemies(5);
                }
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
                BossLevel3 bossLevel = new BossLevel3();
                Stage bossStage = new Stage();
                try {
                    bossLevel.start(bossStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }else if(board[newX][newY].equals("Z")) {
                Level3 miniGame = new Level3();
                Stage lvl3MiniGame = new Stage();
                try {
                    miniGame.start(lvl3MiniGame);
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
                if(computerCounter == 0) {
                    maze.show_computer_level_1();
                } else if(computerCounter == 1) {
                    maze.show_computer_level_2();
                } else if(computerCounter == 2) {
                    maze.show_computer_level_3();
                }
                if(computerCounter != 2) {
                    computerCounter++;
                }else {
                    easter_egg_complete = true;
                    for(int i = 0; i < board.length; i++) {
                        for(int j = 0; j < board[i].length; j++) {
                            maze.full_visibility();
                            if(board[i][j].equals("Z")) {
                                maze.setGraphic(new Person(i,j), i,j);

                            } else if(board[i][j].equals("O")) {
                                maze.addGraphic("main/games/MazeGame/cake.jpg", i, j);
                            } else if(board[i][j].equals("?")) {
                                maze.addGraphic("main/games/MazeGame/soda.jpg", i, j);
                            }
                        }
                    }
                }
                maze.removeGraphic(player.getPosX(), player.getPosY());
                board[player.getPosX()][player.getPosY()] = "X";
                player.setPosX(newX);
                player.setPosY(newY);
                board[player.getPosX()][player.getPosY()] = "P";
            }else if (board[newX][newY].equals("M")) {
                maze.removeGraphic(player.getPosX(), player.getPosY());
                board[player.getPosX()][player.getPosY()] = "X";
                player.setPosX(newX);
                player.setPosY(newY);
                board[player.getPosX()][player.getPosY()] = "P";
                coinCount = coinCount + 1;
            }else if(board[newX][newY].equals("B")) {

                Perk p = new Perk(1,1);
                String perk = p.getRandomPerk();
                maze.add_perk_box_screen(coinCount);
                if (perk.equals("FV")) {
                    maze.full_visibility();
                }
                if (perk.equals("RZ")) {
                    remove_zombies();
                }
                if(perk.equals("MZ")) {
                    add_enemies(5);
                }

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
                BossLevel3 bossLevel = new BossLevel3();
                Stage bossStage = new Stage();
                try {
                    bossLevel.start(bossStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }else if(board[newX][newY].equals("Z")) {
                Level3 miniGame = new Level3();
                Stage lvl3MiniGame = new Stage();
                try {
                    miniGame.start(lvl3MiniGame);
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
                if(computerCounter == 0) {
                    maze.show_computer_level_1();
                } else if(computerCounter == 1) {
                    maze.show_computer_level_2();
                } else if(computerCounter == 2) {
                    maze.show_computer_level_3();
                }
                if(computerCounter != 2) {
                    computerCounter++;
                }else {
                    easter_egg_complete = true;
                    for(int i = 0; i < board.length; i++) {
                        for(int j = 0; j < board[i].length; j++) {
                            maze.full_visibility();
                            if(board[i][j].equals("Z")) {
                                maze.setGraphic(new Person(i,j), i,j);

                            } else if(board[i][j].equals("O")) {
                                maze.addGraphic("main/games/MazeGame/cake.jpg", i, j);
                            } else if(board[i][j].equals("?")) {
                                maze.addGraphic("main/games/MazeGame/soda.jpg", i, j);
                            }
                        }
                    }
                }
                maze.removeGraphic(player.getPosX(), player.getPosY());
                board[player.getPosX()][player.getPosY()] = "X";
                player.setPosX(newX);
                player.setPosY(newY);
                board[player.getPosX()][player.getPosY()] = "P";
            }else if (board[newX][newY].equals("M")) {
                maze.removeGraphic(player.getPosX(), player.getPosY());
                board[player.getPosX()][player.getPosY()] = "X";
                player.setPosX(newX);
                player.setPosY(newY);
                board[player.getPosX()][player.getPosY()] = "P";
                coinCount = coinCount + 1;
            }else if(board[newX][newY].equals("B")) {
                Perk p = new Perk(1,1);
                String perk = p.getRandomPerk();
                maze.add_perk_box_screen(coinCount);
                if (perk.equals("FV")) {
                    maze.full_visibility();
                }
                if (perk.equals("RZ")) {
                    remove_zombies();
                }
                if(perk.equals("MZ")) {
                    add_enemies(5);
                }

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
                BossLevel3 bossLevel = new BossLevel3();
                Stage bossStage = new Stage();
                try {
                    bossLevel.start(bossStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }else if(board[newX][newY].equals("Z")) {
                Level3 miniGame = new Level3();
                Stage lvl3MiniGame = new Stage();
                try {
                    miniGame.start(lvl3MiniGame);
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
                if(computerCounter == 0) {
                    maze.show_computer_level_1();
                } else if(computerCounter == 1) {
                    maze.show_computer_level_2();
                } else if(computerCounter == 2) {
                    maze.show_computer_level_3();
                }
                if(computerCounter != 2) {
                    computerCounter++;
                }else {
                    easter_egg_complete = true;
                    for(int i = 0; i < board.length; i++) {
                        for(int j = 0; j < board[i].length; j++) {
                            maze.full_visibility();
                            if(board[i][j].equals("Z")) {
                                maze.setGraphic(new Person(i,j), i,j);

                            } else if(board[i][j].equals("O")) {
                                maze.addGraphic("main/games/MazeGame/cake.jpg", i, j);
                            } else if(board[i][j].equals("?")) {
                                maze.addGraphic("main/games/MazeGame/soda.jpg", i, j);
                            }
                        }
                    }
                }
                maze.removeGraphic(player.getPosX(), player.getPosY());
                board[player.getPosX()][player.getPosY()] = "X";
                player.setPosX(newX);
                player.setPosY(newY);
                board[player.getPosX()][player.getPosY()] = "P";
            } else if (board[newX][newY].equals("M")) {
                maze.removeGraphic(player.getPosX(), player.getPosY());
                board[player.getPosX()][player.getPosY()] = "X";
                player.setPosX(newX);
                player.setPosY(newY);
                board[player.getPosX()][player.getPosY()] = "P";
                coinCount = coinCount + 1;
            } else if(board[newX][newY].equals("B")) {
                Perk p = new Perk(1,1);
                String perk = p.getRandomPerk();
                maze.add_perk_box_screen(coinCount);
                if (perk.equals("FV")) {
                    maze.full_visibility();
                }
                if (perk.equals("RZ")) {
                    remove_zombies();
                }
                if(perk.equals("MZ")) {
                    add_enemies(5);
                }

            }
        }
        maze.addGraphic(player);
        print_board();
    }

    private void moveZombies(Player player) {
        int posX = player.getPosX();
        int posY = player.getPosY();
        Zombielvl3 z;
        for(int i = 0; i < zList.size(); i++) {
            z = zList.get(i);
            int zPosX = z.getPosX();
            int zPosY = z.getPosY();
            Random r = new Random();
            ArrayList<String> arr = getOpenPositions(zPosX,zPosY);
            String direction;
            if (arr.size() > 1) {
                int move = r.nextInt(arr.size() - 1);
                direction = arr.get(move);
            } else if(arr.size() == 1) {
                direction = arr.get(0);
            } else {
                direction = "NA";
            }
            board[zPosX][zPosY] = "X";
            switch (direction) {
                case "DOWN":
                    board[zPosX][zPosY - 1] = "Z";
                    z.setPosX(zPosX);
                    z.setPosY(zPosY - 1);
                    maze.removeGraphic(zPosX, zPosY);
                    maze.addGraphic(z, zPosX, zPosY - 1);
                    break;
                case "UP":
                    board[zPosX][zPosY + 1] = "Z";
                    z.setPosX(zPosX);
                    z.setPosY(zPosY + 1);
                    maze.removeGraphic(zPosX, zPosY);
                    maze.addGraphic(z, zPosX, zPosY + 1);
                    break;
                case "LEFT":
                    board[zPosX - 1][zPosY] = "Z";
                    z.setPosX(zPosX - 1);
                    z.setPosY(zPosY);
                    maze.removeGraphic(zPosX, zPosY);
                    maze.addGraphic(z, zPosX - 1, zPosY);
                    break;
                case "RIGHT":
                    board[zPosX + 1][zPosY] = "Z";
                    z.setPosX(zPosX + 1);
                    z.setPosY(zPosY);
                    maze.removeGraphic(zPosX, zPosY);
                    maze.addGraphic(z, zPosX + 1, zPosY);
                    break;
                default:
                    break;
            }

        }
    }
    private ArrayList<String> getOpenPositions(int x, int y) {
        ArrayList<String> arr = new ArrayList<String>();
        if(board[x][y + 1].equals("X")) {
            arr.add("UP");
        }
        if(board[x + 1][y].equals("X")) {
            arr.add("RIGHT");
        }
        if(board[x][y - 1].equals("X")) {
            arr.add("DOWN");
        }
        if(board[x - 1][y].equals("X")) {
            arr.add("LEFT");
        }
        return arr;
    }
    public void remove_zombies() {
        zombiesRemoved = true;
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                if (board[i][j].equals("Z")) {
                    maze.removeGraphic(i,j);
                    board[i][j] = "X";
                }
            }
        }
    }

    public void remove_computers() {
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                if (board[i][j].equals("C")) {
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
                    maze.addGraphic(new Obstaclelvl3(i,j), i, j);
                } else if(arr[i][j] == -1){
                    board[i][j] = "E";
                } else if(arr[i][j] == -2) {
                    board[i][j] = "C";
                    maze.addGraphic(new Computer(i,j), i, j);
                } else if(arr[i][j] == -3) {
                    board[i][j] = "B";
                    maze.addGraphic(new Box(i,j), i, j);
                } else if(arr[i][j] == -4) {
                    board[i][j] = "M";
                    maze.addGraphic(new Coin(i,j), i, j);
                }
            }
        }
    }
}
