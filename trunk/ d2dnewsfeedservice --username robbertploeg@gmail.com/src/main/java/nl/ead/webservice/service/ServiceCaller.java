package nl.ead.webservice.service;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.IOException;


/**
 * User: Robbert
 * Date: 29-5-13
 * Time: 11:25
 */
public class ServiceCaller {
    String FACEBOOK_URL = "graph.facebook.com";
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
        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod("http://graph.facebook.com/" + username);

        try {
            client.executeMethod(method);

            if (method.getStatusCode() == HttpStatus.SC_OK) {
                String response = method.getResponseBodyAsString();
                System.out.println("Response = " + response);
                System.out.println("GET Success: OK");
                return response;
            } else if (method.getStatusCode() == HttpStatus.SC_BAD_REQUEST) {
                String fail_response = method.getResponseBodyAsString();
                System.out.println("GET Error: BAD REQUEST, Server Response: \n" + fail_response);
                return "";
            }
            generic_error = method.getResponseBodyAsString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            method.releaseConnection();
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
        String response = ARTICLEAPI_URL;
        return response;
    }
}
