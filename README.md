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

2. **执行ttt的过程中将执行文件`pardus`，`pardus.ko`，`version`，`start.sh`通过tftp传输到设备中。**

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

1. ~~在安卓端实现一个talnet连接功能的软件，将所有脚本的命令按功能需求执行。`start.sh`还是需要传送过去。~~	实现一个在设备中的客户端，建立连接时将客户端文件通过tftp传送到设备中。
2. 在安卓端用tftp传输脚本文件到设备中，根据需要设置的参数修改脚本文件，然后执行脚本文件。

看起来第一种实现方法比较好





## 需求分析

### 1. 系统或产品介绍

随着计算机信息技术的迅猛发展，我们对于各种设备工具的服务稳定性与可靠性等方面提出了更高的要求。为提高设备质量，开发人员需要采取严格的测试方法，最大程度保障设备的服务效率。

设备介绍：基于linux系统**待完成**

设备操作人员需要使用talnet与设备远程连接，执行linux命令完成初始化和升级等等操作，对于没有linux基础的人员来说上手困难，操作不便，因此开发一个基于安卓系统的设备管理测试软件势在必行，也可以大幅减少操作人员的工作量。

本设备测试与管理软件为基于linux系统的设备开发，主要用于设备参数配置与固件升级功能，同时可以显示设备当前的状态信息与日志管理。

### 2. 产品面向客户群体

使用设备的操作人员

### 3. 系统需求

| 功能性需求 | 描述                                    |
| ---------- | --------------------------------------- |
| 连接设备   | 与设备建立连接                          |
| 参数配置   | 修改设备以太网接口的mac地址和ip地址     |
| 固件升级   | 升级设备中的固件                        |
| 查看状态   | 查看设备cpu利用率、内存占用率等状态信息 |
| 输出日志   | 输出设备执行日志                        |

### 4. UML图



识别概念类：

用户/服务器端

- 服务器端控制器 

- tftpserver 

- 固件

- 初始化文件

设备 

- 客户端控制器 
- tftpcilent 
- 日志 
- 系统信息 







### 与设备连接

先假定有两个类实现两个功能，talnet实现linux的命令执行功能，tftp实现文件传输给设备的功能。（可执行文件需要存储在手机中？）

1. 输入设备的ip地址和登陆口令与设备连接；（此时talnet和tftp同时连接上）
2. 安装客户端

### 设置设备参数

1. 服务器：修改参数（以太网接口的ip地址和mac地址）改变`start.sh`的参数内容；
2. 服务器：利用tftp将`start.sh`传输进设备中；
3. 客户端：替换`start.sh`，执行`ttt.sh`中的操作（添加操作权限）；
4. 设置设备参数完成；

### 升级设备

1. 服务器：选择本地文件（`pardus`,`pardus.io`）;
2. 服务器：利用tftp将新的pardus传输进设备中；
3. 客户端：替换本地的pardus文件；
4. 升级完成；

### 查看设备参数

1. 服务器：发出心跳包指令查看设备状态；
2. 客户端：使用top/free和模式匹配等方式获取系统状态信息，生成json文件传回服务器；
3. 服务器：将收到的json文件转换成信息先死在软件中；

### 打印日志

1. 服务器：发出查询日志的请求；
2. 客户端：将日志利用tftp发送给服务器；
3. 服务器：软件中显示日志/将日志分享出去