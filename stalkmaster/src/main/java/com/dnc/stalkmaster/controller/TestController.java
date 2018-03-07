package com.dnc.stalkmaster.controller;

import com.dnc.stalkmaster.service.instagram.InstagramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private InstagramUserService userService;

    @RequestMapping(path = "/user-info", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getUserInfo(){
        return new ResponseEntity<Object>(userService.getUserSelf(), HttpStatus.OK);
    }


    @RequestMapping(path = "/user-profile-picture", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<?> getProfilePicture(){
        return  new ResponseEntity<byte[]>(userService.getProfilePictureData(), HttpStatus.OK);
    }
}
