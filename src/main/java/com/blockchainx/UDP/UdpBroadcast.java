package com.blockchainx.UDP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;

import com.blockchainx.Block;
import com.blockchainx.BlockHander;
import com.blockchainx.Blockchain;
import com.blockchainx.socketServer.NodeThread;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by hzliuxuan on 2018/3/19.
 */
public class UdpBroadcast extends Thread{
    private static final Logger logger = LoggerFactory.getLogger(UdpBroadcast.class);

    final Gson gson = new GsonBuilder().setPrettyPrinting().create();


    @Override
    public void run() {

        Blockchain blockchain = BlockHander.blockchain;

        BlockHander blockHander  = new BlockHander();

        //建立UDPSocket服务对象DatagramSocket
        DatagramSocket ds= null;
        try {
            ds = new DatagramSocket();

            //主机对象
            InetAddress ia = InetAddress.getByName("255.255.255.255");
            ds.setSendBufferSize(1000000);


            for (;;) {

                String blocks = gson.toJson(blockchain);

                List<Block> blockchains = blockchain.getBlockChains();

                Thread.sleep(10000);
                logger.info(
                    "\n===================================UdpBroadcast==================================\n");


                for (Block block:blockchains){



                        String blockStr = gson.toJson(block);
                        //封装为数据报包
                        byte[] buf = blockStr.getBytes();

                        DatagramPacket dp = new DatagramPacket(buf, buf.length, ia, UdpReceive.port);
                        logger.info("send blockStr:"+blockStr);
                        //发送
                        ds.send(dp);
                }


            }






        } catch (SocketException e) {
            logger.error("=================="+e.getMessage(),e);
            e.printStackTrace();
        } catch (UnknownHostException e) {
            logger.error("=================="+e.getMessage(),e);
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("=================="+e.getMessage(),e);
            e.printStackTrace();
        } catch (InterruptedException e) {
            logger.error("=================="+e.getMessage(),e);
            e.printStackTrace();
        }
    }
}
