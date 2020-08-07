package xyz.shandiankulishe.smcl.core;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Iterator;

public class analyzeIndex {
    private static File indexFile;
    private static String indexs;
    private boolean ifSetURL=false;
    public void setManifest(File Manifest) throws IOException {
        indexFile=Manifest;
        BufferedReader reader=new BufferedReader(new FileReader(Manifest));
        String line;
        StringBuilder builder=new StringBuilder();
        while ((line=reader.readLine())!=null){
            builder.append(line);
        }
        indexs=builder.toString();
        ifSetURL=true;
    }
    public void setVersion(String Version) throws IOException {
        analyzeManifest analyzer=new analyzeManifest();
        analyzer.useLatest();
        HashMap<String, List<String>> Map=analyzer.analyzeVersions();
        List<String> id=Map.get("id");
        List<String> url=Map.get("url");
        Iterator<String> iterator=id.iterator();
        String VersionObject;
        boolean ifAlreadyFound=false;
        int index=0;
        int foundIndex=0;
        while (iterator.hasNext()){
            VersionObject=new String(iterator.next());
            if (VersionObject.equals(Version)){
                foundIndex=index;
                ifAlreadyFound=true;
            }
            index++;
        }
        if (ifAlreadyFound=false){
            System.out.println("[FATAL]Can't Find This Version.Core will use the Latest version");
            HashMap<String,String> Map1=analyzer.analyzeLatest();
            String LatestVersion=Map1.get("release");
            Iterator<String> iterator1=id.iterator();
            index=0;
            foundIndex=0;
            while (iterator1.hasNext()){
                VersionObject=new String(iterator1.next());
                if (VersionObject.equals(LatestVersion)){
                    foundIndex=index;
                }
                index++;
            }
            URL DownloadUrl1=new URL(url.get(foundIndex));
            BufferedReader reader1=new BufferedReader(new InputStreamReader(DownloadUrl1.openStream()));
            String line1;
            StringBuilder builder1=new StringBuilder();
            while ((line1=reader1.readLine())!=null){
                builder1.append(line1);
            }
            indexs=builder1.toString();
        } else {
            URL DownloadUrl=new URL(url.get(foundIndex));
            BufferedReader reader=new BufferedReader(new InputStreamReader(DownloadUrl.openStream()));
            String line;
            StringBuilder builder=new StringBuilder();
            while ((line=reader.readLine())!=null){
                builder.append(line);
            }
            indexs=builder.toString();
        }
    }
    public void useLatest() throws IOException {
        analyzeManifest analyzer=new analyzeManifest();
        analyzer.useLatest();
        HashMap<String, List<String>> Map=analyzer.analyzeVersions();
        List<String> id=Map.get("id");
        List<String> url=Map.get("url");
        HashMap<String,String> Map1=analyzer.analyzeLatest();
        String LatestVersion=Map1.get("release");
        Iterator<String> iterator1=id.iterator();
        int index=0;
        int foundIndex=0;
        String VersionObject;
        while (iterator1.hasNext()){
            VersionObject=new String(iterator1.next());
            if (VersionObject.equals(LatestVersion)){
                foundIndex=index;
            }
            index++;
        }
        URL DownloadUrl1=new URL(url.get(foundIndex));
        BufferedReader reader1=new BufferedReader(new InputStreamReader(DownloadUrl1.openStream()));
        String line1;
        StringBuilder builder1=new StringBuilder();
        while ((line1=reader1.readLine())!=null){
            builder1.append(line1);
        }
        indexs=builder1.toString();
    }
    public HashMap<String,List<String >> getLibraries() throws IOException {
        if (ifSetURL!=true){
            useLatest();
        }
        JSONObject indexObject=new JSONObject(indexs);
        JSONArray libraries=indexObject.getJSONArray("libraries");
        JSONObject object;
        JSONObject downloads;
        JSONObject artifact;
        String libname;
        HashMap<String,List<String>> Map=new HashMap<String, List<String>>();
        List<String> path=new ArrayList<String>();
        List<String> sha1=new ArrayList<String>();
        int filesize;
        List<String> size=new ArrayList<String>();
        List<String> url=new ArrayList<String>();
        List<String> name=new ArrayList<String>();
        for (int i=0;i<libraries.length();i++){
            object=libraries.getJSONObject(i);
            downloads=object.getJSONObject("downloads");
            libname=object.getString("name");
            name.add(i,libname);
            artifact=downloads.getJSONObject("artifact");
            path.add(i,artifact.getString("path"));
            sha1.add(i,artifact.getString("sha1"));
            filesize=artifact.getInt("size");
            size.add(i,String.valueOf(filesize));
            url.add(i,artifact.getString("url"));
        }
        Map.put("path",path);
        Map.put("sha1",sha1);
        Map.put("size",size);
        Map.put("url",url);
        Map.put("name",name);
        return Map;
    }
    public HashMap<String,String> getAssetsIndex() throws IOException {
        if (ifSetURL!=true){
            useLatest();
        }
        HashMap<String,String> Map=new HashMap<String, String>();
        JSONObject indexObject=new JSONObject(indexs);
        JSONObject assetsIndex=indexObject.getJSONObject("assetIndex");
        String id=assetsIndex.getString("id");
        String sha1=assetsIndex.getString("sha1");
        int JSONSize=assetsIndex.getInt("size");
        String size=String.valueOf(JSONSize);
        int AssetsTotalSize=assetsIndex.getInt("totalSize");
        String totalSize=String.valueOf(AssetsTotalSize);
        String url=assetsIndex.getString("url");
        Map.put("id",id);
        Map.put("sha1",sha1);
        Map.put("size",size);
        Map.put("totalSize",totalSize);
        Map.put("url",url);
        return Map;
    }
    public HashMap<String,HashMap<String,String>> getDownloads(boolean ifgetMappings) throws IOException {
        if (ifSetURL!=true){
            useLatest();
        }
        HashMap<String,HashMap<String,String>> Map= new HashMap<String,HashMap<String,String>>();
        JSONObject indexObject=new JSONObject(indexs);
        JSONObject DownloadObject=indexObject.getJSONObject("downloads");
        Iterator<String> keys=DownloadObject.keys();
        HashMap<String,String> client=new HashMap<String, String>();
        HashMap<String,String> server=new HashMap<String, String>();
        JSONObject object=DownloadObject.getJSONObject("client");
        Iterator<String> iterator=object.keys();
        String Keys_Object;
        String Keys;
        int size;
        while (iterator.hasNext()){
            Keys=iterator.next();
            Keys_Object=new String(Keys);
            if (Keys_Object.equals("size")){
                size=object.getInt(Keys);
                client.put(Keys,String.valueOf(size));
            } else {
                client.put(Keys,object.getString(Keys));
            }
        }
        object=DownloadObject.getJSONObject("server");
        iterator=object.keys();
        while (iterator.hasNext()){
            Keys=iterator.next();
            Keys_Object=new String(Keys);
            if (Keys_Object.equals("size")){
                size=object.getInt(Keys);
                server.put(Keys,String.valueOf(size));
            } else {
                server.put(Keys,object.getString(Keys));
            }
        }
        Map.put("client",client);
        Map.put("server",server);
        if (ifgetMappings == true){
            if (ifSetURL!=true){
                useLatest();
            }
            HashMap<String,String> client_mappings=new HashMap<String, String>();
            HashMap<String,String> server_mappings=new HashMap<String, String>();
            object=DownloadObject.getJSONObject("client_mappings");
            iterator=object.keys();
            while (iterator.hasNext()){
                Keys=iterator.next();
                Keys_Object=new String(Keys);
                if (Keys_Object.equals("size")){
                    size=object.getInt(Keys);
                    client_mappings.put(Keys,String.valueOf(size));
                } else {
                    client_mappings.put(Keys,object.getString(Keys));
                }
            }
            object=DownloadObject.getJSONObject("server_mappings");
            iterator=object.keys();
            while (iterator.hasNext()){
                Keys=iterator.next();
                Keys_Object=new String(Keys);
                if (Keys_Object.equals("size")){
                    size=object.getInt(Keys);
                    server_mappings.put(Keys,String.valueOf(size));
                } else {
                    server_mappings.put(Keys,object.getString(Keys));
                }
            }
            Map.put("client_mappings",client_mappings);
            Map.put("server_mappings",server_mappings);
        }
        return Map;
    }
    public HashMap<String,List<String>> getArguments() throws IOException {
        if (ifSetURL!=true){
            useLatest();
        }
        JSONObject indexObject=new JSONObject(indexs);
        HashMap<String,List<String>> Map=new HashMap<String,List<String>>();
        List<String> gameArguments=new ArrayList<String>();
        List<String> jvmArguments=new ArrayList<String>();
        JSONObject arguments=indexObject.getJSONObject("arguments");
        JSONArray game=arguments.getJSONArray("game");
        JSONArray jvm=arguments.getJSONArray("jvm");
        String str=null;
        JSONObject object = null;
        JSONObject rules;
        String os;
        Object compareObject;
        Iterator<String> iterator;
        for (int i=0;i<game.length();i++){
            compareObject=game.get(i);
            if (compareObject.equals(object)){
                object= (JSONObject) compareObject;
                iterator=object.keys();
                while (iterator.hasNext()){
                    gameArguments.add(i,iterator.next());
                }
            } else {
                str=compareObject.toString();
                gameArguments.add(i,str);
            }
        }
        for (int i=0;i<jvm.length();i++){
            compareObject=jvm.get(i);
            if (compareObject.equals(object)){
                object= (JSONObject) compareObject;
                iterator=object.keys();
                while (iterator.hasNext()){
                    jvmArguments.add(i,iterator.next());
                }
            } else {
                str=compareObject.toString();
                jvmArguments.add(i,str);
            }
        }
        Map.put("game",gameArguments);
        Map.put("jvm",jvmArguments);
        return Map;
    }
    public HashMap<String,List<String>> getLogging() throws IOException {
        if (ifSetURL!=true){
            useLatest();
        }
        HashMap<String,List<String>> Map=new HashMap<String,List<String>>();
        JSONObject indexObject=new JSONObject(indexs);
        JSONObject loggingObject=indexObject.getJSONObject("logging");
        JSONObject client=loggingObject.getJSONObject("client");
        JSONObject file=client.getJSONObject("file");
        List<String> logging=new ArrayList<String>();
        logging.add(0,client.getString("argument"));
        Iterator<String> iterator=file.keys();
        String Keys;
        String compare_Keys;
        String compare;
        int size;
        int nums = 0;
        while (iterator.hasNext()){
            nums++;
            compare_Keys=iterator.next();
            compare=new String(compare_Keys);
            if (compare.equals("size")){
                size=file.getInt(compare_Keys);
                logging.add(nums,String.valueOf(size));
            } else {
                Keys=file.getString(compare_Keys);
                logging.add(nums,Keys);
            }
        }
        logging.add(nums++,client.getString("type"));
        Map.put("logging",logging);
        return Map;
    }
    public String getMainClass() throws IOException {
        if (ifSetURL!=true){
            useLatest();
        }
        JSONObject indexObject=new JSONObject(indexs);
        String MainClass=indexObject.getString("mainClass");
        return MainClass;
    }
}