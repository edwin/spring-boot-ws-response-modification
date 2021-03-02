package com.edw.controller;

import com.edw.service.ExternalRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *     com.edw.controller.MainController
 * </pre>
 *
 * @author Muhammad Edwin < edwin at redhat dot com >
 * 02 Mar 2021 14:51
 */
@RestController
public class MainController {

    @Autowired
    private ExternalRequestService externalRequestService;

    @GetMapping("/")
    public Map index() {
        return new HashMap(){{
            put("response", externalRequestService.getName());
        }};
    }
}
