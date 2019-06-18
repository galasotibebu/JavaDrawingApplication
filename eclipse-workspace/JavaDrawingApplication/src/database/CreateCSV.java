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

public class CreateCSV {

	public GUICSV csvInterface;
	public static EditorTools editor;

	Formatter fileFormatter;

	FileWriter csvWriter;

	String objectType;

	String objectGeometry;

	String filePath;

	public CreateCSV() {
		CreateCSV.editor = maineditor.editor;
	}

	public void openFile() {
		try {
			filePath = GUICSV.getFilePath();
			fileFormatter = new Formatter(filePath);
		} catch (FileNotFoundException e) {

			System.out.println("Error occured while saving");
		}
	}

	public void closeFile() {
		fileFormatter.close();
	}

	public boolean fillFile(String filePath) {
		try {
			csvWriter = new FileWriter(new File(filePath));
			csvWriter.write("object_type;object_geometry" + "\n");

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
