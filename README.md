# pth-framework

## 前言

pth-framework 这是一个Java技术栈自用框架以及插件工具集，它提供了：

* session管理；
* 权限管理；
* 对于基础orm的封装；
* 待补全。。。



## pth-session

pth-session子模块提供了一个最基本的Session管理功能。

你可以直接使用以下方式来获取并操作Session：

```java
    PthSession session = ServletSessionHolder.getSession();//获取Session
    session.getSessionData().put("test", System.currentTimeMillis());//设置session数据
    session.getSessionData().put("Servlet", "servlet");
```

对于Session的操作是由Filter自动提交的，这代表着Session的持久化操作对您来说是无感的；暂时提供了内存和Redis两种实现方式。

## pth-security

pth-security是一个轻量级的安全框架，依赖pth- session。提供了可独立引用的授权和鉴权功能。

分别是：

* pth-security-authentication（授权）
* pth-security-authorization（鉴权）

其基本思想是，鉴权和授权两个操作，在现在的主流服务架构中，很有可能是需要相互独立引入的。

### 鉴权

### 授权

## pth-base
