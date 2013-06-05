package nl.ead.webservice.service;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.exception.FacebookOAuthException;
import com.restfb.types.User;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * User: Robbert
 * Date: 29-5-13
 * Time: 11:25
 */
public class ServiceCaller {
    String ACCESS_TOKEN = "CAACEdEose0cBAK55jHjz86448JWZBuNaJeqZCS4UiQOVt6BnvvkUZABf3fMUPHcZC3pz9FqAWhiyyCTeoEAAx31Pw3VczDSHE1wHZCZA2hhPgrMt8MAa3CYICpqq8Rl0y3XT4eCnlL8JZCRvta0dI4OIOZCY72AxZCCN1Ev1S5jdfZAAZDZD";

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

    public String connectToArticleAPI(String interest) {
        System.out.println("(--- ARTICLE API CALL ---)");
        String generic_error = "";
        // String response =""; //ARTICLEAPI_URL;
        String url = "http://www.diffbot.com/api/article?token=344e65eee509748803505554ac1615fe&url=";
        String article_url = "http://well.blogs.nytimes.com/2013/05/31/new-tricks-for-old-grains/?ref=health";

        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod(url + article_url);

        try {
            client.executeMethod(method);

            if (method.getStatusCode() == HttpStatus.SC_OK) {
                String response = method.getResponseBodyAsString();
                JSONObject test = new JSONObject(response);
                System.out.println(test);
                System.out.println("GET Success: OK");
                //System.out.println("Article getter replied with: " + response);
                return response;
            } else if (method.getStatusCode() == HttpStatus.SC_BAD_REQUEST) {
                generic_error = method.getResponseBodyAsString();
                System.out.println("GET Error: BAD REQUEST, Server Response: \n" + generic_error);
                return null;
            }
            generic_error = method.getResponseBodyAsString();

        } catch (IOException e) {
            return "IOException.";
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            method.releaseConnection();
        }


        return "GET Request Failed, Server Response: \n" + generic_error;
    }
}
