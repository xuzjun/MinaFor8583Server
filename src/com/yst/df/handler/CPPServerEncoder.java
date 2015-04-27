package com.yst.df.handler;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class CPPServerEncoder implements ProtocolEncoder {

	private Logger logger = Logger.getLogger(CPPServerEncoder.class);
	
	@Override
	public void dispose(IoSession arg0) throws Exception {
		
	}

	@Override
	public void encode(IoSession ioSession, Object message, ProtocolEncoderOutput out)
			throws Exception {
		byte[] input = message == null? new byte[]{(byte)0x00}:(byte[]) message;
		
		IoBuffer buff = IoBuffer.allocate(input.length).setAutoExpand(true);
		buff.put(input);
		buff.flip();
		logger.info(new StringBuffer("·¢ËÍ±¨ÎÄ[").append(ByteUtils.bytesToHexString(input)).append("]"));;
		out.write(buff);
		out.flush();
	}

}
