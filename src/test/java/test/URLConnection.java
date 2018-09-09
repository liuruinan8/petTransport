package test;

import com.pet.transport.common.util.DataConvertUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class URLConnection {


    public static void main(String[] args){
        getPatInfo(args);
    }
    //获取sichuannair数据
    public static void getPatInfo(String[] args){
        try {
            String[] str =new String[]{"犬","狗","猫"};

            for (int i =0 ;i<str.length; i++) {
                String petStr = GetResponse("http://sal.sichuanair.com/sal/pub/searchPetName.action?searchPetName="+str[i],"");
                List<Map> petLst = new ArrayList<Map>();
                petLst = (List<Map>) DataConvertUtil.convertJsonToBean(petStr,List.class);
                if(petLst !=null && petLst.size()>0){
                    for (Map pet:petLst) {
                        String name = (String) pet.get("NAME");
                        Double id = (Double) pet.get("OID");
                        System.out.print(""+name+"  "+id+"  ");
                        String petInfo = GetResponse("http://sal.sichuanair.com/sal/pub/PetClassification.action?petName="+id,"");
                        List<Map> petInfoLst = new ArrayList<Map>();
                        petInfoLst = (List<Map>) DataConvertUtil.convertJsonToBean(petInfo,List.class);
                        Map petInfoMap = petInfoLst.get(0);
                        //"CODE":"dog006","STATUS":0,"PARENTID":0,"TRIGHT":12,"CREATOR":21,"TLEFT":11,"NODETYPE":0,"MAPCOUNT":0,"DVERID":0,"CREATETIME":1520591276000
                        System.out.print(" "+petInfoMap.get("VERID")+"  ");
                        System.out.print(" "+petInfoMap.get("LONGNOTES")+"  ");
                        System.out.print(" "+petInfoMap.get("SOID")+"  ");
                        System.out.print(" "+petInfoMap.get("NAME")+"  ");
                        System.out.print(" "+petInfoMap.get("YN_REINFORCING_BARS")+"  ");
                        System.out.print(" "+petInfoMap.get("CODE")+"  ");
                        System.out.print(" "+petInfoMap.get("TRIGHT")+"  ");
                        System.out.print(" "+petInfoMap.get("TLEFT")+"  ");
                        System.out.println();
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String  GetResponse(String path,String Info) throws IOException
    {
        //1, 得到URL对象
        URL url = new URL(path);

        //2, 打开连接
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        //3, 设置提交类型
        conn.setRequestMethod("POST");

        //4, 设置允许写出数据,默认是不允许 false
        conn.setDoOutput(true);
        conn.setDoInput(true);//当前的连接可以从服务器读取内容, 默认是true

        //5, 获取向服务器写出数据的流
        OutputStream os = conn.getOutputStream();
        //参数是键值队  , 不以"?"开始
        os.write(Info.getBytes());
        //os.write("googleTokenKey=&username=admin&password=5df5c29ae86331e1b5b526ad90d767e4".getBytes());
        os.flush();
        //6, 获取响应的数据
        //得到服务器写回的响应数据
        BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
        String str = br.readLine();
        //System.out.println("响应内容为:  " + str);

        return  str;
    }

}