/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rehman.modals;

/**
 *
 * @author Rehman
 */
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "status")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Status implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // For auto-increment ID in MySQL
    private Long id;

    @Column(name = "status_name", nullable = false, length = 100)
    private String status_name;

    @Column(name = "used_for", nullable = false, length = 100)
    private String used_for;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false, updatable = false)
    private Date created_date;

}
