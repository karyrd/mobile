package com.example.calculator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;

public class Vars {

    public static double BMR;
    public static double AMR;
    public static ArrayList<Double> AMRlist = new ArrayList<Double>
            (Arrays.asList(1.2, 1.375, 1.55, 1.725, 1.9));
    public static Spinner age;
    public static Spinner weight;
    public static Spinner height;
    public static AlertDialog.Builder adb;

    public static double GetBMR(int age, int height, int weight, char gender)
    {
        double bmr = 0;
        if(gender == 'M') {
            bmr = 66.473 + (13.7516 * weight) + (5.0033 * height) - (6.755 * age);
        } else if(gender == 'F'){
            bmr = 655.0955 + (9.5634 * weight) + (1.8496 * height) - (4.6756 * age);
        }
        return bmr;
    }

    public static AlertDialog.Builder GetPopupValid(AlertDialog.Builder adb, String msg)
    {
        adb.setTitle("Your results");
        adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //dismiss the dialog
            }
        });
        adb.setCancelable(true);
        adb.setMessage("Your daily calorie intake is " + msg + " cals");

        return adb;
    }

    public static AlertDialog.Builder GetPopupInvalid() {
        adb.setTitle("Error");
        adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //dismiss the dialog
            }
        });
        adb.setCancelable(true);
        adb.setMessage("Please select your sex");

        return adb;
    }
}
