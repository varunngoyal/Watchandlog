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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static java.lang.StrictMath.abs;

public class balance_sheet extends AppCompatActivity {

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance_sheet);
        final Bundle bundle=getIntent().getExtras();
        final String button_name=bundle.getString("button_name");
        //final HashMap<String, Double> user_contribution = (HashMap<String, Double>) bundle.getSerializable("user_contribution");
        DatabaseReference reff3 = FirebaseDatabase.getInstance().getReference("accounts");
        reff3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String,Double>user_contribution=new HashMap<>();
                TableLayout ll = (TableLayout) findViewById(R.id.amount_table);

                for(DataSnapshot keynode: dataSnapshot.getChildren()) {


                    account acc = keynode.getValue(account.class);
                    if (acc.getAccount_holder().equals(button_name)) {
                        user_contribution = acc.getUser_contribution();
                        Set<String> keys = user_contribution.keySet();
                        double amount = 0.0;
                        int k = 0;
                        for (String i : keys) {
                            amount = user_contribution.get(i);
                            TableRow row = new TableRow(getApplicationContext());
                            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                            row.setLayoutParams(lp);
                            TextView name = new TextView(getApplicationContext());
                            Button pay = new Button(getApplicationContext());

                            if(amount<0)
                            {
                                name.setText("You need to pay " + i + " Rs." + abs(amount));
                                pay.setText("Pay");
                                row.addView(name);
                                row.addView(pay);
                                ll.addView(row, k++);
                            }

                            else if (amount > 0) {
                                name.setText("You need to receive Rs."+amount+" from "+i);
                                pay.setText("received");
                                row.addView(name);
                                row.addView(pay);
                                ll.addView(row,k++);
                            }
                        }

                    }

                }}

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






         /*Map<String,Double> names = new HashMap<>();
         Set<String> keys = user_contribution.keySet();

       for(String name:keys)
        {
            if(!name.equals(button_name))
            {
                names.put(name,0.0);
            }
        }

        DatabaseReference reff = FirebaseDatabase.getInstance().getReference("accounts");
        account acc = new account(button_name,names);
        String key = reff.push().getKey();
        reff.child(key).setValue(acc).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });*/



    }
}

