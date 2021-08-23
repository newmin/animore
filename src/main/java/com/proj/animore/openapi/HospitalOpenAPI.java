package com.proj.animore.openapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class HospitalOpenAPI {

	private final static String SERVICE_KEY="pdkeiUGlzOckrCiEHJaFdSydNz6KL1Wpu7DnP1VQ7L%2B78Nw3mWnpIR0pNmMjZGkmmk82W6O%2B8NT3pjD6xavegA%3D%3D";
	
	public String getHospital(HospitalParam hospitalParam) throws IOException {
		StringBuilder urlBuilder = new StringBuilder("http://data.ulsan.go.kr/rest/ulsananimal/getUlsananimalList"); /*URL*/
		
		//요청파라미터
    urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "="+SERVICE_KEY); /*Service Key*/
    urlBuilder.append("&" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + URLEncoder.encode("-", "UTF-8")); /*공공데이터포털에서 받은 인증키*/
    urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode(hospitalParam.getPageNo(), "UTF-8")); /*페이지번호*/
    urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode(hospitalParam.getNumOfRows(), "UTF-8")); /*한 페이지 결과 수*/

    URL url = new URL(urlBuilder.toString());
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    
    //요청메세지 헤더구성
    conn.setRequestMethod("GET");
    conn.setRequestProperty("Content-type", "application/json");
    log.info("Response code:{}",conn.getResponseCode());
    
//    응답메세지 body 읽기
    BufferedReader rd = null;
//    응답코드 200~300
    if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
        rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//    응답코드가 성공 아니면
    } else {
        rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
    }
    
    //읽어온 응답메세지 문자열객체로 변환
    StringBuilder sb = new StringBuilder();
    String line;
    while ((line = rd.readLine()) != null) {
        sb.append(line);
    }
    rd.close();
    conn.disconnect();

    //log.info(sb.toString());
    
    //xml to json
    int INDENT_FACTOR = 2;
    JSONObject xmlJSONObj = XML.toJSONObject(sb.toString());
    String jsonPrettyString = xmlJSONObj.toString(INDENT_FACTOR);	//들여쓰기
    
    return jsonPrettyString;
	}
}
