package crack.cduestc.byjh.entity.score;

/**
 * 用户分数信息
 *
 * @author Ketuer
 * @since 1.1
 */
public class ScoreData {
    /* 博学 */
    int bx;
    /* 笃行 */
    int dx;
    /* 尽美 */
    int jm;
    /* 明德 */
    int md;
    /* 加分记录数量 */
    int number;

    public ScoreData(int bx, int dx, int jm, int md, int number) {
        this.bx = bx;
        this.dx = dx;
        this.jm = jm;
        this.md = md;
        this.number = number;
    }

    public int getBx() {
        return bx;
    }

    public int getDx() {
        return dx;
    }

    public int getJm() {
        return jm;
    }

    public int getMd() {
        return md;
    }

    public int getNumber() {
        return number;
    }

    /**
     * 获取总成绩
     * @return 总成绩
     */
    public int getAllScore(){
        return bx+dx+md+jm;
    }

    @Override
    public String toString() {
        return "ScoreData{" +
                "bx=" + bx +
                ", dx=" + dx +
                ", jm=" + jm +
                ", md=" + md +
                ", number=" + number +
                '}';
    }
}
