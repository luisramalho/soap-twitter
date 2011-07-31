package soap.serviceOriented.server;

import javax.xml.ws.Endpoint;

public class Server {
	protected Server() {
        // START SERVER
        System.out.println("Starting Server");
        
        TwitterService ts = new TwitterService();
        
        String address = "http://localhost:9000/twitterWebService";

        // Publish
        Endpoint.publish(address, ts);

    }

    public static void main(String args[]) {
        new Server();
        System.out.println("SOAP Service Server READY");
        try {
			Thread.sleep(5 * 60 * 1000); // Timeout
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        System.out.println("SOAP Service Server EXITING");
        System.exit(0);
    }
}
