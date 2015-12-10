package siberia.outlaw.funs;

import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import javax.xml.datatype.Duration;

/**
 * Created by asmodeus on 13.11.15.
 */
public class FunsTime {

    protected final LocalTime startTime;
    protected final int duration;
    protected final int standardBreak;
    protected final int standardClassBreak;
    protected final int greatBreak;
    protected final LocalDate semesterBeginning;
    protected final LocalDate semesterEnd;
    protected final int middleClass;
    protected final int classInterval;

    public FunsTime(LocalTime startTime,
                    int duration,
                    int standardBreak,
                    int standardClassBreak,
                    int greatBreak,
                    LocalDate semesterBeginning,
                    LocalDate semesterEnd,
                    int middleClass) {
        this.startTime = startTime;
        this.duration = duration;
        this.standardBreak = standardBreak;
        this.standardClassBreak = standardClassBreak;
        this.greatBreak = greatBreak;
        this.semesterBeginning = semesterBeginning;
        this.semesterEnd = semesterEnd;
        this.middleClass = middleClass;

        this.classInterval = duration*2 + standardBreak + standardClassBreak;

        System.out.println("time:constructed");
    }

    public LocalTime getStartOfClass(int index) {
        int addition = 0;
        for (int i = 1; i < index; i++) {
            addition += classInterval;
        }
        if (index > 3)
            addition += greatBreak - standardClassBreak;
        return startTime.plusMinutes(addition);
    }

    public LocalTime getEndOfClass(int index) {
        int addition = 0;
        for (int i = 0; i < index; i++) {
            addition += classInterval;
        }
        if (index > 3)
            addition += greatBreak - standardClassBreak;
        return startTime.plusMinutes(addition - standardClassBreak);
    }

    String getTimeOfClassStr(int index) {
        return new StringBuilder(getStartOfClass(index).toString("HH:mm")).append("—").append(getEndOfClass(index).toString("HH:mm")).toString();
    }

    public LocalDate getSemesterBeginning() {
        return semesterBeginning;
    }

    public LocalDate getSemesterEnd() {
        return semesterEnd;
    }

    public boolean isOdd(LocalDate date) {
        int week = date.getWeekOfWeekyear() - this.semesterBeginning.getWeekOfWeekyear();
        System.out.println("week: " + week + " of date " + date.toString());
        if (week%2 == 0)
            return false;
        return true;
    }
}
