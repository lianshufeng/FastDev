import java.net.InetSocketAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequestBuilder;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DemoFullTextQuery {
	private TransportClient client = null;
	private Logger logger = LoggerFactory.getLogger(DemoFullTextQuery.class);
	public final static String HOST = "127.0.0.1";

	public final static int PORT = 9300; // http请求的端口是9200，客户端是9300

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	@SuppressWarnings("resource")
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

	private final static String IndexName = "test_index";

	private Map<String, Object> createSource(String info) {
		Map<String, Object> m = new HashMap<>();
		m.put("time", new Date().getTime());
		m.put("info", info);
		return m;
	}

	// @Test
	public void createIndex() {

		
		IndexResponse response = client.prepareIndex(IndexName, "fulltext").setSource(createSource("我们都是中国人")).get();
		System.out.println("索引名称:" + response.getIndex() + "\t类型:" + response.getType() + "\t文档ID:" + response.getId()
				+ "\t当前实例状态:" + response.status());

		response = client.prepareIndex(IndexName, "fulltext").setSource(createSource("你中间中中国的节奏")).get();
		System.out.println("索引名称:" + response.getIndex() + "\t类型:" + response.getType() + "\t文档ID:" + response.getId()
				+ "\t当前实例状态:" + response.status());

		response = client.prepareIndex(IndexName, "fulltext").setSource(createSource("中韩渔警冲突调查：韩警平均每天扣1艘中国渔船")).get();
		System.out.println("索引名称:" + response.getIndex() + "\t类型:" + response.getType() + "\t文档ID:" + response.getId()
				+ "\t当前实例状态:" + response.status());

		response = client.prepareIndex(IndexName, "fulltext").setSource(createSource("中国驻洛杉矶领事馆遭亚裔男子枪击 嫌犯已自首")).get();
		System.out.println("索引名称:" + response.getIndex() + "\t类型:" + response.getType() + "\t文档ID:" + response.getId()
				+ "\t当前实例状态:" + response.status());

	}

	// @Test
	public void query() throws Exception {
		SearchRequestBuilder requestBuilder = client.prepareSearch(IndexName);
		requestBuilder.setTypes("fulltext");
		// 检索条件
		requestBuilder.setQuery(QueryBuilders.matchPhraseQuery("info", "中国人"));
		// 高亮
		HighlightBuilder highlightBuilder = new HighlightBuilder();
		highlightBuilder.field("info");
		highlightBuilder.preTags("[");
		highlightBuilder.postTags("]");
		requestBuilder.highlighter(highlightBuilder);

		SearchResponse responsebuilder = requestBuilder.get();
		SearchHits searchHits = responsebuilder.getHits();
		for (SearchHit searchHit : searchHits.getHits()) {
			Map<String, Object> sources = searchHit.getSourceAsMap();
			HighlightField highlightField = searchHit.getHighlightFields().get("info");
			System.out.println(sources + " -> " + highlightField.getFragments()[0]);
		}
		System.out.println("query");
	}

	// @Test
	public void fromSize() throws Exception {
		SearchRequestBuilder requestBuilder = client.prepareSearch(IndexName);
		// 类型
		requestBuilder.setTypes("fulltext");
		// 排序
		requestBuilder.addSort("time", SortOrder.DESC);
		// 检索方式
		requestBuilder.setSearchType(SearchType.DEFAULT);
		// 分页
		requestBuilder.setFrom(1);
		requestBuilder.setSize(3);
		requestBuilder.setTimeout(new TimeValue(1000));

		// 高亮
		HighlightBuilder highlightBuilder = new HighlightBuilder();
		highlightBuilder.field("info");
		highlightBuilder.preTags("<-");
		highlightBuilder.postTags("->");
		requestBuilder.highlighter(highlightBuilder);

		requestBuilder.setQuery(QueryBuilders.matchPhraseQuery("info", "中国"));
		SearchResponse responsebuilder = requestBuilder.get();
		SearchHits searchHits = responsebuilder.getHits();
		System.out.println("total:" + searchHits.totalHits);
		for (SearchHit searchHit : searchHits.getHits()) {
			Map<String, Object> sources = searchHit.getSourceAsMap();
			System.out.println(sources + " -> " + searchHit.getHighlightFields());
		}
		System.out.println("fromSize");
	}

	@Test
	public void scroll() throws Exception {
		SearchRequestBuilder requestBuilder = client.prepareSearch(IndexName);
		// 类型
		requestBuilder.setTypes("fulltext");
		// 排序
		requestBuilder.addSort("time", SortOrder.DESC);
		// 检索方式
		requestBuilder.setSearchType(SearchType.DEFAULT);
		// 检索的数据量
		requestBuilder.setTimeout(new TimeValue(1000));

		// 高亮
		HighlightBuilder highlightBuilder = new HighlightBuilder();
		highlightBuilder.field("info");
		highlightBuilder.preTags("<-[");
		highlightBuilder.postTags("]->");
		requestBuilder.highlighter(highlightBuilder);

		// 设置滑动查询，一定要设置size
		Scroll scroll = new Scroll(new TimeValue(1000));
		requestBuilder.setScroll(scroll);
		requestBuilder.setSize(2);

		// 匹配查询
		requestBuilder.setQuery(QueryBuilders.matchPhraseQuery("info", "中国"));

		// 第一次已经取出数据了
		SearchResponse response = requestBuilder.get();
		getData(response);

		// 滚屏ID
		String scrollId = response.getScrollId();
		// 取出总数，但不取数据
		long total = response.getHits().getTotalHits();
		for (int i = 0; i < total; i++) {
			SearchScrollRequestBuilder actionResponse = client.prepareSearchScroll(scrollId);
			SearchResponse searchResponse = actionResponse.get();
			getData(searchResponse);
		}

		System.out.println("scroll : " + total);

	}

	private void getData(SearchResponse response) {
		for (SearchHit searchHit : response.getHits()) {
			Map<String, Object> sources = searchHit.getSourceAsMap();
			System.out.println(sources + " -> " + searchHit.getHighlightFields());
		}

	}

}
