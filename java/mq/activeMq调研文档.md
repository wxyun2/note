## [activemq 实时监控调研文档]
### 一、背景
​        最近在工作中处理问题时，经常出现mq因为积压消息过多，导致mq不能正常工作，基本处于不可用状态。区校数据同步也因此不能正常工作。鉴于此根据activemq官方文档提供的监控方案，主要有以下几种：jmx/jconsole(java扩展管理工具),webconsole(访问8161对应的那个界面),Advisory Message(消息公告),ActiveMQ.Agent(命令行),Visualisation plug-in(可视化插件),Statistics plug-in(统计插件),使用第三方工具如:AMon, HermesJMS等等,因为目前仅仅需要对消费者数量、队列中剩余消息数等做监控，再一个为了不引入第三方的项目，先对activemq官方提供的这几种方式做调研。下面对这几种方式实现方式的说明，优缺点对比。

### 二、方式对比

#### 1.jmx监控
    因为activemq是Java开发的程序，activemq原生就是支持JMX监控的。JMX监控是最全的，任何细节都可以通过JMX获取。如果远程连接JMX监控需要一些额外的配置。本机直接连接不需要，但是由于ActiveMQ是部署在linux上，所以应该很少有人会直接连接本地的JMX，除非是本地开发调试阶段。

##### 1) 修改/etc/hosts 检查hosts文件设置，不用127.0.0.1，用实际IP地址。
ps:为什么不能使用127.0.0.1
以下是我在本地测试使用jmx连接公司的开发服务器上的activemq时的报错信息:

```java
java.net.ConnectException: Connection refused: connect  
java.rmi.ConnectException: Connection refused to host: 127.0.0.1; nested exception is:   
    java.net.ConnectException: Connection refused: connect  
    at sun.rmi.transport.tcp.TCPEndpoint.newSocket(Unknown Source)  
    at sun.rmi.transport.tcp.TCPChannel.createConnection(Unknown Source)  
    at sun.rmi.transport.tcp.TCPChannel.newConnection(Unknown Source)  
    at sun.rmi.server.UnicastRef.invoke(Unknown Source)  
    at java.rmi.server.RemoteObjectInvocationHandler.invokeRemoteMethod(Unknown Source)  
    at java.rmi.server.RemoteObjectInvocationHandler.invoke(Unknown Source)  
    at $Proxy0.getAllSections(Unknown Source)  
    at ccg.boccrawler.rmi.TestClient.main(TestClient.java:21) 
```
在网上找了很久，也google了，没有太明白，有人说是因为rmi服务端造成的,
下面是网上哥们的原话:
```
原因：这个问题其实是由rmi服务器端程序造成的。 客户端程序向服务端请求一个对象的时候，返回的stub对象里面包含了服务器的hostname，客户端的后续操作根据这个hostname来连接服务器端。要想知道这个hostname具体是什么值可以   在服务器端bash中打入指令：hostname -i 如果返回的是127.0.0.1，那么你的客户端肯定会抛如标题的异常了。
```
按着这个哥们的意思就是说，如果要使用jmx来远程连接，要么jmx远程的程序需要和activemq服务在一台服务器上，或者修改本地的hostname，我查了一部分资料其实可以在mq启动时加上
-Djava.rmi.server.hostname=10.4.88.1 这个参数，也可以但是后面还有坑，请继续往下看，加上这个参数接着报错。
```java
Exception in thread "main" java.rmi.ConnectException: Connection refused to host: 10.4.88.1; nested exception is: 
	java.net.ConnectException: Connection timed out: connect
	at sun.rmi.transport.tcp.TCPEndpoint.newSocket(TCPEndpoint.java:619)
	at sun.rmi.transport.tcp.TCPChannel.createConnection(TCPChannel.java:216)
	at sun.rmi.transport.tcp.TCPChannel.newConnection(TCPChannel.java:202)
	at sun.rmi.server.UnicastRef.invoke(UnicastRef.java:130)
	at javax.management.remote.rmi.RMIServerImpl_Stub.newClient(Unknown Source)
	at javax.management.remote.rmi.RMIConnector.getConnection(RMIConnector.java:2430)
	at javax.management.remote.rmi.RMIConnector.connect(RMIConnector.java:308)
	at javax.management.remote.JMXConnectorFactory.connect(JMXConnectorFactory.java:270)
	at javax.management.remote.JMXConnectorFactory.connect(JMXConnectorFactory.java:229)
	at test.TestMain.main(TestMain.java:22)
Caused by: java.net.ConnectException: Connection timed out: connect
	at java.net.DualStackPlainSocketImpl.connect0(Native Method)
	at java.net.DualStackPlainSocketImpl.socketConnect(DualStackPlainSocketImpl.java:79)
	at java.net.AbstractPlainSocketImpl.doConnect(AbstractPlainSocketImpl.java:350)
	at java.net.AbstractPlainSocketImpl.connectToAddress(AbstractPlainSocketImpl.java:206)
	at java.net.AbstractPlainSocketImpl.connect(AbstractPlainSocketImpl.java:188)
	at java.net.PlainSocketImpl.connect(PlainSocketImpl.java:172)
	at java.net.SocksSocketImpl.connect(SocksSocketImpl.java:392)
	at java.net.Socket.connect(Socket.java:589)
	at java.net.Socket.connect(Socket.java:538)
	at java.net.Socket.<init>(Socket.java:434)
	at java.net.Socket.<init>(Socket.java:211)
	at sun.rmi.transport.proxy.RMIDirectSocketFactory.createSocket(RMIDirectSocketFactory.java:40)
	at sun.rmi.transport.proxy.RMIMasterSocketFactory.createSocket(RMIMasterSocketFactory.java:148)
	at sun.rmi.transport.tcp.TCPEndpoint.newSocket(TCPEndpoint.java:613)
	... 9 more
```
不死心，继续排查，发现jmx在启动时有一个rmi(remote managment interface 远程管理接口)的端口，还有一个本身jmx的端口，这两个端口，如果不使用-Dcom.sun.management.jmxremote.port=1099和-Dcom.sun.management.jmxremote.rmi.port=1099配置的话，在启动时activemq会随机使用两个端口启动。去stackoverflow上查了一下，老外说这两个端口可以配置为同一个，然后我在测试时发现并不行，activemq启动不成功，我去看了data/activemq.log并没有输出异常信息，这条路没有走完，等有机会再接着研究研究这个(囧),ps:后面有更好的方式实现监控。

##### 2) 修改active启动脚本

```shell
#修改${ACTIVEMQ_HOME}/bin/active文件。找到下面这一行添加下面的参数  
-Dcom.sun.management.jmxremote     
-Dcom.sun.management.jmxremote.port=1099
```
##### 3)  查看${ACTIVEMQ_HOME}/conf/activemq.xml中的broker节点中useJmx="true"属性。这个属性可以没有或者为true，但是不可以是false。
##### 4)  修改${ACTIVEMQ_HOME}/conf/activemq.xml中的broker节点中找到managementContext节点

```xml
 <managementContext createConnector="true" connectorPort="1099" connectorPath="/jmxrmi" jmxDomainName="org.apache.activemq"/>
```
#####  5) java连接activemq的jmx服务
在连接的时候需要加入以下jar包
activemq-all-5.9.1.jar

```java
public class TestMain {
    private static final String connectorPort = "1099";
    private static final String connectorPath = "/jmxrmi";
    private static final String jmxDomain = "org.apache.activemq";// 必须与activemq.xml中的jmxDomainName一致
    public static void main(String[] args) throws Exception {
        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://10.4.88.1:" + connectorPort + connectorPath);
        JMXConnector connector = JMXConnectorFactory.connect(url);
        connector.connect();
        MBeanServerConnection connection = connector.getMBeanServerConnection();
        ObjectName name = new ObjectName(jmxDomain + ":brokerName=localhost,type=Broker");
        BrokerViewMBean mBean = MBeanServerInvocationHandler.newProxyInstance(connection, name, BrokerViewMBean.class, true);
        
        for (ObjectName queueName : mBean.getQueues()) {
            QueueViewMBean queueMBean = MBeanServerInvocationHandler.newProxyInstance(connection, queueName, QueueViewMBean.class, true);
            System.out.println("\n------------------------------\n");
            // 消息队列名称
            System.out.println("Queue Name --- " + queueMBean.getName());
            // 队列中剩余的消息数
            System.out.println("Queue Size --- " + queueMBean.getQueueSize());
            // 消费者数
            System.out.println("Number of Consumers --- " + queueMBean.getConsumerCount());
            // 出队数
            System.out.println("Number of Dequeue ---" + queueMBean.getDequeueCount());
        }
    }
}
```
