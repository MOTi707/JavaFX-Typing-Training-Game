package com.example.lab.Controller;

import com.example.lab.View.WordsMode;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class wordsModeController {
    public Stage stage=null;
    public Scene menuScene=null;
    private boolean pause=false;
    private ArrayList <String>words=new ArrayList<>(8);
    private ArrayList <String>copyOfwords=new ArrayList<>(8);
    private int currentCorrectWords=0;
    private int totalWords=0;
    private int totalTime=45;
    private Timeline timeline=null;
    private Timeline timelineForSpeed=null;
    private int timeForSpeed=0;
    private int durationTime=0;
    private int letterNumber=0;
    private int correctLetter=0;
    private boolean showText=true;
    private boolean end=false;
    private boolean endProcess =false;
    private String longText="";



    @FXML
    private AnchorPane pane;
    @FXML
    private AnchorPane endPane;
    @FXML
    private Label userTextLabel;
    @FXML
    private Label timeLabel;
    @FXML
    private Label processLabel;
    @FXML
    private Label speedLabel;
    @FXML
    private Label wordToTypeLabel;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private ProgressBar timeBar;
    @FXML
    private Label pauseLabel;
    @FXML
    private Label correctRatetLabel;
    @FXML
    private AnchorPane pausePane;

    @FXML
    public void startHit(ActionEvent event){
    }


    @FXML
    public void keyboardHit(KeyEvent event){
        //获取键码
        KeyCode code = event.getCode();
        String text = event.getText();

        if (code==KeyCode.BACK_SPACE){
            //判断label的长度
           if (!userTextLabel.getText().isEmpty()){
               userTextLabel.setText(userTextLabel.getText().substring(0,userTextLabel.getText().length()-1));
           }
        }
        else if(code==KeyCode.ENTER){
            nextWord();
        }
        //点击空格  执行暂停
        else if (code==KeyCode.F1) {
            pause= !pause;
            pauseAction();
        }
        //输入的是文本
        else {
            letterNumber++;
            userTextLabel.setText(userTextLabel.getText()+text);
            //执行正误判断
            String userText=userTextLabel.getText();//用户的文本
            for (String each:wordToTypeLabel.getText().split(" ")){
                if(userText.equals(each)){
                    //随机变换一个颜色
                    Random random = new Random();
                    Color randomColor = new Color(random.nextDouble(), random.nextDouble(), random.nextDouble(), 1.0);
                    userTextLabel.setTextFill(randomColor);

                    currentCorrectWords++;
                    correctLetter+=userText.length();
                    //标签面板减少1个
                    words.remove(userText);
                    //输入框清空
                   userTextLabel.setText("");
                    //更新正确率
                    initializeCorrectRate();
                    initializeWordLabel();
                    initializeProcess();
                    if (words.isEmpty()){
                        if (!endProcess){
                            endProcess=!endProcess;
                            achieveAll();
                        }
                        String next=copyOfwords.get(random.nextInt(copyOfwords.size()));
                        wordToTypeLabel.setText(next);
                    }
                    break;
                }
            }

        }
    }

    public void resetWordToTypeLabel(){
        StringBuilder content= new StringBuilder();
        for (String each:copyOfwords){
            content.append(each).append(" ");
        }
        wordToTypeLabel.setText(content.toString());
    }


    public void level1 () {
        totalTime=45;
        durationTime=0;
        currentCorrectWords=0;
        letterNumber=0;
        correctLetter=0;

        resetWordToTypeLabel();
        userTextLabel.setText("");
        initializeProcess();
        initializeCorrectRate();
        initializeSpeed();
    }
    public void level2 (){
        totalTime=30;
        durationTime=0;
        currentCorrectWords=0;
        letterNumber=0;
        correctLetter=0;

        resetWordToTypeLabel();
        userTextLabel.setText("");
        initializeProcess();
        initializeCorrectRate();
        initializeSpeed();
    }
    public void level3 (){
        totalTime=20;
        durationTime=0;
        currentCorrectWords=0;
        letterNumber=0;
        correctLetter=0;

        resetWordToTypeLabel();
        userTextLabel.setText("");
        initializeProcess();
        initializeCorrectRate();
        initializeSpeed();
    }
    public void level4 (){
        totalTime=15;
        durationTime=0;
        currentCorrectWords=0;
        letterNumber=0;
        correctLetter=0;

        resetWordToTypeLabel();
        userTextLabel.setText("");
        initializeProcess();
        initializeCorrectRate();
        initializeSpeed();
    }
    public void changeTextVisibility(){
        //现在是展示文本，改为不展示
        if (showText){
            showText=!showText;
            wordToTypeLabel.setVisible(false);
        }
        //现在是不展示文本，改为展示
        else{
            showText=!showText;
            wordToTypeLabel.setVisible(true);
        }
    }

    @FXML
    public void initialize (){
        //words.clear();
        initializeGraph();
        initializeTimer();
    }

    //设置标签的字
    public void initializeWordLabel(){
        StringBuilder content= new StringBuilder();
        for (String each:words){
            content.append(each).append(" ");
        }
        wordToTypeLabel.setText(content.toString());
    }
    public void initializeProcess(){
        if (!end){
            String s=currentCorrectWords+" / "+ totalWords;
            processLabel.setText(s);
            progressBar.setProgress(currentCorrectWords/(double)totalWords);
        }
    }
    public void initializeTimer(){
        // 每秒执行一次
        if (timeline==null){
            timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateTimer()));
        }
        if (timelineForSpeed==null){
            timelineForSpeed = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateTimerForSpeed()));
        }
        timeline.setCycleCount(Timeline.INDEFINITE); // 无限循环
        timeline.play(); // 启动计时器
        timelineForSpeed.setCycleCount(Timeline.INDEFINITE); // 无限循环
        timelineForSpeed.play(); // 启动计时器
    }
    private void updateTimerForSpeed(){
        timeForSpeed++;
        initializeSpeed();
    }
    private void updateTimer() {
        durationTime++;

        if (!end){
            timeLabel.setText(durationTime+" / "+totalTime);
            //更新进度条
            timeBar.setProgress(durationTime/(double)totalTime);
            //更新进度条
        }

        //判断是否超时，结束
        if (durationTime/(double)totalTime>=1){
            /*pauseLabel.setVisible(false);
            timeline.stop(); // 停止计时器
            userTextLabel.setVisible(true);*/
            timeline.stop();
            endPane.setVisible(true);
            //装饰性内容
            GaussianBlur gaussianBlur = new GaussianBlur(10);
            pane.setEffect(gaussianBlur);

        }
    }

    public void initializeSpeed(){
        double speed=letterNumber/((double)timeForSpeed/60);
        String temp=String.valueOf(Math.round(speed))+" WPM";
        speedLabel.setText(temp);
    }

    public void initializeCorrectRate(){
        if (!end){
            double rate=correctLetter/(double) letterNumber*100;
            correctRatetLabel.setText(String.valueOf(Math.round(rate)) + "%"  );
        }

    }

    public void achieveAll(){
        //打完所有字数，仍然继续练习
        end=true;
        timeLabel.setDisable(true);
        processLabel.setDisable(true);
        correctRatetLabel.setDisable(true);
        timeline.stop();
    }

    public void nextWord(){
        if (end){
            Random random = new Random();
            String next=copyOfwords.get(random.nextInt(copyOfwords.size()));
            wordToTypeLabel.setText(next);
        }
    }

    public void pauseAction(){
        if (pause){
            //执行暂停
            pausePane.setVisible(true);
            pausePane.setDisable(false);

            pauseLabel.setVisible(true);
            timeline.stop(); // 停止计时器
            userTextLabel.setVisible(false);
            //高斯模糊
            GaussianBlur gaussianBlur = new GaussianBlur(18);
            pane.setEffect(gaussianBlur);
        }
        else {
            //终止暂停
            pauseLabel.setVisible(false);
            timeline.play(); // 停止计时器
            userTextLabel.setVisible(true);
            pane.setEffect(null);

            pausePane.setVisible(false);
            pausePane.setDisable(true);
        }
    }

    public void exitGame(){
        stage.close();
    }
    public void help(){
        pause=true;
        pauseAction();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("提示");
        alert.setHeaderText(null); // 可以设置为null，避免显示默认标题
        alert.setContentText("""
                你可以选择不同的难度

                按F1表示开始或暂停

                按backspace(退格)删除一个字符

                在结束一轮打字练习后可按Enter(回车)进入下一个单词

                选择功能菜单栏内的关闭游戏按钮可关闭游戏""");

        alert.showAndWait(); // 显示对话框并等待用户关闭
        pause=false;
        pauseAction();
    }
    public void initializeGraph(){
        Image image = new Image("photo2.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(
                image, null, null, null,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO,
                        false, false, true, false));

        pane.setBackground(new Background(backgroundImage));
    }
    public void restart() throws IOException {
        WordsMode.wordsMode(stage,longText,menuScene);
    }


    public void initializeText(String s){
        s = s.replace(",", " ").replace(".", " ");
        String[] words = s.split(" ");
        this.longText=s;
        for (String each:words){
            this.words.add(each);
            this.copyOfwords.add(each);
        }
        totalWords=this.words.size();
        initializeWordLabel();
        initializeProcess();
    }

    public void backToMenu(){
        stage.setTitle("主菜单");
        stage.setScene(menuScene);
        stage.show();
    }
}