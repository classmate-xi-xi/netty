package com.example.demo.MS;

import com.baidu.aip.speech.AipSpeech;
import com.baidu.aip.util.Util;

import org.json.JSONObject;

import java.io.IOException;

public class Speech {

    public static final String APP_ID = "16986710";
    public static final String API_KEY = "XljKVmFrk2r29MI30qlQmGz1";
    public static final String SECRET_KEY = "tM4ugto4mFDRUcfPpyzgEpcNuQCKP57W";

    public static void main(String[] args) throws IOException {
        // 初始化一个AipSpeech
        AipSpeech client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 调用接口
//        JSONObject res = client.asr("/home/abc/Desktop/16k.pcm", "pcm", 16000, null);
//        System.out.println(res.toString(2));

        Speech sampleSound=new Speech();
        sampleSound.asr(client);

    }


    public void asr(AipSpeech client) throws IOException {
        // 对本地语音文件进行识别
        String path = "/home/ywx/Desktop/untitled/src/sample/MscInvisibleDemo/test.pcm";
//        JSONObject asrRes = client.asr(path, "pcm", 16000, null);
//        System.out.println(asrRes);

        // 对语音二进制数据进行识别
        byte[] data = Util.readFileByBytes(path);     //readFileByBytes仅为获取二进制数据示例
        JSONObject asrRes2 = client.asr(data, "pcm", 16000, null);

        String[] s = asrRes2.toString().split("");
        System.out.println(s[0]);
        System.out.println(asrRes2);

    }
}
