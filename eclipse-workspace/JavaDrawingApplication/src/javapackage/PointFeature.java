package javapackage;

import java.awt.Color;
import java.awt.geom.Ellipse2D;

// stores point coordinates and provides methods for
// creating drawable point geometries 
// ShapesParent
public class PointFeature extends ShapesParent {

	// Point class instance variables
	public double x = 0;
	public double y = 0;
	private final int pointsize = 5;
	private final Color ptcolor = new Color(0, 0, 0, 0.75f);

	// private Color ptcolorstart = new Color (0, 255f, 0.75f);
	// private Color ptcolorend = new Color (255f, 0, 0.75f);

	// constructor defines the type of geometry see: constructor of ShapeParent
	public PointFeature() {
		super("Point");
	}

	
	/**
	 * 
	 * @return point
	 */
	public Ellipse2D createPointFeature() {// Creates a drawable Ellipse2D object of PointFeature object// returns point geometry
		Ellipse2D point = new Ellipse2D.Double(this.x, this.y, pointsize, pointsize);
		return point;
	}

	public Color getcolor() {
		return ptcolor;
	}

	// Setter for x- and y-coordinate of PointFeature
	// returns true when coordinates set successfully
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

	//Returns the coordinates of the ToolPoint object as a String
	public String getGeometryAsText() {
		String textGeometry = String.valueOf(this.x) + " " + String.valueOf(this.y);
		return textGeometry;
	}

	// Sets the coordinates from a String (provided by database or '.csv').
	// Returns true when the operation is successful
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
