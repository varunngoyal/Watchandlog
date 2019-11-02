package com.example.watchandlog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.text.*;
import java.time.*;
import java.util.*;
import static java.lang.StrictMath.abs;


public class anu_page extends AppCompatActivity {
    String button_name="";
    double total_amount_contributed = 0.0;
    int project_amount=0;
    double received = 0.0;
    double paid = 0.0;
    double perhead = 0.0;
    double credit_debit_amount=0.0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anu_page);
        //Toast new_
        Bundle bundle = getIntent().getExtras();
        final TextView Total = (TextView) findViewById(R.id.total_amount);
        button_name = bundle.getString("button_name");
        final TextView Debt_text = (TextView) findViewById(R.id.debt_text);
        final TextView Project_amount = (TextView) findViewById(R.id.debt);

        TextView person = (TextView) findViewById(R.id.person);
        person.setText("Welcome " + button_name + "!!");
        DatabaseReference reff2 = FirebaseDatabase.getInstance().getReference("accounts");
        reff2.addValueEventListener(new ValueEventListener() {
            @Override
          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              Map<String,Double> user_contribution=new HashMap<>();
              for(DataSnapshot keynode : dataSnapshot.getChildren())
              {
                  account acc = keynode.getValue(account.class);

                  if(button_name.equals(acc.getAccount_holder()))
                  {
                      user_contribution=acc.getUser_contribution();
                      Set<String>keys=user_contribution.keySet();
                      for(String i:keys)
                      {
                          if(i.compareToIgnoreCase(button_name)==0)
                          {
                              total_amount_contributed=user_contribution.get(i);
                              Total.setText(""+total_amount_contributed);

                          }
                          else
                          {
                              credit_debit_amount+=user_contribution.get(i);
                              //Toast toast=Toast.makeText(getApplicationContext(),"credit_debit_amount: "+credit_debit_amount,Toast.LENGTH_SHORT);
                              //toast.show();
                          }
                      }
                  }
              }
                if(credit_debit_amount>=0)
                {
                    Debt_text.setText("Your credit ");
                    Project_amount.setText(""+credit_debit_amount);
                    Project_amount.setTextColor(Color.GREEN);
                }
                else
                {
                    Debt_text.setText("Your debt ");
                    Project_amount.setText(""+abs(credit_debit_amount));
                }
                credit_debit_amount=0;
                total_amount_contributed=0;

          }
          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {

          }
      });
        //Toast toast=Toast.makeText(getApplicationContext(),"total amount"+total_amount_contributed,Toast.LENGTH_SHORT);
        //toast.show();


     project_amount=0;
     total_amount_contributed=0;
    }

    public void submit(View view)
    {
        EditText event = (EditText) findViewById(R.id.event);
        final EditText amount = (EditText) findViewById(R.id.amount);
        final Double amount_currently_entered=Double.parseDouble(amount.getText().toString());
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
        DateFormat formatter =new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        String name = button_name;
        Date date=new Date();
        add_event Event = new add_event(amount.getText().toString(),event.getText().toString(),formatter.format(date),name);

        DatabaseReference reff = FirebaseDatabase.getInstance().getReference("Events");


       String key = reff.push().getKey();
        reff.child(key).setValue(Event).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });

        perhead = Double.parseDouble(amount.getText().toString())/4;
/*--------------------------------------------------------------------------------------------------------------------------*/
       // updating accounts
        final DatabaseReference reff3 = FirebaseDatabase.getInstance().getReference("accounts");

        reff3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot keynode : dataSnapshot.getChildren())
                {

                    account acc = keynode.getValue(account.class);
                    String key2 = keynode.getKey();
                    Map<String,Double> user = acc.getUser_contribution();
                    Set<String>  keys = user.keySet();
                   if(acc.getAccount_holder().equals(button_name))
                    {
                        for(String name:keys)
                        {
                            if(name.compareToIgnoreCase(button_name)!=0)
                                reff3.child(key2).child("user_contribution").child(name).setValue(user.get(name)+perhead);
                            else
                            {

                                reff3.child(key2).child("user_contribution").child(name).setValue(user.get(name)+amount_currently_entered);

                            }


                        }

                    }
                    else
                    {
                        for(String name:keys)
                        {
                            if(name.equals(button_name))
                                reff3.child(key2).child("user_contribution").child(name).setValue(user.get(name)-perhead);
                        }

                    }
                    keys.clear();


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

/*-------------------------------------------------------------------------------------------------------------------*/






        dlgAlert.setMessage("Data added to the database");
        dlgAlert.setTitle("Success");
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
        event.setText("");
        amount.setText("");

        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {



                    }
                });
    }



}
