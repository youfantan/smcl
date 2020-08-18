package xyz.shandiankulishe.smcl;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.Properties;

public class GaussianBlur {
    static {
        Properties sysProperties=new Properties();
        sysProperties=System.getProperties();
        String architect=sysProperties.getProperty("os.arch");
        if ("amd64".equals(architect)){
            System.loadLibrary("opencv/x64/opencv_java320");
        } else if ("x86".equals(architect)){
            System.loadLibrary("opencv/x86/opencv_java320");
        }
    }
    public void doGaussianBlur(String Path){
        Mat sourceImage= Imgcodecs.imread(Path);
        System.out.println(sourceImage);
        Mat matrix=sourceImage.clone();
        Imgproc.GaussianBlur(sourceImage,matrix,new Size(15,15),10,10);
        Imgcodecs.imwrite("Properties\\GaussianBlur.jpg",matrix);
    }
}
