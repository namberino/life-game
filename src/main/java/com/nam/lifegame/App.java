package com.nam.lifegame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application
{
    @Override
    public void start(Stage stage)
    {
        MainView mainView = new MainView();
        Scene scene = new Scene(mainView, 1050, 690);
        stage.setScene(scene);
        stage.show();

        mainView.draw();
    }

    public static void main(String[] args)
    {
        launch();
    }
}