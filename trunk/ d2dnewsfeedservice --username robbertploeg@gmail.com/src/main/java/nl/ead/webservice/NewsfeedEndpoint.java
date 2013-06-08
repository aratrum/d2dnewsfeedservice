package nl.ead.webservice;

import nl.ead.webservice.core.InterestParser;
import nl.ead.webservice.entity.Interest;
import nl.ead.webservice.service.ServiceCaller;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;

import java.util.ArrayList;
import java.util.List;

@Endpoint
public class NewsfeedEndpoint {
    private Marshaller marshaller;
    private Unmarshaller unmarshaller;

    public NewsfeedEndpoint(Marshaller marshaller, Unmarshaller unmarshaller) {
        this.marshaller = marshaller;
        this.unmarshaller = unmarshaller;
    }

    @SuppressWarnings({"unchecked", "deprecation"})
    @PayloadRoot(localPart = "NFRequest", namespace = "http://www.han.nl/schemas/messages")
    public NFResponse generateNewsfeed(NFRequest req) {
        // GET THE INPUT FROM THE REQUEST
        Input requested_input = req.getInput();
        String requested_name = requested_input.getName();

        ServiceCaller svc = new ServiceCaller();
        InterestParser inp = new InterestParser();
        ArrayList<String> facebook_response;
        ArrayList<Interest> parsed_interests;
        ArrayList<Interest> offline_test_interests = new ArrayList<Interest>();
        offline_test_interests.add(new Interest("Computers"));
        offline_test_interests.add(new Interest("Games"));

        // SEARCH ON FACEBOOK FOR INTERESTS WITH THAT PARTICULAR NAME
        facebook_response = svc.connectToFacebook(requested_name);
        parsed_interests = inp.processInterests(facebook_response);
        for (Interest ist : parsed_interests) {
            System.out.print(ist.getName() + ", ");
        }


        // SEARCH ON THE ARTICLE API FOR ARTICLES MATCHING THE INTERESTS
        String harro = svc.connectToArticleAPI(offline_test_interests);
        System.out.println(harro);

        // PUT THE RESULTS IN THE RESPONSE MESSAGE
        Result result = new Result();
        result.setMessage("Searched for: " + requested_name);
        NFResponse resp = new NFResponse();
        resp.setResult(result);
        return resp;
    }
}
