package soap.serviceOriented.server;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService
public interface TwitterServiceImpl {
	
	/**
	 * Submits a tweet
	 * @param tweet
	 */
	@WebResult(name="response")
	void submitTweet(@WebParam(name="tweet") String tweet);

	/**
	 * Eetrieve a tweet based on its ID
	 * @param tweetID
	 */
	@WebResult(name="response")
	void retrieveTweet(@WebParam(name="tweetID") String tweetID);

}