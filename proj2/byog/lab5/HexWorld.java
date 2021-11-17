package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;

    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);


    public static void drawWorld(TETile[][] world){
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    private static class Position{
        int xpos;
        int ypos;
        private Position(int x, int y){
            xpos = x;
            ypos = y;
        }
    }

    public static Position addoneHexagon(TETile[][] world, int s, Position p, TETile t){
        //bottom up
        for (int y = 0; y < s; y += 1){
            for (int x = p.xpos - y; x< p.xpos+s-1+1*y+1; x+=1){
                world[x][p.ypos+y] = t;
            }
        }

        //change position to middle
        p.xpos = p.xpos - ( s - 1);
        p.ypos = p.ypos + s;

        //top down
        for (int y = 0; y < s; y += 1){
            for (int x = p.xpos+y; x < p.xpos+s-1+2*(s-1)-y+1; x+=1){
                world[x][y+ p.ypos] = t;
            }
        }
        //next's left bottom
        p.xpos = p.xpos + (s-1);
        p.ypos = p.ypos + s;
        // test
        // world[p.xpos][p.ypos]=Tileset.FLOWER;
        return p;

    }

    public static void addHexagon(TETile[][] world, Position p, int s){
        // initial position for vertical set
        p.xpos = s;
        p.ypos = 2*s + 1;
        for (int x = 0; x < 3; x++){
            p = addoneHexagon(world, s, p, randomTile() );
        }
        p.xpos = s+s+s-1;
        p.ypos = s + 1;
        for (int x = 0; x < 4; x++){
            p = addoneHexagon(world, s, p, randomTile() );
        }

        p.xpos = s+2*(s+s-1);
        p.ypos = 1;
        for (int x = 0; x < 5; x++){
            p = addoneHexagon(world, s, p, randomTile() );
        }

        p.xpos = s+3*(s+s-1);
        p.ypos = s + 1;
        for (int x = 0; x < 4; x++){
            p = addoneHexagon(world, s, p, randomTile() );
        }

        p.xpos = s+4*(s+s-1);
        p.ypos = 2*s + 1;
        for (int x = 0; x < 3; x++){
            p = addoneHexagon(world, s, p, randomTile() );
        }


    }

    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(5);
        switch (tileNum) {
            case 0:
                return Tileset.SAND;
            case 1:
                return Tileset.FLOWER;
            case 2:
                return Tileset.GRASS;
            case 3:
                return Tileset.MOUNTAIN;
            case 4:
                return Tileset.TREE;
            default:
                return Tileset.NOTHING;
        }
    }

    public static void main(String[] args) {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = new TETile[WIDTH][HEIGHT];
        Position position = new Position(4,0);
        drawWorld(world);
//        addoneHexagon(world, 5, position);
        addHexagon(world, position, 3);

        ter.renderFrame(world);
    }
}

