package xyz.shandiankulishe.smcl;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.WindowConstants;
import java.awt.*;

public class Info {
    public Info(String Value){
        Dimension ScreenSize=Toolkit.getDefaultToolkit().getScreenSize();
        int width=ScreenSize.width;
        int height=ScreenSize.height;
        JFrame frame=new JFrame();
        JPanel panel=new JPanel();
        Font UWPAssets = new Font("Segoe MDL2 Assets", Font.PLAIN, 30);
        Font SmallTextFont = new Font("Microsoft JhengHei UI Light", Font.BOLD, 14);
        JLabel Icon=new JLabel("\uEA1F");
        JLabel text=new JLabel(Value);
        Icon.setBounds(30,0,50,80);
        text.setBounds(70,20,400,40);
        Icon.setFont(UWPAssets);
        text.setFont(SmallTextFont);
        panel.setLayout(null);
        panel.add(Icon);
        panel.add(text);
        frame.setTitle("INFO");
        frame.setIconImage(new ImageIcon(Info.class.getClassLoader().getResource("Info.png")).getImage());
        frame.setContentPane(panel);
        frame.setSize(400,120);
        frame.setLocation(width/2,height/2);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
