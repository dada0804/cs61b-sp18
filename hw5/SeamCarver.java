import java.awt.Color;
import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    private Picture pic;
    private int width; //column
    private int height; //row
    private double[][] energy;

    public SeamCarver(Picture picture){
        pic = new Picture(picture);
        width = pic.width();
        height = pic.height();
        energy = new double[width][height];
        cal_energy();
    }

    public Picture picture(){
        return pic;
    }

    public int width(){
        return width;
    }

    public int height(){
        return height;
    }


    public double energy(int x, int y){

        if (x > width -1 || x < 0 || y > height - 1 || y < 0){
            throw new IndexOutOfBoundsException();
        }
        int left = x - 1;
        int right = x + 1;
        int up = y - 1;
        int down = y + 1;
        if (left == -1){
            left = width -1;
        }
        if (right == width ){
            right = 0;
        }
        //最好不要复制粘贴！！很容易忘记需要改的地方的！！！
        if (up == -1){
            up = height - 1;
        }
        if (down == height){
            down = 0;
        }

        Color lc = pic.get(left, y);
        Color rc = pic.get(right, y);
        Color uc = pic.get(x, up);
        Color dc = pic.get(x, down);

        int X_dir = (lc.getRed() - rc.getRed())*(lc.getRed() - rc.getRed())
                    + (lc.getBlue() - rc.getBlue())*(lc.getBlue() - rc.getBlue())
                    + (lc.getGreen() - rc.getGreen())*(lc.getGreen() - rc.getGreen());
        int Y_dir = (uc.getRed() - dc.getRed())*(uc.getRed() - dc.getRed())
                + (uc.getBlue() - dc.getBlue())*(uc.getBlue() - dc.getBlue())
                + (uc.getGreen() - dc.getGreen())*(uc.getGreen() - dc.getGreen());

        return X_dir + Y_dir;
    }

    private void cal_energy(){
        for(int x = 0; x < width; x++){
            for (int y = 0; y < height; y++){
                energy[x][y] = energy(x, y);
            }
        }
    }

    public int[] findHorizontalSeam(){
        //transpose
        Picture pic_copy = new Picture( pic);
        int new_w = height;
        int new_h = width;
        pic = new Picture(new_w, new_h);
        double[][] energy_copy = new double[new_w][new_h];
        for (int x = 0; x < new_w; x++) {
            for (int y = 0; y < new_h; y++) {
                energy_copy[x][y] = energy[y][new_w-1-x];
            }
        }

        width = new_w;
        height = new_h;
        energy = energy_copy;

        //use vertical seam
        int[] path = findVerticalSeam();
        for (int i = 0; i < path.length; i++){
            path[i] = new_w - 1 -path[i];
        }


        //transpose back
        pic = new Picture( pic_copy);
        height = pic_copy.height();
        width = pic_copy.width();
        energy = new double[width][height];
        cal_energy();

        return path;

    }


    public int[] findVerticalSeam(){
        // calcutate min energy cost of each pixel
        double[][] min_energy = new double[width][height];
        for (int y = 0; y < height ; y ++){
            for ( int x = 0; x < width ; x ++){
                if ( y == 0){
                    min_energy[x][y] = energy[x][y];
                    continue;
                }
                int left = x - 1;
                int right = x + 1;
                if (left < 0){ left = 0;}
                if (right > width - 1) { right = width - 1;}
                min_energy[x][y] = energy[x][y] + Math.min(Math.min(min_energy[left][y-1], min_energy[right][y-1]), min_energy[x][y-1]);
            }
        }

        // find the minimum energy cost
        double min = Integer.MAX_VALUE;
        int min_index = 0;
        for (int x = 0; x < width; x ++){
            if (min_energy[x][height - 1] < min ){
                min = min_energy[x][height - 1];
            }
        }
        for (int x = 0; x < width; x ++){
            if (min_energy[x][height - 1] == min ){
                min_index = x;
            }
        }

        int[] path = new int[height];
        path[height - 1] = min_index;
//        System.out.println(min_index);

        // find the route
        for (int y = height - 1; y > 0; y --){
            int left = min_index - 1;
            int right = min_index + 1;
            if (left < 0){ left = 0;}
            if (right > width - 1) { right = width - 1;}
            double top_min = Math.min(Math.min(min_energy[left][y-1], min_energy[right][y-1]), min_energy[min_index][y-1]);
            if (min_energy[left][y-1] == top_min){
                min_index = left;
            }
            else if (min_energy[right][y-1] == top_min){
                min_index = right;
            }
            path[y - 1] = min_index;
        }

        for (int i = 0; i < path.length; i ++){
            System.out.println(path[i]);
        }
        return path;
    }

    public void removeHorizontalSeam(int[] seam){
        if (seam.length != width ){
            throw new java.lang.IllegalArgumentException();
        }
        for ( int i = 0; i < seam.length - 1; i++){
            if (Math.abs(seam[i] - seam[i+1]) > 1){
                throw new java.lang.IllegalArgumentException();
            }
        }
        removeHorizontalSeam(seam);

    }

    public void removeVerticalSeam(int[] seam){
        if (seam.length != height ){
            throw new java.lang.IllegalArgumentException();
        }
        for ( int i = 0; i < seam.length - 1; i++){
            if (Math.abs(seam[i] - seam[i+1]) > 1){
                throw new java.lang.IllegalArgumentException();
            }
        }
        removeHorizontalSeam(seam);

    }



}
