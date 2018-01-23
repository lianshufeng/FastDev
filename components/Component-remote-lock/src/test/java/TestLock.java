import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.fast.dev.component.remote.lock.config.RemoteLockOption;
import com.fast.dev.component.remote.lock.impl.RemoteLockZooKeeper;
import com.fast.dev.core.lock.factory.RemoteLock;
import com.fast.dev.core.lock.factory.RemoteLockFactory;

public class TestLock {

	// 最大线程池数量
	private static int maxThreadCount = 3000;
	// 最大运行次数
	private static int maxRunCount = 100000;
	private static CountDownLatch countDownLatch = new CountDownLatch(maxRunCount);
	static RemoteLock remoteLock = null;

	public static void main(String[] args) throws Exception {
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(maxThreadCount);
		remoteLock = RemoteLockFactory.build(RemoteLockZooKeeper.class,
				new RemoteLockOption(new String[] { "127.0.0.1:2181" }));

		long time = System.currentTimeMillis();

		for (int i = 0; i < maxRunCount; i++) {
			final String resourcesName = "test_resources";
			fixedThreadPool.execute(new Runnable() {
				@Override
				public void run() {
					runLock(resourcesName);
				}
			});
		}

		countDownLatch.await();
		fixedThreadPool.shutdownNow();
		long castTime = System.currentTimeMillis() - time;
		System.out.println("run count  : " + maxRunCount + " time : " + (castTime) + " , qps : "
				+ (((double) maxRunCount / (double) (castTime)) * 1000));
	}

	private static void runLock(String resourcesName) {
		try {
			boolean lock = remoteLock.lock(resourcesName);
			if (lock) {
				System.out.println("token thread : " + Thread.currentThread());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		countDownLatch.countDown();
	}
}
