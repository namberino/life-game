/*
This class handles the simulation start, stop and step along with redrawing the board
 */

package com.nam.lifegame;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;

public class Simulator
{
    private final Timeline timeline;
    private final MainView mainView;
    private final Simulation simulation;

    public Simulator(MainView mainView, Simulation simulation)
    {
        this.mainView = mainView;
        this.simulation = simulation;
        this.timeline = new Timeline(new KeyFrame(Duration.millis(150), this::doStep));
        this.timeline.setCycleCount(Timeline.INDEFINITE);
    }

    private void doStep(ActionEvent actionEvent)
    {
        this.simulation.step();
        this.mainView.draw();
    }

    public void start()
    {
        this.timeline.play();
    }

    public void stop()
    {
        this.timeline.stop();
    }
}