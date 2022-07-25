package api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import org.json.simple.JSONObject; // JSON객체 생성
import org.json.simple.parser.JSONParser; // JSON객체 파싱
import org.json.simple.parser.ParseException;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;

@RestController
public class ApiExplorer {

    @GetMapping("/api")
    public static void main(String[] args) throws IOException, ParseException {
        //1. URL을 만들기 위한 StringBuilder
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552115/PowerTradingResultInfo/getPowerTradingResultInfo"); /*URL*/

        //2. 오픈 api 요청 규격에 맞는 파라미터 생성하고 발급받은 인증키 등록
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=0HaEVOrsy7wg618yWM2p7kUJdSyLBa%2Fe%2Fmotv2SF84TWmXqLC3zRWXKTqSGAMkNGhIwUz6rn4ZDosRDMEROmKw%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("30", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*응답메시지 형식(xml/json)*/
        urlBuilder.append("&" + URLEncoder.encode("tradeDay","UTF-8") + "=" + URLEncoder.encode("20220101", "UTF-8")); /*거래일자 YYYYMMDD*/

        //3. URL 객체 생성
        URL url = new URL(urlBuilder.toString());

        //4. 요청하고자 하는 URL과 통신하기 위한 커넥션 객체 생성
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        //5. 통신 위한 메소드 SET
        conn.setRequestMethod("GET");

        //6. 통신 위한 Content-type SET
        conn.setRequestProperty("Content-type", "application/json");

        //7. 통신 응답 코드 확인
        System.out.println("Response code: " + conn.getResponseCode());

        //8. 전달받은 데이터를 BufferedReader 객체로 저장
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        //9. 저장된 데이터를 라인별로 읽어 StringBuilder 객체에 저장
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line+"\n\r");
        }

        //10. 객체 해제
        rd.close();
        conn.disconnect();

        //11. 전달받은 데이터 확인
        String result = sb.toString();
        System.out.println("결과: " + result);

        JSONObject jsonObj = (JSONObject) new JSONParser().parse(result);
        jsonObj.get("response"); // 이런 방식으로 데이터 꺼낼 수 있음.

    }
}
