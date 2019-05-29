package javapackage;

import java.awt.geom.Path2D;

public class TriangleFeature extends ShapesParent {

	// Stores the the vertexes of TriangleFeature object
	public PointFeature[] triangleElements = new PointFeature[3];

	// Defines type of geometry
	public TriangleFeature() {
		super("Triangle");
	}

	// Adds the first point of the triangle to the List of triangle elements
	// as first entry	 
	public void addTriangleStart(PointFeature point) {
		triangleElements[0] = point;
	}

	// Adds the second point of the triangle to List of triangle elements
	// as first entry
	public void addTriangleMid(PointFeature point) {
		triangleElements[1] = point;
	}

	// Adds the last point of the triangle to the List of
	 // triangle elements as first entry
	public void addTriangleEnd(PointFeature point) {
		triangleElements[2] = point;
	}

	// Creates a drawable Path2D object with the points of the TriangleFeature
	public Path2D createTriangleFeature() {
		Path2D pathTriangle = new Path2D.Double();
		pathTriangle.moveTo((int) this.triangleElements[0].x, (int) this.triangleElements[0].y);
		pathTriangle.lineTo((int) this.triangleElements[1].x, (int) this.triangleElements[1].y);
		pathTriangle.lineTo((int) this.triangleElements[2].x, (int) this.triangleElements[2].y);
		pathTriangle.lineTo((int) this.triangleElements[0].x, (int) this.triangleElements[0].y);
		return pathTriangle;
	}

	// Returns the coordinates of the TriangleFeature object as a String
	public String getGeometryAsText() {
		String textGeometry = String.valueOf(this.triangleElements[0].x) + " "
				+ String.valueOf(this.triangleElements[0].y) + " " + String.valueOf(this.triangleElements[1].x) + " "
				+ String.valueOf(this.triangleElements[1].y) + " " + String.valueOf(this.triangleElements[2].x) + " "
				+ String.valueOf(this.triangleElements[2].y) + " " + String.valueOf(this.triangleElements[0].x) + " "
				+ String.valueOf(this.triangleElements[0].y);
		return textGeometry;
	}

	// Sets the Triangle from '.csv'
	public boolean setGeometryFromCSV(String csvGeometry) {
		try {
			String[] coordinates = csvGeometry.split(" ");
			PointFeature startpoint = new PointFeature();
			PointFeature midpoint = new PointFeature();
			PointFeature endpoint = new PointFeature();

			startpoint.setPoint(Double.parseDouble(coordinates[0]), Double.parseDouble(coordinates[1]));
			triangleElements[0] = startpoint;
			midpoint.setPoint(Double.parseDouble(coordinates[2]), Double.parseDouble(coordinates[3]));
			triangleElements[1] = midpoint;
			endpoint.setPoint(Double.parseDouble(coordinates[4]), Double.parseDouble(coordinates[5]));
			triangleElements[2] = endpoint;

			return true;
		} catch (NumberFormatException e) {
			System.err.println("Parsing Error");
			return false;
		}
	}

}
