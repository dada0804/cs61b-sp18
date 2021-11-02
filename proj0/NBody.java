public class NBody{
    public static double readRadius(String filename){
	In in = new In(filename);
	int x = in.readInt();
	return in.readDouble();}

    public static Planet[] readPlanets(String filename){
	In in = new In(filename);
	int index = in.readInt();
	double r = in.readDouble();
	Planet[] allplanets = new Planet[index];
	for (int i = 0; i < index; i += 1){
		allplanets[i] = new Planet(in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readString());}
			
	return allplanets;}

    
    public static void main(String[] args) {
	double T = Double.parseDouble(args[0]);
	double dt = Double.parseDouble(args[1]);
	String filename = args[2];
	Planet[] allplanets = NBody.readPlanets(filename);
	double radius = NBody.readRadius(filename);

	/* draw the background */
	StdDraw.setScale(-radius, radius);
	StdDraw.clear();
	StdDraw.picture(0,0,"images/starfield.jpg");	

	for (Planet p : allplanets){
	    p.draw();}
	

	StdDraw.enableDoubleBuffering();
	
	int num = allplanets.length;
	for (int t = 0; t <=T; t += dt){
	    double[] xForces = new double[num];
	    double[] yForces = new double[num];
	    for (int i = 0; i < num; i += 1){
	        xForces[i] = allplanets[i].calcNetForceExertedByX(allplanets);
		yForces[i] = allplanets[i].calcNetForceExertedByY(allplanets);}
	    for (int j = 0; j < num; j += 1){
		allplanets[j].update(dt, xForces[j], yForces[j]);}
	    StdDraw.picture(0,0,"images/starfield.jpg");
            for (Planet p : allplanets){
	        p.draw();}
	    StdDraw.show();
	    StdDraw.pause(10);}
		
	StdOut.printf("%d\n", num);
	StdOut.printf("%.2e\n", radius);
	for (Planet p : allplanets) {
    	    StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                  p.xxPos, p.yyPos, p.xxVel,
                  p.yyVel, p.mass, p.imgFileName);}

		
}		    
	    

}



	