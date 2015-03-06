import java.io.IOException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Test {
	
	public static void main(String args[]) throws IOException {
		URL url = new URL("http://detail.1688.com/offer/40898643694.html?spm=a2604.152012.1998248592.18.wxi3I1"); 
		Document doc = Jsoup.parse(url, 3000);
		Element content = doc.getElementById("mod-detail-bd") ; 
		Elements imgs = content.getElementsByClass("vertical-img") ; 
		int cnt = 0  ; 
		for (Element img : imgs) {
			if (cnt > 0) {
				break ; 
			} else {
				System.out.println(img.getElementsByTag("img").attr("src").toString()); 
			}
			cnt ++ ;
		}
	}

}
