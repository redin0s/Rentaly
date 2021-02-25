package com.folders.rentaly.service;

import com.folders.rentaly.persistence.dao.RealtyDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ScheduledTasks {

    @Autowired
    private RealtyDAO realtyDAO;

    @Scheduled(cron="0 10 0 * * *")
    public void updateCurrentHolders() {
        log.info("updating current holders for each realty");
        realtyDAO.updateCurrentHolders();
    }
    
}
