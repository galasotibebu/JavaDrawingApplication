package database;

import javapackage.EditorTools;
import javapackage.PointFeature;
import javapackage.LineFeature;
import javapackage.TriangleFeature;
import javapackage.RectangleFeature;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseLogin {
	
	public String DBMS;
	public String dbName;
	public String dbHost;
	public String dbPort;
	public String dbUser;
	public String dbPassword;
	public String tableName;	
	
	int countIdentifier = 0;
	/**
	 * Connect the application with Mysql database
	 * @return
	 * @throws SQLException
	 */

	public Connection accessorConnection() throws SQLException {
		String connection =  "jdbc:" + DBMS +"://" + dbHost + ":" + dbPort + "/" +	dbName + "?autoReconnect=true&useSSL=false&serverTimezone=UTC";
		System.out.println(connection);
		System.out.println(dbUser);
		System.out.println(dbPassword);
		Connection dbConnection = DriverManager.getConnection(connection, dbUser,  dbPassword);
		return dbConnection;
	}
	

	public void InsertGeometricFeature(EditorTools editor) throws SQLException {// "InsertGeometricFeature" renamed it was insertToolObject
		Connection connection = accessorConnection();
		
		String objectType;
		String objectGeometry;
		
		for (PointFeature point : editor.drawingPoints) {
			objectType = point.getObjectType();
			objectGeometry = point.getGeometryAsText();
			PreparedStatement insertPointFeature = connection.prepareStatement("INSERT INTO cadproject"+
					"(type, geom) VALUES('" + objectType + "','" + objectGeometry + "')");
			insertPointFeature.executeUpdate();
		}
		
		for (LineFeature line : editor.drawingLines) {
			objectType = line.getObjectType();
			objectGeometry = line.getGeometryAsText();
			PreparedStatement insertLineFeature = connection.prepareStatement("INSERT INTO cadproject"+
					"(type, geom) VALUES('" + objectType + "','" + objectGeometry + "')");
			insertLineFeature.executeUpdate();
		}
		
		for (TriangleFeature triangle : editor.drawingTriangles) {
			objectType = triangle.getObjectType();
			objectGeometry = triangle.getGeometryAsText();
			PreparedStatement insertTriangleFeature = connection.prepareStatement("INSERT INTO cadproject"+
					"(type, geom) VALUES('" + objectType + "','" + objectGeometry + "')");
			insertTriangleFeature.executeUpdate();
		}
		
		for (RectangleFeature rectangle : editor.drawingRectangles) {
			objectType = rectangle.getObjectType();
			objectGeometry = rectangle.getGeometryAsText();
			PreparedStatement insertRectangleFeature = connection.prepareStatement("INSERT INTO cadproject"+
					"(type, geom) VALUES('" + objectType + "','" + objectGeometry + "')");
			insertRectangleFeature.executeUpdate();
		}
	}
	

	public EditorTools extractGeometricFeature() throws SQLException {
		EditorTools neweditortool = new EditorTools();
		
		Connection connection = accessorConnection();
		java.sql.ResultSet SelectedFeature;
		PreparedStatement displayFeatures;
		
		displayFeatures = connection.prepareStatement("SELECT * FROM cadproject");
		SelectedFeature = displayFeatures.executeQuery();
		
		while (SelectedFeature.next()) {
			String objectType = SelectedFeature.getString("type");
			String objectGeometry = SelectedFeature.getString("geom");
			
			switch(objectType) {
			
				case "Point":
					PointFeature point = new PointFeature();
					point.setGeometryFromCSV(objectGeometry);
					neweditortool.addPoints(point);
					
					countIdentifier++;
					point.ShapesId = countIdentifier;
					System.out.println(point.ShapesId);
					break;
					
				case "Line":
					LineFeature line = new LineFeature();
					line.setGeometryFromCSV(objectGeometry);
					neweditortool.addLines(line);
					
					countIdentifier++;
				    line.ShapesId = countIdentifier;
				    System.out.println(line.ShapesId);
					break;
					
				case "Triangle":
					TriangleFeature triangle = new TriangleFeature();
					triangle.setGeometryFromCSV(objectGeometry);
					neweditortool.addTriangles(triangle);
					
					countIdentifier++;
				    triangle.ShapesId = countIdentifier;
				    System.out.println(triangle.ShapesId);
					break;
					
				case "Rectangle":
					RectangleFeature rectangle = new RectangleFeature ();
					rectangle.setGeometryFromCSV(objectGeometry);
					neweditortool.addRectangles(rectangle);
					
					countIdentifier++;
				    rectangle.ShapesId = countIdentifier;
				    System.out.println(rectangle.ShapesId);
					break;
			}
			
		}

		countIdentifier = 0;
		
		SelectedFeature.close();
		displayFeatures.close();
		return neweditortool;
	}
	

}
