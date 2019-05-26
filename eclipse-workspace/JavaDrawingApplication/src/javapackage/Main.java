

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javapackage;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.*;

import database.GUICSV;

/**
 * Creates the (Tool)Frame of the Software itself. The  is used as (GraphicalUser)Interface between the users actions
 * and the functionalities that are provided by this software.
 * @author 
 */
//@SuppressWarnings("serial")
public class Main extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Main as (GraphicalUser)Interface between user and software
	 * (invoked in main method)
	 */
	public static Main frame;
	/**
	 * Tooldrawingpanel
	 * for displaying geometries in the editor object
	 */
	public JPanel drawingcanvas;
	/**
	 * editor for storing and editing geometries
	 */
	public EditorTools editor;
	/**
	 * GUICSV for importing and exporting data from/to CSV
	 */
	public static GUICSV csv_gui;
	/**
	 * DBInterface for importing and exporting data from/to a Database
	 */
	//public static DBInterface dbinterface;

	//MenuBar Variables

	JMenuBar	menubar		 = new JMenuBar();
	JMenu		datamenu  = new JMenu("Data");
	JMenu		objmenu	 = new JMenu("Objects");
	JMenu		toolabout = new JMenu("About");
	JMenu		help 		= new JMenu("Help");
	
	JMenuItem 	obj1 = new JMenuItem("drawingPoints");
	JMenuItem	obj2 = new JMenuItem("drawingLines");
	JMenuItem	obj3 = new JMenuItem("drawingTriangles");
	JMenuItem 	obj4 = new JMenuItem("drawingRectangles");
	JMenuItem	csv = new JMenuItem("Import CSV");
	JMenuItem   db = new JMenuItem("Database Manager");
	JMenuItem   csvexport = new JMenuItem("Export CSV"); ;
	



	//ToolBar Variables
	/*JToolBar 	toolbar;
	JButton		drawingPoints;
	JButton		drawingLines;
	JButton		drawingTriangles;
	JButton		drawingRectangles;
	JButton		selectElements;
	JButton		deleteElements;
	JButton 	moveElements;
	JButton		changeElements;*/
	
	//Set Buttons for modifiable features
	/*JButton  drawingPoints 		= new JButton("Point");
	JButton	 drawingLines		= new JButton("Line");
	JButton	 drawingTriangles	= new JButton("Triangle");
	JButton	 drawingRectangles	= new JButton("Rectangle");*/
	
	//ToolBar contain editor tools
	JToolBar toolbar= new JToolBar();
	
	//Editor tools
	JButton	 selectElements	= new JButton("Select");
	JButton	 deleteElements = new JButton("Delete");
	JButton	 moveElements 	= new JButton("Move");
	JButton	 changeElements = new JButton("Change");

	Box toolBox;

	//Mouse Tracking Variables
	JLabel 	trackedCoord;
	int 		trackedX;
	int 		trackedY;

	//Mouse Selection Variables
	double selectionX1;
	double selectionY1;
	double selectionX2;
	double selectionY2;

	//DrawMode Tracking Variables
	JLabel 	trackedMode;

	Box trackingBox;

	//Functional Variables
	String drawMode = "default";
	boolean lineInitiated		= false;
	//Triangle
	boolean triangleInitiated1 	= false;
	boolean triangleInitiated2 	= false;
	//Rectangle
	boolean rectangleInitiated 	= false;
	//Selection
	boolean selectionInitiated 	= false;
	//Change
	boolean changeInitiated 	= false;

	//Movement& Change Variables
	boolean movementInitiated 	= false;
	boolean movingPoint 		= false;
	boolean movingLine 			= false;
	boolean movingTriangle 		= false;
	boolean movingRectangle 	= false;
	Ellipse2D selectionCircle;
	int ShapesId;
	int moveStartX;
	int moveStartY;
	int moveDestinationX;
	int moveDestinationY;
	boolean isStart = false;
	boolean isMiddle= false;
	boolean isEnd 	= false;
	Point2D point1;
	Point2D point2;
	Point2D point3;
	int objectArrayPosition;
	double x1;
	double y1;
	double x2;
	double y2;
	double x3;
	double y3;

	// Variables for ToolObjects
	PointFeature point;
	LineFeature line;
	TriangleFeature triangle;
	RectangleFeature rectangle;


	/**
	 * 
	 * ,
	 * as well as Listeners for clicking and moving the mouse.
	 * @author 
	 */
	public Main() {

        //Add items to main menu
		datamenu.add(csv);
		datamenu.add(db);
		
		objmenu.add(obj1);
		objmenu.add(obj2);
		objmenu.add(obj3);
		objmenu.add(obj4);
		
		menubar.add(datamenu);
		menubar.add(objmenu);
		menubar.add(toolabout);
		menubar.add(help);
		this.setJMenuBar(menubar);
		
		//Add menuitems into 'Object' menu
		/*objmenu.add(drawingPoints);
		objmenu.add(drawingLines);
		objmenu.add(drawingTriangles);
		objmenu.add(drawingRectangles);*/
		
		//toolbar.setPreferredSize(new Dimension(200, 500));
		//frame.add(toolbar, BorderLayout.EAST);
		toolBox = Box.createVerticalBox();
		toolBox.add(toolbar);
		add(toolBox);
		toolbar.add(selectElements);
		toolbar.add(deleteElements);
		toolbar.add(moveElements);
		toolbar.add(changeElements);
       
		


		//creates object of editor
		editor = new EditorTools();

		//creates object of Tooldrawingpanel
		drawingcanvas = new DrawingCanvas();
		drawingcanvas.setBackground(Color.white);
		drawingcanvas.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				point = new PointFeature();
				point.setPoint(e.getX(), e.getY());

				switch (drawMode) {

				case "PointMode":
					editor.addPoints(point);
					//drawingcanvas.requestObjectLists(editor);
					drawingcanvas.repaint();
					break;

				case "LineMode":
					if( lineInitiated == false) {
						line = new LineFeature();
						line.addLineStart(point);
						lineInitiated = true;
						break;

					} else if (lineInitiated == true) {
						line.addLineEnd(point);
						editor.addLines(line);
						((DrawingCanvas) drawingcanvas).requestObjectLists(editor);
						((DrawingCanvas) drawingcanvas).clearDrawingElements();
						drawingcanvas.repaint();
						lineInitiated = false;
						break;
					}

				case "TriangleMode":
					if ( triangleInitiated1 == false && triangleInitiated2 == false) {
						triangle = new TriangleFeature();
						triangle.addTriangleStart(point);
						triangleInitiated1 = true;
						break;

					} else if ( triangleInitiated1 == true && triangleInitiated2 == false) {
						triangle.addTriangleMid(point);
						triangleInitiated1 = false;
						triangleInitiated2 = true;
						break;

					} else if ( triangleInitiated1 == false && triangleInitiated2 == true) {
						triangle.addTriangleEnd(point);
						editor.addTriangles(triangle);
						//drawingcanvas.requestObjectLists(editor);
						((DrawingCanvas) drawingcanvas).clearDrawingElements();
						drawingcanvas.repaint();
						triangleInitiated1 = false;
						triangleInitiated2 = false;
						break;
					}

				case "RectangleMode":
					if ( rectangleInitiated == false) {
						rectangle = new RectangleFeature();
						rectangle.addRetangleFirstCorner(point);
						rectangleInitiated = true;
						break;

					} else if ( rectangleInitiated == true) {
						rectangle.addRectangleLastCorner(point);
						editor.addRectangles(rectangle);
						((DrawingCanvas) drawingcanvas).requestObjectLists(editor);
						((DrawingCanvas) drawingcanvas).clearDrawingElements();
						drawingcanvas.repaint();
						rectangleInitiated = false;
						break;
					}

				case "SelectMode":
					if ( selectionInitiated == false) {
						editor.clearCurrentSelection();
						//drawingcanvas.defineSelectionRectangle(null);
						//drawingcanvas.requestObjectLists(editor);
						drawingcanvas.repaint();

						selectionX1 = point.x;
						selectionY1 = point.y;
						selectionInitiated = true;
						break;

					} else if ( selectionInitiated == true) {
						selectionX2 = point.x;
						selectionY2 = point.y;

						//Determine 'height' and 'width' of the Selection Rectangle
						double rectangleWidth = Math.abs(selectionX1-selectionX2);
						double rectangleHeight = Math.abs(selectionY1-selectionY2);

						double rectangleStart;
						double rectangleEnd;

						//Determine 'top-left' and 'bottom-right' Point of the Selection Rectangle
						if( selectionX2 > selectionX1) {
							rectangleStart = selectionX1;
						} else {
							rectangleStart = selectionX2;
						}
						if( selectionY2 > selectionY1) {
							rectangleEnd = selectionY1;
						} else {
							rectangleEnd = selectionY2;
						}

						//Compose Selection Rectangle
						Rectangle2D selectionRectangle = new Rectangle2D.Double();
						selectionRectangle.setRect(rectangleStart, rectangleEnd, rectangleWidth, rectangleHeight);
						//drawingcanvas.defineSelectionRectangle(selectionRectangle);
						drawingcanvas.repaint();

						//editor.selectAffectedToolObjects(selectionRectangle);
						//drawingcanvas.requestToolObjectLists(editor);
						drawingcanvas.repaint();

						selectionInitiated = false;
						break;
					}

				case "MoveMode":

					if ( movementInitiated == false) {
						moveStartX = e.getX();
						moveStartY = e.getY();
						selectionCircle = new Ellipse2D.Double(e.getX()-13, e.getY()-13, 26, 26);

						//Moving Point 1
						editor.drawingPoints.forEach((PointFeature point) -> {
							if ( movingPoint == false) {
								point1 = new Point2D.Double(point.x, point.y);

								if (selectionCircle.contains(point1) && movingPoint == false) {
									x1 = point.x;
									y1 = point.y;
									ShapesId = point.getShapesId();
									movingPoint = true;
									movementInitiated = true;
								}
							}
						});

						//Moving Line 1
						editor.drawingLines.forEach((LineFeature line) -> {
							if ( movingLine == false && movingPoint == false) {
								point1 = new Point2D.Double(line.lineElements[0].x, line.lineElements[0].y);
								point2 = new Point2D.Double(line.lineElements[1].x, line.lineElements[1].y);

								if (selectionCircle.contains(point1) && movingLine == false) {
									x1 = line.lineElements[0].x;
									y1 = line.lineElements[0].y;
									x2 = line.lineElements[1].x;
									y2 = line.lineElements[1].y;
									ShapesId = line.getShapesId();
									movingLine = true;
									movementInitiated = true;
									isStart = true;

								} else if (selectionCircle.contains(point2) && movingLine == false) {
									x1 = line.lineElements[1].x;
									y1 = line.lineElements[1].y;
									x2 = line.lineElements[0].x;
									y2 = line.lineElements[0].y;
									ShapesId = line.getShapesId();
									movingLine = true;
									movementInitiated = true;
									isEnd = true;
								}
							}
						});

						//Moving Triangle 1
						editor.drawingTriangles.forEach((TriangleFeature triangle) -> {
							if ( movingTriangle == false && movingPoint == false && movingLine == false) {
								point1 = new Point2D.Double(triangle.triangleElements[0].x, triangle.triangleElements[0].y);
								point2 = new Point2D.Double(triangle.triangleElements[1].x, triangle.triangleElements[1].y);
								point3 = new Point2D.Double(triangle.triangleElements[2].x, triangle.triangleElements[2].y);

								if (selectionCircle.contains(point1) && movingTriangle == false) {
									x1 = triangle.triangleElements[0].x;
									y1 = triangle.triangleElements[0].y;
									x2 = triangle.triangleElements[1].x;
									y2 = triangle.triangleElements[1].y;
									x3 = triangle.triangleElements[2].x;
									y3 = triangle.triangleElements[2].y;
									ShapesId = triangle.getShapesId();
									movingTriangle = true;
									movementInitiated = true;
									isStart = true;

								} else if (selectionCircle.contains(point2) && movingTriangle == false) {
									x1 = triangle.triangleElements[1].x;
									y1 = triangle.triangleElements[1].y;
									x2 = triangle.triangleElements[2].x;
									y2 = triangle.triangleElements[2].y;
									x3 = triangle.triangleElements[0].x;
									y3 = triangle.triangleElements[0].y;
									ShapesId = triangle.getShapesId();
									movingTriangle = true;
									movementInitiated = true;
									isMiddle = true;

								} else if (selectionCircle.contains(point3) && movingTriangle == false) {
									x1 = triangle.triangleElements[0].x;
									y1 = triangle.triangleElements[0].y;
									x2 = triangle.triangleElements[1].x;
									y2 = triangle.triangleElements[1].y;
									x3 = triangle.triangleElements[2].x;
									y3 = triangle.triangleElements[2].y;
									ShapesId = triangle.getShapesId();
									movingTriangle = true;
									movementInitiated = true;
									isEnd = true;

								}
							}
						});

						//Moving Rectangles 1
						editor.drawingRectangles.forEach((RectangleFeature rectangle) -> {
							if ( movingRectangle == false && movingTriangle == false && movingPoint == false && movingLine == false) {
								point1 = new Point2D.Double(rectangle.rectangleElements[0].x, rectangle.rectangleElements[0].y);
								point2 = new Point2D.Double(rectangle.rectangleElements[1].x, rectangle.rectangleElements[1].y);

								if (selectionCircle.contains(point1) && movingRectangle == false) {
									x1 = rectangle.rectangleElements[0].x;
									y1 = rectangle.rectangleElements[0].y;
									x2 = rectangle.rectangleElements[1].x;
									y2 = rectangle.rectangleElements[1].y;
									ShapesId = rectangle.getShapesId();
									movingRectangle = true;
									movementInitiated = true;
									isStart = true;

								} else if ( selectionCircle.contains(point2) && movingRectangle == false){
									x1 = rectangle.rectangleElements[1].x;
									y1 = rectangle.rectangleElements[1].y;
									x2 = rectangle.rectangleElements[0].x;
									y2 = rectangle.rectangleElements[0].y;
									ShapesId = rectangle.getShapesId();
									movingRectangle = true;
									movementInitiated = true;
									isEnd = true;
								}
							}
						});
						break;


					} else if ( movementInitiated == true) {
						int moveDestinationX = e.getX();
						int moveDestinationY = e.getY();
						int moveDifferenceX = moveStartX - moveDestinationX;
						int moveDifferenceY = moveStartY - moveDestinationY;

						//Moving Point 2
						if ( movingPoint == true) {
							for ( int i=0; i<editor.drawingPoints.size(); i++) {
								int matchIdentifier = editor.drawingPoints.get(i).ShapesId;

								if (ShapesId == matchIdentifier){
									objectArrayPosition = i;
									editor.drawingPoints.get(i).x = moveDestinationX;
									editor.drawingPoints.get(i).y = moveDestinationY;
									//drawingcanvas.requestToolObjectLists(editor);
									drawingcanvas.repaint();
									movingPoint = false;
									movementInitiated = false;
								}
							}

						}

						//Moving Line 2
						if ( movingLine == true) {
							for (int i = 0; i<editor.drawingLines.size(); i++) {
								int matchIdentifier = editor.drawingLines.get(i).ShapesId;

								if (ShapesId == matchIdentifier) {
									objectArrayPosition = i;
									editor.drawingLines.get(i).lineElements[0].x = x1 - moveDifferenceX;
									editor.drawingLines.get(i).lineElements[0].y = y1 - moveDifferenceY;
									editor.drawingLines.get(i).lineElements[1].x = x2 - moveDifferenceX;
									editor.drawingLines.get(i).lineElements[1].y = y2 - moveDifferenceY;
									//drawingcanvas.requestToolObjectLists(editor);
									((DrawingCanvas) drawingcanvas).clearDrawingElements();
									drawingcanvas.repaint();
									movingLine = false;
									movementInitiated = false;

								}
							}

						}

						//Moving Triangle 2
						if ( movingTriangle == true) {
							for (int i = 0; i<editor.drawingTriangles.size(); i++) {
								int matchIdentifier = editor.drawingTriangles.get(i).ShapesId;

								if (ShapesId == matchIdentifier) {
									objectArrayPosition = i;
									editor.drawingTriangles.get(i).triangleElements[0].x = x1 - moveDifferenceX;
									editor.drawingTriangles.get(i).triangleElements[0].y = y1 - moveDifferenceY;
									editor.drawingTriangles.get(i).triangleElements[1].x = x2 - moveDifferenceX;
									editor.drawingTriangles.get(i).triangleElements[1].y = y2 - moveDifferenceY;
									editor.drawingTriangles.get(i).triangleElements[2].x = x3 - moveDifferenceX;
									editor.drawingTriangles.get(i).triangleElements[2].y = y3 - moveDifferenceY;

									//drawingcanvas.requestToolObjectLists(editor);
									drawingcanvas.repaint();
									movingTriangle = false;
									movementInitiated = false;

								}
							}

						}

						//Moving Rectangles 2
						if ( movingRectangle == true) {
							for ( int i=0; i<editor.drawingRectangles.size(); i++) {
								int matchIdentifier = editor.drawingRectangles.get(i).ShapesId;

								if (ShapesId == matchIdentifier){
									objectArrayPosition = i;
									editor.drawingRectangles.get(i).rectangleElements[0].x = x1 - moveDifferenceX;
									editor.drawingRectangles.get(i).rectangleElements[0].y = y1 - moveDifferenceY;
									editor.drawingRectangles.get(i).rectangleElements[1].x = x2 - moveDifferenceX;
									editor.drawingRectangles.get(i).rectangleElements[1].y = y2 - moveDifferenceY;
								}
							}
							//drawingcanvas.requestToolObjectLists(editor);
							drawingcanvas.repaint();
							movingRectangle = false;
							movementInitiated = false;
						}
						isStart = false;
						isMiddle = false;
						isEnd = false;
						break;
					}

				case "ChangeMode":
					moveStartX = e.getX();
					moveStartY = e.getY();

					if ( changeInitiated == false) {
						selectionCircle = new Ellipse2D.Double(e.getX()-13, e.getY()-13, 26, 26);

						//Changing Points
						editor.drawingPoints.forEach((PointFeature point) -> {
							if ( movingPoint == false) {
								point1 = new Point2D.Double(point.x, point.y);

								if (selectionCircle.contains(point1) && movingPoint == false) {
									x1 = point.x;
									y1 = point.y;
									ShapesId = point.getShapesId();
									movingPoint = true;
									changeInitiated = true;
								}
							}
						});

						//Changing Line
						editor.drawingLines.forEach((LineFeature line) -> {
							if ( movingLine == false && movingPoint == false) {
								point1 = new Point2D.Double(line.lineElements[0].x, line.lineElements[0].y);
								point2 = new Point2D.Double(line.lineElements[1].x, line.lineElements[1].y);

								if (selectionCircle.contains(point1) && movingLine == false) {
									ShapesId = line.getShapesId();
									isStart = true;
									movingLine = true;
									changeInitiated = true;

								} else if (selectionCircle.contains(point2) && movingLine == false) {
									ShapesId = line.getShapesId();
									isStart = false;
									isEnd = true;
									movingLine = true;
									changeInitiated = true;
								}
							}
						});

						//Changing Triangle
						editor.drawingTriangles.forEach((TriangleFeature triangle) -> {
							if ( movingLine == false && movingPoint == false && movingTriangle== false) {
								point1 = new Point2D.Double(triangle.triangleElements[0].x, triangle.triangleElements[0].y);
								point2 = new Point2D.Double(triangle.triangleElements[1].x, triangle.triangleElements[1].y);
								point3 = new Point2D.Double(triangle.triangleElements[2].x, triangle.triangleElements[2].y);

								if (selectionCircle.contains(point1) && movingTriangle == false) {
									ShapesId = triangle.getShapesId();
									isStart = true;
									movingTriangle = true;
									changeInitiated = true;

								} else if (selectionCircle.contains(point2) && movingTriangle == false) {
									ShapesId = triangle.getShapesId();
									isMiddle = true;
									movingTriangle = true;
									changeInitiated = true;

								} else if (selectionCircle.contains(point3) && movingTriangle == false) {
									ShapesId = triangle.getShapesId();
									isEnd = true;
									movingTriangle = true;
									changeInitiated = true;
								}
							}
						});

						editor.drawingRectangles.forEach((RectangleFeature rectangle) -> {
							if ( movingLine == false && movingPoint == false && movingTriangle== false && movingRectangle == false) {
								point1 = new Point2D.Double(rectangle.rectangleElements[0].x, rectangle.rectangleElements[0].y);
								point2 = new Point2D.Double(rectangle.rectangleElements[1].x, rectangle.rectangleElements[1].y);

								if (selectionCircle.contains(point1) && movingRectangle == false) {
									ShapesId = rectangle.getShapesId();
									isStart = true;
									movingRectangle = true;
									changeInitiated = true;

								} else if (selectionCircle.contains(point2) && movingRectangle == false) {
									ShapesId = rectangle.getShapesId();
									isEnd = true;
									movingRectangle = true;
									changeInitiated = true;
								}
							}
						});
						break;
					}

					if ( changeInitiated == true) {
						int moveDestinationX = e.getX();
						int moveDestinationY = e.getY();

						if ( movingPoint == true) {
							for ( int i=0; i<editor.drawingPoints.size(); i++) {
								int matchIdentifier = editor.drawingPoints.get(i).ShapesId;

								if (ShapesId == matchIdentifier){
									objectArrayPosition = i;
									editor.drawingPoints.get(i).x = moveStartX;
									editor.drawingPoints.get(i).y = moveStartY;

									//drawingcanvas.requestToolObjectLists(editor);
									drawingcanvas.repaint();
									movingPoint = false;
									changeInitiated = false;
								}
							}

						}

						if (movingLine == true) {
							if ( isStart == true) {
								for (int i = 0; i<editor.drawingLines.size(); i++) {
									int matchIdentifier = editor.drawingLines.get(i).ShapesId;

									if (ShapesId == matchIdentifier) {
										objectArrayPosition = i;
										editor.drawingLines.get(i).lineElements[0].x = moveDestinationX;
										editor.drawingLines.get(i).lineElements[0].y = moveDestinationY;

										//drawingcanvas.requestToolObjectLists(editor);
										((DrawingCanvas) drawingcanvas).clearDrawingElements();
										drawingcanvas.repaint();
										movingLine = false;
										changeInitiated = false;
									}

								}
							}

							if (movingLine == true) {

								if ( isStart == false) {
									for (int i = 0; i<editor.drawingLines.size(); i++) {
										int matchIdentifier = editor.drawingLines.get(i).ShapesId;

										if (ShapesId == matchIdentifier) {
											objectArrayPosition = i;
											editor.drawingLines.get(i).lineElements[1].x = moveDestinationX;
											editor.drawingLines.get(i).lineElements[1].y = moveDestinationY;

											((DrawingCanvas) drawingcanvas).requestObjectLists(editor);
											((DrawingCanvas) drawingcanvas).clearDrawingElements();
											drawingcanvas.repaint();
											movingLine = false;
											changeInitiated = false;
										}
									}
								}

							}
						}

						if (movingTriangle == true) {

							if (isStart == true && isMiddle == false && isEnd == false) {
								for (int i = 0; i<editor.drawingTriangles.size(); i++) {
									int matchIdentifier = editor.drawingTriangles.get(i).ShapesId;

									if (ShapesId == matchIdentifier) {
										objectArrayPosition = i;
										editor.drawingTriangles.get(i).triangleElements[0].x = moveDestinationX;
										editor.drawingTriangles.get(i).triangleElements[0].y = moveDestinationY;

										//drawingcanvas.requestToolObjectLists(editor);
										drawingcanvas.repaint();
										movingTriangle = false;
										changeInitiated = false;

									}
								}
							}

							if (isMiddle == true && isStart == false && isEnd == false) {
								for (int i = 0; i<editor.drawingTriangles.size(); i++) {
									int matchIdentifier = editor.drawingTriangles.get(i).ShapesId;

									if (ShapesId == matchIdentifier) {
										objectArrayPosition = i;
										editor.drawingTriangles.get(i).triangleElements[1].x = moveDestinationX;
										editor.drawingTriangles.get(i).triangleElements[1].y = moveDestinationY;

										//drawingcanvas.requestToolObjectLists(editor);
										drawingcanvas.repaint();
										movingTriangle = false;
										changeInitiated = false;
									}
								}
							}

							if (isEnd == true && isMiddle == false && isStart == false) {
								for (int i = 0; i<editor.drawingTriangles.size(); i++) {
									int matchIdentifier = editor.drawingTriangles.get(i).ShapesId;

									if (ShapesId == matchIdentifier) {
										objectArrayPosition = i;
										editor.drawingTriangles.get(i).triangleElements[2].x = moveDestinationX;
										editor.drawingTriangles.get(i).triangleElements[2].y = moveDestinationY;

										//drawingcanvas.requestToolObjectLists(editor);
										drawingcanvas.repaint();
										movingTriangle = false;
										changeInitiated = false;
									}
								}
							}
						}

						if ( movingRectangle == true) {

							if (isStart == true && isEnd == false) {
								for ( int i=0; i<editor.drawingRectangles.size(); i++) {
									int matchIdentifier = editor.drawingRectangles.get(i).ShapesId;

									if (ShapesId == matchIdentifier){
										objectArrayPosition = i;
										editor.drawingRectangles.get(i).rectangleElements[0].x = moveDestinationX;
										editor.drawingRectangles.get(i).rectangleElements[0].y = moveDestinationY;
									}
								}
								//drawingcanvas.requestToolObjectLists(editor);
								drawingcanvas.repaint();
								movingRectangle = false;
								changeInitiated = false;
							}

							if (isEnd == true && isStart == false) {
								for ( int i=0; i<editor.drawingRectangles.size(); i++) {
									int matchIdentifier = editor.drawingRectangles.get(i).ShapesId;

									if (ShapesId == matchIdentifier){
										objectArrayPosition = i;
										editor.drawingRectangles.get(i).rectangleElements[1].x = moveDestinationX;
										editor.drawingRectangles.get(i).rectangleElements[1].y = moveDestinationY;

									}
									//drawingcanvas.requestToolObjectLists(editor);
									drawingcanvas.repaint();
									movingRectangle = false;
									changeInitiated = false;
								}

							}

						}

						isStart = false;
						isMiddle = false;
						isEnd = false;
					}
				}
			}
		});

		drawingcanvas.addMouseMotionListener(new MouseAdapter() {

			//Live display when creating or editing displayed objects (for better user feedback only)
			@Override
			public void mouseMoved(MouseEvent e) {
				trackedX = e.getX();
				trackedY = e.getY();
				trackedCoord.setText("X: " + trackedX + "    Y: " + trackedY + "   ");
				point = new PointFeature();
				point.setPoint(e.getX(), e.getY());

				if ( lineInitiated == true) {

					/*
					 * DEPRECATED VERSION - see Module Integration Test
                            if ( editor.drawingLines.size() >=1) {
                            editor.drawingLines.remove(editor.drawingLines.size()-1);
                            }

                            line.addLineEnd(point);
                            editor.storeLineElements(line);
                            drawingcanvas
                            .requestToolObjectLists(editor);
                            drawingcanvas
                            .repaint();
					 */

					line.addLineEnd(point);
					((DrawingCanvas) drawingcanvas).storeDrawingLineElements(line);
					drawingcanvas.repaint();
				}

				if ( triangleInitiated1 == true && triangleInitiated2 == false) {

					/*
					 * DEPRECATED VERSION - see Module Integration Test
                            if ( editor.drawingTriangles.size() >=1) {
                            editor.drawingTriangles.remove(editor.drawingTriangles.size()-1);
                            }

                            triangle.addTriangleMid(point);
                            triangle.addTriangleEnd(point);
                            editor.storeTriangleElements(triangle);
                            drawingcanvas
                            .requestToolObjectLists(editor);
                            drawingcanvas
                            .repaint();
					 */

					triangle.addTriangleMid(point);
					triangle.addTriangleEnd(point);
					//drawingcanvas.storeDrawingTriangleElements(triangle);
					drawingcanvas
					.repaint();
				}

				if ( triangleInitiated2 == true && triangleInitiated1 == false) {

					/*
					 * DEPRECATED VERSION - see Module Integration Test
                            if ( editor.drawingTriangles.size() >=1) {
                            editor.drawingTriangles.remove(editor.drawingTriangles.size()-1);
                            }

                            if ( triangle.triangleElements[2] != null) {
                            triangle.triangleElements[2] = null;
                            }
                            triangle.addTriangleEnd(point);
                            editor.storeTriangleElements(triangle);
                            drawingcanvas
                            .requestToolObjectLists(editor);
                            drawingcanvas
                            .repaint();
					 */

					triangle.addTriangleEnd(point);
					((DrawingCanvas) drawingcanvas).storeDrawingTriangleElements(triangle);
					drawingcanvas.repaint();

				}

				if ( rectangleInitiated == true) {

					/*
					 * DEPRECATED VERSION - see Module Integration Test
                            if ( editor.drawingRectangles.size() >=1) {
                            editor.drawingRectangles.remove(editor.drawingRectangles.size()-1);
                            }

                            rectangle.addRectangleLastCorner(point);
                            editor.storeRectangleElements(rectangle);
                            drawingcanvas
                            .requestToolObjectLists(editor);
                            drawingcanvas
                            .repaint();
					 */

					rectangle.addRectangleLastCorner(point);
					//drawingcanvas.storeDrawingRectangleElements(rectangle);
					drawingcanvas
					.repaint();

				}

				if ( selectionInitiated == true) {
					selectionX2 = point.x;
					selectionY2 = point.y;

					//Determine 'height' and 'width' of the Selection Rectangle
					double rectangleWidth = Math.abs(selectionX1-selectionX2);
					double rectangleHeight = Math.abs(selectionY1-selectionY2);

					double rectangleStart;
					double rectangleEnd;

					//Determine 'top-left' and 'bottom-right' Point of the Selection Rectangle
					if( selectionX2 > selectionX1) {
						rectangleStart = selectionX1;
					} else {
						rectangleStart = selectionX2;
					}
					if( selectionY2 > selectionY1) {
						rectangleEnd = selectionY1;
					} else {
						rectangleEnd = selectionY2;
					}

					//Compose Selection Rectangle
					Rectangle2D selectionRectangle = new Rectangle2D.Double();
					selectionRectangle.setRect(rectangleStart, rectangleEnd, rectangleWidth, rectangleHeight);
					//drawingcanvas.defineSelectionRectangle(selectionRectangle);
					//drawingcanvas.requestToolObjectLists(editor);
					drawingcanvas.repaint();
				}

				/*
				 * Live display of 'move'-Tool starting
				 * [Added in the course of the Module Integration Test]
				 * @author 
				 */
				if ( movementInitiated == true && changeInitiated == false) {

					if (movingPoint == true) {
						for (int i = 0; i<editor.drawingPoints.size(); i++) {
							int matchIdentifier = editor.drawingPoints.get(i).ShapesId;

							if (ShapesId == matchIdentifier) {
								editor.drawingPoints.get(i).x = point.x;
								editor.drawingPoints.get(i).y = point.y;

								//drawingcanvas.requestToolObjectLists(editor);
								drawingcanvas.repaint();
							}
						}
					}

					if ( movingLine == true) {
						//If selected line point was the first line element
						if (isStart == true && isEnd == false) {
							for (int i = 0; i<editor.drawingLines.size(); i++) {
								int matchIdentifier = editor.drawingLines.get(i).ShapesId;

								if (ShapesId == matchIdentifier) {
									double diffX = editor.drawingLines.get(i).lineElements[0].x - editor.drawingLines.get(i).lineElements[1].x;
									double diffY = editor.drawingLines.get(i).lineElements[0].y - editor.drawingLines.get(i).lineElements[1].y;

									editor.drawingLines.get(i).addLineStart(point);
									PointFeature newPoint = new PointFeature();
									newPoint.x = editor.drawingLines.get(i).lineElements[0].x - diffX;
									newPoint.y = editor.drawingLines.get(i).lineElements[0].y - diffY;
									editor.drawingLines.get(i).addLineEnd(newPoint);

									//drawingcanvas.requestToolObjectLists(editor);
									drawingcanvas
									.repaint();
								}
							}
						}

						//If selected line point was the last line element
						if (isStart == false && isEnd == true) {
							for (int i = 0; i<editor.drawingLines.size(); i++) {
								int matchIdentifier = editor.drawingLines.get(i).ShapesId;

								if (ShapesId == matchIdentifier) {
									double diffX = editor.drawingLines.get(i).lineElements[0].x - editor.drawingLines.get(i).lineElements[1].x;
									double diffY = editor.drawingLines.get(i).lineElements[0].y - editor.drawingLines.get(i).lineElements[1].y;

									editor.drawingLines.get(i).addLineEnd(point);
									PointFeature newPoint = new PointFeature();
									newPoint.x = editor.drawingLines.get(i).lineElements[1].x + diffX;
									newPoint.y = editor.drawingLines.get(i).lineElements[1].y + diffY;
									editor.drawingLines.get(i).addLineStart(newPoint);

									//drawingcanvas.requestToolObjectLists(editor);
									drawingcanvas.repaint();
								}
							}
						}

					}

					if ( movingTriangle == true) {
						//If selected triangle point was the first triangle element
						if ( isStart == true && isMiddle == false && isEnd == false) {

							for (int i = 0; i<editor.drawingTriangles.size(); i++) {
								int matchIdentifier = editor.drawingTriangles.get(i).ShapesId;

								if (ShapesId == matchIdentifier) {
									double diffX1 = editor.drawingTriangles.get(i).triangleElements[0].x - editor.drawingTriangles.get(i).triangleElements[1].x;
									double diffY1 = editor.drawingTriangles.get(i).triangleElements[0].y - editor.drawingTriangles.get(i).triangleElements[1].y;
									double diffX2 = editor.drawingTriangles.get(i).triangleElements[0].x - editor.drawingTriangles.get(i).triangleElements[2].x;
									double diffY2 = editor.drawingTriangles.get(i).triangleElements[0].y - editor.drawingTriangles.get(i).triangleElements[2].y;

									editor.drawingTriangles.get(i).addTriangleStart(point);
									PointFeature newPoint1 = new PointFeature();
									PointFeature newPoint2 = new PointFeature();
									newPoint1.x = editor.drawingTriangles.get(i).triangleElements[0].x - diffX1;
									newPoint1.y = editor.drawingTriangles.get(i).triangleElements[0].y - diffY1;
									newPoint2.x = editor.drawingTriangles.get(i).triangleElements[0].x - diffX2;
									newPoint2.y = editor.drawingTriangles.get(i).triangleElements[0].y - diffY2;
									editor.drawingTriangles.get(i).addTriangleMid(newPoint1);
									editor.drawingTriangles.get(i).addTriangleEnd(newPoint2);

									//drawingcanvas.requestToolObjectLists(editor);
									drawingcanvas.repaint();

								}
							}
						}

						//If selected triangle point was the middle triangle element
						if ( isStart == false && isMiddle == true && isEnd == false) {

							for (int i = 0; i<editor.drawingTriangles.size(); i++) {
								int matchIdentifier = editor.drawingTriangles.get(i).ShapesId;

								if (ShapesId == matchIdentifier) {
									double diffX1 = editor.drawingTriangles.get(i).triangleElements[1].x - editor.drawingTriangles.get(i).triangleElements[0].x;
									double diffY1 = editor.drawingTriangles.get(i).triangleElements[1].y - editor.drawingTriangles.get(i).triangleElements[0].y;
									double diffX2 = editor.drawingTriangles.get(i).triangleElements[1].x - editor.drawingTriangles.get(i).triangleElements[2].x;
									double diffY2 = editor.drawingTriangles.get(i).triangleElements[1].y - editor.drawingTriangles.get(i).triangleElements[2].y;

									editor.drawingTriangles.get(i).addTriangleMid(point);
									PointFeature newPoint1 = new PointFeature();
									PointFeature newPoint2 = new PointFeature();
									newPoint1.x = editor.drawingTriangles.get(i).triangleElements[1].x - diffX1;
									newPoint1.y = editor.drawingTriangles.get(i).triangleElements[1].y - diffY1;
									newPoint2.x = editor.drawingTriangles.get(i).triangleElements[1].x - diffX2;
									newPoint2.y = editor.drawingTriangles.get(i).triangleElements[1].y - diffY2;
									editor.drawingTriangles.get(i).addTriangleStart(newPoint1);
									editor.drawingTriangles.get(i).addTriangleEnd(newPoint2);

									//drawingcanvas.requestToolObjectLists(editor);
									drawingcanvas.repaint();

								}
							}
						}

						//If selected triangle point was the last triangle element
						if ( isStart == false && isMiddle == false && isEnd == true) {

							for (int i = 0; i<editor.drawingTriangles.size(); i++) {
								int matchIdentifier = editor.drawingTriangles.get(i).ShapesId;

								if (ShapesId == matchIdentifier) {
									double diffX1 = editor.drawingTriangles.get(i).triangleElements[2].x - editor.drawingTriangles.get(i).triangleElements[0].x;
									double diffY1 = editor.drawingTriangles.get(i).triangleElements[2].y - editor.drawingTriangles.get(i).triangleElements[0].y;
									double diffX2 = editor.drawingTriangles.get(i).triangleElements[2].x - editor.drawingTriangles.get(i).triangleElements[1].x;
									double diffY2 = editor.drawingTriangles.get(i).triangleElements[2].y - editor.drawingTriangles.get(i).triangleElements[1].y;

									editor.drawingTriangles.get(i).addTriangleEnd(point);
									PointFeature newPoint1 = new PointFeature();
									PointFeature newPoint2 = new PointFeature();
									newPoint1.x = editor.drawingTriangles.get(i).triangleElements[2].x - diffX1;
									newPoint1.y = editor.drawingTriangles.get(i).triangleElements[2].y - diffY1;
									newPoint2.x = editor.drawingTriangles.get(i).triangleElements[2].x - diffX2;
									newPoint2.y = editor.drawingTriangles.get(i).triangleElements[2].y - diffY2;
									editor.drawingTriangles.get(i).addTriangleMid(newPoint1);
									editor.drawingTriangles.get(i).addTriangleStart(newPoint2);

									//drawingcanvas.requestToolObjectLists(editor);
									drawingcanvas.repaint();

								}
							}
						}
					}

					if (movingRectangle == true) {

						//If selected rectangle point was the first corner of the rectangle element
						if (isStart == true && isEnd == false) {
							for ( int i=0; i<editor.drawingRectangles.size(); i++) {
								int matchIdentifier = editor.drawingRectangles.get(i).ShapesId;

								if (ShapesId == matchIdentifier){
									double diffX1 = editor.drawingRectangles.get(i).rectangleElements[0].x - editor.drawingRectangles.get(i).rectangleElements[1].x;
									double diffY1 = editor.drawingRectangles.get(i).rectangleElements[0].y - editor.drawingRectangles.get(i).rectangleElements[1].y;

									editor.drawingRectangles.get(i).addRetangleFirstCorner(point);
									PointFeature newPoint = new PointFeature();
									newPoint.x = editor.drawingRectangles.get(i).rectangleElements[0].x - diffX1;
									newPoint.y = editor.drawingRectangles.get(i).rectangleElements[0].y - diffY1;
									editor.drawingRectangles.get(i).addRectangleLastCorner(newPoint);

									//drawingcanvas.requestToolObjectLists(editor);
									drawingcanvas.repaint();
								}
							}
						}

						//If selected rectangle point was the last corner of the rectangle element
						if (isStart == false && isEnd == true) {
							for ( int i=0; i<editor.drawingRectangles.size(); i++) {
								int matchIdentifier = editor.drawingRectangles.get(i).ShapesId;

								if (ShapesId == matchIdentifier){
									double diffX1 = editor.drawingRectangles.get(i).rectangleElements[1].x - editor.drawingRectangles.get(i).rectangleElements[0].x;
									double diffY1 = editor.drawingRectangles.get(i).rectangleElements[1].y - editor.drawingRectangles.get(i).rectangleElements[0].y;

									editor.drawingRectangles.get(i).addRectangleLastCorner(point);
									PointFeature newPoint = new PointFeature();
									newPoint.x = editor.drawingRectangles.get(i).rectangleElements[1].x - diffX1;
									newPoint.y = editor.drawingRectangles.get(i).rectangleElements[1].y - diffY1;
									editor.drawingRectangles.get(i).addRetangleFirstCorner(newPoint);

									//drawingcanvas.requestToolObjectLists(editor);
									drawingcanvas.repaint();
								}
							}
						}
					}

				}

				/*
				 * Live display of 'change'-Tool starting
				 * [Added in the course of the Module Integration Test]
				 * @author 
				 */
				if ( changeInitiated == true && movementInitiated == false) {

					if (movingPoint == true) {
						for (int i = 0; i<editor.drawingPoints.size(); i++) {
							int matchIdentifier = editor.drawingPoints.get(i).ShapesId;

							if (ShapesId == matchIdentifier) {
								editor.drawingPoints.get(i).x = point.x;
								editor.drawingPoints.get(i).y = point.y;
								//drawingcanvas.requestToolObjectLists(editor);
								drawingcanvas.repaint();
							}
						}
					}

					if (movingLine == true) {
						//If selected line point was the first line element
						if ( isStart == true && isEnd == false) {
							for (int i = 0; i<editor.drawingLines.size(); i++) {
								int matchIdentifier = editor.drawingLines.get(i).ShapesId;

								if (ShapesId == matchIdentifier) {
									editor.drawingLines.get(i).addLineStart(point);
									//drawingcanvas.requestToolObjectLists(editor);
									drawingcanvas.repaint();
								}
							}
						}

						//If selected line point was the last line element
						if ( isEnd == true && isStart == false) {
							for (int i = 0; i<editor.drawingLines.size(); i++) {
								int matchIdentifier = editor.drawingLines.get(i).ShapesId;

								if (ShapesId == matchIdentifier) {
									editor.drawingLines.get(i).addLineEnd(point);
									//drawingcanvas.requestToolObjectLists(editor);
									drawingcanvas.repaint();
								}
							}
						}
					}

					if (movingTriangle == true) {
						//If selected triangle point was the first triangle element
						if ( isStart == true && isMiddle == false && isEnd == false) {
							for (int i = 0; i<editor.drawingTriangles.size(); i++) {
								int matchIdentifier = editor.drawingTriangles.get(i).ShapesId;

								if (ShapesId == matchIdentifier) {
									editor.drawingTriangles.get(i).addTriangleStart(point);
									//drawingcanvas.requestToolObjectLists(editor);
									drawingcanvas.repaint();

								}
							}
						}

						//If selected triangle point was the middle triangle element
						if ( isStart == false && isMiddle == true && isEnd == false) {
							for (int i = 0; i<editor.drawingTriangles.size(); i++) {
								int matchIdentifier = editor.drawingTriangles.get(i).ShapesId;

								if (ShapesId == matchIdentifier) {
									editor.drawingTriangles.get(i).addTriangleMid(point);

									//drawingcanvas.requestToolObjectLists(editor);
									drawingcanvas.repaint();

								}
							}
						}

						//If selected triangle point was the last triangle element
						if ( isStart == false && isMiddle == false && isEnd == true) {
							for (int i = 0; i<editor.drawingTriangles.size(); i++) {
								int matchIdentifier = editor.drawingTriangles.get(i).ShapesId;

								if (ShapesId == matchIdentifier) {

									editor.drawingTriangles.get(i).addTriangleEnd(point);

									//drawingcanvas.requestToolObjectLists(editor);
									drawingcanvas.repaint();

								}
							}
						}
					}

					if (movingRectangle == true) {
						//If selected rectangle point was the first corner of rectangle element
						if (isStart == true && isEnd == false) {
							for ( int i=0; i<editor.drawingRectangles.size(); i++) {
								int matchIdentifier = editor.drawingRectangles.get(i).ShapesId;

								if (ShapesId == matchIdentifier){
									editor.drawingRectangles.get(i).addRetangleFirstCorner(point);

									//drawingcanvas.requestToolObjectLists(editor);
									drawingcanvas.repaint();
								}
							}
						}

						//If selected rectangle point was the last corner of rectangle element
						if (isStart == false && isEnd == true) {
							for ( int i=0; i<editor.drawingRectangles.size(); i++) {
								int matchIdentifier = editor.drawingRectangles.get(i).ShapesId;

								if (ShapesId == matchIdentifier){
									editor.drawingRectangles.get(i).addRectangleLastCorner(point);

									//drawingcanvas.requestToolObjectLists(editor);
									drawingcanvas.repaint();
								}
							}
						}
					}
				}
			}
		});
	}


	/**
	 * Creates a new object of GUI
() which opens a new frame for
	 * exporting objects as ".csv" or importing objects from a ".csv".
	 * After that, some general methods for displaying a frame are applied.
	 * @author 
	 */
	/*public void openCSVgui() {
		csv_gui = new GUICSV();
		csv_gui.setTitle("CSV Interface");
		csv_gui.setLocationRelativeTo(null);
		csv_gui.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		csv_gui.setVisible(true);

	}*/

	/*
	/**
	 * Creates a new object of DBInterface() which opens a new frame for
	 * exporting objects to a connected database or importing objects from a connected database.
	 * After that, some general methods for displaying a frame are applied.
	 * @author 
	 */
	/*public void openDbInterface() {
		dbinterface = new DBInterface();
		dbinterface.setTitle("DB Interface");
		dbinterface.setLocationRelativeTo(null);
		dbinterface.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		dbinterface.setVisible(true);

	}*/

	/**
	 * Replaces the @param editor with the contents of the @param neweditor
	 * @author 
	 * @param neweditor
	 */
	public void overwriteObjects(EditorTools neweditor) {
		this.editor = neweditor;
		//drawingcanvas.requestToolObjectLists(editor);
		drawingcanvas.repaint();
	}

	/**
	 * Sets the Tooldrawingpanel
	 * as ContentPane.
	 * Adds the MenuBar and the ToolBar including the corresponding items and
	 * defines the appearance of these elements.
	 * Applies general methods for displaying a frame.
	 * @author 
	 */
	public void setLayout() {

		//Set ContentPane
		setContentPane(drawingcanvas
				);

		//Set MenuBar
		/*menubar 	= new JMenuBar();
		datamenu 	= new JMenu("Data");
		toolabout 	= new JMenu("About");
		help 	= new JMenu("Help");
		csv 		= new JMenuItem("Import CSV");
		csvexport = new JMenuItem("Export as CSV");
		db			= new JMenuItem("Database Manager");*/


		/*datamenu.add(csv);
		datamenu.add(csvexport);
		datamenu.add(db);
		menubar.add(datamenu);
		menubar.add(toolabout);
		setJMenuBar(menubar);*/

		//Coordinate Tracking
		menubar.add(Box.createHorizontalGlue());
		trackedCoord = new JLabel("[mouse deactivated]");
		trackedMode	 = new JLabel("[mode deactivated]    |    ");
		menubar.add(trackedMode);
		menubar.add(trackedCoord);

		trackedCoord.setHorizontalAlignment(JLabel.RIGHT);

		//Set Buttons
		/*drawingPoints 	= new JButton("Point");
		drawingPoints.setBackground(Color.decode("#f8f8ff"));
		drawingLines	= new JButton("Line");
		drawingLines.setBackground(Color.decode("#f8f8ff"));
		drawingTriangles	= new JButton("Triangle");
		drawingTriangles.setBackground(Color.decode("#f8f8ff"));
		drawingRectangles	= new JButton("Rectangle");
//		drawingRectangles.setBackground(Color.decode("#f8f8ff"));*/
		
		//Editing buttons
		selectElements	= new JButton("Select");
		selectElements.setBackground(Color.decode("#b3ccff"));
		deleteElements 	= new JButton("Delete");
		deleteElements.setBackground(Color.decode("#ffb3b3"));
		moveElements 	= new JButton("Move");
		moveElements.setBackground(Color.decode("#b3ffb3"));
		changeElements 	= new JButton("Change");
		changeElements.setBackground(Color.decode("#ffff80"));

		//Buttons add ActionListeners
		/*drawingPoints.addActionListener(this);
		drawingLines.addActionListener(this);
		drawingTriangles.addActionListener(this);
		drawingRectangles.addActionListener(this);*/
		selectElements.addActionListener(this);
		deleteElements.addActionListener(this);
		moveElements.addActionListener(this);
		changeElements.addActionListener(this);
		// ActionListener for MenuItems
		obj1.addActionListener(this);
		obj2.addActionListener(this);
		obj3.addActionListener(this);
		obj4.addActionListener(this);

		csv.addActionListener(this);
		csvexport.addActionListener(this);
		db.addActionListener(this);

		//Set ToolBar
		/*toolbar = new JToolBar();
		toolbar.add(drawingPoints);
		toolbar.add(drawingLines);
		toolbar.add(drawingTriangles);
		toolbar.add(drawingRectangles);
		toolbar.add(selectElements);
		toolbar.add(deleteElements);
		toolbar.add(moveElements);
		toolbar.add(changeElements);

		toolBox = Box.createHorizontalBox();
		toolBox.add(toolbar);
		add(toolBox);*/

		// General Frame Settings
		setTitle("CAD APP");
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(640, 480);
		setLocationRelativeTo(null);

	}

	/**
	 * Initializes a new instance of Main.
	 * Sets the defined layout and displays an OptionPane with advice on how to use this software.
	 * @author 
	 * @param args
	 */
	public static void main(String[] args) {

		frame = new Main();
		frame.setLayout();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		Object eTarget = e.getSource();

		if ( eTarget.equals(obj1)) {
			if ( !drawMode.equals("PointMode")) {
				drawMode = "PointMode";
				trackedMode.setText(drawMode+"    |    ");
			} else {
				drawMode = "default";
				trackedMode.setText(drawMode+"    |    ");
			}

		} else if ( eTarget.equals(obj2)) {
			if ( !drawMode.equals("LineMode")) {
				drawMode = "LineMode";
				trackedMode.setText(drawMode+"    |    ");
			} else {
				drawMode = "default";
				trackedMode.setText(drawMode+"    |    ");
			}

		} else if ( eTarget.equals(obj3)) {
			if ( !drawMode.equals("TriangleMode")) {
				drawMode = "TriangleMode";
				trackedMode.setText(drawMode+"    |    ");
			} else {
				drawMode = "default";
				trackedMode.setText(drawMode+"    |    ");
			}

		} else if ( eTarget.equals(obj4)) {
			if ( !drawMode.equals("RectangleMode")) {
				drawMode = "RectangleMode";
				trackedMode.setText(drawMode+"    |    ");
			} else {
				drawMode = "default";
				trackedMode.setText(drawMode+"    |    ");
			}

		} else if ( eTarget.equals(deleteElements)) {
			//editor.deleteAffectedToolObjects();
			editor.clearCurrentSelection();
			//drawingcanvas.defineSelectionRectangle(null);
			//drawingcanvas.requestToolObjectLists(editor);
			drawingcanvas.repaint();

		} else if ( eTarget.equals(selectElements)) {
			if ( !drawMode.equals("SelectMode")) {
				drawMode = "SelectMode";
				trackedMode.setText(drawMode+"    |    ");
			} else {
				drawMode = "default";
				trackedMode.setText(drawMode+"    |    ");
			}

		} else if( eTarget.equals(moveElements)) {
			if ( !drawMode.equals("MoveMode")) {
				drawMode = "MoveMode";
				trackedMode.setText(drawMode+"    |    ");
			} else {
				drawMode = "default";
				trackedMode.setText(drawMode+"    |    ");
			}

		} else if( eTarget.equals(changeElements)) {
			if ( !drawMode.equals("ChangeMode")) {
				drawMode = "ChangeMode";
				trackedMode.setText(drawMode+"    |    ");
			} else {
				drawMode = "default";
				trackedMode.setText(drawMode+"    |    ");
			}

		} else if ( eTarget.equals(csv)) {
			try {
				GUICSV.openFileChooserDialog();
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
//				GUICSV.displayObjectFromCSV();
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		} else if ( eTarget.equals(csvexport)) {
			//openDbInterface();
		}else if ( eTarget.equals(db)) {
			//openDbInterface();
		}

	}

}

