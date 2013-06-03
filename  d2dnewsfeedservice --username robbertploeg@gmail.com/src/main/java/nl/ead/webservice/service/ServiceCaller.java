package nl.ead.webservice.service;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


/**
 * User: Robbert
 * Date: 29-5-13
 * Time: 11:25
 */
public class ServiceCaller {
    String ARTICLEAPI_URL = "";

    public ServiceCaller() {

    }

    /**
     * This method uses the FACEBOOK_URL to connect to the facebookAPI.
     * It needs the facebook username to get the correct data.
     *
     * @param username
     * @return
     */
    public String connectToFacebook(String username) {
        String generic_error = "";
        String url = "http://graph.facebook.com/";
        String fields = "?fields=id,name,locale";
        HttpClient client = new HttpClient();

        // example = http://graph.facebook.com/robbertploeg?fields=id,name -- returns JSON with id + name
        HttpMethod getRequest = new GetMethod(url + username + fields);

        try {
            client.executeMethod(getRequest);

            if (getRequest.getStatusCode() == HttpStatus.SC_OK) {
                String response = getRequest.getResponseBodyAsString();
                System.out.println("["+System.currentTimeMillis()+"]"+" GET Success: OK");
                System.out.println("graph.facebook.com replied with: " + response);
                return response;
            } else if (getRequest.getStatusCode() == HttpStatus.SC_BAD_REQUEST) {
                generic_error = getRequest.getResponseBodyAsString();
                System.out.println("GET Error: BAD REQUEST, Server Response: \n" + generic_error);
                return null;
            }
            generic_error = getRequest.getResponseBodyAsString();

        } catch (IOException e) {
            return "IOException.";
        } finally {
            getRequest.releaseConnection();
        }


        return "GET Request Failed, Server Response: \n" + generic_error;
    }


    /**
     * This method uses the ARTICLEAPI_UR to connect to the ArticleAPI.
     * It needs the interest name to get the correct data.
     *
     * @param interest
     * @return
     */
    public String connectToArticleAPI(String interest) {

        String generic_error = "";
       // String response =""; //ARTICLEAPI_URL;
        String beginningOfReqeustArticels = "http://www.diffbot.com/api/article?token=344e65eee509748803505554ac1615fe&url=";
        String article = "http://well.blogs.nytimes.com/2013/05/31/new-tricks-for-old-grains/?ref=health";

        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod(beginningOfReqeustArticels + article);

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
