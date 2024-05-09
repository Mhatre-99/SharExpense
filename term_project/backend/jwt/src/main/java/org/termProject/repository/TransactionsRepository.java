package org.termProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.termProject.model.Transactions;

import java.util.Optional;
import java.util.Set;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, Long> {

    @Query("SELECT t FROM Transactions t JOIN t.seGroups g WHERE g.Id = :groupId")
    Optional<Set<Transactions>> findTransactionsByGroupId(@Param("groupId") Long groupId);


}
