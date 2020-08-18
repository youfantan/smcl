package xyz.shandiankulishe.smcl;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.WindowConstants;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Toolkit;

public class Error {
    public Error(String Value){
        Dimension ScreenSize=Toolkit.getDefaultToolkit().getScreenSize();
        int width=ScreenSize.width;
        int height=ScreenSize.height;
        JFrame frame=new JFrame();
        JPanel panel=new JPanel();
        Font UWPAssets = new Font("Segoe MDL2 Assets", Font.PLAIN, 30);
        Font SmallTextFont = new Font("Microsoft JhengHei UI Light", Font.BOLD, 14);
        JLabel Icon=new JLabel("\uE171");
        JLabel text=new JLabel(Value);
        JButton OK=new JButton("确认");
        Icon.setBounds(30,0,50,80);
        text.setBounds(70,20,400,40);
        OK.setBounds(200,100,60,30);
        Icon.setFont(UWPAssets);
        text.setFont(SmallTextFont);
        panel.setLayout(null);
        panel.add(Icon);
        panel.add(text);
        panel.add(OK);
        frame.setTitle("ERROR");
        frame.setIconImage(new ImageIcon(Info.class.getClassLoader().getResource("Error.png")).getImage());
        frame.setContentPane(panel);
        frame.setSize(400,120);
        frame.setLocation(width/2,height/2);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
