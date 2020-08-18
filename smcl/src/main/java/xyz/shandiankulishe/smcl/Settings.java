package xyz.shandiankulishe.smcl;
import com.sun.jna.platform.WindowUtils;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class Settings {
    public static JLabel Preview=new JLabel();
    public static ImageIcon icon;
    public static String ItemName;
    public static JSONObject property=new JSONObject("{}");
    public static File ChoosedFile;
    public static HashMap<String,String> launcher_theme=new HashMap<String, String>();
    public void mainWindow(){
        Font CommonTextFont = new Font("Microsoft JhengHei UI Light", Font.PLAIN, 20);
        Font SmallTextFont = new Font("Microsoft JhengHei UI Light", Font.PLAIN, 14);
        Font UWPAssets = new Font("Segoe MDL2 Assets", Font.PLAIN, 30);
        JFrame frame=new JFrame();
        JPanel Panel=new JPanel();
        String GaussianBlur="我们使用了强大的OpenCV机器学习库来完成渲染和储存高斯滤波图像文件，如果您需要使用这个主题，请首先下载OpenCV支持库";
        JLabel GaussianBlur_description=new JLabel("高斯滤波（Gaussian filtering），亦称之为高斯模糊（Gaussian Blur）或高斯平滑（Gaussian smoothing）是在Adobe Photoshop、GIMP以及Paint.NET等图像处理软件中广泛使用的处理效果，通常用它来减少图像噪声以及降低细节层次。一个很贴切的例子是Microsoft Windows 7中使用的Aero（俗称毛玻璃）主题，在Microsoft Windows 10里它被称作Acrylic（亚克力），Acrylic效果相比于Aero增加了动态渲染的效果，您可以在Microsoft提供的WPF平台或UWP平台中使用它们.");
        JLabel SolidColor_description=new JLabel("纯色背景是一种高对比度的主题，不过我们不建议您使用黑色、白色、红色等颜色过深或过浅的颜色，这可能会使显示效果大打折扣");
        JLabel FullImage_description=new JLabel("全图片覆盖是一种彩色的、鲜明的主题，与纯色背景一样，我们不建议您使用主题色过深或过浅的图片，这可能会使显示效果大打折扣");
        JLabel Transparent=new JLabel("半透明主题显示效果是否出色取决与启动器后的颜色或图片，也由于半透明的实现方式是基于JNA-Platform，这种主题极不稳定，所以我们不建议您使用它，我们正在尝试在Java中实现Acrylic效果以取代它");
        JComboBox Themes=new JComboBox();
        Panel.add(Themes);
        Themes.addItem("高斯滤波");
        Themes.addItem("纯色背景");
        Themes.addItem("全图片覆盖");
        Themes.addItem("半透明");
        Themes.setFont(SmallTextFont);
        Themes.setBackground(Color.WHITE);
        Themes.setForeground(Color.BLACK);
        Themes.setOpaque(false);
        Themes.setBounds(30,30,100,50);
        Panel.add(Preview);
        Preview.setBounds(30,100,320,180);
        Themes.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    ItemName= (String) e.getItem();
                    if ("高斯滤波".equals(ItemName)){
                        icon=new ImageIcon(Settings.class.getClassLoader().getResource("Themes\\GaussianBlur.png"));
                        property.put("Themes",0);
                        JFrame childWindow=new JFrame("请选择需要操作的图片");
                        JPanel contentPanel=new JPanel();
                        JTextField path=new JTextField(20);
                        path.setBounds(50,20,200,50);
                        path.setFont(SmallTextFont);
                        JScrollPane jScrollPane=new JScrollPane(path);
                        JButton choose=new JButton("选择");
                        choose.setBounds(150,100,80,50);
                        childWindow.setVisible(true);
                        childWindow.setContentPane(contentPanel);
                        contentPanel.add(jScrollPane);
                        contentPanel.add(choose);
                        childWindow.setSize(300,300);
                        choose.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                JFileChooser chooser=new JFileChooser();
                                chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                                FileNameExtensionFilter filter=new FileNameExtensionFilter("png","jpg");
                                chooser.addChoosableFileFilter(filter);
                                chooser.showDialog(null,"选择图像文件");
                                ChoosedFile=chooser.getSelectedFile();
                                File OpenCV_x86=new File("opencv\\x86\\opencv_java320.dll");
                                File OpenCV_x64=new File("opencv\\x64\\opencv_java320.dll");
                                if (!OpenCV_x86.exists() || !OpenCV_x64.exists()){
                                    try {
                                        Warning warning=new Warning("未找到OpenCV库，请检查是否下载OpenCV库或者检查安装路径");
                                        throw new FileNotFoundException("Can't Found OpenCV Libraries.");
                                    } catch (FileNotFoundException fileNotFoundException) {
                                        fileNotFoundException.printStackTrace();
                                    }
                                }
                                GaussianBlur gaussianBlur=new GaussianBlur();
                                gaussianBlur.doGaussianBlur(ChoosedFile.getPath());
                                if (ChoosedFile.toString().endsWith(".png")){
                                    launcher_theme.put("wallpaper","Properties\\GaussianBlur.png");
                                } else if (ChoosedFile.toString().endsWith(".jpg")){
                                    launcher_theme.put("wallpaper","Properties\\GaussianBlur.jpg");
                                }
                                Info info=new Info("更改成功！内容将会在下次启动时生效");
                            }
                        });
                    } else if ("纯色背景".equals(ItemName)){
                        icon=new ImageIcon(Settings.class.getClassLoader().getResource("Themes\\SolidColor.png"));
                        property.put("Themes",1);
                    } else if ("全图片覆盖".equals(ItemName)){
                        icon=new ImageIcon(Settings.class.getClassLoader().getResource("Themes\\FullImage.png"));
                        property.put("Themes",2);
                    } else if ("半透明".equals(ItemName)){
                        icon=new ImageIcon(Settings.class.getClassLoader().getResource("Themes\\Transparent.png"));
                        property.put("Themes",3);
                    }
                    icon.setImage(icon.getImage().getScaledInstance(320,180,Image.SCALE_DEFAULT));
                    Preview.setIcon(icon);
                }
            }
        });
        Panel.setLayout(null);
        frame.setContentPane(Panel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setFont(UWPAssets);
        frame.setTitle("启动器设置");
        frame.setVisible(true);
        frame.setSize(700,500);
        System.setProperty("sun.java2d.noddraw","true");
        WindowUtils.setWindowAlpha(frame,0.9f);
    }
}
