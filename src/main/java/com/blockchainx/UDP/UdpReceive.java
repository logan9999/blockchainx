package com.blockchainx.UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

import com.blockchainx.Block;
import com.blockchainx.BlockHander;
import com.blockchainx.Blockchain;
import com.blockchainx.socketServer.BroadcastThread;
import com.blockchainx.socketServer.NodeThread;
import com.blockchainx.socketServer.SocketServer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.blockchainx.BlockHander.blockchain;

/**
 * Created by hzliuxuan on 2018/3/19.
 */
public class UdpReceive extends Thread{

    private static final Logger logger = LoggerFactory.getLogger(UdpReceive.class);

    static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    static final int port = 2009;

    static Object o = new Object();

    @Override
    public void run() {
         BlockHander blockHander = new BlockHander();
         Blockchain blockchain = BlockHander.blockchain;
         //收到Block后做验证、上链等操作

        //建立DatagramSocket对象服务，并指定端口
        DatagramSocket ds= null;
        try {
            ds = new DatagramSocket(UdpReceive.port);

        while(true)
        {    //接收数据报包
            byte[] buf=new byte[1024];
            DatagramPacket dp=new DatagramPacket(buf,buf.length);
            //接收
            ds.receive(dp);
            //获取数据报中的数据
            String ip = dp.getAddress().getHostAddress();
            String hostName = dp.getAddress().getHostName();
            String data = new String(dp.getData(),0,dp.getLength());
            logger.info("=====================UdpReceive:"+ip + " hostName:"+hostName);

            try {
                Block receiveblock = gson.fromJson(data,Block.class);
                logger.info("receiverBalck:"+gson.toJson(receiveblock));
                synchronized (o) {
                    //比较当前最后一个Block
                    List<Block> blocks = blockchain.getBlockChains();
                    //最新一个Block
                    Block lastblock = (Block)blocks.get(blockchain.getBlockChains().size() - 1);
                    /*if (lastblock.getHeight() == receiveblock.getHeight() && !lastblock.getHash().equals(
                        receiveblock.getHash())) {
                        //分叉
                        if (BlockHander.isHashValid(receiveblock.getHash(), receiveblock.getDifficulty())) {

                        }
                    }*/
                    //上链
                    Boolean flag = true;
                    if (blockHander.isBlockValid(receiveblock,lastblock)) {
                        for (Block block1 :blocks){
                            if (block1.getHeight() ==  receiveblock.getHeight()){
                                flag = false;
                                break;
                            }

                        }
                        if (flag) {
                            blockchain.getBlockChains().add(receiveblock);
                        }
                        logger.info("receiverBalckchain:" + gson.toJson(blockchain));
                    }
                }

            } catch (Exception e) {
                logger.error("UdpReceive:", e);
            }


        }

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
