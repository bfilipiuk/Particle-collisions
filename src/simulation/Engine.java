package simulation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Engine  extends JPanel implements Runnable, ActionListener {

    private Thread _thread;

    private final static int PARTICLES_NUMNER = 10;

    private Particle[] particles = new Particle[PARTICLES_NUMNER];
    private int screenWidth;
    private int screenHeight;

    private float mapWidth;
    private float mapHeight;

    public Engine(int w, int h, float mapWidth, float mapHeight)
    {
        screenWidth = w;
        screenHeight = h;
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
        start();
    }

    public synchronized void start() {
        for(int i = 0; i < PARTICLES_NUMNER; i++)
        {
            particles[i] = new Particle(mapWidth, mapHeight);
            while ( spawnedInOtherParticle(particles[i], i) )
            {
                particles[i] = new Particle(mapWidth, mapHeight);
            }
        }
        moveParticlesPreventingBug();
        _thread = new Thread(this);
        _thread.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    private void update(double dt)
    {
        for (Particle p : particles) {
            p.addGravity(dt);
            p.resetList();
        }
        checkCollision(dt);
        for (Particle p : particles) {
            if(checkBorderCollision(p, dt))
            {
                borderCollision(p, dt);
            }
            p.move(dt);
        }
        moveParticlesPreventingBug();
    }

    private boolean spawnedInOtherParticle(Particle p, int number)
    {
        for(int i = 0; i < number - 1; i++) {
            double minDistance = particles[i].getRadius() + p.getRadius();
            Vector2D d = Vector2D.sub( particles[i].getPosition(), p.getPosition());
            if( minDistance - d.getV() > 0 )
            {
                return true;
            }
        }
        return false;
    }

    private void moveParticlesPreventingBug()
    {
        for(int i = 0; i < PARTICLES_NUMNER - 1; i++)
        {
            for(int j = i + 1; j < PARTICLES_NUMNER; j++)
            {
                double minDistance = particles[i].getRadius() + particles[j].getRadius();
                Vector2D d = Vector2D.sub( particles[i].getPosition(), particles[j].getPosition());
                double distance = d.getV();
                double diff = minDistance - distance;
                if( diff > 0 )
                {
                    particles[i].addPosition(Vector2D.multByNumb(d.normalize(), diff));
                }
            }
        }
    }

    private void checkCollision(double time)
    {
        for(int i = 0; i < PARTICLES_NUMNER - 1; i++)
        {
            for(int j = i + 1; j < PARTICLES_NUMNER; j++)
            {
                double minDistance = particles[i].getRadius() + particles[j].getRadius();
                double distance = particles[i].getDistance(particles[j].getX(), particles[j].getY());
                if(distance < minDistance)
                {
                    collisionVelocityChange(particles[i], particles[j], distance, time);
                    particles[i].addCollidingParticle(particles[j]);
                    particles[j].addCollidingParticle(particles[i]);
                }
            }
        }
    }

    private void collisionVelocityChange(Particle p1, Particle p2, double dist, double time)
    {
        double m1 = p1.getMass();
        double m2 = p2.getMass();

        double totalMass =  m1 + m2;

        Vector2D r1Subr2 = Vector2D.sub(p1.getPosition(), p2.getPosition());
        Vector2D v1Subv2 = Vector2D.sub(p1.getVelocity(), p2.getVelocity());
        Vector2D r2Subr1 = Vector2D.sub(p2.getPosition(), p1.getPosition());
        Vector2D v2Subv1 = Vector2D.sub(p2.getVelocity(), p1.getVelocity());

        Vector2D u1 = Vector2D.sub(
            p1.getVelocity(),
            Vector2D.multByNumb( Vector2D.multByNumb( r1Subr2, Vector2D.dot(v1Subv2, r1Subr2) / dist ), 2 * m2 / totalMass )
        );
        Vector2D u2 = Vector2D.sub(
                p2.getVelocity(),
                Vector2D.multByNumb( Vector2D.multByNumb( r2Subr1, Vector2D.dot(v2Subv1, r2Subr1) / dist ), 2 * m1 / totalMass )
        );

        p1.setVelocity(Vector2D.multByNumb(u1, time));
        p2.setVelocity(Vector2D.multByNumb(u2, time));
    }

    private void calculation(Particle p, double alfa, double beta, double v1, double v2)
    {
        double x = Math.sin(alfa) * v1 + Math.sin(beta) * v2;
        double y = Math.cos(alfa) * v1 + Math.cos(beta) * v2;

        p.setVelocity(new Vector2D(x, y));
    }

    private void borderCollision(Particle p, double time)
    {
        double r = p.getRadius();
        double x = p.getX();
        double y = p.getY();
        double vy = p.getVelocity().getY() * time;
        double vx = p.getVelocity().getX() * time;

        if ( (( x - r + vx < 0 ) && p.getVelocity().getX() < 0 ) || (( x + r + vx > mapWidth ) && p.getVelocity().getX() > 0 ) )
        {
            p.getVelocity().flipX();
            for(Particle _p : p.getCurrentColliding())
            {
                _p.getVelocity().flipX();
            }
        }

        if(( y - r + vy < 0 && p.getVelocity().getY() < 0))
        {
            p.getVelocity().flipY();
            for(Particle _p : p.getCurrentColliding())
            {
                _p.getVelocity().flipY();
            }
        }

        if(( y + r + vy > mapHeight && p.getVelocity().getY() > 0))
        {
            p.getVelocity().flipY();
            for(Particle _p : p.getCurrentColliding())
            {
                _p.getVelocity().flipY();
            }
        }
    }

    private boolean checkBorderCollision(Particle p, double time)
    {
        double r = p.getRadius();
        double x = p.getX();
        double y = p.getY();
        double vy = p.getVelocity().getY() * time;
        double vx = p.getVelocity().getX() * time;

        return (x - r + vx  < 0 || x + r + vx > mapWidth || y - r + vy < 0 || y + r + vy > mapHeight);
    }

    public void render() {
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.black);
        g.fillRect(0, 0, screenWidth, screenHeight);

        for (Particle p : particles) {
            p.draw(g, mapHeight, mapWidth, screenHeight, screenWidth);
        }
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        long msTimer = System.currentTimeMillis();
        double nsConvert = 1000000000.0 / 60;
        double deltaT = 0;

        while (true) {
            long now = System.nanoTime();
            deltaT += (now - lastTime) / nsConvert;
            lastTime = now;
            while (deltaT >= 1) {
                update(deltaT / 60);
                deltaT--;
            }
            render();
        }
    }
}
