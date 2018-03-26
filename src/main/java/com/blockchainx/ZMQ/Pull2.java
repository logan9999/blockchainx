package com.blockchainx.ZMQ;

import org.zeromq.ZMQ;

/**
 * Created by hzliuxuan on 2018/3/26.
 */
public class Pull2 {

    public static void main(String args[]) {
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket pull = context.socket(ZMQ.PULL);

        pull.connect("tcp://*:" + 20098);

        int number = 0;
        while (true) {
            String message = new String(pull.recv());
            System.out.println(message);
            number++;
            if (number % 1000000 == 0) {
                System.out.println(number);
            }
        }
    }
}
