package scripts.dytt;

import java.text.SimpleDateFormat;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.fast.dev.crawler.core.ContentCrawler;
import com.fast.dev.crawler.model.ContentResult;

public class Dytt_Content implements ContentCrawler {

	@Override
	public String corn() {
		return "0/3 * * * * ?";
	}

	@Override
	public String taskName() {
		return "dytt";
	}

	@Override
	public ContentResult call(String url) {
		try {
			System.out.println("获取详细：" + url);
			Document document = Jsoup.connect(url).timeout(10000).get();
			String title = document.getElementsByClass("title_all").get(4).text();
			Element content = document.getElementsByClass("co_content8").first();
			String downUrl = content.getElementsByTag("a").first().attr("href");
			String timeStr = content.getElementsByTag("ul").text().split(" ")[0].split("：")[1];
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			long publishTime = dateFormat.parse(timeStr).getTime();
			return new ContentResult(title, publishTime, downUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}


}
