package com.blockchainx.socketServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import com.blockchainx.BlockHander;
import com.blockchainx.Blockchain;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by hzliuxuan on 2018/3/16.
 */
public class BroadcastThread extends Thread{
    private static final Logger logger = LoggerFactory.getLogger(NodeThread.class);

    final Gson gson = new GsonBuilder().setPrettyPrinting().create();



    private Socket socket;
    private Blockchain blockchain;

    BlockHander blockHander ;

    public BroadcastThread(Socket socket, Blockchain blockchain) {
        this.socket = socket;
        this.blockchain = blockchain;
    }

    @Override
    public void run() {


        for (;;) {
            PrintWriter pw = null;
            try {
                Thread.sleep(30000);
                logger.info("\n------------broadcast-------------\n");
                logger.info(gson.toJson(blockchain));
                pw = new PrintWriter(socket.getOutputStream());
                // 发送到其他结点
                pw.write("------------broadcast-------------\n");
                pw.write(gson.toJson(blockchain));
                pw.flush();
            } catch (InterruptedException e) {

                try {
                    pw.close();
                    socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                logger.error("error:", e);
            } catch (IOException e) {
                try {
                    pw.close();
                    socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                logger.error("error:", e);
            } catch (Exception e){
                try {
                    pw.close();
                    socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                logger.error("error:", e);
            }
        }
    }
}
