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
@Table(name = "users")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Users implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // For auto-increment ID in MySQL
    private Long id;

    @Column(name = "full_name", nullable = false, length = 100)
    private String full_name;

    @Column(name = "email_address", unique = true, nullable = false, length = 100)
    private String email_address;

    @Column(name = "profile_image", length = 255)
    private String profile_image;

    @Column(name = "mobile_number", nullable = false, length = 15)
    private String mobile_number;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "is_deleted", nullable = false)
    private Boolean is_deleted = false;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "roles_Obj", referencedColumnName = "id", nullable = false)
    private Roles roles_Obj;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_obj", referencedColumnName = "id", nullable = false)
    private Status status_obj;

    @Column(name = "created_date", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_date;
}
