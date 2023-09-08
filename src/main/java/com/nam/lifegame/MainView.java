/*
This class displays the app and all the contents inside the app
 */

package com.nam.lifegame;

import com.nam.lifegame.model.Board;
import com.nam.lifegame.model.BoundedBoard;
import com.nam.lifegame.model.CellState;
import com.nam.lifegame.model.StandardRule;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public class MainView extends VBox
{
    public static final int EDITING = 0;
    public static final int SIMULATING = 1;

    private final InfoBar infoBar;
    private final Canvas canvas;
    private final Affine affine;
    private Simulation simulation;
    private final Board initialBoard;
    private CellState drawMode = CellState.ALIVE;
    private int applicationState = EDITING;

    public MainView()
    {
        this.canvas = new Canvas(1200, 631);
        this.canvas.setOnMousePressed(this::handleDraw);
        this.canvas.setOnMouseDragged(this::handleDraw);
        this.canvas.setOnMouseMoved(this::handleMoved);
        this.setOnKeyPressed(this::onKeyPressed);

        Toolbar toolbar = new Toolbar(this);

        this.infoBar = new InfoBar();
        this.infoBar.setDrawMode(this.drawMode);
        this.infoBar.setCursorPosition(0, 0);

        Pane spacer = new Pane();
        spacer.setMinSize(0, 0);
        spacer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        VBox.setVgrow(spacer, Priority.ALWAYS);

        this.getChildren().addAll(toolbar, this.canvas, spacer, infoBar);

        this.affine = new Affine();
        this.affine.appendScale(150 / 10f, 150 / 10f);

        this.initialBoard = new BoundedBoard(100, 100);
    }

    private void handleMoved(MouseEvent mouseEvent)
    {
        Point2D simCoord = this.getSimulationCoordinates(mouseEvent);
        this.infoBar.setCursorPosition((int) simCoord.getX(), (int) simCoord.getY());
    }

    private void onKeyPressed(KeyEvent keyEvent)
    {
        if (keyEvent.getCode() == KeyCode.D)
        {
            this.drawMode = CellState.ALIVE;
            infoBar.setDrawMode(drawMode);
        }
        else if (keyEvent.getCode() == KeyCode.E)
        {
            this.drawMode = CellState.DEAD;
            infoBar.setDrawMode(drawMode);
        }
    }

    private void handleDraw(MouseEvent event)
    {
        if (this.applicationState == SIMULATING)
        {
            return;
        }

        Point2D simCoord = this.getSimulationCoordinates(event);

        int simX = (int) simCoord.getX();
        int simY = (int) simCoord.getY();

        this.initialBoard.setState(simX, simY, drawMode);
        draw();
    }

    private Point2D getSimulationCoordinates(MouseEvent event)
    {
        double mouseX = event.getX();
        double mouseY = event.getY();

        try
        {
            return this.affine.inverseTransform(mouseX, mouseY);
        }
        catch (NonInvertibleTransformException e)
        {
            throw new RuntimeException("Non invertible transform");
        }
    }

    public void draw()
    {
        GraphicsContext g = this.canvas.getGraphicsContext2D();
        g.setTransform(this.affine);

        g.setFill(Color.LIGHTGRAY);
        g.fillRect(0, 0, 600, 600);

        if (this.applicationState == EDITING)
        {
            drawSimulation(this.initialBoard);
        }
        else
        {
            drawSimulation(this.simulation.getBoard());
        }

        g.setStroke(Color.GRAY);
        g.setLineWidth(0.05);
        for (int x = 0; x <= this.initialBoard.getWidth(); x++)
        {
            g.strokeLine(x, 0, x, 100);
        }

        for (int y = 0; y <= this.initialBoard.getHeight(); y++)
        {
            g.strokeLine(0, y, 100, y);
        }
    }

    private void drawSimulation(Board simulationToDraw)
    {
        GraphicsContext g = this.canvas.getGraphicsContext2D();

        g.setFill(Color.BLACK);
        for (int x = 0; x < simulationToDraw.getWidth(); x++)
        {
            for (int y = 0; y < simulationToDraw.getHeight(); y++)
            {
                if (simulationToDraw.getState(x, y) == CellState.ALIVE)
                {
                    g.fillRect(x, y, 1, 1);
                }
            }
        }
    }

    public Simulation getSimulation()
    {
        return this.simulation;
    }

    public void setDrawMode(CellState newDrawMode)
    {
        this.drawMode = newDrawMode;
        this.infoBar.setDrawMode(newDrawMode);
    }

    public void setApplicationState(int applicationState)
    {
        if (applicationState == this.applicationState)
        {
            return;
        }

        if (applicationState == SIMULATING)
        {
            this.simulation = new Simulation(this.initialBoard, new StandardRule());
        }

        this.applicationState = applicationState;
    }

    public int getApplicationState()
    {
        return applicationState;
    }

    public void resetBoard()
    {
        resetSim(this.initialBoard);

        GraphicsContext g = this.canvas.getGraphicsContext2D();
        g.setTransform(this.affine);

        g.setFill(Color.LIGHTGRAY);
        g.fillRect(0, 0, 600, 600);

        if (this.applicationState == EDITING)
        {
            drawSimulation(this.initialBoard);
        }
        else
        {
            drawSimulation(this.simulation.getBoard());
        }

        g.setStroke(Color.GRAY);
        g.setLineWidth(0.05);
        for (int x = 0; x <= this.initialBoard.getWidth(); x++)
        {
            g.strokeLine(x, 0, x, 100);
        }

        for (int y = 0; y <= this.initialBoard.getHeight(); y++)
        {
            g.strokeLine(0, y, 100, y);
        }
    }

    private void resetSim(Board simulationToDraw)
    {
        for (int x = 0; x < simulationToDraw.getWidth(); x++)
        {
            for (int y = 0; y < simulationToDraw.getHeight(); y++)
            {
                if (simulationToDraw.getState(x, y) == CellState.ALIVE)
                {
                    simulationToDraw.setState(x, y, CellState.DEAD);
                }
            }
        }
    }
}