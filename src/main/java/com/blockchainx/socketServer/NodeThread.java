package com.blockchainx.socketServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.blockchainx.Block;
import com.blockchainx.BlockHander;
import com.blockchainx.Blockchain;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.http.converter.json.GsonBuilderUtils;

/**
 * Created by hzliuxuan on 2018/3/16.
 */
public class NodeThread extends Thread{

    private static final Logger logger = LoggerFactory.getLogger(NodeThread.class);
    final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    static Object o = new Object();
    private Socket socket;
    private Blockchain blockchain;

    private BlockHander blockHander  = new BlockHander();

    public NodeThread(Socket socket, Blockchain blockchain) {
        this.socket = socket;
        this.blockchain = blockchain;
    }

    @Override
    public void run() {
        InetAddress address = socket.getInetAddress();
        BufferedReader br = null;
        PrintWriter pw = null;
        try {
            //提示结点输入
            pw = new PrintWriter(socket.getOutputStream());
            pw.write("please enter a new number(vac):\n");
            pw.flush();
            String info = null;


            // 读取结点发送的信息
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while ((info = br.readLine()) != null) {
                try {
                    Long counts = Long.valueOf(info);



                    synchronized (o) {
                        int chainsSize = blockchain.getBlockChains().size();
                        // 根据vac创建区块
                        Block newBlock = blockHander.generateBlock(blockchain.getBlockChains().get(chainsSize-1),counts);
                        Boolean flag = true;
                        if (blockHander.isBlockValid(newBlock, blockchain.getBlockChains().get(chainsSize - 1))) {

                            List<Block> blocks = blockchain.getBlockChains();
                            blocks.add(newBlock);

                            for (Block block:blocks){
                                if (block.getHeight() == newBlock.getHeight()){
                                    flag = false;
                                }
                            }

                            blockchain.setBlockChains(blocks);

                            pw.write("Success!\n");
                            pw.write(gson.toJson(blockchain));
                        } else {
                            pw.write("HTTP 500: Invalid Block Error\n");
                        }
                        logger.info("add new block with vac：" + counts);
                    }
                } catch (Exception e) {
                    logger.error("not a number:", e);
                }
                pw.write("Congratulations on getting a unique block！You can try again. enter a new number(counts):" + "\n");
                // 调用flush()方法将缓冲输出
                pw.flush();
            }

        } catch (Exception e) {
            logger.error("TCP i/o error Or client closed", e);
        } finally {
            logger.info("node closed:" + address.getHostAddress() + ",port:" + socket.getPort());
            // 关闭资源
            try {
                if (pw != null) {
                    pw.close();
                }
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                logger.error("close error:", e);
            }
        }
    }

}
