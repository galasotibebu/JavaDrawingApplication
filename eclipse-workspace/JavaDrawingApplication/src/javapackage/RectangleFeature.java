package javapackage;
import java.awt.geom.Rectangle2D;


public class RectangleFeature extends ShapesParent {

	// Stores the first and the last PointFeature of a RectangleFeature object

	public PointFeature[] rectangleElements = new PointFeature[2];

	// The constructor defines the rectangle geometry
	// inherits ShapesParent
	public RectangleFeature() {
		super("Rectangle");
	}

	public void addRetangleFirstCorner(PointFeature point) {
		rectangleElements[0] = point;
	}

	public void addRectangleLastCorner(PointFeature point) {
		rectangleElements[1] = point;
	}

	// Creates a rectangle object with the points of the RectangleFeature object
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
	

	// Returns the coordinates of the RectangleFeature object as a String
	public String getGeometryAsText() {
		String textGeometry = String.valueOf(this.rectangleElements[0].x) + " "
				+ String.valueOf(this.rectangleElements[0].y) + " " + String.valueOf(this.rectangleElements[1].x) + " "
				+ String.valueOf(this.rectangleElements[1].y);
		return textGeometry;
	}

	// Sets the Rectangle from a String (from csv file)
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
