package javapackage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.Collections;


/**
 *
 * @author tibebu
 */
public class ShapesParent {	
	/**
	 * A unique ID of geometry objects // while shapes are created in panel
	 */
	public int ShapesId = setShapesId(Main.frame.editor);
	
	/**
	 * The type of geometry
 (see constructor of Shapes and constructor of Main, LineFeature, TriangleFeature and RectangleFeature
	 */
	public String objectType;
	
	/**
	 * Constructor sets the type of the objects.
	 * (see: Constructors of ToolLine, ToolTriangle, ToolRectangle)
	 * @author 
	 * @param objectType String provided by the constructor of the geometrie objects when created
	 */
	public ShapesParent (String objectType) {
		this.objectType = objectType;
	}
	
	/**
	 * Returns the identifier of an object.
	 * @author 
	 * @return Identifier of the object
	 */
	public int getShapesId() {
		return this.ShapesId;
	}
	
	/**
	 * Returns the type of an object.
	 * @author 
	 * @return Type of the object
	 */
	public String getObjectType() {
		return this.objectType;
	}

	/**
	 * Iterates through objects of every type of geometry and adds every ShapesId
	 * to the ShapeIdList, as long as the length of the ShapeIdList is > 0, the ShapeId that 
	 * is returned is the length of the SapesIdList + 1
	 * @author 
	 * @param editor
	 * @return New unique identifier
	 */
	public int setShapesId(EditorTools editor) {
		ArrayList<Integer> ShapesIdList = new ArrayList<>();
		int latestShapesId;
		
		for (PointFeature point: editor.drawingPoints){
			ShapesIdList.add(point.getShapesId());
		}
		for (LineFeature line: editor.drawingLines) {
			ShapesIdList.add(line.getShapesId());
		}
		for (TriangleFeature triangle: editor.drawingTriangles) {
			ShapesIdList.add(triangle.getShapesId());
		}
		for (RectangleFeature rectangle: editor.drawingRectangles) {
			ShapesIdList.add(rectangle.getShapesId());
		}
                
                
		for (PointFeature point: editor.selectedPoints) {
			ShapesIdList.add(point.getShapesId());
		}
		for (LineFeature line: editor.selectedLines){
			ShapesIdList.add(line.getShapesId());
		}
		for(TriangleFeature triangle: editor.selectedTriangles){
			ShapesIdList.add(triangle.getShapesId());
		}
		for (RectangleFeature rectangle: editor.selectedRectangles){
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

