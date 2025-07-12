package com.example.lab.Model;

//已测试
public class textProcess {
    // 去除多余的空格
    public static String removeExtraSpaces(String text) {
        return text.trim().replaceAll("\\s+", " ");
    }

    // 判断是否是中文文本
    public static boolean isMostlyChinese(String text) {
        int chineseCount = 0;
        int totalCount = 0;
        for (char c : text.toCharArray()) {
            if (Character.toString(c).matches("[\\p{IsHan}]")) {
                chineseCount++;
            }
            if (!Character.isWhitespace(c)) {
                totalCount++;
            }
        }

        if (totalCount>0&&((double) chineseCount / totalCount) > 0.5){
            return true;
        }
        return false;
    }

    // 根据文本格式化标点符号
    public static String formatPunctuation(String text) {
        //中文文本  转全角
        if (isMostlyChinese(text)) {
            text=text.replaceAll(",", "，")  // 替换中文逗号
                    .replaceAll("//.", "。")  // 替换中文句号
                    .replaceAll("!", "！");
            char[] c=text.toCharArray();
            for (int i = 0; i < c.length; i++)
            {
                if (c[i]==32)
                {
                    c[i]=(char)12288;
                    continue;
                }
                if (c[i]<127)
                    c[i]=(char)(c[i]+65248);
            }
            return new String(c);
        }
        //全角转半角 英文转纯英文
        else {
            text=text.replaceAll("，", ",")  // 替换中文逗号
                     .replaceAll("。", ".")  // 替换中文句号
                    .replaceAll("！", "!")
                    .replaceAll("’","'")
                    .replaceAll("‘","'");
            char[] c = text.toCharArray();
            for (int i = 0; i < c.length; i++) {
                if (c[i] == 12288) {
                    //全角空格为12288，半角空格为32
                    c[i] = (char) 32;
                    continue;
                }
                if (c[i] > 65280 && c[i] < 65375)
                    //其他字符半角(33-126)与全角(65281-65374)的对应关系是：均相差65248
                    c[i] = (char) (c[i] - 65248);
            }
            return new String(c);
        }
    }
}