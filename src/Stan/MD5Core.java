package Stan;

/**
 * This is core Class for MD5 Algorithm,
 * 
 * MD5 Algorithm have five steps, 1. Append Padding Bits 2. Append length 3.
 * Initialize MD Buffer 4. Process Message in 16-word Block 5. Output
 * 
 * @author stanchen
 * 
 */

public class MD5Core {

	/*
	 * Padding byte (64 bytes)
	 */
	private static final byte padding[] = { (byte) 0x80, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, };

	/**
	 * This is the first step of MD5, append padding to message.
	 * 
	 * @param message
	 *            the whole input value
	 * @param length
	 *            the length of input value
	 * @return the block match 64 bytes
	 */
	public byte[] appendPadding(byte[] message, int message_length) {
		int append_length;
		int reminder = (int) (message_length % 64);
		append_length = (reminder < 56) ? (56 - reminder) : (120 - reminder);
		int total_length = message_length + append_length + 8;
		message = cutMessageLength(message, total_length);
		appendByteArray(padding, 0, message, message_length, append_length);
		return message;
	}

	private byte[] cutMessageLength(byte[] message, int total_length) {
		byte[] result = new byte[total_length];
		appendByteArray(message, 0, result, 0, total_length);
		return result;
	}

	/**
	 * Step two append Byte Array and append Length to the message.
	 * 
	 * @param first_bytes
	 * @param first_length
	 * @param second_bytes
	 * @param second_length
	 */
	private void appendByteArray(byte[] source_array,
			int source_array_position, byte[] dest_array,
			int dest_array_opsition, int append_length) {
		System.arraycopy(source_array, source_array_position, dest_array,
				dest_array_opsition, append_length);
	}

	public byte[] appendLength(byte[] step_one_result_bytes,
			long step_one_result_length) {
		byte[] length_bytes = convertLongToBytes(step_one_result_length);
		int block_number = step_one_result_bytes.length / 64 - 1;
		appendByteArray(length_bytes, 0, step_one_result_bytes,
				(56 + (block_number * 64)), 8);

		return step_one_result_bytes;
	}

	private byte[] convertLongToBytes(long l) {
		l = l * 8;
		byte[] result = new byte[8];
		result[0] = (byte) (l & 0xff);
		result[1] = (byte) ((l >>> 8) & 0xff);
		result[2] = (byte) ((l >>> 16) & 0xff);
		result[3] = (byte) ((l >>> 24) & 0xff);
		result[4] = (byte) ((l >>> 32) & 0xff);
		result[5] = (byte) ((l >>> 40) & 0xff);
		result[6] = (byte) ((l >>> 48) & 0xff);
		result[7] = (byte) ((l >>> 56) & 0xff);
		return result;
	}

	/**
	 * Step three, initialize the four word.
	 * 
	 */
	public int state[] = new int[4];

	private void initState() {
		state[0] = 0x67452301;
		state[1] = 0xefcdab89;
		state[2] = 0x98badcfe;
		state[3] = 0x10325476;
	}

	/**
	 * Step four, hash the value. 1. four methods 2. 64 rounds
	 */

	public void initHash() {
		initState();
	}

	public void hashProcess(byte[] message) {
		int index = message.length / 64;
		for (int i = 0; i < index; i++) {
			int[] subMessageInt = bytesToInts(message, 64, i * 64);
			state = oneHashProcess(state, subMessageInt);
		}
	}

	private int[] bytesToInts(byte buffer[], int len, int offset) {
		int i, j;
		int[] result = new int[16];
		for (i = j = 0; j < len; i++, j += 4) {
			result[i] = ((buffer[j + offset] & 0xff))
					| (((buffer[j + 1 + offset] & 0xff)) << 8)
					| (((buffer[j + 2 + offset] & 0xff)) << 16)
					| (((buffer[j + 3 + offset] & 0xff)) << 24);
		}
		return result;
	}

	private int[] oneHashProcess(int[] state, int[] x) {
		int a = state[0];
		int b = state[1];
		int c = state[2];
		int d = state[3];

		// Round 1
		a = FF(a, b, c, d, x[0], 7, 0xd76aa478);
		d = FF(d, a, b, c, x[1], 12, 0xe8c7b756);
		c = FF(c, d, a, b, x[2], 17, 0x242070db);
		b = FF(b, c, d, a, x[3], 22, 0xc1bdceee);
		a = FF(a, b, c, d, x[4], 7, 0xf57c0faf);
		d = FF(d, a, b, c, x[5], 12, 0x4787c62a);
		c = FF(c, d, a, b, x[6], 17, 0xa8304613);
		b = FF(b, c, d, a, x[7], 22, 0xfd469501);
		a = FF(a, b, c, d, x[8], 7, 0x698098d8);
		d = FF(d, a, b, c, x[9], 12, 0x8b44f7af);
		c = FF(c, d, a, b, x[10], 17, 0xffff5bb1);
		b = FF(b, c, d, a, x[11], 22, 0x895cd7be);
		a = FF(a, b, c, d, x[12], 7, 0x6b901122);
		d = FF(d, a, b, c, x[13], 12, 0xfd987193);
		c = FF(c, d, a, b, x[14], 17, 0xa679438e);
		b = FF(b, c, d, a, x[15], 22, 0x49b40821);

		// Round 2
		a = GG(a, b, c, d, x[1], 5, 0xf61e2562);
		d = GG(d, a, b, c, x[6], 9, 0xc040b340);
		c = GG(c, d, a, b, x[11], 14, 0x265e5a51);
		b = GG(b, c, d, a, x[0], 20, 0xe9b6c7aa);
		a = GG(a, b, c, d, x[5], 5, 0xd62f105d);
		d = GG(d, a, b, c, x[10], 9, 0x02441453);
		c = GG(c, d, a, b, x[15], 14, 0xd8a1e681);
		b = GG(b, c, d, a, x[4], 20, 0xe7d3fbc8);
		a = GG(a, b, c, d, x[9], 5, 0x21e1cde6);
		d = GG(d, a, b, c, x[14], 9, 0xc33707d6);
		c = GG(c, d, a, b, x[3], 14, 0xf4d50d87);
		b = GG(b, c, d, a, x[8], 20, 0x455a14ed);
		a = GG(a, b, c, d, x[13], 5, 0xa9e3e905);
		d = GG(d, a, b, c, x[2], 9, 0xfcefa3f8);
		c = GG(c, d, a, b, x[7], 14, 0x676f02d9);
		b = GG(b, c, d, a, x[12], 20, 0x8d2a4c8a);

		// Round 3
		a = HH(a, b, c, d, x[5], 4, 0xfffa3942);
		d = HH(d, a, b, c, x[8], 11, 0x8771f681);
		c = HH(c, d, a, b, x[11], 16, 0x6d9d6122);
		b = HH(b, c, d, a, x[14], 23, 0xfde5380c);
		a = HH(a, b, c, d, x[1], 4, 0xa4beea44);
		d = HH(d, a, b, c, x[4], 11, 0x4bdecfa9);
		c = HH(c, d, a, b, x[7], 16, 0xf6bb4b60);
		b = HH(b, c, d, a, x[10], 23, 0xbebfbc70);
		a = HH(a, b, c, d, x[13], 4, 0x289b7ec6);
		d = HH(d, a, b, c, x[0], 11, 0xeaa127fa);
		c = HH(c, d, a, b, x[3], 16, 0xd4ef3085);
		b = HH(b, c, d, a, x[6], 23, 0x04881d05);
		a = HH(a, b, c, d, x[9], 4, 0xd9d4d039);
		d = HH(d, a, b, c, x[12], 11, 0xe6db99e5);
		c = HH(c, d, a, b, x[15], 16, 0x1fa27cf8);
		b = HH(b, c, d, a, x[2], 23, 0xc4ac5665);

		// Round 4
		a = II(a, b, c, d, x[0], 6, 0xf4292244);
		d = II(d, a, b, c, x[7], 10, 0x432aff97);
		c = II(c, d, a, b, x[14], 15, 0xab9423a7);
		b = II(b, c, d, a, x[5], 21, 0xfc93a039);
		a = II(a, b, c, d, x[12], 6, 0x655b59c3);
		d = II(d, a, b, c, x[3], 10, 0x8f0ccc92);
		c = II(c, d, a, b, x[10], 15, 0xffeff47d);
		b = II(b, c, d, a, x[1], 21, 0x85845dd1);
		a = II(a, b, c, d, x[8], 6, 0x6fa87e4f);
		d = II(d, a, b, c, x[15], 10, 0xfe2ce6e0);
		c = II(c, d, a, b, x[6], 15, 0xa3014314);
		b = II(b, c, d, a, x[13], 21, 0x4e0811a1);
		a = II(a, b, c, d, x[4], 6, 0xf7537e82);
		d = II(d, a, b, c, x[11], 10, 0xbd3af235);
		c = II(c, d, a, b, x[2], 15, 0x2ad7d2bb);
		b = II(b, c, d, a, x[9], 21, 0xeb86d391);

		state[0] += a;
		state[1] += b;
		state[2] += c;
		state[3] += d;
		return state;
	}

	private int functionF(int x, int y, int z) {
		int result;
		result = ((x & y) | (~x & z));
		return result;
	}

	private int functionG(int x, int y, int z) {
		int result;
		result = ((x & z) | (y & ~z));
		return result;
	}

	private int functionH(int x, int y, int z) {
		int result;
		result = (x ^ y ^ z);
		return result;
	}

	private int functionI(int x, int y, int z) {
		int result;
		result = (y ^ (x | ~z));
		return result;
	}

	private int FF(int a, int b, int c, int d, int x, int s, int t) {
		a += functionF(b, c, d);
		a += x;
		a += t;
		a = (a << s) | (a >>> (32 - s));
		a += b;

		return a;
	}

	private int GG(int a, int b, int c, int d, int x, int s, int t) {
		a += functionG(b, c, d);
		a += x;
		a += t;
		a = (a << s) | (a >>> (32 - s));
		a += b;
		return a;
	}

	private int HH(int a, int b, int c, int d, int x, int s, int t) {
		a += functionH(b, c, d);
		a += x;
		a += t;
		a = (a << s) | (a >>> (32 - s));
		a += b;
		return a;
	}

	private int II(int a, int b, int c, int d, int x, int s, int t) {
		a += functionI(b, c, d);
		a += x;
		a += t;
		a = (a << s) | (a >>> (32 - s));
		a += b;
		return a;
	}

	/**
	 * Step five output
	 */
	public String outputResult() {
		String result = "";
		byte[] resultbytes = intsToBytes(state, 16);
		result = bytesToHex(resultbytes);
		return result;
	}

	private byte[] intsToBytes(int input[], int len) {
		byte[] result = new byte[len];
		int i, j;
		for (i = j = 0; j < len; i++, j += 4) {
			result[j] = (byte) (input[i] & 0xff);
			result[j + 1] = (byte) ((input[i] >>> 8) & 0xff);
			result[j + 2] = (byte) ((input[i] >>> 16) & 0xff);
			result[j + 3] = (byte) ((input[i] >>> 24) & 0xff);
		}
		return result;
	}

	private String bytesToHex(byte hash[]) {
		StringBuffer buf = new StringBuffer(hash.length * 2);
		for (byte element : hash) {
			int intVal = element & 0xff;
			if (intVal < 0x10) {
				buf.append("0");
			}
			buf.append(Integer.toHexString(intVal));
		}
		return buf.toString();
	}

}
