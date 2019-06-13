package javapackage;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;

public class MoveFeature extends JPanel {

public void move() {
	private Rectangle2D.Float myRect = new Rectangle2D.Float(50, 50, 50, 50);
	
	  MovingAdapter ma = new MovingAdapter();
	
	  public MoveFeature() {
	    addMouseMotionListener(ma);
	    addMouseListener(ma);
	  }
	
	
	  class MovingAdapter extends MouseAdapter {
	
	    private int x;
	
	    private int y;
	
	    public void mousePressed(MouseEvent e) {
	      x = e.getX();
	      y = e.getY();
	    }
	
	    public void mouseDragged(MouseEvent e) {
	
	      int dx = e.getX() - x;
	      int dy = e.getY() - y;
	
	      if (myRect.getBounds2D().contains(x, y)) {
	        myRect.x += dx;
	        myRect.y += dy;
	        repaint();
	      }
	      x += dx;
	      y += dy;
	    }
	  }
	
}