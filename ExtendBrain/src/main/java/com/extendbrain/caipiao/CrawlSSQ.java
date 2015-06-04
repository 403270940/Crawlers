package com.extendbrain.caipiao;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.extendbrain.beans.Content;
import com.extendbrain.beans.URLDatum;
import com.extendbrain.dao.Mysql_SSQ;
import com.extendbrain.protocol.Protocol;
import com.extendbrain.protocol.ProtocolFactory;

public class CrawlSSQ {
	private String baseURL = "http://fcasp.zjol.com.cn/zjfc/asp_ssq.asp?pagenum=";
	private int pageNum = 1;
	private boolean continueCrawl = true;
	private int maxEditionInDB = 0;
	private Protocol protocol;
	private List<SSQ> ssqList;
	public CrawlSSQ(){
		maxEditionInDB = Mysql_SSQ.getMaxEdition();
		ssqList = new ArrayList<SSQ>();
		protocol = ProtocolFactory.getProtocol(baseURL);
	}
	public List<SSQ> getSSQList(){		
		while(continueCrawl == true){
			String url = baseURL + pageNum;
			Content content = protocol.getOutput(url, new URLDatum(url));
			String html = content.getContentString();
			Document doc = Jsoup.parse(html,content.getCharSet());
			Elements nodes = doc.select(".px12line21");
			for(int i = 2;i < nodes.size();i++){
				Element node = nodes.get(i);
				String openTime = node.child(0).child(0).text();
				int edition = Integer.valueOf(node.child(1).text());
				String [] result = node.child(2).text().split(",|\\+");
				int red1= Integer.valueOf(result[0].trim());
				int red2= Integer.valueOf(result[1].trim());
				int red3= Integer.valueOf(result[2].trim());
				int red4= Integer.valueOf(result[3].trim());
				int red5= Integer.valueOf(result[4].trim());
				int red6= Integer.valueOf(result[5].trim());
				int blue= Integer.valueOf(result[6].trim());
				String moneyStr = node.child(3).text();
				int money = Integer.valueOf(moneyStr.substring(0, moneyStr.length()-1).trim());
				SSQ ssq = new SSQ(edition, openTime, red1, red2, red3, red4, red5, red6, blue, money);
				if(edition <= maxEditionInDB){
					continueCrawl = false;
					break;
				}
				ssqList.add(ssq);
			}
			pageNum++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(ssqList.size());
		return ssqList;
	}
	public void save(List<SSQ> ssqList){
		if(ssqList.size()>0)
			Mysql_SSQ.save(ssqList);
		System.out.println("一共保存了"+ssqList.size()+"个双色球信息");
	}
	public static void main(String[] args) {
		CrawlSSQ crawlSSQ = new CrawlSSQ();
		List<SSQ> ssqList = crawlSSQ.getSSQList();
//		System.out.println(ssqList.size());
		crawlSSQ.save(ssqList);
	}
}
