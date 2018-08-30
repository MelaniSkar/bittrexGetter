import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;


public class Main {

    public static void main(String[] args) throws IOException {

        try(
                CloseableHttpClient httpClient = HttpClients.createDefault();
                CloseableHttpResponse httpResponse = httpClient.execute(
                new HttpGet("https://bittrex.com/api/v1.1/public/getmarketsummary?market=usd-btc"));
        ) {
            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                JSONTokener tokener = new JSONTokener(
                        IOUtils.toString(entity.getContent()) );
                JSONObject json = new JSONObject(tokener);
                JSONArray result = json.getJSONArray("result");
                JSONObject arrayElem = result.getJSONObject(0);
                String BTC_rate = arrayElem.getString("Last");

                System.out.println(BTC_rate);
            }
        } catch (Throwable cause) {
            cause.printStackTrace();
        }
    }
}
