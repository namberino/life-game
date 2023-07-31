package com.nam.lifegame;

import com.nam.lifegame.model.CellState;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class InfoBar extends HBox
{
    private final Label cursor;
    private final Label editingTool;

    public InfoBar()
    {
        this.editingTool = new Label();
        this.cursor = new Label();

        Pane spacer = new Pane();
        spacer.setMinSize(0, 0);
        spacer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        HBox.setHgrow(spacer, Priority.ALWAYS);

        this.getChildren().addAll(this.editingTool, spacer, this.cursor);
    }

    public void setDrawMode(CellState drawMode)
    {
        String drawModeFormat = "Draw Mode: %s";
        String drawModeString;
        if (drawMode == CellState.ALIVE)
        {
            drawModeString = "Drawing";
        }
        else
        {
            drawModeString = "Erasing";
        }

        this.editingTool.setText(String.format(drawModeFormat, drawModeString));
    }

    public void setCursorPosition(int x, int y)
    {
        String cursorPosFormat = "Cursor: (%d, %d)";
        this.cursor.setText(String.format(cursorPosFormat, x, y));
    }
}