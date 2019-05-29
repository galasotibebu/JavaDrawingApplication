package javapackage;

import java.awt.geom.*;
import java.util.ArrayList;


public class EditorTools {

	// ----------------------------------------------------
	// Declaration of ArrayLists to store new created Features 
	// ---------------------------------------------------
	
	 // ArrayList of new created objects. The arrayList can grow when a new point is created and
	 // shrink when a point is deleted
	public ArrayList<PointFeature> drawingPoints = new ArrayList<>();
	public ArrayList<LineFeature> drawingLines = new ArrayList<>();
	public ArrayList<RectangleFeature> drawingRectangles = new ArrayList<>();
	public ArrayList<TriangleFeature> drawingTriangles = new ArrayList<>();

	// -------------------------------------------------------------------
	// Declaration of ArrayLists to store Corresponding Selected Features 
	// --------------------------------------------------------------------

	// ArrayList of objects which are currently selected
	public ArrayList<PointFeature> selectedPoints = new ArrayList<>();
	public ArrayList<LineFeature> selectedLines = new ArrayList<>();
	public ArrayList<TriangleFeature> selectedTriangles = new ArrayList<>();
	public ArrayList<RectangleFeature> selectedRectangles = new ArrayList<>();

	// --------------------------------------------------------------------
	// CREATE -
	// --------------------------------------------------------------------

	// ----Creates new features and put them inside declared ArrayList
	// (drawing..something..)---
	// --stored in memory--------
	public void addPoints(PointFeature point) {
		drawingPoints.add(point);
	}

	// Adds a LineFeature at the end of the corresponding ArrayList
	public void addLines(LineFeature line) {
		drawingLines.add(line);
	}

	// Adds a TriangleFeature at the end of the corresponding ArrayList
	public void addTriangles(TriangleFeature triangle) {
		drawingTriangles.add(triangle);
	}

	// Adds a RectangleFeature at the end of the corresponding ArrayList
	public void addRectangles(RectangleFeature rectangle) {
		drawingRectangles.add(rectangle);
	}

	// --------------------------------------------------------------------
	// PREPARES FOR MODIFICATION -
	// --------------------------------------------------------------------
	
	// -----Choose features that lies inside 'Selecting Rectangle', and put them
	// inside
	// a declared empty ArrayList(selected..something..) for corresponding
	// features
	public void selectAffectedObjects(Rectangle2D selectionRectangles) {
		drawingPoints.forEach((PointFeature point) -> {
			Ellipse2D point_object = point.createPointFeature();
			// Shape lies entirely within the indicated Rectangle2D
			Rectangle2D queryArea = point_object.getBounds2D();

			// if selection_rectangle contains the object then add this object so the
			// selectedToolPoints
			if (selectionRectangles.contains(queryArea) == true) {
				selectedPoints.add(point);
			}
		});

		drawingLines.forEach((LineFeature line) -> {
			Line2D line_object = line.createLineFeature();
			Rectangle2D queryArea = line_object.getBounds2D();

			// if selection_rectangle contains the object then add this object so the
			// selectedToolFeatures
			if (selectionRectangles.contains(queryArea) == true) {
				selectedLines.add(line);
			}
		});

		drawingTriangles.forEach((TriangleFeature triangle) -> {
			Path2D triangle_object = triangle.createTriangleFeature();
			Rectangle2D queryArea = triangle_object.getBounds2D();

			// if selection_rectangle contains the object then add this object so the
			// selectedTriangleFeatures
			if (selectionRectangles.contains(queryArea) == true) {
				selectedTriangles.add(triangle);
			}
		});

		drawingRectangles.forEach((RectangleFeature rectangle) -> {
			Rectangle2D rectangle_object = rectangle.createRectangleFeature();
			Rectangle2D queryArea = rectangle_object.getBounds2D();

			// if selection_rectangle contains the object then add this object so the
			// selectedToolRectangles
			if (selectionRectangles.contains(queryArea) == true) {
				selectedRectangles.add(rectangle);
			}
		});
	}

	// --------------------------------------------------------------------
	// CANCEL PREPARATIONS FOR MODIFICATION -
	// --------------------------------------------------------------------
	
	// -------------Clears Current Selected Features---------------------
	// releases the memory
	public void clearCurrentSelection() {
		selectedPoints = new ArrayList<>();
		selectedLines = new ArrayList<>();
		selectedTriangles = new ArrayList<>();
		selectedRectangles = new ArrayList<>();
	}

	// --------------------------------------------------------------------
	// PERFORMS DELETION ACTION BY CALLING DELETERS-
	// --------------------------------------------------------------------
	
	// -----Deletes Features whose id's are the same as currently selected
	// Features--------------------------
	public void deleteAffectedObjects() {
		for (PointFeature point : selectedPoints) {
			int identifier = point.getShapesId();
			deletePoint(identifier);
		}
		/*
		 * selectedPoints.forEach((PointFeature point) -> { int identifier =
		 * point.getShapesId(); deletePoint(identifier); });
		 */

		selectedLines.forEach((LineFeature line) -> {
			int identifier = line.getShapesId();
			deleteLine(identifier);
		});

		selectedTriangles.forEach((TriangleFeature triangle) -> {
			int identifier = triangle.getShapesId();
			deleteTriangle(identifier);
		});

		selectedRectangles.forEach((RectangleFeature rectangle) -> {
			int identifier = rectangle.getShapesId();
			deleteRectangle(identifier);
		});
	}	
	// --------------------------------------------------------------------
	// DELETERS -
	// --------------------------------------------------------------------
	
	// Declaration of Deletion Methods that will be implemented for all
	// Features--
	private void deletePoint(int identifier) {
		// loop through every point of managedToolPoints
		for (int i = 0; i < selectedPoints.size(); i++) {
			PointFeature point = selectedPoints.get(i);
			int pointIdentifier = point.getShapesId();
			// if identifier from selectedToolPoints and from managedToolPoints match then
			// delete this point
			if (pointIdentifier == identifier) {
				selectedPoints.remove(i);
			}
		}
	}

	private void deleteLine(int identifier) {
		// loop through every line of drawLine
		for (int i = 0; i < drawingLines.size(); i++) {
			LineFeature line = drawingLines.get(i);
			int lineIdentifier = line.getShapesId();
			// if identifier from selectedToolFeatures and from drawLine match then delete
			// this line
			if (lineIdentifier == identifier) {
				drawingLines.remove(i);
			}
		}
	}

	private void deleteTriangle(int identifier) {
		// loop through every triangle of drawtriangle
		for (int i = 0; i < drawingTriangles.size(); i++) {
			TriangleFeature triangle = drawingTriangles.get(i);
			int triangleIdentifier = triangle.getShapesId();
			// if identifier from selectedTriangleFeatures and from drawtriangle match then
			// delete this triangle
			if (triangleIdentifier == identifier) {
				drawingTriangles.remove(i);
			}
		}
	}

	private void deleteRectangle(int identifier) {
		// loop through every rectangle of managedToolRectangles
		for (int i = 0; i < drawingRectangles.size(); i++) {
			RectangleFeature rectangle = drawingRectangles.get(i);
			int triangleIdentifier = rectangle.getShapesId();
			// if identifier from selectedToolRectangle and from managedToolRectangles match
			// then delete this rectangle
			if (triangleIdentifier == identifier) {
				drawingRectangles.remove(i);
			}
		}
	}

}
