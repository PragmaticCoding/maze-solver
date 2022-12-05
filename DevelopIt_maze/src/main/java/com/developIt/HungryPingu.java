package com.developIt;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HungryPingu extends Application {
    @Override
    public void start(Stage stage) {
        stage.setScene(new Scene(new Controller().getView()));
        stage.show();
    }
}
