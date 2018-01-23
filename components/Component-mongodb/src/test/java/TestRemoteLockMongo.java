
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.fast.dev.core.lock.factory.RemoteLock;

public class TestRemoteLockMongo {

	static int maxTheadPool = 200;
	static int maxRunCount = 50000;
	final static CountDownLatch countDownLatch = new CountDownLatch(maxRunCount);
	final static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(maxTheadPool);

	public static void main(String[] args) throws Exception {

		String[] scanPackage = new String[] { "com.fast.dev" };
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
		applicationContext.scan(scanPackage);
		applicationContext.refresh();

		long createTime = System.currentTimeMillis();
		// 拿取到实现对象
		final RemoteLock remoteLock = applicationContext.getBean(RemoteLock.class);
		for (int i = 0; i < maxRunCount; i++) {
			final int index = i;
			fixedThreadPool.execute(new Runnable() {
				@Override
				public void run() {
					runTask(remoteLock, index);
					countDownLatch.countDown();
				}
			});

		}
		countDownLatch.await();
		fixedThreadPool.shutdownNow();
		long castTime = System.currentTimeMillis() - createTime;
		System.out.println("count : " + maxRunCount + " , 耗时：" + castTime + ", QPS : "
				+ (((double) maxRunCount / (double) (castTime)) * 1000));

//		Thread.sleep(10000);
//		System.exit(0);

	}

	/**
	 * 运行任务
	 * 
	 * @param remoteLock
	 * @param index
	 */
	private static void runTask(RemoteLock remoteLock, int index) {
		try {
			String token = "test111" ;
			if (remoteLock.lock(token)) {
				System.out.println("token : " + Thread.currentThread());
//				remoteLock.unLock(token);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
