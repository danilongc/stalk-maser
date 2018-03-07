package com.dnc.stalkmaster.exception;

import com.dnc.stalkmaster.domain.GenericRestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandle  {


    @ResponseBody
    @ExceptionHandler({BusinessException.class})
    public ResponseEntity<?> serviceRequestException(BusinessException e ){

        GenericRestResponse res = new GenericRestResponse(e.getCode(), e.getMessage());
        HttpStatus status = HttpStatus.BAD_REQUEST;

        if(e.getStatus() != null){
            status =  e.getStatus();
        }

        return new ResponseEntity<>(res, status);
    }
}
