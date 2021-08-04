package com.gyy.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class IOserver {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8000);

        //每一个客户端连接的时候，创建新线程处理
        while (true) {
            // (1) 阻塞方法获取新的连接
            Socket socket = serverSocket.accept();

            new Thread() {
                @Override
                public void run() {
                    String name = Thread.currentThread().getName();

                    try {
                        // (2) 每一个新的连接都创建一个线程，负责读取数据
                        byte[] data = new byte[1024];
                        InputStream inputStream = socket.getInputStream();
                        while (true) {
                            int len;
                            // (3) 按字节流方式读取数据
                            while ((len = inputStream.read(data)) != -1) {
                                System.out.println("线程" + name + ":" + new String(data, 0, len));
                            }
                        }
                    } catch (Exception e) {
                    }
                }
            }.start();
        }
    }
}
