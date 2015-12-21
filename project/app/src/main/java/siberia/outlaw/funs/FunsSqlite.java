package siberia.outlaw.funs;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.joda.time.LocalDate;

/**
 * Created by desktop on 12/21/15.
 */
public class FunsSqlite {
    private static final int STANDART_STATUS = 666;

    public class DBHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "funs.db";
        private static final int SCHEMA = 1;
        static final String TABLE = "stats";
//        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_COURSE_NAME = "cid";
        public static final String COLUMN_DATE_INDEX= "date_index";
//        public static final String COLUMN_INDEX= "class_index";
        public static final String COLUMN_STATUS = "status";


        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, SCHEMA);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL("DROP TABLE IF EXISTS " + TABLE);
            db.execSQL("CREATE TABLE " + TABLE + " (" +
                    //COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_COURSE_NAME + " STRING," +
                    COLUMN_DATE_INDEX + " TEXT PRIMARY KEY," +
                    COLUMN_STATUS + " INTEGER" +
                    ");");
            //db.execSQL("INSERT INTO "+ TABLE +" (" + COLUMN_NAME
            //        + ", " + COLUMN_YEAR  + ") VALUES ('Том Смит', 1981);");
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE);
            onCreate(db);
        }
    }

    DBHelper dbhelper;
    SQLiteDatabase db;

    public FunsSqlite(Context context) {
        dbhelper = new DBHelper(context);
        db = dbhelper.getReadableDatabase();
        reconnect();
    }

    private String stringFromDateIndex(LocalDate date, int index) {
        return new StringBuilder().append(date.toString()).append(" | ").append(index).toString();
    }


    void reconnect() {
        if (!db.isOpen()) {
            db = dbhelper.getReadableDatabase();
            System.out.println("reconnected");
        } else {
            System.out.println("already reconnected");
        }
    }

    public void getDayStats(LocalDate date) {
        System.out.println("daystats");
        Cursor answer = db.rawQuery("SELECT * from " + DBHelper.TABLE
                , null);
        answer.moveToLast();
//        System.out.println(answer.getString(answer.getColumnIndex(DBHelper.COLUMN_DATE)) + " | " + date.toString());
    }

    public int getDayIndexStats(LocalDate date, int index) {
        System.out.println("getDI " + date.toString() + " | " + index);
        Cursor answer = db.rawQuery("SELECT " + DBHelper.COLUMN_STATUS + " from " + DBHelper.TABLE + " WHERE " +
                DBHelper.COLUMN_DATE_INDEX + " = ?"
        , new String[] {stringFromDateIndex(date, index)});
        System.out.println("finded " + answer.getCount() + " entrys");
        if (answer.getCount() > 0) {
            System.out.println("returning status...");
            answer.moveToFirst();
            return answer.getInt(answer.getColumnIndex(DBHelper.COLUMN_STATUS));
        } else
            return STANDART_STATUS;
    }

    public void setDayIndexStats(LocalDate date, int index, int status, String cid) {
        System.out.println("setDI " + date.toString() + " | " + index + " to " + status);
        /*Cursor answer = */db.execSQL("INSERT OR REPLACE INTO " + DBHelper.TABLE + " (" +
                DBHelper.COLUMN_COURSE_NAME + ", " +
                DBHelper.COLUMN_DATE_INDEX + ", " +
//                DBHelper.COLUMN_INDEX + ", " +
                DBHelper.COLUMN_STATUS +
                ") VALUES (" + "?, ?, ?" +
                //cid + ", " +
                //date.toString() + ", " +
                //index + ", " +
                //status + //", " +
                ");", new String[]{
                cid,
                stringFromDateIndex(date, index),
                Integer.toString(status)
        });

        System.out.println("Checking status: " + getDayIndexStats(date, index));
        //System.out.println("setDI " + answer.getCount() + "|" + answer.getString(answer.getColumnIndex(DBHelper.COLUMN_DATE)));
    }

}
