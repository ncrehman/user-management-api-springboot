/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rehman.controller;

import com.rehman.modals.RequestModel;
import com.rehman.modals.ResponseModel;
import com.rehman.service.RolesService;
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
public class RolesController {

    @Autowired
    RolesService service;

    @RequestMapping(value = "/createroles")
    public @ResponseBody
    ResponseEntity<ResponseModel> createRoles(@RequestBody RequestModel requestModelObj) {
        ResponseModel response = service.createRoles(requestModelObj);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/updateroles")
    public @ResponseBody
    ResponseEntity<ResponseModel> updateroles(@RequestBody RequestModel requestModelObj) {
        ResponseModel response = service.updateRoles(requestModelObj);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/deleteroles")
    public @ResponseBody
    ResponseEntity<ResponseModel> deleteroles(@RequestBody RequestModel requestModelObj) {
        ResponseModel response = service.deleteRolesById(requestModelObj);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/getrolesbyid")
    public @ResponseBody
    ResponseEntity<ResponseModel> getrolesbyid(@RequestBody RequestModel requestModelObj) {
        ResponseModel response = service.getRolesById(requestModelObj);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/getallroles")
    public @ResponseBody
    ResponseEntity<ResponseModel> getallroles(@RequestBody RequestModel requestModelObj) {
        ResponseModel response = service.getAllRoles(requestModelObj);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
