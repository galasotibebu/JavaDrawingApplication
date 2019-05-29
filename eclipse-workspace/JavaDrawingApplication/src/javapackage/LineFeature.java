package javapackage;

import java.awt.geom.Line2D;

public class LineFeature extends ShapesParent {

	// Stores the first and the last PointFeature of a ToolLine object
	public PointFeature[] lineElements = new PointFeature[2];

	// The constructor defines the line geometry
	public LineFeature() {
		super("Line");
	}

	// Adds the first point of the line to the List of line elements
	public void addLineStart(PointFeature point) {
		lineElements[0] = point;
	}

	// Adds the second point of the line to the List of line elements
	public void addLineEnd(PointFeature point) {
		lineElements[1] = point;
	}

	// Creates a drawable Line2D object with the points of the ToolLine object
	public Line2D createLineFeature() {
		Line2D line = new Line2D.Double((int) this.lineElements[0].x, (int) this.lineElements[0].y,
				(int) this.lineElements[1].x, (int) this.lineElements[1].y);
		return line;
	}

	// Returns the coordinates of the PointFeature object as a String
	public String getGeometryAsText() {
		String textGeometry = String.valueOf(this.lineElements[0].x) + " " + String.valueOf(this.lineElements[0].y)
				+ " " + String.valueOf(this.lineElements[1].x) + " " + String.valueOf(this.lineElements[1].y);
		return textGeometry;
	}

	// Sets the Line from a String (provided by database of '.csv')
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
