package com.blockchainx;

import java.io.IOException;

import com.blockchainx.socketServer.SocketServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BlockchainxApplication {

	public static void main(String[] args) {


		//开启后台socket监听
		new SocketServer().start();


		SpringApplication.run(BlockchainxApplication.class, args);
	}
}
