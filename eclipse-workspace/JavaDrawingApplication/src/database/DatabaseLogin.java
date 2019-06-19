/**
	 * This java class handles the database connectivity and importing and extracting of geometric features from and to the database. It uses a prepared statement to query features
	 * @return
	 * @throws SQLException
	 * @author amse1013
	 */

package database;

import javapackage.EditorTools;

import javapackage.PointFeature;
import javapackage.LineFeature;
import javapackage.TriangleFeature;
import javapackage.RectangleFeature;
import java.sql.*;

public class DatabaseLogin {
	
	public String DBMS;
	public String dbName;
	public String dbHost;
	public String dbPort;
	public String dbUser;
	public String dbPassword;	
	
	int countIdentifier = 0;
	
	/**
	 * Connect the java application with the MySQL database by giving database port user and password and database name 
	 * @return
	 * @throws SQLException
	 * @author amse1013
	 */
	public Connection accessorConnection() throws SQLException {
		String connection =  "jdbc:" + DBMS +"://" + dbHost + ":" + dbPort + "/" +	dbName + "?autoReconnect=true&useSSL=false&serverTimezone=UTC";
		System.out.println(connection);
		System.out.println(dbUser);
		System.out.println(dbPassword);
		Connection dbmsConnection = DriverManager.getConnection(connection, dbUser,  dbPassword);
		return dbmsConnection;
	}

	/**
	 * Inserting geometric features into the database table name: "geomfeatures" with information of feature/object type and feature/object geometry 
	 * @param editor
	 * @throws SQLException
	 * @author amse1013
	 */
	public void insertObjects(EditorTools editor) throws SQLException {
		Connection connection = accessorConnection();
		
		String objectType;
		String objectGeometry;
		
		for (PointFeature point : editor.drawingPoints) {
			objectType = point.getObjectType();
			objectGeometry = point.getGeometryAsText();
			PreparedStatement insertPointFeature = connection.prepareStatement("INSERT INTO geomfeatures"+
					"(type, geom) VALUES('" + objectType + "','" + objectGeometry + "')");
			insertPointFeature.executeUpdate();
		}
		
		for (LineFeature line : editor.drawingLines) {
			objectType = line.getObjectType();
			objectGeometry = line.getGeometryAsText();
			PreparedStatement insertToolLine = connection.prepareStatement("INSERT INTO geomfeatures"+
					"(type, geom) VALUES('" + objectType + "','" + objectGeometry + "')");
			insertToolLine.executeUpdate();
		}
		
		for (TriangleFeature triangle : editor.drawingTriangles) {
			objectType = triangle.getObjectType();
			objectGeometry = triangle.getGeometryAsText();
			PreparedStatement insertToolTriangle = connection.prepareStatement("INSERT INTO geomfeatures"+
					"(type, geom) VALUES('" + objectType + "','" + objectGeometry + "')");
			insertToolTriangle.executeUpdate();
		}
		
		for (RectangleFeature rectangle : editor.drawingRectangles) {
			objectType = rectangle.getObjectType();
			objectGeometry = rectangle.getGeometryAsText();
			PreparedStatement insertToolRectangle = connection.prepareStatement("INSERT INTO geomfeatures"+
					"(type, geom) VALUES('" + objectType + "','" + objectGeometry + "')");
			insertToolRectangle.executeUpdate();
		}
	}
	
	/**
	 * select geometric features from geomfeatures table based object type  
	 * @return
	 * @throws SQLException
	 * @author
	 */
	public EditorTools extractObjects() throws SQLException {
		EditorTools new_editor = new EditorTools();
		
		Connection connection = accessorConnection();
		java.sql.ResultSet resultSet;
		PreparedStatement displayObjects;
		
		displayObjects = connection.prepareStatement("SELECT * FROM geomfeatures");
		resultSet = displayObjects.executeQuery();
		
		while (resultSet.next()) {
			String objectType = resultSet.getString("type");
			String objectGeometry = resultSet.getString("geom");
			
			switch(objectType) {
			
				case "Point":
					PointFeature point = new PointFeature();
					point.setGeometryFromCSV(objectGeometry);
					new_editor.addPoints(point);
					
					countIdentifier++;
					point.ShapesId = countIdentifier;
					System.out.println(point.ShapesId);
					break;
					
				case "Line":
					LineFeature line = new LineFeature();
					line.setGeometryFromCSV(objectGeometry);
					new_editor.addLines(line);
					
					countIdentifier++;
				    line.ShapesId = countIdentifier;
				    System.out.println(line.ShapesId);
					break;
					
				case "Triangle":
					TriangleFeature triangle = new TriangleFeature();
					triangle.setGeometryFromCSV(objectGeometry);
					new_editor.addTriangles(triangle);
					
					countIdentifier++;
				    triangle.ShapesId = countIdentifier;
				    System.out.println(triangle.ShapesId);
					break;
					
				case "Rectangle":
					RectangleFeature rectangle = new RectangleFeature();
					rectangle.setGeometryFromCSV(objectGeometry);
					new_editor.addRectangles(rectangle);
					
					countIdentifier++;
				    rectangle.ShapesId = countIdentifier;
				    System.out.println(rectangle.ShapesId);
					break;
			}
			
		}
				
		countIdentifier = 0;
		
		resultSet.close();
		displayObjects.close();
		return new_editor;
	}
	

}
