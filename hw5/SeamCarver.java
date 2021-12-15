import java.awt.Color;
import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    private Picture pic;
    private int width;
    private int height;
    private double[][] energy;

    public SeamCarver(Picture picture){
        pic = picture;
        width = picture.width();
        height = picture.height();

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
        int l_rgb = pic.getRGB(left, y);
        int r_rgb = pic.getRGB(right, y);
        int u_rgb = pic.getRGB(x, up);
        int d_rgb = pic.getRGB(x, down);

        Color lc = new Color(l_rgb);
        Color rc = new Color(r_rgb);
        Color uc = new Color(u_rgb);
        Color dc = new Color(d_rgb);

        int X_dir = (lc.getRed() - rc.getRed())*(lc.getRed() - rc.getRed())
                    + (lc.getBlue() - rc.getBlue())*(lc.getBlue() - rc.getBlue())
                    + (lc.getGreen() - rc.getGreen())*(lc.getGreen() - rc.getGreen());
        int Y_dir = (uc.getRed() - dc.getRed())*(uc.getRed() - dc.getRed())
                + (uc.getBlue() - dc.getBlue())*(uc.getBlue() - dc.getBlue())
                + (uc.getGreen() - dc.getGreen())*(uc.getGreen() - dc.getGreen());

        return X_dir + Y_dir;
    }

    public int[] findHorizontalSeam(){
        //transpose
        Picture pic_copy = pic;
        int new_w = height;
        int new_h = width;
        pic = new Picture(new_w, new_h);

        for (int x = 0; x < width ; x ++){
            for ( int y = 0; y < height ; y ++){
                Color color = pic_copy.get(x, y);
                pic.set(height -1-y, x, color);
            }
        }

        width = new_w;
        height = new_h;

        //use vertical seam


        int[] path = findVerticalSeam();

        //transpose back


        int ori_w = height;
        int ori_h = width;

        pic = new Picture(ori_w, ori_h);
        for (int x = 0; x < pic_copy.width() ; x ++){
            for ( int y = 0; y < pic_copy.height() ; y ++){
                Color color = pic_copy.get(x, y);
                pic.set(x,y, color);
            }
        }
        height = pic_copy.height();
        width = pic_copy.width();


        return path;

    }

    public int[] findVerticalSeam(){
        energy = new double[width][height];
        // calcutate min energy cost of each pixel
        for (int y = 0; y < height ; y ++){
            for ( int x = 0; x < width ; x ++){
                if ( y == 0){
                    energy[x][0] = energy(x, 0);
                }
                else{
                    int left = x - 1;
                    int right = x + 1;
                    if (left < 0){ left = 0;}
                    if (right > width - 1) { right = width - 1;}
                    energy[x][y] = energy(x, y) + Math.min(Math.min(energy[left][y-1], energy[right][y-1]), energy[x][y-1]);
                }
            }
        }

        // find the minimum energy cost
        double min = Integer.MAX_VALUE;
        int min_index = 0;
        for (int x = 0; x < width; x ++){
            if (energy[x][height - 1] < min ){
                min = energy[x][height - 1];
            }
        }
        for (int x = 0; x < width; x ++){
            if (energy[x][height - 1] == min ){
                min_index = x;
            }
        }

        int[] path = new int[height];
        path[height - 1] = min_index;
        System.out.println(min_index);

        // find the route
        for (int y = height - 1; y > 0; y --){
            int left = min_index - 1;
            int right = min_index + 1;
            double top_min = Math.min(Math.min(energy[left][y-1], energy[right][y-1]), energy[min_index][y-1]);
            if (energy[left][y-1] == top_min){
                min_index = left;
            }
            else if (energy[right][y-1] == top_min){
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
