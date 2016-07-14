/*
 * Game.java
 *
 * Created on March 16, 2005, 12:13 PM
 */

package tetris;

import java.util.Random;
import java.io.*;
/**
 *
 * @author Menachem
 */
public class Game implements java.awt.event.ActionListener {

    public static final int bWidth = 10;
    public static final int bHeight = 20;
    
    int[][] blocks;
    Random rand = new Random();

    Display display;

    public javax.swing.Timer timer;
    static int delay = 500;

    int shapeNum;
    Shape shape;
    Point zbPos;
    int nextShapeNum;
    int nextShapePos;
    
    int numLines;
    int score;
    int level;
    int neededLines;
    
    boolean paused;
    int [][] pausedBlocks;
    boolean gameOver;
    
    public final static String scoreFileName = "c:\\Menachem\\Java\\Tetris\\scores.dat";
    public final static int numScores = 5;
    
    /** Creates a new instance of Game */
    public Game(Display _display) {
        timer = new javax.swing.Timer(delay, this);
        display = _display;
    }
    
    public void newGame() {
        blocks = new int[bWidth][bHeight];
        
        shape = null;
        shapeNum = 0;
        nextShapeNum = rand.nextInt(7) + 1;
        nextShapePos = rand.nextInt(4);
        
        level = 0;
        numLines = 0;
        score = 0;
        neededLines = 10;
        delay = 500;
        timer.setDelay(delay);
        
        paused = false;
        gameOver = false;
        
        display.updateScores();
        nextShape();
        timer.start();
    }

    public void nextShape() {
        
        shapeNum = nextShapeNum;
        shape = Shape.shapes[shapeNum];

        zbPos = new Point((bWidth - 4) / 2, bHeight - 4);
        
        for (int i = 0; i < nextShapePos; i++) {
            shape = shape.RotateClockWise();
        }

        nextShapeNum = rand.nextInt(7) + 1;
        nextShapePos = rand.nextInt(4);

        for (int i = 0; i < 4; i++)
        {
            if (blocks[shape.blocks[i].x + zbPos.x][shape.blocks[i].y + zbPos.y] != 0){
                gameOver();
                break;
            }
        }
            
        showShape();
        display.update();
    }
    
    public void hideShape() {
        for (int i = 0; i < 4; i++)
        {
            blocks[shape.blocks[i].x + zbPos.x][shape.blocks[i].y + zbPos.y] = 0;
        }
    }
    public void showShape() {
        for (int i = 0; i < 4; i++)
            {
                blocks[shape.blocks[i].x + zbPos.x][shape.blocks[i].y + zbPos.y] = shapeNum;
            }
    }

    public void moveDown() {
        if (paused || gameOver) return;
        
        boolean allowed = true;
        
        hideShape();
        
        for (int i = 0; i < 4; i++)
        {
            if (shape.blocks[i].y + zbPos.y - 1 < 0 ||
                    blocks[shape.blocks[i].x + zbPos.x][shape.blocks[i].y + zbPos.y - 1] != 0) {
                allowed = false;
            }
        }
        if (allowed)
            zbPos.y -= 1;

        showShape();
        
        display.update();
        
        if(!allowed) Tetrisize();
            
    }
            

    public void moveLeft() {
        if (paused || gameOver) return;

        boolean allowed = true;
        
        hideShape();

        for (int i = 0; i < 4; i++)
        {
            if (shape.blocks[i].x + zbPos.x - 1 < 0 ||
                    blocks[shape.blocks[i].x + zbPos.x - 1][shape.blocks[i].y + zbPos.y] != 0) {
                allowed = false;
            }
        }
        if (allowed)
            zbPos.x -= 1;
        
        showShape();
        
        display.update();
        
    }

    public void moveRight() {
        if (paused || gameOver) return;

        boolean allowed = true;
        
        hideShape();

        for (int i = 0; i < 4; i++)
        {
            if (shape.blocks[i].x + zbPos.x + 1 > bWidth - 1||
                    blocks[shape.blocks[i].x + zbPos.x + 1][shape.blocks[i].y + zbPos.y] != 0) {
                allowed = false;
            }
        }
        if (allowed)
            zbPos.x += 1;
        
        showShape();
        
        display.update();
    }

    public void RotateClockWise() {
        if (paused || gameOver) return;

        boolean allowed = true;
        
        hideShape();

        Shape newShape = shape.RotateClockWise();
        for (int i = 0; i < 4; i++)
        {
            if (newShape.blocks[i].x + zbPos.x < 0 ||
                    newShape.blocks[i].x + zbPos.x > bWidth -1 ||
                    newShape.blocks[i].y + zbPos.y < 0 ||
                    blocks[newShape.blocks[i].x + zbPos.x][newShape.blocks[i].y + zbPos.y] != 0)
                allowed = false;
                    
        }
        
        if (allowed)
            shape = newShape;
        
        showShape();
        
        display.update();
    }

    public void RotateCounterClockWise() {
        if (paused || gameOver) return;

        boolean allowed = true;

        hideShape();
        
        Shape newShape = shape.RotateCounterClockWise();
        for (int i = 0; i < 4; i++)
        {
            if (newShape.blocks[i].x + zbPos.x < 0 ||
                    newShape.blocks[i].x + zbPos.x > bWidth -1 ||
                    newShape.blocks[i].y + zbPos.y < 0 ||
                    blocks[newShape.blocks[i].x + zbPos.x][newShape.blocks[i].y + zbPos.y] != 0)
                allowed = false;
                    
        }
        
        if (allowed)
            shape = newShape;
        
        showShape();

        display.update();
    }
    public void Tetrisize() {
        int n = 0;
        int[] lines = new int[4];
        int t;
        
        for (int i = 0; i < bHeight; i++) {
            t = 0;
            for (int j = 0; j < bWidth; j++) {
                if (blocks[j][i] != 0)
                    t++;
            }
            if (t == bWidth) {
                lines[n++] = i;
                for (int j = 0; j < bWidth; j++) {
                    blocks[j][i] = 8;
                }
            }
        }
        

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < bWidth; j++) {
                blocks[j][lines[i]] = 0;
            }
        }
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < bWidth; j++) {
                for (int k = lines[i] - i; k < bHeight - 1; k++) {
                    blocks[j][k] = blocks[j][k + 1];
                }
            }
        }
        numLines += n;
        neededLines -= n;
        if (neededLines <= 0) {
            level += 1;
            neededLines += (level + 1)* 10;
            delay -= (level < 9) ? 50 : 10;
            timer.setDelay(delay);
        }
        
        if (n == 1)
            score += 50 * (level + 1);  
        else if (n == 2)
            score += 150 * (level + 1);
        else if (n == 3)
            score += 250 * (level + 1);
        else if (n == 4)
            score += 400 * (level + 1);
        
        display.update();
        nextShape();
    }
    
    public void gameOver() {
        gameOver = true;
        String[][] scores = getHighScores();
        if (score > Integer.parseInt(scores[numScores - 1][1]))
        {
            addHighScore(display.getScorerName(), score);
            display.updateScores();
        }
    }
    public void pause() {
        paused = true;
        pausedBlocks = blocks;
        blocks = new int[bWidth][bHeight];
        display.update();
    }
    public void unpause() {
        paused = false;
        blocks = pausedBlocks;
        display.update();
    }
    public boolean isPaused() {
        return paused;
    }
    
    public void actionPerformed(java.awt.event.ActionEvent e) {
        moveDown();
    }
    public String[][] getHighScores() {
        String[][] scores = new String[numScores][2];
        try {
            BufferedReader in = new BufferedReader(new FileReader(scoreFileName));
            for (int i = 0; i < numScores; i++) {
                for (int j = 0; j < 2; j++) {
                    scores[i][j] = in.readLine();
                }
            }
            in.close();
        }
        catch (IOException e) {
// Handle Exception            
        }
        return scores;
    }
    
    public void addHighScore(String name, int score) {
        String[][] scores = getHighScores();
        for (int i = 0; i < numScores; i++) {
            if (score > Integer.parseInt(scores[i][1])) {
                for (int j = numScores - 1; j > i; j--) {
                    scores[j] = scores[j - 1];
                }
                scores[i] = new String[2];
                scores[i][0] = name;
                scores[i][1] = Integer.toString(score);
                setHighScores(scores);
                return;
            }
        }
    }
    public void setHighScores(String[][] scores) {
        try {
            File f = new File(scoreFileName);
            PrintWriter out = new PrintWriter(new FileWriter(f));
            for (int i = 0; i < numScores; i++) {
                for (int j = 0; j < 2; j++) {
                    out.println(scores[i][j]);
                }
            }
            out.close();
        }
        catch (IOException e) {}
    }
}

