package com.blockchainx.UDP;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by hzliuxuan on 2018/3/19.
 */
public class UdpSend {


    public static void main(String[] args) throws Exception
    {
        //建立UDPSocket服务对象DatagramSocket
        DatagramSocket ds=new DatagramSocket();
        //主机对象
        InetAddress ia=InetAddress.getByName("255.255.255.255");

        //录入数据,字符流，键盘录入
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        String line=null;
        while((line=br.readLine())!=null)
        {
            if("886".equals(line)) {
                break;
            }
            //封装为数据报包
            byte [] buf=line.getBytes();
            DatagramPacket dp=new DatagramPacket(buf,buf.length,ia,UdpReceive.port);
            //发送
            ds.send(dp);
        }
        //关闭
        br.close();
        ds.close();
    }
}
