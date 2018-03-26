package com.blockchainx.ZMQ;



import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

/**
 * Created by hzliuxuan on 2018/3/26.
 */
public class RepTestServer {


    public static void main(String[] args) throws InterruptedException {

        //实现服务器端的上下文及套接字
        Context context = ZMQ.context(1);
        Socket responder = context.socket(ZMQ.REP);

        //使服务器端通过tcp协议通信，监听5555端口
        responder.bind("tcp://*:5555");
        while (!Thread.currentThread().isInterrupted()) {
            byte[] request = responder.recv(0);
            System.out.println("Received Hello");
            Thread.sleep(1000);
            String reply = "World";
            responder.send(reply.getBytes(), 0);
        }
        //关闭服务器端的上下文及套接字
        responder.close();
        context.close();
    }
}
