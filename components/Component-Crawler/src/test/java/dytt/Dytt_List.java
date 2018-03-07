package dytt;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.fast.dev.crawler.core.ListCrawler;

public class Dytt_List implements ListCrawler {

	@Override
	public String corn() {
		return "0/2 * * * * ?";
	}

	@Override
	public String taskName() {
		return "dytt";
	}

	@Override
	public String[] call(String pageUrl) {
		try {
			System.out.println("获取列表：" + pageUrl );
			List<String> urls = new ArrayList<>();
			Document document = Jsoup.connect(pageUrl).timeout(10000).get();
			Elements content = document.getElementsByClass("co_content8");
			Elements tables = content.get(0).getElementsByTag("table");
			for (int i = 0; i < tables.size(); i++) {
				Element table = tables.get(i);
				Element td = table.getElementsByTag("tr").get(1).getElementsByTag("td").get(1);
				Element a = td.getElementsByTag("a").get(0);
				String url = "http://www.dytt8.net" + a.attr("href");
//				String title = a.text();
				urls.add(url);
			}
			return urls.toArray(new String[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

}
