package com.action.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.action.entity.Audit;

@Repository
public interface AuditRepo extends JpaRepository<Audit, Integer> {

}
