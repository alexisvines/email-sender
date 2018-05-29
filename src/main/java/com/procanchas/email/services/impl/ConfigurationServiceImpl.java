package com.procanchas.email.services.impl;

import com.procanchas.email.entity.Configuration;
import com.procanchas.email.repository.ConfigurationRepository;
import com.procanchas.email.services.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfigurationServiceImpl implements ConfigurationService {

    @Autowired
    private ConfigurationRepository configurationRepository;

    public List<Configuration> findAll(){
        return configurationRepository.findAll();
    }


}
