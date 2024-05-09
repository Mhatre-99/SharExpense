package org.termProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.termProject.model.SEGroups;
import org.termProject.model.User;
import org.termProject.model.UserGroup;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup,Long> {


    @Query("SELECT ug.group FROM UserGroup ug WHERE ug.user.id = :userId")
    Set<SEGroups> findGroupByUserId(Long userId);

    @Query("SELECT ug.user FROM UserGroup  ug WHERE ug.group.Id = :groupId")
    Set<User> findUserByGroupId(Long groupId);

    @Query("SELECT ug from UserGroup ug where ug.group.Id = :groupId and ug.user.id = :userId")
    UserGroup findAmountbyUserIdAndGroupId(Long groupId, Long userId);

}
