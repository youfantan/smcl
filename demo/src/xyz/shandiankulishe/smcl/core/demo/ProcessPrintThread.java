package xyz.shandiankulishe.smcl.core.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.net.URL;
import java.net.URLConnection;

class ProcessPrintThread extends Thread{
    URL downloadURL;
    File downloadFile;
    String FileName;
    String sha1;
    String StandardSize;
    Logger logger= LogManager.getLogger(ProcessPrintThread.class);
    ProcessPrintThread(URL downloadURL, File downloadFile, String FileName, String StandardSize){
        this.downloadURL=downloadURL;
        this.downloadFile=downloadFile;
        this.FileName=FileName;
        this.sha1=sha1;
        this.StandardSize=StandardSize;
    }
    @Override
    public void run() {
        URLConnection connection = null;
        try {
            connection=downloadURL.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        double remoteSize=connection.getContentLength();
        double localSize=downloadFile.length();
        double downloadSpeed;
        double currentFileSize;
        double percentage;
        BigDecimal decimal1;
        BigDecimal decimal2;
        BigDecimal decimal3;
        while (localSize<remoteSize){
            currentFileSize=localSize;
            currentFileSize=currentFileSize/1024;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            localSize=downloadFile.length();
            localSize=localSize/1024;
            downloadSpeed=localSize-currentFileSize;
            percentage=localSize/remoteSize;
            percentage=percentage*100000;
            decimal1=new BigDecimal(localSize).setScale(2,RoundingMode.UP);
            decimal2=new BigDecimal(downloadSpeed).setScale(2,RoundingMode.UP);
            decimal3=new BigDecimal(percentage).setScale(2,RoundingMode.UP);
            logger.info("已下载 "+decimal1.toString()+" KB 下载速度："+decimal2.toString()+" KB/s 已完成"+decimal3.toString()+" %");
            localSize=downloadFile.length();
        }
    }
}
