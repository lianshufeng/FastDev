import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.fast.dev.component.remote.lock.config.RemoteLockOption;
import com.fast.dev.component.remote.lock.impl.RemoteLockZooKeeper;
import com.fast.dev.core.lock.factory.SyncToken;
import com.fast.dev.core.lock.factory.RemoteLock;
import com.fast.dev.core.lock.factory.RemoteLockFactory;

public abstract class Demo {

	static Long count = 0l;
	static RemoteLock remoteLock = null;
	CountDownLatch countDownLatch = null;

	public void run() throws Exception {

		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(maxTheadPool());

		countDownLatch = new CountDownLatch((int) maxRunCount());

		RemoteLockOption option = new RemoteLockOption();
		option.setHostNames(new String[] { host() });
		remoteLock = RemoteLockFactory.build(RemoteLockZooKeeper.class, option);

		for (int i = 0; i < maxRunCount(); i++) {
			final Integer index = i;
			fixedThreadPool.execute(new Runnable() {
				@Override
				public void run() {
					runLock(index);
				}
			});
		}

		countDownLatch.await();
		fixedThreadPool.shutdownNow();
		Thread.sleep(300);
		System.out.println("count :" + count + ", class : " + this.getClass());
	}

	private void runLock(final Integer index) {
		SyncToken lockToken = null;
		try {
			lockToken = remoteLock.sync("test");
			if (sleepTime() > 0) {
				Thread.sleep(sleepTime());
			}

			count++;

			System.out.println("执行:" + index + " time : " + System.currentTimeMillis() + " count : " + count);

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if (lockToken != null) {
				lockToken.release();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		countDownLatch.countDown();
	}

	/**
	 * 每次执行延迟时间
	 * 
	 * @return
	 */
	public long sleepTime() {
		return 0;
	};

	/**
	 * 运行次数
	 * 
	 * @return
	 */
	public long maxRunCount() {
		return 3000;
	};

	/**
	 * 主机
	 * 
	 * @return
	 */
	public String host() {
		return "127.0.0.1:2181";
	}

	/**
	 * 最大线程数
	 * 
	 * @return
	 */
	public int maxTheadPool() {
		return 30;
	}

}
