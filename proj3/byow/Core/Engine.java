package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.StdDraw;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import edu.princeton.cs.algs4.Out;
import edu.princeton.cs.algs4.In;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    //private static final long SEED = 489456;
    //private static final Random RANDOM = new Random();

    private static boolean[][] occupied = new boolean[WIDTH][HEIGHT];
    private static ArrayList<position> centerTracker = new ArrayList<>();


    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        ter.initialize(80, 34);
        menuScreen();
        StdDraw.pause(1000);
        TETile[][] world = readInputString(ter);
        hud(world);
        StdDraw.pause(500);
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     * <p>
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     * <p>
     * In other words, running both of these:
     * - interactWithInputString("n123sss:q")
     * - interactWithInputString("lww")
     * <p>
     * should yield the exact same world state as:
     * - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // TODO: Fill out this method so that it run the engine using the input
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.
        String[] inputs = input.split("(?<=\\d)(?=\\D)|(?<=\\D)(?=\\d)");
        TETile[][] randomTiles = new TETile[80][34];
        ter.initialize(80, 34);
        if (Objects.equals(inputs[0], "N") || Objects.equals(inputs[0], "n")) {
            String s = inputs[1];
            long seed = Long.parseLong(s);
            fillWithNothing(randomTiles);
            Random RANDOM = new Random(seed);
            drawAllRoom(RANDOM, randomTiles);
            position current = initializePlayer(RANDOM, randomTiles);
            movePlayerString(current, randomTiles, inputs[2]);
        }
        if (inputs[0].length() > 1) {
            if (Objects.equals(inputs[0].charAt(0), "L") || Objects.equals(inputs[0].charAt(0), "l")) {
                loadGame("saved_game.txt");
            }
            //movePlayerString(p, randomTiles, inputs[0]);
        }

        TETile[][] finalWorldFrame = randomTiles;
        ter.renderFrame(randomTiles);
        hud(randomTiles);
        return finalWorldFrame;
    }
    public static void quitGame() {
        System.exit(0);
    }

    public static void saveGame(TETile[][] tiles, position p) {
        StdDraw.pause(1000);
        String filename = "saved_game.txt";
        System.out.println("Writing to " + filename);
        Out out = new Out(filename);
    }

    public static void loadGame(String filename) {
        System.out.println("Reading from " + filename + ". Contents are:");
        In in = new In(filename);
        String s = in.readAll();
        System.out.println(s);
    }

    public static void movePlayerString(position p, TETile[][] tiles, String typed) {
        for (int i = 0; i < typed.length(); i++) {
            Character x = typed.charAt(i);
            position current = p;
            if (Objects.equals(x, 'w') || Objects.equals(x, 'W')) {
                position check = p.shift(0, 1);
                if (!tiles[check.x][check.y].equals(Tileset.WALL)) {
                    p = p.shift(0, 1);
                    tiles[current.x][current.y] = Tileset.FLOOR;
                    tiles[p.x][p.y] = Tileset.AVATAR;
                }
            } else if (Objects.equals(x, 's') || Objects.equals(x, 'S')) {
                position check = p.shift(0, -1);
                if (!tiles[check.x][check.y].equals(Tileset.WALL)) {
                    p = p.shift(0, -1);
                    tiles[current.x][current.y] = Tileset.FLOOR;
                    tiles[p.x][p.y] = Tileset.AVATAR;
                }
            } else if (Objects.equals(x, 'a') || Objects.equals(x, 'A')) {
                position check = p.shift(-1, 0);
                if (!tiles[check.x][check.y].equals(Tileset.WALL)) {
                    p = p.shift(-1, 0);
                    tiles[current.x][current.y] = Tileset.FLOOR;
                    tiles[p.x][p.y] = Tileset.AVATAR;
                }
            } else if (Objects.equals(x, 'd') || Objects.equals(x, 'D')) {
                position check = p.shift(1, 0);
                if (!tiles[check.x][check.y].equals(Tileset.WALL)) {
                    p = p.shift(1, 0);
                    tiles[current.x][current.y] = Tileset.FLOOR;
                    tiles[p.x][p.y] = Tileset.AVATAR;
                }
            } else if (Objects.equals(x, 'q') || Objects.equals(x, 'Q')) {
                saveGame(tiles, p);
                quitGame();
            }
        }
    }
    public void hud(TETile[][] tiles) {
        int mouseX = (int) StdDraw.mouseX();
        int mouseY = (int) StdDraw.mouseY();
        while (!StdDraw.hasNextKeyTyped()) {
            if (mouseX != StdDraw.mouseX() || mouseY != StdDraw.mouseY()) {
                mouseX = (int) StdDraw.mouseX();
                mouseY = (int) StdDraw.mouseY();
                if (mouseY >= 34 || mouseY < 0 || mouseX >= 80 || mouseX < 0) {
                    continue;
                }
                StdDraw.setPenColor(Color.WHITE);
                Font fontBig = new Font("Monaco", Font.BOLD, 15);
                StdDraw.setFont(fontBig);
                if (tiles[mouseX][mouseY] == Tileset.FLOOR) {
                    StdDraw.text(5, HEIGHT, "Floor Tile");
                    StdDraw.show();
                    StdDraw.pause(500);
                } else if (tiles[mouseX][mouseY] == Tileset.WALL) {
                    StdDraw.text(5, HEIGHT, "Wall Tile");
                    StdDraw.show();
                    StdDraw.pause(500);
                }
                ter.renderFrame(tiles);
            }
        }
    }
    public static void menuScreen() {
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        StdDraw.text(WIDTH / 2, HEIGHT - 5, "CS61B: THE GAME");
        StdDraw.text(WIDTH / 2, HEIGHT / 2, "Quit (Q)");
        StdDraw.text(WIDTH / 2, HEIGHT / 2 + HEIGHT / 10, "Load Game (L)");
        StdDraw.text(WIDTH / 2, HEIGHT / 2 + 2 * HEIGHT / 10, "New Game (N)");
        StdDraw.show();
    }
    public TETile[][] readInputString(TERenderer ter) {
        String s = "";
        boolean run = true;
        while (run) {
            while (StdDraw.hasNextKeyTyped()) {
                s += StdDraw.nextKeyTyped();
                if (s.equals("N") || s.equals("n")) {
                    run = false;
                }
            }
        }
        TETile[][] randomTiles = new TETile[0][];
        if (s.equals("N") || s.equals("n")) {
            StdDraw.clear(Color.BLACK);
            StdDraw.setXscale(0, WIDTH);
            StdDraw.setYscale(0, HEIGHT);
            Font font = new Font("Monaco", Font.BOLD, 30);
            StdDraw.setFont(font);
            StdDraw.text(WIDTH / 2, HEIGHT / 2 + HEIGHT / 10, "Please enter a seed");
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.show();
            Long seed = solicitSeed();
            randomTiles = new TETile[80][34];
            fillWithNothing(randomTiles);
            Random RANDOM = new Random(seed);
            drawAllRoom(RANDOM, randomTiles);
            position current = initializePlayer(RANDOM, randomTiles);
            ter.renderFrame(randomTiles);
            movePlayerKeyboard(current, randomTiles, ter);
            return randomTiles;
        }
        if (s.equals("L") || s.equals("l")) {
            loadGame("saved_game.txt");
        }
        return null;
    }
    public static void drawFrame(String s) {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font fontBig = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(fontBig);
        StdDraw.text(WIDTH / 2, HEIGHT / 2, s);
        StdDraw.show();
    }
    public static long solicitSeed() {
        String s = "";
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char curr = StdDraw.nextKeyTyped();
                if (Objects.equals(curr, 's') || Objects.equals(curr, 'S')) {
                    break;
                }
                s += curr;
                drawFrame(s);
                StdDraw.pause(200);
            }
        }
        return Long.parseLong(s);
    }
    public static position initializePlayer(Random RANDOM, TETile[][] tiles) {
        position p = new position(RANDOM.nextInt(1, WIDTH), RANDOM.nextInt(0, HEIGHT-1));
        while (!occupied[p.x][p.y]) {
            p = new position(RANDOM.nextInt(1, WIDTH), RANDOM.nextInt(0, HEIGHT - 1));
        }
        tiles[p.x][p.y] = Tileset.AVATAR;
        return p;
    }

    public static void movePlayerKeyboard(position p, TETile[][] tiles,TERenderer ter) {
        String s = "";
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char curr = StdDraw.nextKeyTyped();
                position current = p;
                if (Objects.equals(curr, 'w') || Objects.equals(curr, 'W')) {
                    position check = p.shift(0,1);
                    if (!tiles[check.x][check.y].equals(Tileset.WALL)) {
                        p = p.shift(0,1);
                        tiles[current.x][current.y] = Tileset.FLOOR;
                        tiles[p.x][p.y] = Tileset.AVATAR;
                    }
                }
                if (Objects.equals(curr, 's') || Objects.equals(curr, 'S')) {
                    position check = p.shift(0,-1);
                    if (!tiles[check.x][check.y].equals(Tileset.WALL)) {
                        p = p.shift(0,-1);
                        tiles[current.x][current.y] = Tileset.FLOOR;
                        tiles[p.x][p.y] = Tileset.AVATAR;
                    }
                }
                if (Objects.equals(curr, 'a') || Objects.equals(curr, 'A')) {
                    position check = p.shift(-1,0);
                    if (!tiles[check.x][check.y].equals(Tileset.WALL)) {
                        p = p.shift(-1,0);
                        tiles[current.x][current.y] = Tileset.FLOOR;
                        tiles[p.x][p.y] = Tileset.AVATAR;
                    }
                }
                if (Objects.equals(curr, 'd') || Objects.equals(curr, 'D')) {
                    position check = p.shift(1,0);
                    if (!tiles[check.x][check.y].equals(Tileset.WALL)) {
                        p = p.shift(1,0);
                        tiles[current.x][current.y] = Tileset.FLOOR;
                        tiles[p.x][p.y] = Tileset.AVATAR;

                    }
                }
                ter.renderFrame(tiles);
            }
        }
    }



    /**
     * Method used to keep track of position of the cursor. where the tile is drawn from
     */
    private static class position {
        int x;
        int y;
        position(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public position shift(int dx, int dy) {
            return new position (this.x + dx, this.y + dy);
        }
    }
    /**
     * Method to draw room given starting position
     */
    public static void generateRoom(TETile[][] tiles, position p, TETile tile, int height, int width){
        for (int i = p.x; i < p.x + width; i++) {
            for (int j = p.y; j > p.y - height; j--) {
                tiles[i][j] = tile;
                occupied[i][j] = true;
            }
        }
    }
    public static void fillWithNothing(TETile[][] tiles) {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT + 4; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
    }
    public static void drawRoom(Random RANDOM, TETile[][] tiles) {
        int height = RANDOM.nextInt(5, 10);
        int width = RANDOM.nextInt(4, 9);
        position p = new position(RANDOM.nextInt(1, WIDTH), RANDOM.nextInt(0, HEIGHT-1));
        if (!spaceOccupied(p, width, height) && !outside(p, width, height)) {
            generateRoom(tiles, p, Tileset.FLOOR, height, width);
            position hallwayStart = p.shift(width/2, -height/2);
            centerTracker.add(hallwayStart);
        }
    }
    public static void drawAllRoom(Random RANDOM, TETile[][] tiles) {
        int n = RANDOM.nextInt(30, 40);
        System.out.println(n);
        int count = 0;
        while (count < n) {
            drawRoom(RANDOM, tiles);
            count++;
        }
        drawHallway(tiles, centerTracker, Tileset.FLOOR);
        drawWalls(occupied, tiles, Tileset.WALL);
    }

    public static void drawRow1(position a, position b, TETile[][] tiles, TETile tile) {
        for (int dx = a.x; dx <= b.x ; dx++) {
            tiles[dx][a.y] = tile;
            occupied[dx][a.y] = true;
        }
    }
    public static void drawRow2(position a, position b, TETile[][] tiles, TETile tile) {
        for (int dx = b.x; dx <= a.x; dx++) {
            tiles[dx][b.y] = tile;
            occupied[dx][b.y] = true;
        }
    }
    public static void drawUp1(position a, position b, TETile[][] tiles, TETile tile) {
        for (int dy = a.y; dy <= b.y; dy++) {
            tiles[b.x][dy] = tile;
            occupied[b.x][dy] = true;
        }
    }
    public static void drawUp2(position a, position b, TETile[][] tiles, TETile tile) {
        for (int dy = b.y; dy <= a.y; dy++) {
            tiles[a.x][dy] = tile;
            occupied[a.x][dy] = true;
        }
    }
    public static void drawUp3(position a, position b, TETile[][] tiles, TETile tile) {
        for (int dy = a.y; dy <= b.y; dy++) {
            tiles[a.x][dy] = tile;
            occupied[a.x][dy] = true;
        }
    }
    public static void drawUp4(position a, position b, TETile[][] tiles, TETile tile) {
        for (int dy = b.y; dy <= a.y; dy++) {
            tiles[b.x][dy] = tile;
            occupied[b.x][dy] = true;
        }
    }
    public static void drawHallway(TETile[][] tiles, ArrayList<position> centerTracker, TETile tile) {
        for (int centers = 0; centers < centerTracker.size() - 1; centers++) {
            position a = centerTracker.get(centers);
            position b = centerTracker.get(centers + 1);
            if (a.x < b.x && a.y < b.y) {
                drawRow1(a, b, tiles, tile);
                drawUp1(a, b, tiles, tile);
            }
            if (a.x < b.x && a.y > b.y) {
                drawRow1(a, b, tiles, tile);
                drawUp4(a, b, tiles, tile);
            }
            if (a.x > b.x && a.y < b.y) {
                drawRow2(a, b, tiles, tile);
                drawUp3(a, b, tiles, tile);
            }
            if ( a.x > b.x && a.y > b.y) {
                drawRow2(a, b, tiles, tile);
                drawUp2(a, b, tiles, tile);
            }
        }
    }
    public static void drawWalls(boolean[][] occupied, TETile[][] tiles, TETile tile) {
        for (int x = 0; x < WIDTH; x++) { // should be width - 1
            for (int y = 0; y < HEIGHT; y++) { // should be height - 1
                if (occupied[x][y]) {
                    if (!occupied[x + 1][y]) {
                        tiles[x + 1][y] = tile;
                    }
                    if (!occupied[x + 1][y + 1]) {
                        tiles[x + 1][y + 1] = tile;
                    }
                    if (!occupied[x][y + 1]) {
                        tiles[x][y + 1] = tile;
                    }
                    if (!occupied[x - 1][y + 1]) {
                        tiles[x - 1][y + 1] = tile;
                    }
                    if (!occupied[x - 1][y - 1]) {
                        tiles[x - 1][y - 1] = tile;
                    }
                    if (!occupied[x + 1][y - 1]) {
                        tiles[x + 1][y - 1] = tile;
                    }
                    if (!occupied[x - 1][y]) {
                        tiles[x - 1][y] = tile;
                    }
                    if (!occupied[x][y - 1]) {
                        tiles[x][y - 1] = tile;
                    }
                }
            }
        }
    }
    public static boolean spaceOccupied(position p, int width, int height) {
        for (int i = p.x; i < p.x + width; i++) {
            for (int j = p.y; j >= p.y - height; j--) {
                if (j >= 0 && i < WIDTH && occupied[i][j] == true) {
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean outside(position p, int width, int height) {
        if (p.y - height <= 0 || p.x + width >= WIDTH  ||  p.x + width <= 0 || p.y - height >= HEIGHT) {
            return true;
        }
        return false;
    }
}
