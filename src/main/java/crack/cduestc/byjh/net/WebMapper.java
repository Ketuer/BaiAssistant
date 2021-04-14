package crack.cduestc.byjh.net;

/**
 * id对照表
 *
 * @author Ketuer
 * @since 1.0
 */
public class WebMapper {
    /**
     * 状态码转活动类型
     * @param type 状态码
     * @return 活动类型
     */
    public static String getType(String type){
        switch (type){
            case "1":
                return "博学";
            case "2":
                return "笃行";
            case "3":
                return "尽美";
            case "4":
                return "明德";
            default:
                return "未知";
        }
    }

    /**
     * 状态码转中文
     * @param status 状态码
     * @return 意义
     */
    public static String getStatus(int status){
        switch (status){
            case 4:
                return "报名中";
            case 5:
                return "报名结束";
            case 8:
                return "已结束";
            default:
                return "未知";
        }
    }

    /**
     * 状态码转中文
     * @param stat 状态码
     * @return 意义
     */
    public static String getStat(int stat){
        switch (stat){
            case 4:
                return "已参加";
            case 3:
                return "已报名";
            case 2:
                return "缺席";
            default:
                return "未知";
        }
    }
}
