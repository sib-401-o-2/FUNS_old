package siberia.outlaw.funs;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by asmodeus on 13.11.15.
 */
public class FunsScheduleParser {

    public static FunsSchedule.Course[] getCourses(String fileName) {
        JSONObject obj;
        try {
            File file = new File(fileName);
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();

            System.out.println("schedule courses: " + new String(data, "UTF-8"));

            obj = new JSONObject(new String(data, "UTF-8"));

            int version = obj.getInt("version");
            System.out.println("schedule courses: version " + version);
            if (version >= 1) {
                JSONArray cs = obj.getJSONArray("courses");
                FunsSchedule.Course[] courses = new FunsSchedule.Course[cs.length()];
                for (int i = 0; i < cs.length(); i++) {
                    JSONObject c = cs.getJSONObject(i);
                    courses[i] = new FunsSchedule.Course(
                            c.getInt("id"),
                            c.getString("name"),
                            c.getString("teacherName"),
                            Subject.intToImportance(c.getInt("importance"))
                    );
                }

                System.out.println("schedule courses: lenght " + courses.length);
                return courses;
            }
        } catch (Exception e) {
            e.printStackTrace();

            System.out.println("schedule courses: expt " + e.toString());
        }

        System.out.println("schedule courses: :C");
        return null;
    }

    public static FunsSchedule.Day[] getScheduleDays(String fileName) {
        JSONObject obj;
        try {
            File file = new File(fileName);
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();

            System.out.println("schedule days: " + new String(data, "UTF-8"));

            obj = new JSONObject(new String(data, "UTF-8"));

            int version = obj.getInt("version");
            System.out.println("schedule days: version " + version);
            if (version >= 1) {
                FunsSchedule.Day[] days = new FunsSchedule.Day[7];

                if (!obj.getString("monday").equals("null")) {
                    days[0] = dayFromJSON(obj.getJSONArray("monday"));
                } else
                    days[0] = null;


                if (!obj.getString("tuesday").equals("null")) {
                    days[1] = dayFromJSON(obj.getJSONArray("tuesday"));
                } else
                    days[1] = null;


                if (!obj.getString("wednesday").equals("null")) {
                    days[2] = dayFromJSON(obj.getJSONArray("wednesday"));
                } else
                    days[2] = null;


                if (!obj.getString("thursday").equals("null")) {
                    days[3] = dayFromJSON(obj.getJSONArray("thursday"));
                } else
                    days[3] = null;


                if (!obj.getString("friday").equals("null")) {
                    days[4] = dayFromJSON(obj.getJSONArray("friday"));
                } else
                    days[4] = null;


                if (!obj.getString("saturday").equals("null")) {
                    days[5] = dayFromJSON(obj.getJSONArray("saturday"));
                } else
                    days[5] = null;


                if (!obj.getString("sunday").equals("null")) {
                    days[6] = dayFromJSON(obj.getJSONArray("sunday"));
                } else
                    days[6] = null;

                return days;
            }
        } catch (Exception e) {
            e.printStackTrace();

            System.out.println("schedule days: expt " + e.toString());
        }

        return null;
    }

    public static FunsSchedule.Day dayFromJSON(JSONArray a) {
        FunsSchedule.Day.Index[] indexes = new FunsSchedule.Day.Index[a.length()];
        try {
            for (int i = 0; i < a.length(); i++) {
                JSONObject o = a.getJSONObject(i);
                indexes[i] = new FunsSchedule.Day.Index(
                        o.getInt("index"),
                        o.getInt("course_id"),
                        o.getString("cabinet")
                );

            }
        } catch (Exception e) {
            e.printStackTrace();

            System.out.println("day from JSON: expt " + e.toString());
        }
        return  new FunsSchedule.Day(indexes);
    }
}
