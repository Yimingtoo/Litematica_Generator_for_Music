package com.yiming.lite;

import javax.swing.*;

public class Dialog {

//    public static boolean isExit = false;

    public static boolean warningDialog(String text) {
        return showDialog("Warning:" + text, "Warning");
    }

    public static boolean errorDialog(String text) {
        JOptionPane.showMessageDialog(null, "Error:" + text, "Error", JOptionPane.ERROR_MESSAGE);
        System.exit(0);
        return true;
    }

    public static boolean showDialog(String text, String title) {
        Object[] options = {"继续", "结束程序"};  //自定义按钮上的文字
        int m = JOptionPane.showOptionDialog(null, text, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (m == 0) {
            System.out.println("继续");
            return false;
        } else {
            System.out.println("结束");
            System.exit(0);
            return true;
        }

    }

    public static void main(String[] args) {
        errorDialog("dsklfj");
    }

}



