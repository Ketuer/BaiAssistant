package crack.cduestc.byjh.entity.account;

import com.alibaba.fastjson.JSONObject;
import crack.cduestc.byjh.entity.activity.SignedActivity;
import crack.cduestc.byjh.exception.ActivityOprException;
import crack.cduestc.byjh.exception.LoginException;
import crack.cduestc.byjh.net.Result;
import crack.cduestc.byjh.net.WebManager;

import java.util.List;

/**
 * 百叶计划账号实体类，包含LoginAccount和OperableAccount接口的
 * 所有功能，你完全可以只操作此实体类来完成对一个账户的任意操作。
 *
 * @author Ketuer
 * @since 1.0
 */
public class BaiAccount implements LoginAccount, OperableAccount {
    /* 账号 */
    private final String id;
    /* 密码 */
    private String password;

    /* token，登陆成功后才能拿到，有token才能执行登陆以外的全部操作 */
    private String token;
    /* 用户名，登陆成功后才能拿到 */
    private String userName;
    /* 用户头像链接，登陆成功后才能拿到 */
    private String headImgUrl;
    /* 所属部门，登陆成功后才能拿到 */
    private String identity;
    /* 电话号码，登陆成功后才能拿到 */
    private String phone;
    /* 百叶计划内部ID号，登陆成功后才能拿到 */
    private String userId;
    /* 专业，登陆成功后才能拿到 */
    private String major;
    /* 班级，登陆成功后才能拿到 */
    private String clazz;
    /* 性别，登陆成功后才能拿到 */
    private String sex;

    /**
     * 内部维护私有构造，不允许直接构造，请通过createAccount静态方法创建账户
     *
     * @param id       账号
     * @param password 密码
     */
    private BaiAccount(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public static BaiAccount createAccount(String id, String password) {
        return new BaiAccount(id, password);
    }

    @Override
    public void login() throws LoginException {
        Result result = WebManager.login(this.id, this.password);
        if (result.getStatusCode() == 10000) {
            JSONObject data = result.getOriginData().getJSONObject("data");
            this.token = data.getString("access_token");
            JSONObject client = data.getJSONObject("client");
            this.userName = client.getString("username");
            this.headImgUrl = client.getString("head_img");
            this.identity = client.getString("identity");
            this.phone = client.getString("phone");
            this.userId = client.getString("user_id");
            this.major = client.getString("major");
            this.clazz = client.getString("class");
            this.sex = client.getString("sex");
        } else {
            throw new LoginException(result.getStatusCode(), result.getMessage());
        }
    }

    @Override
    public void signActivity(int activityId) throws ActivityOprException {
        if (token == null) throw new ActivityOprException(10009, "此用户未登录！");
        Result result = WebManager.signActivity(token, activityId);
        if (result.getStatusCode() != 10000) {
            throw new ActivityOprException(result.getStatusCode(), result.getMessage());
        }
    }

    @Override
    public void cancelActivity(int activityId) throws ActivityOprException {
        if (token == null) throw new ActivityOprException(10009, "此用户未登录！");
        Result result = WebManager.cancelActivity(token, activityId);
        if (result.getStatusCode() != 10000) {
            throw new ActivityOprException(result.getStatusCode(), result.getMessage());
        }
    }

    @Override
    public List<SignedActivity> getActivities() throws ActivityOprException {
        if (token == null) throw new ActivityOprException(10009, "此用户未登录！");
        return WebManager.getAccountActivities(token);
    }

    /**
     * 获取账号
     *
     * @return 账号
     */
    public String getId() {
        return id;
    }

    /**
     * 获取密码
     *
     * @return 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 获取用户名称
     *
     * @return 用户名称
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 获取用户头像链接
     *
     * @return 用户头像链接
     */
    public String getHeadImgUrl() {
        return headImgUrl;
    }

    /**
     * 获取部门
     *
     * @return 部门
     */
    public String getIdentity() {
        return identity;
    }

    /**
     * 获取电话号码
     *
     * @return 电话号码
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 获取内部id
     *
     * @return 百叶计划内部id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 获取Token
     *
     * @return Token
     */
    public String getToken() {
        return token;
    }

    /**
     * 获取班级
     *
     * @return 班级
     */
    public String getClazz() {
        return clazz;
    }

    /**
     * 获取专业
     *
     * @return 专业
     */
    public String getMajor() {
        return major;
    }

    /**
     * 获取性别
     *
     * @return 性别
     */
    public String getSex() {
        return sex;
    }

    @Override
    public String toString() {
        return "BaiAccount{" +
                "id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                ", userName='" + userName + '\'' +
                ", headImgUrl='" + headImgUrl + '\'' +
                ", identity='" + identity + '\'' +
                ", phone='" + phone + '\'' +
                ", userId='" + userId + '\'' +
                ", major='" + major + '\'' +
                ", clazz='" + clazz + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}