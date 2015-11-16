package siberia.outlaw.funs;

import android.util.JsonReader;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;

/**
 * Created by asmodeus on 13.11.15.
 */
public class FunsTimeParser {

    private static LocalDate strToDate(String str) {
        int d0 = Character.getNumericValue(str.charAt(0));
        int d1 = Character.getNumericValue(str.charAt(1));
        int m0 = Character.getNumericValue(str.charAt(3));
        int m1 = Character.getNumericValue(str.charAt(4));

        System.out.println("str T: " + new Integer(m0*10+m1) + " "+ new Integer(d0*10+d1) );
        return new LocalDate(LocalDate.now().getYear(),
                m0*10+m1,
                d0*10+d1);
    }

    public static FunsTime getTime(String fileName) {
        JSONObject obj;
        try {
            File file = new File(fileName);
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();


            System.out.println("time: " + new String(data, "UTF-8"));

            obj = new JSONObject(new String(data, "UTF-8"));

            int version = obj.getInt("version");
            System.out.println("time: version " + version);
            if (version >= 1) {
                LocalTime startTime =
                        new LocalTime(
                            obj.getInt("startTimeHour"),
                            obj.getInt("startTimeMinute")
                        );
                int duration = obj.getInt("classDuration");
                int standardBreak = obj.getInt("standardBreak");
                int standardClassBreak = obj.getInt("standardClassBreak");
                int greatBreak = obj.getInt("greatBreak");
                String b = obj.getString("semesterBeginning");
                LocalDate semesterBeginning = strToDate(b);
                String e = obj.getString("semesterEnd");
                LocalDate semesterEnd = strToDate(e);
                int middleClass = obj.getInt("middleClass");
                System.out.println("time: midClass" + middleClass);
                return new FunsTime(
                        startTime,
                        duration,
                        standardBreak,
                        standardClassBreak,
                        greatBreak,
                        semesterBeginning,
                        semesterEnd,
                        middleClass);
            }
        } catch (Exception e) {
            e.printStackTrace();

            System.out.println("time: expt " + e.toString());
        }

        System.out.println("time: :C");
        return null;
    }
}
