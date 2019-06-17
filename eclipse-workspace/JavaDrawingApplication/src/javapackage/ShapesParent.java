package javapackage;

import java.util.ArrayList;
import java.util.Collections;

public class ShapesParent {

	// A unique ID of geometry objects // while shapes are created in panel
	public int ShapesId = setShapesId(Main.frame.editor);

	// Geometry type
	public String objectType;

	// Constructor sets the type of the objects
	public ShapesParent(String objectType) {
		this.objectType = objectType;
	}

	// Returns the shape_id of an object
	public int getShapesId() {
		return this.ShapesId;
	}

	// Returns the type of an object
	public String getObjectType() {
		return this.objectType;
	}

	// Iterates through objects of every type of geometry and adds every ShapesId to
	// the ShapeIdList, as long as the length of the ShapeIdList is > 0
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
		for (PolygonFeature polygon : editor.drawingPolygons) {
			ShapesIdList.add(polygon.getShapesId());
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
		for (PolygonFeature polygon : editor.selectedPolygons) {
			ShapesIdList.add(polygon.getShapesId());
		}
		

		if (ShapesIdList.isEmpty()) {
			latestShapesId = 0;
		} else {
			latestShapesId = Collections.max(ShapesIdList);
		}
		return latestShapesId + 1;
	}

}
