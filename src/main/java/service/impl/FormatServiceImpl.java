package service.impl;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import common.Context;
import service.FormatService;

import java.io.*;
import java.text.SimpleDateFormat;



/**
 * @author kinst
 */
public class FormatServiceImpl implements FormatService {

	final private static String CHARSET_CN = "GB18030";
	final private static String UTF_8 = "UTF-8";
	final private String regex = "[\\u4e00-\\u9fa5]";

	@Override
	public Session sshLogin(String host, String port, String userName, String password) {
		Session session = null;
		try {
			System.out.println("链接到" + host + ":" + port + ",用户名：" + userName + ",密码：" + password);
			JSch jsch = new JSch();
			session = jsch.getSession(userName, host, Integer.parseInt(port));
			session.setPassword(password);
			session.setConfig("StrictHostKeyChecking", "no");
			session.setTimeout(6000);
			session.connect();

		} catch (JSchException e) {
			System.out.println("建立连接失败！");
			e.printStackTrace();
		}
		return session;
	}

	@Override
	public synchronized void readImputStream(InputStream in, String CHARSET) {

		try (OutputStream out = new FileOutputStream("test.csv")) {
			byte[] buf = new byte[1013];
			byte[] remain = new byte[1013];
			byte[] end = new byte[10];
			int len;
			int split = 1;
			boolean readAll = false;

			while ((len = in.read(buf)) != -1) {
				Context context = new Context(split);
				if (remain.length == 1013 || readAll) {
					context.setRes(new String(buf, 0, len, CHARSET));
				} else {
					buf = byteMerger(remain, buf, len);
					len = buf.length;
					context.setRes(new String(buf, 0, len, CHARSET));
				}

				while (context.getEnd() < context.getRes().length()) {
					char index = context.getRes().charAt(context.getEnd());

					if (String.valueOf(index).matches(regex)) {
						context.addCnNumber();
					} else if ('|' == index) {
						if (context.getSplit() == 1) {
							createDateFormat(context);
						} else if (context.getSplit() == 2) {
							context.setStart(context.getEnd() + 1);
							context.setPassNumber(context.getCnNumber() * 2 + context.getOtherNumber() + 1);
						}
						context.addOtherNumber();
					} else if (';' == index) {
						setMessageAndNewline(context);
						context.addOtherNumber();
					} else {
						context.addOtherNumber();
					}
					if (context.getEnd() == context.getRes().length() - 1) {
						remain = checkRemainByte(context, buf, CHARSET);
						split = context.getSplit();
						readAll = false;
					} else {
						readAll = true;
					}
					context.setEnd(context.getEnd() + 1);
				}
				byte[] outStream = context.getSb().toString().getBytes(CHARSET);
				out.write(outStream, 0, outStream.length);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void doCommand(Session session) throws JSchException {
		ChannelExec exec = (ChannelExec) session.openChannel("exec");
		try (InputStream in = exec.getInputStream()) {
//			exec.setCommand("cat test.log");
			exec.setCommand("cmd /c type test.log");
			exec.connect();
			this.readImputStream(in, CHARSET_CN);
			exec.disconnect();
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void SystemMemoryTotal() {
		Long totalMemory = Runtime.getRuntime().totalMemory() / 1048576;
		Long maxMemory = Runtime.getRuntime().maxMemory() / 1048576;

		System.out.println("总共内存：" + maxMemory + "MB，已经占用：" + totalMemory + "MB，剩余内存：" + (maxMemory - totalMemory) + "MB");
	}

	private void createDateFormat(Context context) {
		String time = context.getRes().substring(context.getStart(), context.getEnd());
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			String dateString = dateformat.format(Long.parseLong(time));
			context.getSb().append(dateString);
		} catch (NumberFormatException e) {
//			System.out.println(e.getMessage());
		}
		context.setStart(context.getEnd() + 1);
		context.setPassNumber(context.getCnNumber() * 2 + context.getOtherNumber() + 1);
		context.setSplit(2);
	}

	private void setMessageAndNewline(Context context) {
		if (context.getSplit() == 2) {
			context.getSb().append(",");
			context.getSb().append(context.getRes().substring(context.getStart(), context.getEnd()));
		}
		context.getSb().append("\r\n");
		context.setStart(context.getEnd() + 1);
		context.setPassNumber(context.getCnNumber() * 2 + context.getOtherNumber() + 1);
		context.setSplit(1);
	}

	private byte[] checkRemainByte(Context context, byte[] buf, String CHARSET) throws UnsupportedEncodingException {

		int len = buf.length-context.getPassNumber();
		byte [] ans = new byte[len];
		System.arraycopy(buf,context.getPassNumber(),ans,0,len);
		return ans;
	}

	private byte[] byteMerger(byte[] remain, byte[] buf, int len) throws UnsupportedEncodingException {

		byte[] ans = new byte[len + remain.length];
		System.arraycopy(remain, 0, ans, 0, remain.length);
		System.arraycopy(buf, 0, ans, remain.length, len);
		return ans;
	}

}

