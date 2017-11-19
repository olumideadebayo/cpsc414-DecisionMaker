package org.olumide.adebayo.decisionmaker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oadebayo on 11/15/17.
 */

public class Person {

    private int age =1;
    private String gender ;
    private String diet;
    private String color;
    private ArrayList<String> food = new ArrayList<String>();
    private String name;
    private String fname;
    private String lname;
    private long id;

    public Person(){}

    public long getId(){ return id;}
    public void setId(long i){ id =i;}

    public void setAge(int i){ age=i;}
    public int getAge(){ return age;}

    public void setGender(String s){ gender=s;}
    public String getGender(){ return gender;}

    public void setDiet(String s){ diet = s;}
    public String getDiet( ){ return diet;}

    public void setColor(String s){ color= s;}
    public String getColor(){ return color;}

    public void setName(String s){ name=s;}
    public String getName(){ return fname+" "+lname;}

    public void addFood(String s){ food.add(s);}
    public List<String> getAllFood(){ return food;}
    public String getFood(int i){ return food.get(i);}

    public void setLname(String s){ lname=s;}
    public String getLname(){return lname;}

    public void setFname(String s){ fname=s;}
    public String getFname(){return fname;}


}
