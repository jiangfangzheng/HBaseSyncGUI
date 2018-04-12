package gui;

import javax.swing.*;
import java.util.List;

import static util.HBaseRestUtil.adds;
import static util.StringUtil.getFileNameByFullPath;

/**
 * @author Sandeepin
 * 2018/4/12 0012
 */
public class GuiWorker {

    // 后台任务执行
    private SwingWorker<String, String> task;

    GuiWorker(JLabel fileNameLabel, JProgressBar syncProgressBar, JButton fileOpenButton, JButton fileSyncButton, List<String> fileList) {
        // 涉及的组件
        JLabel fileNameLabel1 = fileNameLabel;
        JProgressBar syncProgressBar1 = syncProgressBar;
        JButton fileOpenButton1 = fileOpenButton;
        JButton fileSyncButton1 = fileSyncButton;
        this.task = new SwingWorker<String, String>() {
            @Override
            protected String doInBackground() throws Exception {
                // 自定义代码
                for (int i = 0; i < fileList.size(); i++) {
                    // 延时模拟耗时操作
                    Thread.sleep(100);
                    String fileName = getFileNameByFullPath(fileList.get(i));
                    String realIndex = (i + 1) + "";
                    // 设置 progress 属性的值（通过属性改变监听器传递数据到事件调度线程）
//                    setProgress(i);
                    // 通过 SwingWorker 内部机制传递数据到事件调度线程
                    publish(realIndex, fileList.size() + "", fileName + "...");
                    boolean b = adds(fileList.get(i));
                    String result = " Error！";
                    if (b) {
                        result = " OK！";
                    }
                    System.out.println(fileName + " " + b);
                    Thread.sleep(100);
                    publish(realIndex, fileList.size() + "", fileName + result);
                    System.out.println(fileName + " " + b + " " + realIndex);
                }
                // 返回计算结果
                return "done！";
            }

            @Override
            protected void process(List<String> chunks) {
                // 此方法在 调用 doInBackground 调用 public 方法后在事件调度线程中被回调
                int realIndex = Integer.parseInt(chunks.get(0));
                int size = Integer.parseInt(chunks.get(1));
                String fileName = chunks.get(2);
                syncProgressBar.setValue(realIndex * 100 / size);
                fileNameLabel.setText("(" + realIndex + "/" + size + ") " + fileName);
                System.out.println("(" + realIndex + "/" + size + ") " + fileName);
            }

            @Override
            protected void done() {
                // 此方法将在后台任务完成后在事件调度线程中被回调
                String result;
                try {
                    result = get();
                    System.out.println("后台任务结果：" + result);
                    // 自定义内容
                    fileOpenButton.setEnabled(true);
                    fileSyncButton.setEnabled(true);
                    syncProgressBar.setValue(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    void execute() {
        task.execute();
    }
}
