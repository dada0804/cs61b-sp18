package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.util.Random;


public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    //why?
    private static final int MAX_ROOMLENGTH = 8;
    private static final int MIN_ROOMLENGTH = 3;

    private static Random RANDOM;
    private static Rooms[] rooms;

    private static int roomNum;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().


        Long seed = -1L;
        if(input.toLowerCase().contains("n") && input.toLowerCase().contains("s")){
            int start = input.toLowerCase().indexOf("n") + 1;
            int end = input.toLowerCase().indexOf("s");
            try {
                seed = Long.parseLong(input.substring(start, end));
            }catch(Exception e){
                throw new RuntimeException("Seed has to be an integer. You entered: \"" + input.substring(start, end) + "\"");
            }
        }

        setSeed(seed);
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] randomWorld = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                randomWorld[x][y] = Tileset.NOTHING;
            }
        }

        initializeRandomRooms(100);
        setRooms(randomWorld);
        ter.renderFrame(randomWorld);
        TETile[][] finalWorldFrame = randomWorld;
        return finalWorldFrame;
    }

    public static void setSeed(Long seed){
        RANDOM = new Random(seed);
    }

    private static boolean collidedWithPreviousRooms(Rooms[] rooms, int i){
        for(int j = 0; j <= i; j++){
            if(rooms[i+1].isCollided(rooms[j])){
                return true;
            }
        }
        return false;
    }

    public static void branchTop(Rooms[] rooms, int i) {
        if (HEIGHT - (rooms[i].y + rooms[i].roomHeight) < MIN_ROOMLENGTH) {
            return;
        }

        //x应该是width上任意一点
        //如果该room的width和最小长度一样，那么x（也就是上面房间的x）就等于这个room的x，否则等于随机数（要把范围传进去，0-该数值-1）
        int x = (rooms[i].roomWidth == MIN_ROOMLENGTH) ?
                rooms[i].x : RANDOM.nextInt(rooms[i].roomWidth - MIN_ROOMLENGTH) + rooms[i].x;
        //y坐标就是在此基础上加上height
        int y = rooms[i].y + rooms[i].roomHeight;
        //新房子的宽度要么是在3-8之间，要么是整个房间宽度-x坐标轴，选取这两个小的（以防超出大房间范围）
        int width = Math.min(RANDOM.nextInt(MAX_ROOMLENGTH - MIN_ROOMLENGTH) + MIN_ROOMLENGTH, WIDTH - x);
        int height = Math.min(RANDOM.nextInt(MAX_ROOMLENGTH - MIN_ROOMLENGTH) + MIN_ROOMLENGTH, HEIGHT - y);

        rooms[i + 1] = new Rooms(x, y, width, height);
        if (collidedWithPreviousRooms(rooms, i)) {
            rooms[i + 1] = null;
        } else {
            //create doors
            int leftBound = rooms[i + 1].x + 1;
            //based on which one is left or right
            int rightBound = Math.min(rooms[i].x + rooms[i].roomWidth - 2, rooms[i + 1].x + rooms[i + 1].roomWidth - 2);

            int doorX0 = (rightBound - leftBound == 0) ? rightBound : RANDOM.nextInt(rightBound - leftBound) + leftBound;
            int doorY0 = y - 1;
            int doorX1 = doorX0;
            int doorY1 = y;

            rooms[i].createDoor(1, doorX0, doorY0);
            rooms[i + 1].createDoor(0, doorX1, doorY1);
        }
    }

    public static  void branchBot(Rooms[] rooms, int i){
        if (rooms[i].y < MIN_ROOMLENGTH){
            return;
        }
        int x = (rooms[i].roomWidth == MIN_ROOMLENGTH) ?
                rooms[i].x : RANDOM.nextInt(rooms[i].roomWidth - MIN_ROOMLENGTH) + rooms[i].x;
        int width = Math.min(RANDOM.nextInt(MAX_ROOMLENGTH - MIN_ROOMLENGTH) + MIN_ROOMLENGTH, WIDTH - x);
        int height = Math.min(RANDOM.nextInt(MAX_ROOMLENGTH - MIN_ROOMLENGTH) + MIN_ROOMLENGTH, rooms[i].y);
        int y = rooms[i].y - height;

        rooms[i + 1] = new Rooms(x, y, width, height);
        if(collidedWithPreviousRooms(rooms, i)){
            rooms[i + 1] = null;
        }else{
            //create doors when branching top.
            int leftBound = rooms[i + 1].x + 1;
            int rightBound = Math.min(rooms[i].x + rooms[i].roomWidth - 2, rooms[i + 1].x + rooms[i + 1].roomWidth - 2);
            //x + rW - 1 is the x pos of the room's right most block, need to use x + rW - 2 because the door can't be at the corner.

            int doorX0 = (rightBound - leftBound == 0) ? rightBound : RANDOM.nextInt(rightBound - leftBound) + leftBound;
            int doorY0 = rooms[i].y;
            int doorX1 = doorX0;
            int doorY1 = rooms[i].y - 1;

            rooms[i].createDoor(1, doorX0, doorY0);
            rooms[i + 1].createDoor(0, doorX1, doorY1);
        }
    }

    public static void branchLeft(Rooms[] rooms, int i){
        if (rooms[i].x < MIN_ROOMLENGTH){
            return;
        }
        int y = (rooms[i].roomHeight == MIN_ROOMLENGTH) ?
                rooms[i].y : RANDOM.nextInt(rooms[i].roomHeight - MIN_ROOMLENGTH) + rooms[i].y;
        int height = (Math.min(RANDOM.nextInt(MAX_ROOMLENGTH - MIN_ROOMLENGTH) + MIN_ROOMLENGTH, HEIGHT - y));
        int width = Math.min(RANDOM.nextInt(MAX_ROOMLENGTH - MIN_ROOMLENGTH) + MIN_ROOMLENGTH, rooms[i].x);
        int x = rooms[i].x - width;

        rooms[i + 1] = new Rooms(x, y, width, height);
        if(collidedWithPreviousRooms(rooms, i)){
            rooms[i + 1] = null;
        }else{
            //create doors for two rooms when branching left.
            int botBound = rooms[i + 1].y + 1;
            int topBound = Math.min(rooms[i].y + rooms[i].roomHeight - 2, rooms[i + 1].y + rooms[i + 1].roomHeight - 2);

            int doorX0 = rooms[i].x;
            int doorY0 = (topBound - botBound == 0) ? topBound : RANDOM.nextInt(topBound - botBound) + botBound;
            int doorX1 = rooms[i].x - 1;
            int doorY1 = doorY0;

            rooms[i].createDoor(1, doorX0, doorY0);
            rooms[i + 1].createDoor(0, doorX1, doorY1);

        }
    }
    public static void branchRight(Rooms[] rooms, int i){
        if (WIDTH - (rooms[i].x + rooms[i].roomWidth) < MIN_ROOMLENGTH){
            return;
        }
        int y = (rooms[i].roomHeight == MIN_ROOMLENGTH) ?
                rooms[i].y : RANDOM.nextInt(rooms[i].roomHeight - MIN_ROOMLENGTH) + rooms[i].y;
        int x = rooms[i].x + rooms[i].roomWidth;
        int height = (Math.min(RANDOM.nextInt(MAX_ROOMLENGTH - MIN_ROOMLENGTH) + MIN_ROOMLENGTH, HEIGHT - y));
        int width = Math.min(RANDOM.nextInt(MAX_ROOMLENGTH - MIN_ROOMLENGTH) + MIN_ROOMLENGTH, WIDTH - x);

        rooms[i + 1] = new Rooms(x, y, width, height);
        if(collidedWithPreviousRooms(rooms, i)){
            rooms[i + 1] = null;
        }else{
            //create doors for two rooms when branching right.
            int botBound = rooms[i + 1].y + 1;
            int topBound = Math.min(rooms[i].y + rooms[i].roomHeight - 2, rooms[i + 1].y + rooms[i + 1].roomHeight - 2);

            int doorX0 = x - 1;
            int doorY0 = (topBound - botBound == 0) ? topBound : RANDOM.nextInt(topBound - botBound) + botBound;
            int doorX1 = x;
            int doorY1 = doorY0;

            rooms[i].createDoor(1, doorX0, doorY0);
            rooms[i + 1].createDoor(0, doorX1, doorY1);
        }
    }

    public static void branchRoom(Rooms[] rooms, int i){
        int trial = 0;
        while (rooms[i + 1] == null){
            switch(RANDOM.nextInt(4)){
                case 0:
                    branchTop(rooms, i);
                    trial++;
                    break;
                case 1:
                    branchBot(rooms, i);
                    trial++;
                    break;
                case 2:
                    branchLeft(rooms, i);
                    trial++;
                    break;
                case 3:
                    branchRight(rooms, i);
                    trial++;
                    break;
                default:
                    break;
            }
            if(trial >= 50){
                roomNum = i;
                System.out.println("Failed to create room[" + Integer.toString(i + 1) + "], total Room number: " + Integer.toString(roomNum));

                break;
            }
        }
    }

    public static void initializeRandomRooms(int n) {
        rooms = new Rooms[n];
        int x = RANDOM.nextInt(WIDTH - MIN_ROOMLENGTH);
        int y = RANDOM.nextInt(HEIGHT - MIN_ROOMLENGTH);
        int roomWidth = Math.min(RANDOM.nextInt(MAX_ROOMLENGTH - MIN_ROOMLENGTH) + MIN_ROOMLENGTH, WIDTH - x);
        int roomHeight = Math.min(RANDOM.nextInt(MAX_ROOMLENGTH - MIN_ROOMLENGTH) + MIN_ROOMLENGTH, HEIGHT - y);
        rooms[0] = new Rooms(x, y, roomWidth, roomHeight);

        //branch
        for (int i = 0; i < n -1; i++){
            branchRoom(rooms, i);
            if (rooms[i+1] == null){
                break;
            }
        }


    }

    public static void setRooms(TETile[][] tiles){
        for(int i = 0; i < rooms.length; i++){
            if(rooms[i] == null){
                break;
            }
            rooms[i].draw(tiles);
        }
    }



}
