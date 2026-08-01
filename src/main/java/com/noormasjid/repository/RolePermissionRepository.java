package com.noormasjid.repository;

import com.noormasjid.entity.auth.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {
    @Query("SELECT p.name FROM RolePermission rp JOIN rp.permission p JOIN rp.role r WHERE r.id = :roleId")
    List<String> findPermissionNamesByRoleId(@Param("roleId") Long roleId);
}
