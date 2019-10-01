package com.example.watchandlog;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button button;

    public void openActivity_anu() {
        /*DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("Login");
        Login login = new Login();
        login.setPass("1234");
        login.setUname("Anirudh");
        reff.push().setValue(login);

        login.setPass("12345");
        login.setUname("Vaibhav");
        reff.push().setValue(login);

        login.setPass("123456");
        login.setUname("Amit");
        reff.push().setValue(login);

        login.setPass("1234567");
        login.setUname("Varun");
        reff.push().setValue(login);*/

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


    DatabaseReference reff;
    Login login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        //Toast.makeText(MainActivity.this, "Firebase connection successful", Toast.LENGTH_LONG).show();
        /*button = (Button) findViewById(R.id.b_anu);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v1)
            {
                openActivity_anu();
            }
        });
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
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
