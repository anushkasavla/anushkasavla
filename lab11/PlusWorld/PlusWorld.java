package PlusWorld;
import org.junit.Test;
import static org.junit.Assert.*;

import byowTools.TileEngine.TERenderer;
import byowTools.TileEngine.TETile;
import byowTools.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of plus shaped regions.
 */
public class PlusWorld {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;
    private static int size;

    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    /**
     * Fills the given 2D array of tiles with blank tiles.
     */

    public static void drawSquare(TETile[][] tiles, position p, TETile tile, int length){
        for (int i = 0; i < length; i++) {
            tiles[p.x+i][p.y]= tile;
        }
    }
    public static void addSquare(TETile[][] tiles, position p, TETile tile, int track, int t) {
        size = t;
        position start = p;
        drawSquare(tiles, start, tile, t);
        if (track < t){
            position next = p.shift(0,-1);
            addSquare(tiles, next, tile, track + 1, t);
        }
    }
    public static void fillWithNothing(TETile[][] tiles) {
        int height = tiles[0].length;
        int width = tiles.length;
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
    }
    private static class position {
        int x;
        int y;
        position(int x, int y) {
            this.x = x;
            this.y =y;
        }
        public position shift(int dx, int dy) {
            return new position (this.x + dx, this.y + dy);
        }
    }

    public static void drawWorld(TETile[][] tiles) {
        fillWithNothing(tiles);
        position p = new position(20, 40);
        addSquare(tiles, p, Tileset.WATER, 0, 10);
        addSquare(tiles, p.shift(+size,  -size), Tileset.WATER, 0, 10);
        addSquare(tiles, p.shift(-size, -size), Tileset.WATER, 0, 10);
        addSquare(tiles, p.shift(0, -size), Tileset.WATER, 0, 10);
        addSquare(tiles, p.shift(0, -size*2), Tileset.WATER, 0, 10);


    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] randomTiles = new TETile[WIDTH][HEIGHT];
        drawWorld(randomTiles);

        ter.renderFrame(randomTiles);
    }

}
