/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rehman.controller;

import com.rehman.modals.RequestModel;
import com.rehman.modals.ResponseModel;
import com.rehman.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Rehman
 */
@RestController
@RequestMapping("/")
public class StatusController {

    @Autowired
    StatusService service;

    @RequestMapping(value = "/createstatus")
    public @ResponseBody
    ResponseEntity<ResponseModel> createstatus(@RequestBody RequestModel requestModelObj) {
        ResponseModel response = service.createstatus(requestModelObj);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/updatestatus")
    public @ResponseBody
    ResponseEntity<ResponseModel> updatestatus(@RequestBody RequestModel requestModelObj) {
        ResponseModel response = service.updatestatus(requestModelObj);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/deletestatus")
    public @ResponseBody
    ResponseEntity<ResponseModel> deletestatus(@RequestBody RequestModel requestModelObj) {
        ResponseModel response = service.deletestatusById(requestModelObj);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/getstatusbyid")
    public @ResponseBody
    ResponseEntity<ResponseModel> getstatusbyid(@RequestBody RequestModel requestModelObj) {
        ResponseModel response = service.getstatusById(requestModelObj);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/getallstatus")
    public @ResponseBody
    ResponseEntity<ResponseModel> getallstatus(@RequestBody RequestModel requestModelObj) {
        ResponseModel response = service.getallstatus(requestModelObj);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
