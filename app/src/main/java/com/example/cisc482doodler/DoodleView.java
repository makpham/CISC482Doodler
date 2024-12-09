package com.example.cisc482doodler;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Stack;

public class DoodleView extends View {
    private Paint paint; // Paint object for drawing
    private Path currentPath; // Current path being drawn
    private Stack<Path> undoStack; // Stack to store paths for undo
    private Stack<Path> redoStack; // Stack to store paths for redo
    private float brushSize = 10f; // Default brush size

    public DoodleView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Initialize paint
        paint = new Paint();
        paint.setColor(0xFF000000); // Default black color
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(brushSize); // Default brush size
        paint.setAntiAlias(true);

        // Initialize paths and stacks
        currentPath = new Path();
        undoStack = new Stack<>();
        redoStack = new Stack<>();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw all paths from the undoStack
        for (Path path : undoStack) {
            canvas.drawPath(path, paint);
        }

        // Draw the current path being drawn
        canvas.drawPath(currentPath, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // Start a new path
                currentPath = new Path();
                currentPath.moveTo(x, y);
                break;

            case MotionEvent.ACTION_MOVE:
                // Continue drawing the path
                currentPath.lineTo(x, y);
                break;

            case MotionEvent.ACTION_UP:
                // Add the finished path to the undoStack
                undoStack.push(currentPath);
                currentPath = new Path(); // Reset the current path
                redoStack.clear(); // Clear redoStack when a new path is added
                break;
        }

        invalidate(); // Redraw the view
        return true;
    }

    // Method to get the current brush size
    public float getBrushSize() {
        return brushSize;
    }

    // Method to set the brush size
    public void setBrushSize(float size) {
        this.brushSize = size;
        paint.setStrokeWidth(size); // Update the Paint object
        invalidate(); // Redraw with the new brush size
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            // Move the last path from undoStack to redoStack
            Path lastPath = undoStack.pop();
            redoStack.push(lastPath);
            invalidate(); // Redraw the view
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            // Move the last path from redoStack to undoStack
            Path restoredPath = redoStack.pop();
            undoStack.push(restoredPath);
            invalidate();
        }
    }

    public void clear() {
        // Clear all stacks and the current path
        undoStack.clear();
        redoStack.clear();
        currentPath.reset(); // Reset the current path
        invalidate(); // Redraw the view
    }
}
