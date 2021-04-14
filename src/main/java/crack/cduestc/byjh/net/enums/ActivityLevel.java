package crack.cduestc.byjh.net.enums;

public enum ActivityLevel {
    /* 院级 */
    COLLAGE(2),
    /* 系级 */
    DEPARTMENT(1),
    /* 所有 */
    ALL(0);
    public int i;
    ActivityLevel(int i){
        this.i = i;
    }
}
