package com.example.watchandlog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import static java.lang.StrictMath.abs;

public class balance_sheet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance_sheet);
        final TableLayout ll = (TableLayout) findViewById(R.id.amount_table);
        final Bundle bundle=getIntent().getExtras();
        final String button_name=bundle.getString("button_name");
        HashMap<String,Integer>user_contribution=new HashMap<>();




        final Set<String> keys = user_contribution.keySet();
        int k = 0;
        DatabaseReference reff = FirebaseDatabase.getInstance().getReference("Events");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap<String, Integer> user_contribution = (HashMap<String, Integer>) bundle.getSerializable("user_contribution");
                int k = 0;
                //final AlertDialog.Builder dlgAlert = new AlertDialog.Builder(getApplicationContext());
                int total_amount=0;


                for (DataSnapshot keynode : dataSnapshot.getChildren()) {
                    add_event Event = keynode.getValue(add_event.class);

                    //toast.setMargin(500,500);
                    int amount = Integer.parseInt(Event.getAmount());
                    total_amount+=amount;
                    user_contribution.put(Event.getName(), amount + user_contribution.get(Event.getName()));

                }
                Set<String>s=user_contribution.keySet();
                double equal_contribution=total_amount/4.0;
                double denominator=0.0;
                for(String i:s) {
                    if (user_contribution.get(i) > equal_contribution) {
                        denominator += user_contribution.get(i) - equal_contribution;
                    }
                }
                double numerator = user_contribution.get(button_name) - equal_contribution;





                for(String i:s)
                {
                    if (i.compareTo(button_name)!=0 && user_contribution.get(button_name)>equal_contribution) {
                        if(user_contribution.get(i) < equal_contribution)
                        {
                            double multiplier=equal_contribution-user_contribution.get(i);
                            double ans=(numerator/denominator)*multiplier;

                            TableRow row = new TableRow(getApplicationContext());
                            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                            row.setLayoutParams(lp);
                            TextView name = new TextView(getApplicationContext());
                            TextView amount = new TextView(getApplicationContext());
                            Button pay=new Button(getApplicationContext());
                            pay.setText("Received");

                            //name.setText(i);
                            amount.setText("You need to collect Rs." + String.format("%.2f",ans)+" from "+i);
                            //row.addView(name);
                            row.addView(amount);
                            row.addView(pay);
                            pay.setOnClickListener(new View.OnClickListener()
                            {
                                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(balance_sheet.this);

                                public void onClick(View v)
                                {
                                    dlgAlert.setMessage("Money Received");
                                    dlgAlert.setTitle("Success");
                                    dlgAlert.setPositiveButton("OK", null);
                                    dlgAlert.setCancelable(true);
                                    dlgAlert.create().show();

                                }
                            });
                            ll.addView(row, k++);
                        }

                    }
                    else if(i.compareTo(button_name)!=0 && user_contribution.get(button_name)<equal_contribution)
                    {
                        if(user_contribution.get(i) > equal_contribution)
                        {
                            double multiplier=equal_contribution-user_contribution.get(i);
                            double ans=(numerator/denominator)*multiplier;

                            TableRow row = new TableRow(getApplicationContext());
                            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                            row.setLayoutParams(lp);
                            TextView name = new TextView(getApplicationContext());
                            TextView amount = new TextView(getApplicationContext());
                            Button pay=new Button(getApplicationContext());
                            pay.setText("Pay");
                            //name.setText(i);
                            amount.setText("You need to pay Rs." + String.format("%.2f",abs(ans))+" to "+i);
                            //row.addView(name);
                            row.addView(amount);
                            row.addView(pay);

                            pay.setOnClickListener(new View.OnClickListener()
                            {
                                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(balance_sheet.this);
                                public void onClick(View v)
                                {
                                    dlgAlert.setMessage("Paid Successfully");
                                    dlgAlert.setTitle("Success");
                                    dlgAlert.setPositiveButton("OK", null);
                                    dlgAlert.setCancelable(true);
                                    dlgAlert.create().show();

                                }
                            });




                            ll.addView(row, k++);
                        }
                    }



                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });










    }
}

