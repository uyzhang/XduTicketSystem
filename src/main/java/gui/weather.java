package gui;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

public class weather {
    String result;
    public weather(String local) {
        String host = "http://weatherq.market.alicloudapi.com";
        String path = "/clouds/query/weather/details";
        String method = "GET";
        String appcode = "443be23396e742fea97cbc47e8fc226d";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("cityNameOrId", local);


        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            System.out.println(response.toString());
            //获取response的body
            result = EntityUtils.toString(response.getEntity());
            System.out.println(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String get()
    {
        int index1 = result.indexOf("\"weather\":");
        int index2 = result.indexOf(",\"weatherGroup\"");
        return result.substring(index1+11,index2-1);
    }
}
