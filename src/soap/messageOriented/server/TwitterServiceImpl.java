package soap.messageOriented.server;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.soap.Addressing;

import org.apache.cxf.ws.addressing.AddressingProperties;
import org.apache.cxf.ws.addressing.EndpointReferenceType;

@WebService
@Addressing
@org.apache.cxf.interceptor.InInterceptors(interceptors = {"org.apache.cxf.interceptor.LoggingInInterceptor"})
public class TwitterServiceImpl implements TwitterService {

	ReceiveTweetsThread rtt;

    public TwitterServiceImpl() {
    	rtt = new ReceiveTweetsThread();
    	rtt.start();
    }

    @Resource
    WebServiceContext webServiceContext;

	public void searchTweets(String keyword) {
		MessageContext messageContext = webServiceContext.getMessageContext();

        AddressingProperties addressProp = (AddressingProperties) messageContext
        	.get(org.apache.cxf.ws.addressing.JAXWSAConstants.SERVER_ADDRESSING_PROPERTIES_INBOUND);

        EndpointReferenceType eprType = addressProp.getReplyTo();
        rtt.scheduleReceiveTweets(keyword, eprType);
		
	}
}