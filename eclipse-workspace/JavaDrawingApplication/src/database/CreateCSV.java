package database;

import javapackage.EditorTools;
import database.GUICSV;
import javapackage.PointFeature;
import javapackage.LineFeature;
import javapackage.TriangleFeature;
import javapackage.RectangleFeature;

import static javapackage.Main.maineditor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Formatter;
/**
 * Declaration of variables, methods for creating and opening csv
 * @author gykr1011
 */
public class CreateCSV {

	
	String type;
	String coords;
	String location;
	Formatter formatFile;
	FileWriter writeCsv;
	
	
	
	public GUICSV csvInterface;
	public static EditorTools editor;
	

	

	public CreateCSV() {
		CreateCSV.editor = maineditor.editor;
	}
	/**
	 * Method for opening .csv files
	 * @author gykr1011
	 */
	public void openFile() {
		try {
			location = GUICSV.getFilePath();
			formatFile = new Formatter(location);
		} catch (FileNotFoundException e) {

			System.out.println("File couldn`t be saved");
		}
	}
	/**
	 * Method for closing .csv file
	 * @author gykr1011
	 */
	public void closeFile() {
		formatFile.close();
	}
	/**
	 * Method for updating file with information about the different geometries
	 * @param location
	 * @return true
	 * @author gykr1011
	 */
	public boolean addFeatures(String location) {
		try {
			writeCsv = new FileWriter(new File(location));
			writeCsv.write("FeatureType;Geometry" + "\n");

			editor.drawingPoints.forEach((PointFeature point) -> {
				type = point.getObjectType();
				coords = point.getGeometryAsText();
				try {
					writeCsv.write(type + ";" + coords + "\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			});

			editor.drawingLines.forEach((LineFeature line) -> {
				type = line.getObjectType();
				coords = line.getGeometryAsText();
				try {
					writeCsv.write(type + ";" + coords + "\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			});

			editor.drawingTriangles.forEach((TriangleFeature triangle) -> {
				type = triangle.getObjectType();
				coords = triangle.getGeometryAsText();
				try {
					writeCsv.write(type + ";" + coords + "\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			});

			editor.drawingRectangles.forEach((RectangleFeature rectangle) -> {
				type = rectangle.getObjectType();
				coords = rectangle.getGeometryAsText();
				try {
					writeCsv.write(type + ";" + coords + "\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			});

			writeCsv.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return true;
	}
}
