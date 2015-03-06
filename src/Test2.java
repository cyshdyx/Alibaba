import java.io.IOException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Test2 {
	
	public static void main(String args[]) throws IOException {
		URL url = new URL("https://play.google.com/store/apps/details?id=com.linecorp.LGRGS"); 
		Document doc = Jsoup.parse(url, 3000);
		System.out.println("App標題:"+doc.select("div.document-title > div").text()) ; 
		System.out.println("App公司:"+doc.select("a.document-subtitle.primary > span").text()) ; 
		System.out.println("App類別:"+doc.select("a.document-subtitle.category > span").text()) ;
		System.out.println("App星評:"+doc.select("div.tiny-star.star-rating-non-editable-container").attr("aria-label").toString().substring(4, 7)) ;
		System.out.println("App總評分次數:"+doc.select("div.stars-count").text()) ;
		Elements stars = doc.select("span.bar-number") ; 
		int i = 5 ; 
		for (Element e : stars) {
			System.out.println("App"+i+"星評次數:"+e.text()) ;
			i-- ; 
			if (i<=0) {
				break ; 
			}
		}
		
	}

}
