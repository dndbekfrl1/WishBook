package com.jina.wishbook.BookAPI;

import android.util.Log;

import com.jina.wishbook.BuildConfig;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class BookAPI {
    /**
     * curl "https://openapi.naver.com/v1/search/book.xml?query=%EB%B0%94%EB%B3%B4%EC%9D%98%EC%84%B8%EA%B3%84" \
     *         -H "X-Naver-Client-Id: 2Ame9kykI8dCEUi9wXWt" \
     *         -H "X-Naver-Client-Secret: fJEF32yMUr" -v
     */
    private DocumentBuilderFactory documentBuilderFactory = null;
    private DocumentBuilder documentBuilder = null;
    private Document document = null;
    private InputSource inputSource = null;

    private String clientId = BuildConfig.NAVER_SEARCH_ID;
    private String clientSecret = BuildConfig.NAVER_SEARCH_SCRET;
    private String book_title=null;
    private String book_author="";
    private String book_cover=null;
    private String book_link =null;
    private ArrayList<String> data = new ArrayList<>();

    public BookAPI(String book_title) {
        this.book_title = book_title;
    }

    public ArrayList<String> searchAPI() {
        data.add(book_title);
        try {
            book_title = URLEncoder.encode(book_title, "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException("검색어 인코딩 실패",e);
        }

        String apiURL = "https://openapi.naver.com/v1/search/book.xml?query="+book_title+"&display=1&start=1";

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        String responseBody = get(apiURL,requestHeaders);

        parsingXML(responseBody);

        data.add(book_link);
        data.add(book_author);
        data.add(book_cover);

        return data;
    }

    private void parsingXML(String xml)  {
        try {
            documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            inputSource = new InputSource();
            inputSource.setCharacterStream(new StringReader(xml));

            document=documentBuilder.parse(inputSource);
            NodeList nodeList = document.getElementsByTagName("item");

            for(int i=0;i<nodeList.getLength();i++){
                Element element = (Element) nodeList.item(i);

                NodeList title = element.getElementsByTagName("title");
                Element line = (Element) title.item(0);
                Log.e("title",getCharacterDataFromElement(line));

                NodeList link = element.getElementsByTagName("link");
                line = (Element) link.item(0);
                book_link=getCharacterDataFromElement(line);

                NodeList author = element.getElementsByTagName("author");
                line = (Element) author.item(0);
                Log.e("author",getCharacterDataFromElement(line));
                book_author=getCharacterDataFromElement(line);

                NodeList image = element.getElementsByTagName("image");
                line = (Element) image.item(0);
                Log.e("image",getCharacterDataFromElement(line));
                book_cover=getCharacterDataFromElement(line);
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

    }

    public static String getCharacterDataFromElement(Element e) {
        Node child = e.getFirstChild();
        if (child instanceof CharacterData) {
            CharacterData cd = (CharacterData) child;
            return cd.getData();
        }
        return "?";
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

