package com.blockchainx.IO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by hzliuxuan on 2018/3/23.
 */
public class IOStreamTest {

    public static void main(String args[]) {
        String s;
        // 创建缓冲区阅读器从键盘逐行读入数据
        InputStreamReader ir = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(ir);
        System.out.println("Unix系统: ctrl-d 或 ctrl-c 退出"
            + "\nWindows系统: ctrl-z 退出");
        try {
            // 读一行数据，并标准输出至显示器
            s = in.readLine();
            // readLine()方法运行时若发生I/O错误，将抛出IOException异常
            while (s != null) {
                System.out.println("Read: " + s);
                s = in.readLine();
            }
            // 关闭缓冲阅读器
            in.close();
        } catch (IOException e) { // Catch any IO exceptions.
            e.printStackTrace();
        }
    }

}
