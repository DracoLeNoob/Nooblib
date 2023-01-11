package fr.ideria.nooblib.time;

import java.time.LocalDateTime;

public class Clock {
    private static LocalDateTime clock;

    private static void update(){
        clock = LocalDateTime.now();
    }

    public static int getYear(){
        update();
        return clock.getYear();
    }

    public static int getDayOfYear(){
        update();
        return clock.getDayOfYear();
    }

    public static int getMonth(){
        update();
        return clock.getMonthValue();
    }

    public static int getDayOfMonth(){
        update();
        return clock.getDayOfMonth();
    }

    public static int getHour(){
        update();
        return clock.getHour();
    }

    public static int getMinute(){
        update();
        return clock.getMinute();
    }

    public static int getSecond(){
        update();
        return clock.getSecond();
    }
}