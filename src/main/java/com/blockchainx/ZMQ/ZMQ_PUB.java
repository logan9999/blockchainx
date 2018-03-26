package com.blockchainx.ZMQ;

import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

/**
 * Created by hzliuxuan on 2018/3/26.
 */
public class ZMQ_PUB {

    public static void main(String[] args) throws InterruptedException {
        Context context = ZMQ.context(1);
        Socket publisher = context.socket(ZMQ.PUB);
        publisher.bind("tcp://*:5555");
        Thread.sleep(3000);
        for(int i=0;i<100;i++){
            publisher.send(("admin " + i).getBytes(), ZMQ.NOBLOCK);
            System.out.println("pub msg " + i);
            Thread.sleep(1000);
        }
        context.close();
        publisher.close();
    }
}
