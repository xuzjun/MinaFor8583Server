package com.yst.df.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.concurrent.BlockingQueue;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.example.tapedeck.CommandDecoder;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.yst.df.handler.PackageHandler;

public class DFServer implements Runnable {

	private BlockingQueue<Object> queue;
	private static final int PORT = 10104;

	public DFServer(BlockingQueue<Object> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		IoAcceptor accptor = new NioSocketAcceptor();
		accptor.getFilterChain().addLast("logger", new LoggingFilter());
		//accptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8")) ));
//		accptor.getFilterChain().addLast( "codec", new ProtocolCodecFilter( new TextLineCodecFactory( Charset.forName( "UTF-8" ))));
		accptor.setDefaultLocalAddress(new InetSocketAddress(PORT));
		accptor.getSessionConfig().setReadBufferSize(2048);
		accptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 30);
		accptor.setHandler(new PackageHandler(queue));
		try {
			accptor.bind();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
