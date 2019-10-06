package com.example.watchandlog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Anu extends AppCompatActivity {
    String button_name="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anu);
        Bundle bundle    = getIntent().getExtras();
        button_name = bundle.getString("button_name");
        TextView display_name = (TextView) findViewById(R.id.display_name);
        display_name.setText("Welcome "+button_name+"!!");


    }


    public void validate(View view) {


        final EditText pin = (EditText) findViewById(R.id.pin);
        final int password = Integer.parseInt(pin.getText().toString());
        final Intent int1;

        int1 = new Intent(this, option_page.class);
        TextView name = (TextView) findViewById(R.id.display_name);

        Bundle bundle = new Bundle();

        bundle.putString("button_name1",button_name);
        int1.putExtras(bundle);

        DatabaseReference reff = FirebaseDatabase.getInstance().getReference("Login");



        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {

                boolean flga = true;
                for(DataSnapshot keynode: dataSnapshot.getChildren())
                {
                   Login l=keynode.getValue(Login.class);

                    if(button_name.equals(l.getUname()) && pin.getText().toString().equals(l.getPass()))
                    {
                        finish();
                        startActivity(int1);
                        flga = false;
                        Toast toast=Toast.makeText(getApplicationContext(),"Logged in!!",Toast.LENGTH_SHORT);
                        //toast.setMargin(500,500);
                        toast.show();

                    }



                }

                if(flga){
                Toast toast=Toast.makeText(getApplicationContext(),"Wrong pin!!",Toast.LENGTH_SHORT);
                //toast.setMargin(500,500);
                toast.show();
                pin.setText("");}


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast toast=Toast.makeText(getApplicationContext(),"Hello bhosadiwale",Toast.LENGTH_SHORT);
                toast.setMargin(50,50);
                toast.show();
            }
        });
    }
};