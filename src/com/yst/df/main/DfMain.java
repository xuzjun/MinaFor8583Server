package com.yst.df.main;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.yst.df.client.DFClient;
import com.yst.df.server.DFServer;

public class DfMain {

	public static void main(String[] args) {

		BlockingQueue<Object> queue = new LinkedBlockingQueue<Object>();
		DFClient client = new DFClient(queue);
		DFServer server = new DFServer(queue);
		
		new Thread(client).start();
		new Thread(server).start();
	}

}
