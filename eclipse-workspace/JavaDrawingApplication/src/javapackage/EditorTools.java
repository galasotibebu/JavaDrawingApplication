package javapackage;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.*;
import java.util.ArrayList;

import javax.swing.JPanel;

import javapackage.RectangleFeature.MoveFeature.MovingAdapter;

public class EditorTools extends JPanel {

	// ----------------------------------------------------
	// Declaration of ArrayLists to store new created Features
	// ---------------------------------------------------

	// ArrayList of new created objects. The arrayList can grow when a new point is
	// created and
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

		for (PointFeature point : drawingPoints) {
			Ellipse2D pointObj = point.createPointFeature();
			Rectangle2D queryPoints = pointObj.getBounds2D();// reads the x,y coordinate of an affected PointFeatures
			//
			if (selectionRectangles.contains(queryPoints) == true) {
				selectedPoints.add(point);
				System.out.println(drawingPoints.size() + "points created.");
				System.out.println(selectedPoints.size() + "points selected.");
			}
		}

		for (LineFeature line : drawingLines) {
			Line2D lineObj = line.createLineFeature();
			Rectangle2D queryLines = lineObj.getBounds2D();

			// if selection_rectangle contains the object then add this object so the
			// selected Features
			if (selectionRectangles.contains(queryLines) == true) {
				selectedLines.add(line);
			}
		}

		for (TriangleFeature triangle : drawingTriangles) {
			Path2D triangleObj = triangle.createTriangleFeature();
			Rectangle2D queryTriangles = triangleObj.getBounds2D();

			// if selection_rectangle contains the object then add this object so the
			// selectedTriangleFeatures
			if (selectionRectangles.contains(queryTriangles) == true) {
				selectedTriangles.add(triangle);
			}
		}

		for (RectangleFeature rectangle : drawingRectangles) {
			Rectangle2D rectangleObj = rectangle.createRectangleFeature();
			Rectangle2D queryRect = rectangleObj.getBounds2D();

			// if selection_rectangle contains the object then add this object so the
			// selected Rectangles
			if (selectionRectangles.contains(queryRect) == true) {
				selectedRectangles.add(rectangle);
			}
		}
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

		for (LineFeature line : selectedLines) {
			int identifier = line.getShapesId();
			deleteLine(identifier);
		}

		for (TriangleFeature triangle : selectedTriangles) {
			int identifier = triangle.getShapesId();
			deleteTriangle(identifier);
		}

		for (RectangleFeature rectangle : selectedRectangles) {
			int identifier = rectangle.getShapesId();
			deleteRectangle(identifier);
		}
	}
	// --------------------------------------------------------------------
	// DELETERS -
	// --------------------------------------------------------------------

	// Declaration of Deletion Methods that will be implemented for all
	// Features--
	private void deletePoint(int identifier) {
		// loop through every point of Points
		for (int i = 0; i < drawingPoints.size(); i++) {
			PointFeature point = drawingPoints.get(i);


			int pointIdentifier = point.getShapesId();
			// if identifier from selectedPoints and from Points match then
			// delete this point
			if (pointIdentifier == identifier) {
				drawingPoints.remove(i);
			}
		}
	}

	private void deleteLine(int identifier) {
		// loop through every line of drawLine
		for (int i = 0; i < drawingLines.size(); i++) {
			LineFeature line = drawingLines.get(i);
			int lineIdentifier = line.getShapesId();
			// if identifier from selectedFeatures and from drawLine match then delete
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
		// loop through every Rectangles
		for (int i = 0; i < drawingRectangles.size(); i++) {
			RectangleFeature rectangle = drawingRectangles.get(i);
			int triangleIdentifier = rectangle.getShapesId();
			// if identifier from selected Rectangle and from Rectangles match
			// then delete this rectangle
			if (triangleIdentifier == identifier) {
				drawingRectangles.remove(i);
			}
		}
	}
	
	
	
	
	// -----Move Features whose id's are the same as currently selected
	// Features--------------------------
	public void moveAffectedObjects() {

		for (RectangleFeature rectangle : selectedRectangles) {
			int identifier = rectangle.getShapesId();
			moveRectangle(identifier);
		}
	}
	
//****************Methods for moving objects on the canvas********************
	

	private void moveRectangle(int identifier) {
		// loop through every Rectangles
		for (int i = 0; i < drawingRectangles.size(); i++) {
			RectangleFeature rectangle = drawingRectangles.get(i);
			int rectangleIdentifier = rectangle.getShapesId();
			// if identifier from selected Rectangle and from Rectangles match
			// then delete this rectangle
			if (rectangleIdentifier == identifier) {
				
				
//				drawingRectangles.move(i);
			}
		}
	}
	
	


}
