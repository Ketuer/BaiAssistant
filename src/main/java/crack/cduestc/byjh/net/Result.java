package crack.cduestc.byjh.net;

import com.alibaba.fastjson.JSONObject;

/**
 * 网络操作的返回结果实体
 *
 * @author Ketuer
 * @since 1.0
 */
public class Result {
    int statusCode;
    String message;
    JSONObject origin;

    Result(int statusCode, String message, JSONObject origin) {
        this.statusCode = statusCode;
        this.message = message;
        this.origin = origin;
    }

    public String getMessage() {
        return message;
    }

    /**
     * 获取状态码
     * <ul>
     *  <li>10000 通过</li>
     *  <li>10002 未通过</li>
     *  <li>10003 服务器返回了错误的内容</li>
     *  <li>10004 网络请求出现异常</li>
     * <ul/>
     * @return 状态码
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * 获取原始Http数据，注意，它在某些 (出现异常、网络错误) 情况下可能是null
     * @return 原始JSON数据
     */
    public JSONObject getOriginData(){
        return origin;
    }

    @Override
    public String toString() {
        return "Result{" +
                "statusCode=" + statusCode +
                ", message='" + message + '\'' +
                ", origin=" + origin +
                '}';
    }
}
