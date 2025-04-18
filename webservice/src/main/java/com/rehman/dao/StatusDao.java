/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rehman.dao;

import com.rehman.modals.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Rehman
 */
public interface StatusDao extends JpaRepository<Status, Long> {

    Status findByid(Long id);

    @Query(value = "SELECT COUNT(*) FROM Status WHERE status_name = :name AND used_for = :other", nativeQuery = true)
    long getStatusCount(@Param("name") String name, @Param("other") String other);

    @Query(value = "SELECT * FROM Status WHERE status_name = :status_name AND used_for = :used_for", nativeQuery = true)
    Status findRoleByRoleName(@Param("status_name") String status_name, @Param("used_for") String used_for);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Status r WHERE r.status_name = :status_name AND r.id != :id")
    boolean getStatusByNameInUpdate(@Param("status_name") String status_name, @Param("id") Long id);

//    @Modifying
//    @Query("UPDATE Status u SET u.status_name = :#{#statusObj.status_name}, u.used_for = :#{#statusObj.used_for} WHERE u.id = :#{#statusObj.id}")
//    int updateRoleNameById(@Param("statusObj") Status statusObj);
}
