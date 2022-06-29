package simulation;

public class Vector2D {
    private double x;
    private double y;

    Vector2D(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public double getY() {
        return y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Vector2D add(double x, double y)
    {
        this.x += x;
        this.y += y;
        return this;
    }

    public void add(Vector2D v)
    {
        this.x += v.getX();
        this.y += v.getY();
    }

    public void addX(double x)
    {
        this.x += x;
    }

    public void addY(double y)
    {
        this.x += y;
    }

    public void flipX(){ this.x *= -1; };
    public void flipY(){ this.y *= -1; };

    public double getV(){return Math.sqrt(x*x + y*y);}
    public double getAngle()
    {
        return Math.atan2(y, x);
    }

    public static double angleBetweenVectors(Vector2D v1, Vector2D v2)
    {
        double dy = v1.getY() - v2.getY();
        double dx = v1.getX() - v2.getX();

        double rad = Math.atan2(dy, dx);
        return rad * (180 / Math.PI);
    }

    public static Vector2D add(Vector2D v1, Vector2D v2)
    {
        return new Vector2D(v1.x + v2.x, v1.y + v2.y);
    }
    public static Vector2D sub(Vector2D v1, Vector2D v2)
    {
        return new Vector2D(v1.x - v2.x, v1.y - v2.y);
    }
    public static Vector2D divByNumb(Vector2D v1, double n)
    {
        return new Vector2D(v1.x / n, v1.y / n);
    }
    public static Vector2D multByNumb(Vector2D v1, double n)
    {
        return new Vector2D(v1.x * n, v1.y * n);
    }
    public static double dot(Vector2D v1, Vector2D v2)
    {
        return v1.x * v2.x + v1.y * v2.y;
    }

    public Vector2D normalize()
    {
        return new Vector2D(x/getV(), y/getV());
    }

}
