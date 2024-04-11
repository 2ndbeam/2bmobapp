package g313.mirenkov.lab13;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class database extends SQLiteOpenHelper {

    public database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table tablica (id integer primary key autoincrement, value text)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public void insert(String value) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String sql = String.format("insert into tablica values('%s')", value);
        sqLiteDatabase.execSQL(sql);
    }

    public @Nullable String select(String key)
    {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String sql = String.format("select value from tablica where id = '%s'", key);
        Cursor cur = sqLiteDatabase.rawQuery(sql, null);
        if (!cur.moveToFirst())
        {
            return null;
        }
        return cur.getString(0);
    }

    public int get_rows_count() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String sql = "select id from tablica order by id desc limit 1";
        Cursor cur = sqLiteDatabase.rawQuery(sql, null);
        if (!cur.moveToFirst()) {
            return -1;
        }
        return cur.getInt(0);
    }
}
