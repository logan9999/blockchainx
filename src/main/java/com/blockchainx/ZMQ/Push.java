package com.blockchainx.ZMQ;

import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

/**
 * Created by hzliuxuan on 2018/3/26.
 */
public class Push {

    public static void main(String[] args) throws InterruptedException {
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket push  = context.socket(ZMQ.PUSH);
        //push.bind("tcp://localhost:" + 20099);
        //pull.bind("ipc://fjsaa");
        push.bind("tcp://*:" + 20098);
        for (int i = 0; i > -1; i++) {
            push.send("hello".getBytes());
            System.out.println("=====hello" + i);
            Thread.sleep(1000);
        }
        push.close();
        context.term();
    }
}
