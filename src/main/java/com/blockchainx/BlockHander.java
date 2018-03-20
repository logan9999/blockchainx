package com.blockchainx;

import java.util.Date;

import javax.annotation.PostConstruct;

import com.blockchainx.socketServer.SocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by hzliuxuan on 2018/3/15.
 */
public class BlockHander {

    private static final Logger logger = LoggerFactory.getLogger(SocketServer.class);

    static public Blockchain blockchain = new Blockchain();

    static private int diffculty = 2;


    public String calculateHash(Block block){

        String recode = block.getHeight() + block.getPreHash() + block.getTimestamp() + block.getCounts() + block
            .getDifficulty() + block.getNonce();
        return BlockUtils.getSHA2HexValue(recode);

    }



    public Block generateBlock(Block oldBlock ,Long counts){
        Block newBlock = new Block();

        //创世区块
        if (BlockHander.blockchain.getBlockChains().isEmpty()){

            newBlock.setHeight(0L);
            newBlock.setTimestamp(new Date().toString());
            newBlock.setPreHash(null);
            newBlock.setCounts(0L);
            newBlock.setDifficulty(diffculty);

        }else{
            newBlock.setCounts(counts);
            newBlock.setHeight(oldBlock.getHeight()+1);
            newBlock.setPreHash(oldBlock.getHash());
            newBlock.setTimestamp(new Date().toString());
            newBlock.setDifficulty(diffculty);

        }

        /*
        * 这里的 for 循环很重要： 获得 i 的十六进制表示 ，将 Nonce 设置为这个值，并传入 calculateHash 计算哈希值。
        * 之后通过上面的 isHashValid 函数判断是否满足难度要求，如果不满足就重复尝试。 这个计算过程会一直持续，直到求得了满足要求的
        * Nonce 值，之后通过 handleWriteBlock 函数将新块加入到链上。
        */
        for (int i = 0;; i++) {
            String hex = String.format("%x", i);
            newBlock.setNonce(hex);
            if (!isHashValid(calculateHash(newBlock), newBlock.getDifficulty())) {
                System.out.printf("%s do more work!\n", calculateHash(newBlock));
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    logger.error("error:", e);
                }
                continue;
            } else {
                System.out.printf("%s work done!\n", calculateHash(newBlock));
                newBlock.setHash(calculateHash(newBlock));
                break;
            }
        }



        return newBlock;
    }


    public Boolean isBlockValid(Block newBlock,Block oldBlock){
        if (newBlock.getHeight() - oldBlock.getHeight() !=1){
            return false;
        }

        if (!newBlock.getPreHash().equals(oldBlock.getHash())){
            return false;
        }

        if (!calculateHash(newBlock).equals(newBlock.getHash())) {
            return false;
        }

        if (!isHashValid(newBlock.getHash(),newBlock.getDifficulty())){
            return false;
        }

        return true;

    }

    private static String repeat(String str, int repeat) {
        final StringBuilder buf = new StringBuilder();
        for (int i = 0; i < repeat; i++) {
            buf.append(str);
        }
        return buf.toString();
    }


    public static boolean isHashValid(String hash, int difficulty) {
        String prefix = repeat("0", difficulty);
        return hash.startsWith(prefix);
    }

    public void replaceChain(Blockchain newBlockchain){
        if (newBlockchain.getBlockChains().size() > BlockHander.blockchain.getBlockChains().size()){
            BlockHander.blockchain = newBlockchain;
        }

    }




}
