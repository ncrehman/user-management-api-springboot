/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rehman.service;

import com.rehman.dao.StatusDao;
import com.rehman.utilities.UtilService;
import com.google.gson.Gson;
import com.rehman.modals.RequestModel;
import com.rehman.modals.ResponseModel;
import com.rehman.modals.Status;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

/**
 *
 * @author Rehman
 */
@Service
public class StatusService {

    @Autowired
    UtilService utilService;
    @Autowired
    StatusDao statusDao;
    @Autowired
    MessageSource messageSource;
    private final Gson gson = new Gson();

    public ResponseModel createstatus(RequestModel requestModelObj) {
        ResponseModel responseModel = new ResponseModel();
        String lang = requestModelObj.getLang() != null ? requestModelObj.getLang() : "en";
        try {
            Status statusReqObj = utilService.jsonToObject(gson.toJson(requestModelObj.getReqObject()), Status.class);
            if (statusReqObj != null) {
                long statusObj = statusDao.getStatusCount(statusReqObj.getStatus_name(), statusReqObj.getUsed_for());
                if (statusObj > 0) {
                    responseModel.setStatusCode(6);
                    responseModel.setMessage(messageSource.getMessage("message.duplicates", null, "", Locale.forLanguageTag(lang)));
                } else {
                    statusReqObj.setCreated_date(new Date());
                    Status result = statusDao.save(statusReqObj);
                    if (result != null) {
                        responseModel.setMessage(messageSource.getMessage("message.addsuccess", null, "", Locale.forLanguageTag(lang)));
                        responseModel.setRespObject(result);
                        responseModel.setStatusCode(0);
                    } else {
                        responseModel.setMessage(messageSource.getMessage("message.failedtoadd", null, "", Locale.forLanguageTag(lang)));
                        responseModel.setStatusCode(2);
                    }
                }
            } else {
                responseModel.setMessage(messageSource.getMessage("message.inputerror", null, "", Locale.forLanguageTag(lang)));
                responseModel.setStatusCode(3);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseModel.setMessage(messageSource.getMessage("message.exceptioninmapping", null, "", Locale.forLanguageTag(lang)));
            responseModel.setStatusCode(11);
        }
        return responseModel;
    }

    public ResponseModel updatestatus(RequestModel requestModelObj) {
        ResponseModel responseModel = new ResponseModel();
        String lang = requestModelObj.getLang() != null ? requestModelObj.getLang() : "en";
        try {
            Status statusReqObj = utilService.jsonToObject(gson.toJson(requestModelObj.getReqObject()), Status.class);
            if (statusReqObj != null) {
                Boolean checkStatus = statusDao.getStatusByNameInUpdate(statusReqObj.getStatus_name(), statusReqObj.getId());
                if (checkStatus) {
                    responseModel.setStatusCode(6);
                    responseModel.setMessage(messageSource.getMessage("message.duplicates", null, "", Locale.forLanguageTag(lang)));
                } else {
                    Status result = statusDao.save(statusReqObj);
                    if (result != null) {
                        responseModel.setMessage(messageSource.getMessage("message.updatesuccess", null, "", Locale.forLanguageTag(lang)));
                        responseModel.setStatusCode(0);
                        responseModel.setRespObject(result);
                    } else {
                        responseModel.setMessage(messageSource.getMessage("message.failedtoupdate", null, "", Locale.forLanguageTag(lang)));
                        responseModel.setStatusCode(2);
                    }
                }

            } else {
                responseModel.setMessage(messageSource.getMessage("message.pleasegivecorrectinput", null, "", Locale.forLanguageTag(lang)));
                responseModel.setStatusCode(5);
            }
        } catch (Exception e) {
//            e.printStackTrace();
            System.out.println("mess:" + e.getMessage());
            responseModel.setMessage(e.getMessage());
//            responseModel.setMessage(messageSource.getMessage("message.exceptioninmapping", null, "", Locale.forLanguageTag(lang)));
            responseModel.setStatusCode(11);
        }
        return responseModel;
    }

    public ResponseModel deletestatusById(RequestModel requestModelObj) {
        ResponseModel responseModel = new ResponseModel();
        String lang = requestModelObj.getLang() != null ? requestModelObj.getLang() : "en";
        try {
            Status statusReqObj = utilService.jsonToObject(gson.toJson(requestModelObj.getReqObject()), Status.class);
            if (statusReqObj != null) {
//                Integer counts = usersDao.getUserCountByRoleId(statusReqObj.getId());
                Integer counts = 0;
                if (counts > 0) {
                    responseModel.setStatusCode(9);
                    responseModel.setMessage(messageSource.getMessage("message.dependencycheckdelete", null, "", Locale.forLanguageTag(lang)));
                } else {
                    statusDao.deleteById(statusReqObj.getId());
                    Integer result = 0;
                    switch (result) {
                        case 0:
                            responseModel.setMessage(messageSource.getMessage("message.deletedsuccessfully", null, "", Locale.forLanguageTag(lang)));
                            responseModel.setStatusCode(0);
                            break;
                        case 1:
                            responseModel.setStatusCode(1);
                            responseModel.setMessage(messageSource.getMessage("message.alreadyexists", null, "", Locale.forLanguageTag(lang)));
                            break;
                        case 2:
                            responseModel.setMessage(messageSource.getMessage("message.failedtodelete", null, "", Locale.forLanguageTag(lang)));
                            responseModel.setStatusCode(2);
                            break;
                        default:
                            responseModel.setMessage(messageSource.getMessage("message.failedtodelete", null, "", Locale.forLanguageTag(lang)));
                            responseModel.setStatusCode(2);
                            break;
                    }
                }
            } else {
                responseModel.setMessage(messageSource.getMessage("message.pleasegivecorrectinput", null, "", Locale.forLanguageTag(lang)));
                responseModel.setStatusCode(3);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseModel.setMessage(messageSource.getMessage("message.exceptioninmapping", null, "", Locale.forLanguageTag(lang)));
            responseModel.setStatusCode(11);
        }
        return responseModel;
    }

    public ResponseModel getstatusById(RequestModel requestModelObj) {
        ResponseModel responseModel = new ResponseModel();
        String lang = requestModelObj.getLang() != null ? requestModelObj.getLang() : "en";
        try {
            Status statusReqObj = utilService.jsonToObject(gson.toJson(requestModelObj.getReqObject()), Status.class);
            if (statusReqObj != null) {
                Status statusObj = statusDao.findByid(statusReqObj.getId());
                if (statusObj != null) {
                    responseModel.setRespObject(statusObj);
                    responseModel.setStatusCode(0);
                    responseModel.setMessage(messageSource.getMessage("message.success", null, "", Locale.forLanguageTag(lang)));
                } else {
                    responseModel.setMessage(messageSource.getMessage("message.notfound", null, "", Locale.forLanguageTag(lang)));
                    responseModel.setStatusCode(1);
                }
            } else {
                responseModel.setMessage(messageSource.getMessage("message.pleasegivecorrectinput", null, "", Locale.forLanguageTag(lang)));
                responseModel.setStatusCode(3);
            }
        } catch (Exception e) {
            responseModel.setMessage(messageSource.getMessage("message.exceptioninmapping", null, "", Locale.forLanguageTag(lang)));
            responseModel.setStatusCode(11);
        }
        return responseModel;
    }

    public ResponseModel getallstatus(RequestModel requestModelObj) {
        ResponseModel responseModel = new ResponseModel();
        List<Status> statusList = statusDao.findAll();
        String lang = requestModelObj.getLang() != null ? requestModelObj.getLang() : "en";
        if (statusList != null) {
            responseModel.setRespList(statusList);
            responseModel.setStatusCode(0);
            responseModel.setMessage(messageSource.getMessage("message.statusgetsuccessfully", null, "", Locale.forLanguageTag(lang)));
        } else {
            responseModel.setStatusCode(1);
            responseModel.setMessage(messageSource.getMessage("message.statusnotfound", null, "", Locale.forLanguageTag(lang)));
        }
        return responseModel;
    }

}
