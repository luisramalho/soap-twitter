# Description
It uses the java library Twitter4J[1] to interact with the Twitter API mainly because
it does not require any other jars and it has built-in OAuth support. Moreover, it is a very complete
library and at the same time not difficult to use. Apache CXF[2] was used for the SOAP communication
betweent client and server, it is an open source services framework. I have decided to go with
this framework because of it extensibility, intuitiveness and the fact that it is relatively easy to use.

The program has two versions:

* Service-oriented version, where there is an RPC-style interaction between the client and the
server to submit and retrieve tweets.

* Message-oriented callback version, where the user can register a special interest in a particular
keyword so that once it occurs the user will receive a callback.


# References
[1] Yusuke Yamamoto. Twitter4J - Unofficial Java library for the Twitter API.
http://twitter4j.org/en/index.html, April 2011. Version 2.2.2.
[2] The Apache Software Foundation. Apache CXF: An Open-Source Services Framework.
http://cxf.apache.org/, April 2011. Version 2.4.0.