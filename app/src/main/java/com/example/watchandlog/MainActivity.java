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

public class MainActivity extends AppCompatActivity {

    private Button button;
    int project_amount=0;

    public void openActivity_anu() {


        Intent intent = new Intent(this, Anu.class);
        Button button_text = (Button) findViewById(R.id.b_anu);
        String get_button_name=button_text.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString("button_name", get_button_name);
        intent.putExtras(bundle);

        startActivity(intent);
    }

    public void sendMessage1 (View view){
        openActivity_anu();
    }


    public void openActivity_vai() {
        Intent intent = new Intent(this, Anu.class);
        Button button_text = (Button) findViewById(R.id.b_vai);
        String get_button_name=button_text.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString("button_name", get_button_name);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void sendMessage2 (View view){
        openActivity_vai();
    }

    public void openActivity_amit() {
        Intent intent = new Intent(this, Anu.class);
        Button button_text = (Button) findViewById(R.id.b_amit);
        String get_button_name=button_text.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString("button_name", get_button_name);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void sendMessage3 (View view){
        openActivity_amit();
    }

    public void openActivity_var() {
        Intent intent = new Intent(this, Anu.class);
        Button button_text = (Button) findViewById(R.id.b_var);
        String get_button_name=button_text.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString("button_name", get_button_name);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void sendMessage4 (View view){
        openActivity_var();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DatabaseReference reff = FirebaseDatabase.getInstance().getReference("Events");

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot keynode: dataSnapshot.getChildren())
                {
                    add_event Event =keynode.getValue(add_event.class);
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
