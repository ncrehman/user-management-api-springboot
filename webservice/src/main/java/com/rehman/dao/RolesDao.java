/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rehman.dao;

import com.rehman.modals.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Rehman
 */
public interface RolesDao extends JpaRepository<Roles, Long> {

    Roles findByid(Long id);

    @Query(value = "SELECT COUNT(*) FROM Roles WHERE roles_name = :name AND other_roles_name = :other", nativeQuery = true)
    long getRolesCount(@Param("name") String name, @Param("other") String other);

    @Query(value = "SELECT * FROM Roles WHERE roles_name = :roles_name AND other_roles_name = :other_roles_name", nativeQuery = true)
    Roles findRoleByRoleName(@Param("roles_name") String roles_name, @Param("other_roles_name") String other_roles_name);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Roles r WHERE r.roles_name = :roleName AND r.id != :id")
    boolean getRolesByNameInUpdate(@Param("roleName") String roleName, @Param("id") Long id);

//    @Modifying
//    @Query("UPDATE Roles u SET u.roles_name = :roleName, u.other_roles_name = :other_roles_name, u.roles_level = :roleLevel WHERE u.id = :id")
//    int updateRoleNameById(@Param("roleName") String roleName,
//            @Param("other_roles_name") String other_roles_name,
//            @Param("roles_level") Long roles_level,
//            @Param("id") Long id);
//    @Query(value = "SELECT COUNT(*) FROM Roles WHERE roles_name = :name AND other_roles_name = :other", nativeQuery = true)
//    long updateRoleNameById(@Param("name") String name,
//            @Param("other") String other,
//            @Param("roleLevel") Long roleLevel,
//            @Param("id") Long id);
}
