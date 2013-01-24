package Stan.src.Stan.MD5;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * This is Class for MD5 Algorithm,
 * 
 * MD5 Algorithm have five steps, 1. Append Padding Bits 2. Append length 3.
 * Initialize MD Buffer 4. Process Message in 16-word Block 5. Output
 * 
 * @author stanchen
 * 
 */
public class MD5Interface {
	private MD5Core md5core;

	public MD5Interface() {
		md5core = new MD5Core();
	}

	/**
	 * This is read file method.
	 * 
	 * @throws IOException
	 * 
	 */
	public static void main(String[] args) throws IOException {

		// String file_location = "test";
		// MD5Interface md5 = new MD5Interface();
		// md5.readFileToByte(new File(file_location));
		String message = "adf1ienwlinvienllwiilslsdoofsnownoa;oe";
		MD5Interface md5 = new MD5Interface();
		System.out.println(md5.readString(message));
	}

	public String readFileToByte(File f) throws IOException {
		InputStream is = new FileInputStream(f);
		String hash = getHashString(is);
		is.close();
		return hash;
	}

	public String readString(String str) throws IOException {
		InputStream is = new ByteArrayInputStream(str.getBytes());
		String hash = getHashString(is);
		is.close();
		return hash;
	}

	/**
	 * Read the Input Stream to byte array and the length of message in length
	 * 
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public String getHashString(InputStream in) throws IOException {
		byte[] buffer = new byte[1024];
		int length;
		while ((length = in.read(buffer)) != -1) {
			byte[] result = md5core.appendPadding(buffer, length);
			byte[] test = md5core.appendLength(result, (long) length);
			md5core.initHash(test);
			System.out.println("Result: " + md5core.outputResult());

		}

		return md5core.outputResult();
	}

}
