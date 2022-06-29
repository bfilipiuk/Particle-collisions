package simulation;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Particle {
    private Vector2D position;
    private Vector2D velocity;

    //private int SCREENHEIGHT = 500;
    private double radius;
    private double mass;

    private List<Particle> currentColliding = new ArrayList<Particle>();

    public void addCollidingParticle(Particle p)
    {
        currentColliding.add(p);
    }

    public List<Particle> getCurrentColliding() {
        return currentColliding;
    }

    public void resetList()
    {
        currentColliding = new ArrayList<Particle>();
    }

    public void addPosition(Vector2D v)
    {
        position.add(v.getX(), v.getY());
    }

    public Particle()
    {
        double x = 10;
        double y = 50;
        position = new Vector2D(x, y);

    }

    public Particle(double width, double height)
    {
        double x = Math.random() * width;
        double y = Math.random() * height;
        position = new Vector2D(x, y);
        velocity = new Vector2D(0, 0);
        //Losowa prędkość początkowa
        double vx = Math.random() * 10;
        double vy = Math.random() * 10;
        velocity = new Vector2D(vx, vy);

        //promień kuli można dodać losowość
        radius = 20;
        //masa kuli można dodać losowość
        mass = 1;
    }

    public void draw(Graphics g, double mapHeight, double mapWidth, int screenHeight, int screenWidth)
    {
        g.setColor(Color.red);
        g.fillOval((int)((position.getX()/mapWidth) * screenWidth), (int)(screenHeight - (position.getY()/mapHeight) * screenHeight),
                (int)((radius*2/mapWidth) * screenWidth), (int)((radius*2/mapHeight) * screenHeight));
    }

    public void addGravity(double time){
        velocity.add(Vector2D.multByNumb(new Vector2D(0, -9.81), time));
    }

    public void move(double time)
    {
        position.add(velocity.getX() * time, velocity.getY() * time);
    }

    public double getDistance(double x, double y)
    {
        return Math.sqrt((position.getX() - x) * (position.getX() - x) + (position.getY() - y) * (position.getY() - y));
    }


    public Vector2D getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2D velocity) {
        this.velocity = velocity;
    }

    public double getX() {
        return position.getX();
    }

    public double getY() {
        return position.getY();
    }

    public double getMass(){return mass; }

    public void setY(float y) { position.setY(y); }

    public void setX(float x) { position.setX(x); }

    public double getRadius() { return radius; }

    public Vector2D getPosition() {
        return position;
    }
}
