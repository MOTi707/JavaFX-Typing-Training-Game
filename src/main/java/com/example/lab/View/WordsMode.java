package com.example.lab.View;

import com.example.lab.Controller.wordsModeController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WordsMode extends Application {

    @Override
    public void start (Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(WordsMode.class.getResource("wordsModes.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        wordsModeController controller = fxmlLoader.getController();
        controller.stage=stage;
        stage.setTitle("单词打字");
        stage.setScene(scene);
        stage.show();
    }


    public static void main (String[] args) {
        launch(args);
    }

    public static void wordsMode (Stage stage,String s,Scene menu) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(WordsMode.class.getResource("wordsModes.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        wordsModeController controller = fxmlLoader.getController();
        controller.initializeText(s);
        controller.menuScene=menu;
        controller.stage=stage;
        stage.setTitle("单词打字");
        stage.setScene(scene);
        stage.show();
    }

    public static void wordsMode (Stage stage,String s) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(WordsMode.class.getResource("wordsModes.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        wordsModeController controller = fxmlLoader.getController();
        controller.initializeText(s);
        controller.stage=stage;
        stage.setTitle("单词打字");
        stage.setScene(scene);
        stage.show();
    }
}
