package nl.ead.webservice.service;

/**
 * User: Robbert
 * Date: 29-5-13
 * Time: 11:25
 */
public class ServiceCaller {
    String FACEBOOK_URL = "";
    String ARTICLEAPI_URL = "";

    public ServiceCaller(){

    }

    /**
     * This method uses the FACEBOOK_URL to connect to the facebookAPI.
     * It needs the facebook username to get the correct data.
     * @param username
     * @return
     */
    public String connectToFacebook(String username){
        String response = FACEBOOK_URL;
        return response;
    }

    /**
     * This method uses the ARTICLEAPI_UR to connect to the ArticleAPI.
     * It needs the interest name to get the correct data.
     * @param interest
     * @return
     */
    public String connectToArticleAPI(String interest){
        String response = ARTICLEAPI_URL;
        return response;
    }
}
