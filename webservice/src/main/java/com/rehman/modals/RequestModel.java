/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rehman.modals;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author Rehman
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestModel {

    private String userId;
    private String searchKey;
    private Integer limit;
    private Integer offset;
    private String lang;
    private String extraVariable;
    private String otherVariable;
    private Object reqObject;
    private List<?> reqList;
    private List<String> stringList;
    private ImageRequest imageObj;
    private ImageRequest imageObj2;
    private Object userType;
    private String[] reqArray;
    private Double latitude;
    private Double longitude;
    private Boolean dateiseReport;
    private String authToken;
    private Object role;
    private Integer idVariable;

    private Object videoObj;
    private Object docObj;

}
