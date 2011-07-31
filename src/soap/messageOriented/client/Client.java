package soap.messageOriented.client;

import static org.apache.cxf.ws.addressing.JAXWSAConstants.CLIENT_ADDRESSING_PROPERTIES;

import java.util.Map;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.ws.addressing.AddressingPropertiesImpl;
import org.apache.cxf.ws.addressing.EndpointReferenceType;
import org.apache.cxf.ws.addressing.soap.VersionTransformer;

import soap.messageOriented.server.ReceiveTweetsImpl;
import soap.messageOriented.server.TwitterService;

public class Client {

    private static final String TWITTERSERVICE_EP_URL = "http://localhost:9091/sendKeyword";
    private static final String RECEIVETWEETS_EP_URL = "http://localhost:9092/receiveTweets";

    public static void main(String[] args) {

        EndpointReferenceType receiveTweetsEP = startReceiveTweetsEndpoint();
        TwitterService ts = getTwitterService();
        setReplyDestination(ts, receiveTweetsEP);
        
        // Search for tweets with this keyword
        ts.searchTweets("@starbucks");
        
        // When finished.
        System.out.println("DONE.");
    }

    private static EndpointReferenceType startReceiveTweetsEndpoint() {
        EndpointImpl endpoint = (EndpointImpl) Endpoint.publish(RECEIVETWEETS_EP_URL, new ReceiveTweetsImpl());
        return VersionTransformer.convertToInternal(endpoint.getEndpointReference());
    }

    private static void setReplyDestination(TwitterService ts, EndpointReferenceType replyTo) {

        AddressingPropertiesImpl maps = new AddressingPropertiesImpl();
        maps.setReplyTo(replyTo);

        Map<String, Object> requestContext = ((BindingProvider) ts).getRequestContext();
        requestContext.put(CLIENT_ADDRESSING_PROPERTIES, maps);

    }

    private static TwitterService getTwitterService() {
        QName serviceName = new QName(TwitterService.NAMESPACE, "TwitterService");
        QName portName = new QName(TwitterService.NAMESPACE, "TwitterServicePorts");

        Service service = Service.create(serviceName);
        service.addPort(portName, SOAPBinding.SOAP11HTTP_BINDING, TWITTERSERVICE_EP_URL);

        TwitterService ts = service.getPort(portName, TwitterService.class);
        return ts;
    }
}
