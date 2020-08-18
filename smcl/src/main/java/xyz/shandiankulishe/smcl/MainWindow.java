package xyz.shandiankulishe.smcl;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;

import com.sun.jna.platform.WindowUtils;
import org.apache.logging.log4j.*;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;
import org.json.*;
import org.apache.commons.io.*;

public class MainWindow extends JFrame{
    public static Logger logger=LogManager.getLogger(MainWindow.class);
    public static int eventX;
    public static int eventY;
    public static int cX=0;
    public static int cY=0;
    public static JLabel Response=new JLabel("",JLabel.CENTER);
    public static ConfigurationSource source;
    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, FileNotFoundException {
        String log4jPath=MainWindow.class.getClassLoader().getResource("log4j2.xml").toString();
        log4jPath=log4jPath.substring(6);
        source=new ConfigurationSource(new FileInputStream(new File(log4jPath)), new File(log4jPath));
        Configurator.initialize(null,source);
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        JFrame frame = new JFrame("shandiankulishe's Minecraft Launcher");
        Font CommonTextFont = new Font("Microsoft JhengHei UI Light", Font.PLAIN, 20);
        Font SmallTextFont = new Font("Microsoft JhengHei UI Light", Font.PLAIN, 14);
        Font UWPAssets = new Font("Segoe MDL2 Assets", Font.PLAIN, 30);
        Font JetBrainsMono = new Font("JetBrains Mono Extra Light", Font.PLAIN, 20);
        Border border = BorderFactory.createLineBorder(Color.WHITE, 1, true);
        Border AccountBorder = BorderFactory.createTitledBorder(border, "请在此输入您注册时的邮箱或旧版Mojang用户名", TitledBorder.CENTER, 0, SmallTextFont, Color.WHITE);
        Border PasswordBorder = BorderFactory.createTitledBorder(border, "请在此输入您的Mojang账户密码", TitledBorder.CENTER, 0, SmallTextFont, Color.WHITE);
        frame.setLayout(null);
        JButton Exit = new JButton("\uE10A");
        Exit.setFont(UWPAssets);
        Exit.setContentAreaFilled(false);
        Exit.setBounds(1210, 0, 70, 60);
        Exit.setFocusPainted(false);
        frame.setSize(1280, 720);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JTextField Account = new JTextField(35);
        Account.setBounds(465, 130, 350, 60);
        Account.setOpaque(false);
        Account.setForeground(Color.WHITE);
        Account.setBorder(AccountBorder);
        Account.setHorizontalAlignment(JTextField.CENTER);
        JTextField Password = new JTextField(35);
        Password.setBorder(PasswordBorder);
        Password.setBounds(465, 210, 350, 60);
        Password.setOpaque(false);
        Password.setForeground(Color.WHITE);
        Password.setHorizontalAlignment(JTextField.CENTER);
        JButton Login = new JButton("\uE008");
        Login.setContentAreaFilled(false);
        Login.setBounds(600, 290, 80, 60);
        Login.setFocusPainted(false);
        JButton Settings=new JButton("\uE115");
        Settings.setFont(UWPAssets);
        Settings.setContentAreaFilled(false);
        Settings.setFocusPainted(false);
        Settings.setBounds(1140, 0, 70, 60);
        JBackPanel Panel = new JBackPanel(new ImageIcon("background.jpg"));
        Exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                cX=e.getX();
                cY=e.getY();
            }
        });
        frame.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int x = e.getXOnScreen();
                int y = e.getYOnScreen();
                frame.setLocation(x-cX, y-cY);
            }
        });
        Border clickedBorder = BorderFactory.createLineBorder(Color.decode("#ffff00"), 1, false);
        Account.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Account.setBorder(clickedBorder);
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                Account.setBorder(clickedBorder);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                String nullValue = "";
                if (nullValue.equals(Account.getText())) {
                    Account.setBorder(AccountBorder);
                }
            }
        });
        Password.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Password.setBorder(clickedBorder);
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                Password.setBorder(clickedBorder);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                String nullValue = "";
                if (nullValue.equals(Password.getText())) {
                    Password.setBorder(PasswordBorder);
                }
            }
        });
        Login.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                Login.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Login.setForeground(Color.BLACK);
            }
        });
        Settings.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                Settings.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Settings.setForeground(Color.BLACK);
            }
        });
        Exit.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                Exit.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Exit.setForeground(Color.BLACK);
            }
        });
        Response.setBounds(465, 330, 350, 40);
        Response.setFont(CommonTextFont);
        Login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Response.setText("正在向auth.mojang.com发起POST请求以验证您的账户，此过程可能较长，请稍等");
                Response.setForeground(Color.WHITE);
                String account = Account.getText();
                System.out.println(account);
                String pswd = Password.getText();
                System.out.println(pswd);
                auth auth = null;
                String rv = null;
                try {
                    auth = new auth(account, pswd);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                try {
                    rv=auth.getReturnValue();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                logger.info("auth.mojang.com返回的状态码为"+auth.getRC());
                if (auth.getRC() == 403) {
                    Response.setText("您输入的信息有误，请重新输入");
                    Response.setForeground(Color.RED);
                } else { ;
                    Response.setText("验证成功！正在存储配置文件...");
                    String accessToken=auth.getToken(rv);
                    String playername=auth.getName(rv);
                    String playeruuid=auth.getUUID(rv);
                    JSONObject object=new JSONObject("{}");
                    object.put("accessToken",accessToken);
                    object.put("uuid",playeruuid);
                    object.put("name",playername);
                    File launcher_properties=new File("SMCL\\Properties\\launcher_properties.json");
                    if (!launcher_properties.exists()){
                        try {
                            launcher_properties.createNewFile();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                    try {
                        FileUtils.write(launcher_properties,object.toString(),"UTF-8");
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });
        Account.setFont(JetBrainsMono);
        Password.setFont(JetBrainsMono);
        Login.setFont(UWPAssets);
        Panel.add(Exit);
        Panel.add(Settings);
        Panel.add(Response);
        Panel.setLayout(null);
        Panel.add(Account);
        Panel.add(Password);
        Panel.add(Login);
        frame.setContentPane(Panel);
        frame.setUndecorated(true);
        frame.setVisible(true);
        System.setProperty("sun.java2d.noddraw", "true");
    }
    public static void init(){
        File propertyDir=new File("SMCL\\Properties");
        File gameDir=new File("SMCL\\.minecraft");
        if (!propertyDir.exists()){
            propertyDir.mkdirs();
        }
        if (!gameDir.exists()){
            gameDir.mkdirs();
        }
    }
}
