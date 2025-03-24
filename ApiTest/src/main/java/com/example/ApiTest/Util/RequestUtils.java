package com.example.ApiTest.Util;

import java.io.*;
import java.net.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

/*
    국경일: getHoliDeInfo
    공휴일: getRestDeInfo
    기념일: getAnniversaryInfo 현충일 - 휴일 X?
    24절기:  get24DivisionsInfo
    잡절:  getSundryDayInfo
    */
public class RequestUtils {
    private static String secretKey = "vViW4zOxxuzXyXXQX4tqaNeYLJUuxcv%2BZJX1pKAohBXLX2Yuoc%2FOnMIbm%2FK6UI3nqpjR6vcaNCsHCi5HxO8J5A%3D%3D";

    public static Map<String, Object> holidayInfoAPI() throws IOException {

        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getAnniversaryInfo"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + secretKey); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("365", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("solYear", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(LocalDate.now().getYear()), "UTF-8")); /*연 */
        //urlBuilder.append("&" + URLEncoder.encode("solMonth", "UTF-8") + "=" + URLEncoder.encode(month.length() == 1 ? "0" + month : month, "UTF-8")); /*월*/
        urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /* json으로 요청 */

        URL url = new URL(urlBuilder.toString());
        System.out.println("요청URL = " + urlBuilder);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
      //  System.out.println("Response code: " + conn.getResponseCode());

        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        // System.out.println(sb.toString());

        return string2Map(sb.toString());
    }

    /**
     * Json String을 Hashmap으로 반환
     *
     * @param json
     * @return
     */
    public static Map<String, Object> string2Map(String json) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = null;

        try {
            map = mapper.readValue(json, Map.class);
            System.out.println(map);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;


    }



}