package crack.cduestc.byjh.net;

import crack.cduestc.byjh.net.enums.ActivityBelong;
import crack.cduestc.byjh.net.enums.ActivityLevel;
import crack.cduestc.byjh.net.enums.ActivityType;

import java.util.ArrayList;
import java.util.List;

/**
 * 高级活动筛选器
 * 能够根据不同院系、所属级别、主办院系进行搜索
 *
 * @author Ketuer
 * @since 1.1
 */
public class ActivitySearcher {
    private ActivityType[] types;
    private ActivityLevel[] levels;
    private ActivityBelong[] belongs;

    private ActivitySearcher(){ }

    public static ActivitySearcher create(){
        return new ActivitySearcher();
    }

    /**
     * 设定搜索的活动类型
     * @param types 类型
     * @return 高级筛选器
     */
    public ActivitySearcher addActivityTypes(ActivityType... types){
        this.types = types;
        return this;
    }

    /**
     * 设定搜索的活动等级
     * @param levels 等级
     * @return 高级筛选器
     */
    public ActivitySearcher addActivityLevel(ActivityLevel... levels){
        this.levels = levels;
        return this;
    }

    /**
     * 设定搜索的活动所属院系
     * @param belongs 所属院系
     * @return 高级筛选器
     */
    public ActivitySearcher addActivityBelong(ActivityBelong... belongs){
        this.belongs = belongs;
        return this;
    }

    String genParam(){
        StringBuilder builder = new StringBuilder("project=");
        List<String> list = new ArrayList<>();
        for(ActivityType type : types) list.add(type.i);
        builder.append(list);

        builder.append("&level=");
        List<Integer> list1 = new ArrayList<>();
        for(ActivityLevel level : levels) list1.add(level.i);
        builder.append(list1);

        builder.append("&faculty=");
        List<Integer> list2 = new ArrayList<>();
        for(ActivityBelong belong : belongs) list2.add(belong.i);
        builder.append(list2);

        return builder.toString();
    }
}
