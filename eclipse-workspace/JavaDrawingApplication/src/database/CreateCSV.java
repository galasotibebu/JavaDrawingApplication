package database;


import javapackage.EditorTools;
import database.GUICSV;
import javapackage.PointFeature;
import javapackage.LineFeature;
import javapackage.TriangleFeature;
import javapackage.RectangleFeature;

import static javapackage.Main.frame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Formatter;

/**
 * Creating CSV file
 * @author 
 * 
 */

public class CreateCSV {
	
	public GUICSV csvInterface ;
	public static EditorTools editor;
	
	Formatter fileFormatter;
	
	FileWriter csvWriter;
	
	String objectType;
	
	String objectGeometry;
	
	String filePath;
	
	public CreateCSV() {
		CreateCSV.editor = frame.editor;		
	}
	
	/**
	 * Opening CSV file with defining path and format 
	 * @author 
	 */
	public void openFile() {		
		try {
			filePath =  GUICSV.getFilePath();
			fileFormatter = new Formatter(filePath);
		} catch (FileNotFoundException e) {

			System.out.println("Error occured while saving");
		}
	}
	
	/**
	 * Close the CSV file 
	 * @author 
	 */
	public void closeFile() {
		fileFormatter.close();
	}
	
	/**
	 * Writing objects (point,line,triangle,rectangle) to the CSV file with the type and geometry information with ; delimiter and save it as a format of .csv
	 * @param filePath
	 * @return
	 * @author 
	 */
	public boolean fillFile(String filePath) {
		try {
			csvWriter = new FileWriter(new File(filePath));
			csvWriter.write("object_type;object_geometry" + "\n");	
			
			/*
			if (editor.managedToolLines.size() >=2) {
				editor.managedToolLines.remove(editor.managedToolLines.size()-1);
			}
			
			if (editor.managedToolTriangles.size() >=2) {
				editor.managedToolTriangles.remove(editor.managedToolTriangles.size()-1);
			}
			
			if (editor.managedToolRectangles.size() >=2) {
				editor.managedToolRectangles.remove(editor.managedToolRectangles.size()-1);
			}	
			*/		
			
			editor.drawingPoints.forEach((PointFeature point) -> {
				objectType = point.getObjectType();
				objectGeometry = point.getGeometryAsText();
				try {
					csvWriter.write(objectType + ";" + objectGeometry + "\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			
			editor.drawingLines.forEach((LineFeature line) -> {
				objectType = line.getObjectType();
				objectGeometry = line.getGeometryAsText();
				try {
					csvWriter.write(objectType + ";" + objectGeometry + "\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			});	
			
			editor.drawingTriangles.forEach((TriangleFeature triangle) -> {
				objectType = triangle.getObjectType();
				objectGeometry = triangle.getGeometryAsText();
				try {
					csvWriter.write(objectType + ";" + objectGeometry + "\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			
			editor.drawingRectangles.forEach((RectangleFeature rectangle) -> {
				objectType = rectangle.getObjectType();
				objectGeometry = rectangle.getGeometryAsText();
				try {
					csvWriter.write(objectType + ";" + objectGeometry + "\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			
			csvWriter.close();						
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return true;
	}
}
