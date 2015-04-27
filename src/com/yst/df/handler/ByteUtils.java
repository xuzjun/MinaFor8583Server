package com.yst.df.handler;

public class ByteUtils {

	public static String bytesToHexString(byte[] input) {

		String output = null;
		for (int i = 0; i < input.length; i++) {
			String s = Integer.toHexString(input[i] & 0xFF);
			s = s.length() == 2 ? s : "0" + s;
			if (output == null) {
				output = s;
			} else {
				output += s;
			}
		}

		return output;
	}

	public static byte[] hexStringToBytes(String input) {
		if (!isHexString(input)) {
			return null;
		}

		int len;

		len = input.length();
		byte[] res = new byte[len / 2];

		for (int i = 0, j = 0; i < len; i++, j++) {
			res[j] = (byte) (Integer.parseInt(input.substring(i, ++i + 1), 16) & 0xFF);
		}

		return res;
	}

	public static void printBytes(byte[] bs) {
		for (int i = 0; i < bs.length; i++) {
			String s = Integer.toHexString(bs[i] & 0xFF);
			s = s.length() == 2 ? s : "0" + s;
			System.out.print(s + " ");
		}
		System.out.println();
	}

	public static boolean isHexString(String input) {

		int length = input.length();
		char[] dst = new char[length];

		input.getChars(0, length, dst, 0);
		int i;
		for (i = 0; i < dst.length; i++) {
			if (!((dst[i] >= '0' && dst[i] <= '9')
					|| (dst[i] >= 'a' && dst[i] <= 'z') || (dst[i] >= 'A' && dst[i] <= 'Z'))) {
				return false;
			}
		}

		if (i % 2 != 0) {
			return false;
		}

		return true;
	}

	public static byte[] shortToBytes(short source) {
		byte[] bs = new byte[2];
		bs[0] = (byte) (source >> 8 & 0xFF);
		bs[1] = (byte) (source & 0xFF);

		return bs;
	}

	public static int bcd2int(byte[] bt) {
		int num = 0;
		byte[] nbt = new byte[bt.length * 2];
		for (int i = 0; i < bt.length; i++) {
			int h = ((bt[i] & 0xff) >> 4);
			if (h > 9) {
				h += 55;
			} else {
				h += 48;
			}
			nbt[i * 2] = (byte) h;
			int l = (bt[i] & 0x0f);
			if (l > 9) {
				l += 55;
			} else {
				l += 48;
			}
			nbt[i * 2 + 1] = (byte) l;
			num += 1;
		}
		String hexStr = new String(nbt);
		return Integer.valueOf(hexStr, 16);
	}
}
