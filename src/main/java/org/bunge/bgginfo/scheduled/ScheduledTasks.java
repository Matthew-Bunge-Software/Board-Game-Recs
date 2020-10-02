package org.bunge.bgginfo.scheduled;

import org.bunge.bgginfo.bggintegration.service.BGGService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
    @Autowired
    private BGGService bggService;

    @Scheduled(fixedRate = 300)
    public void queryApi() {
        if (!bggService.maxGameIdReached()) {
            bggService.getNextGameTerms();
        }
    }
}
