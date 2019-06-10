package com.pet.transport.video.convert.domain;

import java.util.ArrayList;
import java.util.List;

public class Convert {




    public void convertProtocol(String ffmpeg,String rtsp, String rtmp,String s) {
        //ffmpeg -i "rtsp://admin:12345@192.168.3.218/h264/ch1/main/av_stream"
        // 		-f flv -r 25 -s 640x480 -an "rtmp://192.168.3.138:1935/myapp/test2"
        List<String> commend = new ArrayList<String>();
        commend.add(ffmpeg);
        commend.add("-i");
        commend.add("\"" + rtsp + "\"");
        commend.add("-f");
        commend.add("flv");
        commend.add("-r");
        commend.add("25");
        commend.add("-s");
        commend.add(s);
        commend.add("-an");
        commend.add(rtmp);
        try {
            ProcessBuilder builder = new ProcessBuilder(); //创建系统进程
            builder.command(commend);
            builder.start();//启动进程
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
