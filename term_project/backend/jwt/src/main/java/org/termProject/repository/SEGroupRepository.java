package org.termProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.termProject.model.SEGroups;

import java.util.Optional;
import java.util.Set;

@Repository
public interface SEGroupRepository extends JpaRepository<SEGroups, Long> {

    Optional<SEGroups> findSEGroupsById(Long id);


}
