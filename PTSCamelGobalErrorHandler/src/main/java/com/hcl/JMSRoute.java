package com.hcl;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
@Component
public class JMSRoute extends RouteBuilder {

	private static final Logger Log = LoggerFactory.getLogger(JMSRoute.class);

	@Override
	public void configure() throws Exception {
		// TODO Auto-generated method stub
		from("{{inbound.endpoint}}")
		.transacted()
		.log(LoggingLevel.INFO,Log,"RECEIVED MESSAGE")
		.process(new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception{
				Log.info("Exchange:{}",exchange);
			}
		})
        .loop()
        .simple("{{outbound.loop.count}}")
        .to("{{outbound.endpoint}}")
        .log(LoggingLevel.INFO,Log,"MESSAGE SENT. Iteration: ${property.CamelLoopindex}")
        .end();
	}

}
