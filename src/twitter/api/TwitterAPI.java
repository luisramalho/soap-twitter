package twitter.api;

import java.util.List;

import javax.jws.WebService;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Tweet;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

/**
 * SOAP-based interface to Twitter
 * 
 * SERVER - Can submit and query tweets;
 * (Essentially the server bridges SOAP to the Twitter API.)
 * 
 * @author 080010830
 */

@WebService(endpointInterface = "twitter.service.ITwitterAPI", serviceName = "ITwitterAPI")
public class TwitterAPI implements ITwitterAPI {
	
	public static Twitter twitter;
	
	public TwitterAPI() {
		// The factory instance is re-usable and thread safe.
	    twitter = new TwitterFactory().getInstance();
	}

	public void submitTweet(String tweet) {
		try {
	    	Status status = twitter.updateStatus(tweet);
			System.out.println("Successfully submitted the status [" + status.getText() + "].");
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to submit tweet: " + te.getMessage());
		}
	}

	public void retrieveTweet(String tweetID) {
		try {
			Status status = twitter.showStatus(Long.parseLong(tweetID));
	        System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
	    } catch (TwitterException te) {
	    	te.printStackTrace();
	    	System.out.println("Failed to show status: " + te.getMessage());
	    }
	}

	public List<Tweet> searchTweets(String keyword) {
		List<Tweet> tweets = null;
		try {
            QueryResult result = twitter.search(new Query(keyword));
            tweets = result.getTweets();
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
        }
        return tweets;
	}

}
