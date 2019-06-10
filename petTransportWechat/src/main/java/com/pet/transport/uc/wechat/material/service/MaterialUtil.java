package com.pet.transport.uc.wechat.material.service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author zimok
 * @Title: MaterialService
 * @ProjectName petTransport
 * @Description: TODO
 * @date 2019/2/1720:56
 */
public class MaterialUtil {

    /**
     * 上传永久素材
     * @param	file
     * @param	type
     * @param	title type为video时需要,其他类型设null
     * @param	introduction type为video时需要,其他类型设null
     * @return	{"media_id":MEDIA_ID,"url":URL}
     */
    public static String uploadPermanentMaterial(String access_token,File file, String type, String title, String introduction) {

        String url = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token="
                + access_token + "&type=" + type;

        String result = null;

        try {
            URL uploadURL = new URL(url);

            HttpURLConnection conn = (HttpURLConnection) uploadURL.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(30000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Cache-Control", "no-cache");
            String boundary = "-----------------------------" + System.currentTimeMillis();
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            OutputStream output = conn.getOutputStream();
            output.write(("--" + boundary + "\r\n").getBytes());
            output.write(String.format("Content-Disposition: form-data; name=\"media\"; filename=\"%s\"\r\n", file.getName()).getBytes());
            output.write("Content-Type: video/mp4 \r\n\r\n".getBytes());

            byte[] data = new byte[1024];
            int len = 0;
            FileInputStream input = new FileInputStream(file);
            while ((len = input.read(data)) > -1) {
                output.write(data, 0, len);
            }

            /*对类型为video的素材进行特殊处理*/
            if ("video".equals(type)) {
                output.write(("--" + boundary + "\r\n").getBytes());
                output.write("Content-Disposition: form-data; name=\"description\";\r\n\r\n".getBytes());
                output.write(String.format("{\"title\":\"%s\", \"introduction\":\"%s\"}", title, introduction).getBytes());
            }

            output.write(("\r\n--" + boundary + "--\r\n\r\n").getBytes());
            output.flush();
            output.close();
            input.close();

            InputStream resp = conn.getInputStream();

            StringBuffer sb = new StringBuffer();

            while ((len = resp.read(data)) > -1){
                sb.append(new String(data, 0, len, "utf-8"));
            }

            resp.close();
            result = sb.toString();
        } catch (IOException e) {
            //....
        }

        return result;
    }

}
