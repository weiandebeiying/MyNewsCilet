package db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by weian on 2017/6/29.
 */

public class MyDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "sb.DB";
    public MyDBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //频道
        db.execSQL("create table channel(name text not null)");
        db.execSQL("insert into channel(name) values('头条')");
        db.execSQL("insert into channel(name) values('娱乐')");
        db.execSQL("insert into channel(name) values('游戏')");
        db.execSQL("insert into channel(name) values('体育')");
        db.execSQL("insert into channel(name) values('精选')");
        //收藏
        db.execSQL("create table collect(imgurl text not null," +
                "title text not null," +
                "source text not null," +
                "url text not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
