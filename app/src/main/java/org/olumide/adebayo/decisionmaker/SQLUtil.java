package org.olumide.adebayo.decisionmaker;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.prefs.Preferences;

import static android.R.attr.id;
import static android.R.attr.value;
import static android.os.Build.VERSION_CODES.N;
import static android.provider.Contacts.SettingsColumns.KEY;
import static java.sql.Types.VARCHAR;
import static org.olumide.adebayo.decisionmaker.MainActivity.dbname;
import static org.olumide.adebayo.decisionmaker.R.id.grunt;

/**
 * Created by oadebayo on 11/15/17.
 */

public class SQLUtil extends SQLiteOpenHelper {

    private String dbname =null;
    private Context context= null;
    public SQLUtil(Context ctx, String _dname,int _dversion) {
        super(ctx, _dname, null, _dversion);
        dbname=_dname;
        context=ctx;
     }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public SQLiteDatabase getReadableDatabase() {
        return super.getReadableDatabase();
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        return super.getWritableDatabase();
    }

    public List<Employee> getEmployees(int grp, int idx){

        List<Employee> list = new ArrayList<Employee>();

        return list;
    }
    public void emptyTable(){

        SQLiteDatabase db = getWritableDatabase();
        String ddl = "delete from "+Employee.TABLE;
        db.execSQL(ddl);


    }

    private List<Employee> extract(Cursor cursor){
        List<Employee> list = new ArrayList<Employee>();


        int i = 0;
        if (cursor.moveToFirst()) {

            do {
                i++;
                Employee e = new Employee();
                e.setFname(cursor.getString( cursor.getColumnIndex("fname")));
                e.setLname(cursor.getString( cursor.getColumnIndex("lname")));

                e.setGender( cursor.getString( cursor.getColumnIndex("gender")));
                e.setColor(cursor.getString( cursor.getColumnIndex("color")));
                e.setDiet(cursor.getString( cursor.getColumnIndex("diet")));;
                e.addFood(cursor.getString( cursor.getColumnIndex("food")));
                e.addFood(cursor.getString( cursor.getColumnIndex("food2")));

                e.setSalary(cursor.getLong( cursor.getColumnIndex("salary")));
                e.setAge(cursor.getInt( cursor.getColumnIndex("age")));;
                e.setId(cursor.getInt( cursor.getColumnIndex("id")));
                list.add(e);

            } while (cursor.moveToNext());
        }

        cursor.close();

        Log.d("OluSQLLite","total fetched from db "+i);
        return list;


    }
    public List<Employee> getAll(){

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor =
        db.query(Employee.TABLE,Employee.COLUMNS,null,null,null,null,null);
        return extract(cursor);
    }

    public void deleteDB(String db){
        close();
        context.deleteDatabase(dbname);

    }

    public List<Employee> getDataBySQL(String selection, String[] selectionArgs){
        String table = Employee.TABLE;
        String[] columns = Employee.COLUMNS;
        String groupBy = null;
        String having = null;
        String orderBy = null;
        String limit = null;

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
        return extract(cursor);
    }
    public List<Employee> getDataByRawSQL(String sql,String[] vars){

        sql = "select * from "+Employee.TABLE +" "+sql;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor=       db.rawQuery(sql,vars);

    return extract(cursor);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String ddl = "CREATE TABLE " + Employee.TABLE + "("
                + " id INTEGER PRIMARY KEY AUTOINCREMENT ,"
                +  " color VARCHAR, "
                +  " age INTEGER, "
                + " food VARCHAR,"
                + " food2 VARCHAR, "
                + " gender VARCHAR, "
                + " lname VARCHAR, "
                + " fname VARCHAR, "
                + " salary DECIMAL(10,2),"
                +  " diet VARCHAR )";
        db.execSQL(ddl);

    }

    @Override
    public synchronized void close() {
        super.close();
    }

}
