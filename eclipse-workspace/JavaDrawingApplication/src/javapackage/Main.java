package javapackage;

import database.GUICSV;
import database.DBUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import javax.swing.*;
/**
 * Creates the GUI for the application 
 * 
 * @author T Galaso
 */
@SuppressWarnings("serial")
public class Main extends JFrame implements ActionListener {


	public static Main maineditor;
	
	
	public DrawingCanvas newcanvas;

	// Editor for storing and editing geometries
     public EditorTools editor;

	
	public static DBUI databaseUI;

	// MenuBar Variables
	JMenuBar menubar;
	JMenu datamenu;
	JMenu objmenu;
	JMenu toolabout;
	JMenu help;

	// MenuItems
	JMenuItem obj1;
	JMenuItem obj2;
	JMenuItem obj3;
	JMenuItem obj4;
	JMenuItem obj5;
	JMenuItem csvimport;
	JMenuItem db;
	JMenuItem csvexport;

	// Contains editor tools buttons
	JToolBar toolbar;

	// Editor tool buttons
	JButton selectElements;
	JButton deleteElements;
	JButton shiftElements;

	// Mouse Tracking Variables Done
	JLabel trackedCoord;
	int trackedX;
	int trackedY;
	// DrawMode Tracking
	JLabel trackedMode;

	// Mouse Selection Variables
	double selectionX1;
	double selectionY1;
	double selectionX2;
	double selectionY2;

	// Functional Variables
	String drawMode = "default";
	boolean lineInitiated = false;
	boolean triangleInitiated1 = false;
	boolean triangleInitiated2 = false;
	boolean rectangleInitiated = false;
	boolean polygonInitiated1 = false;
	boolean polygonInitiated2 = false;
	boolean selectionInitiated = false;
	boolean changeInitiated = false;
	boolean polygonIsNowComplete = false;

	// Movement& Change Variables
	boolean movementInitiated = false;
	boolean movingPoint = false;
	boolean movingLine = false;
	boolean movingTriangle = false;
	boolean movingRectangle = false;
	boolean movingPolygon = false;
	Rectangle2D selectionRectangle = null;
	int ShapesId;
	int moveStartX;
	int moveStartY;
	int moveDestinationX;
	int moveDestinationY;
	boolean isStart = false;
	boolean isMiddle = false;
	boolean isEnd = false;
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
 *
 */
	public Main() {

		// MenuBar & menus
		menubar = new JMenuBar();
		datamenu = new JMenu("File");
		objmenu = new JMenu("Objects");
		help = new JMenu("Help");

		// MenuItems
		obj1 = new JMenuItem("Points");
		obj2 = new JMenuItem("Lines");
		obj3 = new JMenuItem("Triangles");
		obj4 = new JMenuItem("Rectangles");
		csvimport = new JMenuItem("Import");
		csvexport = new JMenuItem("Export");
		db = new JMenuItem("Database Manager");

		// New ToolBar.
		toolbar = new JToolBar("Editor", JToolBar.HORIZONTAL);
		//ToolBar elements
		selectElements = new JButton("Select");
		shiftElements = new JButton("Shift");
		deleteElements = new JButton("Delete");

		// Adds items to main menu.
		this.setJMenuBar(menubar);
		datamenu.add(csvimport);
		datamenu.add(csvexport);
		datamenu.add(db);
        
		//Adds items to 'Objects' menu.
		objmenu.add(obj1);
		objmenu.add(obj2);
		objmenu.add(obj3);
		objmenu.add(obj4);
		
        //Adds three basic menus to main menu.
		menubar.add(datamenu);
		menubar.add(objmenu);
		menubar.add(help);

		toolbar.add(selectElements);
		toolbar.add(shiftElements);
		toolbar.add(deleteElements);

		// Buttons add ActionListeners
		selectElements.addActionListener(this);
		deleteElements.addActionListener(this);
		shiftElements.addActionListener(this);
		// ActionListener for MenuItems
		obj1.addActionListener(this);
		obj2.addActionListener(this);
		obj3.addActionListener(this);
		obj4.addActionListener(this);

		csvimport.addActionListener(this);
		csvexport.addActionListener(this);
		db.addActionListener(this);
		
		//creates object of editor
		editor = new EditorTools();

		// creates object of drawingpanel
		newcanvas = new DrawingCanvas();
		newcanvas.setBackground(Color.white);

		newcanvas.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// PointFeatures class will be used to draw features
				point = new PointFeature();
				point.setPoint(e.getX(), e.getY());

				switch (drawMode) {

				case "PointMode":// DRAW POINT
					// creates and stores the new object to the 'drawingPoints' ArrayLists in
					// EditorTools class.
					editor.addPoints(point);// see line 29
					newcanvas.requestObjectLists(editor);
					newcanvas.repaint();
					break;

				case "LineMode": // DRAW LINE
					if (lineInitiated == false) {
						line = new LineFeature();
						line.addLineStart(point);
						lineInitiated = true;
						break;

					} else if (lineInitiated == true) {
						line.addLineEnd(point);
						editor.addLines(line);
						newcanvas.requestObjectLists(editor);
						newcanvas.clearDrawingElements();
						newcanvas.repaint();
						lineInitiated = false;
						break;
					}

				case "TriangleMode":// DRAW TRIANGLE
					if (triangleInitiated1 == false && triangleInitiated2 == false) {
						System.out.println("TRIANGLE ONCE CLICKED");
						triangle = new TriangleFeature();
						triangle.addTriangleStart(point);
						triangleInitiated1 = true;
						break;

					} else if (triangleInitiated1 == true && triangleInitiated2 == false) {
						System.out.println("TRIANGLE TWICE CLICKED");
						triangle.addTriangleMid(point);
						triangleInitiated1 = false;
						triangleInitiated2 = true;
						break;

					} else if (triangleInitiated1 == false && triangleInitiated2 == true) {
						System.out.println("TRIANGLE TRICE CLICKED");
						triangle.addTriangleEnd(point);
						editor.addTriangles(triangle);
						newcanvas.requestObjectLists(editor);
						newcanvas.clearDrawingElements();
						newcanvas.repaint();
						triangleInitiated1 = false;
						triangleInitiated2 = false;
						break;
					}

				case "RectangleMode":// DRAW RECTANGLE
					if (rectangleInitiated == false) {
						editor.clearCurrentSelection();
						rectangle = new RectangleFeature();
						rectangle.addRetangleFirstCorner(point);
						rectangleInitiated = true;
						break;

					} else if (rectangleInitiated == true) {
						editor.clearCurrentSelection();
						rectangle.addRectangleLastCorner(point);
						editor.addRectangles(rectangle);
						newcanvas.requestObjectLists(editor);
						newcanvas.clearDrawingElements();
						newcanvas.repaint();
						rectangleInitiated = false;
						break;
					}

				case "SelectMode":// DRAW SELECTION BOX
					if (selectionInitiated == false) {
						System.out.println("selection Initiated: false");
						editor.clearCurrentSelection();
						newcanvas.defineSelectionRectangle(null);
						newcanvas.requestObjectLists(editor);
						newcanvas.repaint();
						// x,y of first click of selection mode
						selectionX1 = point.x;
						selectionY1 = point.y;
						selectionInitiated = true;
						break;

					} else if (selectionInitiated == true) {
						System.out.println("selection Initiated: true");
						selectionX2 = point.x;
						selectionY2 = point.y;

						// Determine 'height' and 'width' of the Selection Rectangle
						double rectangleWidth = Math.abs(selectionX1 - selectionX2);
						double rectangleHeight = Math.abs(selectionY1 - selectionY2);

						double rectangleStart;
						double rectangleEnd;

						// Determine 'top-left' and 'bottom-right' Point of the Selection Rectangle
						if (selectionX2 > selectionX1) {
							rectangleStart = selectionX1;
						} else {
							rectangleStart = selectionX2;
						}
						if (selectionY2 > selectionY1) {
							rectangleEnd = selectionY1;
						} else {
							rectangleEnd = selectionY2;
						}

						// Compose Selection Rectangle
						// Rectangle2D selectionRectangle = new Rectangle2D.Double();
						selectionRectangle = new Rectangle2D.Double();
						selectionRectangle.setRect(rectangleStart, rectangleEnd, rectangleWidth, rectangleHeight);
						newcanvas.defineSelectionRectangle(selectionRectangle);
						newcanvas.repaint();// we dont need the selection rectangle to be displayed.

						editor.selectAffectedObjects(selectionRectangle);
						 newcanvas.requestObjectLists(editor);
						newcanvas.repaint();

						selectionInitiated = false;
						break;
					}

				case "ShiftMode":// CHECKS IF SELECTION BOX IS CREATED(features are selected) THEN CONTINUES to
									// further conditions

					if (selectionRectangle != null) {
						// initiates Shift
						if (movementInitiated == false) {
							moveStartX = e.getX();
							moveStartY = e.getY();
							// selectionRectangle = new Rectangle2D.Double(e.getX() - 13, e.getY() - 13, 26,
							// 26);

							// Moving Point 1
							for (PointFeature point : editor.drawingPoints) {
								if (movingPoint == false) {
									point1 = new Point2D.Double(point.x, point.y);// here creates new point called 'point1' from existing pointfeature

									if (selectionRectangle.contains(point1) && movingPoint == false) {// then if
																										// selection box
																										// contains this
																										// new created
																										// point1(copy
																										// of original
																										// points)
										x1 = point.x;
										y1 = point.y;
										ShapesId = point.getShapesId();
										movingPoint = true;
										movementInitiated = true;
									}
								}
							}
							// Moving Line 1
							editor.drawingLines.forEach((LineFeature line) -> {
								if (movingLine == false && movingPoint == false) {
									point1 = new Point2D.Double(line.lineElements[0].x, line.lineElements[0].y);
									point2 = new Point2D.Double(line.lineElements[1].x, line.lineElements[1].y);

									if (selectionRectangle.contains(point1) && movingLine == false) {
										x1 = line.lineElements[0].x;
										y1 = line.lineElements[0].y;
										x2 = line.lineElements[1].x;
										y2 = line.lineElements[1].y;
										ShapesId = line.getShapesId();
										movingLine = true;
										movementInitiated = true;
										isStart = true;

									} else if (selectionRectangle.contains(point2) && movingLine == false) {
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

							// Moving Triangle 1
							editor.drawingTriangles.forEach((TriangleFeature triangle) -> {
								if (movingTriangle == false && movingPoint == false && movingLine == false) {
									point1 = new Point2D.Double(triangle.triangleElements[0].x,
											triangle.triangleElements[0].y);
									point2 = new Point2D.Double(triangle.triangleElements[1].x,
											triangle.triangleElements[1].y);
									point3 = new Point2D.Double(triangle.triangleElements[2].x,
											triangle.triangleElements[2].y);

									// If triangle is totally contained in 'selectionRectangle'
									if ((selectionRectangle.contains(point1) && selectionRectangle.contains(point2)
											&& selectionRectangle.contains(point3)) && movingTriangle == false) {
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

									}
								}
							});

							// Moving Rectangles 1
							editor.drawingRectangles.forEach((RectangleFeature rectangle) -> {
								if (movingRectangle == false && movingTriangle == false && movingPoint == false
										&& movingLine == false) {
									point1 = new Point2D.Double(rectangle.rectangleElements[0].x,
											rectangle.rectangleElements[0].y);
									point2 = new Point2D.Double(rectangle.rectangleElements[1].x,
											rectangle.rectangleElements[1].y);

									if (selectionRectangle.contains(point1) && movingRectangle == false) {
										x1 = rectangle.rectangleElements[0].x;
										y1 = rectangle.rectangleElements[0].y;
										x2 = rectangle.rectangleElements[1].x;
										y2 = rectangle.rectangleElements[1].y;
										ShapesId = rectangle.getShapesId();
										movingRectangle = true;
										movementInitiated = true;
										isStart = true;

									} else if (selectionRectangle.contains(point2) && movingRectangle == false) {
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

						} else if (movementInitiated == true) {
							int moveDestinationX = e.getX();
							int moveDestinationY = e.getY();
							int moveDifferenceX = moveStartX - moveDestinationX;
							int moveDifferenceY = moveStartY - moveDestinationY;

							// Moving Point 2
							if (movingPoint == true) {
								for (int i = 0; i < editor.drawingPoints.size(); i++) {
									int matchIdentifier = editor.drawingPoints.get(i).ShapesId;

									if (ShapesId == matchIdentifier) {
										objectArrayPosition = i;
										editor.drawingPoints.get(i).x = moveDestinationX;
										editor.drawingPoints.get(i).y = moveDestinationY;
										newcanvas.requestObjectLists(editor);
										newcanvas.repaint();
										movingPoint = false;
										movementInitiated = false;
									}
								}

							}

							// Moving Line 2
							if (movingLine == true) {
								for (int i = 0; i < editor.drawingLines.size(); i++) {
									int matchIdentifier = editor.drawingLines.get(i).ShapesId;

									if (ShapesId == matchIdentifier) {
										objectArrayPosition = i;
										editor.drawingLines.get(i).lineElements[0].x = x1 - moveDifferenceX;
										editor.drawingLines.get(i).lineElements[0].y = y1 - moveDifferenceY;
										editor.drawingLines.get(i).lineElements[1].x = x2 - moveDifferenceX;
										editor.drawingLines.get(i).lineElements[1].y = y2 - moveDifferenceY;
										newcanvas.requestObjectLists(editor);
										 newcanvas.clearDrawingElements();
										newcanvas.repaint();
										movingLine = false;
										movementInitiated = false;

									}
								}

							}

							// Moving Triangle 2
							if (movingTriangle == true) {
								for (int i = 0; i < editor.drawingTriangles.size(); i++) {
									int matchIdentifier = editor.drawingTriangles.get(i).ShapesId;

									if (ShapesId == matchIdentifier) {
										objectArrayPosition = i;
										editor.drawingTriangles.get(i).triangleElements[0].x = x1 - moveDifferenceX;
										editor.drawingTriangles.get(i).triangleElements[0].y = y1 - moveDifferenceY;
										editor.drawingTriangles.get(i).triangleElements[1].x = x2 - moveDifferenceX;
										editor.drawingTriangles.get(i).triangleElements[1].y = y2 - moveDifferenceY;
										editor.drawingTriangles.get(i).triangleElements[2].x = x3 - moveDifferenceX;
										editor.drawingTriangles.get(i).triangleElements[2].y = y3 - moveDifferenceY;

										newcanvas.requestObjectLists(editor);
										newcanvas.repaint();
										movingTriangle = false;
										movementInitiated = false;

									}
								}

							}

							// Moving Rectangles 2
							if (movingRectangle == true) {
								for (int i = 0; i < editor.drawingRectangles.size(); i++) {
									int matchIdentifier = editor.drawingRectangles.get(i).ShapesId;

									if (ShapesId == matchIdentifier) {
										objectArrayPosition = i;
										editor.drawingRectangles.get(i).rectangleElements[0].x = x1 - moveDifferenceX;
										editor.drawingRectangles.get(i).rectangleElements[0].y = y1 - moveDifferenceY;
										editor.drawingRectangles.get(i).rectangleElements[1].x = x2 - moveDifferenceX;
										editor.drawingRectangles.get(i).rectangleElements[1].y = y2 - moveDifferenceY;
									}
								}
								newcanvas.requestObjectLists(editor);
								newcanvas.repaint();
								movingRectangle = false;
								movementInitiated = false;
							}
							isStart = false;
							isMiddle = false;
							isEnd = false;
							break;
						}
					} // selection rectangle is not null?
					else {
						System.out.println("PLEASE SELECT FIRST!");
					}

				case "ChangeMode":
					moveStartX = e.getX();
					moveStartY = e.getY();

					if (changeInitiated == false) {

						// Changing Points
						editor.drawingPoints.forEach((PointFeature point) -> {
							if (movingPoint == false) {
								point1 = new Point2D.Double(point.x, point.y);

								if (selectionRectangle.contains(point1) && movingPoint == false) {
									x1 = point.x;
									y1 = point.y;
									ShapesId = point.getShapesId();
									movingPoint = true;
									changeInitiated = true;
								}
							}
						});

						// Changing Line
						editor.drawingLines.forEach((LineFeature line) -> {
							if (movingLine == false && movingPoint == false) {
								point1 = new Point2D.Double(line.lineElements[0].x, line.lineElements[0].y);
								point2 = new Point2D.Double(line.lineElements[1].x, line.lineElements[1].y);

								if (selectionRectangle.contains(point1) && movingLine == false) {
									ShapesId = line.getShapesId();
									isStart = true;
									movingLine = true;
									changeInitiated = true;

								} else if (selectionRectangle.contains(point2) && movingLine == false) {
									ShapesId = line.getShapesId();
									isStart = false;
									isEnd = true;
									movingLine = true;
									changeInitiated = true;
								}
							}
						});

						// Changing Triangle
						editor.drawingTriangles.forEach((TriangleFeature triangle) -> {
							if (movingLine == false && movingPoint == false && movingTriangle == false) {
								point1 = new Point2D.Double(triangle.triangleElements[0].x,
										triangle.triangleElements[0].y);
								point2 = new Point2D.Double(triangle.triangleElements[1].x,
										triangle.triangleElements[1].y);
								point3 = new Point2D.Double(triangle.triangleElements[2].x,
										triangle.triangleElements[2].y);

								if (selectionRectangle.contains(point1) && movingTriangle == false) {
									ShapesId = triangle.getShapesId();
									isStart = true;
									movingTriangle = true;
									changeInitiated = true;

								} else if (selectionRectangle.contains(point2) && movingTriangle == false) {
									ShapesId = triangle.getShapesId();
									isMiddle = true;
									movingTriangle = true;
									changeInitiated = true;

								} else if (selectionRectangle.contains(point3) && movingTriangle == false) {
									ShapesId = triangle.getShapesId();
									isEnd = true;
									movingTriangle = true;
									changeInitiated = true;
								}
							}
						});

						editor.drawingRectangles.forEach((RectangleFeature rectangle) -> {
							if (movingLine == false && movingPoint == false && movingTriangle == false
									&& movingRectangle == false) {
								point1 = new Point2D.Double(rectangle.rectangleElements[0].x,
										rectangle.rectangleElements[0].y);
								point2 = new Point2D.Double(rectangle.rectangleElements[1].x,
										rectangle.rectangleElements[1].y);

								if (selectionRectangle.contains(point1) && movingRectangle == false) {
									ShapesId = rectangle.getShapesId();
									isStart = true;
									movingRectangle = true;
									changeInitiated = true;

								} else if (selectionRectangle.contains(point2) && movingRectangle == false) {
									ShapesId = rectangle.getShapesId();
									isEnd = true;
									movingRectangle = true;
									changeInitiated = true;
								}
							}
						});
						break;
					}

					if (changeInitiated == true) {
						int moveDestinationX = e.getX();
						int moveDestinationY = e.getY();

						if (movingPoint == true) {
							for (int i = 0; i < editor.drawingPoints.size(); i++) {
								int matchIdentifier = editor.drawingPoints.get(i).ShapesId;

								if (ShapesId == matchIdentifier) {
									objectArrayPosition = i;
									editor.drawingPoints.get(i).x = moveStartX;
									editor.drawingPoints.get(i).y = moveStartY;

									newcanvas.requestObjectLists(editor);
									newcanvas.repaint();
									movingPoint = false;
									changeInitiated = false;
								}
							}

						}

						if (movingLine == true) {
							if (isStart == true) {
								for (int i = 0; i < editor.drawingLines.size(); i++) {
									int matchIdentifier = editor.drawingLines.get(i).ShapesId;

									if (ShapesId == matchIdentifier) {
										objectArrayPosition = i;
										editor.drawingLines.get(i).lineElements[0].x = moveDestinationX;
										editor.drawingLines.get(i).lineElements[0].y = moveDestinationY;

										newcanvas.requestObjectLists(editor);
										newcanvas.clearDrawingElements();
										newcanvas.repaint();
										movingLine = false;
										changeInitiated = false;
									}

								}
							}

							if (movingLine == true) {

								if (isStart == false) {
									for (int i = 0; i < editor.drawingLines.size(); i++) {
										int matchIdentifier = editor.drawingLines.get(i).ShapesId;

										if (ShapesId == matchIdentifier) {
											objectArrayPosition = i;
											editor.drawingLines.get(i).lineElements[1].x = moveDestinationX;
											editor.drawingLines.get(i).lineElements[1].y = moveDestinationY;

											newcanvas.requestObjectLists(editor);
											newcanvas.clearDrawingElements();
											newcanvas.repaint();
											movingLine = false;
											changeInitiated = false;
										}
									}
								}

							}
						}

						if (movingTriangle == true) {

							if (isStart == true && isMiddle == false && isEnd == false) {
								for (int i = 0; i < editor.drawingTriangles.size(); i++) {
									int matchIdentifier = editor.drawingTriangles.get(i).ShapesId;

									if (ShapesId == matchIdentifier) {
										objectArrayPosition = i;
										editor.drawingTriangles.get(i).triangleElements[0].x = moveDestinationX;
										editor.drawingTriangles.get(i).triangleElements[0].y = moveDestinationY;

										newcanvas.requestObjectLists(editor);
										newcanvas.repaint();
										movingTriangle = false;
										changeInitiated = false;

									}
								}
							}

							if (isMiddle == true && isStart == false && isEnd == false) {
								for (int i = 0; i < editor.drawingTriangles.size(); i++) {
									int matchIdentifier = editor.drawingTriangles.get(i).ShapesId;

									if (ShapesId == matchIdentifier) {
										objectArrayPosition = i;
										editor.drawingTriangles.get(i).triangleElements[1].x = moveDestinationX;
										editor.drawingTriangles.get(i).triangleElements[1].y = moveDestinationY;

										newcanvas.requestObjectLists(editor);
										newcanvas.repaint();
										movingTriangle = false;
										changeInitiated = false;
									}
								}
							}

							if (isEnd == true && isMiddle == false && isStart == false) {
								for (int i = 0; i < editor.drawingTriangles.size(); i++) {
									int matchIdentifier = editor.drawingTriangles.get(i).ShapesId;

									if (ShapesId == matchIdentifier) {
										objectArrayPosition = i;
										editor.drawingTriangles.get(i).triangleElements[2].x = moveDestinationX;
										editor.drawingTriangles.get(i).triangleElements[2].y = moveDestinationY;

										 newcanvas.requestObjectLists(editor);
										newcanvas.repaint();
										movingTriangle = false;
										changeInitiated = false;
									}
								}
							}
						}

						if (movingRectangle == true) {

							if (isStart == true && isEnd == false) {
								for (int i = 0; i < editor.drawingRectangles.size(); i++) {
									int matchIdentifier = editor.drawingRectangles.get(i).ShapesId;

									if (ShapesId == matchIdentifier) {
										objectArrayPosition = i;
										editor.drawingRectangles.get(i).rectangleElements[0].x = moveDestinationX;
										editor.drawingRectangles.get(i).rectangleElements[0].y = moveDestinationY;
									}
								}
								newcanvas.requestObjectLists(editor);
								newcanvas.repaint();
								movingRectangle = false;
								changeInitiated = false;
							}

							if (isEnd == true && isStart == false) {
								for (int i = 0; i < editor.drawingRectangles.size(); i++) {
									int matchIdentifier = editor.drawingRectangles.get(i).ShapesId;

									if (ShapesId == matchIdentifier) {
										objectArrayPosition = i;
										editor.drawingRectangles.get(i).rectangleElements[1].x = moveDestinationX;
										editor.drawingRectangles.get(i).rectangleElements[1].y = moveDestinationY;

									}
									 newcanvas.requestObjectLists(editor);
									newcanvas.repaint();
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

		newcanvas.addMouseMotionListener(new MouseAdapter() {

			// visualise while creating and modifying features
			@Override
			public void mouseMoved(MouseEvent e) {
				trackedX = e.getX();
				trackedY = e.getY();
				trackedCoord.setText("X: " + trackedX + "    Y: " + trackedY + "   ");
				point = new PointFeature();
				// After each click on the canvas the PointFeature for general purpose will be
				// created.
				point.setPoint(e.getX(), e.getY());
				// first click was the first pointFeature then it checks for the next condition
				if (lineInitiated == true) {
					selectionInitiated = false;
					line.addLineEnd(point);
					newcanvas.storeDrawingLineElements(line);
					newcanvas.repaint();
				}

				if (triangleInitiated1 == true && triangleInitiated2 == false) {
					selectionInitiated = false;
					triangle.addTriangleMid(point);
					triangle.addTriangleEnd(point);
					newcanvas.storeDrawingTriangleElements(triangle);
					newcanvas.repaint();
				}

				if (triangleInitiated2 == true && triangleInitiated1 == false) {
					selectionInitiated = false;
					triangle.addTriangleEnd(point);
					newcanvas.storeDrawingTriangleElements(triangle);
					newcanvas.repaint();

				}

				if (rectangleInitiated == true) {
					selectionInitiated = false;
					rectangle.addRectangleLastCorner(point);
					newcanvas.repaint();
				}

				if (selectionInitiated == true) {
					selectionX2 = point.x;
					selectionY2 = point.y;

					// Determine 'height' and 'width' of the Selection Rectangle
					double rectangleWidth = Math.abs(selectionX1 - selectionX2);
					double rectangleHeight = Math.abs(selectionY1 - selectionY2);

					double rectangleStart;
					double rectangleEnd;

					// Determine 'top-left' and 'bottom-right' Point of the Selection Rectangle
					if (selectionX2 > selectionX1) {
						rectangleStart = selectionX1;
					} else {
						rectangleStart = selectionX2;
					}
					if (selectionY2 > selectionY1) {
						rectangleEnd = selectionY1;
					} else {
						rectangleEnd = selectionY2;
					}

					// Compose Selection Rectangle
					Rectangle2D selectionRectangle = new Rectangle2D.Double();
					selectionRectangle.setRect(rectangleStart, rectangleEnd, rectangleWidth, rectangleHeight);
					newcanvas.defineSelectionRectangle(selectionRectangle);
					 newcanvas.requestObjectLists(editor);
					newcanvas.repaint();
				}

				if (movementInitiated == true && changeInitiated == false) {

					if (movingPoint == true) {
						for (int i = 0; i < editor.drawingPoints.size(); i++) {
							int matchIdentifier = editor.drawingPoints.get(i).ShapesId;

							if (ShapesId == matchIdentifier) {
								editor.drawingPoints.get(i).x = point.x;
								editor.drawingPoints.get(i).y = point.y;

								newcanvas.requestObjectLists(editor);
								newcanvas.repaint();
							}
						}
					}

					if (movingLine == true) {
						// If selected line point was the first line element
						if (isStart == true && isEnd == false) {
							for (int i = 0; i < editor.drawingLines.size(); i++) {
								int matchIdentifier = editor.drawingLines.get(i).ShapesId;

								if (ShapesId == matchIdentifier) {
									double diffX = editor.drawingLines.get(i).lineElements[0].x
											- editor.drawingLines.get(i).lineElements[1].x;
									double diffY = editor.drawingLines.get(i).lineElements[0].y
											- editor.drawingLines.get(i).lineElements[1].y;

									editor.drawingLines.get(i).addLineStart(point);
									PointFeature newPoint = new PointFeature();
									newPoint.x = editor.drawingLines.get(i).lineElements[0].x - diffX;
									newPoint.y = editor.drawingLines.get(i).lineElements[0].y - diffY;
									editor.drawingLines.get(i).addLineEnd(newPoint);

									newcanvas.requestObjectLists(editor);
									newcanvas.repaint();
								}
							}
						}

						// If selected line point was the last line element
						if (isStart == false && isEnd == true) {
							for (int i = 0; i < editor.drawingLines.size(); i++) {
								int matchIdentifier = editor.drawingLines.get(i).ShapesId;

								if (ShapesId == matchIdentifier) {
									double diffX = editor.drawingLines.get(i).lineElements[0].x
											- editor.drawingLines.get(i).lineElements[1].x;
									double diffY = editor.drawingLines.get(i).lineElements[0].y
											- editor.drawingLines.get(i).lineElements[1].y;

									editor.drawingLines.get(i).addLineEnd(point);
									PointFeature newPoint = new PointFeature();
									newPoint.x = editor.drawingLines.get(i).lineElements[1].x + diffX;
									newPoint.y = editor.drawingLines.get(i).lineElements[1].y + diffY;
									editor.drawingLines.get(i).addLineStart(newPoint);

									newcanvas.requestObjectLists(editor);
									newcanvas.repaint();
								}
							}
						}

					}

					if (movingTriangle == true) {
						// If selected triangle point was the first triangle element
						if (isStart == true && isMiddle == false && isEnd == false) {

							for (int i = 0; i < editor.drawingTriangles.size(); i++) {
								int matchIdentifier = editor.drawingTriangles.get(i).ShapesId;

								if (ShapesId == matchIdentifier) {
									double diffX1 = editor.drawingTriangles.get(i).triangleElements[0].x
											- editor.drawingTriangles.get(i).triangleElements[1].x;
									double diffY1 = editor.drawingTriangles.get(i).triangleElements[0].y
											- editor.drawingTriangles.get(i).triangleElements[1].y;
									double diffX2 = editor.drawingTriangles.get(i).triangleElements[0].x
											- editor.drawingTriangles.get(i).triangleElements[2].x;
									double diffY2 = editor.drawingTriangles.get(i).triangleElements[0].y
											- editor.drawingTriangles.get(i).triangleElements[2].y;

									editor.drawingTriangles.get(i).addTriangleStart(point);
									PointFeature newPoint1 = new PointFeature();
									PointFeature newPoint2 = new PointFeature();
									newPoint1.x = editor.drawingTriangles.get(i).triangleElements[0].x - diffX1;
									newPoint1.y = editor.drawingTriangles.get(i).triangleElements[0].y - diffY1;
									newPoint2.x = editor.drawingTriangles.get(i).triangleElements[0].x - diffX2;
									newPoint2.y = editor.drawingTriangles.get(i).triangleElements[0].y - diffY2;
									editor.drawingTriangles.get(i).addTriangleMid(newPoint1);
									editor.drawingTriangles.get(i).addTriangleEnd(newPoint2);

									newcanvas.requestObjectLists(editor);
									newcanvas.repaint();

								}
							}
						}

						// If selected triangle point was the middle triangle element
						if (isStart == false && isMiddle == true && isEnd == false) {

							for (int i = 0; i < editor.drawingTriangles.size(); i++) {
								int matchIdentifier = editor.drawingTriangles.get(i).ShapesId;

								if (ShapesId == matchIdentifier) {
									double diffX1 = editor.drawingTriangles.get(i).triangleElements[1].x
											- editor.drawingTriangles.get(i).triangleElements[0].x;
									double diffY1 = editor.drawingTriangles.get(i).triangleElements[1].y
											- editor.drawingTriangles.get(i).triangleElements[0].y;
									double diffX2 = editor.drawingTriangles.get(i).triangleElements[1].x
											- editor.drawingTriangles.get(i).triangleElements[2].x;
									double diffY2 = editor.drawingTriangles.get(i).triangleElements[1].y
											- editor.drawingTriangles.get(i).triangleElements[2].y;

									editor.drawingTriangles.get(i).addTriangleMid(point);
									PointFeature newPoint1 = new PointFeature();
									PointFeature newPoint2 = new PointFeature();
									newPoint1.x = editor.drawingTriangles.get(i).triangleElements[1].x - diffX1;
									newPoint1.y = editor.drawingTriangles.get(i).triangleElements[1].y - diffY1;
									newPoint2.x = editor.drawingTriangles.get(i).triangleElements[1].x - diffX2;
									newPoint2.y = editor.drawingTriangles.get(i).triangleElements[1].y - diffY2;
									editor.drawingTriangles.get(i).addTriangleStart(newPoint1);
									editor.drawingTriangles.get(i).addTriangleEnd(newPoint2);

									newcanvas.requestObjectLists(editor);
									newcanvas.repaint();

								}
							}
						}

						// If selected triangle point was the last triangle element
						if (isStart == false && isMiddle == false && isEnd == true) {

							for (int i = 0; i < editor.drawingTriangles.size(); i++) {
								int matchIdentifier = editor.drawingTriangles.get(i).ShapesId;

								if (ShapesId == matchIdentifier) {
									double diffX1 = editor.drawingTriangles.get(i).triangleElements[2].x
											- editor.drawingTriangles.get(i).triangleElements[0].x;
									double diffY1 = editor.drawingTriangles.get(i).triangleElements[2].y
											- editor.drawingTriangles.get(i).triangleElements[0].y;
									double diffX2 = editor.drawingTriangles.get(i).triangleElements[2].x
											- editor.drawingTriangles.get(i).triangleElements[1].x;
									double diffY2 = editor.drawingTriangles.get(i).triangleElements[2].y
											- editor.drawingTriangles.get(i).triangleElements[1].y;

									editor.drawingTriangles.get(i).addTriangleEnd(point);
									PointFeature newPoint1 = new PointFeature();
									PointFeature newPoint2 = new PointFeature();
									newPoint1.x = editor.drawingTriangles.get(i).triangleElements[2].x - diffX1;
									newPoint1.y = editor.drawingTriangles.get(i).triangleElements[2].y - diffY1;
									newPoint2.x = editor.drawingTriangles.get(i).triangleElements[2].x - diffX2;
									newPoint2.y = editor.drawingTriangles.get(i).triangleElements[2].y - diffY2;
									editor.drawingTriangles.get(i).addTriangleMid(newPoint1);
									editor.drawingTriangles.get(i).addTriangleStart(newPoint2);

									newcanvas.requestObjectLists(editor);
									newcanvas.repaint();

								}
							}
						}
					}

					if (movingRectangle == true) {

						// If selected rectangle point was the first corner of the rectangle element
						if (isStart == true && isEnd == false) {
							for (int i = 0; i < editor.drawingRectangles.size(); i++) {
								int matchIdentifier = editor.drawingRectangles.get(i).ShapesId;

								if (ShapesId == matchIdentifier) {
									double diffX1 = editor.drawingRectangles.get(i).rectangleElements[0].x
											- editor.drawingRectangles.get(i).rectangleElements[1].x;
									double diffY1 = editor.drawingRectangles.get(i).rectangleElements[0].y
											- editor.drawingRectangles.get(i).rectangleElements[1].y;

									editor.drawingRectangles.get(i).addRetangleFirstCorner(point);
									PointFeature newPoint = new PointFeature();
									newPoint.x = editor.drawingRectangles.get(i).rectangleElements[0].x - diffX1;
									newPoint.y = editor.drawingRectangles.get(i).rectangleElements[0].y - diffY1;
									editor.drawingRectangles.get(i).addRectangleLastCorner(newPoint);

									newcanvas.requestObjectLists(editor);
									newcanvas.repaint();
								}
							}
						}

						// If selected rectangle point was the last corner of the rectangle element
						if (isStart == false && isEnd == true) {
							for (int i = 0; i < editor.drawingRectangles.size(); i++) {
								int matchIdentifier = editor.drawingRectangles.get(i).ShapesId;

								if (ShapesId == matchIdentifier) {
									double diffX1 = editor.drawingRectangles.get(i).rectangleElements[1].x
											- editor.drawingRectangles.get(i).rectangleElements[0].x;
									double diffY1 = editor.drawingRectangles.get(i).rectangleElements[1].y
											- editor.drawingRectangles.get(i).rectangleElements[0].y;

									editor.drawingRectangles.get(i).addRectangleLastCorner(point);
									PointFeature newPoint = new PointFeature();
									newPoint.x = editor.drawingRectangles.get(i).rectangleElements[1].x - diffX1;
									newPoint.y = editor.drawingRectangles.get(i).rectangleElements[1].y - diffY1;
									editor.drawingRectangles.get(i).addRetangleFirstCorner(newPoint);

									newcanvas.requestObjectLists(editor);
									newcanvas.repaint();
								}
							}
						}
					}

				}

				if (changeInitiated == true && movementInitiated == false) {

					if (movingPoint == true) {
						for (int i = 0; i < editor.drawingPoints.size(); i++) {
							int matchIdentifier = editor.drawingPoints.get(i).ShapesId;

							if (ShapesId == matchIdentifier) {
								editor.drawingPoints.get(i).x = point.x;
								editor.drawingPoints.get(i).y = point.y;
								newcanvas.requestObjectLists(editor);
								newcanvas.repaint();
							}
						}
					}

					if (movingLine == true) {
						// If selected line point was the first line element
						if (isStart == true && isEnd == false) {
							for (int i = 0; i < editor.drawingLines.size(); i++) {
								int matchIdentifier = editor.drawingLines.get(i).ShapesId;

								if (ShapesId == matchIdentifier) {
									editor.drawingLines.get(i).addLineStart(point);
									newcanvas.requestObjectLists(editor);
									newcanvas.repaint();
								}
							}
						}

						// If selected line point was the last line element
						if (isEnd == true && isStart == false) {
							for (int i = 0; i < editor.drawingLines.size(); i++) {
								int matchIdentifier = editor.drawingLines.get(i).ShapesId;

								if (ShapesId == matchIdentifier) {
									editor.drawingLines.get(i).addLineEnd(point);
									newcanvas.requestObjectLists(editor);
									newcanvas.repaint();
								}
							}
						}
					}

					if (movingTriangle == true) {
						// If selected triangle point was the first triangle element
						if (isStart == true && isMiddle == false && isEnd == false) {
							for (int i = 0; i < editor.drawingTriangles.size(); i++) {
								int matchIdentifier = editor.drawingTriangles.get(i).ShapesId;

								if (ShapesId == matchIdentifier) {
									editor.drawingTriangles.get(i).addTriangleStart(point);
									 newcanvas.requestObjectLists(editor);
									newcanvas.repaint();

								}
							}
						}

						// If selected triangle point was the middle triangle element
						if (isStart == false && isMiddle == true && isEnd == false) {
							for (int i = 0; i < editor.drawingTriangles.size(); i++) {
								int matchIdentifier = editor.drawingTriangles.get(i).ShapesId;

								if (ShapesId == matchIdentifier) {
									editor.drawingTriangles.get(i).addTriangleMid(point);

									 newcanvas.requestObjectLists(editor);
									newcanvas.repaint();

								}
							}
						}

						// If selected triangle point was the last triangle element
						if (isStart == false && isMiddle == false && isEnd == true) {
							for (int i = 0; i < editor.drawingTriangles.size(); i++) {
								int matchIdentifier = editor.drawingTriangles.get(i).ShapesId;
								if (ShapesId == matchIdentifier) {
									editor.drawingTriangles.get(i).addTriangleEnd(point);
									 newcanvas.requestObjectLists(editor);
									newcanvas.repaint();

								}
							}
						}
					}

					if (movingRectangle == true) {
						// If selected rectangle point was the first corner of rectangle element
						if (isStart == true && isEnd == false) {
							for (int i = 0; i < editor.drawingRectangles.size(); i++) {
								int matchIdentifier = editor.drawingRectangles.get(i).ShapesId;
								if (ShapesId == matchIdentifier) {
									editor.drawingRectangles.get(i).addRetangleFirstCorner(point);
									 newcanvas.requestObjectLists(editor);
									newcanvas.repaint();
								}
							}
						}

						// If selected rectangle point was the last corner of rectangle element
						if (isStart == false && isEnd == true) {
							for (int i = 0; i < editor.drawingRectangles.size(); i++) {
								int matchIdentifier = editor.drawingRectangles.get(i).ShapesId;
								if (ShapesId == matchIdentifier) {
									editor.drawingRectangles.get(i).addRectangleLastCorner(point);
									 newcanvas.requestObjectLists(editor);
									newcanvas.repaint();
								}
							}
						}
					}
				}
			}
		});
	}


	/**
	 * Assigns new content to editor
	 *@param neweditor 
	 *@author T Galaso 
	 */
	public void overwriteObjects(EditorTools neweditor) {
		this.editor = neweditor;
		 newcanvas.requestObjectLists(editor);
		 newcanvas.repaint();
	}
/**
 * Sets attributes for database user interface
 * @author T Galaso
 */
	public void dbUI() {
		databaseUI = new DBUI();
		databaseUI.setTitle("DB Interface");
		databaseUI.setLocationRelativeTo(null);
		databaseUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		databaseUI.setVisible(true);
	}

	/**
	 * Sets frame layout
	 * @author T Galaso
	 */
	public void setLayout() {

		// Set ContentPane
		setContentPane(newcanvas);

		// Status Bar
		menubar.add(Box.createHorizontalGlue());
		trackedCoord = new JLabel("[mouse deactivated]");
		trackedMode = new JLabel("[mode deactivated]    |    ");
		menubar.add(trackedMode);
		menubar.add(trackedCoord);
		trackedCoord.setHorizontalAlignment(JLabel.RIGHT);

		// Frame layout
		setTitle("CAD APP");
		setVisible(true);
		newcanvas.add(toolbar);
		toolbar.setFloatable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(640, 480);
		setLocationRelativeTo(null);
	}

	// ----------------------------------------
	// MAIN METHOD
	// ----------------------------------------
	public static void main(String[] args) {

		maineditor = new Main();
		maineditor.setLayout();

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		Object eTarget = e.getSource();

		if (eTarget.equals(obj1)) {
			if (!drawMode.equals("PointMode")) {
				drawMode = "PointMode";
				trackedMode.setText(drawMode + "    |    ");
			} else {
				drawMode = "default";
				trackedMode.setText(drawMode + "    |    ");
			}

		} else if (eTarget.equals(obj2)) {
			if (!drawMode.equals("LineMode")) {
				drawMode = "LineMode";
				trackedMode.setText(drawMode + "    |    ");
			} else {
				drawMode = "default";
				trackedMode.setText(drawMode + "    |    ");
			}

		} else if (eTarget.equals(obj3)) {
			if (!drawMode.equals("TriangleMode")) {
				drawMode = "TriangleMode";
				trackedMode.setText(drawMode + "    |    ");
			} else {
				drawMode = "default";
				trackedMode.setText(drawMode + "    |    ");
			}

		} else if (eTarget.equals(obj4)) {
			if (!drawMode.equals("RectangleMode")) {
				drawMode = "RectangleMode";
				trackedMode.setText(drawMode + "    |    ");
			} else {
				drawMode = "default";
				trackedMode.setText(drawMode + "    |    ");
			}

		} else if (eTarget.equals(obj5)) {
			if (!drawMode.equals("PolygonMode")) {
				drawMode = "PolygonMode";
				trackedMode.setText(drawMode + "    |    ");
			} else {
				drawMode = "default";
				trackedMode.setText(drawMode + "    |    ");
			}

		} else if (eTarget.equals(selectElements)) {
			if (!drawMode.equals("SelectMode")) {
				drawMode = "SelectMode";
				trackedMode.setText(drawMode + "    |    ");
			} else {
				drawMode = "default";
				trackedMode.setText(drawMode + "    |    ");
			}
		} else if (eTarget.equals(deleteElements)) {
			editor.deleteAffectedObjects();
			editor.clearCurrentSelection();
			 newcanvas.defineSelectionRectangle(null);
			 newcanvas.requestObjectLists(editor);
			newcanvas.repaint();

		}

		else if (eTarget.equals(shiftElements)) {
			if (!drawMode.equals("ShiftMode")) {
				drawMode = "ShiftMode";
				trackedMode.setText(drawMode + "    |    ");
			} else {
				drawMode = "default";
				trackedMode.setText(drawMode + "    |    ");
			}

		} else if (eTarget.equals(csvimport)) {
			try {
				GUICSV.openFileChooserDialog();
				GUICSV.displayObjectFromCSV();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} else if (eTarget.equals(csvexport)) {
			try {
				GUICSV.saveFileChooserDialog();
				GUICSV.exportAsCsv();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (eTarget.equals(db)) {
			dbUI();
		}

	}


}
