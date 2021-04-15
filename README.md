# BaiAssistant 百叶计划助手
不会吧不会吧，不会还有人在花钱请别人代抢活动，还有人在卖活动名额吧，为什么要给那些人赚这个钱呢，自己多买包烟不香吗。
## 简介 👑
* 此项目基于Java开发，支持跨平台使用。
* 电子科技大学成都学院百叶计划助手，支持通过接口直接操作百叶计划账号
* 本项目遵循GPLv2协议，仅供学习交流使用，严禁用于商业用途
* 如果喜欢本项目，请点个star⭐️
* <s>注意本接口需要全程使用校园网或寝室宽带</s> (1.1版本开始已默认外网访问)

## 版本历史 👒
* ### 1.1 - Release
    * 更加强大的活动查询系统，支持模糊匹配、筛选查找、高级筛选查找
    * 新增用户登出和重置密码操作
    * 新增分数查询操作
    * 新增加分记录查询操作
    * 现在默认使用外网接口（可以手动切回内网，更稳定）
* ### 1.0 - Release
    * 支持基本的用户登录操作，并获取用户信息
    * 直接操作用户报名活动、取消活动
    * 活动列表查询，获取活动信息

## 你可以直接使用已经编写好的抢活动程序 😘
* 需要先下载安装 [Java 8](https://www.oracle.com/java/technologies/javase-jre8-downloads.html) 或以上环境
* 下载最新的 [default.zip](https://github.com/Ketuer/BaiAssistant/releases) 程序包
* Windows用户：
   * 解压后直接双击windows.bat即可开启
* Mac用户：
   * 请在桌面解压，然后进入文件夹双击macos.command
* Linux用户：
   * 解压后运行linux.sh即可

# 如果你是Java开发者
## 已知问题
* 在外网环境下无法进行登出操作（迷）
* 某些即使已经结束的活动（但是未报满），也能进行报名，一开始显示为缺席，过一段时间直接变成加分（？？？？？）

## 添加依赖 👻
#### 你可以直接导入jar依赖或是添加maven依赖：
* 直接下载最新的 [BaiAssistant-X.X-Release.jar](https://github.com/Ketuer/BaiAssistant/releases/) 并导入jar文件作为依赖。
* 也可以添加Maven项目依赖：
```html
<repositories>
    <repository>
        <id>crack-mvn-repo</id>
        <url>https://raw.githubusercontent.com/Ketuer/BaiAssistant/main/repo</url>
    </repository>
</repositories>

<dependencies> 
    <dependency>
        <groupId>crack.cduestc</groupId>
        <artifactId>byjh</artifactId>
        <version>1.1-Release</version>
    </dependency>
</dependencies>
```

## 快速开始 🤟
* ### 获取所有活动并打印到控制台
```java
public class Main {
    public static void main(String[] args) throws ByjhAssistantException {
        //获取所有活动列表并将详细信息打印到控制台
        //StatusType.ALL表示获取全部类型的活动，你也可以使用StatusType枚举类的其他类型
        WebManager.getAllActivities(StatusType.ALL).forEach(System.out::println);
    }
}
```

* ### 登陆一个用户
```java
public class Main {
    public static void main(String[] args) throws ByjhAssistantException {
        //创建账户请使用BaiAccount.createAccount方法，填写账号和密码即可
        BaiAccount account = BaiAccount.createAccount("1950610101", "123456");
        //登陆账号，登陆成功后就可以获取到所有的用户信息了
        account.login();
        //查看登陆成功后的用户信息
        System.out.println(account);
    }
}
```

* ### 为当前用户进行活动报名、取消活动、查看活动列表操作
```java
public class Main {
    public static void main(String[] args) throws ByjhAssistantException {
        //同样需要登陆账号才能获取用户信息
        BaiAccount account = BaiAccount.createAccount("1950610101", "123456");
        account.login();
        //立即报名一个活动(需要填入活动的id，活动id可以通过活动列表获取)
        account.signActivity("1234");
        //取消报名一个活动
        account.cancelActivity("1234");
        //列出当前已经参加的活动并输出到控制台
        account.getActivities().forEach(System.out::println);
    }
}
```

* ### 获取指定活动并报名
```java
public class Main {
    public static void main(String[] args) throws ByjhAssistantException {
        //先登录账户
        BaiAccount account = BaiAccount.createAccount("1950870101", "123456");
        account.login();
        //获取所有活动并筛选名称带有 "计算机" 的活动，类型为SIGNING保证是正在报名的活动
        //在抢活动时同理，建议contains模糊匹配，匹配到时直接报名，反正可以取消
        WebManager.getAllActivities(StatusType.SIGNING)
                .stream()
                .filter(activity -> activity.getName().contains("计算机"))
                .forEach(activity -> {
                    try {
                        account.signActivity(activity.getId());
                    } catch (ActivityOprException e) {
                        e.printStackTrace();
                    }
                });
    }
}
```
