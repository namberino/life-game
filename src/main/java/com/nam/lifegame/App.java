package com.nam.lifegame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class App extends Application
{
    @Override
    public void start(Stage stage)
    {
        Label label = new Label("Game of Life");
        MainView mainView = new MainView();
        Scene scene = new Scene(mainView, 840, 680);
        stage.setScene(scene);
        stage.show();

        mainView.draw();
    }

    public static void main(String[] args)
    {
        launch();
    }
}