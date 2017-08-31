
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.fast.dev.component.mongo.MongoStore;
import com.fast.dev.component.mongo.MongoStoreFactory;
import com.fast.dev.component.mongo.model.MongoConfig;
import com.fast.dev.component.taskpool.TaskPoolFactory;
import com.fast.dev.component.taskpool.model.TaskPoolConfig;
import com.fast.dev.component.taskpool.pool.TaskExecutor;
import com.fast.dev.component.taskpool.pool.TaskManager;
import com.fast.dev.component.taskpool.pool.TaskParameter;
import com.fast.dev.core.util.code.JsonUtil;

public class TestMain {

	// 校验任务是否被重复调用
	final static Map<String, Boolean> tasks = new ConcurrentHashMap<String, Boolean>();

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		MongoConfig mongoConfig = new MongoConfig();
		mongoConfig.setHost(new String[] { "127.0.0.1:27017" });
		mongoConfig.setDbName("task");
		// 生成mongo连接池
		MongoStore mongoStore = new MongoStoreFactory().create(mongoConfig);
		// 任务池的配置
		TaskPoolConfig taskPoolConfig = new TaskPoolConfig();
		taskPoolConfig.setMongoTemplate(mongoStore.getMongoTemplate());
		taskPoolConfig.setMaxRunCount(100);
		taskPoolConfig.setMaxLoadTaskCount(100);
		taskPoolConfig.setMinBeginLoadCount(60);
		taskPoolConfig.setTaskWorkTimeOut(20 * 1000);
		taskPoolConfig.setCoreTime(1 * 1000);
		taskPoolConfig.setTaskExecutor(new TaskExecutor() {

			// 该任务被执行
			@Override
			public void execute(TaskParameter task) {
				TaskParameterImpl taskParameterImpl = (TaskParameterImpl) task;
				if (tasks.containsKey(taskParameterImpl.getTaskId())) {
					System.err.println("警告：该任务被重复执行 , " + taskParameterImpl.getTaskId());
					return;
				}

				try {
					int sleep = 2 * (int) (Math.random() * 10000);
					Thread.sleep(sleep);
					tasks.put(taskParameterImpl.getTaskId(), true);
					System.out.println(JsonUtil.toJson(task));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		TaskManager taskManager = new TaskPoolFactory().create(taskPoolConfig);

		// 执行任务
		for (int i = 0; i < 0; i++) {
			TaskParameterImpl task = new TaskParameterImpl();
			task.setFile("c:/1.rar");
			task.setTaskId("uuuid_xiaofeng_" + i);
			taskManager.executeTask(task);
		}

		// 销毁任务
		try {
			Thread.sleep(600 * 1000);
			taskManager.shutdown();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
}
