package com.proyecto.prueba.repository;

import com.proyecto.prueba.model.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for performing CRUD operations on the Loan entity.
 */
@Repository
public interface LoanRepository extends JpaRepository<Loan,Long> {
}
