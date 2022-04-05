package app;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import service.impl.FormatServiceImpl;

import java.util.Scanner;

public class LogFormat {

	public static void main(String[] args) throws JSchException {

		FormatServiceImpl formatService = new FormatServiceImpl();

		Scanner input = new Scanner(System.in);
		System.out.println("输入host地址：");
		String host = input.nextLine();
		System.out.println("输入端口");
		String port = input.nextLine();
		System.out.println("输入用户名：");
		String userName = input.nextLine();
		System.out.println("输入密码：");
		String password = input.nextLine();

		Session session = formatService.sshLogin(host, port, userName, password);
		if (session != null) {
			for (int i = 0; i < 100000; i++) {
				formatService.doCommand(session);
				if (i % 1000 == 0 && i != 0) {
					System.out.println(i);
					formatService.SystemMemoryTotal();
				}
			}
			session.disconnect();
			}

		System.out.println("结束连接");
	}
}
