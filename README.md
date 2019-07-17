# hadoop-api
hadoop 的java的相关文件操作api (在 hadoop-start项目都通过后,这边可以简单测试)

* 一开始遇到各种报错,大致如下: 
* 本地没有配置hadoop的环境变量  
* 上传文件后,在HDFS页面(50070)下载,网页报错,sun.com:50070 连接失败 原因:本地 etc/hosts没有配置虚拟机的ip 主机名 https://blog.csdn.net/Happy_Sunshine_Boy/article/details/84859643
* java.io.IOException: Could not locate executable D:\hadoop\hadoop-2.7.7\bin\winutils.exe in the Hadoop 这个报错不用管不影响程序,有强迫症的可以自行百度

![image](https://github.com/17661977890/hadoop-api/blob/master/src/main/resources/image/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20190717160125.png)
