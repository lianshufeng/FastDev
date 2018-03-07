package dytt;

import java.util.ArrayList;
import java.util.List;

import com.fast.dev.crawler.core.PageCrawler;

public class Dytt_Page implements PageCrawler {

	@Override
	public String corn() {
		return "0/10 * * * * ?";
	}

	@Override
	public String[] pageUrls() {
		List<String> urls = new ArrayList<>();
		urls.add("http://www.dytt8.net/html/gndy/dyzz/index.html");
		for (int i = 2; i <= 171; i++) {
			urls.add("http://www.dytt8.net/html/gndy/dyzz/list_23_" + (i) + ".html");
		}
		return urls.toArray(new String[0]);
	}

	@Override
	public int repeatPageCount() {
		return 3;
	}

	@Override
	public String taskName() {
		return "dytt";
	}

}
