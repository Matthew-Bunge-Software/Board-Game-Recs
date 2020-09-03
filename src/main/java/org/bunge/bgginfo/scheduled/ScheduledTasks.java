package org.bunge.bgginfo.scheduled;

import java.util.Random;

import org.bunge.bgginfo.bggintegration.service.BGGService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    @Autowired 
    BGGService bggService;

	private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
 
    @Scheduled(fixedRate = 3000)
    public void queryApi() {
        Random r = new Random();
        int i = r.nextInt(206480) + 1;
        log.info("" + i);
        log.info(bggService.getRandomGameTerms(i));
    }
}