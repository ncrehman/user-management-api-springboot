package com.rehman.modals;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseModel implements Serializable {

    private Integer statusCode;
    private String message;
    private String authToken;
    private Integer idVariable;
    private Long totalRecords;
    private Long variable;
    private String extraVariable;
    private Object respObject;
    private Object object;
    private List<?> respList;
    private String[] respArray;

}
