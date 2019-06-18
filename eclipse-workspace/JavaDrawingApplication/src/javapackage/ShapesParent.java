package javapackage;

import java.util.ArrayList;
import java.util.Collections;


/**
 * This is the Super class for PointFeature, RectangleFeature and TriangleFeature classes
 * @author T Galaso
 * 
 */
public class ShapesParent {

	// A unique ID of geometry objects // while shapes are created in panel
	public int ShapesId = setShapesId(Main.frame.editor);

	// Geometry type
	public String objectType;

	// Constructor sets the type of the objects
	
	/**
	 * The constructor gives the type of geometry for the objects in the subclasses
	 *  of ShapesParent class
	 *  
	 * @param objectType the type of the geometry whether it is point, line, triangle or rectangle
	 * @author T Galaso
	 */
	public ShapesParent(String objectType) {
		this.objectType = objectType;
	}

	// Returns the shape_id of an object
	
	/**
	 *Gets the shape id which is an identifier for each geometry
	 *
	 *@return ShapesId Identifier of the geometry
	 *@author T Galaso
	 * 
	 */
	public int getShapesId() {
		return this.ShapesId;
	}

	// Returns the type of an object
	/**
	 *Gets the type of geometry (point, line, triangle, rectangle)
	 *
	 *@return objectType type of the geometry whether it is point, line, triangle or rectangle
	 *@author T Galaso
	 * 
	 */	

	public String getObjectType() {
		return this.objectType;
	}

	// Iterates through objects of every type of geometry and adds every ShapesId to
	// the ShapeIdList, as long as the length of the ShapeIdList is > 0
	
	
	/**
	 * The method adds the ShapesId for each each feature to an array 
	 * 
	 * @param editor 
	 * @author T Galaso
	 * @return The next shapesID
	 * 
	 */
	public int setShapesId(EditorTools editor) {
		ArrayList<Integer> ShapesIdList = new ArrayList<>();
		int latestShapesId;

		for (PointFeature point : editor.drawingPoints) {
			ShapesIdList.add(point.getShapesId());
		}
		for (LineFeature line : editor.drawingLines) {
			ShapesIdList.add(line.getShapesId());
		}
		for (TriangleFeature triangle : editor.drawingTriangles) {
			ShapesIdList.add(triangle.getShapesId());
		}
		for (RectangleFeature rectangle : editor.drawingRectangles) {
			ShapesIdList.add(rectangle.getShapesId());
		}

		for (PointFeature point : editor.selectedPoints) {
			ShapesIdList.add(point.getShapesId());
		}
		for (LineFeature line : editor.selectedLines) {
			ShapesIdList.add(line.getShapesId());
		}
		for (TriangleFeature triangle : editor.selectedTriangles) {
			ShapesIdList.add(triangle.getShapesId());
		}
		for (RectangleFeature rectangle : editor.selectedRectangles) {
			ShapesIdList.add(rectangle.getShapesId());
		}

		if (ShapesIdList.isEmpty()) {
			latestShapesId = 0;
		} else {
			latestShapesId = Collections.max(ShapesIdList);
		}
		return latestShapesId + 1;
	}

}
