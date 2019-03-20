# win10下安装centos7

### wsl简介

​      Windows Subsystem for Linux（简称WSL）是一个为在Windows 10上能够原生运行Linux二进制可执行文件（ELF格式）的兼容层。它是由[微软](https://baike.baidu.com/item/%E5%BE%AE%E8%BD%AF/124767)与Canonical公司合作开发，目标是使纯正的Ubuntu 14.04 "Trusty Tahr"映像能下载和解压到用户的本地计算机，并且映像内的工具和实用工具能在此子系统上原生运行

   WSL是一些组件的集合，允许原生的[Linux](https://baike.baidu.com/item/Linux) [ELF](https://baike.baidu.com/item/ELF/7120560)64二进制文件跑在Windows上。它同时包括了用户态和内核态组件，主要包含以下部分：

1. 用户态会话管理服务处理Linux实例的生命周期

2. Pico provider drivers (lxss.sys, lxcore.sys)“翻译”系统调用，以模拟Linux内核

3. Pico 进程管理原生的用户态Linux（比如/bin/bash)

奇迹就发生于用户态的Linux二进制文件和Windows内核组件之间。通过将未经修改的Linux二进制文件放置于Pico进程中，我们把Linux系统调用直接导入Windows内核中。lxss.sys, lxcore.sys驱动将Linux系统调用翻译为NT APIs，来模拟Linux内核

###  安装步骤

##### 1.win10下打开wsl

使用管理员权限打开 powershell,执行 `打开powershell快捷键 win+X+A `

```powershell
Enable-WindowsOptionalFeature -Online -FeatureName Microsoft-Windows-Subsystem-Linux
```

##### 2. 安装 chocolatey

参考 ：https://chocolatey.org/install

使用管理员权限打开 powershell,执行 

```powershell
Set-ExecutionPolicy Bypass -Scope Process -Force; iex ((New-Object System.Net.WebClient).DownloadString('https://chocolatey.org/install.ps1'))
```

##### 3. 安装LxRunOffline

```powershell
choco install lxrunoffline
```

##### 4.下载 Centos 7 的docker 镜像

可以参考 https://github.com/RoliSoft/WSL-Distribution-Switcher  来下载。

或者直接下载 下面的链接给出的镜像。

```bash
wget https://raw.githubusercontent.com/CentOS/sig-cloud-instance-images/a77b36c6c55559b0db5bf9e74e61d32ea709a179/docker/centos-7-docker.tar.xz
```

centos docker镜像下载地址:https://hub.docker.com/_/centos/?tab=description

##### 5. 使用 LxRunOffline 部署 Centos 到WSL

```powershell
 LxRunOffline.exe  install -n centos -d E:\ProgramData\Microsoft\Windows\WSL\CentOS -f  E:\ProgramData\Microsoft\Windows\WSL\centos-7-docker.tar.xz
```

其中 -d 后面是要安装到的目录，-f 是前面下载的镜像， -n 用来指定名称。

然后使用  LxRunOffine 来开启 Centos

```powershell
 LxRunOffline  run  -n centos
```

当然，如果你只安装了这一个WSL，那直接输入bash 也可以进行WSL.

