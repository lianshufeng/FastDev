package test1;

import java.util.ArrayList;
import java.util.List;

import com.fast.dev.crawler.core.ContentCrawler;
import com.fast.dev.crawler.model.ContentResult;
import com.fast.dev.crawler.model.CrawlerParameter;
import com.fast.dev.crawler.model.ListCrawlerResult;
import com.fast.dev.crawler.model.ListNextPageAndUrlsResult;

public class Dytt_Dm_Content implements ContentCrawler {

	@Override
	public String name() {
		return "dytt_dm";
	}


	@Override
	public ContentResult call(CrawlerParameter parameter) {
		
		return new ContentResult();
	}
}
