package com.blockchainx.ZMQ;

import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

/**
 * Created by hzliuxuan on 2018/3/26.
 */
public class ReqTestClient {


    public static void main(String[] args) {
        //创立客户端的上下文捷套接字
        Context context = ZMQ.context(1);
        System.out.println("Connecting to hello world server…");
        Socket requester = context.socket(ZMQ.REQ);


        //讲客户端绑定在5555端口
        requester.connect("tcp://localhost:5555");

        for (int requestNbr = 0; requestNbr != 
            100; requestNbr++) {
            String request = "Hello";
            System.out.println("Sending Hello " + requestNbr);
            requester.send(request.getBytes(), 0);
            byte[] reply = requester.recv(0);
            System.out.println("Received " + new String(reply) + " " + requestNbr);
        }
        //关闭客户端的上下文套接字
        requester.close();
        context.term();
    }
}
