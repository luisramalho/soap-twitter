package twitter.api;

import java.util.List;

import javax.jws.WebResult;
import javax.jws.WebService;

import twitter4j.Tweet;

@WebService
public interface ITwitterAPI {
	/**
	 * Submits a new tweet
	 * @param tweet The tweet to be submitted
	 */
	@WebResult(name="response")
	public void submitTweet(String tweet);

	/**
	 * Queries tweets by their ID
	 * @param tweetID The ID of the tweet to query
	 */
	@WebResult(name="response")
	public void retrieveTweet(String tweetID);
	
	/**
	 * Searchs tweets based on the keyword provided
	 * @param keyword
	 */
	@WebResult(name="response")
	public List<Tweet> searchTweets(String keyword);
}
