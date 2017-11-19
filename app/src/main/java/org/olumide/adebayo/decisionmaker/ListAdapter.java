package org.olumide.adebayo.decisionmaker;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static org.olumide.adebayo.decisionmaker.R.id.age;

/**
 * Created by oadebayo on 11/15/17.
 */

public class ListAdapter extends ArrayAdapter<Employee> {

    private static List<Employee> employees;

    private LayoutInflater mInflater;
    private Context context ;

    public ListAdapter(@NonNull Context ctx, @LayoutRes int resource,  @NonNull List<Employee> objects) {

        super(ctx, resource,  objects);

        if( ctx == null){
            Log.d("Olu","ctx is null");
        }
        Log.d("Olu",objects.size()+" employess passed in");


        employees=objects;
        mInflater = LayoutInflater.from(ctx);
        Log.d("Olu","list adapter initialized");

        context=ctx;

    }

    @Override
    public void add(@Nullable Employee object) {
        super.add(object);
        Log.d("Olu","add object called");
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        Log.d("Olu","get count called "+employees.size() );
        return employees.size();
    }

    @Override
    public Employee getItem(int arg0) {
        // TODO Auto-generated method stub
        Log.d("Olu","get item called "+arg0);
        return employees.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        Log.d("Olu","get item id called "+arg0);
        // TODO Auto-generated method stub
        return arg0;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        Log.d("Olu","get view called "+position);

        Employee employee = employees.get(position);

        if(convertView == null){//inflate
            Log.d("Olu"," view is null");

            convertView = mInflater.inflate(R.layout.mylist,parent,false);
        } else {
            Log.d("Olu","view is not null");
            String pk = (String)convertView.getTag();
            if ( pk.equals( employee.getId()+"")){
                return convertView;
            }
        }

       // convertView.setBackgroundColor(Color.BLUE);
        //pupulate view
        LinearLayout left = convertView.findViewById(R.id.left);
        LinearLayout right = convertView.findViewById(R.id.right);

        TextView age = left.findViewById(R.id.age);


        TextView salary = left.findViewById(R.id.salary);

        TextView food = right.findViewById(R.id.food);
        TextView food2 = right.findViewById(R.id.food2);
        ImageView diet = convertView.findViewById(R.id.diet);
        ImageView gender = convertView.findViewById(R.id.gender);
        ImageView color = convertView.findViewById(R.id.color);
        TextView name = convertView.findViewById(R.id.name);
        convertView.setTag(employee.getId()+"");
        age.setText("AGE: "+ employee.getAge());
        salary.setText("Salary: $"+employee.getSalary());

        name.setText(employee.getName());

        food.setText("food1 "+employee.getFood(0));
        food2.setText("food 2 "+employee.getFood(1));
        diet.setImageResource( getDiet(employee.getDiet()));
        gender.setImageResource( getGender( employee.getGender()));
        color.setBackgroundColor(  getColor( employee.getColor()));




        return convertView;
    }




    private int getColor(String s){

        switch(s){
            case "blue": return Color.BLUE;
            case "green": return Color.GREEN;
            case "red": return Color.RED;
            case "yellow": return Color.YELLOW;
            case "orange": return Color.parseColor("#FFA500");

            default : return Color.BLACK;
        }
    }
    private int getGender(String s){
        if( s.equals(("male"))){
            return R.drawable.male;
        }
        return R.drawable.famale;
    }
    private int getDiet(String s){
        if( s.equals("poultry")){
            return R.drawable.poultry;
        }
        if( s.equals("redMeat")){
            return R.drawable.redmeat;
        }
        if( s.equals("vegetarian")){
            return R.drawable.vegetarian;
        }
        return 0;
    }
}
