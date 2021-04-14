package crack.cduestc.byjh.exception;

/**
 * 登陆相关的异常
 *
 * @author Ketuer
 * @since 1.0
 */
public class LoginException extends ByjhAssistantException{

    public LoginException(int statusCode, String msg) {
        super(statusCode, msg);
    }
}
