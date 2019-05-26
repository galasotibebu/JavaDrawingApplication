
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javapackage;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 *
 * @author admin
 */
public class EditorTools {
         
        //::::::::::::::::::::::::::::::::::::::::::::::::::::
        //Declaration of ArrayLists of new created Features ::
        //::::::::::::::::::::::::::::::::::::::::::::::::::::
	/**
	 * ArrayList of Main
	 * This Main object can grow when a new point is created and shrink
	 * when a point is deleted. Used to store objects currently being drawing
	 */
	public ArrayList<PointFeature> drawingPoints = new ArrayList<>();
        
	/**
	 * ArrayList of LineFeature
	 * This LineFeature object can grow when a new Line is created and shrink
	 * when a Line is deleted.
	 */
	public ArrayList<LineFeature> drawingLines = new ArrayList<>();
        
	/**
	 * ArrayList of LineFeature
	 * This LineFeature object can grow when a new Line is created and shrink
	 * when a Line is deleted.
	 */
	public ArrayList<RectangleFeature> drawingRectangles = new ArrayList<>();
        
	/**
	 * ArrayList of RectangleFeature
	 * This RectangleFeature object can grow when a new Rectangle is created and shrink
	 * when a Rectangle is deleted.
	 */
	public ArrayList<TriangleFeature> drawingTriangles = new ArrayList<>();
        
        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        //Declaration of ArrayLists to store Corresponding Selected Features ::
        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

	/**
	 * ArrayList of Main
         * This Points are currently selected.
	 */
	public ArrayList<PointFeature> selectedPoints = new ArrayList<>();

	/**
	 * ArrayList of LineFeature
	 * This Lines are currently selected.
	 */
	public ArrayList<LineFeature> selectedLines = new ArrayList<>();
        
	/**
	 * ArrayList of TriangleFeature
	 * This Triangle are currently selected.
	 */
	public ArrayList<TriangleFeature> selectedTriangles = new ArrayList<>();
        
        /**
	 * ArrayList of RectangleFeature
	 * This Rectangles are are currently selected.
	 */
	public ArrayList<RectangleFeature> selectedRectangles = new ArrayList<>();

	//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        //    Add Features to Their Corresponding ArrayLists            ::
        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	
        /**
	 * Adds a ToolPoint at the end of the corresponding ArrayList.
	 * @author 
     * @param point 
	 */
	public void addPoints(PointFeature point) {
		drawingPoints.add(point);
	}
	/**
	 * Adds a ToolFeature at the end of the corresponding ArrayList.
	 * @author 
     * @param line 
	 */
	public void addLines(LineFeature line) {
		drawingLines.add(line);
	}

	/**
	 * Adds a TriangleFeature at the end of the corresponding ArrayList.
	 * @author 
     * @param triangle 
	 */
	public void addTriangles(TriangleFeature triangle) {
		drawingTriangles.add(triangle);
	}

	/**
	 * Adds a ToolRectangle at the end of the corresponding ArrayList.
	 * @author 
     * @param rectangle 
	 */
	public void addRectangles(RectangleFeature rectangle) {
		drawingRectangles.add(rectangle);
	}
        
        //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        //        Clears  Current Selected Features                     ::
        //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	/**
	 * Clears the ArrayLists that contain the objects that are added by the selection rectangle
	 * @author 
	 */
	public void clearCurrentSelection() {
		selectedPoints = new ArrayList<>();
		selectedLines = new ArrayList<>();
		selectedTriangles = new ArrayList<>();
		selectedRectangles = new ArrayList<>();
	}

	/**
	 * performs the given action for each element of the Iterable until all elements have been processed or the action throws an exception.
	 * Iterates through every object of every type of geometry and calculates whether the objects lies
	 * inside the selection rectangle or outside.
	 * If the objects lies inside the selection rectangle it will be added to the ArrayList of selected objects.
	 * The function contains() is provided by Java.
	 * @author 
     * @param selectionRectangles 
	 */
	
public void selectAffectedObjects(Rectangle2D selectionRectangles) {
		drawingPoints.forEach((PointFeature point) -> {
			Ellipse2D point_object 		= point.createPointFeature();
			Rectangle2D queryArea		= point_object.getBounds2D();

			//if selection_rectangle contains the object then add this object so the selectedToolPoints
			if (selectionRectangles.contains(queryArea) == true) {
				selectedPoints.add(point);
			}
		});

		drawingLines.forEach((LineFeature line) -> {
			Line2D line_object = line.createLineFeature();
			Rectangle2D queryArea = line_object.getBounds2D();

			//if selection_rectangle contains the object then add this object so the selectedToolFeatures
			if ( selectionRectangles.contains(queryArea) == true) {
				selectedLines.add(line);
			}
		});

		drawingTriangles.forEach((TriangleFeature triangle) -> {
			Path2D triangle_object 		= triangle.createTriangleFeature();
			Rectangle2D queryArea		= triangle_object.getBounds2D();

			//if selection_rectangle contains the object then add this object so the selectedTriangleFeatures
			if (selectionRectangles.contains(queryArea) == true) {
				selectedTriangles.add(triangle);
			}
		});

		drawingRectangles.forEach((RectangleFeature rectangle) -> {
			Rectangle2D rectangle_object = rectangle.createRectangleFeature();
			Rectangle2D queryArea = rectangle_object.getBounds2D();

			//if selection_rectangle contains the object then add this object so the selectedToolRectangles
			if ( selectionRectangles.contains(queryArea) == true) {
				selectedRectangles.add(rectangle);
			}
		});
}

	/**
	 * Iterates through every object of every type of geometry in the ArrayList of the selected objects
	 * and requests the id of the objects. After that it iterates through the ArrayLists of objects that
	 * are currently displayed and deletes objects with the same id as the object in the ArrayLists of selected objects.
	 * @author 
	 */
	
	
	//:::::::::::::::::::::
	//::::::::::::::::::::..
	
	
	
	public void deleteAffectedObjects() {
		for (PointFeature point : selectedPoints) {
			int identifier = point.getShapesId();
			deletePoint(identifier);
		}
		/*selectedPoints.forEach((PointFeature point) -> {
			int identifier = point.getShapesId();
			deletePoint(identifier);
		});*/

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

	/**
	 * Iterates through every drawn ToolPoint in the corresponding ArrayList and deletes the object with the
	 * same id as the provided id in the parameter.
	 * @author 
	 * @param identifier Identifies the point that should be deleted
	 */
	private void deletePoint(int identifier) {
		//loop through every point of managedToolPoints
		for ( int i=0; i<selectedPoints.size(); i++) {
			PointFeature point = selectedPoints.get(i);
			int pointIdentifier = point.getShapesId();
			//if identifier from selectedToolPoints and from managedToolPoints match then delete this point
			if (pointIdentifier == identifier) {
				selectedPoints.remove(i);
			}
		}
	}

	/**
	 * Iterates through every drawn ToolFeature in the corresponding ArrayList and deletes the object with the
	 * same id as the provided id in the parameter.
	 * @author 
	 * @param identifier Identifies the line that should be deleted
	 */
	private void deleteLine(int identifier) {
		//loop through every line of drawLine
		for ( int i=0; i<drawingLines.size(); i++) {
			LineFeature line = drawingLines.get(i);
			int lineIdentifier = line.getShapesId();
			//if identifier from selectedToolFeatures and from drawLine match then delete this line
			if (lineIdentifier == identifier) {
				drawingLines.remove(i);
			}
		}
	}

	/**
	 * Iterates through every drawn TriangleFeature in the corresponding ArrayList and deletes the object with the
	 * same id as the provided id in the parameter.
	 * @author 
	 * @param identifier Identifies the triangle that should be deleted
	 */
	private void deleteTriangle(int identifier) {
		//loop through every triangle of drawtriangle
		for ( int i=0; i<drawingTriangles.size(); i++) {
			TriangleFeature triangle = drawingTriangles.get(i);
			int triangleIdentifier = triangle.getShapesId();
			//if identifier from selectedTriangleFeatures and from drawtriangle match then delete this triangle
			if (triangleIdentifier == identifier) {
				drawingTriangles.remove(i);
			}
		}
	}

	/**
	 * Iterates through every drawn ToolRectangle in the corresponding ArrayList and deletes the object with the
	 * same id as the provided id in the parameter.
	 * @author 
	 * @param identifier Identifies the rectangle that should be deleted
	 */
	private void deleteRectangle(int identifier) {
		//loop through every rectangle of managedToolRectangles
		for ( int i=0; i<drawingRectangles.size(); i++) {
			RectangleFeature rectangle = drawingRectangles.get(i);
			int triangleIdentifier = rectangle.getShapesId();
			//if identifier from selectedToolRectangle and from managedToolRectangles match then delete this rectangle
			if (triangleIdentifier == identifier) {
				drawingRectangles.remove(i);
			}
		}
	}
}


