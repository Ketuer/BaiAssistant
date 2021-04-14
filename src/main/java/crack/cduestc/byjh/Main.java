package crack.cduestc.byjh;

import crack.cduestc.byjh.entity.account.BaiAccount;
import crack.cduestc.byjh.exception.ActivityOprException;
import crack.cduestc.byjh.exception.ByjhAssistantException;
import crack.cduestc.byjh.net.WebManager;
import crack.cduestc.byjh.net.WebMapper;
import crack.cduestc.byjh.net.enums.StatusType;

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

