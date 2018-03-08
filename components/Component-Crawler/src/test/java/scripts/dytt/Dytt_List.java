package scripts.dytt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.fast.dev.crawler.core.ListCrawler;
import com.fast.dev.crawler.model.UrlJob;

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
	public UrlJob[] call(String pageUrl, Map<String, Object> data) {
		try {
			System.out.println("获取列表：" + pageUrl);
			List<UrlJob> urls = new ArrayList<>();
			Document document = Jsoup.connect(pageUrl).timeout(10000).get();
			Elements content = document.getElementsByClass("co_content8");
			Elements tables = content.get(0).getElementsByTag("table");
			for (int i = 0; i < tables.size(); i++) {
				Element table = tables.get(i);
				Element td = table.getElementsByTag("tr").get(1).getElementsByTag("td").get(1);
				Element a = td.getElementsByTag("a").get(0);
				String url = "http://www.dytt8.net" + a.attr("href");
				String dateStre = table.getElementsByTag("tr").get(2).getElementsByTag("font").text();
				dateStre = dateStre.split("：")[1];
				final String publishTime = dateStre.substring(0, dateStre.lastIndexOf(" "));

				// 扩展数据
				Map<String, Object> m = new HashMap<String, Object>() {
					private static final long serialVersionUID = 1L;
					{
						put("publishTime", publishTime);
					}
				};
				urls.add(new UrlJob(url, m));
			}
			return urls.toArray(new UrlJob[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
