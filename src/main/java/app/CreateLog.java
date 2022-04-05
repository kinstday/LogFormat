package app;


import java.io.*;

public class CreateLog {

	final private static String CHARSET_CN = "GB18030";

	public static void main(String[] args) throws IOException {

		try (OutputStream out = new FileOutputStream("test.log")) {
			int i = 0;
			while (i < 100) {
				String res = "1595830781357|李四|测试网元服务器" + i + "千万人大气污染的气味犬瘟热犬瘟热去器人爱瑞发二分其恶趣味请问请问驱蚊器爱上范德萨阿萨大大阿达萨达犬瘟热犬瘟热请问案发时;";
				byte[] outStream = res.getBytes(CHARSET_CN);
				i++;
				out.write(outStream, 0, outStream.length);
			}
		}
	}
}
