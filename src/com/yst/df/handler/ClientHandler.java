package com.yst.df.handler;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class ClientHandler extends IoHandlerAdapter {

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		System.out.println(session.getRemoteAddress().toString()
				+ "  ESTABLISHED");
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		System.out.println(session.getRemoteAddress().toString() + " CLOSED");
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		System.out.println(message.toString());
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		System.out.println("Message has sent out: " + message.toString());
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		System.out.println("Idle " + status.toString() + " " + session.isConnected());
		byte[] bs = new byte[4];
		for (int i = 0; i < bs.length; i++) {
			bs[i] = 0x30;
		}
		session.write(bs);
	}
}
