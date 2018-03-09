package com.dnc.stalkmaster.controller;

import com.dnc.stalkmaster.service.instagram.InstagramRelationshipService;
import com.dnc.stalkmaster.service.instagram.InstagramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/insta")
public class InstaController {

    @Autowired
    private InstagramUserService userService;

    @Autowired
    private InstagramRelationshipService relationshipService;


    @RequestMapping(method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> helloWorld(){
        ResponseEntity response = new ResponseEntity("Hello Insta", HttpStatus.OK);
        return response;
    }

    @RequestMapping(path = "/user-info", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getUserInfo(){
        return new ResponseEntity<Object>(userService.getUserSelf(), HttpStatus.OK);
    }

    @RequestMapping(path = "/non-folowers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getNonFolowers(){
        return new ResponseEntity<Object>(relationshipService.getNonFollowers(), HttpStatus.OK);
    }

}
