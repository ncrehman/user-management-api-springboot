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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "roles")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Roles implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // For auto-increment ID in MySQL
    private Long id;

    @Column(name = "roles_name", unique = true, nullable = false, length = 100)
    private String roles_name;

    @Column(name = "other_roles_name", nullable = false, length = 100)
    private String other_roles_name;

    @Column(name = "roles_level", unique = true, nullable = false, length = 100)
    private Long roles_level;
}
