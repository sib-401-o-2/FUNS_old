package siberia.outlaw.funs;

import org.json.JSONObject;
/**
 * Created by asmodeus on 01.10.15.
 */
public class Subject {
    enum Importance {NONE, LOW, MEDIUM, HEAVY}

    private String name;
    private String teacher;
    private String cabinet;

    public int getIndex() {
        return index;
    }

    private int index;
    Importance importance;

    public static Importance intToImportance(int i) {
        switch (i) {
            case 0:
                return Importance.NONE;
            case 1:
                return Importance.LOW;
            case 2:
                return Importance.MEDIUM;
            case 3:
                return Importance.HEAVY;
        }
        return Importance.NONE;
    }

    public Subject(String name, String teacher, String cabinet, Importance importance, int index) {
        this.name = name;
        this.teacher = teacher;
        this.cabinet = cabinet;
        this.importance = importance;
        this.index = index;
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
