# DeviceTestingApplication

测试系统管理包括测试系统的配置管理、软件版本管理和运行管理等。分析Android系统下测试系统管理APP的需求，掌握Android系统开发环境及相关开发技术，设计并实现Android系统下测试系统管理APP。

## 设备启动步骤

```shell
#help
#修改 ttt里的IP地址为计算机的地址78
#登录主板
cd  u
# -g  下载，-r 下载时间不受限制
tftp -g -r ttt  192.168.0.78
sh ttt
```

1. 使用tftp将ttt.sh传输到设备的`/u`目录中，执行ttt。

```shell
#ttt
tftp -g -r pardus 192.168.0.36
tftp -g -r pardus.ko 192.168.0.36
tftp -g -r version 192.168.0.36
tftp -g -r start.sh 192.168.0.36
chmod +x start.sh
chmod +x pardus
chmod +x pardus.ko
ls >disable
```

2. #### 执行ttt的过程中将执行文件`pardus`，`pardus.ko`，`version`，`start.sh`通过tftp传输到设备中。

3. 初始化工作由`start.sh`完成，`ustart.h`可以完成不同参数的初始化工作（需要实现软件在ui设置初始化的参数）

```shell
#start.sh
ifconfig eth1 down
ifconfig eth1 hw ether 40:3D:5F:20:00:30
ifconfig eth1 up
ifconfig eth1 192.168.0.80
ifconfig eth0 192.168.1.80
ifconfig eth0 down

#每次启动初始化将version字段+1
cat /u/version | while read var 
do
var=`expr $var + 1`
echo $var>/u/version
done

sleep 2
insmod /u/pardus.ko
/u/pardus

#ustart.sh
ifconfig eth1 down
ifconfig eth1 hw ether 40:3D:5F:20:05:72
ifconfig eth1 up
ifconfig eth1 192.168.0.72
ifconfig eth0 192.168.1.72
ifconfig eth0 down

cat /u/version | while read var 
do
var=`expr $var + 1`
echo $var>/u/version
done

sleep 2
insmod /u/pardus.ko
/u/pardus -g 80000/0
sleep 1
/u/pardus
```



## 需要实现的功能

1. 初始化

**参数**有哪些？从设备中传调整好参数的ttt和start

- 从安卓设备中将几个配置文件传到待测试设备中  ？？？**怎么传送**用**ftp**协议
- `start.sh`中的参数需要修改设备以太网接口的mac地址和ip地址

2. 升级

~~升级需要改变什么？~~

需要更新**pardus**和**pardus.ko**

3. 显示设备属性

cpu情况，内存情况，当前版本，ip地址等等信息。

使用top等命令，进行正则模式匹配得到对应系统信息，传至安卓设备中，形成设备信息，需要定期刷新？

4. 输出日志信息

   `pardus`会定期输出日志，需要将日志信息采集到安卓软件中





## 实现方法(二选一)

1. 在安卓端实现一个talnet连接功能的软件，将所有脚本的命令按功能需求执行。`start.sh`还是需要传送过去。
2. 在安卓端用tftp传输脚本文件到设备中，根据需要设置的参数修改脚本文件，然后执行脚本文件。

看起来第一种实现方法比较好