package crack.cduestc.byjh.net.enums;

/**
 * 活动所属院系
 */
public enum ActivityBelong {
    /* 所有 */
    ALL(0),
    /* 校团委 */
    XTW(1),
    /* 计算机 */
    JSJ(2),
    /* 电子工程 */
    DZ(3),
    /* 通信与信息工程 */
    TX(4),
    /* 微电子技术 */
    WD(5),
    /* 云计算科学与技术 */
    YJS(6),
    /* 经济与管理工程 */
    JG(7),
    /* 财经系 */
    CJ(8),
    /* 文理系 */
    WL(9),
    /* 航空分院 */
    HK(10),
    /* 行知学院 */
    XZ(11),
    /* 艺术与科技 */
    YK(12);

    public int i;
    ActivityBelong(int i){
        this.i = i;
    }
}
