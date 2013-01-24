package Stan;

import java.math.BigInteger;

public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		byte[] c;
		byte[] sixfour = new byte[8];
		int a = 20;
		c = new byte[] { (byte) (a >>> 24), (byte) (a >>> 16),
				(byte) (a >>> 14), (byte) a };

		String s = "20";
		byte[] sb = s.getBytes();
		int rgb = 56;
		System.out.println(" c " + c[0] + " " + c[1] + " " + c[2] + " " + c[3]
				+ " ");
		System.out.println("BitCount: " + Integer.bitCount(a));
		System.out.println("Bytes length: " + sb.length);
		for (byte i : sb) {
			System.out.print(i);
		}
		System.out.println("rgb: " + rgb);
		System.out.println("10/ 64 = " + 10 / 64);
		System.out.println(Integer.toHexString(rgb));
		String b = "aya";
		String out = "";
		out += s;
		out += b;

		System.out.println(71/64);

	}

	private static byte[] encode(int input[], int len) {
		byte[] out = new byte[len];
		int i, j;
		for (i = j = 0; j < len; i++, j += 4) {
			out[j] = (byte) (input[i] & 0xff);
			out[j + 1] = (byte) ((input[i] >>> 8) & 0xff);
			out[j + 2] = (byte) ((input[i] >>> 16) & 0xff);
			out[j + 3] = (byte) ((input[i] >>> 24) & 0xff);
		}
		return out;
	}

	private static byte[] encode(long l) {
		byte[] out = new byte[8];
		out[0] = (byte) (l & 0xff);
		out[1] = (byte) ((l >>> 8) & 0xff);
		out[2] = (byte) ((l >>> 16) & 0xff);
		out[3] = (byte) ((l >>> 24) & 0xff);
		out[4] = (byte) ((l >>> 32) & 0xff);
		out[5] = (byte) ((l >>> 40) & 0xff);
		out[6] = (byte) ((l >>> 48) & 0xff);
		out[7] = (byte) ((l >>> 56) & 0xff);
		return out;
	}
}
