package siberia.outlaw.funs;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by asmodeus on 14.11.15.
 */
public class Funs {
    private static final String funsDirName = "/storage/emulated/0" + "/.FUNS/";
    private static final String timeFilename = "time.json";
    private static final String scheduleFilename = "schedule.json";

    private static FunsTime time;
    private static FunsSchedule schedule;
    private static ArrayList<ArrayList<Subject>> subjects;

    private Funs() {
        time = FunsTimeParser.getTime(funsDirName + timeFilename);
        schedule = new FunsSchedule(
                FunsScheduleParser.getCourses(funsDirName + scheduleFilename),
                FunsScheduleParser.getScheduleDays(funsDirName + scheduleFilename)
        );

        subjects = new ArrayList<>(14);
        for (int i = 1; i<=7; i++) {
            ArrayList<Subject> records = new ArrayList<Subject>();
            for (int si = 0; si < 10; si++) {
                Subject record = Funs.getInstance().getSchedule().getSubject(i, si, true); // odd
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
