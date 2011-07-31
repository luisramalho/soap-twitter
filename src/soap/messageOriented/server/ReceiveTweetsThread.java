package soap.messageOriented.server;

import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;
import javax.xml.ws.wsaddressing.W3CEndpointReference;

import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.ws.addressing.EndpointReferenceType;
import org.apache.cxf.wsdl.EndpointReferenceUtils;
import org.w3c.dom.Element;

import twitter.api.TwitterAPI;
import twitter4j.Tweet;

public class ReceiveTweetsThread extends Thread implements Runnable {

    boolean run = true;
    boolean called = false;
    BlockingQueue<Tuple> queueTweets = new LinkedBlockingQueue<Tuple>(50);
    TwitterAPI api = new TwitterAPI();
    String keyword = "";
    Date date = new Date();
    EndpointReferenceType eprType = null;

    public void scheduleReceiveTweets(String keyword, EndpointReferenceType eprType) {
    	called = true;
    	date.setTime(0);
    	this.keyword = keyword;
    	this.eprType = eprType;
    	
        try {
        	List<Tweet> tweets = api.searchTweets(keyword);
        	for (Tweet tweet : tweets) {
        		queueTweets.put(new Tuple(tweet, eprType));
        		// Latest Tweet
        		if(tweet.getCreatedAt().after(date)) {
        			date = tweet.getCreatedAt();
        		}
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void run() {
        System.out.println("ReceiveTweets Thread started.. ");
        while (run) {
	        if(called) {
	        	try {
	        		Thread.sleep(500); // 0.5 sec
	        		Tuple tuple = queueTweets.poll();
	        		if (tuple != null) {
	        			processCall(tuple);
	        		} else {
	        			List<Tweet> tweets = api.searchTweets(keyword);
	                	for (Tweet tweet : tweets) {
	                		if(tweet.getCreatedAt().after(date)) {
	                			queueTweets.put(new Tuple(tweet, eprType));
		                		// Latest Tweet
		                		if(tweet.getCreatedAt().after(date)) {
		                			date = tweet.getCreatedAt();
		                		}
	                		} else {
	                			Thread.sleep(1000); // Sleep for 1 sec
	                		}
	                    }
	        		}
	        	} catch (InterruptedException e) {
	        		e.printStackTrace();
	        	}
	        }
        }
    }

	private void processCall(Tuple tuple) {
        Service service = createService(tuple.epr);
        if (service != null) {
    		W3CEndpointReference reference = new W3CEndpointReference(EndpointReferenceUtils.convertToXML(tuple.epr));
    		ReceiveTweets rt = service.getPort(reference, ReceiveTweets.class);
			rt.receiveTweets(tuple.tweet.getId(), tuple.tweet.getFromUser(), tuple.tweet.getText(), tuple.tweet.getCreatedAt());
        }
    }
	
	private static Service createService(EndpointReferenceType referenceType) {
        List<Object> any = referenceType.getMetadata().getAny();
        String namespace = null;
        String serviceName = null;
        String endpointName = null;

        for (Object a : any) {
            Element element = (Element) a;
            endpointName = element.getAttribute("EndpointName");
            String serviceQnameString = element.getTextContent().trim();
            String[] split = null;
            if (serviceQnameString != null && (split = serviceQnameString.split(":")).length == 2) {
                namespace = DOMUtils.getNamespace(element, split[0]);
                serviceName = split[1];
            }
        }

        if (serviceName != null && endpointName != null && namespace != null) {
            QName serviceQName = new QName(namespace, serviceName);
            QName portQName = new QName(namespace, endpointName);

            Service service = Service.create(serviceQName);
            service.addPort(portQName, SOAPBinding.SOAP11HTTP_BINDING, referenceType.getAddress().getValue());
            return service;
        }

        return null;
    }

    public void stopProcessing() {
        this.run = false;
    }
    
    class Tuple {
        Tweet tweet;
        EndpointReferenceType epr;

        public Tuple(Tweet tweet, EndpointReferenceType epr) {
            super();
            this.tweet = tweet;
            this.epr = epr;
        }
    }

}