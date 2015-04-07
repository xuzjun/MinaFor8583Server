package com.yst.df.handler;

import java.util.concurrent.BlockingQueue;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class PackageHandler extends IoHandlerAdapter {
	
	private BlockingQueue<Object> queue;
	
	public PackageHandler(BlockingQueue<Object> queue) {
		this.queue = queue;
	}
	
	@Override
	public void sessionCreated(IoSession session) throws Exception {
		System.out.println(session.getRemoteAddress().toString() + " connected");
	}
	
	@Override
	public void sessionClosed(IoSession session) throws Exception {
		System.out.println(session.getRemoteAddress().toString() + " disconnected");
	}
	
	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		System.out.println("received message");
		String receivedMessage = message.toString();
		
		System.out.println("Recieved Message: " + receivedMessage);
		queue.put(receivedMessage);
	}
}