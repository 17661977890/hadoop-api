package com.example.hadoopapi;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
/**
 * http://hadoop.apache.org/docs/r2.7.3/api/index.html（api地址）
 * 
 * 
 * https://blog.csdn.net/snwdwtm/article/details/78242805
 * 
 * 该类含有操作 HDFS 的各种方法，类似于 jdbc 中操作数据库的直接入口是 Connection 类。
 *
 * 注意本地环境变量也配置一下，否则会报错
 */
public class FileSystemApi {
   private static FileSystem fs=null;
   private static Configuration conf=null;
   static{
         try {
            URI uri = new URI("hdfs://192.168.2.31:9000");
               conf  = new Configuration();
             //其实就是配置core-site.xml,也可以放在resourse目录下进行加载
             conf.set("fs.defaultFS", "hdfs://192.168.2.31:9000/");
             conf.set("dfs.client.use.datanode.hostname", "true");
              fs = FileSystem.get(uri,conf,"root"); //指定root用户
      } catch (Exception e) {
        e.printStackTrace();
      }
   }


    /**
     * 每个方法从上向下单独执行测试
     *
     * 上传的文件在hdfs 50070页面下载失败，你要在本地 C:/windows/system20/drivers/etc/hosts配置 虚拟机的ip 和主机名 我的是: 192.168.2.31 sun.com
     * @param args
     * @throws IOException
     * @throws InterruptedException
     */
//    public static void main(String[] args) throws IOException, InterruptedException {
////      mkdirs("/usr/local/input");
//      putFile("C:\\Users\\彬\\Desktop\\123.txt", "/usr/local/input");
////      down("/usr/local/input/接口.doc", "C:\\Users\\彬\\Desktop", false);
////      delete("/usr/local/apifiledist", true);
////     listStatus("/usr/local");
////      rename("/usr/local/api2/111.txt", "/usr/local/api2/222.txt");
////      write("/usr/local/f1", "DDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
////      open("/usr/local/api2/222.txt");
//    }
    
    /**
     * 创建文件夹
     * @param path
     */
    public static void  mkdirs(String path) throws IllegalArgumentException, IOException{
      boolean exists = fs.exists(new Path(path));
      System.out.println(path+":文件夹是否存在 "+exists);
      if(!exists){
        boolean result = fs.mkdirs(new Path(path));
        System.out.println("创建文件夹："+result);
      }
    }
    
    /**
     * 上传文件导HDFS
     * @param localFile 本地文件路径
     * @param dst    远程HDFS服务器路径
     */
  public static void putFile(String localFile,String dst){
      try {
      fs.copyFromLocalFile(new Path(localFile), new Path(dst));
      File f=new File(localFile);
      String fileName=f.getName();
      System.out.println("文件名："+fileName);
      boolean ex=fs.exists(new Path(dst+fileName));
      if(ex){
        System.out.println("上传成功："+dst+fileName);
      }else{
        System.out.println("上传失败："+dst+fileName);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  /**
   * 下载文件
   * @param dst  远程HDFS服务器文件路径
   * @param localPath  本地存储路径
   * @param falg  若执行该值为false 则不删除 hdfs上的相应文件
   * @param   
   */
  public static void down(String dst,String localPath,boolean falg){
    try {
      //第4个参数表示使用本地原文件系统
      fs.copyToLocalFile(falg,new Path(dst), new Path(localPath),true);
  } catch (Exception e) {
    e.printStackTrace();
   }
  }
  
  /**
   * 删除文件夹
   * @param dst   远程HDFS服务器文件夹路径
   * @param falg  为true时能够将带有内容的文件夹删除，为false时，则只能删除空目录
   */
  public static void delete(String dst,boolean falg){
    try {
      boolean b= fs.delete(new Path(dst), falg);
     System.out.println("删除文件夹状态："+b);
  } catch (Exception e) {
    e.printStackTrace();
  }
  }
  
  /**
   * 查看文件及文件夹的信息
   * @param dst  远程HDFS服务器文件夹路径
   */
  public static void  listStatus(String dst){
   try {
     FileStatus[ ]  fss =   fs.listStatus(new Path(dst));
       String flag ="";
       for(FileStatus  f : fss){
           if(f.isDirectory()){
               flag = "Directory";
          }else{
            flag ="file";
           }
           System.out.println(flag + "\t" +f.getPath().getName());
       }
  } catch (Exception e) {
    e.printStackTrace();
  }
  }
  
  /**
   * 文件重命名
   * @param dst
   * @param newDst
   */
  public static void rename(String dst,String newDst){
  try {
    boolean bs = fs.rename(new Path(dst), new Path(newDst));
     System.out.println("文件重命名:"+bs);
  } catch (Exception e) {
    e.printStackTrace();
  } 
  }
  
  /**
   * 写入文件
   * @param dst   文件服务器目录
   * @param value 值
   */
  public static void write(String dst,String value){
    try {
      FSDataOutputStream fsDataOutputStream = fs.create(new Path(dst));
      IOUtils.copyBytes(new ByteArrayInputStream(value.getBytes()), fsDataOutputStream, conf, true);
      //读
      FSDataInputStream fsDataInputStream = fs.open(new Path(dst));
      IOUtils.copyBytes(fsDataInputStream, System.out, conf, true);
  } catch (Exception e) {
    e.printStackTrace();
  }
  }
  /**
   * 读文件
   * @param dst
   */
  public static void open(String dst){
    try {
      FSDataInputStream fsDataInputStream = fs.open(new Path(dst));
      IOUtils.copyBytes(fsDataInputStream, System.out, conf, true);
  } catch (Exception e) {
    e.printStackTrace();
  }
  }
  
  
}