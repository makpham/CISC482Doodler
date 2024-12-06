package com.example.cisc482doodler;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Stack;

public class DoodleView extends View {
    private Paint paint;
    private ArrayList<Point> points;
    private Stack<Point> undoStack;
    private boolean isInverted = false;
    private float brushSize = 10f; // Default brush size

    public DoodleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(0xFF000000); // Black color by default
        paint.setStrokeWidth(brushSize);
        points = new ArrayList<>();
        undoStack = new Stack<>();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isInverted) {
            paint.setColor(0xFFFFFFFF);
        } else {
            paint.setColor(0xFF000000);
        }

        for (Point point : points) {
            paint.setStrokeWidth(brushSize);
            canvas.drawPoint(point.x, point.y, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_DOWN) {
            points.add(new Point(event.getX(), event.getY()));
            invalidate();
        }
        return true;
    }

    // Get current brush size
    public float getBrushSize() {
        return brushSize;
    }

    // Set brush size
    public void setBrushSize(float size) {
        brushSize = size;
        paint.setStrokeWidth(size);
        invalidate(); // Redraw with the new brush size
    }

    // Clear the doodle
    public void clear() {
        points.clear();
        undoStack.clear(); // Clear undo history as well
        invalidate(); // Redraw the view
    }

    // Invert the colors
    public void invertColors() {
        isInverted = !isInverted; // Toggle inversion
        invalidate(); // Redraw the view with the new colors
    }

    // Undo the last action
    public void undo() {
        if (!points.isEmpty()) {
            // Remove the last point and save it to the undo stack
            undoStack.push(points.remove(points.size() - 1));
            invalidate(); // Redraw the view
        }
    }

    // Redo the last undone action
    public void redo() {
        if (!undoStack.isEmpty()) {
            // Restore the last undone point back to the points list
            points.add(undoStack.pop());
            invalidate(); // Redraw the view
        }
    }

    // Class to store a point's coordinates
    private static class Point {
        float x, y;

        Point(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }
}