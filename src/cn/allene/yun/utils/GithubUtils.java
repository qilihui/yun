package cn.allene.yun.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class GithubUtils {
//	public static final String reqUrl = "http://aliyun.us.to:8888/yun/user/authorization_code.action";
	public static final String reqUrl = "http://localhost:8080/yun/user/authorization_code.action";

	public static final String client_id = "31a441180b82dd8f8a63";

	public static final String client_secret = "bb6beea515da562fed84e8073c7aa3d8f5578a27";

	public static Map getJson(String code) throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		Map<String, String> map = new HashMap<>();
		map.put("client_id", client_id);
		map.put("client_secret", client_secret);
		map.put("state", "test");
		map.put("code", code);
		map.put("redirect_uri", reqUrl);
		Map<String, String> resp = restTemplate.postForObject("https://github.com/login/oauth/access_token", map,
				Map.class);
		System.out.println(resp);
		if (resp.get("error") != null) {
			throw new Exception(resp.get("error").toString());
		}
		HttpHeaders httpheaders = new HttpHeaders();
		httpheaders.add("Authorization", "token " + resp.get("access_token"));
		HttpEntity<?> httpEntity = new HttpEntity<>(httpheaders);
		ResponseEntity<Map> exchange = null;
		try {
			exchange = restTemplate.exchange("https://api.github.com/user", HttpMethod.GET, httpEntity,
					Map.class);
		} catch(Exception e) {
			throw new Exception(e);
		}
		System.out.println("exchange.getBody() = " + exchange.getBody());
		return exchange.getBody();
	}
}
