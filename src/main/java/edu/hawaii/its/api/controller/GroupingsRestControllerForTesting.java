package edu.hawaii.its.api.controller;

import edu.hawaii.its.groupings.access.User;
import edu.hawaii.its.groupings.access.UserContextService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import edu.hawaii.its.api.service.HttpRequestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/groupings/testing")
public class GroupingsRestControllerForTesting {

    private static final Log logger = LogFactory.getLog(GroupingsRestControllerForTesting.class);

    @Value("${url.api.2.1.base}/testing")
    private String API_2_1_BASE;

    @Autowired
    private HttpRequestService httpRequestService;

    @Autowired
    private UserContextService userContextService;

    @GetMapping(value = "/exception")
    public ResponseEntity<String> throwException() {
        logger.info("Entered REST throwException...");
        String principalName = userContextService.getCurrentUserName();
        String uri = String.format(API_2_1_BASE + "/exception");
        return httpRequestService.makeApiRequest(principalName, uri, HttpMethod.GET);
    }
}
