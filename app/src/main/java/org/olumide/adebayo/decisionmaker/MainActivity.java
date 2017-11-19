package org.olumide.adebayo.decisionmaker;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static org.olumide.adebayo.decisionmaker.R.id.imageView;


public class MainActivity extends AppCompatActivity {

    List<Employee> employeeList = new ArrayList<Employee>();
    static final String[] files = {"age","colors","diet","food","food2","gender","names","salary"};

    static final String dbname = "decision-db";
    int dbversion = 1;

    private SQLUtil sqlUtil = null;

    private ListView listView;

    boolean dataLoaded=false;
    SharedPreferences sharedPreferences= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(sqlUtil==null) {
            sqlUtil = new SQLUtil(MainActivity.this, Employee.TABLE, dbversion);
        }

        sharedPreferences=getSharedPreferences("csprefsA1",Context.MODE_PRIVATE);

        if(!dataLoaded){
            loadData();
        }



    }

    public void loadEmployeesFromSharedPreferences(int cnt){

        employeeList.clear();//empty first

        for(int i=0;i<cnt;i++){

            String str = sharedPreferences.getString("employee_"+i,null);
            if( str != null) {
                Employee e = Employee.deserialize(str);
                employeeList.add(e);
            }
        }
    }
    private void saveEmployeesToSharedPreferences(){

        SharedPreferences.Editor editor = sharedPreferences.edit();

        int cnt = 0;
        for(Employee e: employeeList){
            String s = Employee.serialize(e);

            editor.putString("employee_"+cnt++,s);
        }
        editor.putInt("employeeCount",cnt);
        editor.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);

        //add grunt's questions
        MenuItem menuItem = menu.findItem(R.id.grunt);
        SubMenu subMenu = menuItem.getSubMenu();
        for(int i=0;i<Constants.gruntQuestions.size();i++){
            subMenu.add(0,i,0,Constants.gruntQuestions.get(i));
        }

        menuItem = menu.findItem(R.id.milton);
        subMenu = menuItem.getSubMenu();
        for(int i=0;i<Constants.miltonQuestions.size();i++){
            subMenu.add(1,i,0,Constants.miltonQuestions.get(i));
        }

        menuItem = menu.findItem(R.id.common);
        subMenu = menuItem.getSubMenu();
        for(int i=0;i<Constants.commonQuestion.size();i++){
            subMenu.add(2,i,0,Constants.commonQuestion.get(i));
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Log.d("Olu",item.getGroupId()+" "+ item.getItemId()+ " "+item.getTitle());

        if( item.getItemId() == R.id.delete){
            Log.d("Olu","calling delete db");
            deleteDatabase();
            Log.d("Olu", "done delete db");
            dataLoaded=false;
            revert();
            return true;
        }

        if(! dataLoaded){ loadData();  }

        Log.d("OluA","done reloading data");

        if( item.getItemId() == R.id.all){
            getAllEmployees();
            redraw();
            return true;
        }

        int gid = item.getGroupId();
        int itemId=item.getItemId();
        String sql = null;

        if( gid==1) {//milton
            sql = Constants.QUERIES.get(gid).get(itemId);
            if( sql == null){
                return true;
            }
            Log.d("OluA",sql);

            employeeList = sqlUtil.getDataBySQL(sql,Constants.MiltonArgs.get(itemId));
            Log.d("Olu",""+employeeList.size());
            redraw();
            return true;
        }
        if( gid==0 || gid==2){//grunt
            sql = Constants.QUERIES.get(gid).get(itemId);
            if( sql == null){
                return true;
            }

            Log.d("Olu",sql);


            employeeList = sqlUtil.getDataByRawSQL(sql,null);
            redraw();

            Log.d("Olu",employeeList.size()+"");
            return true;
        }


        return super.onOptionsItemSelected(item);

    }



    public void loadData(){

        //we only load data if just opening app or after a delete db call
        //get a new instance of this to ensure clean performance
        sqlUtil = new SQLUtil(MainActivity.this, Employee.TABLE, dbversion);


        int employeeCount = sharedPreferences.getInt("employeeCount",0);
        Log.d("Olu","here...cnt is "+employeeCount);

        if( employeeCount == 0) {//load from file

            loadFileAsset();
            if(sqlUtil==null){
                sqlUtil = new SQLUtil(MainActivity.this,Employee.TABLE,dbversion);

            }
            saveEmployeesToDB();
            saveEmployeesToSharedPreferences();
        }else{

            Log.d("Olu","employee count in sp is "+employeeCount);
            loadEmployeesFromSharedPreferences(employeeCount);
            sqlUtil.emptyTable();
            saveEmployeesToDB();
        }
    }

    //load all the file assets
    public boolean loadFileAsset(){
        ArrayList<String> age, color, diet, food, food2, gender, names,salary;
        names=null;
        age=null;
        color=null;
        diet=null;
        food=null;
        food2=null;
        gender=null;
        salary=null;

        for(String _s: files){
            ArrayList<String> tmp = loadFileIntoArray(_s+".txt");

            if( tmp == null) {//problem
            }
            switch(_s){
                case "age": age=tmp; break;
                case "colors": color=tmp; break;
                case "diet": diet=tmp; break;
                case "food": food=tmp; break;
                case "food2": food2=tmp; break;
                case "gender" : gender=tmp;break;
                case "names": names=tmp; break;
                case "salary": salary=tmp;break;
                default: break;
            }
        }

        //loop thru' all the arrays to create an employee
        for(int i=0;i<names.size();i++){

                String[] _tmp = names.get(i).split(" ");
                Employee e = new Employee();
                //e.setName(names.get(i));
                e.setFname(_tmp[0]);
                e.setLname(_tmp[1]);
                e.setColor(color.get(i));
                e.setDiet(diet.get(i));
                e.setGender(gender.get(i));
                e.addFood(food.get(i));
                e.addFood(food2.get(i));

                long l = Long.parseLong(salary.get(i));
                e.setSalary(l);

                int _age = Integer.parseInt(age.get(i));
                e.setAge(_age);

                //add to list
                employeeList.add(e);


        }
        return true;
    }

    //save the employee list into the db
    private void saveEmployeesToDB(){
        for(Employee e: employeeList){
            e.insertIntoDB(sqlUtil.getWritableDatabase());
        }
    }

    //given a file name, return the data in an array list of strings
    public ArrayList<String> loadFileIntoArray(String filename){

        ArrayList<String> list = new ArrayList<String>();
        String line = "";
        try{
            InputStream iss = MainActivity.this.getResources().getAssets().open(filename);

            if( iss != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(iss));
                if( reader != null){
                    while( ( line = reader.readLine()) != null){
                        list.add( line.trim());
                    }
                    reader.close();
                }

                iss.close();
            }

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
        return list;

    }

    public void getAllEmployees(){

        employeeList = sqlUtil.getAll();
    }

    public boolean deleteDatabase(){

        Log.d("Olu",sharedPreferences.contains("employeeCount")+"");

        sharedPreferences.edit().remove("employeeCount").apply();
        Log.d("Olu",sharedPreferences.contains("employeeCount")+"");


        sharedPreferences.edit().clear().apply();
        sqlUtil.deleteDB(dbname);

        employeeList.clear();
        dataLoaded=false;
        return true;
    }

    /* call this anytime you update the employeelist */
    private void redraw(){

        findViewById(R.id.t1).setVisibility(View.GONE);
        findViewById(R.id.t2).setVisibility(View.GONE);
        findViewById(imageView).setVisibility(View.GONE);


        Log.d("Olu","adding "+employeeList.size()+" rows to list view");

        listView = (ListView) findViewById(R.id.list);

        ListAdapter listAdapter = new ListAdapter(MainActivity.this,R.layout.mylist,employeeList);

        listView.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();

        TextView t= (TextView)findViewById(R.id.count);
        t.setText("Entries "+employeeList.size());

        listView.setVisibility(View.VISIBLE);

        LinearLayout.LayoutParams lvp= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        listView.setLayoutParams(lvp);
        t.setVisibility(View.VISIBLE);
    }

    //call this when there's nothing to show
    private void revert(){

        try {
            listView.setVisibility(View.GONE);
            listView.clearChoices();
            findViewById(R.id.count).setVisibility(View.GONE);
        }catch(Exception e){

        }
        findViewById(R.id.t1).setVisibility(View.VISIBLE);
        findViewById(R.id.t2).setVisibility(View.VISIBLE);
        findViewById(imageView).setVisibility(View.VISIBLE);


    }

}
