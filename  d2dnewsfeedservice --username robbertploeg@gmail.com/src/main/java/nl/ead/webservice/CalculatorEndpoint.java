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
public class CalculatorEndpoint {
    private Marshaller marshaller;
    private Unmarshaller unmarshaller;

    public CalculatorEndpoint(Marshaller marshaller, Unmarshaller unmarshaller) {
        this.marshaller = marshaller;
        this.unmarshaller = unmarshaller;
    }

    @SuppressWarnings({"unchecked", "deprecation"})
    @PayloadRoot(localPart = "CalculateRequest", namespace = "http://www.han.nl/schemas/messages")
    public CalculateResponse calculateSumForName(CalculateRequest req) {
        // a sequence of a minimum of 1 and unbounded max is generated as a
        // List<>
        List<Integer> paramList = req.getInput().getParamlist().getParam();
        CalculateOperation op = req.getInput().getOperation();
        int retValue = paramList.get(0);

        for (int i = 1; i < paramList.size(); i++) {
            // CalculateOperation is generated as an enum
            if (op.equals(CalculateOperation.ADD)) {
                retValue += paramList.get(i).intValue();
            } else if (op.equals(CalculateOperation.SUBTRACT)) {
                retValue -= paramList.get(i).intValue();
            } else if (op.equals(CalculateOperation.MULTIPLY)) {
                retValue *= paramList.get(i).intValue();
            } else if (op.equals(CalculateOperation.DIVIDE)) {
                retValue /= paramList.get(i).intValue();
            }
        }

        ServiceCaller svc = new ServiceCaller();
        InterestParser inp = new InterestParser();
        ArrayList<String> facebook_response;
        ArrayList<Interest> parsed_interests;

        facebook_response = svc.connectToFacebook("robbertploeg");
        parsed_interests = inp.processInterests(facebook_response);
        for (Interest ist : parsed_interests) {
            System.out.print(ist.getName() + ", ");
        }

        ArrayList<Interest> offline_test_interests = new ArrayList<Interest>();
        offline_test_interests.add(new Interest("Computers"));
        offline_test_interests.add(new Interest("Games"));

        // use the offline interest_list if access token is no longer valid. will be fixed.
        String harro = svc.connectToArticleAPI(offline_test_interests);
        System.out.println(harro);

        CalculateResult result = new CalculateResult();
        result.setMessage("Response Sent");
        result.setValue(retValue);
        CalculateResponse resp = new CalculateResponse();
        resp.setResult(result);
        return resp;
    }
}
