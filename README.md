# udp and protocol buffers in netty 
This Library helps to understand:

<li>how to use many handlers in a pipeline
<li>how to use protocol buffers in netty
<li>how to use udp(user datagram protocol) inside netty
<li>how to combine layered architecture and event based architecture
<li>how and when to pass variables to the next handler
<li>how to use java enum to encode different commands as integers

##Motivation
Combining netty and RxJava for infrastructure development is essential for
businesses that need to scale their business to more than 500 million users or devices(in IOT).
This architecture creates greater throughput and extremely high concurrency in
comparison with traditional blocking approaches of Apache. By utilizing
more pipelines rather than just one pipeline we can combine the following architectures:
<li> traditional layered architecture (very traditional and old J2EE frameworks )
<li> event based architecture( both mediator and broker) (used in Netflix and Facebook and linkedin)
<li> microservices  (used in Amazon)

### how to use many channel handlers in a pipeline
 Since Only one SimpleChannelInboundHandler can be used for each pipeline, we should use
 ChannelInboundHandlerAdapter and manually tell netty when to pass variables
 to the next handler.

###  how to build protocol buffer in java
protoc --java_out=src/main/java    src/main/resources/proto/UDP.proto
<code>

        ChannelPipeline p = c.pipeline();
        //presentation layer
        p.addLast(new DatagramPacketDecoder(new ProtobufDecoder(Update.getDefaultInstance())));
        p.addLast("idleMonitor", new IdleStateHandler(60, 0, 0));
        p.addLast("idleHandler", new NetStateChangeHandler());
        p.addLast("login",new LoginHandler());
        //business logic
        p.addLast("switch",new Multiplexer());
        p.addLast("server",new Server());
</code>