package crack.cduestc.byjh.entity.score;

/**
 * 加分记录
 *
 * @author Ketuer
 * @since 1.1
 */
public class ScoreAdd {
    /* 活动名称 */
    String name;
    /* 活动id */
    int id;
    /* 加分数量 */
    int add;
    /* 加分类型 */
    String type;
    /* 加分理由 */
    String reason;

    public ScoreAdd(String name, int id, int add, String type, String reason) {
        this.name = name.replace("\n", "");
        this.id = id;
        this.add = add;
        this.type = type;
        this.reason = reason;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getAdd() {
        return add;
    }

    public String getType() {
        return type;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public String toString() {
        return "ScoreAdd{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", add=" + add +
                ", type='" + type + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}
