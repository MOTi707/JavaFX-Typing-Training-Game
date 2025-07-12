package com.example.lab.View;

import com.example.lab.Controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class login extends Application {

    @Override
    public void start (Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(WordsMode.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        LoginController controller = fxmlLoader.getController();
        controller.stage=stage;
        stage.setTitle("用户登录");
        stage.setScene(scene);
        stage.show();
    }
    public static void main (String[] args) {
        launch(args);
    }
}
