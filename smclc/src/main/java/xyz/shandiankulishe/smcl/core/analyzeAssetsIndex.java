package xyz.shandiankulishe.smcl.core;

import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class analyzeAssetsIndex {
    String assetsIndex;
    boolean ifSetUrl;
    analyzeIndex analyzer=new analyzeIndex();
    public static void main(String[] args) throws IOException {
        analyzeAssetsIndex analyzeAssetsIndex=new analyzeAssetsIndex();
        analyzeAssetsIndex.useLatest();
        HashMap<String,List<String>> Map=analyzeAssetsIndex.analyzeAssetsIndex();
        System.out.println(Map);
    }
    public void useLatest() throws IOException {
        analyzer.useLatest();
        HashMap<String,String> getAssetsIndex=analyzer.getAssetsIndex();
        String targetIndex=getAssetsIndex.get("url");
        URL targetUrl=new URL(targetIndex);
        BufferedReader reader=new BufferedReader(new InputStreamReader(targetUrl.openStream()));
        String lines;
        StringBuilder builder=new StringBuilder();
        while ((lines=reader.readLine())!=null){
            builder.append(lines);
        }
        assetsIndex=builder.toString();
    }
    public void setVersion(String Version) throws IOException {
        analyzer.setVersion(Version);
        HashMap<String,String> getAssetsIndex=analyzer.getAssetsIndex();
        String targetIndex=getAssetsIndex.get("url");
        URL targetUrl=new URL(targetIndex);
        BufferedReader reader=new BufferedReader(new InputStreamReader(targetUrl.openStream()));
        String lines;
        StringBuilder builder=new StringBuilder();
        while ((lines=reader.readLine())!=null){
            builder.append(lines);
        }
        assetsIndex=builder.toString();
    }
    public void setIndex(File indexFile) throws IOException {
        BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(indexFile),"UTF-8"));
        String lines;
        StringBuilder builder=new StringBuilder();
        while ((lines=reader.readLine())!=null){
            builder.append(lines);
        }
        assetsIndex=builder.toString();
    }
    public HashMap<String, List<String>> analyzeAssetsIndex(){
        JSONObject assetsIndexObject=new JSONObject(assetsIndex);
        JSONObject objects=assetsIndexObject.getJSONObject("objects");
        JSONObject Object;
        HashMap<String,List<String>> Map=new HashMap<String, List<String>>();
        Iterator<String> iterator=objects.keys();
        List<String> size=new ArrayList<String>();
        List<String> hash=new ArrayList<String>();
        List<String> name=new ArrayList<String>();
        String NextValue;
        int Size;
        int index=0;
        while (iterator.hasNext()){
            NextValue=iterator.next();
            Object=objects.getJSONObject(NextValue);
            name.add(NextValue);
            Size=Object.getInt("size");
            size.add(index,String.valueOf(Size));
            hash.add(index,Object.getString("hash"));
            index++;
        }
        Map.put("name",name);
        Map.put("hash",hash);
        Map.put("size",size);
        return Map;
    }
}
