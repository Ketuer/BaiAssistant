package crack.cduestc.byjh.net;

import com.alibaba.fastjson.JSONObject;
import crack.cduestc.byjh.entity.activity.Activity;
import crack.cduestc.byjh.entity.activity.SignedActivity;
import crack.cduestc.byjh.net.enums.StatusType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 基于java.net框架编写，包含全部类型操作的网络访问模块
 *
 * @author Ketuer
 * @since 1.0
 */
public class WebManager {
    /**
     * 为用户执行登陆操作
     * @param id 百叶计划用户账号
     * @param password 百叶计划用户密码
     * @return 登陆操作结果
     */
    public static Result login(String id, String password){
        Result result = getHttpResult("http://byjh.cduestc.cn/Api/Token/login", "student_id="+id+"&password="+password);
        if(result.statusCode != 10000) return result;
        JSONObject object = result.getOriginData().getJSONObject("data");
        String token = object.getString("access_token");
        JSONObject client = object.getJSONObject("client");
        Result result1 = getHttpResult("http://byjh.cduestc.cn/Api/During/information", "token="+token+"&is_cj=10001");
        JSONObject data = result1.getOriginData().getJSONObject("data");
        client.put("major", data.getString("major"));
        client.put("sex", data.getString("sex"));
        client.put("class", data.getString("class"));
        return result;
    }

    /**
     * 报名一个活动
     * @param token token
     * @param activityId 活动ID
     * @return 报名结果
     */
    public static Result signActivity(String token, int activityId){
        return getHttpResult("http://byjh.cduestc.cn/Api/During/sign", "token="+token+"&url="+activityId);
    }

    /**
     * 取消一个活动
     * @param token token
     * @param activityId 活动ID
     * @return 活动取消结果
     */
    public static Result cancelActivity(String token, int activityId){
        return getHttpResult("http://byjh.cduestc.cn/Api/During/cancel", "token="+token+"&url="+activityId);
    }

    /**
     * 直接获取所有的活动列表
     * @param type 类型
     * @return 活动列表
     */
    public static List<Activity> getAllActivities(StatusType type){
        return getHttpResultAsActivityList("http://byjh.cduestc.cn/Api/List/index", "status="+type.i);
    }

    /**
     * 获取账户已参加的所有的活动列表
     * @param token 用户token
     * @return 活动列表
     */
    public static List<SignedActivity> getAccountActivities(String token){
        return getHttpResultAsActivityList("http://byjh.cduestc.cn/Api/During/myDuring", "token="+token)
                .stream()
                .map(activity -> (SignedActivity) activity)
                .collect(Collectors.toList());
    }

    private static Result getHttpResult(String url, String params){
        try {
            String data = createHttpPost(url, params);
            if(!data.isEmpty()) {
                JSONObject object = JSONObject.parseObject(data);
                return new Result(object.getInteger("status"), object.getString("type"), object);
            }else {
                return new Result(10004, "返回的内容为空串！", null);
            }
        } catch (IOException e) {
            return new Result(10003, e.getMessage(), null);
        }
    }

    private static List<Activity> getHttpResultAsActivityList(String url, String params){
        List<Activity> activities = new ArrayList<>();
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String data = createHttpPost(url, params);
            if(!data.isEmpty()) {
                JSONObject object = JSONObject.parseObject(data);
                object.getJSONArray("data").forEach(a -> {
                    JSONObject activity = JSONObject.parseObject(a.toString());
                    int id = activity.getInteger("id");
                    String name = activity.getString("titleing");
                    String type = WebMapper.getType(activity.getString("project"));
                    String imageUrl = "http://byjh.cduestc.cn"+activity.getString("cover").replace("#", "");
                    Date date = null;
                    try {
                        date = format.parse(activity.getString("during_start"));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String place = activity.getString("place");
                    String hospital = activity.getString("hospital");
                    String status = WebMapper.getStatus(activity.getInteger("status"));

                    if(activity.containsKey("check_code")){
                        String checkCode = activity.getString("check_code");
                        String qrCode = "http://byjh.cduestc.cn/"+activity.getString("qr_code");
                        String stat = WebMapper.getStat(activity.getInteger("stat"));
                        activities.add(new SignedActivity(id, name, imageUrl, hospital, date, type, place, status, stat, checkCode, qrCode));
                    }else {
                        Integer reg = activity.getInteger("reg_num");
                        Integer max = activity.getInteger("minimum");
                        activities.add(new Activity(id, name, imageUrl, hospital, date, type, place, max, reg, status));
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return activities;
    }

    private static String createHttpPost(String url, String params) throws IOException {
        BufferedReader in = null;
        OutputStream out = null;
        StringBuilder result = new StringBuilder();
        try {
            URL urlX = new URL(url);
            URLConnection connection = urlX.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 5.1; rv:23.0) Gecko/20100101 Firefox/23.0");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            out = connection.getOutputStream();
            out.write(params.getBytes());
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result.toString();
    }
}
