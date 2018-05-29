package com.procanchas.email.repository;

import com.procanchas.email.entity.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 */
@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration,Long> {

}
