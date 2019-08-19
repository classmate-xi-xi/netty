package com.example.demo.MS;

import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;
import org.tritonus.share.sampled.AudioUtils;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Mp3 {
   /**
     * MP3转换PCM文件方法
     * @param mp3filepath 原始文件路径
     * @param pcmfilepath 转换文件的保存路径
     * @throws Exception
     */
//        public static void mp3Convertpcm(String mp3filepath, String pcmfilepath) throws Exception{
//            File mp3 = new File(mp3filepath);
//            File pcm = new File(pcmfilepath);
//            //原MP3文件转AudioInputStream
//            AudioInputStream mp3audioStream = AudioSystem.getAudioInputStream(mp3);
//            //将AudioInputStream MP3文件 转换为PCM AudioInputStream
//            AudioInputStream pcmaudioStream = AudioSystem.getAudioInputStream(AudioFormat.Encoding.PCM_SIGNED, mp3audioStream);
//            //准备转换的流输出到OutputStream
//            OutputStream os = new FileOutputStream(pcm);
//            int bytesRead = 0;
//            byte[] buffer = new byte[8192];
//            while ((bytesRead=pcmaudioStream.read(buffer, 0, 8192))!=-1) {
//                os.write(buffer, 0, bytesRead);
//            }
//            os.close();
//            pcmaudioStream.close();
//        }
//
//    public static void main(String[] args) throws Exception {
//        mp3Convertpcm("/home/ywx/Music/xxx.mp3","/home/ywx/");
//    }

   private static AudioUtils audioUtils = null;
    private Mp3(){

    }

    //双判断，解决单利问题
    public static AudioUtils getInstance(){
        if(audioUtils == null){
            synchronized (AudioUtils.class) {
                if(audioUtils == null){
                    audioUtils = new AudioUtils();
                }
            }
        }
        return audioUtils;
    }

    /**
     * MP3转换PCM文件方法
     *
     * @param mp3filepath 原始文件路径
     * @param pcmfilepath 转换文件的保存路径
     * @return
     * @throws Exception
     */
    public boolean convertMP32Pcm(String mp3filepath, String pcmfilepath){
        try {
            //获取文件的音频流，pcm的格式
            AudioInputStream audioInputStream = getPcmAudioInputStream(mp3filepath);
            //将音频转化为  pcm的格式保存下来
            AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, new File(pcmfilepath));
            return true;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 播放MP3方法
     *
     * @param mp3filepath
     * @throws Exception
     */
    public void playMP3(String mp3filepath) throws Exception {
        //获取音频为pcm的格式
        AudioInputStream audioInputStream = getPcmAudioInputStream(mp3filepath);

        // 播放
        if (audioInputStream == null){
            System.out.println("null audiostream");
            return;
        }
        //获取音频的格式
        AudioFormat targetFormat = audioInputStream.getFormat();
        DataLine.Info dinfo = new DataLine.Info(SourceDataLine.class, targetFormat, AudioSystem.NOT_SPECIFIED);
        //输出设备
        SourceDataLine line = null;
        try {
            line = (SourceDataLine) AudioSystem.getLine(dinfo);
            line.open(targetFormat);
            line.start();

            int len = -1;
//            byte[] buffer = new byte[8192];
            byte[] buffer = new byte[1024];
            //读取音频文件
            while ((len = audioInputStream.read(buffer)) > 0) {
                //输出音频文件
                line.write(buffer, 0, len);
            }

            // Block等待临时数据被输出为空
            line.drain();

            //关闭读取流
            audioInputStream.close();

            //停止播放
            line.stop();
            line.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("audio problem " + ex);

        }
    }

    /**
     * 创建日期:2018年1月14日<br/>
     * 创建时间:下午9:53:14<br/>
     * 创建用户:yellowcong<br/>
     * 机能概要:获取文件的音频流
     * @param mp3filepath
     * @return
     */
    private AudioInputStream getPcmAudioInputStream(String mp3filepath) {
        File mp3 = new File(mp3filepath);
        AudioInputStream audioInputStream = null;
        AudioFormat targetFormat = null;
        try {
            AudioInputStream in = null;

            //读取音频文件的类
            MpegAudioFileReader mp = new MpegAudioFileReader();
            in = mp.getAudioInputStream(mp3);
            AudioFormat baseFormat = in.getFormat();

            //设定输出格式为pcm格式的音频文件
            targetFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16,
                    baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);

            //输出到音频
            audioInputStream = AudioSystem.getAudioInputStream(targetFormat, in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return audioInputStream;
    }

    public static void main(String[] args) {
        Mp3 m = new Mp3();
        m.convertMP32Pcm("/home/ywx/Desktop/.mp3","/home/ywx/xxx.pcm");
        System.out.println("完成！");
    }

}

