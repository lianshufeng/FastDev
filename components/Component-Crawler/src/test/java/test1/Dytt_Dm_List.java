package test1;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.fast.dev.crawler.core.ListCrawler;
import com.fast.dev.crawler.model.ListCrawlerParameter;
import com.fast.dev.crawler.model.ListCrawlerResult;
import com.fast.dev.crawler.model.ListNextPageAndUrlsResult;
import com.fast.dev.crawler.model.ListUrlsResult;

public class Dytt_Dm_List implements ListCrawler {

	// 默认的主页
	private String pageHome = "http://www.dytt8.net/html/gndy/dyzz/index.html";

	@Override
	public String name() {
		return "dytt_dm";
	}

	@Override
	public String corn() {
		return "0/10 * * * * ?";
	}

	@Override
	public ListCrawlerResult call(ListCrawlerParameter parameter) {

		// 取出结束标记
		String endInfo = parameter.getEndInfo();
		String newEndInfo = null;
		// 是否有下一页
		if (parameter.getNextPage() != null) {
			pageHome = parameter.getNextPage();
		}

		System.out.println("访问页面:" + pageHome);

		try {
			List<String> urls = new ArrayList<>();
			Document document = Jsoup.connect(pageHome).get();
			Elements content = document.getElementsByClass("co_content8");
			Elements tables = content.get(0).getElementsByTag("table");
			// 新的结束标记
			newEndInfo = tables.first().getElementsByTag("tr").get(1).getElementsByTag("td").get(1)
					.getElementsByTag("a").first().text();
			boolean stopNextPage = false;
			for (int i = 0; i < tables.size(); i++) {
				Element table = tables.get(i);
				Element td = table.getElementsByTag("tr").get(1).getElementsByTag("td").get(1);
				Element a = td.getElementsByTag("a").get(0);
				String url = "http://www.dytt8.net" + a.attr("href");
				String title = a.text();
				if (endInfo != null && endInfo.equals(title)) {
					stopNextPage = true;
					continue;
				}
				urls.add(url);
			}
			String nextPageUrl = null;
			// 查看是否有下一页
			Elements nextPages = content.get(0).getElementsByClass("x").get(0).getElementsByTag("a");
			for (int i = 0; i < nextPages.size(); i++) {
				Element nextPage = nextPages.get(i);
				if (nextPage.text().equals("下一页")) {
					nextPageUrl = "http://www.dytt8.net/html/gndy/dyzz/" + nextPage.attr("href");
				}
			}
			if (nextPageUrl != null && !stopNextPage) {
				return new ListNextPageAndUrlsResult().setNextPage(nextPageUrl).setUrls(urls.toArray(new String[0]))
						.setEndInfo(newEndInfo);
			} else {
				return new ListUrlsResult().setUrls(urls.toArray(new String[0])).setEndInfo(newEndInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.exit(0);
		return null;

	}

}
