//package com.ludogoriesoft.job.crawler.JobCrawler.exception;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<String> handleUnexpectedError(Exception ex){
//        return ResponseEntity
//                .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body("Error occurred: " + ex);
//    }
//}
