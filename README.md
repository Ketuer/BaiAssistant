# BaiAssistant 百叶计划助手
##简介👑
* 电子科技大学成都学院百叶计划助手，支持通过接口直接操作百叶计划账号
* 本项目遵循GPLv2协议，仅供学习交流使用，严禁用于商业用途
* 如果喜欢本项目，请点个star⭐️

##版本历史👒
* ###1.0 - Release
    * 支持基本的用户登录操作，并获取用户信息
    * 直接操作用户报名活动、取消活动
    * 活动列表查询，获取活动信息

##快速开始🤟
* ###获取所有活动并打印到控制台👻
```java
public class Main {
    public static void main(String[] args) throws ByjhAssistantException {
        //获取所有活动列表并将详细信息打印到控制台
        //StatusType.ALL表示获取全部类型的活动，你也可以使用StatusType枚举类的其他类型
        WebManager.getAllActivities(StatusType.ALL).forEach(System.out::println);
    }
}
```

* ###登陆一个用户
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

* ###为当前用户进行活动报名、取消活动、查看活动列表操作
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

##应用型操作🎒
* ###获取指定活动并报名
```java
public class Main {
    public static void main(String[] args) throws ByjhAssistantException {
        //先登录账户
        BaiAccount account = BaiAccount.createAccount("1950870101", "123456");
        account.login();
        //获取所有活动并筛选名称带有 "计算机" 的活动
        //在抢活动时同理，建议contains模糊匹配，匹配到时直接报名，反正可以取消
        WebManager.getAllActivities(StatusType.ALL)
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