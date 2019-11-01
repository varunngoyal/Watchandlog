package com.example.watchandlog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class option_page extends AppCompatActivity {

    private Button button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_page);
    }

    public void goto_page(View view)
    {
        final Intent intent;
        Button balance_sheet = (Button) findViewById(R.id.balance_sheet);
        Button add_data = (Button) findViewById(R.id.add_data);

        if(view==balance_sheet)
        {
            intent = new Intent(this, balance_sheet.class);
            Bundle bundle = getIntent().getExtras();
            intent.putExtras(bundle);

            //finish();
            //commented the finish() function because it destroys this page. so on pressing back button it goes to main page
            startActivity(intent);
            //finish();
        }
        else
        {
            intent = new Intent(this, anu_page.class);
            Bundle bundle = getIntent().getExtras();
            intent.putExtras(bundle);
            startActivity(intent);
            //finish();
        }
    }
}
