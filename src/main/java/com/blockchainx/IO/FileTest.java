package com.blockchainx.IO;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by hzliuxuan on 2018/3/23.
 */
public class FileTest {


    public static void main(String args[]) throws IOException {
        File dir = new File("\\root");
        File f1 = new File(dir, "fileOne.txt");
        File f2 = new File(dir, "fileTwo.java");
        // 文件对象创建后，指定的文件或目录不一定物理上存在
        if (!dir.exists()) {
            dir.mkdir();

        }
        if (!f1.exists()) {
            f1.createNewFile();
        }
        if (!f2.exists()) {
            f2.createNewFile();
        }
        System.out.println("f1's AbsolutePath=  " + f1.getAbsolutePath());
        System.out.println("f1 Canread=" + f1.canRead());
        System.out.println("f1's len= " + f1.length());
        String[] FL;
        int count = 0;
        FL = dir.list();
        for (int i = 0; i < FL.length; i++) {
            count++;
            System.out.println(FL[i] + "is in \\root");
        }
        System.out.println("there are" + count + " file in //root");


        try{
            FileInputStream rf=new   FileInputStream(f1);
            int n=512;
            byte  buffer[]=new  byte[n];


            //BufferedInputStream bin = new  BufferedInputStream( rf);
            //
            //while (bin.read(buffer) != -1){
            //    System.out.println("++++++"+new String(buffer) );
            //}

            FileReader fr = new FileReader(f1);
            char c[] = new char[n];
            while (fr.read(c,0,n) !=-1 ){
                System.out.println("||||||"+new String(c) );
            }
            fr.close();

            //while((rf.read(buffer)!=-1)){
            //    System.out.println("===="+new String(buffer) );
            //}
            //System.out.println();
            rf.close();
        } catch(IOException  IOe){
            System.out.println(IOe.toString());
        }
    }
}
