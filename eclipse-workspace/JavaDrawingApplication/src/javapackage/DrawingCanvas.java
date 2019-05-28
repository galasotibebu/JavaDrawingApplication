package javapackage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * The ToolDrawPanel class requests ArrayLists with geometry objects from an
 * ObjectManager object and displays them.
 * 
 * @author
 *
 */
@SuppressWarnings("serial")
public class DrawingCanvas extends JPanel {

	// ----------NEW FEATURES-------------------------------------------
	/**
	 * ArrayList of PointFeature objects to be drawn
	 */
	private ArrayList<PointFeature> PointLists = new ArrayList<>();
	/**
	 * ArrayList of LineFeature objects to be drawn
	 */
	private ArrayList<LineFeature> LineLists = new ArrayList<>();
	/**
	 * ArrayList of TriangleFeature objects to be drawn
	 */
	private ArrayList<TriangleFeature> TriangleLists = new ArrayList<>();
	/**
	 * ArrayList of RectangleFeature objects to be drawn
	 */
	private ArrayList<RectangleFeature> RectangleLists = new ArrayList<>();
	// ------------------------------------------------------------------

	// ------------Currently Selected--------------------------------------
	/**
	 * ArrayList of PointFeature objects that are currently selected (will be drawn
	 * in another color for visual feedback)
	 */
	private ArrayList<PointFeature> selectedPointLists = new ArrayList<>();
	/**
	 * ArrayList of LineFeature objects that are currently selected (will be drawn
	 * in another color for visual feedback)
	 */
	private ArrayList<LineFeature> selectedLineLists = new ArrayList<>();
	/**
	 * ArrayList of TriangleFeature objects that are currently selected (will be
	 * drawn in another color for visual feedback)
	 */
	private ArrayList<TriangleFeature> selectedTriangleLists = new ArrayList<>();
	/**
	 * ArrayList of RectangleFeature objects that are currently selected (will be
	 * drawn in another color for visual feedback)
	 */
	private ArrayList<RectangleFeature> selectedRectangleLists = new ArrayList<>();
	// ---------------------------------------------------------------------------

	// -------------Altered Features-----------------------------------------------
	/**
	 * ArrayList of the different states of LineFeature objects that modified by
	 * 'move' or 'selected'
	 */
	private ArrayList<LineFeature> Line_drawing = new ArrayList<>();
	/*
	 * ArrayList of the different states of TriangleFeature objects that modified by
	 * 'move' or 'selected'
	 */
	private ArrayList<TriangleFeature> Triangle_drawing = new ArrayList<>();
	/**
	 * ArrayList of the different states of RectangleFeature objects that modified
	 * by 'move' or 'selected'
	 */
	private ArrayList<RectangleFeature> Rectangle_drawing = new ArrayList<>();

	// ::::::::::::::::::::::::::::::::::::::::::::::::::::
	// ::

	// --------------------------------------------------------------------
	// SELECTOR -
	// --------------------------------------------------------------------
	/**
	 * Defines the selection rectangle for displaying it
	 */
	private Rectangle2D selectionRectangle = null;
	// ------------------------------------------------------------

	/**
	 * Sets the selection Rectangle
	 * 
	 * @author
	 * @param Rectangle
	 */
	public void defineSelectionRectangle(Rectangle2D Rectangle) {
		this.selectionRectangle = Rectangle;
	}

	// --------------------------------------------------------------------
	// BRINGS ACTUALLY CREATED & SELECTED FEATURES FROM EDITORTOOLS CLASS to deal
	// with them -
	// --------------------------------------------------------------------
	/**
	 * Replaces the current contents of the ArrayLists in the DrawingCanvas with the
	 * contents of the corresponding ArrayLists in the Editor class
	 * 
	 * @author
	 * @param editor
	 */
	public void requestObjectLists(EditorTools editor) {
		// Draw Objects
		PointLists = editor.drawingPoints;
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
	/*
	 * public void storeDrawingPointElements(PointFeature point) {
	 * Point_drawing.add(point); }
	 */
	public void storeDrawingLineElements(LineFeature line) {
		Line_drawing.add(line);
	}

	/**
	 * Adds a TriangleFeature at the end of the corresponding ArrayList.
	 * 
	 * @author
	 * @param triangle
	 */
	public void storeDrawingTriangleElements(TriangleFeature triangle) {
		Triangle_drawing.add(triangle);
	}

	/**
	 * Adds a RectangleFeature at the end of the corresponding ArrayList.
	 * 
	 * @author
	 * @param rectangle
	 */
	public void storeDrawingRectangleElements(RectangleFeature rectangle) {
		Rectangle_drawing.add(rectangle);
	}// ................... new feature creater..............................

	// --------------------------------------------------------------------
	// i will come later here
	// --------------------------------------------------------------------
	/**
	 * Clears all ArrayLists for live-displaying objects when drawn
	 * 
	 * @author
	 */
	public void clearDrawingElements() {
		// Point_drawing.clear()
		Line_drawing.clear();
		Triangle_drawing.clear();
		Rectangle_drawing.clear();
	}// -------------------------------------------------------------------

	// --------------------------------------------------------------------
	// DO DRAWING IN CANVAS FOR NEW FEATURES & SELECTION BOX
	// --------------------------------------------------------------------
	/*
	 * public class DrawingPanel extends JPanel implements MouseListener,
	 * MouseMotionListener {
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Color drawColor = new Color(0, 0, 0, 0.75f);
		// g2d.setPaint(drawColor);//used to generate color during the rendering process

		Color drawingColor = Color.cyan;
		g2d.setPaint(drawingColor);

		Color selectionColor = Color.red;// new Color(1, 0, 1, 1f);
		g2d.setPaint(selectionColor);// used to generate color during the rendering process

		// ::::::::::::::::::::::::::::::::::::::::::::::::::::
		// Draw Point, Line, Triangle, Rectangle ::
		// ::::::::::::::::::::::::::::::::::::::::::::::::::::
		// Draw Points
		PointLists.forEach((PointFeature point) -> {
			Ellipse2D pt = point.createPointFeature();
			g2d.fill(pt);
		});

		// Draw Lines
		LineLists.forEach((LineFeature line) -> {
			Line2D ln = line.createLineFeature();
			g2d.draw(ln);
			g2d.setPaint(drawingColor);
		});

		// Draw Triangles
		TriangleLists.forEach((TriangleFeature triangle) -> {
			Path2D pth_t = triangle.createTriangleFeature();
			g2d.draw(pth_t);
			g2d.setPaint(drawingColor);
		});

		
		
		// Draw Rectangles
		RectangleLists.forEach((RectangleFeature rectangle) -> {
			Rectangle2D rct = rectangle.createRectangleFeature();
			g2d.draw(rct);
			g2d.setPaint(drawingColor);
		});

		// ::::::::::::::::::::::::::::::::::::::::::::::::::::::::
		// Draw Selection Box ::
		// ::::::::::::::::::::::::::::::::::::::::::::::::::::::::

		// Draw Selections

		if (selectionRectangle != null) {
			g2d.draw(selectionRectangle);
		}

		// Draw selected Objects in Red
		selectedPointLists.forEach((PointFeature point) -> {
			Ellipse2D ptS = point.createPointFeature();
			g2d.fill(ptS);
			g2d.setPaint(selectionColor);
		});

		selectedLineLists.forEach((LineFeature line) -> {
			Line2D lnS = line.createLineFeature();
			g2d.draw(lnS);
			g2d.setPaint(selectionColor);
		});

		selectedTriangleLists.forEach((TriangleFeature triangle) -> {
			Path2D trS = triangle.createTriangleFeature();
			g2d.draw(trS);
			g2d.setPaint(selectionColor);
		});

		selectedRectangleLists.forEach((RectangleFeature rectangle) -> {
			Rectangle2D rcS = rectangle.createRectangleFeature();
			g2d.draw(rcS);
			g2d.setPaint(selectionColor);
		});

		// ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
		// Live Display of states of objects modified by 'move' or 'change'::
		// ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

		Line_drawing.forEach((LineFeature line) -> {
			Line2D dln = line.createLineFeature();
			g2d.draw(dln);
		});

		Triangle_drawing.forEach((TriangleFeature drawtriangle) -> {
			Path2D dtr = drawtriangle.createTriangleFeature();
			g2d.draw(dtr);
		});

		Rectangle_drawing.forEach((RectangleFeature drawRectangle) -> {
			Rectangle2D drc = drawRectangle.createRectangleFeature();
			g2d.draw(drc);
		});

	}
	

}
