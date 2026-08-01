package com.noormasjid.repository;

import com.noormasjid.entity.auth.UserMasjidRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserMasjidRoleRepository extends JpaRepository<UserMasjidRole, Long> {
    Optional<UserMasjidRole> findByUserIdAndMasjidId(Long userId, Long masjidId);
    List<UserMasjidRole> findByUserId(Long userId);
    List<UserMasjidRole> findByMasjidId(Long masjidId);
    boolean existsByUserIdAndMasjidId(Long userId, Long masjidId);

    @Query("SELECT r.name FROM UserMasjidRole umr JOIN umr.role r WHERE umr.user.id = :userId AND umr.masjid.id = :masjidId")
    Optional<String> findRoleNameByUserIdAndMasjidId(@Param("userId") Long userId, @Param("masjidId") Long masjidId);

    @Query("SELECT p.name FROM UserMasjidRole umr JOIN umr.role r JOIN RolePermission rp ON rp.role.id = r.id JOIN rp.permission p WHERE umr.user.id = :userId AND umr.masjid.id = :masjidId")
    List<String> findPermissionNamesByUserIdAndMasjidId(@Param("userId") Long userId, @Param("masjidId") Long masjidId);
}
