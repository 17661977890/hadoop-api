# hadoop-api
hadoop 的java的相关文件操作api (在 hadoop-start项目都通过后,这边可以简单测试)

* 一开始遇到各种报错,大致如下: 

* 本地没有配置hadoop的环境变量  

* 上传文件后,在HDFS页面(50070)下载,网页报错,sun.com:50070 连接失败

原因:本地 etc/hosts没有配置虚拟机的ip 主机名 https://blog.csdn.net/Happy_Sunshine_Boy/article/details/84859643

* java.io.IOException: Could not locate executable D:\hadoop\hadoop-2.7.7\bin\winutils.exe in the Hadoop 

这个报错不用管不影响程序,有强迫症的可以自行百度

* 下载时候,json格式异常{{datanode 没有发现之类的}} 以及 Hadoop集群启动后，使用jps查看没有DataNode进程？

原因：在第一次格式化dfs后，启动并使用了hadoop，后来又重新执行了格式化命令（hdfs namenode -format)，这时namenode的clusterID会重新生成，而datanode的clusterID 保持不变。因此就会造成datanode与namenode之间的id不一致。

解决方法：删除dfs.data.dir（在core-site.xml中配置了此目录位置）目录里面的所有文件，重新格式化，最后重启。

![image](https://github.com/17661977890/hadoop-api/blob/master/src/main/resources/image/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20190717160125.png)
