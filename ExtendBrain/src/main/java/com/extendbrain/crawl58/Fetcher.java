package com.extendbrain.crawl58;


import java.io.File;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;


















import com.extendbrain.beans.Content;
import com.extendbrain.beans.URLDatum;
import com.extendbrain.dao.Mysql_Content;
import com.extendbrain.dao.Mysql_URLDatum;
import com.extendbrain.protocol.Protocol;
import com.extendbrain.protocol.ProtocolFactory;

public class Fetcher {
	
	FetchItemQueues queues;
	public Fetcher(){
		queues = new FetchItemQueues();
	}
	
	public static class QueueFeeder extends Thread{
		private FetchItemQueues queues;
		private int size;
		public QueueFeeder(FetchItemQueues queues, int size){
			this.queues = queues;
			this.size = size;
			this.setName("QueueFeeder");
		}
		
		public void run() {
			List<URLDatum> list = Mysql_URLDatum.getStatus(URLDatum.STATUS_INJECTED);
			System.out.println("list size:" + list.size());
			int feed = size - queues.getTotalSize();
			while(!list.isEmpty()){
				if(feed <= 0) {
					try {
						Thread.sleep(1000);
						continue;
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					URLDatum datum = list.remove(list.size()-1);
					System.out.println(datum.getUrl());
					queues.addFetchItem(datum.getUrl(), datum);
					feed --;
				}
			}
		}
	}
	
	public class FetchThread extends Thread{
		public void run() {
			while(queues.getTotalSize()!=0){
				System.out.println(queues.getTotalSize());
				FetchItem fit = queues.getFetchItem();
				System.out.println(queues.getTotalSize());
				if(fit == null){
					try {
						Thread.sleep(1000);
						continue;
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				URLDatum datum = fit.getDatum();
				Protocol protocol = ProtocolFactory.getProtocol(datum.getUrl());
				Content content = protocol.getOutput(datum.getUrl(), datum);
				queues.finishFetchItem(fit, false);
				Mysql_Content.save(content);
				Mysql_URLDatum.update(datum, URLDatum.STATUS_SUCCESS);
			}
			System.out.println("Fetch end");
		}
	}
	
	private static class FetchItem{
		String url;
		String queueId;
		URLDatum datum;
		private FetchItem(String url,String queueId,URLDatum datum){
			this.url = url;
			this.queueId = queueId;
			this.datum = datum;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getQueueId() {
			return queueId;
		}
		public void setQueueId(String queueId) {
			this.queueId = queueId;
		}
		public URLDatum getDatum() {
			return datum;
		}
		public void setDatum(URLDatum datum) {
			this.datum = datum;
		}
		public static FetchItem create(String url,URLDatum datum){
			String queueId = null;
			URL u = null;
			try {
				u = new URL(url);
				String protocol = u.getProtocol();
				String ip = InetAddress.getByName(u.getHost()).getHostAddress();
				queueId = protocol + ":" + ip;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return new FetchItem(url, queueId, datum);
		}
	}
	
	private static class FetchItemQueue{
		List<FetchItem> queue = Collections.synchronizedList(new LinkedList<FetchItem>());
		Set<FetchItem> inProgress = Collections.synchronizedSet(new HashSet<FetchItem>());
		AtomicLong nextFetchTime = new AtomicLong();
		long minFetchDelay;
		long maxFetchDelay;
		int maxThreads = 10;
		
		public int getQueueSize(){
			return queue.size();
		}
		public synchronized int emptyQueue(){
			int preSize = queue.size();
			queue.clear();
			return preSize;
		}
		public int getInProgressSize(){
			return inProgress.size();
		}
		public void addFetchItem(FetchItem fit){
			if(fit==null) return;
			queue.add(fit);
		}
		public void finishFetchItem(FetchItem fit,boolean isDelay){
			if(fit==null) return;
			inProgress.remove(fit);
			setEndTime(System.currentTimeMillis(),isDelay);
		}
		public void addInProgressFetchItem(FetchItem fit){
			if(fit==null) return;
			inProgress.add(fit);
		}
		public void setEndTime(long time){
			nextFetchTime.set(time);
		}
		public void setEndTime(long time,boolean isDelay){
			if(isDelay){
				
			}
		}
		public FetchItem getFetchItem(){
			if(inProgress.size()>=maxThreads)return null;
			long now = System.currentTimeMillis();
			if(nextFetchTime.get() > now) return null;
			FetchItem fit = null;
			if(queue.size() == 0) return null;
			fit = queue.remove(0);
			inProgress.add(fit);
			return fit;
		}
	}
	
	private static class FetchItemQueues{
		Map<String,FetchItemQueue> queues = new HashMap<String, FetchItemQueue>();
		AtomicInteger totalSize = new AtomicInteger(0);
		long crawlDelay;
		long minCrawlDelay;
		public FetchItemQueues(){
			crawlDelay = 24 * 3600;
			minCrawlDelay = 3600;
		}
		public int getTotalSize(){
			return totalSize.get();
		}
		public int getQueueCount(){
			return queues.size();
		}
		public void finishFetchItem(FetchItem fit,boolean isDelay){
			FetchItemQueue fiq = queues.get(fit.queueId);
			if(fiq == null){
				return;
			}
			fiq.finishFetchItem(fit, isDelay);
		}
		public synchronized void addFetchItem(String url,URLDatum datum){
			FetchItem fit = FetchItem.create(url, datum);
			if(fit != null) addFetchItem(fit);
		}
		public synchronized void addFetchItem(FetchItem fit){
			FetchItemQueue fiq = getFetchItemQueue(fit.queueId);
			fiq.addFetchItem(fit);
			totalSize.incrementAndGet();
		}
		public synchronized FetchItemQueue getFetchItemQueue(String queueID){
			FetchItemQueue fiq = queues.get(queueID);
			if(fiq == null) {
				fiq = new FetchItemQueue();
				queues.put(queueID, fiq);
			}
			return fiq;
		}
		
		public synchronized FetchItem getFetchItem(){
			Iterator<Map.Entry<String, FetchItemQueue>> iterator = 
					queues.entrySet().iterator();
			while(iterator.hasNext()){
				FetchItemQueue fiq = iterator.next().getValue();
				System.out.println("fiq:" + fiq.getQueueSize());
				if(fiq.getQueueSize() == 0 && fiq.getInProgressSize() == 0){
					iterator.remove();
					continue;
				}
				FetchItem fit = fiq.getFetchItem();
				if(fit != null) {
					totalSize.decrementAndGet();
					System.out.println("size:" + totalSize);
					return fit;
				}
			}
					
			return null;
		}
		
		public synchronized int emptyQueues(){
			int count = 0;
			for(String id : queues.keySet()){
				FetchItemQueue fiq = queues.get(id);
				if(fiq.getQueueSize() == 0)continue;
				int deleted = fiq.emptyQueue();
				for(int i = 0; i < deleted; i ++){
					totalSize.decrementAndGet();
				}
				count += deleted;
			}
			return count;
		}
	}
	
	
	public void run(){
		QueueFeeder feeder = new QueueFeeder(queues, 10);
		feeder.start();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i = 0; i < 2;i ++){
			new FetchThread().start();
		}
	}
	public static void main(String[] args) {
		Fetcher fetcher = new Fetcher();
		fetcher.run();
//		URLDatum datum = new URLDatum();
//		Vector<String> urls = new Vector<String>(10);
//		urls.add("http://www.baidu.com");
//		urls.add("http://www.2cto.com/News/it/");
//		urls.add("http://www.2cto.com/");
//		urls.add("http://www.2cto.com/News/it/");
//		urls.add("http://www.2cto.com/News/201410/344090.html");
//		urls.add("http://www.2cto.com/Article/201410/345140.html");
//		urls.add("http://www.2cto.com/Article/201410/345136.html");
//		urls.add("http://www.2cto.com/Article/201410/345126.html");
//		urls.add("http://www.2cto.com/Article/201410/343729.html");
//		urls.add("http://www.2cto.com/News/201410/345523.html");
//		for(int i = 0 ;i < 10;i ++){
//			datum.setUrl(urls.remove(0));
//			Content content = fetcher.get(datum);
//			Mysql_Content.save(content);
//		}
	}
}
