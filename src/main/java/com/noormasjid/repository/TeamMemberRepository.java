package com.noormasjid.repository;

import com.noormasjid.entity.cms.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
    List<TeamMember> findByMasjidIdAndIsDeleted(Long masjidId, Integer isDeleted);
}
