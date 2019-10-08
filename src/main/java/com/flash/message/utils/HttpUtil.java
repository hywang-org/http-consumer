package com.flash.message.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * @author 作者 :hywang
 *
 * @version 创建时间：2019年9月7日 下午2:05:22
 *
 * @version 1.0
 */
public class HttpUtil {

	/* 正则匹配host */
	public static String getHost(String url) {
		String host = StringUtils.EMPTY;
		Pattern pattern = Pattern.compile("^(\\w+):\\/\\/([^/:]+)(:\\d*|\\/)");
		Matcher match = pattern.matcher(url);
		if (match.find()) {
			host = match.group(2);
		}
		return host;
	}

	public static String sendGet(String url) {
		String result = StringUtils.EMPTY;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		httpGet.addHeader("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		httpGet.addHeader("Accept-Encoding", "gzip, deflate");
		httpGet.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
		httpGet.addHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36");
		httpGet.addHeader("Content-Type", "text/html; charset=UTF-8");
		httpGet.addHeader("Connection", "Keep-Alive");
		String host = getHost(url);
		httpGet.addHeader("Host", host);
		httpGet.setConfig(RequestConfig.custom().setConnectTimeout(1000).build());
		try {
			CloseableHttpResponse response = httpClient.execute(httpGet);
			int status = response.getStatusLine().getStatusCode();
			if (status == HttpStatus.SC_OK) {
				HttpEntity responseEntity = response.getEntity();
				result = EntityUtils.toString(responseEntity, "UTF-8");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String sendPost(String url, Map<String, Object> bodyMap) {
		String result = StringUtils.EMPTY;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		httpPost.addHeader("Accept-Encoding", "gzip, deflate");
		httpPost.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
		httpPost.addHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36");
		httpPost.addHeader("Content-Type", "text/html; charset=UTF-8");
		httpPost.addHeader("Connection", "Keep-Alive");
		String host = getHost(url);
		httpPost.addHeader("Host", host);
		List<NameValuePair> bodyList = new ArrayList<NameValuePair>();
		for (Entry<String, Object> entry : bodyMap.entrySet()) {
			bodyList.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
		}
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(bodyList, "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		try {
			CloseableHttpResponse response = httpClient.execute(httpPost);
			int status = response.getStatusLine().getStatusCode();
			if (status == HttpStatus.SC_OK) {
				HttpEntity responseEntity = response.getEntity();
				result = EntityUtils.toString(responseEntity, "UTF-8");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String sendJsonPost(String url, JSONObject json) {
		String result = StringUtils.EMPTY;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		httpPost.addHeader("Accept-Encoding", "gzip, deflate");
		httpPost.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
		httpPost.addHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36");
		httpPost.addHeader("Content-Type", "application/json");
		httpPost.addHeader("Connection", "Keep-Alive");
		String host = getHost(url);
		httpPost.addHeader("Host", host);
		StringEntity requestEntity = new StringEntity(json.toJSONString(), "utf-8");
		requestEntity.setContentEncoding("UTF-8");
		httpPost.setEntity(requestEntity);
		try {
			CloseableHttpResponse response = httpClient.execute(httpPost);
			int status = response.getStatusLine().getStatusCode();
			if (status == HttpStatus.SC_OK) {
				HttpEntity responseEntity = response.getEntity();
				result = EntityUtils.toString(responseEntity, "UTF-8");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}
