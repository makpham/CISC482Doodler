package com.example.cisc482doodler;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.TextView;

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

        // Set up the INVERT button
        Button invertButton = findViewById(R.id.invertButton);
        invertButton.setOnClickListener(v -> doodleView.invertColors());

        // Set up the UNDO button
        Button undoButton = findViewById(R.id.undoButton);
        undoButton.setOnClickListener(v -> doodleView.undo());

        // Set up the REDO button
        Button redoButton = findViewById(R.id.redoButton);
        redoButton.setOnClickListener(v -> doodleView.redo());
    }

    private void showBrushSizeDialog() {
        // Create a dialog with a SeekBar to adjust the brush size
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Brush Size");

        // Inflate a custom layout for the dialog
        SeekBar seekBar = new SeekBar(this);
        seekBar.setMax(50); // Maximum brush size
        seekBar.setProgress((int) doodleView.getBrushSize()); // Set current brush size

        // TextView to display the current brush size
        TextView sizePreview = new TextView(this);
        sizePreview.setText("Brush Size: " + doodleView.getBrushSize() + " px");
        sizePreview.setPadding(20, 20, 20, 20);

        // Add a listener to update the preview when the SeekBar is moved
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                sizePreview.setText("Brush Size: " + progress + " px");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Create a layout for the dialog
        FrameLayout layout = new FrameLayout(this);
        layout.setPadding(40, 20, 40, 20);
        layout.addView(sizePreview);
        layout.addView(seekBar);

        // Add the layout to the dialog
        builder.setView(layout);

        // Add "OK" button to save the selected brush size
        builder.setPositiveButton("OK", (dialog, which) -> doodleView.setBrushSize(seekBar.getProgress()));

        // Add "Cancel" button to close the dialog without changes
        builder.setNegativeButton("Cancel", null);

        // Show the dialog
        builder.create().show();
    }
}