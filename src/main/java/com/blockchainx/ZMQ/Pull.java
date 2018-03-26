package com.blockchainx.ZMQ;

import java.util.concurrent.atomic.AtomicInteger;

import org.zeromq.ZMQ;

/**
 * Created by hzliuxuan on 2018/3/26.
 */
public class Pull {



    public static void main(String args[]) {
        final AtomicInteger number = new AtomicInteger(0);
        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable(){
                private int here = 0;
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    ZMQ.Context context = ZMQ.context(1);
                    ZMQ.Socket pull = context.socket(ZMQ.PULL);
                    //pull.connect("ipc://fjsaa");

                    pull.bind("tcp://localhost:" + 20098);

                    while (true) {
                        String message = new String(pull.recv());
                        System.out.println("message:"+ message);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        int now = number.incrementAndGet();
                        here++;
                        if (now % 1000000 == 0) {
                            System.out.println(now + "  here is : " + here);
                        }
                    }
                }
            }).start();
        }
    }

}
