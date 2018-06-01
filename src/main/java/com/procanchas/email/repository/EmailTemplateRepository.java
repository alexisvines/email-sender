package com.procanchas.email.repository;


import com.procanchas.email.entity.EmailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 */
@Repository
public interface EmailTemplateRepository extends JpaRepository<EmailTemplate,Long> {
}
