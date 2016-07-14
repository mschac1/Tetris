/*
 * Shape.java
 *
 * Created on March 16, 2005, 2:25 PM
 */

package tetris;

/**
 *
 * @author Menachem
 */
public class Shape {

    Point[] blocks;
    
    static Shape Square = new Shape(new Point(1,1), new Point(1, 2),
            new Point(2, 1), new Point(2, 2));

    static Shape Line = new Shape(new Point(2,0), new Point(2, 1),
            new Point(2, 2), new Point(2, 3));

    static Shape El = new Shape(new Point(1,3), new Point(1, 2),
            new Point(1, 1), new Point(2, 1));

    static Shape REl = new Shape(new Point(2,3), new Point(2, 2),
            new Point(1, 1), new Point(2, 1));

    static Shape Tee = new Shape(new Point(1,2), new Point(2, 2),
        new Point(3, 2), new Point(2, 1));

    static Shape Zee = new Shape(new Point(1,2), new Point(2, 2),
        new Point(3, 1), new Point(2, 1));

    static Shape Ess = new Shape(new Point(3,2), new Point(2, 2),
        new Point(1, 1), new Point(2, 1));
    
    final static Shape[] shapes = {null, Square, Line, El, REl, Tee, Zee, Ess};
    
    /** Creates a new instance of Shape */
    private Shape(Point... _blocks) {
        if (_blocks.length != 4)
            ;/* This should not happen */
        blocks = _blocks;
    }
    private Shape() {    
        blocks = new Point[4];
        for (int i = 0; i < 4; i++)
            blocks[i] = new Point();
    }
    
    public Shape Clone() {
        Shape s;
        Point[] ps = new Point[4];
        
        for (int i = 0; i < 4; i++)
        {
            ps[i] = new Point();
            ps[i].x = blocks[i].x;
            ps[i].y = blocks[i].y;
        }
        s = new Shape(ps);
        return s;
    }
    
    public Shape RotateCounterClockWise() {
        Shape newShape = new Shape();
        
        for (int i = 0; i < 4; i++)
        {
            newShape.blocks[i] = new Point(3 - blocks[i].y, blocks[i].x);
        }
        return newShape;
        
    }

    public Shape RotateClockWise() {
        Shape newShape = new Shape();
        
        for (int i = 0; i < 4; i++)
        {
            newShape.blocks[i] = new Point(blocks[i].y, 3 - blocks[i].x);
        }
        return newShape;
        
    }
                

    
}

