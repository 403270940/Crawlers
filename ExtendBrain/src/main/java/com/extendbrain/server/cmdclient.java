package com.extendbrain.server;

import java.io.PrintWriter;
import java.net.Socket;

public class cmdclient {

	public static void main(String[] args) {
		try {
			Socket socket = new Socket("localhost", 12323);
			PrintWriter os = new PrintWriter(socket.getOutputStream());
//			os.write(cmd.ShutDownDelay + 3600);
			os.write("");
			os.flush();
			os.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
