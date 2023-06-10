package com.example.tipcalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

import kotlin.text._OneToManyTitlecaseMappingsKt;

public class MainActivity extends AppCompatActivity
        implements TextView.OnEditorActionListener, View.OnClickListener {

    private String billAmountString ="";
    private float  tipPercent = .15f;


    //define variables for the user interface controls we want to interact with

    private EditText billAmountEdittext;
    private  TextView percentTextView;
    private Button percentUpButton;
    private  Button percentDownButton;
    private TextView tipTextView;
    private TextView totalTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Get references to the UI Controls
        billAmountEdittext = findViewById(R.id.billAmountEditText);
        percentTextView = findViewById(R.id.percentTexView);
        percentUpButton = findViewById(R.id.percentUpButton);
        percentDownButton = findViewById(R.id.percentDownButton);
        tipTextView = findViewById(R.id.TipTextView);
        totalTextView = findViewById(R.id.TotalTexView);

        //set the listeners
        billAmountEdittext.setOnEditorActionListener(this);
        percentDownButton.setOnClickListener(this);
        percentUpButton.setOnClickListener(this);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("billAmountString",billAmountString);
        outState.putFloat("tipPercent", tipPercent);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState != null){
            billAmountString = savedInstanceState.getString("billAmountString", "");
            tipPercent = savedInstanceState.getFloat("tipPercent", 0.15f);

//            billAmountEdittext.setText(billAmountString);
            calculateAndDisplay();
        }
    }

    public void calculateAndDisplay(){
        //get the bill amount

       billAmountString = billAmountEdittext.getText().toString();
       float billAmount;
       if(billAmountString.equals("")){
           billAmount = 0;
       }
       else{
           billAmount = Float.parseFloat(billAmountString);
       }



       //Calculate tip and total
       float tipAmount = billAmount * tipPercent;
       float totalAmount = billAmount + tipAmount;

       //Display the result with formatting
       NumberFormat currency = NumberFormat.getCurrencyInstance();
       tipTextView.setText(currency.format(tipAmount));
       totalTextView.setText(currency.format(totalAmount));

       NumberFormat percent = NumberFormat.getPercentInstance();
       percentTextView.setText(percent.format(tipPercent));
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {

       if(v.getId() == R.id.percentDownButton){
           tipPercent = tipPercent - 0.01f;
           calculateAndDisplay();
       }
       else if(v.getId() == R.id.percentUpButton){
           tipPercent = tipPercent + 0.01f;
           calculateAndDisplay();
       }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        calculateAndDisplay();
        return false;
    }
}