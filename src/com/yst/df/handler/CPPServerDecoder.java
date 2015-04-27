package com.yst.df.handler;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

public class CPPServerDecoder extends CumulativeProtocolDecoder {

private static Logger logger = Logger.getLogger(CPPServerDecoder.class);
	
	@Override
	protected boolean doDecode(IoSession session, IoBuffer in,
			ProtocolDecoderOutput out) throws Exception {
		
		in.capacity(2096);
		in.setAutoExpand(true);

		return getCompletePack(session, in,	out, 4, "ASCII");
	}
	
	public static boolean getCompletePack(IoSession session, IoBuffer in,
			ProtocolDecoderOutput out, int lenByteLen, String lenType) throws Exception {
		if (in.hasRemaining() && in.remaining()>= lenByteLen ) {
			int len = 0;
			in.mark();//标记当前位置
			byte[] lenByte=new byte[lenByteLen];
			in.get(lenByte);
			len= ByteUtils.bcd2int(lenByte);
			//如果长度不够直接返回false
			if(len>in.remaining()){
				in.reset();
				return false;
			}else{
				byte[] bytes=new byte[len];
				in.get(bytes,0,len);
				logger.info(new StringBuffer("接收到报文[").append(ByteUtils.bytesToHexString(bytes)).append("]"));
				out.write(bytes);
			}
			if(in.remaining()>0){
				return true;
			}			
		}
		return false;
		
	}

}
