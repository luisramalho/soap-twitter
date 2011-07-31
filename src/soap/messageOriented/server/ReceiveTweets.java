package soap.messageOriented.server;

import java.util.Date;

import javax.jws.Oneway;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;

@WebService()
public interface ReceiveTweets {
	String NAMESPACE = "http://server.messageOriented.soap/";
	
	@Oneway
	@RequestWrapper(localName="searchTweetsResponse", targetNamespace=NAMESPACE)
	public void receiveTweets(
			@WebParam(name="ID") long ID,
			@WebParam(name="username") String username,
			@WebParam(name="message") String message,
			@WebParam(name="date") Date date
	);
}