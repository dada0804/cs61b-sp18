public class Planet{
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    public static double G = 6.67e-11;

    public Planet(double xP, double yP, double xV, double yV, double m, String img){
    xxPos = xP;
    yyPos = yP;
    xxVel = xV;
    yyVel = yV;
    mass = m;
    imgFileName = img;}

    public Planet(Planet p){
	xxPos = p.xxPos;
	yyPos = p.yyPos;
	xxVel = p.xxVel;
	yyVel = p.yyVel;
	mass = p.mass;
	imgFileName = p.imgFileName;   
}

    public double calcDistance(Planet p){
	return Math.sqrt((this.xxPos - p.xxPos)*(this.xxPos - p.xxPos) + (this.yyPos - p.yyPos)*(this.yyPos - p.yyPos));
	}

    public double calcForceExertedBy(Planet p){
	
	double dy = p.yyPos - this.yyPos;
	double r_square = calcDistance(p) * calcDistance(p);
	return G * this.mass * p.mass/r_square;}
	

    public double calcForceExertedByX(Planet p){
	double dx = p.xxPos - this.xxPos;
	double F = calcForceExertedBy(p);
	double r = calcDistance(p);
	return F*dx/r;}


    public double calcForceExertedByY(Planet p){
	double dy = p.yyPos - this.yyPos;
	double F = calcForceExertedBy(p);
	double r = calcDistance(p);
	return F*dy/r;}

    public double calcNetForceExertedByX(Planet[] allPlanets){	
	double force = 0;
	for (int i = 0; i < allPlanets.length; i+=1){
	    if (this.equals(allPlanets[i])){
		continue;}
	    force += this.calcForceExertedByX(allPlanets[i]);
	}
	return force;}

    public double calcNetForceExertedByY(Planet[] allPlanets){	
	double force = 0;
	for (Planet p : allPlanets){
	    if (this.equals(p)){
		continue;}
	    force += this.calcForceExertedByY(p);
	}
	return force;}

    public void update(double dt, double fX, double fY){
	double a_x = fX/this.mass;
	double a_y = fY/this.mass;
	this.xxVel = this.xxVel + dt*a_x;
	this.yyVel = this.yyVel + dt*a_y;
	this.xxPos = this.xxPos + dt*this.xxVel;
	this.yyPos = this.yyPos + dt*this.yyVel;}


    public void draw(){
	StdDraw.picture(this.xxPos, this.yyPos, "images/"+imgFileName);}
	



}