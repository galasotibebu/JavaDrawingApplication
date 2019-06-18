package javapackage;

import java.awt.Color;
import java.awt.geom.Ellipse2D;

// stores point coordinates and provides methods for
// creating drawable point geometries 
// ShapesParent

/**
 * This class is the base for drawing point geometries on the canvas.
 * It contains methods for creating a point feature of predefined size and colour at a particular location
 * defined by coordinates. It inherits from the ShapesParent class
 * 
 * @author A Mashoko
 * 
 */
public class PointFeature extends ShapesParent {

	// Point class instance variables
	public double x = 0;
	public double y = 0;
	private final int pointsize = 5;
	private final Color ptcolor = new Color(0, 0, 0, 0.75f);



	
	/**
	 * The purpose of the constructor is to define the geometry type, which is Point
	 * @author A Mashoko
	 */
	public PointFeature() {
		super("Point");
	}

	
	/**The method creates and returns an point as an Ellipse2D object
	 * 
	 * @return point The point object created
	 */
	public Ellipse2D createPointFeature() {
		Ellipse2D point = new Ellipse2D.Double(this.x, this.y, pointsize, pointsize);
		return point;
	}

	
	/**This method returns the color of created point
	 * 
	 * @return ptcolor The color given to the point is the return value
	 * @author A Mashoko
	 * 
	 */
	public Color getcolor() {
		return ptcolor;
	}


	
	/**
	 * This method sets the coordinates of a point feature 
	 * 
	 * 
	 * @param x the x coordinate for the point feature
	 * @param y the y coordinate for the point feature
	 * @return   the x,y coordinate pair of the point feature
	 * @author A Mashoko
	 * 
	 */
	public boolean setPoint(double x, double y) {
		this.x = x;
		this.y = y;
		return true;
	}
	
	public double getXcoor() {
		return this.x;
	}
	
	public double getYcoor() {
		return this.y;
	}


	
	/** Returns the x,y coordinates of a point as a string
	 * 
	 * @return textGeometry The coordinates as a String
	 * 
	 * 
	 */
	public String getGeometryAsText() {
		String textGeometry = String.valueOf(this.x) + " " + String.valueOf(this.y);
		return textGeometry;
	}


	
	/**The method gets the x and y coordinates  of a point from CSV
	 * 
	 * @param csvGeometry coordinates of point as a string in csv 
	 * @return true if coordinates were extracted from csv successfully
	 * @author A Mashoko
	 * 
	 */
	public boolean setGeometryFromCSV(String csvGeometry) {
		try {
			String[] coordinatesPoint = csvGeometry.split(" ");
			this.x = Double.parseDouble(coordinatesPoint[0]);
			this.y = Double.parseDouble(coordinatesPoint[1]);
			return true;
		} catch (NumberFormatException e) {
			System.err.println("Parsing Error");
			return false;
		}
	}

}
