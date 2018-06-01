package com.procanchas.email.repository;

import com.procanchas.email.entity.EmailTemplateLabel;
import com.sun.mail.imap.protocol.ID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 */
@Repository
public interface EmailTemplateLabelRepository  extends JpaRepository<EmailTemplateLabel,Long>{
}
