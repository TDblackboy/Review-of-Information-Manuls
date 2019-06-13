package pachong;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HttpUtil {

	/**
	 * 根据url 发送请求，获取响应
	 * 
	 * @param client
	 * @param goalUrl
	 * @return
	 */
	public static HttpResponse getRawHtml(HttpClient client, String goalUrl) {
		// 获取响应文件，即html，采用get方法获取响应数据
		HttpGet getMethod = new HttpGet(goalUrl);
		HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1, HttpStatus.SC_OK, "OK");
		try { // 执行get方法
			response = client.execute(getMethod);
		} catch (IOException e) {
			e.printStackTrace();
		} finally { //
			// getMethod.abort();
		}
		return response;
	}

	/**
	 * 获取url的参数lemma-跳转到正确的页面
	 */
	public String htmlParseToGetUrlLemma(String html) {
		String lemma = "";
		String subHtml1=html.substring(html.indexOf("$.ajax"));
		//System.out.println("subHtml1:"+subHtml1);
		String subHtml2=subHtml1.substring(subHtml1.indexOf("lemmaId"),subHtml1.indexOf("}"));
		//System.out.println("subHtml2:"+subHtml2);
		String subHtml3=subHtml2.substring(subHtml2.indexOf(":"),subHtml2.indexOf(","));
		//System.out.println("subHtml3:"+subHtml3);
		lemma=subHtml3.split(" ")[1];
		return lemma;
	}

	/**
	 * 解析目的页面-获取目的文本
	 * 
	 * @param html
	 * @return
	 */
	public String htmlParseToGetText(String html) {
		String text = "";
		Document doc = Jsoup.parse(html);

		return text;
	}

	
	/**
	 * 用于外部调用接口 - 主流程函数
	 * 
	 * @param client
	 * @param url
	 * @return
	 */
	public String responseHtmlByUrl(String url) {
		String text = "";
		String lemma = "";
		HttpClient client=HttpClientBuilder.create().build();
		HttpResponse response = getRawHtml(client, url);
		int StatusCode = response.getStatusLine().getStatusCode();
		if (StatusCode == 200) {
			String entity = "";// 获取html1
			try {
				entity = EntityUtils.toString(response.getEntity(), "utf-8");
			} catch (ParseException | IOException e) {
				e.printStackTrace();
			}
			System.out.println("-----html1-----\n"+entity);
			lemma = htmlParseToGetUrlLemma(entity);
			System.out.println("lemma:" + lemma);
			try {
				EntityUtils.consume(response.getEntity());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else { // 否则，消耗掉实体
			try {
				EntityUtils.consume(response.getEntity());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

//		// 再次获取页面html2
//		String newUrl = url + "/" + lemma;
//		response = getRawHtml(client, newUrl);
//		StatusCode = response.getStatusLine().getStatusCode();
//		if (StatusCode == 200) {
//			String entity = "";// 获取html2
//			try {
//				entity = EntityUtils.toString(response.getEntity(), "utf-8");
//			} catch (ParseException | IOException e) {
//				e.printStackTrace();
//			}
//			System.out.println("-----html2-----\n"+entity);
//			text = htmlParseToGetText(entity);
//			System.out.println("text:" + text);
//			try {
//				EntityUtils.consume(response.getEntity());
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		} else { // 否则，消耗掉实体
//			try {
//				EntityUtils.consume(response.getEntity());
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
		
		return text;
	}
}
