package com.example.watchandlog;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private int project_amount=0;
    private HashMap<String,Double>user_contribution=new HashMap<>();
   // private final Intent intent = new Intent(this, Anu.class);
    //private final Intent main_to_balance = new Intent(this, balance_sheet.class);

    public void sendMessage (View view){
        Intent intent = new Intent(this, Anu.class);
        Button b=(Button)view;
        String get_button_name=b.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString("button_name", get_button_name);
        bundle.putSerializable("user_contribution",user_contribution);
        intent.putExtras(bundle);
        Set<String>s=user_contribution.keySet();
       /* for(String i:s) {
            Toast toast = Toast.makeText(getApplicationContext(), ""+i+": " + user_contribution.get(i), Toast.LENGTH_SHORT);
            toast.show();
        }*/
        startActivity(intent);

    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DatabaseReference reff = FirebaseDatabase.getInstance().getReference("Events");
        DatabaseReference users = FirebaseDatabase.getInstance().getReference("Login");

        users.addValueEventListener((new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot keynode: dataSnapshot.getChildren())
                {
                    Login log_details  =keynode.getValue(Login.class);

                    user_contribution.put(log_details.getUname(),0.0);

                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        }));


        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot keynode: dataSnapshot.getChildren())
                {
                    add_event Event =keynode.getValue(add_event.class);
                    String uname=Event.getName();
                   project_amount+=Integer.parseInt(Event.getAmount());





                }


                TextView Project_amount = (TextView) findViewById(R.id.project_amount);
                Project_amount.setText("Rs. "+project_amount);
                project_amount=0;


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast toast=Toast.makeText(getApplicationContext(),"Hello bhosadiwale",Toast.LENGTH_SHORT);
                toast.setMargin(50,50);
                toast.show();
            }
        });






    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
