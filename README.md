# hadoop--> hdfs api and mapReduce

## hadoop---> HDFS(分布式文件系统)        java的相关文件操作api (在 hadoop-start项目都通过后,这边可以简单测试)   

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


## hadoop-----> MapReduce （分布式计算框架）   数据计算

* Temperature 类为主类，内含两个静态处理类，即mapReduce的两个数据处理组件 map 和 reduce
* 参考连接： https://github.com/17661977890/interview-docs/blob/master/docs/hadoop/hadoop04.md
----
* **本文测试：**  
* （1）本地新建123.text 文件，内容为：2014010216 2014010410 2012010609 2012010812 2012011023 2001010212 2001010411 2013010619 2013010812 2013011023 2008010216 2008010414 2007010619 2007010812 2007011023 2010010216 2010010410 2015010649 2015010812 2015011023
    
    上述是文件中的一行内容，我们的map处理类中，会进行相应数据的格式处理，当然mapReduce 貌似底层默认以\t 制表符为换行读取，具体百度。
    
    将此文件通过之前写的 api 上传到你得 hdfs 根目录的指定文件下中  执行方法：putFile("C:\\Users\\彬\\Desktop\\123.txt", "/usr/local/input");

* （2）编写代码 只有一个类Temperature
    
    代码main方法的输入路径和输出路径需要注意 换成自己的ip 以及 文件位置，输出路径必须是不存在的文件（空的也不行）-否则部署运行时候报错

* （3）项目打包,idea打包，记得配置主类,步骤按照一下图片操作：
    
    ![image](https://github.com/17661977890/hadoop-api/blob/master/src/main/resources/image/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20190718114114.png)
    
    ![image](https://github.com/17661977890/hadoop-api/blob/master/src/main/resources/image/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20190718114328.png)
    
    ![image](https://github.com/17661977890/hadoop-api/blob/master/src/main/resources/image/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20190718114333.png)

    ![image](https://github.com/17661977890/hadoop-api/blob/master/src/main/resources/image/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20190718114430.png)
    
    ![image](https://github.com/17661977890/hadoop-api/blob/master/src/main/resources/image/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20190718114434.png)
    
    * 打包成功后，找到自己的jar包位置，拷贝到你得虚拟机的某个位置，然后执行部署命令
    
    ![image](https://github.com/17661977890/hadoop-api/blob/master/src/main/resources/image/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20190718114506.png)
 
* (4) 将打包生成的jar包放入hadoop集群中运行，hadoop一般会有多个节点，一个namenode节点和多个datanode节点，这里只需要把jar放入namenode中，并使用相应的hadoop命令即可.
    ```bash
    # 格式：hadoop jar [jar文件位置] [jar 主类] [HDFS输入位置] [HDFS输出位置] 我们在项目代码写死输入输出位置，所以后两个不用加
    hadoop jar [jar文件位置] [jar主类]
    
    # 执行成功日志：---但是没有输出我的打桩语句，不知为何
    [root@sun hadoop]# hadoop jar /home/admin/hadoop-api.jar com.example.hadoopapi.Temperature
    19/07/18 11:03:16 INFO client.RMProxy: Connecting to ResourceManager at sun.com/192.168.2.31:8032
    19/07/18 11:03:16 WARN mapreduce.JobResourceUploader: Hadoop command-line option parsing not performed. Implement the Tool interface and execute your application with ToolRunner to remedy this.
    19/07/18 11:03:18 INFO input.FileInputFormat: Total input paths to process : 1
    19/07/18 11:03:18 INFO mapreduce.JobSubmitter: number of splits:1
    19/07/18 11:03:18 INFO mapreduce.JobSubmitter: Submitting tokens for job: job_1563414652869_0006
    19/07/18 11:03:18 INFO impl.YarnClientImpl: Submitted application application_1563414652869_0006
    19/07/18 11:03:18 INFO mapreduce.Job: The url to track the job: http://sun.com:8088/proxy/application_1563414652869_0006/
    19/07/18 11:03:18 INFO mapreduce.Job: Running job: job_1563414652869_0006
    19/07/18 11:03:29 INFO mapreduce.Job: Job job_1563414652869_0006 running in uber mode : false
    19/07/18 11:03:29 INFO mapreduce.Job:  map 0% reduce 0%
    19/07/18 11:03:35 INFO mapreduce.Job:  map 100% reduce 0%
    19/07/18 11:03:41 INFO mapreduce.Job:  map 100% reduce 100%
    19/07/18 11:03:42 INFO mapreduce.Job: Job job_1563414652869_0006 completed successfully
    19/07/18 11:03:42 INFO mapreduce.Job: Counters: 49
    	File System Counters
    		FILE: Number of bytes read=116
    		FILE: Number of bytes written=245647
    		FILE: Number of read operations=0
    		FILE: Number of large read operations=0
    		FILE: Number of write operations=0
    		HDFS: Number of bytes read=333
    		HDFS: Number of bytes written=64
    		HDFS: Number of read operations=6
    		HDFS: Number of large read operations=0
    		HDFS: Number of write operations=2
    	Job Counters 
    		Launched map tasks=1
    		Launched reduce tasks=1
    		Data-local map tasks=1
    		Total time spent by all maps in occupied slots (ms)=4231
    		Total time spent by all reduces in occupied slots (ms)=3757
    		Total time spent by all map tasks (ms)=4231
    		Total time spent by all reduce tasks (ms)=3757
    		Total vcore-milliseconds taken by all map tasks=4231
    		Total vcore-milliseconds taken by all reduce tasks=3757
    		Total megabyte-milliseconds taken by all map tasks=4332544
    		Total megabyte-milliseconds taken by all reduce tasks=3847168
    	Map-Reduce Framework
    		Map input records=1
    		Map output records=10
    		Map output bytes=90
    		Map output materialized bytes=116
    		Input split bytes=113
    		Combine input records=0
    		Combine output records=0
    		Reduce input groups=8
    		Reduce shuffle bytes=116
    		Reduce input records=10
    		Reduce output records=8
    		Spilled Records=20
    		Shuffled Maps =1
    		Failed Shuffles=0
    		Merged Map outputs=1
    		GC time elapsed (ms)=149
    		CPU time spent (ms)=980
    		Physical memory (bytes) snapshot=309649408
    		Virtual memory (bytes) snapshot=4220010496
    		Total committed heap usage (bytes)=165810176
    	Shuffle Errors
    		BAD_ID=0
    		CONNECTION=0
    		IO_ERROR=0
    		WRONG_LENGTH=0
    		WRONG_MAP=0
    		WRONG_REDUCE=0
    	File Input Format Counters 
    		Bytes Read=220
    	File Output Format Counters 
    		Bytes Written=64
    ====>Finished：  true
    
    # 成功之后，用命令测试输出文件
    [root@sun hadoop]# hdfs dfs -ls /usr/local/output12
    Found 2 items
    -rw-r--r--   1 root supergroup          0 2019-07-18 10:57 /usr/local/output12/_SUCCESS
    -rw-r--r--   1 root supergroup         64 2019-07-18 10:57 /usr/local/output12/part-r-00000
    [root@sun hadoop]# hdfs dfs cat /usr/local/output12/*
    cat: Unknown command
    Did you mean -cat?  This command begins with a dash.
    [root@sun hadoop]# hdfs dfs -cat /usr/local/output12/*
    2001	12
    2007	12
    2008	14
    2010	16
    2012	12
    2013	23
    2014	10
    2015	49
    
    # 按说打jar包时候配置了主类，这里的部署命令就不用加主类，但是不加就会报错，不写全路径也会报错。
    [root@sun hadoop]# hadoop jar /home/admin/hadoop-api.jar
    RunJar jarFile [mainClass] args...
    [root@sun hadoop]# hadoop jar /home/admin/hadoop-api.jar Temperature
    Exception in thread "main" java.lang.ClassNotFoundException: Temperature
    	at java.net.URLClassLoader.findClass(URLClassLoader.java:382)
    	at java.lang.ClassLoader.loadClass(ClassLoader.java:424)
    	at java.lang.ClassLoader.loadClass(ClassLoader.java:357)
    	at java.lang.Class.forName0(Native Method)
    	at java.lang.Class.forName(Class.java:348)
    	at org.apache.hadoop.util.RunJar.run(RunJar.java:219)
    	at org.apache.hadoop.util.RunJar.main(RunJar.java:141)

    ```
   * ps: 第一次搞得时候，一直报一个类没有发现得错误，就是mapReduce的 Mapper数据处理逻辑类：TempMapper class no found,网上找了很多都没找到解决方案，貌似是打jar包的问题，我一直尝试未果，第二天来把之前pom springboot 的maven打包插件去掉，将之前的启动类也去掉，就好了，但是我有恢复发现还是ok的，所以也不是这个问题，应该是打包过程idea给我偷懒了吧?
   

### mapReduce 概述：
    
  * Hadoop Map/Reduce是一个使用简易的软件框架，基于它写出来的应用程序能够运行在由上千个商用机器组成的大型集群上，并以一种可靠容错的方式并行处理上T级别的数据集。
    
    一个Map/Reduce 作业（job） 通常会把输入的数据集切分为若干独立的数据块，由 map任务（task）以完全并行的方式处理它们。 **框架会对map的输出先进行排序， 然后把结果输入给reduce任务**。 通常作业的输入和输出都会被存储在文件系统中。 整个框架负责任务的调度和监控，以及重新执行已经失败的任务。
    
    通常，Map/Reduce框架和分布式文件系统是运行在一组相同的节点上的，也就是说，计算节点和存储节点通常在一起。这种配置允许框架在那些已经存好数据的节点上高效地调度任务，这可以使整个集群的网络带宽被非常高效地利用。
    
    Map/Reduce框架由一个单独的master JobTracker 和每个集群节点一个slave TaskTracker共同组成。master负责调度构成一个作业的所有任务，这些任务分布在不同的slave上，master监控它们的执行，重新执行已经失败的任务。而slave仅负责执行由master指派的任务。
    
    应用程序至少应该指明输入/输出的位置（路径），并通过实现合适的接口或抽象类提供map和reduce函数。再加上其他作业的参数，就构成了作业配置（job configuration）。然后，Hadoop的 job client提交作业（jar包/可执行程序等）和配置信息给JobTracker，后者负责分发这些软件和配置信息给slave、调度任务并监控它们的执行，同时提供状态和诊断信息给job-client。
    
    虽然Hadoop框架是用JavaTM实现的，但Map/Reduce应用程序则不一定要用 Java来写 。
    
  * http://hadoop.apache.org/docs/r1.0.4/cn/mapred_tutorial.html   参考