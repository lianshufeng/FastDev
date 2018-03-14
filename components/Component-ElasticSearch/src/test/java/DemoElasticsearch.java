import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkProcessor.Listener;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("resource")
public class DemoElasticsearch {

	private Logger logger = LoggerFactory.getLogger(DemoElasticsearch.class);

	public final static String HOST = "127.0.0.1";

	public final static int PORT = 9300; // http请求的端口是9200，客户端是9300

	private TransportClient client = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		client = new PreBuiltTransportClient(Settings.EMPTY)
				.addTransportAddresses(new TransportAddress(new InetSocketAddress(HOST, PORT)));
		logger.debug("连接信息:" + client.toString());
	}

	@After
	public void tearDown() throws Exception {
		if (null != client) {
			logger.debug("执行关闭连接操作...");
			client.close();
		}
	}

	/**
	 * 创建索引库
	 * 
	 * @Title: addIndex1
	 * @author sunt
	 * @date 2017年11月23日
	 * @return void 需求:创建一个索引库为：msg消息队列,类型为：tweet,id为1 索引库的名称必须为小写
	 * @throws IOException
	 */
	@Test
	public void addIndex1() throws IOException {
		IndexResponse response = client.prepareIndex("msg", "tweet", "1").setSource(XContentFactory.jsonBuilder()
				.startObject().field("userName", "张三").field("sendDate", new Date()).field("msg", "你好李四").endObject())
				.get();

		logger.info("索引名称:" + response.getIndex() + "\t类型:" + response.getType() + "\t文档ID:" + response.getId()
				+ "\t当前实例状态:" + response.status());
	}

	/**
	 * 添加索引:传入json字符串
	 * 
	 * @Title: addIndex2
	 * @author sunt
	 * @date 2017年11月23日
	 * @return void
	 */
	@Test
	public void addIndex2() {
		String jsonStr = "{\"userName\":\"张三\",\"sendDate\":\"2017-11-30\",\"msg\":\"你好李四\"}";
		IndexResponse response = client.prepareIndex("weixin", "tweet").setSource(jsonStr, XContentType.JSON).get();
		logger.info("json索引名称:" + response.getIndex() + "\tjson类型:" + response.getType() + "\tjson文档ID:"
				+ response.getId() + "\t当前实例json状态:" + response.status());
	}

	/**
	 * 创建索引-传入Map对象
	 * 
	 * @Title: addIndex3
	 * @author sunt
	 * @date 2017年11月23日
	 * @return void
	 */
	@Test
	public void addIndex3() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userName", "张三");
		map.put("sendDate", new Date());
		map.put("msg", "你好李四");
		IndexResponse response = client.prepareIndex("momo", "tweet").setSource(map).get();
		logger.info("map索引名称:" + response.getIndex() + "\t map类型:" + response.getType() + "\t map文档ID:"
				+ response.getId() + "\t当前实例map状态:" + response.status());
	}

	/**
	 * 从索引库获取数据
	 * 
	 * @Title: getData1
	 * @author sunt
	 * @date 2017年11月23日
	 * @return void
	 */
	@Test
	public void getData1() {
		GetResponse getResponse = client.prepareGet("msg", "tweet", "1").get();
		logger.info("索引库的数据:" + getResponse.getSourceAsString());
	}

	/**
	 * 更新索引库数据
	 * 
	 * @Title: updateData
	 * @author sunt
	 * @date 2017年11月23日
	 * @return void
	 */
	@Test
	public void updateData() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userName", "王五");
		map.put("sendDate", "2008-08-08");
		map.put("msg", "你好,张三，好久不见");

		UpdateResponse updateResponse = client.prepareUpdate("msg", "tweet", "1").setDoc(map).get();

		logger.info("updateResponse索引名称:" + updateResponse.getIndex() + "\t updateResponse类型:"
				+ updateResponse.getType() + "\t updateResponse文档ID:" + updateResponse.getId()
				+ "\t当前实例updateResponse状态:" + updateResponse.status());
	}

	/**
	 * 根据索引名称，类别，文档ID 删除索引库的数据
	 * 
	 * @Title: deleteData
	 * @author sunt
	 * @date 2017年11月23日
	 * @return void
	 */
	@Test
	public void deleteData() {
		DeleteResponse deleteResponse = client.prepareDelete("msg", "tweet", "1").get();
		logger.info("deleteResponse索引名称:" + deleteResponse.getIndex() + "\t deleteResponse类型:"
				+ deleteResponse.getType() + "\t deleteResponse文档ID:" + deleteResponse.getId()
				+ "\t当前实例deleteResponse状态:" + deleteResponse.status());
	}

	@Test
	public void testBulk() throws Exception {
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		bulkRequest.add(client.prepareIndex("twitter", "tweet", "1")
				.setSource(XContentFactory.jsonBuilder().startObject().field("user", "kimchy")
						.field("postDate", new Date()).field("message", "trying out Elasticsearch").endObject()));
		bulkRequest.add(client.prepareIndex("twitter", "tweet", "2")
				.setSource(XContentFactory.jsonBuilder().startObject().field("user", "kimchy")
						.field("postDate", new Date()).field("message", "another post").endObject()));
		BulkResponse response = bulkRequest.get();

		logger.info("testBulk:" + response.getIngestTookInMillis() + "\t当前实例testBulk状态:" + response.status());
	}

	@Test
	public void testBulkProcessor() throws Exception {
		// 创建BulkPorcessor对象
		BulkProcessor bulkProcessor = BulkProcessor.builder(client, new Listener() {
			public void beforeBulk(long paramLong, BulkRequest paramBulkRequest) {
				// TODO Auto-generated method stub
			}

			// 执行出错时执行
			public void afterBulk(long paramLong, BulkRequest paramBulkRequest, Throwable paramThrowable) {
				// TODO Auto-generated method stub
			}

			public void afterBulk(long paramLong, BulkRequest paramBulkRequest, BulkResponse paramBulkResponse) {
				// TODO Auto-generated method stub
			}
		})
				// 1w次请求执行一次bulk
				.setBulkActions(10000)
				// 1gb的数据刷新一次bulk
				.setBulkSize(new ByteSizeValue(1, ByteSizeUnit.GB))
				// 固定5s必须刷新一次
				.setFlushInterval(TimeValue.timeValueSeconds(5))
				// 并发请求数量, 0不并发, 1并发允许执行
				.setConcurrentRequests(1)
				// 设置退避, 100ms后执行, 最大请求3次
				.setBackoffPolicy(BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3)).build();

		// 添加单次请求
		bulkProcessor.add(new IndexRequest("twitter", "tweet", "1"));
		bulkProcessor.add(new DeleteRequest("twitter", "tweet", "2"));

		// 关闭
		bulkProcessor.awaitClose(10, TimeUnit.MINUTES);
		// 或者
		bulkProcessor.close();
	}

}
