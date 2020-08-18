package xyz.shandiankulishe.smcl;

import javax.swing.*;
import java.awt.*;

public class JBackPanel extends JPanel {
    Image BackgroundImage;
    public JBackPanel(ImageIcon BackgroundImage){
        this.BackgroundImage=BackgroundImage.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(BackgroundImage,0,0,this.getWidth(),this.getHeight(),this);
    }
}
