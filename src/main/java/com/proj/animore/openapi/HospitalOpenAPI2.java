package com.proj.animore.openapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class HospitalOpenAPI2 {
	
	private final JdbcTemplate jt;
	private final static String SERVICE_KEY="pdkeiUGlzOckrCiEHJaFdSydNz6KL1Wpu7DnP1VQ7L%2B78Nw3mWnpIR0pNmMjZGkmmk82W6O%2B8NT3pjD6xavegA%3D%3D";
	private static String htag = "";
	
	public List<Hospital> getHospital(HospitalParam hospitalParam) throws IOException {
		StringBuilder urlBuilder = new StringBuilder("http://data.ulsan.go.kr/rest/ulsananimal/getUlsananimalList"); /*URL*/
		
		//요청파라미터
    urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "="+SERVICE_KEY); /*Service Key*/
    urlBuilder.append("&" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + URLEncoder.encode("-", "UTF-8")); /*공공데이터포털에서 받은 인증키*/
    urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode(hospitalParam.getPageNo(), "UTF-8")); /*페이지번호*/
    urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode(hospitalParam.getNumOfRows(), "UTF-8")); /*한 페이지 결과 수*/
    
    log.info("urlBuilder:{}",urlBuilder);
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
    
    //xml to json
    int INDENT_FACTOR = 2;
    JSONObject xmlJSONObj = XML.toJSONObject(sb.toString());
    
    JSONObject jsonrfcOpenApi = xmlJSONObj.getJSONObject("rfcOpenApi");
    JSONObject jsonbody = jsonrfcOpenApi.getJSONObject("body");
    JSONObject jsondata = jsonbody.getJSONObject("data");
    
    JSONArray jsonArray = new JSONArray(jsondata.getJSONArray("list"));
    log.info("jsonArray:{}",jsonArray);
    log.info("jsonArrayLength:{}",jsonArray.length());
//    for(int i=0; i<jsonArray.length(); i++) {
//    	JSONObject jsonObject = jsonArray.getJSONObject(i);    	
//    	log.info("jsonObject-title:{}",jsonObject.get("title"));
//    }
//    return xmlJSONObj;
    
    ObjectMapper mapper = new ObjectMapper();
    mapper.setSerializationInclusion(Include.NON_NULL);
    
    ArrayList hList = mapper.readValue(jsonArray.toString(), ArrayList.class);
    List<Hospital> hList2 = mapper.convertValue(hList, new TypeReference<List<Hospital>>() {});
    
    
    
    String sqlGetStartSeq = "select business_bnum_seq.nextval from dual";
    Integer startSeq = jt.queryForObject(sqlGetStartSeq, Integer.class) + 1;
    
    
    StringBuffer sql = new StringBuffer();
		sql.append("insert into business (bnum, bbnum, bname, baddress, btel ) ");
    sql.append(" values ( business_bnum_seq.nextval , ? , ? , ? , ? ) ");
    
		jt.batchUpdate(sql.toString(), new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, ((Hospital) hList2.get(i)).getEntId());
				ps.setString(2, ((Hospital) hList2.get(i)).getTitle());
				ps.setString(3, ((Hospital) hList2.get(i)).getAddress());
				ps.setString(4, ((Hospital) hList2.get(i)).getTel());
			}
			
			@Override
			public int getBatchSize() {
				return hList2.size();
			}
		});
		
		
		String sqlBcategoryInsert = "insert into bcategory (bnum, bhospital) values( ? , ? )";
		jt.batchUpdate(sqlBcategoryInsert, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				log.info("startSeq+i:{}",startSeq+i);
				ps.setInt(1, startSeq+i);
				ps.setString(2, "Y");
			}
			
			@Override
			public int getBatchSize() {
				return hList2.size();
			}
		});
		
    
    return hList2;
	}
}
