package soap.messageOriented.server;

import javax.xml.ws.Endpoint;

public class Server {
	
	protected Server() {
    	// START SERVER
        System.out.println("Starting Server");
        
        TwitterServiceImpl tsi = new TwitterServiceImpl();
        
        String address = "http://localhost:9091/sendKeyword";
        
        // Publish
    	Endpoint.publish(address, tsi);
    }
	
    public static void main(String[] args) {
    	new Server();
    	System.out.println("SOAP Message Server READY");
        try {
			Thread.sleep(5 * 60 * 1000); // Timeout
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
        System.out.println("SOAP Message Server EXITING");
        System.exit(0);
    }

}
