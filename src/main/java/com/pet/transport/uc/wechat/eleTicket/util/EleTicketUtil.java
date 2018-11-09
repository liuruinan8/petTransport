package com.pet.transport.uc.wechat.eleTicket.util;

import com.pet.transport.uc.wechat.common.trust.MyX509TrustManager;
import com.pet.transport.uc.wechat.core.util.WeChatUtil;
import com.pet.transport.uc.wechat.eleTicket.po.TokenData;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLConnection;

public class EleTicketUtil {
    private static Logger log = LoggerFactory.getLogger(EleTicketUtil.class);
    private static WeChatUtil weChatUtil = WeChatUtil.getInstance();
    public static String scan ="************";

    //获取access_token
    public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    /*
     * 获取返回参数
     * access_token_data_url
     * 固定 Scan_test不固定
     * https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1496554912_vfWU0
     */
    public final static String Scan_test = "https://mp.weixin.qq.com/intp/invoice/usertitlewxa?action=list_auth&invoice_key=Scan_test";
    public final static String access_token_data_url = "https://api.weixin.qq.com/card/invoice/scantitle?access_token=AccessToken";

    /*public static void main(String[] args) {
        String Access_Token=weChatUtil.getAccessToken();
        System.out.println("Access_Token-->"+Access_Token);

        String url = access_token_data_url.replace("AccessToken", Access_Token);
        System.out.println("url-->"+url);

        String scan_test =Scan_test.replace("Scan_test", scan);
        System.out.println("scan_test-->"+scan_test);
        Bean b = new Bean();
        b.scan_test = scan_test;
        String body = new Gson().toJson(b);
        String result = post(url, body);
        System.out.println("result-->"+result);

        TokenData tokendata = Access_Token(result);
        System.out.println("tokendata-->"+tokendata);

        int title_type = tokendata.getTitle_type();
        System.out.println("title_type-->"+title_type);
        System.out.println("title_type-->"+tokendata.getTax_no());
        System.out.println("title_type-->"+tokendata.getBank_no());
        System.out.println("title_type-->"+tokendata.getBank_type());

    }*/

    /**
     * 发起https请求并获取结果
     *
     * @param requestUrl
     *            请求地址
     * @param requestMethod
     *            请求方式（GET、POST）
     * @param outputStr
     *            提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);
            if ("GET".equalsIgnoreCase(requestMethod))
                httpUrlConn.connect();

            // 当有数据需要提交时
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (ConnectException ce) {
            log.error("Weixin server connection timed out.");
        } catch (Exception e) {
            log.error("https request error:{}", e);
        }
        return jsonObject;
    }

    /*
     *发起https请求并获取结果
     *
     * @param url
     *            请求地址
     * @param requestMethod
     *            请求方式（POST）
     * @param body
     *            请求参数
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static String post(String url, String body) {
        String result = "";
        try {
            OutputStreamWriter out = null;
            BufferedReader in = null;
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();

            // 发送请求,将网页以流的形式读回来
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(20000);

            // 设置字节流
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            out.write(body);
            out.flush();

            //System.out.println(conn.getInputStream());
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line = "";
            boolean firstLine = true;
            while ((line = in.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                } else {
                    result += System.getProperty("line.separator");
                }
                result += line;
            }
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /*
     * 工具类
     * 就一个属性就写在这了
     */
    public static class Bean {
        public String scan_test;
    }

    public static String getPicBASE64(String picPath) {
        String content = "";
        try {
            FileInputStream fileForInput = new FileInputStream(picPath);
            byte[] bytes = new byte[fileForInput.available()];
            if (bytes.length < 102400) {
                System.out.print(bytes.length);
            }
            fileForInput.read(bytes);
            content = new sun.misc.BASE64Encoder().encode(bytes); // 具体的编码方法
            fileForInput.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    /*
     * 解析返回参数
     */
    private static TokenData Access_Token(String result) {
        TokenData tokendata = null;
        //result = "{'title':'单位抬头','tax_no':'aaaa123456','addr':'12345688','errcode':0,'bank_type':'银行类型','phone':'123999','title_type':0,'errmsg':'ok','bank_no':'999685',}";
        JSONObject jsonObject = JSONObject.fromObject(result);
        System.out.println("jsonObject-->"+jsonObject);
        // 如果请求成功
        if (null != jsonObject) {
            try {
                tokendata = new TokenData();
                tokendata.setTitle_type(jsonObject.getInt("title_type"));
                tokendata.setTitle(jsonObject.getString("title"));
                tokendata.setPhone(jsonObject.getString("phone"));
                tokendata.setTax_no(jsonObject.getString("tax_no"));
                tokendata.setAddr(jsonObject.getString("addr"));
                tokendata.setBank_type(jsonObject.getString("bank_type"));
                tokendata.setBank_no(jsonObject.getString("bank_no"));
            } catch (JSONException e) {
                tokendata = null;
                // 获取token失败
                // log.error("获取token失败 errcode:{} errmsg:{}",
                jsonObject.getInt("errcode");
                jsonObject.getString("errmsg");
                System.out.println("null");
            }
        }
        return tokendata;
    }
}
