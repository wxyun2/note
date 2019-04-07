# docker入门教程

>此教程基于centos7.6版本

## 1. docker简介

docker目前有两个版本:

- Community Edition (CE)     社区版 (免费)
- Community Edition (CE)     企业版 (收费)

> Docker Community Edition（CE）非常适合希望开始使用Docker并尝试使用基于容器的应用程序的个人开发人员和小型团队。

> Docker企业版（EE）专为企业开发和IT团队而设计，他们在生产中大规模构建，发布和运行业务关键型应用程序。

#### docker 架构

Docker 使用客户端-服务器 (C/S) 架构模式，使用远程API来管理和创建Docker容器。

Docker 容器通过 Docker 镜像来创建。

容器与镜像的关系类似于面向对象编程中的对象与类。

| Docker | 面向对象 |
| ------ | -------- |
| 容器   | 对象     |
| 镜像   | 类       |

![576507-docker1.png](https://github.com/wxyun2/note/blob/master/imgs/576507-docker1.png?raw=true)

| Docker 镜像(Images)    | Docker 镜像是用于创建 Docker 容器的模板。                    |
| ---------------------- | ------------------------------------------------------------ |
| Docker 容器(Container) | 容器是独立运行的一个或一组应用。                             |
| Docker 客户端(Client)  | Docker 客户端通过命令行或者其他工具使用 Docker API (<https://docs.docker.com/reference/api/docker_remote_api>) 与 Docker 的守护进程通信。 |
| Docker 主机(Host)      | 一个物理或者虚拟的机器用于执行 Docker 守护进程和容器。       |
| Docker 仓库(Registry)  | Docker 仓库用来保存镜像，可以理解为代码控制中的代码仓库。Docker Hub([https://hub.docker.com](https://hub.docker.com/)) 提供了庞大的镜像集合供使用。 |

#### 对容器的简要说明

**镜像**是一种轻量级、可执行的独立软件包，它包含运行某个软件所需的所有内容，包括代码、运行时、库、环境变量和配置文件。

**容器**是镜像的运行时实例 - 实际执行时镜像会在内存中变成什么。默认情况下，它完全独立于主机环境运行，仅在配置为访问主机文件和端口的情况下才执行此操作。

容器在主机内核上以本机方式运行应用。与仅通过管理程序对主机资源进行虚拟访问的虚拟机相比，它们具有更好的性能特征。容器可以获取本机访问，每个容器都在独立进程中运行，占用的内存不超过任何其他可执行文件。

## 2. 软件安装

#### 注意事项

目前，CentOS 仅发行版本中的内核支持 Docker。

Docker 运行在 CentOS 7 上，要求系统为64位、系统内核版本为 3.10 以上。

Docker 运行在 CentOS-6.5 或更高的版本的 CentOS 上，要求系统为64位、系统内核版本为 2.6.32-431 或者更高版本。

Docker 要求 CentOS 系统的内核版本高于 3.10 ，查看本页面的前提条件来验证你的CentOS 版本是否支持 Docker 。

```bash
#通过 uname -r 命令查看你当前的内核版本
uname -r
```

#### 关闭系统防火墙和selinux

```bash 
#检查防火墙状态 not running代表关闭 running代表开启
firewall-cmd --state   #查看防火墙状态

#停止防火墙
systemctl stop firewalld 

#关闭selinux
#修改/etc/selinux/config 文件
#将SELINUX=enforcing改为SELINUX=disabled
#重启系统

#临时关闭selinux
setenforce 0

```

#### 移除老版本的docker

```bash
yum remove docker docker-client docker-client-latest docker-common docker-latest docker-latest-logrotate docker-logrotate docker-engine
```

#### 安装docker依赖

```bash
yum install -y yum-utils \
  device-mapper-persistent-data \
  lvm2
```

#### 配置dockerce yum源

```bash
#官方
yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo
#阿里云
yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
```

> 官方的源可能在国内访问比较慢，推荐使用阿里云或其他国内的yum源

#### 安装

```bash
#yum安装dockerce版本
yum install -y docker-ce
#启动docker服务
systemctl start docker
#加入开机启动
systemctl enable docker
```

#### 测试

```bash
#运行hello wrold  控制台有打印出 Hello from Docker!即可
docker run hello-world
```

#### 查看docker版本

```bash
# 查看docker详细信息 下面的
[root@localhost ~]# docker info
Containers: 1  #容器数量
 Running: 0    #正在运行的容器数量
 Paused: 0    
 Stopped: 1    #已停止的容器数量
Images: 1      #目前本地有一个镜像，可以使用 docker images查看本地有的镜像列表
Server Version: 18.09.4  #docker服务端版本
Storage Driver: overlay2 #存储驱动
 Backing Filesystem: xfs
 Supports d_type: true
 Native Overlay Diff: true
Logging Driver: json-file
Cgroup Driver: cgroupfs
Plugins:
 Volume: local
 Network: bridge host macvlan null overlay
 Log: awslogs fluentd gcplogs gelf journald json-file local logentries splunk syslog
Swarm: inactive
Runtimes: runc
Default Runtime: runc
Init Binary: docker-init
containerd version: bb71b10fd8f58240ca47fbb579b9d1028eea7c84
runc version: 2b18fe1d885ee5083ef9f0838fee39b62d653e30
init version: fec3683
Security Options:
 seccomp
  Profile: default
Kernel Version: 3.10.0-957.el7.x86_64
Operating System: CentOS Linux 7 (Core)
OSType: linux
Architecture: x86_64
CPUs: 1
Total Memory: 972.6MiB
Name: localhost.localdomain
ID: K2SF:WXE4:XDHH:JAIQ:OJJ5:AHTC:T4PQ:BEAR:2KFE:B4HV:X5W3:KWCD
Docker Root Dir: /var/lib/docker
Debug Mode (client): false
Debug Mode (server): false
Registry: https://index.docker.io/v1/
Labels:
Experimental: false
Insecure Registries:
 127.0.0.0/8
Live Restore Enabled: false
Product License: Community Engine

#查看当前docker版本
[root@localhost ~]# docker --version
Docker version 18.09.4, build d14af54266
```

## 3. 镜像管理

#### 镜像是什么？

- 一个分层存储的文件
- 一个软件的环境
- 一个镜像可以创建N个容器
- 一种标准化的交付
- 一个不包含linux内核而又精简的linux操作系统

镜像不是一个单一的文件，而是由多层构成。可以通过docker history <id/name> 查看镜像中各层内容及大小，每层对应着Dockerfile中的一条指令。Docker镜像默认存储在/var/lib/docker/<storage-driver> 中

```bash
[root@localhost ~] cd /var/lib/docker
#docker默认使用overlay2存储驱动
[root@localhost docker] ls
builder  buildkit  containers  image  network  overlay2  plugins  runtimes  swarm  tmp  trust  volumes
[root@localhost overlay2] pwd
/var/lib/docker/overlay2
[root@localhost overlay2] ls
1955b47eabc610e46c96089f65d33933a7eddfcd2199b04807e4aaed1173e0db       b4f44b55778c0c976a63681749b6f883b7e621acfb92c323db15ec4f17eaa474
7bff76819074c72519cde252ec530ed2d7c64911386e98a152d39880b1510f02       backingFsBlockDev
7bff76819074c72519cde252ec530ed2d7c64911386e98a152d39880b1510f02-init  c7be70536cb42e6ac53a00863206b0ff13493b26322fe1e8c835193a07d8a24c
7f1040ebeff6ef9a87e0e96dcbefdf46eafa1db8cb9884ffae26120499184de9       l
a09fb7d926b488dba4b83490263b43028059147a045e647d8b54761164ae63b9
```

#### 镜像从哪里来？

Docker Hub是由Docker公司负责维护的公共注册中心，包含大量的容器镜像，Docker工具默认从这个公共镜像仓库下载镜像。

地址：https://hub.docker.com/explore

```bash
#在控制台可以使用docker search <镜像名> 来搜索镜像
[root@localhost overlay2] docker search nginx
```

#### 镜像与容器的联系

![a29a8fc658e05344443774d37fa9912d.jpg](https://github.com/wxyun2/note/blob/master/imgs/a29a8fc658e05344443774d37fa9912d.jpg?raw=true)

##### 镜像（Image）——一个特殊的文件系统

操作系统分为内核和用户空间。对于Linux而言，内核启动后，会挂载root文件系统为其提供用户空间支持。而Docker镜像（Image），就相当于是一个root文件系统。

Docker镜像是一个特殊的文件系统，除了提供容器运行时所需的程序、库、资源、配置等文件外，还包含了一些为运行时准备的一些配置参数（如匿名卷、环境变量、用户等）。 镜像不包含任何动态数据，其内容在构建之后也不会被改变。

Docker设计时，就充分利用Union FS的技术，将其设计为分层存储的架构。 镜像实际是由多层文件系统联合组成。

##### 容器（Container）——镜像运行时的实体

 镜像（Image）和容器（Container）的关系，就像是面向对象程序设计中的类和实例一样，镜像是静态的定义，容器是镜像运行时的实体。容器可以被创建、启动、停止、删除、暂停等 。

容器的实质是进程，但与直接在宿主执行的进程不同，容器进程运行于属于自己的独立的命名空间。前面讲过镜像使用的是分层存储，容器也是如此。

容器存储层的生存周期和容器一样，容器消亡时，容器存储层也随之消亡。因此，任何保存于容器存储层的信息都会随容器删除而丢失。

按照Docker最佳实践的要求，容器不应该向其存储层内写入任何数据 ，容器存储层要保持无状态化。所有的文件写入操作，都应该使用数据卷（Volume）、或者绑定宿主目录，在这些位置的读写会跳过容器存储层，直接对宿主（或网络存储）发生读写，其性能和稳定性更高。数据卷的生存周期独立于容器，容器消亡，数据卷不会消亡。因此， 使用数据卷后，容器可以随意删除、重新run，数据却不会丢失。  

#### 镜像管理常用命令

格式: docker image <command>

| 指令    | 描述                                         |
| ------- | -------------------------------------------- |
| ls      | 列出镜像                                     |
| build   | 使用Dockerfile构建镜像                       |
| history | 查看镜像历史                                 |
| inspect | 显示一个或多个镜像详细信息                   |
| pull    | 从镜像仓库拉取镜像                           |
| push    | 推送一个镜像到镜像仓库                       |
| rm      | 移除一个或多个镜像                           |
| prune   | 移除未使用的镜像。没有被标记或被任何容器引用 |
| tag     | 创建一个引用源镜像标记目标镜像               |
| export  | 导出容器文件系统到tar归档文件                |
| import  | 导入容器文件系统tar归档文件创建镜像          |
| save    | 保存一个或多个镜像到一个tar归档文件          |
| load    | 加载来自tar归档或标准输入的镜像              |

例子：

##### 1.列出镜像

```bash
[root@localhost overlay2]# docker image ls
REPOSITORY          TAG                 IMAGE ID            CREATED             SIZE
nginx               latest              2bcb04bdb83f        10 days ago         109MB
centos              latest              9f38484d220f        3 weeks ago         202MB
hello-world         latest              fce289e99eb9        3 months ago        1.84kB
#下面这个是docker老版本支持的命令,建议使用上面的方式查看
[root@localhost overlay2]# docker images
REPOSITORY          TAG                 IMAGE ID            CREATED             SIZE
nginx               latest              2bcb04bdb83f        10 days ago         109MB
centos              latest              9f38484d220f        3 weeks ago         202MB
hello-world         latest              fce289e99eb9        3 months ago        1.84kB
```

##### 2.查看镜像详细信息

```bash
[root@localhost overlay2]# docker image inspect nginx
[
    {
        "Id": "sha256:2bcb04bdb83f7c5dc30f0edaca1609a716bda1c7d2244d4f5fbbdfef33da366c",
        "RepoTags": [
            "nginx:latest"
        ],
        "RepoDigests": [
            "nginx@sha256:c8a861b8a1eeef6d48955a6c6d5dff8e2580f13ff4d0f549e082e7c82a8617a2"
        ],
        "Parent": "",
        "Comment": "",
        "Created": "2019-03-26T23:13:42.01289097Z",
        "Container": "6c02a05b3d095c6e0f51aa3d6ff84c3cac8c76b8464ee4930c151b5afffce9ad",
        "ContainerConfig": {
            "Hostname": "6c02a05b3d09",
            "Domainname": "",
            "User": "",
            "AttachStdin": false,
            "AttachStdout": false,
            "AttachStderr": false,
            "ExposedPorts": {
                "80/tcp": {}
            },
            "Tty": false,
            "OpenStdin": false,
            "StdinOnce": false,
            "Env": [
                "PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin",
                "NGINX_VERSION=1.15.10-1~stretch",
                "NJS_VERSION=1.15.10.0.3.0-1~stretch"
            ],
            "Cmd": [
                "/bin/sh",
                "-c",
                "#(nop) ",
                "CMD [\"nginx\" \"-g\" \"daemon off;\"]"
            ],
            "ArgsEscaped": true,
            "Image": "sha256:eb70ea14d4ac658e54090a984eaf06ed1bc41efed0f688020d7b88d26ba38920",
            "Volumes": null,
            "WorkingDir": "",
            "Entrypoint": null,
            "OnBuild": null,
            "Labels": {
                "maintainer": "NGINX Docker Maintainers <docker-maint@nginx.com>"
            },
            "StopSignal": "SIGTERM"
        },
        "DockerVersion": "18.06.1-ce",
        "Author": "",
        "Config": {
            "Hostname": "",
            "Domainname": "",
            "User": "",
            "AttachStdin": false,
            "AttachStdout": false,
            "AttachStderr": false,
            "ExposedPorts": {
                "80/tcp": {}
            },
            "Tty": false,
            "OpenStdin": false,
            "StdinOnce": false,
            "Env": [
                "PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin",
                "NGINX_VERSION=1.15.10-1~stretch",
                "NJS_VERSION=1.15.10.0.3.0-1~stretch"
            ],
            "Cmd": [
                "nginx",
                "-g",
                "daemon off;"
            ],
            "ArgsEscaped": true,
            "Image": "sha256:eb70ea14d4ac658e54090a984eaf06ed1bc41efed0f688020d7b88d26ba38920",
            "Volumes": null,
            "WorkingDir": "",
            "Entrypoint": null,
            "OnBuild": null,
            "Labels": {
                "maintainer": "NGINX Docker Maintainers <docker-maint@nginx.com>"
            },
            "StopSignal": "SIGTERM"
        },
        "Architecture": "amd64",
        "Os": "linux",
        "Size": 109294563,
        "VirtualSize": 109294563,
        "GraphDriver": {
            "Data": {
                "LowerDir": "/var/lib/docker/overlay2/1955b47eabc610e46c96089f65d33933a7eddfcd2199b04807e4aaed1173e0db/diff:/var/lib/docker/overlay2/c7be70536cb42e6ac53a00863206b0ff13493b26322fe1e8c835193a07d8a24c/diff",
                "MergedDir": "/var/lib/docker/overlay2/7f1040ebeff6ef9a87e0e96dcbefdf46eafa1db8cb9884ffae26120499184de9/merged",
                "UpperDir": "/var/lib/docker/overlay2/7f1040ebeff6ef9a87e0e96dcbefdf46eafa1db8cb9884ffae26120499184de9/diff",
                "WorkDir": "/var/lib/docker/overlay2/7f1040ebeff6ef9a87e0e96dcbefdf46eafa1db8cb9884ffae26120499184de9/work"
            },
            "Name": "overlay2"
        },
        "RootFS": {
            "Type": "layers",
            "Layers": [
                "sha256:5dacd731af1b0386ead06c8b1feff9f65d9e0bdfec032d2cd0bc03690698feda",
                "sha256:dd0338cdfab32cdddd6c30efe8c89d0229d9f939e2bb736fbb0a52f27c2b0ee9",
                "sha256:7e274c0effe81c48f9337879b058c729c33bd0199e28e2c55093d79398f5e8c0"
            ]
        },
        "Metadata": {
            "LastTagTime": "0001-01-01T00:00:00Z"
        }
    }
]
```

##### 3.下载镜像

```bash
#冒号后面可以跟上指定版本，不加时，默认使用最新版本的镜像
[root@localhost overlay2]# docker image  pull nginx:1.14
1.14: Pulling from library/nginx
27833a3ba0a5: Already exists 
0f23e58bd0b7: Pull complete 
8ca774778e85: Pull complete 
Digest: sha256:f7988fb6c02e0ce69257d9bd9cf37ae20a60f1df7563c3a2a6abe24160306b8d
Status: Downloaded newer image for nginx:1.14
```

##### 4.删除镜像

```bash
[root@localhost overlay2]# docker image rm nginx:1.14
Untagged: nginx:1.14
Untagged: nginx@sha256:f7988fb6c02e0ce69257d9bd9cf37ae20a60f1df7563c3a2a6abe24160306b8d
Deleted: sha256:295c7be079025306c4f1d65997fcf7adb411c88f139ad1d34b537164aa060369
Deleted: sha256:19606512dfe192788a55d7c1efb9ec02041b4e318587632f755c5112f927e0e3
Deleted: sha256:0b83495b3ad3db8663870c3babeba503a35740537a5e25acdf61ce6a8bdad06f
```

##### 5.保存镜像

```bash
#保存镜像到标准输出
[root@localhost overlay2]# docker image save nginx > xxx.tar
```

## 4.容器管理

#### 创建容器的常用选项

语法:

> ```bash
> docker run [OPTIONS] IMAGE [COMMAND] [ARG...]
> ```

OPTIONS说明：

- **-a stdin:** 指定标准输入输出内容类型，可选 STDIN/STDOUT/STDERR 三项；
- **-d:** 后台运行容器，并返回容器ID；
- **-i:** 以交互模式运行容器，通常与 -t 同时使用；
- **-p:** 端口映射，格式为：**主机(宿主)端口:容器端口**
- **-t:** 为容器重新分配一个伪输入终端，通常与 -i 同时使用；
- **--name="nginx-lb":** 为容器指定一个名称；
- **--dns 8.8.8.8:** 指定容器使用的DNS服务器，默认和宿主一致；
- **--dns-search example.com:** 指定容器DNS搜索域名，默认和宿主一致；
- **-h "mars":** 指定容器的hostname；
- **-e username="ritchie":** 设置环境变量；
- **--env-file=[]:** 从指定文件读入环境变量；
- **--cpuset="0-2" or --cpuset="0,1,2":** 绑定容器到指定CPU运行；
- **-m :**设置容器使用内存最大值；
- **--net="bridge":** 指定容器的网络连接类型，支持 bridge/host/none/container: 四种类型；
- **--link=[]:** 添加链接到另一个容器；
- **--expose=[]:** 开放一个端口或一组端口；

例子:

使用docker镜像nginx:latest以后台模式启动一个容器,并将容器命名为mynginx。

```bash
docker run --name mynginx -d nginx:latest
```

使用镜像nginx:latest以后台模式启动一个容器,并将容器的80端口映射到主机随机端口。

```bash
docker run -P -d nginx:latest
```

使用镜像 nginx:latest，以后台模式启动一个容器,将容器的 80 端口映射到主机的 80 端口,主机的目录 /data 映射到容器的 /data。

```bash
docker run -p 80:80 -v /data:/data -d nginx:latest
```

绑定容器的 8080 端口，并将其映射到本地主机 127.0.0.1 的 80 端口上。

```bash
$ docker run -p 127.0.0.1:80:8080/tcp ubuntu bash
```

使用镜像nginx:latest以交互模式启动一个容器,在容器内执行/bin/bash命令。

```bash
runoob@runoob:~$ docker run -it nginx:latest /bin/bash
root@b8573233d675:/# 
```

  