package relax.sn.com.relax4.utils;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.Date;

import relax.sn.com.relax4.bean.ChatMessage;
import relax.sn.com.relax4.bean.Result;


/**
 * Created by John on 2018/4/1.
 */
public class HttpUtils {
    private static final String URL = "http://www.tuling123.com/openapi/api";
    //"请自行申请apikey"
    private static final String API_KEY = "534dc342ad15885dffc10d7b5f813451";

    /**
     * 发送一个消息，得到返回的消息
     * @param msg
     * @return
     * 11.同为用户传入一个mseeage
     */
    public static ChatMessage sendMessage(String msg)
    {
        ChatMessage chatMessage = new ChatMessage();//下面需要将chatMessage转换为Result，用gson
        String jsonRes = doGet(msg);//12.通过doget拿到返回数据
        Gson gson = new Gson();//13.通过gson将返回数据转换成result对象
        Result result = null;//因为gson返回可能不匹配，所以try catch 保证能返回一个东西
        try
        {
            result = gson.fromJson(jsonRes, Result.class);
            chatMessage.setMsg(result.getText());//14.转换成功result中的消息就是聊天的消息
        } catch (Exception e)
        {
            chatMessage.setMsg("服务器繁忙，请稍候再试"); //15.失败的话
        }
        chatMessage.setDate(new Date());//设置时间
        chatMessage.setType(ChatMessage.Type.INCOMING);//设置类型为收到
        return chatMessage;
    }
    //1.首先用户传入一个msg
    public static String doGet(String msg)
    {
        String result = "";
        String url = setParams(msg); //2.拼接url
        ByteArrayOutputStream baos = null;
        InputStream is = null;
        try
        {
            java.net.URL urlNet = new java.net.URL(url);//3.通过url拿到一个java.net的url
            HttpURLConnection conn = (HttpURLConnection) urlNet
                    .openConnection();//4.通过java.net的url打开一个链接,下面设置一些参数
            conn.setReadTimeout(5 * 1000);
            conn.setConnectTimeout(5 * 1000);
            conn.setRequestMethod("GET");
            is = conn.getInputStream();//5.拿到服务器返回的流
            int len = -1;
            byte[] buf = new byte[128];
            baos = new ByteArrayOutputStream();
            //6.对流进行读操作，读到本地
            while ((len = is.read(buf)) != -1)
            {
                baos.write(buf, 0, len);
            }
            baos.flush();
            result = new String(baos.toByteArray());//将流转换成字符串并返回
        } catch (MalformedURLException e)
        {
            e.printStackTrace();
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                if (baos != null)
                    baos.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }

            try
            {
                if (is != null)
                {
                    is.close();
                }
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return result;
    }

    private static String setParams(String msg)
    {
        String url = "";
        try
        {
            url = URL + "?key=" + API_KEY + "&info="
                    + URLEncoder.encode(msg, "UTF-8");
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return url;
    }

}
