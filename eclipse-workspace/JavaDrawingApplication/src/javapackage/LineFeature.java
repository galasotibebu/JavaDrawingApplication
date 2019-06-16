package javapackage;

import java.awt.geom.Line2D;

/**
 * This class is the base for drawing lines  on the canvas.
 * It contains methods for creating a line feature based on coordinates of rectangle 
 * corners. It inherits from the ShapesParent class
 * 
 * @author T Galaso
 * 
 */
public class LineFeature extends ShapesParent {

	// Stores the first and the last PointFeature of a ToolLine object
	public PointFeature[] lineElements = new PointFeature[2];

	/**
	 * The purpose of the constructor is to define the geometry type, which is line
	 * @authorT Galaso
	 */
	public LineFeature() {
		super("Line");
	}

	/**
	 * sets the first specified point as an element of the lineElements array
	 * which is used to draw the line
	 * 
	 * @param point The first point defining a line
	 * @author T Galaso
	 * 
	 */
	public void addLineStart(PointFeature point) {
		lineElements[0] = point;
	}

	/**
	 * sets the second specified point as an element of the lineElements array
	 * which is used to draw the line
	 * 
	 * @param point The second point defining a line
	 * @author T Galaso
	 * 
	 */
	public void addLineEnd(PointFeature point) {
		lineElements[1] = point;
	}


	
	
    /**
     * creates a line feature using coordinates of the two specified ends of the line
     * 
     * @return line the line feature drawn on the canvas
     * @author  T Galaso
     */
	public Line2D createLineFeature() {
		Line2D line = new Line2D.Double((int) this.lineElements[0].x, (int) this.lineElements[0].y,
				(int) this.lineElements[1].x, (int) this.lineElements[1].y);
		return line;
	}

	/**
	 * The method returns the coordinates of two end points of the line as a string
	 * 
	 * @return textGeometry end points coordinates as text
	 * @author T Galaso
	 */
	public String getGeometryAsText() {
		String textGeometry = String.valueOf(this.lineElements[0].x) + " " + String.valueOf(this.lineElements[0].y)
				+ " " + String.valueOf(this.lineElements[1].x) + " " + String.valueOf(this.lineElements[1].y);
		return textGeometry;
	}

	// Sets the Line from a String (provided by database of '.csv')
	
	/**
	 * The method gets the coordinates of two end points of a line from a CSV
	 * 
	 * @param csvGeometry line end points coordinates as a string in csv
	 * @return true if coordinates were extracted from csv successfully
	 * @author T Galaso
	 * 
	 * 
	 */
	public boolean setGeometryFromCSV(String csvGeometry) {
		try {
			String[] coordinates = csvGeometry.split(" ");
			PointFeature startpoint = new PointFeature();
			PointFeature endpoint = new PointFeature();

			startpoint.setPoint(Double.parseDouble(coordinates[0]), Double.parseDouble(coordinates[1]));
			lineElements[0] = startpoint;
			endpoint.setPoint(Double.parseDouble(coordinates[2]), Double.parseDouble(coordinates[3]));
			lineElements[1] = endpoint;

			return true;
		} catch (NumberFormatException e) {
			System.err.println("Parsing Error");
			return false;
		}
	}
}
