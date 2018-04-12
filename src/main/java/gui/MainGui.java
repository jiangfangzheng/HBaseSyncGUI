package gui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import static gui.GuiUtil.setFrameCenter;
import static gui.GuiUtil.setSystemStyle;
import static util.FileUtil.readFilesByPath;

/**
 * @author Sandeepin
 * 2018/4/12 0012
 */
public class MainGui extends JFrame implements ActionListener, ChangeListener {

    private String filePath;

    private JButton fileOpenButton;
    private JButton fileSyncButton;
    private JLabel filePathLabel;
    private JLabel fileNameLabel;
    private JProgressBar syncProgressBar;

    public static void createGUI() {
        MainGui mainGui = new MainGui();
    }

    public MainGui() {
        // 系统风格界面、初始化
        setSystemStyle();
        JFrame jf = new JFrame("HBase同步软件 v1.0    By jfz");
        jf.setSize(400, 200);
        jf.setMinimumSize(new Dimension(400, 200));
        fileOpenButton = new JButton("打开");
        fileOpenButton.setFocusPainted(false);
        fileOpenButton.setActionCommand("open");
        fileOpenButton.addActionListener(this);

        fileSyncButton = new JButton("同步");
        fileSyncButton.setFocusPainted(false);
        fileSyncButton.setActionCommand("sync");
        fileSyncButton.addActionListener(this);
        fileSyncButton.setEnabled(false);

        filePathLabel = new JLabel("无");
        fileNameLabel = new JLabel("无");

        syncProgressBar = new JProgressBar();
        syncProgressBar.setMinimum(0);
        syncProgressBar.setMaximum(100);
        syncProgressBar.setValue(0);
        syncProgressBar.setStringPainted(true);
        syncProgressBar.addChangeListener(this);
        syncProgressBar.setPreferredSize(new Dimension(360,24));

        // 第 1 个 JPanel, 使用默认的浮动布局
        JPanel panel01 = new JPanel();
        panel01.add(new JLabel("1、选择目录："));
        panel01.add(fileOpenButton);
        panel01.add(new JLabel("          2、开始同步："));
        panel01.add(fileSyncButton);

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
    }

    @Override
    public void stateChanged(ChangeEvent e) {
//        System.out.println("进度:" + syncProgressBar.getValue() + " 百分比:" + syncProgressBar.getPercentComplete());
    }
}
