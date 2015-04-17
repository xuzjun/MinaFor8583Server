package com.yst.df.client;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.concurrent.BlockingQueue;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.yst.df.handler.ClientHandler;

public class DFClient implements Runnable {

	private static final String IP = "192.168.17.11";
	private static final int PORT = 10105;
	private BlockingQueue<Object> queue;

	public DFClient(BlockingQueue<Object> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		NioSocketConnector connector = new NioSocketConnector();
		connector.setConnectTimeoutMillis(30000000);
		// connector.getSessionConfig().setIdleTime(IdleStatus.WRITER_IDLE, 5);
		connector.getFilterChain().addLast("logger", new LoggingFilter());
		connector.getFilterChain().addLast(
				"codec",
				new ProtocolCodecFilter(new TextLineCodecFactory(Charset
						.forName("UTF-8"))));
		connector.setHandler(new ClientHandler());
		ConnectFuture cf = connector.connect(new InetSocketAddress(IP, PORT));
		cf.awaitUninterruptibly();
		IoSession session = cf.getSession();

		while (true) {
			Object message = null;
			try {
				message = queue.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Client : " + (String)message);
			session.write("0000");
		}
	}

}
