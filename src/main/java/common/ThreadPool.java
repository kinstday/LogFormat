package common;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.time.LocalTime;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPool {


	private int corePoolSize = 5;

	private int maximumPoolSize = 20;

	private long keepAliveTime = 180000;

	private int queueCapacity = 100;

	private boolean allowCoreThreadTimeOut = true;

	private ThreadPoolExecutor threadPoolExecutor;

	public ThreadPool() {
		this.initThreadPool();
	}

	public void initThreadPool() {
		threadPoolExecutor = new ThreadPoolExecutor(corePoolSize,
				maximumPoolSize, keepAliveTime, TimeUnit.MILLISECONDS,
				new ArrayBlockingQueue<Runnable>(queueCapacity),
				// 设置线程池内创建的线程名称
				new ThreadFactoryBuilder().setNameFormat("ThreadPool-%d").build(),
				new ThreadPoolExecutor.CallerRunsPolicy());
		// 允许核心线程过期
		threadPoolExecutor.allowCoreThreadTimeOut(allowCoreThreadTimeOut);
	}


	public void execute(Runnable runnable) throws InterruptedException {
		Thread.sleep((long) (Math.random() * 100));
		threadPoolExecutor.execute(runnable);
	}

	public int getCorePoolSize() {
		return corePoolSize;
	}

	public void setCorePoolSize(int corePoolSize) {
		this.corePoolSize = corePoolSize;
	}

	public int getMaximumPoolSize() {
		return maximumPoolSize;
	}

	public void setMaximumPoolSize(int maximumPoolSize) {
		this.maximumPoolSize = maximumPoolSize;
	}

	public long getKeepAliveTime() {
		return keepAliveTime;
	}

	public void setKeepAliveTime(long keepAliveTime) {
		this.keepAliveTime = keepAliveTime;
	}

	public int getQueueCapacity() {
		return queueCapacity;
	}

	public void setQueueCapacity(int queueCapacity) {
		this.queueCapacity = queueCapacity;
	}

	public boolean isAllowCoreThreadTimeOut() {
		return allowCoreThreadTimeOut;
	}

	public void setAllowCoreThreadTimeOut(boolean allowCoreThreadTimeOut) {
		this.allowCoreThreadTimeOut = allowCoreThreadTimeOut;
	}

	public ThreadPoolExecutor getThreadPoolExecutor() {
		return threadPoolExecutor;
	}

	public void setThreadPoolExecutor(ThreadPoolExecutor threadPoolExecutor) {
		this.threadPoolExecutor = threadPoolExecutor;
	}
}