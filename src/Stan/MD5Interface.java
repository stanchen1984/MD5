package Stan;

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
	static long start;
	
	public static void main(String[] args) throws IOException {
		start = System.currentTimeMillis();
		String file_location = "GameOfThrones.mp4";
		MD5Interface md5 = new MD5Interface();
		md5.readFileToByte(new File(file_location));
	}

	public String readFileToByte(File f) throws IOException {
		InputStream is = new FileInputStream(f);
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
		md5core.initHash();
		long total_length = 0;
		while ((length = in.read(buffer)) != -1) {
			if (length < 1024) {
				total_length += length;
				byte[] result = md5core.appendPadding(buffer, length);
				byte[] test = md5core.appendLength(result, total_length);
				md5core.hashProcess(test);
				md5core.outputResult();
			}else{
				total_length += 1024;
				md5core.hashProcess(buffer);
			}
		}
		System.out.println("Result: " + md5core.outputResult());
		long end_point = System.currentTimeMillis();
		System.out.println("Time used (seconds) : " + (end_point - start)/1000);
		return "aaa";
	}

}
