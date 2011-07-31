package soap.messageOriented.server;

import java.util.Date;

import javax.jws.Oneway;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
@org.apache.cxf.interceptor.InInterceptors (interceptors = {"org.apache.cxf.interceptor.LoggingInInterceptor" })

public class ReceiveTweetsImpl implements ReceiveTweets {
	
	@Oneway
	public void receiveTweets(
			@WebParam(name="ID") long ID,
			@WebParam(name="username") String username,
			@WebParam(name="message") String message,
			@WebParam(name="date") Date date) {
		System.out.println("Received tweet " + ID + " from @" + username + " saying \"" + message + "\" " + " at " + date);
	}

}