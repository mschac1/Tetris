/*
 * Point.java
 *
 * Created on March 16, 2005, 12:37 PM
 */

package tetris;

/**
 *
 * @author Menachem
 */
public class Point {
    
    int x;
    
    int y;

    /** Creates a new instance of Point */
    public Point() {
        x = 0;
        y = 0;
    }
    public Point(int _x, int _y) {
        x = _x;
        y = _y; 
    }
    
    public boolean Equals(Point p) {
        return (x == p.x && y == p.y);
    }
    
    public static Point Add(Point p1, Point p2) {
        Point p = new Point();
        p.x = p1.x + p2.x;
        p.y = p1.y + p2.y;
        return p;
    }
    public void Add(Point p)
    {
        this.x += p.x;
        this.y += p.y;
    }
    
}
