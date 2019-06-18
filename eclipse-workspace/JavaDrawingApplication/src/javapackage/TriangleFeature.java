package javapackage;

import java.awt.geom.Path2D;

/**
 * This class is the base for drawing triangles  on the canvas.
 * It contains methods for creating a triangle feature based on coordinates of rectangle 
 * corners. It inherits from the ShapesParent class
 * 
 * @author T Galaso
 * 
 */

public class TriangleFeature extends ShapesParent {

	// Stores the the vertexes of TriangleFeature object
	public PointFeature[] triangleElements = new PointFeature[3];

	/**
	 * The purpose of the constructor is to define the geometry type, which is Triangle
	 * @author T Galaso
	 */
	public TriangleFeature() {
		super("Triangle");
	}

 
	/**
	 * This methods captures the coordinates of the first corner that defines a triangle
	 * and appends it to an array of point coordinates.
	 * 
	 * @param The first triangle corner as a point
	 * @author T Galaso
	 * 
	 */
	public void addTriangleStart(PointFeature point) {
		triangleElements[0] = point;
	}

	/**
	 * This methods captures the coordinates of the second corner that defines a triangle
	 * and appends it to an array of point coordinates.
	 * 
	 * @param The second triangle corner as a point
	 * @author T Galaso
	 * 
	 */
	public void addTriangleMid(PointFeature point) {
		triangleElements[1] = point;
	}
	/**
	 * Captures the coordinates of the third corner that defines a triangle
	 * and appends it to an array of point coordinates.
	 * 
	 * @param The third triangle corner as a point
	 * @author T Galaso
	 * 
	 */
	public void addTriangleEnd(PointFeature point) {
		triangleElements[2] = point;
	}


	
	/**
	 * The method creates a Triangle feature that is drawn to the canvas
	 * @return pathTriangle outline of the newly created rectangle
	 * @author T Galaso
	 * 
	 */
	public Path2D createTriangleFeature() {
		Path2D pathTriangle = new Path2D.Double();
		pathTriangle.moveTo((int) this.triangleElements[0].x, (int) this.triangleElements[0].y);
		pathTriangle.lineTo((int) this.triangleElements[1].x, (int) this.triangleElements[1].y);
		pathTriangle.lineTo((int) this.triangleElements[2].x, (int) this.triangleElements[2].y);
		pathTriangle.lineTo((int) this.triangleElements[0].x, (int) this.triangleElements[0].y);
		return pathTriangle;
	}


	/**
	 * The method returns the coordinates of the three corners of the Triangle as a string
	 * 
	 * @return textGeometry corners coordinates as text
	 * @authorT Galaso
	 */
	public String getGeometryAsText() {
		String textGeometry = String.valueOf(this.triangleElements[0].x) + " "
				+ String.valueOf(this.triangleElements[0].y) + " " + String.valueOf(this.triangleElements[1].x) + " "
				+ String.valueOf(this.triangleElements[1].y) + " " + String.valueOf(this.triangleElements[2].x) + " "
				+ String.valueOf(this.triangleElements[2].y) + " " + String.valueOf(this.triangleElements[0].x) + " "
				+ String.valueOf(this.triangleElements[0].y);
		return textGeometry;
	}


	/**
	 * The method gets the coordinates of corners of a triangle
	 * @param csvGeometry coordinates of triangle corners as a string in csv
	 * @return true if coordinates were extracted from csv successfully
	 * @author T Galaso
	 * 
	 * 
	 */
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
