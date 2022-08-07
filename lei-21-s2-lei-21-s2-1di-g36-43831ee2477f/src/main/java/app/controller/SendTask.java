package app.controller;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SendTask {
    public void sendReport(){

        ZonedDateTime now = ZonedDateTime.now(ZoneId.systemDefault());
        ZonedDateTime nextRun = now.withHour(6).withMinute(0).withSecond(0);
        if(now.compareTo(nextRun) > 0)
            nextRun = nextRun.plusDays(1);

        Duration duration = Duration.between(now, nextRun);
        long initalDelay = duration.getSeconds();

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new AutomaticReportNHSSender(),
                initalDelay,
                TimeUnit.DAYS.toSeconds(1),
                TimeUnit.SECONDS);
    }
}
