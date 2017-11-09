import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.fast.dev.core.util.code.JsonUtil;
import com.fast.dev.core.util.net.HttpClient;

public class TestSecurity {

	private static Long bigTime = 0l;
	private static Long successCount = 0l;

	public static void main(String[] args) throws Exception {

		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(100);

		request(true);

		long time = System.currentTimeMillis();
		int threadCount = 50000;
		final CountDownLatch countDownLatch = new CountDownLatch(threadCount);

		for (int i = 0; i < threadCount; i++) {
			fixedThreadPool.execute(new Runnable() {
				@Override
				public void run() {
					try {
						request(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
					countDownLatch.countDown();
				}
			});
		}

		countDownLatch.await();

		fixedThreadPool.shutdown();

		System.out.println("cost : " + (System.currentTimeMillis() - time));
		System.out.println("bigTime : " + (bigTime));
		System.out.println("successCount : " + (successCount));
	}

	@SuppressWarnings("unchecked")
	private static void request(boolean hasUToken) throws Exception {
		
		byte[] bin = new HttpClient().get("http://127.0.0.1:8080/PServer/user/info.json"
				+ (hasUToken ? "?_uToken=" + UUID.randomUUID().toString() : ""));
		String buf = new String(bin);
		Map<String, Object> map = JsonUtil.toObject(buf, Map.class);
		map = (Map<String, Object>) map.get("invokerResult");
		long usedTime = Long.valueOf(String.valueOf(map.get("usedTime")));
		synchronized (bigTime) {
			if (bigTime < usedTime) {
				bigTime = usedTime;
			}
		}
		synchronized (successCount) {
			boolean success = Boolean.valueOf(String.valueOf(map.get("success")));
			if (success) {
				successCount++;
			} else {
				System.out.println("error:" + buf);
			}
		}
	}

}
