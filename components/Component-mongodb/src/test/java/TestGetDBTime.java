import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.fast.dev.component.mongodb.helper.time.DBTimerHelper;

public class TestGetDBTime {

	final static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(20);

	public static void main(String[] args) {

		String[] scanPackage = new String[] { "com.fast.dev" };
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
		applicationContext.scan(scanPackage);
		applicationContext.refresh();
		// 时间请求
		final DBTimerHelper dbTimerHelper = applicationContext.getBean(DBTimerHelper.class);

		for (int i = 0; i < 10000; i++) {
			fixedThreadPool.execute(new Runnable() {
				@Override
				public void run() {
					System.out.println(dbTimerHelper.getDBTime());
					;
					try {
						Thread.sleep(100);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});

		}

	}
}
