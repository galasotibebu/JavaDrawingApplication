package javapackage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.*;
import java.util.ArrayList;
import javax.swing.JPanel;

// Drawing canvas class requests ArrayLists with geometry objects from an
// EditorTools class and displays them.
@SuppressWarnings("serial")
public class DrawingCanvas extends JPanel {

	// ----------NEW FEATURES-------------------------------------------

	// ArrayList of PointFeature objects to be drawn
	private ArrayList<PointFeature> PointLists = new ArrayList<>();

	// ArrayList of LineFeature objects to be drawn
	private ArrayList<LineFeature> LineLists = new ArrayList<>();

	// ArrayList of TriangleFeature objects to be drawn
	private ArrayList<TriangleFeature> TriangleLists = new ArrayList<>();

	// ArrayList of RectangleFeature objects to be drawn
	private ArrayList<RectangleFeature> RectangleLists = new ArrayList<>();

	// ------------Currently Selected--------------------------------------

	// ArrayList of PointFeature objects that are currently selected
	private ArrayList<PointFeature> selectedPointLists = new ArrayList<>();

	// ArrayList of LineFeature objects that are currently selected
	private ArrayList<LineFeature> selectedLineLists = new ArrayList<>();

	// ArrayList of TriangleFeature objects that are currently selected
	private ArrayList<TriangleFeature> selectedTriangleLists = new ArrayList<>();

	// ArrayList of RectangleFeature objects that are currently selected
	private ArrayList<RectangleFeature> selectedRectangleLists = new ArrayList<>();
	// ---------------------------------------------------------------------------

	// -------------Altered Features-----------------------------------------------

	// ArrayList to store different states of LineFeature objects that is altered by
	// different tools
	private ArrayList<PointFeature> Point_drawing = new ArrayList<>();

	// ArrayList to store different states of LineFeature objects that is altered by
	// different tools
	private ArrayList<LineFeature> Line_drawing = new ArrayList<>();

	// ArrayList to store different states of TriangleFeature objects that is
	// altered by different tools
	private ArrayList<TriangleFeature> Triangle_drawing = new ArrayList<>();

	// ArrayList to store different states of RectangleFeature objects that is
	// altered by tools
	private ArrayList<RectangleFeature> Rectangle_drawing = new ArrayList<>();

	// --------------------------------------------------------------------
	// SELECTOR -
	// --------------------------------------------------------------------
	// Defines the selection rectangle
	private Rectangle2D selectionRectangle = null;

	// Sets the selection Rectangle
	//will get rectangle drwn in main class as parameter
	public void defineSelectionRectangle(Rectangle2D Rectangle) {
		this.selectionRectangle = Rectangle;
		
	}

	// --------------------------------------------------------------------
	// BRINGS ACTUALLY CREATED & SELECTED FEATURES FROM EDITORTOOLS CLASS to deal
	// with them -
	// --------------------------------------------------------------------

	// Replaces the current contents of the ArrayLists in the DrawingCanvas with the
	// contents of the corresponding ArrayLists in the EditorTools class
	public void requestObjectLists(EditorTools editor) {
		// Draw Objects
		PointLists = editor.drawingPoints;//assigns points drawn in main-->EditorTools to PointLists in current class
		LineLists = editor.drawingLines;
		TriangleLists = editor.drawingTriangles;
		RectangleLists = editor.drawingRectangles;
		// Selection Objects
		selectedPointLists = editor.selectedPoints;
		selectedLineLists = editor.selectedLines;
		selectedTriangleLists = editor.selectedTriangles;
		selectedRectangleLists = editor.selectedRectangles;
	}
	// --------------------------------------------------------------------
	// NEW FEATURE CREATER IN ACTUAL DWRAWING-CANVAS CLASS
	// --------------------------------------------------------------------

	// Appends new PointFeature to corresponding Arraylist
	public void storeDrawingPointElements(PointFeature point) {
		Point_drawing.add(point);
	}

	// Appends new LineFeature to corresponding Arraylist
	public void storeDrawingLineElements(LineFeature line) {
		Line_drawing.add(line);
	}

	// Appends new TriangleFeature to corresponding Arraylist
	public void storeDrawingTriangleElements(TriangleFeature triangle) {
		Triangle_drawing.add(triangle);
	}

	// Appends new RectangleFeature to corresponding Arraylist
	public void storeDrawingRectangleElements(RectangleFeature rectangle) {
		Rectangle_drawing.add(rectangle);
	}

	// --------------------------------------------------------------------
	// i will come later here
	// --------------------------------------------------------------------

	// Clears all ArrayLists for live-displaying objects when drawn
	public void clearDrawingElements() {
		Point_drawing.clear();
		Line_drawing.clear();
		Triangle_drawing.clear();
		Rectangle_drawing.clear();
	}

	// --------------------------------------------------------------------
	// DO DRAWING IN CANVAS FOR NEW FEATURES & SELECTION BOX
	// --------------------------------------------------------------------

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		Color selectionColor = Color.cyan;
		g2d.setPaint(selectionColor);// used to generate color during the rendering process

		Color drawingColor = Color.black;
		g2d.setPaint(drawingColor);
		
		Color selectionBoxColor = Color.red;
		g2d.setPaint(selectionBoxColor);

		// ----------------------------------------------------
		// Draw Point, Line, Triangle, Rectangle
		// ----------------------------------------------------
		// Draw Points
		for (PointFeature point : PointLists) {
			Ellipse2D pt = point.createPointFeature();
			g2d.fill(pt);
			g2d.setPaint(drawingColor);
		}

		// Draw Lines
		for (LineFeature line : LineLists) {
			Line2D ln = line.createLineFeature();
			g2d.draw(ln);
			g2d.setPaint(drawingColor);
		}

		// Draw Triangles
		for (TriangleFeature triangle : TriangleLists) {
			Path2D pth_t = triangle.createTriangleFeature();
			g2d.draw(pth_t);
			g2d.setPaint(drawingColor);
		}

		// Draw Rectangles
		for (RectangleFeature rectangle : RectangleLists) {
			Rectangle2D rct = rectangle.createRectangleFeature();
			g2d.draw(rct);
			g2d.setPaint(drawingColor);
		}

		// --------------------------------------------------------
		// Draw Selection Box
		// --------------------------------------------------------	
		
		if (selectionRectangle != null) {
			g2d.draw(selectionRectangle);
			g2d.setPaint(selectionBoxColor);
		}

		// --------------------------------------------------------
		// Draw Selected objects
		// --------------------------------------------------------
		for (PointFeature point : selectedPointLists) {
			Ellipse2D pts = point.createPointFeature();
			g2d.fill(pts);
			g2d.setPaint(selectionColor);
		}

		for (LineFeature line : selectedLineLists) {
			Line2D lns = line.createLineFeature();
			g2d.draw(lns);
			g2d.setPaint(selectionColor);
		}

		for (TriangleFeature triangle : selectedTriangleLists) {
			Path2D trs = triangle.createTriangleFeature();
			g2d.draw(trs);
			g2d.setPaint(selectionColor);
		}

		for (RectangleFeature rectangle : selectedRectangleLists) {
			Rectangle2D rcs = rectangle.createRectangleFeature();
			g2d.draw(rcs);
			g2d.setPaint(selectionColor);
		}

		// ------------------------------------------------------------------
		// Displays states of objects modified
		// -------------------------------------------------------------------
		
		for(PointFeature point: Point_drawing) {
			Ellipse2D pts = point.createPointFeature();
			g2d.draw(pts);
		}

		for(LineFeature line: Line_drawing) {
			Line2D lns = line.createLineFeature();
			g2d.draw(lns);
		}

		for(TriangleFeature drawtriangle: Triangle_drawing) {
			Path2D trs = drawtriangle.createTriangleFeature();
			g2d.draw(trs);
		}

		for(RectangleFeature drawRectangle: Rectangle_drawing) {
			Rectangle2D rcts = drawRectangle.createRectangleFeature();
			g2d.draw(rcts);
		}

	}

}
