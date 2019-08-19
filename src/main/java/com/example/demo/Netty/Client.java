package com.example.demo.Netty;

import java.io.File;

public class Client {
    private static final int FILE_PORT = 9991;

    public static void main(String[] args) {

        try {
            FileUploadFile uploadFile = new FileUploadFile();
            File file = new File("/home/ywx/Desktop/新工科.txt");
            String fileMd5 = file.getName();
            uploadFile.setFile(file);
            uploadFile.setFile_md5(fileMd5);
            // 文件开始位置
            uploadFile.setStarPos(0);
            new FileUploadClient().connect(FILE_PORT, "127.0.0.1", uploadFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
