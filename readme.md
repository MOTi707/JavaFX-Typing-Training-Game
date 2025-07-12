  
![](https://cdn.nlark.com/yuque/0/2025/png/56925099/1752337459225-d47a9661-8e1f-4bf8-a974-bec3352cca68.png)

  
![](https://cdn.nlark.com/yuque/0/2025/png/56925099/1752337462686-2eeea486-8f45-4397-9628-8970412f2d10.png)![](https://cdn.nlark.com/yuque/0/2025/png/56925099/1752337466284-81aca29d-e952-4290-917a-1bf6062b3909.png)

![](https://cdn.nlark.com/yuque/0/2025/png/56925099/1752337472481-6209fd21-4e22-440b-8e17-74331b891b15.png)

![](https://cdn.nlark.com/yuque/0/2025/png/56925099/1752337487560-d8fa910f-afd8-41f8-90cc-593e8db7f6a4.png)**  
**

****

**一、****          ****调试分析**

4.1    问题以及修复情况总览

| <font style="color:black;">问题编号</font> | <font style="color:black;">问题内容</font> | <font style="color:black;">优先级别</font> | <font style="color:black;">解决情况</font> |
| :--- | :--- | --- | :---: |
| <font style="color:black;">1</font> | <font style="color:black;">动画异常：实现聚焦动画时，只能放大一次，如果后续持续缩小，背景将离开舞台</font> | <font style="color:black;">重要</font><font style="color:black;">(major)</font> | <font style="color:black;">解决</font><font style="color:black;">(resolved)</font> |
| <font style="color:black;">2</font> | <font style="color:black;">面板异常：无法达到想要的不同</font><font style="color:black;">pane</font><font style="color:black;">面板具有不同透明度，以区分重点，引导用户视线</font> | <font style="color:black;">次要</font><font style="color:black;">(minor)</font> | <font style="color:black;">解决</font><font style="color:black;">(resolved)</font> |
| <font style="color:black;">3</font> | <font style="color:black;">监听事件异常：不同</font><font style="color:black;">pane</font><font style="color:black;">下的按钮无法点击</font> | <font style="color:black;">重要</font><font style="color:black;">(major)</font> | <font style="color:black;">解决</font><font style="color:black;">(resolved)</font> |
| <font style="color:black;">4</font> | <font style="color:black;">功能异常：暂停游戏时，虽然正确率，完成度的实时计算得到了暂停，但是用户仍然能够输入，实现作弊。</font> | <font style="color:black;">紧急</font><font style="color:black;">(critical)</font> | <font style="color:black;">减轻</font><font style="color:black;">(alleviated)</font> |
| <font style="color:black;">5</font> | <font style="color:black;">功能异常：重新开始游戏，游戏虽然数据得到初始化，但是需要打字的内容仍然延续上局游戏的内容</font> | <font style="color:black;">次要</font><font style="color:black;">(minor)</font> | <font style="color:black;">解决</font><font style="color:black;">(resolved)</font> |
| <font style="color:black;">6</font> | <font style="color:black;">功能异常：更换难度后，会再次新建一个计时器，导致计时速度翻倍</font> | <font style="color:black;">紧急</font><font style="color:black;">(critical)</font> | <font style="color:black;">解决</font><font style="color:black;">(resolved)</font> |
| <font style="color:black;">7</font> | <font style="color:black;">工具栏异常：菜单点击无效果</font> | <font style="color:black;">重要</font><font style="color:black;">(major)</font> | <font style="color:black;">解决</font><font style="color:black;">(resolved)</font> |
| <font style="color:black;">8</font> | <font style="color:black;">用户体验异常：正确率更新速度快，数据闪动，用户体验不佳</font> | <font style="color:black;">重要</font><font style="color:black;">(major)</font> | <font style="color:black;">解决</font><font style="color:black;">(resolved)</font> |
| <font style="color:black;">9</font> | <font style="color:black;">用户体验异常：玩家在打字过程中，无法返回主菜单进行自定义文本导入或者重新选择关卡</font> | <font style="color:black;">重要</font><font style="color:black;">(major)</font> | <font style="color:black;">解决</font><font style="color:black;">(resolved)</font> |
| <font style="color:black;">10</font> | <font style="color:black;">IO</font><font style="color:black;">异常</font><font style="color:black;">:</font><font style="color:black;">如果导入非</font><font style="color:black;">txt</font><font style="color:black;">文件，并不会在一开始报错，到最后进入游戏后会造成没有需要打字练习的字符异常</font> | <font style="color:black;">重要</font><font style="color:black;">(major)</font> | <font style="color:black;">解决</font><font style="color:black;">(resolved)</font> |
| | | | |
| | | <font style="color:black;"></font> | |




4.2** ****    **详细调试修复过程

1.动画异常：实现聚焦动画时，只能放大一次，如果后续持续缩小，背景将离开舞台

Javafx类库方法中，scaleTransition.setToX(rate); 这行代码设置了在X轴上的放大倍数，rate的值决定了放大的程度。具体来说：

初始值：当rate为1时，表示没有放大，元素保持原始大小。

放大倍数：每次调用zoomIn()方法时，rate会乘以1.08。这意味着如果你调用zoomIn()两次，rate会变成1.08（第一次放大）和1.1664（第二次放大）。这样，每次放大的比例都是基于当前状态，而不是从原始大小重新计算。

效果：因此，setToX(rate)设置的值会逐步增加，使得每次放大后的元素大小是累积的，形成总的放大效果。

简单来说，rate控制了元素在X轴的缩放程度，随着每次放大，元素会越来越大。这样就可以和缩小的方法zoomOut相配合，实现聚焦放大，失焦缩小的效果。

  
Bug产生原因：对Java类库方法使用不熟悉，没注意看API类库说明文档。



  




2.面板异常：无法达到想要的不同pane面板具有不同透明度，以区分重点，引导用户视线

在JavaFX中，每个Pane类似于PhotoShop中的图层。对当前Pane属性的更改仅会影响该Pane及其下方组成部分的属性。这种设计限制了对Pane内容的修改，无法像三原色叠加那样影响所在像素区域的所有组成部分。所以，为了实现预期的效果，合理地设计每个Pane的层级关系至关重要。通过精确规划每个Pane的附属关系，开发者可以有效控制界面的表现和交互效果，从而实现复杂的UI设计和视觉效果。

以下为重新设计的Javafx从属关系和实现的效果

合理的搭配Slogan层和选择关卡层的pane，合理的改变每一层的透明度，颜色搭配关系，模糊效果和阴影效果，以实现吸睛的视觉效果。

  
  


  
  


3.监听事件异常：不同pane下的按钮无法点击  
不同的组件虽然被设置了visible的属性，以改变其可见度，但是即便这个按钮不可见，但它仍然是可点击的，可实现其效果；如果在一个pane的按钮被覆盖在它上面的pane所遮挡，即使按钮能够显示出来，也是无法点击触发相应的事件的。因此，需要合理的设置可见性，可操作性和不同pane间的关系。

  


4.功能异常：暂停游戏时，虽然正确率，完成度的实时计算得到了暂停，但是用户仍然能够输入，实现作弊。

<font style="color:#871094;background-color:#FFFFFF;">pausePane</font><font style="color:#080808;background-color:#FFFFFF;">.setVisible(</font><font style="color:#0033b3;background-color:#FFFFFF;">true</font><font style="color:#080808;background-color:#FFFFFF;">);  
</font><font style="color:#871094;background-color:#FFFFFF;">pausePane</font><font style="color:#080808;background-color:#FFFFFF;">.setDisable(</font><font style="color:#0033b3;background-color:#FFFFFF;">false</font><font style="color:#080808;background-color:#FFFFFF;">);  
  
</font><font style="color:#871094;background-color:#FFFFFF;">pauseLabel</font><font style="color:#080808;background-color:#FFFFFF;">.setVisible(</font><font style="color:#0033b3;background-color:#FFFFFF;">true</font><font style="color:#080808;background-color:#FFFFFF;">);  
</font><font style="color:#871094;background-color:#FFFFFF;">timeline</font><font style="color:#080808;background-color:#FFFFFF;">.stop(); </font>_<font style="color:#8c8c8c;background-color:#FFFFFF;">// </font>__<font style="color:#8c8c8c;background-color:#FFFFFF;">停止计时器</font>__<font style="color:#8c8c8c;background-color:#FFFFFF;">  
</font>_<font style="color:#871094;background-color:#FFFFFF;">userTextLabel</font><font style="color:#080808;background-color:#FFFFFF;">.setVisible(</font><font style="color:#0033b3;background-color:#FFFFFF;">false</font><font style="color:#080808;background-color:#FFFFFF;">);  
</font>_<font style="color:#8c8c8c;background-color:#FFFFFF;">//</font>__<font style="color:#8c8c8c;background-color:#FFFFFF;">高斯模糊</font>__<font style="color:#8c8c8c;background-color:#FFFFFF;">  
</font>_<font style="color:black;background-color:#FFFFFF;">GaussianBlur gaussianBlur </font><font style="color:#080808;background-color:#FFFFFF;">= </font><font style="color:#0033b3;background-color:#FFFFFF;">new </font><font style="color:#080808;background-color:#FFFFFF;">GaussianBlur(</font><font style="color:#1750eb;background-color:#FFFFFF;">18</font><font style="color:#080808;background-color:#FFFFFF;">);  
</font><font style="color:#871094;background-color:#FFFFFF;">pane</font><font style="color:#080808;background-color:#FFFFFF;">.setEffect(</font><font style="color:black;background-color:#FFFFFF;">gaussianBlur</font><font style="color:#080808;background-color:#FFFFFF;">);</font>

暂停游戏时，虽然相关计时器得到了关闭，导致相关的数据不会得到更新，但是键盘输入的检测仍然在进行，因此玩家仍然可以进行输入；最开始尝试使用禁用显示用户输入的标签以实现禁止输入效果（因为程序设计是从前端显示的标签来获取用户字符串的），但实验验证该方法不可行。到最后发现，这其实是程序设计问题，无法解决。

  
所以，我在此采用一种取巧的方法，在暂停后，取消显示需要打字的字符串和用户已经打字的字符，并且对背景进行重度的高斯模糊处理，防止用户偷看。



5.功能异常：重新开始游戏，游戏虽然数据得到初始化，但是需要打字的内容仍然延续上局游戏的内容

在打字练习界面添加一个属性：保存原始的需要打字的内容，在重新开始游戏后，再次进行打字内容的初始化。

```plain
private ArrayList <String>words=new ArrayList<>(8);

private ArrayList <String>copyOfwords=new ArrayList<>(8);
```

private String longText="";<font style="color:#080808;">  
</font>

<font style="color:#080808;"></font>

6.功能异常：更换难度后，会再次新建一个计时器，导致计时速度翻倍

```plain
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
```



在JavaFX中，Timeline类用于创建动画和定时任务，而在上面的代码中，对timeline和timelineForSpeed的判断主要是为了确保在每次调用时不会重复初始化这些对象。首先，timeline和timelineForSpeed在未被创建时为null，通过检查它们的状态，可以防止在每次更新时创建新的Timeline实例，从而避免不必要的资源消耗和潜在的内存泄漏。

若timeline或timelineForSpeed已被实例化，再次尝试创建它们将导致多个计时器同时运行。这不仅会导致更新逻辑的重复执行，还可能造成界面响应的混乱，影响应用程序的整体性能。因此，通过仅在它们为null时创建新的Timeline，确保了每种定时器在应用程序生命周期内仅存在一个实例。此外，setCycleCount(Timeline.INDEFINITE)方法的使用确保了计时器的无限循环，这意味着一旦启动，它们会持续运行，直到手动停止。



7.工具栏异常：菜单点击无效果

如果创建了一个 Menu，但没有添加任何 MenuItem。这样做是合法的，但用户无法选择或进行任何操作，因此通常建议至少添加一个 MenuItem 以增强用户体验。虽然可以创建空菜单，但建议根据实际需要设计用户界面，以便让用户能够清楚地理解如何进行操作。

  




8.用户体验异常：正确率更新速度快，数据闪动，用户体验不佳  
       更新数据刷新的逻辑，将正确率更新的方法移到检测到输入正确的单词后执行，这样既能降低性能消耗，又能提高用户体验。

```plain
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
```



9.用户体验异常：玩家在打字过程中，无法返回主菜单进行自定义文本导入或者重新选择关卡

在每一个打字窗口类里添加主菜单的scene，使其能够自由切换。因为一定是在功能选择之后才能进入游戏，所以可以不用担心主菜单的scene为null的情况。但是，如果用户在主菜单选择的是从关卡选择进入游戏，那么在游戏中返回主菜单后也会显示关卡选择的pane，但这样比重新new一个主菜单出来相比，大大减少了内存消耗。

  




10.IO异常:如果导入非txt文件，并不会在一开始报错，到最后进入游戏后会造成没有需要打字练习的字符异常  
       添加相应的提示语，并且加入判断语句，保证用户输入的是合法的txt文本文件。具体代码如下

```plain
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

        try {…} catch (IOException ioException) {

            ioException.printStackTrace();

        }

    }

}
```



4.3** ****    **调试分析

1.动画异常：问题出现在实现聚焦动画时，放大动画只能执行一次，后续缩小动画导致背景离开舞台。修复方法是调整scaleTransition的setToX(rate)方法，确保每次放大都是基于当前状态而非原始大小重新计算。

2.面板异常：不同面板无法实现不同的透明度，以区分重点并引导用户视线。问题在于对JavaFX的Pane层级关系理解不足。修复方法是通过合理设计Pane的层级关系，实现预期的视觉效果。

3.监听事件异常：不同面板下的按钮无法点击。问题在于即使按钮不可见，它仍然是可点击的。修复方法是合理设置可见性、可操作性以及不同面板间的关系。

4.功能异常：暂停游戏时，用户仍能输入，导致作弊。修复方法是在暂停时取消显示需要打字的字符串和用户已输入的字符，并对背景进行高斯模糊处理。

5.功能异常：重新开始游戏时，需要打字的内容延续上局游戏的内容。修复方法是在打字练习界面添加属性保存原始的需要打字内容，并在重新开始游戏后进行初始化。

6.功能异常：更换难度后，会新建一个计时器，导致计时速度翻倍。修复方法是确保在每次调用时不会重复初始化Timeline对象，避免多个计时器同时运行。

7.工具栏异常：菜单点击无效果。修复方法是至少添加一个MenuItem以增强用户体验。

8.用户体验异常：正确率更新速度快，数据闪动。修复方法是将正确率更新的逻辑移到检测到输入正确的单词后执行。

9.用户体验异常：玩家无法在打字过程中返回主菜单。修复方法是在每个打字窗口类中添加主菜单的scene，允许自由切换。

10.IO异常：导入非txt文件时不会报错。修复方法是添加提示语和判断语句，确保用户输入的是合法的txt文本文件。



<font style="color:#14151a;"></font>

