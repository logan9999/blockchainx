package com.blockchainx.socketServer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import static com.blockchainx.socketServer.SocketServer.port;

/**
 * Created by hzliuxuan on 2018/3/16.
 */
public class SocketClient extends Thread{

    static String host = "127.0.0.1";


    //多线程挖矿
    public static void main(String args[]) throws Exception {

        for (int i = 0; i < 10; i++) {
            new SocketClient().start();
        }


    }


    @Override
    public void run(){

        try {

            sendThread();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void sendThread() throws IOException {
        int i = 1;
        while (true) {
            Socket socket = new Socket(host, port);
            PrintWriter pw = null;
            try {
                Thread.sleep(1000);
                System.out.println("===========socketClient================");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 建立连接后获得输出流
            OutputStream outputStream = socket.getOutputStream();
            String message = "99" + ++i;
            outputStream.write(message.getBytes("UTF-8"));
            outputStream.flush();

            socket.close();


        }


    }
}
