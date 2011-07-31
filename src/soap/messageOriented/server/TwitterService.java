package soap.messageOriented.server;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.soap.Addressing;

import org.apache.cxf.jaxrs.ext.Oneway;

@WebService(targetNamespace=TwitterService.NAMESPACE)
@Addressing(enabled=true, required=true)

public interface TwitterService {
	String NAMESPACE = "http://server.messageOriented.soap/";

	@WebResult(name="response")
	@Oneway
	public void searchTweets(@WebParam(name="keyword") String keyword);
}