package com.example.lab.Controller;

import com.example.lab.View.WordsMode;
import com.example.lab.Model.textProcess;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FunctionController {
    public Scene myself = null;
    private Stage stage;
    @FXML
    public Text prompt;
    @FXML
    public Text fileName;
    @FXML
    public Arc arc1;
    @FXML
    public Arc arc2;
    @FXML
    public Rectangle rec;
    @FXML
    public Text textCustomized;
    @FXML
    public AnchorPane mainPane;
    @FXML
    public AnchorPane barrierPane;
    @FXML
    private AnchorPane sloganPane;

    private String s = "";


    public Stage getStage () {
        return stage;
    }

    public void setStage (Stage stage) {
        this.stage = stage;
    }


    @FXML
    private void inputClick () {
        //输入txt文本
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择需要练习打字的txt文件");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("选择txt文本文件", "*.txt")
        );
        File file = fileChooser.showOpenDialog(stage);

        //尝试读取文件
        if (file != null) {
            try {
                s = new String(Files.readAllBytes(file.toPath()));
                prompt.setVisible(true);
                //读取文件名字
                String fileNameText = file.getName();
                fileName.setText(fileNameText);
                fileName.setVisible(true);
                arc1.setFill(Color.DODGERBLUE);
                arc2.setFill(Color.DODGERBLUE);
                rec.setFill(Color.DODGERBLUE);
                textCustomized.setDisable(false);
                arc1.setDisable(false);
                arc2.setDisable(false);
                rec.setDisable(false);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    @FXML
    private void customizedGame () throws IOException {
        //自定义文本开始游戏
        //先对 文本进行半角全角西文中文的识别转换
        s = textProcess.formatPunctuation(s);
        WordsMode.wordsMode(stage, s, myself);
    }

    @FXML
    public void initialize () {
        initialMainGraph();
        initializeGraph();
    }

    public void initialMainGraph () {
        Image image = new Image("photo1.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(
                image, null, null, null,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO,
                        false, false, true, false));

        mainPane.setBackground(new Background(backgroundImage));
    }

    public void initializeGraph () {
        Image image = new Image("levelSelect.png");
        BackgroundImage backgroundImage = new BackgroundImage(
                image, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, null,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO,
                        false, false, true, false));

        barrierPane.setBackground(new Background(backgroundImage));
    }

    public void normalGame () {
        barrierPane.setVisible(true);
        barrierPane.setDisable(false);
        GaussianBlur gaussianBlur = new GaussianBlur(15);
        mainPane.setEffect(gaussianBlur);
        sloganPane.setVisible(false);
    }

    public void cancel () {
        barrierPane.setVisible(false);
        barrierPane.setDisable(true);
        mainPane.setEffect(null);
        sloganPane.setVisible(true);
    }

    public void b1 () throws IOException {
        String s = getIOTxt("b1");
        WordsMode.wordsMode(stage, s, myself);
    }

    public void b2 () throws IOException {
        String s = getIOTxt("b2");
        WordsMode.wordsMode(stage, s, myself);
    }

    public void b3 () throws IOException {
        String s = getIOTxt("b3");
        WordsMode.wordsMode(stage, s, myself);
    }

    public String getIOTxt (String barrier) {
        String s = "";
        String filePath = "src/main/resources/" + barrier + ".txt";
        try {
            s = Files.readString(Paths.get(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }
}