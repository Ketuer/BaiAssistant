package crack.cduestc.byjh.entity.account;
import crack.cduestc.byjh.exception.LoginException;

/**
 * 登录账户接口，包含登陆相关的操作
 *
 * @author Ketuer
 * @since 1.0
 */
public interface LoginAccount {
    /**
     * 对账户进行登录操作
     *
     * @throws LoginException 登陆过程可能出现任何异常
     */
    void login() throws LoginException;
}
