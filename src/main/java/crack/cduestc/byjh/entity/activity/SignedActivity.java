package crack.cduestc.byjh.entity.activity;

import java.util.Date;

/**
 * 用户活动（已报名活动）
 * 此类型看不到参与人数等信息
 *
 * @author Ketuer
 * @since 1.0
 */
public class SignedActivity extends Activity{

    String qr_code;
    String check_code;
    String stat;
    public SignedActivity(int id, String name, String coverUrl, String hospital, Date start,
                          String type, String place, String status, String stat, String qr_code, String check_code) {
        super(id, name, coverUrl, hospital, start, type, place, 0, 0, status);
        this.check_code = check_code;
        this.qr_code = qr_code;
        this.stat = stat;
    }

    /**
     * 获取此活动的二维码链接
     * @return 二维码链接
     */
    public String getQrCode() {
        return qr_code;
    }

    /**
     * 获取此活动的一维码（数字序列）
     * @return 一维码
     */
    public String getCheckCode() {
        return check_code;
    }

    /**
     * 获取此活动的参加状态
     * @return 状态
     */
    public String getStat() {
        return stat;
    }

    @Override
    public String toString() {
        return "SignedActivity{" +
                "qr_code='" + qr_code + '\'' +
                ", check_code='" + check_code + '\'' +
                ", stat='" + stat + '\'' +
                super.toString()+'}';
    }
}
