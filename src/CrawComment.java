import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class CrawComment {

	public static void main(String args[]) {
		
		Scanner cin = new Scanner(System.in) ; 
		System.out.println("請輸入商家ID:") ; 
		String shopid = cin.next() ; 
		System.out.println("請輸入商品ID:") ; 
		String itemid = cin.next() ; 
		System.out.println("設定每頁評價數量:") ; 
		String pagesize = cin.next() ; 
		
		// 生成HttpClient物件

		DefaultHttpClient httpClient = new DefaultHttpClient();

		// 生成 proxy 物件
		// HttpHost proxy = new HttpHost("12.199.141.164", 80, "http");

		try {

			// get方法連接

			// HttpHost target = new HttpHost(url, 80, "http");

			HttpGet httpget = new HttpGet("http://rate.1688.com/remark/offerDetail/rates.json?_input_charset=GBK&offerId="+itemid+"&page=1&pageSize=+"+pagesize+"&starLevel=&orderBy=date&semanticId=&showStat=1&content=1&t=1422953024588&memberId="+shopid+"&callback=jQuery17206406422895379364_1422953023603");

			// 得到responce

			HttpResponse response = httpClient.execute(httpget);

			// 回傳代碼

			int resStatu = response.getStatusLine().getStatusCode();

			// 200代表正常

			if (resStatu == HttpStatus.SC_OK) {

				// 取得各元件實體

				HttpEntity entity = response.getEntity();
				if (entity != null) {

					// 取得html原始碼

					String html = EntityUtils.toString(entity, "UTF-8") ; 
					html = html.substring(html.indexOf("{"), html.length()) ; 
					JSONObject json = new JSONObject(html) ; 
					JSONArray comments = new JSONArray(json.getJSONObject("data").getJSONArray("rates").toString()) ; 
					System.out.println(comments); 
				    for (int i = 0 ; i < comments.length(); i++) {
				    	
				        JSONObject obj = comments.getJSONObject(i);
				        // 會員編號
				        String member = (obj.isNull("member"))?"":obj.getString("member") ;
				        // 累計採購
				        int countQuantity = (obj.isNull("countQuantity"))?0:obj.getInt("countQuantity") ;
				        // 採購數量
				        int quantity = (obj.isNull("quantity"))?0:obj.getInt("quantity") ;
				        /////////////////////////////////////////////////////////////
				        
				        JSONArray items = obj.getJSONArray("rateItem");
				        JSONObject item = items.getJSONObject(0) ; 
				        // 星級
				        int starLevel = (item.isNull("starLevel"))?0:item.getInt("starLevel") ;
				        // 時間
				        String remarkTime = (item.isNull("remarkTime"))?"":item.getString("remarkTime") ;
				        // 評價內容
				        String remarkContent = (item.isNull("remarkContent"))?"":item.getString("remarkContent") ;
				        
				        System.out.println("會員編號:\t"+member); 
				        System.out.println("累計採購:\t"+countQuantity); 
				        System.out.println("採購數量:\t"+quantity); 
				        System.out.println("星級:\t"+starLevel); 
				        System.out.println("時間:\t"+remarkTime); 
				        System.out.println("評價內容:\t"+remarkContent); 
				        System.out.println("=========================="); 
				    }

				}

			}

		} catch (Exception e) {

			System.out.println("URL出現異常");

			e.printStackTrace();

		} finally {

			// httpget.releaseConnection();

			httpClient.getConnectionManager().shutdown();

		}
	}
}
