package org.olumide.adebayo.decisionmaker;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import static android.R.attr.id;

/**
 * Created by oadebayo on 11/15/17.
 */

public class Employee extends Person {

    private long salary = 0l;

    public static final String TABLE = "employee";
    public static final String[] COLUMNS = {"age","lname","salary","color","diet","gender","food","food2","id","fname"};


    public Employee(){
        super();
    }

    public void setSalary(long l){ salary=l;    }
    public long getSalary(){ return salary;}

    public long insertIntoDB(SQLiteDatabase db){
        //Open connection to write data
       // SQLiteDatabase db = util.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("age",getAge());
        values.put("fname",getFname());
        values.put("lname",getLname());
        values.put("salary",getSalary());
        values.put("color",getColor());
        values.put("diet",getDiet());
        values.put("gender",getGender());
        values.put("food",getFood(0));
        values.put("food2",getFood(1));

        // Inserting Row
        long id = db.insert(Employee.TABLE, null, values);

      //  Log.d("Olu"," pk from db is "+id);

        setId(id);

        return id;

    }

    private static final String DELIM = "___";

    public static Employee deserialize(String s){

        Employee e= new Employee();
        String[] tmp = s.split(DELIM);

        e.setLname(tmp[0]);
        e.setSalary( Long.parseLong(tmp[1]));
        e.setAge( Integer.parseInt(tmp[2]));
        e.setColor(tmp[3]);
        e.addFood(tmp[4]);
        e.addFood(tmp[5]);
        e.setGender(tmp[6]);
        e.setDiet(tmp[7]);
        e.setFname(tmp[8]);
        return e;
    }
    public static String serialize(Employee e){

        StringBuffer buf = new StringBuffer();
        buf.append(e.getLname());
        buf.append(DELIM);
        buf.append(e.getSalary());
        buf.append(DELIM);
        buf.append(e.getAge());
        buf.append(DELIM);
        buf.append(e.getColor());
        buf.append(DELIM);
        buf.append(e.getFood(0));
        buf.append(DELIM);
        buf.append(e.getFood(1));
        buf.append(DELIM);
        buf.append(e.getGender());
        buf.append(DELIM);
        buf.append(e.getDiet());
        buf.append(DELIM);
        buf.append(e.getFname());

        return buf.toString();
    }
}
