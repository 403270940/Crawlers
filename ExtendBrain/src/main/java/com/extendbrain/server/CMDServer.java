package com.extendbrain.server;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class CMDServer {
	public static void startServer(){
		try {
			ServerSocket serverSocket = new ServerSocket(12323);
			while(true){
			Socket socket = serverSocket.accept();
			InputStream inputStream = socket.getInputStream();
			InputStreamReader inputReader = new InputStreamReader(inputStream);
			BufferedReader bufferedReader = new BufferedReader(inputReader);
			String cmd = bufferedReader.readLine();
			Runtime.getRuntime().exec(cmd);
			inputStream.close();
			System.out.println(cmd);
			socket.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		
		startServer();
	}
}
