package com.edw.config;

import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.soap.SoapVersion;
import org.springframework.ws.soap.saaj.SaajSoapMessage;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;

import javax.xml.soap.MessageFactory;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * <pre>
 *     com.edw.config.SOAPClientInterceptor
 * </pre>
 *
 * @author Muhammad Edwin < edwin at redhat dot com >
 * 02 Mar 2021 19:10
 */
public class SOAPClientInterceptor implements ClientInterceptor {

    @Override
    public boolean handleRequest(MessageContext messageContext) throws WebServiceClientException {
        return true;
    }

    @Override
    public boolean handleResponse(MessageContext messageContext) throws WebServiceClientException {
        return true;
    }

    @Override
    public boolean handleFault(MessageContext messageContext) throws WebServiceClientException {
        return false;
    }

    @Override
    public void afterCompletion(MessageContext messageContext, Exception e) throws WebServiceClientException {
        OutputStream s = null;
        InputStream is1 = null;
        try {
            SaajSoapMessage message = (SaajSoapMessage) messageContext.getResponse();
            s = new ByteArrayOutputStream();
            message.writeTo(s);

            String response = s.toString();
            System.out.println("SOAP RESPONSE: " + response);

            // do all string replacement here
            response = response.replace("FOO", "BAR");

            is1 = new ByteArrayInputStream(response.getBytes());

            SaajSoapMessageFactory saaj = new SaajSoapMessageFactory();
            saaj.setSoapVersion(SoapVersion.SOAP_12);
            saaj.setMessageFactory(MessageFactory.newInstance());

            messageContext.clearResponse();
            messageContext.setResponse(saaj.createWebServiceMessage(is1));

            System.out.println("New Response has been SET");
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if(s != null)
                try {
                    s.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            if(is1 != null)
                try {
                    is1.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
        }
    }
}
