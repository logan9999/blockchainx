package com.blockchainx.socketServer;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import com.blockchainx.Block;
import com.blockchainx.BlockHander;
import com.blockchainx.Blockchain;
import com.blockchainx.UDP.UdpBroadcast;
import com.blockchainx.UDP.UdpReceive;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by hzliuxuan on 2018/3/16.
 */
public class SocketServer extends Thread{

    private static final Logger logger = LoggerFactory.getLogger(SocketServer.class);

    // 监听指定的端口
    static int port = 2008;

    //Blockchain blockchain = BlockHander.blockchain;
    static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) throws Exception {
        socketStart();

    }


    @Override
    public void  run(){
        try {
            SocketServer.socketStart();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void socketStart() throws IOException {
        //创世区块
        Block genesisBlock = new BlockHander().generateBlock(null,0L);

        Blockchain blockchain = BlockHander.blockchain;

        List<Block> blocks = BlockHander.blockchain.getBlockChains();
        blocks.add(genesisBlock);
        blockchain.setBlockChains(blocks);

        logger.info(gson.toJson(BlockHander.blockchain));

        //启动UDP监听
        new UdpReceive().start();

        //实时广播区块链数据
        new UdpBroadcast().start();



        ServerSocket serverSocket = new ServerSocket(port);

        // server将一直等待连接的到来
        System.out.println("server将一直等待连接的到来");
        logger.info("*** Node is started,waiting for others ***");
        // 监听对等网络中的结点
        for(;;) {
            final Socket socket = serverSocket.accept();
            // 创建一个新的线程 ,和建立连接的结点通讯
            new NodeThread(socket, blockchain).start();

            // 模拟网络结点广播
            new BroadcastThread(socket, blockchain).start();



        }
    }
}
