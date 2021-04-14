package crack.cduestc.byjh.net.enums;

public enum StatusType{
    /* 所有活动 */
    ALL(0),
    /* 正在报名的活动 */
    SIGNING(4),
    /* 报名结束的活动 */
    READY(5),
    /* 活动正在进行 */
    ACTIVE(7),
    /* 已结束的活动 */
    END(8);

    public int i;
    StatusType(int i){
        this.i = i;
    }
}
