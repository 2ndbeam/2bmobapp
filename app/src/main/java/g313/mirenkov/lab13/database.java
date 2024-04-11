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
        String sql = "create table tablica (qkey text primary key, qvalue text)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insert(String key, String value) throws Exception {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        if (select(key) != null)
        {
            throw new Exception("Key already exists in this database");
        }
        String sql = String.format("insert into tablica values('%s', '%s')", key, value);
        sqLiteDatabase.execSQL(sql);
    }

    public @Nullable String select(String key)
    {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String sql = String.format("select qvalue from tablica where qkey = '%s'", key);
        Cursor cur = sqLiteDatabase.rawQuery(sql, null);
        if (!cur.moveToFirst())
        {
            return null;
        }
        return cur.getString(0);
    }

    public void delete(String key) throws Exception
    {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        if (select(key) == null)
        {
            throw new Exception("Key does not exist in this database");
        }
        String sql = String.format("delete from tablica where qkey = '%s'", key);
        sqLiteDatabase.execSQL(sql);
    }

    public void update(String key, String value) throws Exception
    {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        if (select(key) == null)
        {
            throw new Exception("Key does not exist in this database. If this is intended, instead use Insert");
        }
        String sql = String.format("update tablica set qvalue = '%s' where qkey = '%s'", value, key);
        sqLiteDatabase.execSQL(sql);
    }
}
