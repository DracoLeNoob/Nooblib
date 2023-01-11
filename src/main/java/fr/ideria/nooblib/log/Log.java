package fr.ideria.nooblib.log;

import fr.ideria.nooblib.Nooblib;
import fr.ideria.nooblib.time.Clock;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Log {
    private static int dashes = 0;
    private static final List<String> dashers = new ArrayList<>();
    private static final List<String> undashers = new ArrayList<>();
    public static void addDashedWord(String dasher, String undasher){
        dashers.add(dasher);
        undashers.add(undasher);
    }

    private static String getDate(){
        return "[" + Clock.getYear() + "-" + Clock.getMonth() + "-" + Clock.getDayOfMonth() + "]";
    }

    private static String getHour(){
        return "[" + Clock.getHour() + ":" + Clock.getMinute() + ":" + Clock.getSecond() + "]";
    }

    private static String getTime(){
        return getDate() + getHour();
    }

    private static void print(String thread, String from, String text){
        System.out.println(getTime() + "[" + thread + "][" + from + "]: " + text);
    }

    public static void print(String text){
        String word = text.split(" ")[0];

        if(dashers.contains(word))
            dashes++;

        String dash = " ".repeat(dashes);

        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
        Log.print(Thread.currentThread().getName(), Nooblib.lastOf(stacktrace).getFileName(), dash + text);

        if(undashers.contains(word))
            dashes = Math.max(dashes - 1, 0);
    }

    public static void print(@NotNull Object object){
        Log.print(object.toString());
    }
}