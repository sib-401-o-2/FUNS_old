package siberia.outlaw.funs;

import android.graphics.Color;
import android.support.v4.content.res.TypedArrayUtils;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static siberia.outlaw.funs.FunsScheduleParser.*;

/**
 * Created by asmodeus on 14.11.15.
 */
public class Funs {
    private static final String funsDirName = "/storage/emulated/0" + "/.FUNS/";
    private static final String timeFilename = "time.json";
    private static final String scheduleFilename = "schedule.json";
    private static final String scheduleOddFilename = "scheduleOdd.json";
    private static final String scheduleEvenFilename = "scheduleEven.json";

    private static FunsTime time;
    private static FunsSchedule schedule;
    private static ArrayList<ArrayList<Subject>> subjects;

    private Funs() {
        time = FunsTimeParser.getTime(funsDirName + timeFilename);

        FunsSchedule.Day[] days;
        FunsSchedule.Course[] courses;

        File file = new File(funsDirName + scheduleFilename);
        if(file.exists()) {
            days = getScheduleDays(funsDirName + scheduleFilename);
            courses = getCourses(funsDirName + scheduleFilename);
            System.out.println("неждан!");
        } else {
            FunsSchedule.Day[] odd = getScheduleDays(funsDirName + scheduleOddFilename);
            FunsSchedule.Day[] even = getScheduleDays(funsDirName + scheduleEvenFilename);
            days = new FunsSchedule.Day[14];
            System.arraycopy(odd, 0, days, 0, odd.length);
            System.arraycopy(even, 0, days, odd.length, odd.length);
            courses = getCourses(funsDirName + scheduleOddFilename);
            System.out.println("ждан!" + days.length);
        }
        schedule = new FunsSchedule(
                courses,
                days
        );

        subjects = new ArrayList<>(14);
        for (int i = 1; i<=7; i++) {
            ArrayList<Subject> records = new ArrayList<Subject>();
            for (int si = 0; si < 10; si++) {
                Subject record = getSchedule().getSubject(i, si, true); // odd
                if (record != null) {
                    records.add(record);
                    System.out.println("gening ODD: day " + i + " " + record.getName());
                }
            }
            subjects.add(records);
        }

        for (int i = 1; i<7; i++) {
            ArrayList<Subject> records = new ArrayList<Subject>();
            for (int si = 0; si < 10; si++) {
                Subject record = schedule.getSubject(i, si, false); // even
                if (record != null)
                    records.add(record);
            }
            subjects.add(records);
        }
        System.out.println("FUNS Initialized");
    }

    public static FunsTime getTime() {
        return time;
    }

    public static int getImportanceColor(Subject.Importance i) {
        switch (i) {
            case LOW:
                return Color.GREEN;
            case MEDIUM:
                return  Color.YELLOW;
            case HEAVY:
                return  Color.RED;
        }
        return -1;
    }

    public static FunsSchedule getSchedule() {
        return schedule;
    }

    public static int getViewPagerCurrentItem() {
        System.out.print(Days.daysBetween(time.getSemesterBeginning(), LocalDate.now()).getDays());
        return Days.daysBetween(time.getSemesterBeginning(), LocalDate.now()).getDays();
    }

    public static LocalDate getSemesterBeginning() {
        return time.getSemesterBeginning();
    }

    public static LocalDate getSemesterEnd() {
        return time.getSemesterEnd();
    }

    public static String getDayString(int position) {
        LocalDate date = getDateFromPosition(position);
        StringBuilder strb = new StringBuilder(date.toString("dd MMMM"));
        if (time.isOdd(date))
            strb.append(" НН");
        else
            strb.append(" ЧН");
        return strb.toString();
    }

    private static LocalDate getDateFromPosition(int position) {
        System.out.println("date p " + position + " " + time.getSemesterBeginning().plusDays(position).toString() + " " + time.getSemesterBeginning().toString());
        return time.getSemesterBeginning().plusDays(position);
    }

    private static int getDayIndexFromDate(LocalDate date) {
        int ret = date.getDayOfWeek() - 1;
        //if (!time.isOdd(date))
        //    ret += 7;
        System.out.println("date " + date.toString("E") + " " + ret);
        return ret;
    }

    public static List<Subject> getSubjectsList(int position) {
        System.out.println();
        return subjects.get(getDayIndexFromDate(getDateFromPosition(position)));
    }

    private static class FunsHolder {
        private static final Funs instance = new Funs();
    }

    public static Funs getInstance() {
        return FunsHolder.instance;
    }
}
