package com.edw.service;

import com.edw.wsdl.bean.hello.EmployeeIdWrapper;
import com.edw.wsdl.bean.hello.EmployeeInfoWrapper;
import com.edw.wsdl.bean.hello.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import javax.xml.bind.JAXBElement;

/**
 * <pre>
 *     com.edw.service.ExternalRequestService
 * </pre>
 *
 * @author Muhammad Edwin < edwin at redhat dot com >
 * 02 Mar 2021 15:08
 */
@Service
public class ExternalRequestService extends WebServiceGatewaySupport {

    @Autowired
    private WebServiceTemplate webServiceTemplate;

    public String getName() {

        EmployeeIdWrapper employeeIdWrapper = new EmployeeIdWrapper();
        employeeIdWrapper.getEid().add("001002");

        JAXBElement<EmployeeInfoWrapper> employeeInfoJaxB = (JAXBElement<EmployeeInfoWrapper>) webServiceTemplate
                .marshalSendAndReceive("http://localhost:8082",
                        new ObjectFactory().createEmployeeIdList(employeeIdWrapper));

        return employeeInfoJaxB.getValue()
                .getEmployeeInfo()
                .get(0)
                .getFirstName();
    }
}
