package com.blockchainx;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzliuxuan on 2018/3/15.
 */
public class TestMain {

    public static void main(String [] args) {
        List<String> tempTxList = new ArrayList<String>();
        tempTxList.add("a");
        tempTxList.add("b");
        tempTxList.add("c");
        tempTxList.add("d");
        tempTxList.add("e");
        MerkleTrees merkleTrees = new MerkleTrees(tempTxList);
        merkleTrees.merkle_tree();
        System.out.println("root : " + merkleTrees.getRoot());
    }


}
