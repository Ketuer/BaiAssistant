package crack.cduestc.byjh.entity.activity;

import java.util.Date;

/**
 * 活动实体类
 *
 * @author Ketuer
 * @since 1.0
 */
public class Activity {
    /* 活动编号 */
    private final int id;
    /* 活动名称 */
    private final String name;
    /* 活动图标 */
    private final String coverUrl;
    /* 还不清楚到底是个什么，暂时保留 */
    private final String hospital;
    /* 开始时间 */
    private final Date start;
    /* 类型 */
    private final String type;
    /* 地点 */
    private final String place;
    /* 最大参加人数 */
    private final int max;
    /* 当前参加人数 */
    private final int reg;
    /* 活动状态 */
    private final String status;

    public Activity(int id, String name, String coverUrl, String hospital, Date start, String type, String place, int max, int reg, String status) {
        this.id = id;
        this.name = name;
        this.coverUrl = coverUrl;
        this.hospital = hospital;
        this.start = start;
        this.type = type;
        this.place = place;
        this.max = max;
        this.reg = reg;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public String getHospital() {
        return hospital;
    }

    public Date getStart() {
        return start;
    }

    public String getType() {
        return type;
    }

    public String getPlace() {
        return place;
    }

    public int getMax() {
        return max;
    }

    public int getReg() {
        return reg;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coverUrl='" + coverUrl + '\'' +
                ", hospital='" + hospital + '\'' +
                ", start=" + start +
                ", type='" + type + '\'' +
                ", place='" + place + '\'' +
                ", max=" + max +
                ", reg=" + reg +
                ", status='" + status + '\'' +
                '}';
    }
}
