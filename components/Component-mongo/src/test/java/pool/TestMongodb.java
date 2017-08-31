package pool;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import com.fast.dev.component.mongo.MongoStore;
import com.fast.dev.component.mongo.MongoStoreFactory;
import com.fast.dev.component.mongo.model.MongoConfig;
import com.fast.dev.core.util.code.JsonUtil;

public class TestMongodb {

	public static void main(String[] args) throws Exception {

		MongoConfig mongoConfig = new MongoConfig();
		mongoConfig.setDbName("test");
		mongoConfig.setHost(new String[] { "127.0.0.1:27017", "127.0.0.1:27018", "127.0.0.1:27019", "127.0.0.1:27020",
				"127.0.0.1:27021", "127.0.0.1:27022", "127.0.0.1:27023" });

		// mongoConfig.setHost(new String[] {"10.13.42.6"
		// ,"10.13.56.37","10.13.64.227","10.13.34.253","10.13.58.13"});
		mongoConfig.setUserName("test");
		mongoConfig.setPassWord("test");
		mongoConfig.setTimeOut(5000);

		MongoStoreFactory mongoComponentFactory = new MongoStoreFactory();
		MongoStore mongoStore = mongoComponentFactory.create(mongoConfig);

		MongoTemplate mongoTemplate = mongoStore.getMongoTemplate();

		// for (int i = 0; i < 10; i++) {
		// mongoTemplate.save(new Model("test"+i));
		// }

		for (int i = 0; i < 1000; i++) {
			try {
				mongoTemplate.dropCollection(Model.class);
				mongoTemplate.save(new Model("test" + i));
				System.out.println(JsonUtil.toJson(mongoTemplate.find(new Query(), Model.class)));
				Thread.sleep(1000l);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
