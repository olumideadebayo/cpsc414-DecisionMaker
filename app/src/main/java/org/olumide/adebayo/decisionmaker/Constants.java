package org.olumide.adebayo.decisionmaker;

import java.util.ArrayList;
import java.util.HashMap;

import static android.os.Build.VERSION_CODES.M;
import static org.olumide.adebayo.decisionmaker.R.id.milton;


/**
 * Created by oadebayo on 11/15/17.
 */

public final class Constants {

    public static ArrayList<String> gruntQuestions;
    public static ArrayList<String> miltonQuestions;
    public static ArrayList<String> commonQuestion;

    public static final HashMap<Integer,HashMap<Integer,String>> QUERIES
            = new HashMap<Integer,HashMap<Integer,String>>();

    public static final HashMap<Integer, String[]> MiltonArgs
            = new HashMap<Integer,String[]>();

    static{
        gruntQuestions = new ArrayList<String>();

        gruntQuestions.add("Show a list of employees who make more than 70K");
        gruntQuestions.add("Show a list of employees who are older than 40 that have poultry as their diet");
        gruntQuestions.add("Show a list of employees who like turkey as their first food and that are under 40");
        gruntQuestions.add("Show a list of employees who are male that like the color red and are red meat lovers");
        gruntQuestions.add("Show a list of employees who are female that are vegetarians that like tofu for their first or second favorite food.");

        miltonQuestions= new ArrayList<String>();

        miltonQuestions.add("Show a list of employees who like chicken");
        miltonQuestions.add("Show a list of employees who like goat");
        miltonQuestions.add("Show a list of employees who make less than 60K");
        miltonQuestions.add("Show a list of employees who are younger than 38");
        miltonQuestions.add("Show a list of employees who like blue that are female");

        commonQuestion = new ArrayList<String>();
        commonQuestion.add("Get a list of employees who their last name starts with M or O and favorite color is blue.");
        commonQuestion.add("Get a list of employees who are female that eat red meat that like yellow then get a list of employees that are male that eat poultry that likes blue. Then from those two lists, return a list that is over 30");

        HashMap<Integer,String> milton = new HashMap<Integer, String>();
        milton.put(0,"  food like ? OR food2 like ?");
        MiltonArgs.put(0, new String[] {"chicken","chicken"});
        milton.put(1," food=? or food2=?");
        MiltonArgs.put(1, new String[]{"goat","goat"});
        milton.put(2," salary < ?");
        MiltonArgs.put(2,new String[] {"60"});
        milton.put(3," age < ?");
        MiltonArgs.put(3,new String[] {"30"});
        milton.put(4," gender=? and color=?");
        MiltonArgs.put(4,new String[]{"female","blue"});

        HashMap<Integer,String> grunt = new HashMap<Integer,String>();

        grunt.put(0," where salary>70000");
        grunt.put(1," where age>40 and diet='poultry'");
        grunt.put(2," where age<40 and food='turkey' ");
        grunt.put(3," where gender='male' and color='red' and diet='redMeat' ");
        grunt.put(4," where gender='female' and diet='vegetarian' and ( food='tofu' or food2='tofu') ");

        HashMap<Integer,String> common = new HashMap<Integer,String>();
        common.put(0," where color='blue' and (lname like 'M%' or lname like 'O%' )");

        common.put(1," where gender>30 and ( (gender='female' and diet='redMeat' and color='yellow') OR (gender='male' and diet='poultry' and color='blue'))");


        QUERIES.put(0,grunt);
        QUERIES.put(2,common);
        QUERIES.put(1,milton);


    }
}
