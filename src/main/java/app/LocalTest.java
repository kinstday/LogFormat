package app;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import common.ThreadPool;
import service.impl.FormatServiceImpl;

public class LocalTest extends ThreadPool {

	public static void main(String[] args) throws JSchException, InterruptedException {
		FormatServiceImpl formatService = new FormatServiceImpl();
		ThreadPool threadPool = new ThreadPool();
//		Session session = formatService.sshLogin("127.0.0.1", "40", "root", "000000");
		Session session = formatService.sshLogin("192.168.3.34", "40", "root", "000000");
		if (session != null) {
			for (int index = 0; index < 100000; index++) {
				threadPool.execute(new ThreadWorker(formatService, session, index));
//				formatService.doCommand(session);
			}
			session.disconnect();
		}
		System.out.println("结束连接");
	}
	private static final class ThreadWorker implements Runnable {
		private FormatServiceImpl formatService;
		private Session session;
		private int index;

		public ThreadWorker(FormatServiceImpl formatService, Session session,int index){
			this.formatService = formatService;
			this.session = session;
			this.index = index;
		}

		@Override
		public void run() {
			try {
				formatService.doCommand(session);
				System.out.println(index);
				if (index % 1000 == 0) {
					formatService.SystemMemoryTotal();
				}
			} catch (Exception e) {
				// do nothing
			}
		}
	}

}
