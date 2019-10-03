package com.example.watchandlog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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


public class anu_page extends AppCompatActivity {
    String button_name="";
    int total_amount = 0;
    int project_amount=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anu_page);

        Bundle bundle = getIntent().getExtras();
        button_name = bundle.getString("button_name1");
        TextView person = (TextView) findViewById(R.id.person);
        person.setText("Welcome " +button_name+"!!");

        DatabaseReference reff = FirebaseDatabase.getInstance().getReference("Events");

       reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                for(DataSnapshot keynode: dataSnapshot.getChildren())
                {
                    add_event Event =keynode.getValue(add_event.class);

                    if(button_name.equalsIgnoreCase(Event.getName())) {

                        total_amount+=Integer.parseInt(Event.getAmount());


                    }

                    project_amount+=Integer.parseInt(Event.getAmount());

                }

                TextView Total = (TextView) findViewById(R.id.total_amount);
                Total.setText(""+total_amount);
                TextView Debt_text = (TextView) findViewById(R.id.debt_text);
                TextView Project_amount = (TextView) findViewById(R.id.debt);

                if(((project_amount/4.0)-total_amount)<=0)
                {
                    Debt_text.setText("Your credit ");
                    Project_amount.setText(""+(total_amount-(project_amount/4.0)));
                    Project_amount.setTextColor(Color.GREEN);
                }
                else
                {
                    Project_amount.setText(""+((project_amount/4.0)-total_amount));
                }
                project_amount=0;
                total_amount=0;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast toast=Toast.makeText(getApplicationContext(),"Hello bhosadiwale",Toast.LENGTH_SHORT);
                toast.setMargin(50,50);
                toast.show();
            }
        });

    }

    public void submit(View view)
    {
        EditText event = (EditText) findViewById(R.id.event);
        EditText amount = (EditText) findViewById(R.id.amount);
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
