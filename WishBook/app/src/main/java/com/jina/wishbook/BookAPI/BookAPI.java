package com.jina.wishbook.BookAPI;

import android.util.Log;

import com.jina.wishbook.BuildConfig;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class BookAPI {
    /**
     * curl "https://openapi.naver.com/v1/search/book.xml?query=%EB%B0%94%EB%B3%B4%EC%9D%98%EC%84%B8%EA%B3%84" \
     *         -H "X-Naver-Client-Id: 2Ame9kykI8dCEUi9wXWt" \
     *         -H "X-Naver-Client-Secret: fJEF32yMUr" -v
     */
    private String clientId = BuildConfig.NAVER_SEARCH_ID;
    private String clientSecret = BuildConfig.NAVER_SEARCH_SCRET;
    private String book_title=null;

    public BookAPI(String book_title) {
        this.book_title = book_title;
    }

    public String searchAPI(){
        try {
            book_title = URLEncoder.encode(book_title, "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException("검색어 인코딩 실패",e);
        }

        String apiURL = "https://openapi.naver.com/v1/search/book.xml?query=" + book_title;

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        String responseBody = get(apiURL,requestHeaders);

        Log.e(",EEEEE",responseBody);

        return responseBody;
    }

    private static String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 에러 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }
}

