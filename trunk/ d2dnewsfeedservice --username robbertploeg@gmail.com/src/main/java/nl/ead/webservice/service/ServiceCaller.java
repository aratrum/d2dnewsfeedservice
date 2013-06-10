package nl.ead.webservice.service;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.exception.FacebookOAuthException;
import com.restfb.types.User;
import nl.ead.webservice.entity.Interest;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * User: Robbert
 * Date: 29-5-13
 * Time: 11:25
 */
public class ServiceCaller {
    String ACCESS_TOKEN = "CAACEdEose0cBAG9lKUGRqk39rAvjMZAZAhXZAF00ZCclxZAWS0nwXatrY62e6uOqvKmxH7W89ZC2Lpez0aeCFOMDY2hKaev3osq6FgVfjbPJTVg9KzCyq4Bhi5Rv5y2LCWsq1STYOjSo96F8wzn5n4L3AUnRx1PqcZD";

    public ServiceCaller() {

    }

    public ArrayList<String> connectToFacebook(String username) {
        ArrayList<String> interests;
        FacebookClient client = new DefaultFacebookClient(ACCESS_TOKEN);

        try {
            User user_data = client.fetchObject(username, User.class);
            System.out.println("Name: " + user_data.getLastName() + ", " + user_data.getFirstName());
            // returns JSONObject collection
            Connection<String> user_connections = client.fetchConnection(username + "/likes", String.class);

            interests = new ArrayList<String>();

            for (List<String> users : user_connections) {
                for (String like : users) {
                    try {
                        JSONObject json = new JSONObject(like);
                        interests.add(json.getString("category"));
                    } catch (JSONException e) {
                        System.out.println("JSON EXCEPTION");
                    }
                }
            }
            return interests;

        } catch (NullPointerException e) {
            System.out.println("Facebook API did not return anything." +
                    "\nException message: '" + e.getMessage() + "'");
        } catch (FacebookOAuthException e) {
            System.out.println("Facebook API returned an Exception." +
                    "\nException message: '" + e.getMessage() + "'");
        }

        return null;
    }

    public String connectToArticleAPI(ArrayList<Interest> interest) throws IOException {
        JSONArray result = new JSONArray();

        System.out.println("(--- ARTICLE API CALL ---)");
        String generic_error = "";
        // String response =""; //ARTICLEAPI_URL;
        String getArticleUrl = "http://www.diffbot.com/api/article?token=344e65eee509748803505554ac1615fe&url=";
        String article_url = "";
        String beginningSearchString = "http://www.nu.nl/zoeken/?q=";
        HttpClient client = new HttpClient();
        int z = 0;
        System.out.println(interest.size());
        for(int j = 0; j < interest.size(); j++)
        {
            Interest item = interest.get(j);
            String url = beginningSearchString + item.getName();
            System.out.println(url);
            Document doc = Jsoup.connect(url).get();
            Elements newsHeadlines = doc.select("#searchlist .subarticle .caption h2 a");
            for(Element src : newsHeadlines)
            {
                if(z == 1)
                {
                    z = 0;
                    break;
                }
                z++;
                HttpMethod method = new GetMethod(getArticleUrl + src.attr("href"));

                try {
                    client.executeMethod(method);
                    if (method.getStatusCode() == HttpStatus.SC_OK) {
                        String response = method.getResponseBodyAsString();
                        result.put(response);

                    } else if (method.getStatusCode() == HttpStatus.SC_BAD_REQUEST) {
                        generic_error = method.getResponseBodyAsString();
                        System.out.println("GET Error: BAD REQUEST, Server Response: \n" + generic_error);
                        return null;
                    }
                    generic_error = method.getResponseBodyAsString();
                } catch (IOException e) {
                    return "IOException.";
                } finally {
                    method.releaseConnection();
                }
            }
        }
        return result.toString();
    }
}
