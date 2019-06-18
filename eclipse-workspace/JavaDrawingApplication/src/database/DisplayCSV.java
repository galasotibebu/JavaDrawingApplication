package database;

import static javapackage.Main.maineditor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javapackage.EditorTools;
import javapackage.LineFeature;
import javapackage.PointFeature;
import javapackage.RectangleFeature;
import javapackage.TriangleFeature;

public class DisplayCSV {

	String filePath;

	public static EditorTools editor;

	int countIdentifier = 0;

	public DisplayCSV(String filePath) {
		this.filePath = filePath;
		DisplayCSV.editor = maineditor.editor;
	}

	public EditorTools displayObjects() throws FileNotFoundException, IOException {
		EditorTools editor = new EditorTools();

		boolean containsHeader = true;
		String fileLine = "";

		try (BufferedReader bufferedreader = Files.newBufferedReader(Paths.get(filePath))) {

			while ((fileLine = bufferedreader.readLine()) != null) {

				if (containsHeader == true) {
					containsHeader = false;
					continue;
				}

				String[] fileRow = fileLine.split(";");

				String objectType = fileRow[0];
				String objectGeometry = fileRow[1];
				System.out.println(objectType);
				System.out.println(fileRow[1]);

				switch (objectType) {
				case "Point":
					PointFeature point = new PointFeature();
					point.setGeometryFromCSV(objectGeometry);
					editor.addPoints(point);

					countIdentifier++;
					point.ShapesId = countIdentifier;
					System.out.println(point.ShapesId);
					break;

				case "Line":
					LineFeature line = new LineFeature();
					line.setGeometryFromCSV(objectGeometry);
					editor.addLines(line);

					countIdentifier++;
					line.ShapesId = countIdentifier;
					System.out.println(line.ShapesId);
					break;

				case "Triangle":
					TriangleFeature triangle = new TriangleFeature();
					triangle.setGeometryFromCSV(objectGeometry);
					editor.addTriangles(triangle);

					countIdentifier++;
					triangle.ShapesId = countIdentifier;
					System.out.println(triangle.ShapesId);
					break;

				case "Rectangle":
					RectangleFeature rectangle = new RectangleFeature();
					rectangle.setGeometryFromCSV(objectGeometry);
					editor.addRectangles(rectangle);

					countIdentifier++;
					rectangle.ShapesId = countIdentifier;
					System.out.println(rectangle.ShapesId);
					break;

				}

			}

		}
		countIdentifier = 0;
		return editor;
	}

}
