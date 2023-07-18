import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class Main extends JFrame {
    private JButton startButton;
    private JButton stopButton;
    private Robot robot;
    private boolean autoClicking;
    private Thread autoClickThread;

    public Main() {
        setTitle("Auto Enter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setAlwaysOnTop(true); // 设置窗口置于最顶层

        startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startAutoClick();
            }
        });

        stopButton = new JButton("Stop");
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopAutoClick();
            }
        });

        // 创建一个 ImageIcon 对象，指定要显示的图片文件路径

        ImageIcon imageIcon = new ImageIcon(Main.class.getResource("/resource/icon.png"));
        Image scaledImage = imageIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);

        ImageIcon scaledImageIcon = new ImageIcon(scaledImage);

        // 创建一个 JLabel，并使用 ImageIcon 对象作为标签的图标
        JLabel label = new JLabel(scaledImageIcon);

        // 设置标签居中对齐
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);


        getContentPane().add(label, BorderLayout.CENTER);
        getContentPane().add(startButton, BorderLayout.WEST);
        getContentPane().add(stopButton, BorderLayout.EAST);
        pack();
        setLocationRelativeTo(null); // 居中显示窗口

        // 调整窗口大小
        Dimension windowSize = new Dimension(350, 200);
        setPreferredSize(windowSize);
        setMinimumSize(windowSize);
        setMaximumSize(windowSize);
        setSize(windowSize);
    }

    private void startAutoClick() {
        if (!autoClicking) {
            startButton.setEnabled(false);
            stopButton.setEnabled(true);
            autoClicking = true;

            autoClickThread = new Thread(new Runnable() {
                public void run() {
                    try {
                        robot = new Robot();

                        // 持续执行按下和释放 Enter 键
                        while (autoClicking) {
                            robot.keyPress(KeyEvent.VK_ENTER);
                            robot.keyRelease(KeyEvent.VK_ENTER);
                            Thread.sleep(10); // 间隔 10 毫秒
                        }
                    } catch (AWTException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            autoClickThread.start();
        }
    }

    private void stopAutoClick() {
        if (autoClicking) {
            startButton.setEnabled(true);
            stopButton.setEnabled(false);
            autoClicking = false;

            try {
                autoClickThread.join(); // 等待自动点击线程结束
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }
}
