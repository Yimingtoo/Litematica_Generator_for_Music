package com.yiming.frame;

import com.formdev.flatlaf.FlatLightLaf;
import com.yiming.midi.midiinfo.Instrument;
import com.yiming.midi.midiinfo.MidiInfo;
import com.yiming.midi.midiinfo.MidiTrack;
import net.miginfocom.swing.MigLayout;
import org.apache.poi.ss.formula.functions.T;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TrackComponents extends JPanel {

    JPanel headPanel = new JPanel();
    JLabel trackLabel = new JLabel();
    JCheckBox trackCheckBox = new JCheckBox();
    JPanel contextPanel = new JPanel();
    JLabel label1 = new JLabel();
    JButton soloButton = new JButton("独奏");
    JButton priorityButton = new JButton("主副");
    JTextField priorityTextField = new JTextField("0");
    JSpinner prioritySpinner=new JSpinner();
    private JPanel panel1 = new JPanel();
    private JPanel panel1_1 = new JPanel();
    private JPanel panel1_2 = new JPanel();

    int trackNumber;


    public TrackComponents() {
        initComponents();
        addListener();
    }

    public TrackComponents(MidiInfo midiInfo, MidiTrack midiTrack, boolean fold) {
        this();
        trackNumber = midiTrack.getTrackNumber();
        foldContext(fold);
        setTrackName(midiTrack.getTrackName());
        String str;
        str = "<html>";
        str += "noteArrayListLength: \t" + midiTrack.getNoteArrayList().size();
        str += "<br>";
        str += "Instrument: \t";

        for (int i : midiTrack.getUsedChannel()) {
            str += " " + Instrument.getInstrumentTypeFromChannel(midiInfo.getInstrumentHashSet(), i) + " channel:" + (i);
        }
        str += "<br>";

        str += "TimeRange: \t";
        long[] ticks = midiTrack.getTickRange();
        if (ticks != null) {
            int time = (int) Math.ceil(midiInfo.getSecondTime(ticks[0]));
            str += String.format("%d:%02d", time / 60, time % 60);
            str += " - ";
            time = (int) Math.ceil(midiInfo.getSecondTime(ticks[1]));
            str += String.format("%d:%02d", time / 60, time % 60);
        } else {
            str += "null";
        }
        str += "<br>";

//        str+="channel";
//        str+=;
//

        str += "</html>";

        label1.setText(str);

    }


    //组件初始化
    public void initComponents() {
        this.setLayout(new BorderLayout());
        this.add(headPanel, BorderLayout.NORTH);
        this.add(contextPanel, BorderLayout.SOUTH);
//        this.setLayout(new MigLayout("hidemode 3,gap 0 0", "[grow,fill]", "[]"));
//        this.add(headPanel, "cell 0 0");
//        this.add(contextPanel, "cell 0 1");
        setTopLine(false);

        headPanel.setLayout(new MigLayout("hidemode 3", "[fill]" + "[35:n,fill]" +"[fill]" + "[grow,fill]" +"[35:n,fill]"+"[35:n,fill]", "[fill]"));
        headPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(0xf2, 0xf2, 0xf2)));

        //添加trackCheckBox
        trackCheckBox.setSelected(true);
        headPanel.add(trackCheckBox, "cell 0 0");

        //添加soloButton
        soloButton.setBorder(null);
        soloButton.setBackground(null);
        soloButton.setForeground(null);
        headPanel.add(soloButton, "cell 1 0");

        //添加trackLabel
        trackLabel.setText("Track");
        headPanel.add(trackLabel, "cell 2 0");

        //添加横线
        panel1.setLayout(new BorderLayout());
        headPanel.add(panel1, "cell 3 0");
        panel1_1.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(0xd1, 0xd1, 0xd1)));
        panel1.add(panel1_1, BorderLayout.NORTH);
        panel1.add(panel1_2, BorderLayout.SOUTH);

        //添加 prioritySpinner
        prioritySpinner.setEnabled(false);
        prioritySpinner.setMaximumSize(new Dimension(50,20));
        // 设置 prioritySpinner 最大值和最小值
        prioritySpinner.setModel(new SpinnerNumberModel(0, 0, 10, 1));
        prioritySpinner.setToolTipText("数字越小优先级越高");
        headPanel.add(prioritySpinner, "cell 4 0");

        // 添加priorityButton
        priorityButton.setBorder(null);
        priorityButton.setBackground(null);
        priorityButton.setForeground(null);
        //添加priorityButton的提示信息
        priorityButton.setToolTipText("设置主副选项");
        headPanel.add(priorityButton, "cell 5 0");

        contextPanel.setLayout(new MigLayout("insets 0", "[40:n][grow,fill]", "[]"));

        label1.setText("This is context.");
        contextPanel.add(label1, "cell 1 0");

    }

    //region Folded: 监听事件相关
    //添加监听事件
    private void addListener() {
        //添加 headPanel 的监听事件
        headPanel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                onHeadPanelClicked();
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                //设置 headPanel 背景颜色
                headPanel.setBackground(new Color(0xd1, 0xd1, 0xd1));
                panel1_1.setBackground(new Color(0xd1, 0xd1, 0xd1));
                panel1_2.setBackground(new Color(0xd1, 0xd1, 0xd1));
                panel1_1.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(0xb1, 0xb1, 0xb1)));
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                //设置 headPanel 的背景透明
                headPanel.setBackground(new Color(0xf2, 0xf2, 0xf2));
                panel1_1.setBackground(new Color(0xf2, 0xf2, 0xf2));
                panel1_2.setBackground(new Color(0xf2, 0xf2, 0xf2));
                panel1_1.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(0xd1, 0xd1, 0xd1)));
                repaint();
            }
        });

        //添加 trackCheckBox 的监听事件
        trackCheckBox.addActionListener(e -> {
            ontrackCheckBoxChanged();
        });

        //添加soloButton的监听事件
        soloButton.addActionListener(e -> {
            onSoloButtonClicked();
        });

        //添加priorityButton监听事件
        priorityButton.addActionListener(e -> {
            onPriorityButtonClicked();
        });
    }

    public void onHeadPanelClicked() {
        contextPanel.setVisible(!contextPanel.isVisible());
    }

    public void ontrackCheckBoxChanged() {

    }

    public void onSoloButtonClicked() {
    }
    private void onPriorityButtonClicked() {
        switch (priorityButton.getText()) {
            case "主副":
                priorityButton.setText("主");
                prioritySpinner.setEnabled(true);
                break;
            case "主":
                priorityButton.setText("副");
                prioritySpinner.setEnabled(true);
                break;
            case "副":
                priorityButton.setText("主副");
                prioritySpinner.setEnabled(false);
                break;
        }
    }
    //endregion

    public void setTrackName(String name) {
        trackLabel.setText(name);
    }

    public void setTopLine(boolean isShow) {
        if (isShow) {
            this.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(0xd1, 0xd1, 0xd1)));
        } else {
            //去除 headPanel 的border
            this.setBorder(null);
        }
    }


    public boolean isTrackCheckBoxSelected() {
        return trackCheckBox.isSelected();
    }

    public void foldContext(boolean fold) {
        contextPanel.setVisible(!fold);
    }

    public static void main(String[] args) {
        FlatLightLaf.install();
        JFrame jFrame = new JFrame();

        jFrame.setLayout(new BorderLayout());
//        jFrame.add(new JButton("hello"));
        jFrame.setVisible(true);
        jFrame.setSize(new Dimension(400, 400));

        //设置jFrame到屏幕中央
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new MigLayout(
                "hidemode 3,gap 0 0",
                // columns
                "[grow,fill]",
                // rows
                "[][]"));
        jFrame.add(jPanel);

        TrackComponents trackComponents = new TrackComponents();
        TrackComponents trackComponents1 = new TrackComponents();
        jPanel.add(trackComponents, "cell 0 0");
        trackComponents.setTopLine(false);
        jPanel.add(trackComponents1, "cell 0 1");
    }
}
