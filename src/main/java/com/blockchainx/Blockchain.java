package com.blockchainx;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzliuxuan on 2018/3/15.
 */
public  class  Blockchain {

    private List<Block> blockChains = new ArrayList<Block>();

    public List<Block> getBlockChains() {
        return blockChains;
    }

    public void setBlockChains(List<Block> blockChains) {
        this.blockChains = blockChains;
    }
}
