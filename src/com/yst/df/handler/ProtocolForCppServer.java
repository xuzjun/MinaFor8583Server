package com.yst.df.handler;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class ProtocolForCppServer implements ProtocolCodecFactory {
	
	private ProtocolDecoder decoder;
	private ProtocolEncoder encoder;

	public ProtocolForCppServer() {
		this.decoder = new CPPServerDecoder();
		this.encoder = new CPPServerEncoder();
	}
	
	@Override
	public ProtocolDecoder getDecoder(IoSession arg0) throws Exception {
		return decoder;
	}

	@Override
	public ProtocolEncoder getEncoder(IoSession arg0) throws Exception {
		return encoder;
	}

}
