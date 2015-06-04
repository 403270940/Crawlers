package com.extendbrain.crawl58;

import java.util.Map.Entry;

import com.extendbrain.beans.Content;
import com.extendbrain.beans.District;
import com.extendbrain.beans.URLDatum;
import com.extendbrain.protocol.Protocol;
import com.extendbrain.protocol.ProtocolFactory;

public class Injector {

	public static void main(String[] args) {
		String url = "http://bj.58.com/tongzhouqu/chuzu/";
		URLDatum datum = new URLDatum(url);
		Protocol protocol = ProtocolFactory.getProtocol(datum.getUrl());
		Content content = protocol.getOutput(datum.getUrl(), datum);
		Parser_58.getRentItems(content);

		
//		System.out.println(new String(content.getContent()));
//		parser58.parse(content);
		
	}
}
