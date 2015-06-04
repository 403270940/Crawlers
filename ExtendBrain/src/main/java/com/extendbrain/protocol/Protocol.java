package com.extendbrain.protocol;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;

import com.extendbrain.beans.Content;
import com.extendbrain.beans.URLDatum;

public interface Protocol {
	public Content getOutput(String url);
	public Content getOutput(HttpGet get);
	public Content getOutput(String url,URLDatum datum);
	public Content postOutput(HttpPost httpPost);
}