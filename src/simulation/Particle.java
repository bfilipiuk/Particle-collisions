package simulation;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.random.RandomGenerator;

public class Particle {
    private Vector2D position;
    private Vector2D velocity;

    Random generator = new Random();

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

        double vx = (generator.nextInt(20) - 40) * 10;
        double vy = (generator.nextInt(20) - 40) * 10;
        velocity = new Vector2D(vx, vy);

        radius = generator.nextInt(30) + 10;
        mass = radius / 20;
    }

    public void draw(Graphics g, double mapHeight, double mapWidth, int screenHeight, int screenWidth)
    {
        g.setColor(Color.red);
        g.fillOval((int)(((position.getX() - radius - 5)/mapWidth) * screenWidth), (int)(screenHeight - ((position.getY() + radius + 15)/mapHeight) * screenHeight),
                (int)((radius*2/mapWidth) * screenWidth), (int)((radius*2/mapHeight) * screenHeight));
    }

    public void addGravity(double time){
        velocity.add(Vector2D.multByNumb(new Vector2D(0, -450.81), time));
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
