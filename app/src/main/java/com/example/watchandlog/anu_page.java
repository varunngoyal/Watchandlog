package com.example.watchandlog;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class anu_page extends AppCompatActivity {
    String button_name="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anu_page);

        Bundle bundle = getIntent().getExtras();
        button_name = bundle.getString("button_name1");
        TextView person = (TextView) findViewById(R.id.person);
        person.setText(button_name);


    }

    public void submit(View view)
    {
        EditText event = (EditText) findViewById(R.id.event);
        EditText amount = (EditText) findViewById(R.id.amount);
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);

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
