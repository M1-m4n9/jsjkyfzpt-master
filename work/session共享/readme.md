# tomcat的安装与配置
## tomcat的安装
**官网下载**

[Apache Tomcat官网](https://tomcat.apache.org/)

>  我下载的版本为**8.5.82**

![在这里插入图片描述](https://img-blog.csdnimg.cn/7e354ff584b74ca780399b8490b042ca.png)


**解压两份**

![在这里插入图片描述](https://img-blog.csdnimg.cn/a97adaace08743d0be95900e6825f518.png)

## tomcat集群的配置

**官网提供的配置**

[官网配置地址](https://tomcat.apache.org/tomcat-8.5-doc/cluster-howto.html)

![在这里插入图片描述](https://img-blog.csdnimg.cn/c560ac8546ee42b494d6b0fe1545a807.png)

**我的配置步骤**

```xml
  <Cluster className="org.apache.catalina.ha.tcp.SimpleTcpCluster"
                 channelSendOptions="8">

          <Manager className="org.apache.catalina.ha.session.DeltaManager"
                   expireSessionsOnShutdown="false"
                   notifyListenersOnReplication="true"/>

          <Channel className="org.apache.catalina.tribes.group.GroupChannel">
            <Membership className="org.apache.catalina.tribes.membership.McastService"
                        address="228.0.0.4"
                        port="45564"
                        frequency="500"
                        dropTime="3000"/>
            <Receiver className="org.apache.catalina.tribes.transport.nio.NioReceiver"
                      address="auto"
                      port="4000"
                      autoBind="100"
                      selectorTimeout="5000"
                      maxThreads="6"/>

            <Sender className="org.apache.catalina.tribes.transport.ReplicationTransmitter">
              <Transport className="org.apache.catalina.tribes.transport.nio.PooledParallelSender"/>
            </Sender>
            <Interceptor className="org.apache.catalina.tribes.group.interceptors.TcpFailureDetector"/>
            <Interceptor className="org.apache.catalina.tribes.group.interceptors.MessageDispatchInterceptor"/>
          </Channel>

          <Valve className="org.apache.catalina.ha.tcp.ReplicationValve"
                 filter=""/>
          <Valve className="org.apache.catalina.ha.session.JvmRouteBinderValve"/>

          <Deployer className="org.apache.catalina.ha.deploy.FarmWarDeployer"
                    tempDir="/tmp/war-temp/"
                    deployDir="/tmp/war-deploy/"
                    watchDir="/tmp/war-listen/"
                    watchEnabled="false"/>

          <ClusterListener className="org.apache.catalina.ha.session.ClusterSessionListener"/>
        </Cluster>
```

**1、将上面的复制到两个tomcat的server.xml配置文件中**


![在这里插入图片描述](https://img-blog.csdnimg.cn/aadbe1a6fd1b44af81387a16dd4c32ea.png)


**2、改端口**


> 为啥改端口：**因为我只有一台电脑**
> </br>
> 只要不与tomcat1相同就行


![在这里插入图片描述](https://img-blog.csdnimg.cn/f1a3242f29d94d73a7d2977f528b8433.png)

![在这里插入图片描述](https://img-blog.csdnimg.cn/64316338d2b34668872effce067c0db0.png)



![在这里插入图片描述](https://img-blog.csdnimg.cn/7d23fc889de94f8b96035ffc52eac3aa.png)

![在这里插入图片描述](https://img-blog.csdnimg.cn/fda2f588963f4ee99b4b239b97ed1607.png)


**3、在tomcat1和tomcat2中的webapps\ROOT目录下删除页面然后加上这三个页面**


> **第一个是index，第二个是login，第三个是logout**
> index是首页，login是设置session，logout是删除session

> 把tomcat2改为tomcat1然后继续复制

![在这里插入图片描述](https://img-blog.csdnimg.cn/80ccadf49bc94ffcaf21987bfa22fb3f.png)


```java
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
	<title>
	tomcat2
	</title>
	</head>
    <body>
        SessionID:<%=session.getId()%>
		<br>
        SessionValue:<%=session.getAttribute("session")%>
        <br>
		SessionIP:<%=request.getServerName()%>
        <br>
		SessionPort:<%=request.getServerPort()%>
		<br>
        <%
            out.println("this is tomcat 2");
        %>
    </body>
</html>
```

```java
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
	<title>
	tomcat2
	</title>
	</head>
    <body>
        <%
			session.setAttribute("session","libo");
		%>
        <%
            out.println("this is set SessionValue success");
        %>
    </body>
</html>
```

```java
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
	<title>
	tomcat2
	</title>
	</head>
    <body>
        <%
			session.removeAttribute("session");
		%>
        <%
            out.println("this is remove SessionValue success");
        %>
    </body>
</html>
```


# session问题解决方法
## 前端存储
### 原理

> 每次前端发请求时候都把这个携带走

![在这里插入图片描述](https://img-blog.csdnimg.cn/4909059c93fb438baa6d6a29cd5169ca.png)


### 优缺点


> **优点**
> 
> 不占用服务端内存
>
> **缺点**
>
> 存在安全风险
>
> 数据大小受cookie限制
>
> 占用外网宽带

## session的复制
### 操作步骤

> 在每个tomcat 的 "webapps\ROOT\WEB-INF\web.xml" 里面加上  distributable标签

![在这里插入图片描述](https://img-blog.csdnimg.cn/e51cf65ffef444be8e9ced407ab2c1b3.png)


**都没有登录**


![在这里插入图片描述](https://img-blog.csdnimg.cn/05d7e0229a36468592abd08f23b5fbbe.png)
![在这里插入图片描述](https://img-blog.csdnimg.cn/e1121723bd26439a841cd6aa84dc9310.png)


**tomcat1登录**


![在这里插入图片描述](https://img-blog.csdnimg.cn/a464ebfd58334b6b8ed5e1334fbad6a6.png)


**tomcat2查看是否可以获取数据**


> 获取成功



![在这里插入图片描述](https://img-blog.csdnimg.cn/8d8ea8de9d134fb49f244b64d6312564.png)

**tomcat1退出**


![在这里插入图片描述](https://img-blog.csdnimg.cn/b528729fd1b44e7a99ffd50325cc17be.png)


**tomcat2查看是否可以获取数据**


> 获取不到了，因为session是共享，已经在tomcat1那里退出登录了



![在这里插入图片描述](https://img-blog.csdnimg.cn/495cf848e3b9456f84ef728bcbc72a3e.png)

### 原理

> 多个server之间相互同步session，这样每个server之间都包含全部的session


![在这里插入图片描述](https://img-blog.csdnimg.cn/631068384eea4249a46d73f3b50066b4.png)

### 优缺点

> **优点**
>
> 1、只需要设定配置，应用程序不需要修改代码
>
> 2、有一台机器挂了，其他机器还保存有
>
>
> **缺点**
>
> 1、ession的同步需要数据传输，占内网带宽，有延时
>
> 2、所有server都包含所有session数据，数据量受最小内存的sever限制


## session粘性
### 操作步骤
**下载安装nginx**

> 官网下载，我是已经下载过的了
>
> CSDN博客找篇按照下载

**配置nginx.conf**

> 这样就会轮询两个tomcat服务器

![在这里插入图片描述](https://img-blog.csdnimg.cn/e4c36920337b481b80d4e519695e53d3.png)
**把刚才tomcat里面的web.xml去掉distributable标签**

**访问localhost然后就会一直是一个tomcat**

![在这里插入图片描述](https://img-blog.csdnimg.cn/e21a730aafb5413b85cba7c32e902bdb.png)
**注意**

> 如果还是轮询的话,可能是配置文件没有生效
>
>  去任务管理器关闭所有带有nginx的进程
![在这里插入图片描述](https://img-blog.csdnimg.cn/fc05d8db557f41cba5be75bd7f26a312.png)

### 原理

> 可以通过某种形式，将用户的每次请求都固定到某一台机器上。例如通过Nginx的ip_hash策略进行负载均衡，只要用户的IP固定不变，总能访问到同一台服务器上

![在这里插入图片描述](https://img-blog.csdnimg.cn/b57e19917ee54ea3b9ba346b23fee7b8.png)

### 优缺点

> **优点**
>
> 无需修改代码
>
> 服务端可以水平扩展
>
> **缺点**
>
>如果某台服务器挂掉了，Session就会丢失
>
> 如果一个局域网内大量用户那么也没啥用了（使用默认的ip_hash算法）


## 后端存储(Mysql、Redis等)

> 原理都是一样的，但是如果要是海量请求同时发送，请求数据库，容易造成数据库的压力过大从而崩溃，**所以我们演示是基于Redis**

### 操作步骤
访问我仓库下载代码
### 原理

![在这里插入图片描述](https://img-blog.csdnimg.cn/239d9b23b77846f18c0c6ac41974b255.png)


> 传统模式中，当request进入web容器，根据request获取session时，如果容器内存在session则返回，如果不存在就创建一个session然后返回，将sessionId返回
> 后端存储就是将session从容器里面抽出来，形成独立的模块，以便分布式或者集群能够共享。当request进入容器时，根据request获取session时，去找redis如果存在就返回，如果不存在就创建并存储

### 优缺点

> **优点**
>
> 代码灵活，对web容器无侵入
>
> 速度比mysql更快
> 
> **缺点**
>
> 需要编写代码

