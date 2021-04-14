package crack.cduestc.byjh.net;

import com.alibaba.fastjson.JSONObject;
import crack.cduestc.byjh.entity.activity.Activity;
import crack.cduestc.byjh.entity.activity.SignedActivity;
import crack.cduestc.byjh.net.enums.ActivityType;
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
    private static String ip = "http://byjh.cduestc.cn:1356";

    /**
     * 切换是否为内网访问接口
     * @param use 是否使用内网访问
     * @since 1.1
     */
    public static void switchInnerIp(boolean use){
        if (use){
            ip = "http://byjh.cduestc.cn";
        }else {
            ip = "http://byjh.cduestc.cn:1356";
        }
    }


    /**
     * 为用户执行登陆操作
     * @param id 百叶计划用户账号
     * @param password 百叶计划用户密码
     * @return 登陆操作结果
     */
    public static Result login(String id, String password){
        Result result = getHttpResult("/Api/Token/login", "student_id="+id+"&password="+password);
        if(result.statusCode != 10000) return result;
        JSONObject object = result.getOriginData().getJSONObject("data");
        String token = object.getString("access_token");
        JSONObject client = object.getJSONObject("client");
        Result result1 = getHttpResult("/Api/During/information", "token="+token+"&is_cj=10001");
        JSONObject data = result1.getOriginData().getJSONObject("data");
        client.put("major", data.getString("major"));
        client.put("sex", data.getString("sex"));
        client.put("class", data.getString("class"));
        return result;
    }

    /**
     * 执行登出操作
     * @param token token
     * @return 登出结果
     * @since 1.1
     */
    public static Result logout(String token){
        return getHttpResult("/Api/Pull/index", "token="+token);
    }

    /**
     * 执行重置密码操作
     * @param token token
     * @param password 新密码
     * @return 重置结果
     * @since 1.1
     */
    public static Result resetPassword(String token, String password){
        return getHttpResult("/Api/During/edit_pass", "token=" +token+"&password="+password+"&&password1="+password);
    }

    /**
     * 报名一个活动
     * @param token token
     * @param activityId 活动ID
     * @return 报名结果
     */
    public static Result signActivity(String token, int activityId){
        return getHttpResult("/Api/During/sign", "token="+token+"&url="+activityId);
    }

    /**
     * 取消一个活动
     * @param token token
     * @param activityId 活动ID
     * @return 活动取消结果
     */
    public static Result cancelActivity(String token, int activityId){
        return getHttpResult("/Api/During/cancel", "token="+token+"&url="+activityId);
    }

    /**
     * 直接获取所有的活动列表
     * @param type 类型
     * @return 活动列表
     */
    public static List<Activity> getAllActivities(StatusType type){
        return getHttpResultAsActivityList("/Api/List/index", "status="+type.i);
    }

    /**
     * 直接获取某种类型的活动列表
     * @param type 类型
     * @param type2 活动类型
     * @return 活动列表
     * @since 1.1
     */
    public static List<Activity> getTypeActivities(StatusType type, ActivityType type2){
        return getHttpResultAsActivityList("/Api/List/project", "status="+type.i+"&project="+type2.i);
    }

    /**
     * 模糊名称匹配活动，获取列表
     * @param name 名称
     * @return 活动列表
     * @since 1.1
     */
    public static List<Activity> getSearchActivities(String name){
        return getHttpResultAsActivityList("/Api/During/search", "search="+name);
    }

    /**
     * 使用高级搜索器进行活动搜索
     * @param searcher 搜索器
     * @return 活动列表
     * @since 1.1
     */
    public static List<Activity>  superSearchActivity(ActivitySearcher searcher){
        return getHttpResultAsActivityList("/Api/List/race", searcher.genParam());
    }

    /**
     * 获取账户已参加的所有的活动列表
     * @param token 用户token
     * @return 活动列表
     */
    public static List<SignedActivity> getAccountActivities(String token){
        return getHttpResultAsActivityList("/Api/During/myDuring", "token="+token)
                .stream()
                .map(activity -> (SignedActivity) activity)
                .collect(Collectors.toList());
    }

    /**
     * 获取用户的得分信息
     * @param token token
     * @return 得分信息
     * @since 1.1
     */
    public static Result getScore(String token){
        return getHttpResult("/Api/During/integral", "token="+token);
    }

    /**
     * 获取用户的加分记录信息
     * @param token token
     * @return 加分记录
     * @since 1.1
     */
    public static Result getScoreList(String token){
        Result result = getHttpResult("/Api/Index/integral", "token="+token);
        result.getOriginData().getJSONArray("data").forEach(o -> {
            JSONObject j = (JSONObject) o;
            String poj = WebMapper.getType(j.getString("project"));
            j.put("project", poj);
            j.put("titleing", j.getJSONObject("during").getString("titleing"));
            j.put("reason", "正常签到扫码加分");
        });
        Result result1 = getHttpResult("/Api/Apply/record", "token="+token+"&status=2");
        result1.getOriginData().getJSONArray("data").forEach(o -> {
            JSONObject j = (JSONObject) o;
            String poj = WebMapper.getType(j.getString("project"));
            j.put("project", poj);
            j.put("reason", j.getString("during"));
            j.put("titleing", j.getString("remarks"));
            result.getOriginData().getJSONArray("data").add(j);
        });
        return result;
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
                if(object.getString("data").equals("0")) return activities;
                object.getJSONArray("data").forEach(a -> {
                    JSONObject activity = JSONObject.parseObject(a.toString());
                    int id = activity.getInteger("id");
                    String name = activity.getString("titleing");
                    String type = WebMapper.getType(activity.getString("project"));
                    String imageUrl = ip+activity.getString("cover").replace("#", "");
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
                        String qrCode = ip+"/"+activity.getString("qr_code");
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
            URL urlX = new URL(ip+url);
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
