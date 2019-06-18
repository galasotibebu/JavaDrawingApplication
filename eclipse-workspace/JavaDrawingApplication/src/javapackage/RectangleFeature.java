package javapackage;
import java.awt.geom.Rectangle2D;
/**
 * This class is the base for drawing rectangles  on the canvas.
 * It contains methods for creating a rectangle feature based on coordinates of rectangle 
 * corners. It inherits from the ShapesParent class
 * 
 * @author A Mashoko
 * 
 */

public class RectangleFeature extends ShapesParent {

	// Stores the first and the last PointFeature of a RectangleFeature object

	public PointFeature[] rectangleElements = new PointFeature[2];

	
	/**
	 * The purpose of the constructor is to define the geometry type, which is Rectangle
	 * @author A Mashoko
	 */
	public RectangleFeature() {
		super("Rectangle");
	}

	/**
	 * sets the first specified corner as an element of the rectangleElements array
	 * which is used to draw the Rectangle
	 * 
	 * @param point The first rectangle corner as a point
	 * @author A Mashoko
	 * 
	 */
	public void addRetangleFirstCorner(PointFeature point) {
		rectangleElements[0] = point;
	}
	/**
	 * sets the second specified corner as an element of the rectangleElements array
	 * which is used to draw the Rectangle
	 * 
	 * @param point The second rectangle corner as a point
	 * @author A Mashoko
	 * 
	 */
	public void addRectangleLastCorner(PointFeature point) {
		rectangleElements[1] = point;
	}

	// Creates a rectangle object with the points of the RectangleFeature object
	
	
    /**
     * creates a rectangle feature using coordinates of specified rectangle corner coordinates
     * 
     * @return Rectangle the rectangle feature drawn on the canvas
     * @author  A Mashoko
     */
	public Rectangle2D createRectangleFeature() {
		Rectangle2D Rectangle = new Rectangle2D.Double();

		double width;
		double height;
		width = Math.abs(rectangleElements[0].x - rectangleElements[1].x);
		height = Math.abs(rectangleElements[0].y - rectangleElements[1].y);

		double rectangleStart;
		double rectangleEnd;
		if (rectangleElements[1].x > rectangleElements[0].x) {
			rectangleStart = rectangleElements[0].x;
		} else {
			rectangleStart = rectangleElements[1].x;
		}
		if (rectangleElements[1].y > rectangleElements[0].y) {
			rectangleEnd = rectangleElements[0].y;
		} else {
			rectangleEnd = rectangleElements[1].y;
		}

		Rectangle.setRect(rectangleStart, rectangleEnd, width, height);

		return Rectangle;
	}
	
<<<<<<< .mine

=======
	
>>>>>>> .theirs

<<<<<<< .mine
	// Returns the coordinates of the RectangleFeature object as a String









=======

	// Returns the coordinates of the RectangleFeature object as a String
	
	
	/**
	 * The method returns the coordinates of two corners of the rectangle as a string
	 * 
	 * @return textGeometry corners coordinates as text
	 * @author A Mashoko
	 */
>>>>>>> .theirs
	public String getGeometryAsText() {
		String textGeometry = String.valueOf(this.rectangleElements[0].x) + " "
				+ String.valueOf(this.rectangleElements[0].y) + " " + String.valueOf(this.rectangleElements[1].x) + " "
				+ String.valueOf(this.rectangleElements[1].y);
		return textGeometry;
	}


	
	
	
	/**
	 * The method gets the coordinates of corners of a rectangle from a CSV
	 * 
	 * @param csvGeometry coordinates of rectangle corners as a string in csv
	 * @return true if coordinates were extracted from csv successfully
	 * @author A Mashoko
	 * 
	 * 
	 */
	public boolean setGeometryFromCSV(String csvGeometry) {
		try {
			String[] coordinates = csvGeometry.split(" ");
			PointFeature startpoint = new PointFeature();
			PointFeature endpoint = new PointFeature();

			startpoint.setPoint(Double.parseDouble(coordinates[0]), Double.parseDouble(coordinates[1]));
			rectangleElements[0] = startpoint;
			endpoint.setPoint(Double.parseDouble(coordinates[2]), Double.parseDouble(coordinates[3]));
			rectangleElements[1] = endpoint;

			return true;
		} catch (NumberFormatException e) {
			System.err.println("Parsing Error");
			return false;
		}
	}

}
