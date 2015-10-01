package siberia.outlaw.funs;

/**
 * Created by asmodeus on 01.10.15.
 */
public class Subject {
    enum Importance {NONE, LOW, MEDIUM, HEAVY}

    private String name;
    private String teacher;
    private String cabinet;
    Importance importance;

    public Subject(String name, String teacher, String cabinet, Importance importance) {
        this.name = name;
        this.teacher = teacher;
        this.cabinet = cabinet;
        this.importance = importance;
    }
    public Subject() {

    }

    public String getName() {
        return name;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getCabinet() {
        return cabinet;
    }

    public Importance getImportance() {
        return importance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public void setCabinet(String cabinet) {
        this.cabinet = cabinet;
    }

    public void setImportance(Importance importance) {
        this.importance = importance;
    }
}
