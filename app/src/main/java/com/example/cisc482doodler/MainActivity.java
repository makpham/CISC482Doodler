package com.example.cisc482doodler;

import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private DoodleView doodleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the DoodleView
        FrameLayout doodleContainer = findViewById(R.id.doodleContainer);
        doodleView = new DoodleView(this, null);
        doodleContainer.addView(doodleView);

        // Set up the BRUSH button
        Button brushButton = findViewById(R.id.brushButton);
        brushButton.setOnClickListener(v -> showBrushSizeDialog());

        // Set up the CLEAR button
        Button clearButton = findViewById(R.id.clearButton);
        clearButton.setOnClickListener(v -> doodleView.clear());

        // Set up the UNDO button
        Button undoButton = findViewById(R.id.undoButton);
        undoButton.setOnClickListener(v -> doodleView.undo());

        // Set up the REDO button
        Button redoButton = findViewById(R.id.redoButton);
        redoButton.setOnClickListener(v -> doodleView.redo());
    }

    private void showBrushSizeDialog() {
        // Brush size dialog implementation remains unchanged
    }
}
