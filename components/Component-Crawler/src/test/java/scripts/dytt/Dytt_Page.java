package scripts.dytt;

import java.util.ArrayList;
import java.util.List;

import com.fast.dev.crawler.core.PageCrawler;
import com.fast.dev.crawler.model.UrlJob;

public class Dytt_Page implements PageCrawler {

	@Override
	public String corn() {
		return "0/10 * * * * ?";
	}

	@Override
	public List<UrlJob> pageUrls() {
		List<UrlJob> urls = new ArrayList<>();
		urls.add(new UrlJob("http://www.dytt8.net/html/gndy/dyzz/index.html"));
		for (int i = 2; i <= 171; i++) {
			urls.add(new UrlJob("http://www.dytt8.net/html/gndy/dyzz/list_23_" + (i) + ".html"));
		}
		return urls;
	}

	@Override
	public List<UrlJob> repeat(List<UrlJob> sources) {
		return sources.subList(0, 3);
	}

	@Override
	public String taskName() {
		return "dytt";
	}

}
