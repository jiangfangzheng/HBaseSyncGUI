package gui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static gui.GuiUtil.setFrameCenter;
import static gui.GuiUtil.setSystemStyle;
import static util.FileUtil.readFile;
import static util.FileUtil.readFilesByPath;

/**
 * @author Sandeepin
 * 2018/4/12 0012
 */
public class MainGui extends JFrame implements ActionListener, ChangeListener {

    private String filePath;

    private JButton fileOpenButton;
    private JButton fileSyncButton;
    private JButton logShowButton;
    private JLabel filePathLabel;
    private JLabel fileNameLabel;
    private JProgressBar syncProgressBar;

    public static void createGUI() {
        MainGui mainGui = new MainGui();
    }

    public MainGui() {
        // 系统风格界面、初始化
        setSystemStyle();
        JFrame jf = new JFrame("HBase同步软件 v1.1 by jfz");
        jf.setSize(550, 200);
        jf.setMinimumSize(new Dimension(550, 200));
        fileOpenButton = new JButton("打开");
        fileOpenButton.setFocusPainted(false);
        fileOpenButton.setActionCommand("open");
        fileOpenButton.addActionListener(this);

        fileSyncButton = new JButton("同步");
        fileSyncButton.setFocusPainted(false);
        fileSyncButton.setActionCommand("sync");
        fileSyncButton.addActionListener(this);
        fileSyncButton.setEnabled(false);

        logShowButton = new JButton("查看日志");
        logShowButton.setFocusPainted(false);
        logShowButton.setActionCommand("logshow");
        logShowButton.addActionListener(this);
        logShowButton.setEnabled(true);

        filePathLabel = new JLabel("无");
        fileNameLabel = new JLabel("无");

        syncProgressBar = new JProgressBar();
        syncProgressBar.setMinimum(0);
        syncProgressBar.setMaximum(100);
        syncProgressBar.setValue(0);
        syncProgressBar.setStringPainted(true);
        syncProgressBar.addChangeListener(this);
        syncProgressBar.setPreferredSize(new Dimension(360, 24));

        // 第 1 个 JPanel, 使用默认的浮动布局
        JPanel panel01 = new JPanel();
        panel01.add(new JLabel("1、选择目录："));
        panel01.add(fileOpenButton);
        panel01.add(new JLabel("          2、开始同步："));
        panel01.add(fileSyncButton);
        panel01.add(new JLabel("          "));
        panel01.add(logShowButton);

        // 第 2 个 JPanel, 使用默认的浮动布局
//        JPanel panel02 = new JPanel();
//        panel02.add(new JLabel("2、开始同步："));
//        panel02.add(fileSyncButton);

        // 第 3 个 JPanel, 使用浮动布局, 并且容器内组件居中显示
        JPanel panel03 = new JPanel();
        panel03.add(new JLabel("路径："));
        panel03.add(filePathLabel);

        // 第 3 个 JPanel, 使用浮动布局, 并且容器内组件居中显示
        JPanel panel04 = new JPanel();
        panel04.add(new JLabel("当前处理文件："));
        panel04.add(fileNameLabel);

        // 第 4 个 JPanel, 使用浮动布局, 并且容器内组件居中显示
        JPanel panel05 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel05.add(syncProgressBar);

        // 创建一个垂直盒子容器, 把上面 3 个 JPanel 串起来作为内容面板添加到窗口
        Box vBox = Box.createVerticalBox();
        vBox.add(panel01);
//        vBox.add(panel02);
        vBox.add(panel03);
        vBox.add(panel04);
        vBox.add(panel05);

        jf.setContentPane(vBox);

        // jf.pack();
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);
        setFrameCenter(jf, true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 选择文件
        if ("open".equals(e.getActionCommand())) {
            filePathLabel.setText("无");
            fileNameLabel.setText("无");
            fileSyncButton.setEnabled(false);
            syncProgressBar.setValue(0);
            JFileChooser jf = new JFileChooser();
            // 只选择文件夹
            jf.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            // 显示打开的文件对话框
            jf.showOpenDialog(this);
            // 使用文件类获取选择器选择的文件
            if (jf.getSelectedFile() != null) {
                File f = jf.getSelectedFile();
                // 返回路径名
                String s = f.getAbsolutePath();
                System.out.println(s);
                filePath = s;
                filePathLabel.setText(s);
                if (filePath != null) {
                    fileSyncButton.setEnabled(true);
                }
            }
        }
        // 同步
        if ("sync".equals(e.getActionCommand())) {
            fileNameLabel.setText("无");
            fileOpenButton.setEnabled(false);
            fileSyncButton.setEnabled(false);
            System.out.println("同步");
            List<String> fileList = readFilesByPath(filePath);
//            FileUtil.show(fileList);
            // 创建后台任务
            GuiWorker task = new GuiWorker(fileNameLabel, syncProgressBar, fileOpenButton, fileSyncButton, fileList);
            task.execute();
        }
        // 选择文件
        if ("logshow".equals(e.getActionCommand())) {
            showLogDialog(this, this);
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
//        System.out.println("进度:" + syncProgressBar.getValue() + " 百分比:" + syncProgressBar.getPercentComplete());
    }

    // 显示日志对话框
    private static void showLogDialog(Frame owner, Component parentComponent) {
        String logStr = "日志不存在！";
        try {
            logStr = readFile("HBaseSync.log");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(logStr);
        // 创建一个模态对话框
        final JDialog dialog = new JDialog(owner, "显示日志", true);
        // 设置对话框的宽高
        dialog.setSize(640, 480);
        // 设置对话框大小不可改变
        dialog.setResizable(false);
        // 设置对话框相对显示的位置
        dialog.setLocationRelativeTo(parentComponent);
        // 创建一个标签显示消息内容
        JLabel messageLabel = new JLabel("读取文件为：HBaseSync.log，请确保文件存在！");
        // 创建一个 5 行 10 列的文本区域
        final JTextArea textArea = new JTextArea(20,40);
        // 设置自动换行
        textArea.setLineWrap(true);
        textArea.setEnabled(false);
        textArea.setText(logStr);
        // 创建一个按钮用于刷新日志的按钮
        JButton okBtn = new JButton("重新载入日志");
        okBtn.setFocusPainted(false);
        okBtn.addActionListener(e -> {
            try {
                String tmpLog = readFile("HBaseSync.log");
                textArea.setText(tmpLog);
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        });
        // 创建对话框的内容面板, 在面板内可以根据自己的需要添加任何组件并做任意是布局
        JPanel panel = new JPanel();
        // 添加组件到面板
        panel.add(messageLabel);
        panel.add(textArea);
        panel.add(okBtn);
        // 设置对话框的内容面板
        dialog.setContentPane(panel);
        // 显示对话框
        dialog.setVisible(true);
    }

}
