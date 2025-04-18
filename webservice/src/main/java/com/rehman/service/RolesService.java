/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rehman.service;

import com.rehman.utilities.UtilService;
import com.google.gson.Gson;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import com.rehman.dao.RolesDao;
import com.rehman.modals.RequestModel;
import com.rehman.modals.ResponseModel;
import com.rehman.modals.Roles;
import jakarta.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Rehman
 */
@Service
public class RolesService {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    UtilService utilService;
    @Autowired
    RolesDao rolesDao;
    @Autowired
    MessageSource messageSource;
    private final Gson gson = new Gson();

    public ResponseModel createRoles(RequestModel requestModelObj) {
        ResponseModel responseModel = new ResponseModel();
        String lang = requestModelObj.getLang() != null ? requestModelObj.getLang() : "en";
        try {
            Roles rolesReqObj = utilService.jsonToObject(gson.toJson(requestModelObj.getReqObject()), Roles.class);
            if (rolesReqObj != null) {
                long rolesObj = rolesDao.getRolesCount(rolesReqObj.getRoles_name(), rolesReqObj.getOther_roles_name());
                if (rolesObj > 0) {
                    responseModel.setStatusCode(6);
                    responseModel.setMessage(messageSource.getMessage("message.duplicates", null, "", Locale.forLanguageTag(lang)));
                } else {
                    Roles result = rolesDao.save(rolesReqObj);
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

    public ResponseModel updateRoles(RequestModel requestModelObj) {
        ResponseModel responseModel = new ResponseModel();
        String lang = requestModelObj.getLang() != null ? requestModelObj.getLang() : "en";
        try {
            Roles rolesReqObj = utilService.jsonToObject(gson.toJson(requestModelObj.getReqObject()), Roles.class);
            if (rolesReqObj != null) {
                Boolean checkRoles = rolesDao.getRolesByNameInUpdate(rolesReqObj.getRoles_name(), rolesReqObj.getId());
                if (checkRoles) {
                    responseModel.setStatusCode(6);
                    responseModel.setMessage(messageSource.getMessage("message.duplicates", null, "", Locale.forLanguageTag(lang)));
                } else {
                    Roles result = rolesDao.save(rolesReqObj);
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
            e.printStackTrace();
            responseModel.setMessage(messageSource.getMessage("message.exceptioninmapping", null, "", Locale.forLanguageTag(lang)));
            responseModel.setStatusCode(11);
        }
        return responseModel;
    }

    public ResponseModel deleteRolesById(RequestModel requestModelObj) {
        ResponseModel responseModel = new ResponseModel();
        String lang = requestModelObj.getLang() != null ? requestModelObj.getLang() : "en";
        try {
            Roles rolesReqObj = utilService.jsonToObject(gson.toJson(requestModelObj.getReqObject()), Roles.class);
            if (rolesReqObj != null) {
//                Integer counts = usersDao.getUserCountByRoleId(rolesReqObj.getId());
                Integer counts = 0;
                if (counts > 0) {
                    responseModel.setStatusCode(9);
                    responseModel.setMessage(messageSource.getMessage("message.dependencycheckdelete", null, "", Locale.forLanguageTag(lang)));
                } else {
                    rolesDao.delete(rolesReqObj);
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

    public ResponseModel getRolesById(RequestModel requestModelObj) {
        ResponseModel responseModel = new ResponseModel();
        String lang = requestModelObj.getLang() != null ? requestModelObj.getLang() : "en";
        try {
            Roles rolesReqObj = utilService.jsonToObject(gson.toJson(requestModelObj.getReqObject()), Roles.class);
            if (rolesReqObj != null) {
                Roles rolesObj = rolesDao.findByid(rolesReqObj.getId());
                if (rolesObj != null) {
                    responseModel.setRespObject(rolesObj);
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

    public ResponseModel getAllRoles(RequestModel requestModelObj) {
        ResponseModel responseModel = new ResponseModel();
        List<Roles> rolesList = rolesDao.findAll();
        String lang = requestModelObj.getLang() != null ? requestModelObj.getLang() : "en";
        if (rolesList != null) {
            responseModel.setRespList(rolesList);
            responseModel.setStatusCode(0);
            responseModel.setMessage(messageSource.getMessage("message.rolesgetsuccessfully", null, "", Locale.forLanguageTag(lang)));
        } else {
            responseModel.setStatusCode(1);
            responseModel.setMessage(messageSource.getMessage("message.rolesnotfound", null, "", Locale.forLanguageTag(lang)));
        }
        return responseModel;
    }

}
