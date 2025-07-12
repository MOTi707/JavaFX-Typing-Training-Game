package com.example.lab.View;

import com.example.lab.Controller.FunctionController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start (Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        //传参
        FunctionController controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.myself = (scene);
        stage.setTitle("主菜单");
        stage.setScene(scene);
        stage.show();
    }

    public static void main (String[] args) {
        launch();
    }

    public static Scene getScene(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        //传参
        FunctionController controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.myself = (scene);
        return scene;
    }
}