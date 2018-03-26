package com.blockchainx.IO;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Created by hzliuxuan on 2018/3/23.
 */
public class EncodeTest {

    private static void readBuff(byte [] buff) throws IOException {
        ByteArrayInputStream in =new ByteArrayInputStream(buff);
        int data;
        while((data=in.read())!=-1){
            System.out.print(data+"  ");

        }
        System.out.println();     in.close();     }

    public static void main(String args[]) throws IOException {
        System.out.println("内存中采用unicode字符编码：" );
        char   c='好';
        int lowBit=c&0xFF;     int highBit=(c&0xFF00)>>8;
        System.out.println("lowBit:"+lowBit+"  higtBit: "+highBit);
        String s="好";
        System.out.println("本地操作系统默认字符编码：");
        readBuff(s.getBytes());
        System.out.println("采用GBK字符编码：");
        readBuff(s.getBytes("GBK"));
        System.out.println("采用UTF-8字符编码：");
        readBuff(s.getBytes("UTF-8"));
    }
}



