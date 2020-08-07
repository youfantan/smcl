package xyz.shandiankulishe.smcl.core.demo;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Tools {
    Logger logger= LogManager.getLogger(downloadResources.class);
    public String getFileSHA1(File targetFile) throws IOException, NoSuchAlgorithmException {
        FileInputStream fis=new FileInputStream(targetFile);
        MessageDigest messageDigest=MessageDigest.getInstance("SHA-1");
        byte[] buffer=new byte[1024];
        int length=-1;
        while ((length=fis.read(buffer,0,1024))!=-1){
            messageDigest.update(buffer,0,length);
        }
        fis.close();
        byte[] sha1=messageDigest.digest();
        BigInteger bigInteger=new BigInteger(1,sha1);
        return bigInteger.toString(16);
    }
    public void downloadResources(URL downloadURL, File downloadFile, String FileName, String sha1, String StandardSize) throws IOException, InterruptedException, NoSuchAlgorithmException {
        logger.info("当前任务："+FileName+" 文件大小："+StandardSize+" SHA1校验值："+sha1+"\n");
        long StartTime=System.currentTimeMillis();
        ProcessPrintThread ppt=new ProcessPrintThread(downloadURL,downloadFile,FileName,StandardSize);
        ppt.start();
        FileUtils.copyURLToFile(downloadURL,downloadFile);
        long FinishTime=System.currentTimeMillis();
        double UseTime=FinishTime-StartTime;
        UseTime=UseTime/1000;
        logger.info("下载完成，正在比对文件完整性");
        int actFileSize= (int) downloadFile.length();
        int stdFileSize=Integer.valueOf(StandardSize);
        if (stdFileSize==actFileSize){
            logger.info("文件未出现问题。标准大小"+stdFileSize+" 实际大小"+actFileSize);
        }
        logger.info("开始比对文件SHA-1值");
        Tools toolkit=new Tools();
        String actSHA1=toolkit.getFileSHA1(downloadFile);
        String stdSHA1=new String(sha1);
        if (stdSHA1.equals(actSHA1)){
            logger.info("SHA-1值比对未出现问题，标准SHA-1"+stdSHA1+" 实际SHA-1"+actSHA1);
        }
        Thread.sleep(1000);
        logger.info(FileName+" 任务结束 耗时 "+UseTime+" 秒"+"\n");
    }
    public void downloadResources(URL downloadURL, File downloadFile, String FileName, String StandardSize) throws IOException, InterruptedException {
        logger.info("当前任务："+FileName+" 文件大小："+StandardSize+"\n");
        long StartTime=System.currentTimeMillis();
        ProcessPrintThread ppt=new ProcessPrintThread(downloadURL,downloadFile,FileName,StandardSize);
        ppt.start();
        FileUtils.copyURLToFile(downloadURL,downloadFile);
        long FinishTime=System.currentTimeMillis();
        double UseTime=FinishTime-StartTime;
        UseTime=UseTime/1000;
        logger.info("下载完成，正在比对文件完整性");
        int actFileSize= (int) downloadFile.length();
        int stdFileSize=Integer.valueOf(StandardSize);
        if (stdFileSize==actFileSize){
            logger.info("文件未出现问题。标准大小"+stdFileSize+" 实际大小"+actFileSize);
        }
        Thread.sleep(1000);
        logger.info(FileName+" 任务结束 耗时 "+UseTime+" 秒"+"\n");
    }
}
