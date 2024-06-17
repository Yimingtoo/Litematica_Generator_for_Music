/*
 * Created by JFormDesigner on Thu Mar 21 17:25:59 GMT+08:00 2024
 */

package com.yiming.frame;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.management.remote.JMXAddressable;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultCaret;

import com.formdev.flatlaf.FlatLightLaf;
import com.yiming.lite.ComposeMusic;
import com.yiming.lite.Convert;
import com.yiming.midi.CtrlMidi;
import com.yiming.midi.midiinfo.MidiTrack;
import net.miginfocom.swing.*;
import org.apache.poi.ss.formula.functions.T;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * @author Administrator
 */
public class MyFrame extends JFrame {


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        menuBar1 = new JMenuBar();
        menu1 = new JMenu();
        menuItem1 = new JMenuItem();
        exportMenuItem = new JMenuItem();
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        toolPanel = new JPanel();
        playAllCheckBox = new JCheckBox();
        foldButton = new JButton();
        scrollPane1 = new JScrollPane();
        midiInfoPanel = new JPanel();
        tipPanel = new JPanel();
        tipLabel = new JLabel();
        panel1 = new JPanel();
        buttonBar = new JPanel();
        okButton = new JButton();
        slider1 = new JSlider();
        timeDisplayLabel = new JLabel();
        menuBar2 = new JMenuBar();
        speedMenu = new JMenu();
        speedMenuItem0_5x = new JMenuItem();
        speedMenuItem0_75x = new JMenuItem();
        speedMenuItem1_0x = new JMenuItem();
        speedMenuItem1_25x = new JMenuItem();
        speedMenuItem1_5x = new JMenuItem();
        speedMenuItem2_0x = new JMenuItem();
        speedTextField = new JTextField();
        statusBar = new JPanel();
        statusLabel = new JLabel();

        //======== this ========
        setIconImage(new ImageIcon(getClass().getResource("/images/play.png")).getImage());
        setTitle("\u4e50\u5668\u6295\u5f71\u751f\u6210\u5668");
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== menuBar1 ========
        {

            //======== menu1 ========
            {
                menu1.setText("\u6587\u4ef6");

                //---- menuItem1 ----
                menuItem1.setText("\u6253\u5f00...");
                menu1.add(menuItem1);

                //---- exportMenuItem ----
                exportMenuItem.setText("\u5bfc\u51fa...");
                menu1.add(exportMenuItem);
            }
            menuBar1.add(menu1);
        }
        setJMenuBar(menuBar1);

        //======== dialogPane ========
        {
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(new BorderLayout());

                //======== toolPanel ========
                {
                    toolPanel.setLayout(new MigLayout(
                        "insets 4 0 4 0",
                        // columns
                        "[5:n,fill]" +
                        "[fill]" +
                        "[40:n,fill]" +
                        "[grow,fill]" +
                        "[fill]",
                        // rows
                        "[grow,fill]"));

                    //---- playAllCheckBox ----
                    playAllCheckBox.setText("\u64ad\u653e\u5168\u90e8");
                    toolPanel.add(playAllCheckBox, "cell 1 0");

                    //---- foldButton ----
                    foldButton.setText("\u5c55\u5f00");
                    foldButton.setBorder(null);
                    foldButton.setBackground(null);
                    foldButton.setForeground(null);
                    toolPanel.add(foldButton, "cell 2 0");
                }
                contentPanel.add(toolPanel, BorderLayout.NORTH);

                //======== scrollPane1 ========
                {
                    scrollPane1.setPreferredSize(new Dimension(100, 100));
                    scrollPane1.setMaximumSize(new Dimension(100, 100));
                    scrollPane1.setBorder(null);

                    //======== midiInfoPanel ========
                    {
                        midiInfoPanel.setLayout(new MigLayout(
                            "hidemode 3",
                            // columns
                            "[grow,fill]",
                            // rows
                            "[grow,fill]"));

                        //======== tipPanel ========
                        {
                            tipPanel.setLayout(new BorderLayout());

                            //---- tipLabel ----
                            tipLabel.setText("\u70b9\u51fb\u4ee5\u6253\u5f00\u6587\u4ef6");
                            tipLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
                            tipLabel.setHorizontalAlignment(SwingConstants.CENTER);
                            tipPanel.add(tipLabel, BorderLayout.CENTER);
                        }
                        midiInfoPanel.add(tipPanel, "cell 0 0");
                    }
                    scrollPane1.setViewportView(midiInfoPanel);
                }
                contentPanel.add(scrollPane1, BorderLayout.CENTER);
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== panel1 ========
            {
                panel1.setLayout(new BorderLayout());

                //======== buttonBar ========
                {
                    buttonBar.setLayout(new MigLayout(
                        "fillx,insets panel,alignx center",
                        // columns
                        "[::50,fill]" +
                        "[::60,center]" +
                        "[200:n,fill]" +
                        "[:80:80,fill]" +
                        "[::50,fill]" +
                        "[::50,fill]",
                        // rows
                        null));

                    //---- okButton ----
                    okButton.setSelectedIcon(null);
                    okButton.setIcon(null);
                    okButton.setText("\u25ba");
                    okButton.setToolTipText("\u64ad\u653e");
                    buttonBar.add(okButton, "cell 1 0,width 25:25:25");

                    //---- slider1 ----
                    slider1.setMaximum(1000);
                    slider1.setValue(0);
                    buttonBar.add(slider1, "cell 2 0,growx");

                    //---- timeDisplayLabel ----
                    timeDisplayLabel.setText("0:00/0:00");
                    buttonBar.add(timeDisplayLabel, "cell 3 0,alignx center,growx 0");

                    //======== menuBar2 ========
                    {

                        //======== speedMenu ========
                        {
                            speedMenu.setText("\u500d\u901f");

                            //---- speedMenuItem0_5x ----
                            speedMenuItem0_5x.setText("0.5x");
                            speedMenu.add(speedMenuItem0_5x);

                            //---- speedMenuItem0_75x ----
                            speedMenuItem0_75x.setText("0.75x");
                            speedMenu.add(speedMenuItem0_75x);

                            //---- speedMenuItem1_0x ----
                            speedMenuItem1_0x.setText("1.0x");
                            speedMenu.add(speedMenuItem1_0x);

                            //---- speedMenuItem1_25x ----
                            speedMenuItem1_25x.setText("1.25x");
                            speedMenu.add(speedMenuItem1_25x);

                            //---- speedMenuItem1_5x ----
                            speedMenuItem1_5x.setText("1.5x");
                            speedMenu.add(speedMenuItem1_5x);

                            //---- speedMenuItem2_0x ----
                            speedMenuItem2_0x.setText("2.0x");
                            speedMenu.add(speedMenuItem2_0x);
                            speedMenu.add(speedTextField);
                        }
                        menuBar2.add(speedMenu);
                    }
                    buttonBar.add(menuBar2, "cell 4 0");
                }
                panel1.add(buttonBar, BorderLayout.CENTER);

                //======== statusBar ========
                {
                    statusBar.setOpaque(false);
                    statusBar.setPreferredSize(new Dimension(10, 20));
                    statusBar.setLayout(new BorderLayout());

                    //---- statusLabel ----
                    statusLabel.setPreferredSize(null);
                    statusBar.add(statusLabel, BorderLayout.EAST);
                }
                panel1.add(statusBar, BorderLayout.SOUTH);
            }
            dialogPane.add(panel1, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }


    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JMenuBar menuBar1;
    private JMenu menu1;
    private JMenuItem menuItem1;
    private JMenuItem exportMenuItem;
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JPanel toolPanel;
    private JCheckBox playAllCheckBox;
    private JButton foldButton;
    private JScrollPane scrollPane1;
    private JPanel midiInfoPanel;
    private JPanel tipPanel;
    private JLabel tipLabel;
    private JPanel panel1;
    private JPanel buttonBar;
    private JButton okButton;
    private JSlider slider1;
    private JLabel timeDisplayLabel;
    private JMenuBar menuBar2;
    private JMenu speedMenu;
    private JMenuItem speedMenuItem0_5x;
    private JMenuItem speedMenuItem0_75x;
    private JMenuItem speedMenuItem1_0x;
    private JMenuItem speedMenuItem1_25x;
    private JMenuItem speedMenuItem1_5x;
    private JMenuItem speedMenuItem2_0x;
    private JTextField speedTextField;
    private JPanel statusBar;
    private JLabel statusLabel;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    String openFilePath = "./";
    String saveFilePath = "./";
    String fileName = "untitled";
    CtrlMidi ctrlMidi;
    MyThread myThread;
    StatusLabelThread statusLabelThread;
    ArrayList<TrackComponents> trackComponentsArrayList = new ArrayList<>();

    ArrayList<Integer> mainList = new ArrayList<>();
    ArrayList<Integer> viceList = new ArrayList<>();

    public MyFrame() {
        //关闭窗口后结束程序
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //JFormDesigner生成
        initComponents();

        //读取初始文件
        readInitJson();
        //组件初始化
        //设置最小窗口尺寸
        setMinimumSize(new Dimension(this.getWidth() + 100, this.getHeight() + 100));
        setSize(800, 450);
        okButton.setSize(30, 30);

        //设置menuBar2的背景为透明
        menuBar2.setBackground(new Color(240, 240, 240));
        //去除menuBar2的border
        menuBar2.setBorder(BorderFactory.createEmptyBorder());
        //设置speedTextField居中显示
        speedTextField.setHorizontalAlignment(SwingConstants.CENTER);
        speedTextField.setText("自定义");
        //设置 scrollPane1 的滚动速度
        scrollPane1.getVerticalScrollBar().setUnitIncrement(10);
        //设置playAllCheckBox全选
        playAllCheckBox.setSelected(true);

        // 设置toolPanel不可见
        toolPanel.setVisible(false);

        // 设置 foldButton背景透明
        foldButton.setBackground(new Color(240, 240, 240));

        menuBar1.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(0xd1, 0xd1, 0xd1)));
        toolPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(0xd1, 0xd1, 0xd1)));
        // 设置toolPanel的margin为0
//        // midiInfoPanel的初始化
//        initMidiInfoPanel();
    }

    class MyThread extends Thread {
        boolean isWait;

        @Override
        public void run() {
            while (ctrlMidi == null) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("Thread start.");
            while (true) {
                double playRate = ctrlMidi.getSecondTime() / ctrlMidi.getMusicTime();
                int slider1Position = (int) (slider1.getMaximum() * playRate);
                slider1.setValue(slider1Position);
//                System.out.println(slider1Position + "\t" + slider1.getValue());
                if (slider1Position == slider1.getMaximum()) {
                    ctrlMidi.stopPlay();
                    ctrlMidi.setTickPosition(0);
                    slider1.setValue(0);
                    stopPlaying();
                }
                //修改显示时长
                String playTimeLength = ctrlMidi.convertDisplayTime((int) Math.ceil(ctrlMidi.getMusicTime()));
                String currnetPlayTime = ctrlMidi.convertDisplayTime((int) (ctrlMidi.getMusicTime() * playRate));
                timeDisplayLabel.setText(currnetPlayTime + "/" + playTimeLength);
                try {
                    Thread.sleep(200);
                    while (isWait) {
                        Thread.sleep(10);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    class StatusLabelThread extends Thread {
        public int countDown = -1;

        @Override
        public void run() {
            while (true) {
                repaint();
                //延时1s显示一次
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                if (countDown < 0) {
                    continue;
                } else if (countDown == 0) {
                    statusLabel.setText("");
                }
                countDown--;
            }
        }
    }

    public void readInitJson() {
        //读取json文件
        File file = new File("init.json");
        try {
            if (file.exists()) {
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] readBytes = fileInputStream.readAllBytes();
                String str = new String(readBytes);
                System.out.println(str);
                JSONObject json = new JSONObject(str);
                // 检查json是否含有lastDirPath这个key
                if (json.has("lastOpenDirPath")) {
                    openFilePath = json.getString("lastOpenDirPath");
                    System.out.println(openFilePath);
                }
                if (json.has("lastSaveDirPath")) {
                    saveFilePath = json.getString("lastSaveDirPath");
                    System.out.println(openFilePath);
                }

                fileInputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initListener() {
        //添加okButton的点击监听事件
        okButton.addActionListener(e -> {
            if (ctrlMidi != null) {
                playOrStop();
            }
        });

        //添加一个鼠标松开slider1的监听事件
        slider1.addMouseListener(new MouseAdapter() {
            static boolean isContinue = false;

            @Override
            public void mousePressed(MouseEvent e) {
                if (ctrlMidi == null) {
                    return;
                }
                super.mousePressed(e);
                myThread.isWait = true;
//                System.out.println("mouseclicked slider1");
                if (ctrlMidi.isPlaying()) {
                    playOrStop();
                    isContinue = true;
                } else {
                    isContinue = false;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (ctrlMidi == null) {
                    return;
                }
                super.mouseReleased(e);
                //改变sequencer中的tick
                long tickPosition = ctrlMidi.getTickLength() * slider1.getValue() / slider1.getMaximum();
//                System.out.println("mouseReleased slider1:" + tickPosition);
                ctrlMidi.setTickPosition(tickPosition);
                if (isContinue) {
                    playOrStop();
                }
                myThread.isWait = false;
            }
        });
        slider1.addChangeListener(e -> {
            if (ctrlMidi == null) {
                return;
            }
            if (!ctrlMidi.isPlaying()) {
                double playRate = ((double) slider1.getValue()) / slider1.getMaximum();
                String playTimeLength = ctrlMidi.convertDisplayTime((int) Math.ceil(ctrlMidi.getMusicTime()));
                String currnetPlayTime = ctrlMidi.convertDisplayTime((int) (ctrlMidi.getMusicTime() * playRate));
                timeDisplayLabel.setText(currnetPlayTime + "/" + playTimeLength);
            }
        });

        //添加menuItem1的监听事件，
        menuItem1.addActionListener(e -> {
            if (ctrlMidi != null) {
                if (ctrlMidi.isPlaying()) {
                    playOrStop();
                }
            }
            //ctrlMidi
            try {
                if (openFile()) return;
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (MidiUnavailableException ex) {
                throw new RuntimeException(ex);
            } catch (InvalidMidiDataException ex) {
                throw new RuntimeException(ex);
            }

            //重置所有数据
            resetAllComponent();
        });

        //添加倍速相关监听事件
        speedRelatedListener();

        //tipPanel监听事件
        tipPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                try {
                    if (openFile()) {
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (MidiUnavailableException ex) {
                    throw new RuntimeException(ex);
                } catch (InvalidMidiDataException ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                tipPanel.setBackground(new Color(0xec, 0xec, 0xec));
                super.mouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                tipPanel.setBackground(new Color(0xf2, 0xf2, 0xf2));
                super.mouseExited(e);
            }
        });

        //添加 playAllCheckBox 的监听事件
//        playAllCheckBox.addChangeListener();

        playAllCheckBox.addActionListener(e -> {
            if (playAllCheckBox.isSelected()) {
                for (int i = 0; i < trackComponentsArrayList.size(); i++) {
                    trackComponentsArrayList.get(i).trackCheckBox.setSelected(true);
                }
                ctrlMidi.setAllTrackMute(false);
            } else {
                for (int i = 0; i < trackComponentsArrayList.size(); i++) {
                    trackComponentsArrayList.get(i).trackCheckBox.setSelected(false);
                }
                ctrlMidi.setAllTrackMute(true);
            }
        });

        //添加foldButton的监听事件
        foldButton.addActionListener(e -> {
            if (foldButton.getText().equals("展开")) {
                for (TrackComponents trackComponents : trackComponentsArrayList) {
                    trackComponents.foldContext(false);
                    foldButton.setText("折叠");
                }
            } else {
                for (TrackComponents trackComponents : trackComponentsArrayList) {
                    trackComponents.foldContext(true);
                    foldButton.setText("展开");
                }
            }
        });

        //region Folded: 添加一个退出程序的事件
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                File file = new File("init.json");
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                    BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                    HashMap<String, Object> initJson = new HashMap<String, Object>();
                    initJson.put("lastOpenDirPath", openFilePath);
                    initJson.put("lastSaveDirPath", saveFilePath);
                    String strJson = new JSONObject(initJson).toString();

                    // 格式化json内容
                    JSONTokener jsonTokener = new JSONTokener(strJson);
                    String formattedJson = new JSONObject(jsonTokener).toString(4);
                    System.out.println(formattedJson);

                    bufferedWriter.write(formattedJson);
                    bufferedWriter.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                System.exit(0);
            }
        });
        //endregion

        //导出相关监听事件
        exportRelatedListen();

    }

    private boolean openFile() throws IOException, MidiUnavailableException, InvalidMidiDataException {
        JFileChooser jfc;
        //添加一个选择文件的对话框
        jfc = new JFileChooser(openFilePath);

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Midi文件(.mid) 或 Excel文件(.xlsx)", "xlsx", "mid");
        jfc.setFileFilter(filter);
        int result = jfc.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {//选择文件
            System.out.println(jfc.getSelectedFile());
            String fileAbsoluteName = jfc.getSelectedFile().toString();
            String[] strs = jfc.getSelectedFile().getName().split("\\.");
            fileName = strs[0];
            if (fileAbsoluteName.endsWith("mid")) {
                System.out.println("Midi File:" + fileAbsoluteName);
                ctrlMidi = new CtrlMidi(fileAbsoluteName);
                //隐藏tipPanel
                tipPanel.setVisible(false);
                toolPanel.setVisible(true);
                midiInfoPanel.setLayout(new MigLayout("hidemode 3,gap 0 0",
                        // columns
                        "[grow,fill]",
                        // rows
                        "[][]"));

                setMidiInfoPanel();
            } else if (fileAbsoluteName.endsWith("xlsx")) {
                System.out.println("Excel File:" + fileAbsoluteName);
            } else {
                return false;
            }
            openFilePath = jfc.getSelectedFile().getParentFile().getPath();
            System.out.println(openFilePath);
        }
        return false;
    }

    //region Folded: midiInfo显示相关
    public void setMidiInfoPanel() {
        while (ctrlMidi == null) {

        }
        //清除midiInfoPanel的布局
        if (!trackComponentsArrayList.isEmpty()) {
            System.out.println("empty");
            for (TrackComponents trackComponents : trackComponentsArrayList) {
                midiInfoPanel.remove(trackComponents);
            }
            trackComponentsArrayList.clear();
        }
        midiInfoPanel.setLayout(new MigLayout("hidemode 3,gap 0 0",
                // columns
                "[grow,fill]",
                // rows
                "[][]"));
        for (MidiTrack midiTrack : ctrlMidi.getMidiInfo().getTrackArrayList()) {
            if (midiTrack.getNoteArrayList().isEmpty()) {
                continue;
            }
            trackComponentsArrayList.add(new TrackComponents(ctrlMidi.getMidiInfo(), midiTrack, true) {
                @Override
                public void ontrackCheckBoxChanged() {
                    super.ontrackCheckBoxChanged();
                    ctrlMidi.setTrackMute(midiTrack.getTrackNumber(), !this.isTrackCheckBoxSelected());
                    playAllCheckBox.setSelected(checkAllSelected());
                }

                @Override
                public void onSoloButtonClicked() {
                    super.onSoloButtonClicked();
                    setSingleCheckSelected(this);
                    ctrlMidi.setAllTrackMute(true);
                    ctrlMidi.setTrackMute(midiTrack.getTrackNumber(), !this.isTrackCheckBoxSelected());
                    playAllCheckBox.setSelected(checkAllSelected());
                }

            });
        }
        for (int i = 0; i < trackComponentsArrayList.size(); i++) {
            midiInfoPanel.add(trackComponentsArrayList.get(i), "cell " + "0 " + (i + 1));
        }
        repaint();
    }

    public boolean checkAllSelected() {
        boolean selected = false;
        boolean notSelected = false;

        for (TrackComponents trackComponents : trackComponentsArrayList) {
            if (trackComponents.trackCheckBox.isSelected()) {
                selected = true;
            } else {
                notSelected = true;
            }
        }
        if (selected && !notSelected) {
            return true;
        }
        return false;
    }

    public void setSingleCheckSelected(TrackComponents trackComponents) {
        for (TrackComponents trackComponents1 : trackComponentsArrayList) {
            if (trackComponents1.equals(trackComponents)) {
                trackComponents.trackCheckBox.setSelected(true);
//                System.out.println("true");
            } else {
                trackComponents1.trackCheckBox.setSelected(false);
//                System.out.println("false");
            }
        }
    }

    //endregion

    //倍速相关监听事件
    //region Folded: Speed factor listener
    public void speedRelatedListener() {

        //添加speedmenu的点击监听事件
        speedMenu.addMenuListener(new MenuListener() {
            public void menuSelected(MenuEvent e) {
//                System.out.println("menuSelected");
                speedTextField.transferFocusBackward();
                speedTextField.selectAll();
            }

            public void menuDeselected(MenuEvent e) {
//                System.out.println("menuDeselected");
            }

            public void menuCanceled(MenuEvent e) {
//                System.out.println("menuCanceled");
            }
        });
        //添加speedTextField失去焦点事件
        speedTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                setCustomSpeed();
            }
        });

        //添加speedTextField的按键监听事件
        speedTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
//                    speedTextField.;
//                    System.out.printf("ldfksj");
//                    speedMenu.doClick(100);
                    speedMenu.doClick();

                }
            }
        });

        //添加speedMenuItem监听事件
        {
            speedMenuItem0_5x.addActionListener(e -> {
                setSpeedFactor(0.5);
            });
            speedMenuItem0_75x.addActionListener(e -> {
                setSpeedFactor(0.75);
            });
            speedMenuItem1_0x.addActionListener(e -> {
                setSpeedFactor(1);
            });
            speedMenuItem1_25x.addActionListener(e -> {
                setSpeedFactor(1.25);
            });
            speedMenuItem1_5x.addActionListener(e -> {
                setSpeedFactor(1.5);
            });
            speedMenuItem2_0x.addActionListener(e -> {
                setSpeedFactor(2);
            });

        }

    }

    public void setCustomSpeed() {
        if (ctrlMidi == null) {
            setStatusLabelText("提示：请先选择Midi文件再修改倍速！", 20);
            speedTextField.setText("自定义");
            return;
        }
        String text = speedTextField.getText();
        if (text.equals("自定义")) {
            return;
        }
        double speed;
        try {
            speed = Double.parseDouble(text);
            if (speed > 4) {
                //添加0k cancel确认弹窗
                int result = JOptionPane.showConfirmDialog(MyFrame.this, speedTextField.getText() + "倍速可能过快", "你确定要这么快？", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.CANCEL_OPTION) {
                    return;
                }
                speedMenu.setText(speed + "x");
                ctrlMidi.setTempoFactor((float) speed);
            } else if (speed <= 0) {
                setStatusLabelText("提示：倍速需要大于0！");
            }else {
                speedMenu.setText(speed + "x");
                setSpeedFactor(speed);
            }
        } catch (Exception exception) {
            setStatusLabelText("提示：倍速自定义需要是数字！");
        }
        speedTextField.setText("自定义");
    }

    public void setSpeedFactor(double factor) {
        if (ctrlMidi == null) {
            setStatusLabelText("提示：请先选择Midi文件再修改倍速！", 20);
            return;
        }
        if (factor == 1) {
            speedMenu.setText("倍速");
        } else {
            speedMenu.setText(factor + "x");
        }
        ctrlMidi.setTempoFactor((float) factor);
    }
    //endregion

    //导出相关事件
    public void exportRelatedListen() {
        //添加 exportMenuItem 监听事件
        exportMenuItem.addActionListener(e -> {
            if (ctrlMidi == null) {
                return;
            }
            //todo vvv 选择导出文件的位置文件夹
            JFileChooser jfc;
            jfc = new JFileChooser(saveFilePath);
            // 默认保存名称为 "untitled"
            jfc.setSelectedFile(new File(fileName + ".litematic"));
            int result = jfc.showSaveDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = jfc.getSelectedFile();
                if (!file.toString().endsWith(".litematic")) {
                    file = new File(file.toString() + ".litematic");
                }
                saveFilePath = file.getParent();
                String[] str1s = file.getName().split("\\.");
                fileName = str1s[0];
            } else {
                return;
            }
            //todo ^^^

            mainList.clear();
            viceList.clear();
            // 检查优先级
            for (int i = 0; i <= 10; i++) {
                for (TrackComponents trackComponents : trackComponentsArrayList) {
                    if ((int) trackComponents.prioritySpinner.getValue() == i) {
                        if (trackComponents.priorityButton.getText().equals("主")) {
                            mainList.add(trackComponents.trackNumber);
                        }
                        if (trackComponents.priorityButton.getText().equals("副")) {
                            viceList.add(trackComponents.trackNumber);
                        }
                    }
                }
            }
            System.out.println(saveFilePath);
            System.out.println(saveFilePath + "\\" + fileName + ".litematic");

//            System.out.println(Float.parseFloat(speedMenu.getText().substring(0, speedMenu.getText().length() - 1)));
            float speed = 1;
            if (speedMenu.getText().equals("倍速")) {
                speed = 1;
            } else {
                speed = Float.parseFloat(speedMenu.getText().substring(0, speedMenu.getText().length() - 1));
            }

            ArrayList<ArrayList<ArrayList<Integer>>> notesList = ctrlMidi.getMidiNotes(mainList, viceList, speed);

            Convert convert = new Convert();

            try {
                convert.writeFile(notesList.get(0), notesList.get(1), saveFilePath + "\\" + fileName + ".litematic");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        });
    }

    //设置statusLabel文字
    //延迟 time * 100ms 清楚文字
    public void setStatusLabelText(String string, int time) {
        statusLabelThread.countDown = time;
        statusLabel.setText(string);
    }

    public void setStatusLabelText(String string) {
        setStatusLabelText(string + "  ", 10);
    }

    // 重新打开文件时重置所有组件
    private void resetAllComponent() {
        if (ctrlMidi != null) {
            String playTimeLength = ctrlMidi.convertDisplayTime((int) Math.ceil(ctrlMidi.getMusicTime()));
            String currnetPlayTime = ctrlMidi.convertDisplayTime(0);
            timeDisplayLabel.setText(currnetPlayTime + "/" + playTimeLength);
        }
        if (slider1.getValue() != 0) {
            slider1.setValue(0);
        }
        speedMenu.setText("倍速");
        foldButton.setText("展开");
    }

    private void playOrStop() {
        if (ctrlMidi.isPlaying()) {
            //改变okButton的文字和注释
            okButton.setText("►");
            okButton.setToolTipText("播放");
            ctrlMidi.stopPlay();
        } else {
            okButton.setText("⏸");
            okButton.setToolTipText("暂停");
            ctrlMidi.startPlay();
        }
    }

    private void stopPlaying() {
        okButton.setText("►");
        okButton.setToolTipText("播放");
        ctrlMidi.stopPlay();
    }

    public void creatThread() {
        myThread = new MyThread();
        myThread.start();
        statusLabelThread = new StatusLabelThread();
        statusLabelThread.start();
    }


    public static void main(String[] args) {
        FlatLightLaf.install();
        MyFrame myFrame = new MyFrame();
        myFrame.setVisible(true);
        myFrame.initListener();
        myFrame.creatThread();
    }

}
