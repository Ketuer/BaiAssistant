package crack.cduestc.byjh.exception;

/**
 * 百叶计划助手异常
 *
 * @author Ketuer
 * @since 1.0
 */
public class ByjhAssistantException extends Exception{
    public ByjhAssistantException(int statusCode, String msg){
        super("错误码: "+statusCode+" > "+msg);
    }
}
