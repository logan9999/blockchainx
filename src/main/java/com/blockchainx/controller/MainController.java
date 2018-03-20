package com.blockchainx.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.blockchainx.Block;
import com.blockchainx.BlockHander;
import com.blockchainx.Blockchain;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hzliuxuan on 2018/1/31.
 */
@EnableAutoConfiguration
@RestController
@RequestMapping("/")
public class MainController {
    @RequestMapping("getBlockchain")
    public Blockchain getBlockchain() {

        return BlockHander.blockchain;

    }

    @RequestMapping("pullBlock/{counts}")
    public Blockchain pullBlock(@PathVariable(value = "counts", required = false) @NotEmpty Long counts) {
        BlockHander blockHander = new BlockHander();
        Blockchain blockchain = BlockHander.blockchain;
        int size = 0;
        Block lastBlock = null;

        if (blockchain!=null && blockchain.getBlockChains() !=null && !blockchain.getBlockChains().isEmpty()){
            size = blockchain.getBlockChains().size();
            lastBlock = blockchain.getBlockChains().get(size -1);
        }


        Block newBlock = blockHander.generateBlock(lastBlock,counts);
        //非创世区块
        if (size > 0 && blockHander.isBlockValid(newBlock,
            blockchain.getBlockChains().get(size - 1))) {

            List<Block> blocks = BlockHander.blockchain.getBlockChains();
            blocks.add(newBlock);
            blockchain.setBlockChains(blocks);
            blockHander.replaceChain(blockchain);

            return BlockHander.blockchain;

        }

        //创世区块
        if (size == 0 ){
            List<Block> blocks = BlockHander.blockchain.getBlockChains();
            blocks.add(newBlock);
            BlockHander.blockchain.setBlockChains(blocks);
        }

        return BlockHander.blockchain;

    }
}
