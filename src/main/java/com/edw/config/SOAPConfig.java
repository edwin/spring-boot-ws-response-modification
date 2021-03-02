package com.edw.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;

/**
 * <pre>
 *     com.edw.config.SOAPConfig
 * </pre>
 *
 * @author Muhammad Edwin < edwin at redhat dot com >
 * 02 Mar 2021 15:23
 */
@Configuration
public class SOAPConfig {
    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan("com.edw.wsdl.bean.hello");
        return marshaller;
    }

    @Bean
    public WebServiceTemplate webServiceTemplate() {
        WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
        webServiceTemplate.setMarshaller(marshaller());
        webServiceTemplate.setUnmarshaller(marshaller());

        // register our interceptor here
        webServiceTemplate.setInterceptors(new ClientInterceptor[]{new SOAPClientInterceptor()});

        return webServiceTemplate;
    }
}
