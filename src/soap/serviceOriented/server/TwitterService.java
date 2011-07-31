package soap.serviceOriented.server;

import javax.jws.WebService;

import twitter.api.TwitterAPI;

@WebService(endpointInterface = "soap.serviceOriented.server.TwitterServiceImpl", serviceName = "TwitterServiceImpl")
public class TwitterService implements TwitterServiceImpl {
	
	@Override
	public void submitTweet(String tweet) {
		TwitterAPI api = new TwitterAPI();
		api.submitTweet(tweet);
	}
	
	@Override
	public void retrieveTweet(String tweetID) {
		TwitterAPI api = new TwitterAPI();
		api.retrieveTweet(tweetID);
	}

}