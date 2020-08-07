package xyz.shandiankulishe.smcl.core.demo;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.fusesource.jansi.AnsiConsole;
import org.apache.logging.log4j.Logger;

import org.json.JSONObject;

import xyz.shandiankulishe.smcl.core.analyzeIndex;
import xyz.shandiankulishe.smcl.core.authProfile;
import xyz.shandiankulishe.smcl.core.analyzeManifest;
import xyz.shandiankulishe.smcl.core.analyzeAssetsIndex;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.Color;

public class Main {
    public static Logger logger= LogManager.getLogger(Main.class);
    public static void main(String[] args) throws IOException, InterruptedException, NoSuchAlgorithmException {
        AnsiConsole.systemInstall();
        System.out.println(ansi().eraseLine().fg(Color.GREEN).a("欢迎使用SMCL启动器"));
        File property=new File("launcher_property.json");
        if (!property.exists()) {
            logger.warn("未成功查找到启动器配置文件。启动器将会尝试创建一个新的配置文件");
            property.createNewFile();
        }
        logger.info("正在读取配置文件");
        String PropertyContent=FileUtils.readFileToString(property,"UTF-8");
        JSONObject PropertyObject=null;
        String Compare=new String(PropertyContent);
        if (Compare.equals("")){
            logger.warn("配置文件是空的，启动器准备写入基础配置内容");
            System.out.println(ansi().eraseLine().fg(Color.YELLOW).a("请输入您的Minecraft账户邮箱（或旧版Mojang用户名）"));
            Scanner getUsername=new Scanner(System.in);
            String username=getUsername.next();
            System.out.println(ansi().eraseLine().fg(Color.YELLOW).a("请输入您的Minecraft账户密码"));
            Scanner getPassword=new Scanner(System.in);
            String password=getPassword.next();
            System.out.println(ansi().eraseLine().fg(Color.YELLOW).a("启动器正在向auth.mojang.com发送数据验证。请稍等"));
            authProfile auth=new authProfile(username,password);
            FileUtils.write(property,"{}","UTF-8");
            PropertyContent=FileUtils.readFileToString(property,"UTF-8");
            PropertyObject=new JSONObject(PropertyContent);
            String authReturn=auth.get();
            JSONObject response=new JSONObject(authReturn);
            String clientToken=response.getString("clientToken");
            PropertyObject.put("clientToken",clientToken);
            String accessToken=response.getString("accessToken");
            JSONObject selectedProfile=response.getJSONObject("selectedProfile");
            String id=selectedProfile.getString("id");
            PropertyObject.put("uuid",id);
            String name=selectedProfile.getString("name");
            PropertyObject.put("PlayerName",name);
            FileUtils.write(property,PropertyObject.toString(),"UTF-8");
        }
        PropertyObject=new JSONObject(PropertyContent);
        String Name=PropertyObject.getString("PlayerName");
        System.out.println(Name+"，欢迎回家！");
        File gameProperty=new File("game_property.json");
        if (!gameProperty.exists()){
            logger.warn("未成功查找到游戏配置文件。启动器将会尝试创建一个新的配置文件");
            JSONObject GameObject=new JSONObject("{}");
            System.out.println("是否需要创建一个新的游戏档案？（y/n）");
            Scanner ifCreateNewProfile=new Scanner(System.in);
            String code=new String(ifCreateNewProfile.next());
            if (code.equals("y")){
                Main main=new Main();
                main.createNewGameProfile();
            } else {
                System.out.println("准备进入主页面");
            }
        }
    }
    public void createNewGameProfile() throws IOException, InterruptedException, NoSuchAlgorithmException {
        long startTime=0;
        long endTime=0;
        double takeTime=0;
        Tools toolkit=new Tools();
        System.out.println("正在从launchermeta.mojang.com/mc/game/version_manifest.json获取最新的版本清单文件，请稍等");
        analyzeManifest ManifestAnalyze=new analyzeManifest();
        ManifestAnalyze.useLatest();
        HashMap<String,String> latestVersions=ManifestAnalyze.analyzeLatest();
        String latestRelease=latestVersions.get("release");
        String latestSnapshot=latestVersions.get("snapshot");
        HashMap<String,List<String>> AllVersions=ManifestAnalyze.analyzeVersions();
        List<String> versions_name=AllVersions.get("id");
        List<String> versions_type=AllVersions.get("type");
        List<String> versions_releaseTime=AllVersions.get("releaseTime");
        System.out.println("最新版本：");
        System.out.println("正式版："+latestRelease);
        System.out.println("快照版："+latestSnapshot);
        System.out.println("所有版本：");
        Iterator<String> names=versions_name.iterator();
        String NextChar;
        int index=0;
        while (names.hasNext()){
            NextChar=names.next();
            System.out.println("版本名称："+NextChar+" 版本类型："+versions_type.get(index)+" 版本发布时间："+versions_releaseTime.get(index));
            index++;
        }
        System.out.println("输入您需要安装的版本名称，如果取消则输入CANCEL");
        Scanner getVersion=new Scanner(System.in);
        String version=new String(getVersion.next());
        names=versions_name.iterator();
        index=0;
        NextChar=null;
        int type_index=0;
        while (names.hasNext()){
            NextChar=new String(names.next());
            if (NextChar.equals(version)){
                type_index=index;
            }
            index++;
        }
        String choosed_version_type=versions_type.get(type_index);
        if (version.equals("CANCEL")){
            System.out.println("准备进入主页面");
            main(null);
        } else {
            System.out.println("正在获取版本文件，请稍等");
            analyzeIndex Indexanalyze=new analyzeIndex();
            Indexanalyze.setVersion(version);
            HashMap<String,List<String>> Libraries=Indexanalyze.getLibraries();
            logger.info("开始下载libraries（游戏运行依赖库）");
            startTime=System.currentTimeMillis();
            List<String> libraries_downloadUrl=Libraries.get("url");
            List<String> libraries_downloadPath=Libraries.get("path");
            List<String> libraries_downloadName=Libraries.get("name");
            List<String> libraries_downloadSha1=Libraries.get("sha1");
            List<String> libraries_downloadSize=Libraries.get("size");
            Iterator<String> iterator=libraries_downloadName.iterator();
            int downloadIndex=0;
            String nextValue;
            String saveUrl=null;
            File librariesPath=new File(".minecraft\\libraries");
            librariesPath.mkdirs();

            takeTime=endTime-startTime;
            takeTime=takeTime/1000;
            endTime=System.currentTimeMillis();
            logger.info("libraries（游戏运行依赖库）下载完成，耗时"+takeTime+"秒");
            File assetsPath=new File(".minecraft\\assets");
            if (!assetsPath.exists()){
                assetsPath.mkdirs();
            }
            logger.info("开始下载assetsIndex（游戏资源文件索引）");
            startTime=System.currentTimeMillis();
            HashMap<String,String> AssetsIndex=Indexanalyze.getAssetsIndex();
            String assetsIndex_download_url=AssetsIndex.get("url");
            String assetsIndex_download_id=AssetsIndex.get("id");
            String assetsIndex_download_sha1=AssetsIndex.get("sha1");
            int assetsIndex_download_size=Integer.valueOf(AssetsIndex.get("size"));
            int assetsIndex_download_totalSize=Integer.valueOf(AssetsIndex.get("totalSize"));
            File assetsIndex_downloadFile=new File(assetsPath+"\\indexes\\"+assetsIndex_download_id+".json");
            toolkit.downloadResources(new URL(assetsIndex_download_url),assetsIndex_downloadFile,assetsIndex_download_id,assetsIndex_download_sha1,String.valueOf(assetsIndex_download_size));
            endTime=System.currentTimeMillis();
            takeTime=endTime-startTime;
            takeTime=takeTime/1000;
            logger.info("assetsIndex（游戏资源文件索引）文件下载完成，耗时"+takeTime+"秒");
            logger.info("开始下载assets（游戏资源文件）");
            startTime=System.currentTimeMillis();
            String resDownloadUrl="http://resources.download.minecraft.net";
            analyzeAssetsIndex AssetsIndexAnalyze=new analyzeAssetsIndex();
            AssetsIndexAnalyze.setIndex(assetsIndex_downloadFile);
            HashMap<String,List<String>> assets=AssetsIndexAnalyze.analyzeAssetsIndex();
            List<String> assets_download_name=assets.get("name");
            List<String> assets_download_hash=assets.get("hash");
            List<String> assets_download_size=assets.get("size");
            iterator=assets_download_name.iterator();
            downloadIndex=0;
            nextValue=null;
            String assets_download_url;
            File assets_download_path;
            String assets_hash_sec;
            while (iterator.hasNext()){
                nextValue=iterator.next();
                assets_hash_sec=assets_download_hash.get(downloadIndex).substring(0,2);
                assets_download_path=new File(assetsPath+"\\objects\\"+assets_hash_sec+"\\"+assets_download_hash.get(downloadIndex));
                assets_download_url=resDownloadUrl+"/"+assets_hash_sec+"/"+assets_download_hash.get(downloadIndex);
                toolkit.downloadResources(new URL(assets_download_url),assetsIndex_downloadFile,assets_download_name.get(downloadIndex),assets_download_size.get(downloadIndex));
                downloadIndex++;
            }
            endTime=System.currentTimeMillis();
            takeTime=endTime-startTime;
            takeTime=takeTime/1000;
            logger.info("assets（游戏资源文件）下载完成，耗时"+takeTime+"秒");
            logger.info("开始下载游戏客户端文件");
            startTime=System.currentTimeMillis();
            HashMap<String,HashMap<String,String>> downloads=Indexanalyze.getDownloads(true);
            HashMap<String,String> client=downloads.get("client");
            String client_download_url=client.get("url");
            String client_download_sha1=client.get("sha1");
            String client_download_size=client.get("size");
            String client_download_path=".minecraft\\versions\\"+version+"\\"+version+".jar";
            toolkit.downloadResources(new URL(client_download_url),new File(client_download_path),client_download_path,version+"\\"+version+".jar",client_download_size);
            endTime=System.currentTimeMillis();
            takeTime=endTime-startTime;
            takeTime=takeTime/1000;
            logger.info("游戏客户端文件下载完成，耗时"+takeTime+"秒");
            HashMap<String,List<String>> Arguments=Indexanalyze.getArguments();
            logger.info("开始下载游戏日志配置文件");
            startTime=System.currentTimeMillis();
            HashMap<String,List<String>> Logging=Indexanalyze.getLogging();
            List<String> logging=Logging.get("logging");
            String logging_download_url=logging.get(5);
            String logging_download_sha1=logging.get(1);
            String logging_download_name=logging.get(3);
            String logging_download_size=logging.get(2);
            String logging_download_path=".minecraft\\assets\\log_configs\\"+logging_download_name;
            toolkit.downloadResources(new URL(logging_download_url),new File(logging_download_path),logging_download_name,logging_download_sha1,logging_download_size);
            endTime=System.currentTimeMillis();
            takeTime=endTime-startTime;
            takeTime=takeTime/1000;
            logger.info("游戏日志配置文件下载完成，耗时"+takeTime+"秒");
            String MainClass=Indexanalyze.getMainClass();
            JSONObject Property=new JSONObject(FileUtils.readFileToString(new File("lau")))
            CreateLaunchArgs createLaunchArgs=new CreateLaunchArgs(version,choosed_version_type,)
        }
    }
}
