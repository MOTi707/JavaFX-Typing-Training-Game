package com.example.lab.Controller;

import com.example.lab.View.HelloApplication;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class LoginController {

    public Stage stage;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private AnchorPane photoPane;
    @FXML
    private AnchorPane bodyPane;
    @FXML
    private AnchorPane backPane;
    @FXML
    private TextField textField;

    private boolean isZoom=false;
    private double rate=1;



    public void zoomIn(){
        if (!isZoom){
            GaussianBlur gaussianBlur = new GaussianBlur(14);
            backPane.setEffect(gaussianBlur);

            // 获取 Pane 的宽度和高度
            double centerX = backPane.getWidth() / 2;
            double centerY = backPane.getHeight() / 2;
            ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.3), backPane);
            rate*=1.08;
            scaleTransition.setToX(rate); // 设置放大目标X倍数
            scaleTransition.setToY(rate); // 设置放大目标Y倍数
            scaleTransition.setCycleCount(1); // 设置为播放一次

            isZoom=!isZoom;
            scaleTransition.play();
        }
    }

    public void zoomOut(){
        if (isZoom){
            backPane.setEffect(null);

            double centerX = backPane.getWidth() / 2;
            double centerY = backPane.getHeight() / 2;
            ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.3), backPane);
            rate=rate/1.08;
            scaleTransition.setToX(rate); // 设置放大目标X倍数
            scaleTransition.setToY(rate); // 设置放大目标Y倍数
            scaleTransition.setCycleCount(1); // 设置为播放一次

            isZoom=!isZoom;
            scaleTransition.play();
        }
    }


    public void initialize () {
        initialField();
        initialMainGraph();
    }

    public void initialField(){

        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                // 聚焦时执行的操作
                zoomIn();
            }
        });
    }
    public void initialMainGraph () {
        Image image = new Image("photo1.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(
                image, null, null, null,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO,
                        false, false, true, false));

        photoPane.setBackground(new Background(backgroundImage));
    }

    public void zoomOutAndLostFocus(){
        zoomOut();
        bodyPane.requestFocus();
    }


    public void gotoFunction() throws IOException {
        String s=textField.getText();
        if (!s.isEmpty()){
            stage.setTitle("");
            Scene fun= HelloApplication.getScene(stage);
            stage.setScene(fun);
            stage.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("提示");
            alert.setHeaderText(null);
            alert.setContentText("    哥们，你还没输入账户名");
            alert.showAndWait();
        }

    }

}
