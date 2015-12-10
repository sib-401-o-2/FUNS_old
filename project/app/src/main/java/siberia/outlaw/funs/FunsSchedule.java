package siberia.outlaw.funs;

import org.joda.time.LocalDate;

/**
 * Created by asmodeus on 13.11.15.
 */
public class FunsSchedule {
    static class Course {

        private final int id;
        private final String name;
        private final String teacher;
        private final Subject.Importance importance;

        public Course(int id, String name, String teacher, Subject.Importance importance) {
            this.id = id;
            this.name = name;
            this.teacher = teacher;
            this.importance = importance;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getTeacher() {
            return teacher;
        }

        public Subject.Importance getImportance() {
            return importance;
        }
    }

    static class Day{
        Day(Index[] indexes) {
            this.indexes = indexes;
        }

        public Index getIndex(int i) {
            for (Index x:
                    indexes) {
                if (x.getIndex() == i)
                    return x;
            }
            return null;
        }

        static class Index {
            private final int index;
            private final int course_id;
            private final String cabinet;

            public Index(int index, int course_id, String cabinet) {
                this.index = index;
                this.course_id = course_id;
                this.cabinet = cabinet;
            }

            public int getIndex() {
                return index;
            }

            public int getCourse_id() {
                return course_id;
            }

            public String getCabinet() {
                return cabinet;
            }
        }

        private final Index[] indexes;
    }

    protected final Course[] courses;
    protected final Day[] days;
    protected final boolean oddAndEvenWeeks;

    public FunsSchedule(Course[] courses, Day[] days) {
        this.courses = courses;
        this.days = days;
        oddAndEvenWeeks = days.length > 7;
        System.out.println("is oddandeven weeks " + oddAndEvenWeeks);
    }

    public Subject getSubject(int dayOfWeek, int index, boolean odd) {
        int dayI = dayOfWeek - 1;
        if (oddAndEvenWeeks && odd)
            dayI += 7;
        if (days[dayI] == null)
            return null;
        Day.Index s = days[dayI].getIndex(index);
        if (s == null)
            return null;
        Course course = courses[s.getCourse_id()];
        return new Subject(
                course.getName(),
                course.getTeacher(),
                s.getCabinet(),
                course.getImportance(),
                s.getIndex()
        );
    }

}
