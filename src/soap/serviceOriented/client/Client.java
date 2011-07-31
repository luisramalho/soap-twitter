package soap.serviceOriented.client;

import javax.jws.WebService;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

import soap.serviceOriented.server.TwitterServiceImpl;

@WebService
@org.apache.cxf.interceptor.InInterceptors (interceptors = {"org.apache.cxf.interceptor.LoggingInInterceptor" })
public class Client {

	private static final QName SERVICE_NAME = new QName("http://server.serviceOriented.soap/", "TwitterServiceImpl");
	private static final QName PORT_NAME = new QName("http://server.serviceOriented.soap/", "TwitterServiceImplPort");
	
	private Client() {} 

	public static void main(String args[]) throws Exception {
		// Creates a new service
		Service service = Service.create(SERVICE_NAME);
		
		// End point Address
		String endpointAddress = "http://localhost:9000/twitterWebService";

		// Adds a port to the previously created service 
		service.addPort(PORT_NAME, SOAPBinding.SOAP11HTTP_BINDING, endpointAddress);

		TwitterServiceImpl ts = service.getPort(TwitterServiceImpl.class);
		
		// Submits a tweet using service
		ts.submitTweet("Testing more stuff.");
		
		// Retrieve a tweet based on its ID using service.
		ts.retrieveTweet("66172092370006017");

	}
}
