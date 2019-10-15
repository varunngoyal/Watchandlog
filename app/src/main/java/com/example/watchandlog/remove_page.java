package com.example.watchandlog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.view.Event;

import static java.lang.StrictMath.abs;

public class remove_page extends AppCompatActivity {

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_page);

        Bundle bundle = getIntent().getExtras();
        final String uname = (String) bundle.getString("button_name");

        final add_event[] EventList = new add_event[50];

        //steps to make this page
        // 1. show list of events each with a remove button
        //      1.1 get session variable of username    done
        //      1.2 seperate event list                 done
        //      1.3 put a for loop and make an item of event    done
        //      1.4 make a linear layout with all events
        //      1.5 write code for button onclick event
        // 2. after clicking on remove a dialog box for confirmation
        // 3. after that delete the event and show the same screen

        //retrieve events from Firebase events table

        /*Toast toast = Toast.makeText(getApplicationContext(), uname, Toast.LENGTH_SHORT);
        toast.show();*/

        DatabaseReference reff = FirebaseDatabase.getInstance().getReference("Events");

        reff.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                int k = -1;
                for(DataSnapshot keynode: dataSnapshot.getChildren())  {
                    add_event event1 = (add_event) keynode.getValue(add_event.class);
                    Toast toast = Toast.makeText(getApplicationContext(), uname+" "+event1.getName()+" "+uname.equals(event1.getName()), Toast.LENGTH_SHORT);
                    toast.show();

                    if(uname.equals(event1.getName()))
                    {
                        ++k;
                        EventList[k] = new add_event();
                        EventList[k] = event1;
                        toast = Toast.makeText(getApplicationContext(), event1.getAmount()+" "+event1.getName()+" "+event1.getDate()+" "+event1.getEvent(), Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
                add_event event1 = EventList[0];
                /*Toast toast = Toast.makeText(getApplicationContext(), event1.getAmount()+" "+event1.getName()+" "+event1.getDate()+" "+event1.getEvent(), Toast.LENGTH_SHORT);
                toast.show();*/

                for(int i=0;i<=k;i++) {
                    event1 = EventList[i];
                    Toast toast = Toast.makeText(getApplicationContext(), event1.getAmount()+" "+event1.getName()+" "+event1.getDate()+" "+event1.getEvent(), Toast.LENGTH_SHORT);
                    toast.show();
                }

                //this will create container for event list
                TableLayout l1 = (TableLayout) findViewById(R.id.listofevents);
                for(int i=0;i<=k;i++)
                {
                    Toast toast = Toast.makeText(getApplicationContext(), EventList[i].getAmount()+" "+EventList[i].getName()+" "+EventList[i].getDate()+" "+EventList[i].getEvent(), Toast.LENGTH_SHORT);
                    toast.show();
                    TableRow row = new TableRow(getApplicationContext());
                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT);

                    row.setLayoutParams(lp);

                    TextView name = new TextView(getApplicationContext());
                    Button pay = new Button(getApplicationContext());
                    name.setText(EventList[i].getEvent());
                    pay.setId(i);
                    pay.setText("REMOVE");

                    row.addView(name);
                    row.addView(pay);

                    l1.addView(row,i);
                }

                for(int i=0;i<=k;i++) {
                    final Button btn = (Button) findViewById(i);
                    Toast toast = Toast.makeText(getApplicationContext(), "Button "+i, Toast.LENGTH_SHORT);
                    toast.show();
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //remove selected event
                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                            Query q1 = ref.child("Events").orderByChild("event").equalTo(EventList[btn.getId()].getEvent());

                            q1.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot keynode: dataSnapshot.getChildren()) {
                                        keynode.getRef().removeValue();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                        }
                    });
                }

                k = -1;


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






        /*TextView txt_amount = new TextView(this);
        txt_timestamp.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        txt_timestamp.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        txt_timestamp.setText(EventList[0].getAmount());
        ll.addView(txt_amount);

        Button btn_remove_event = new Button(this);
        btn_remove_event.setText("Remove");
        ll.addView(btn_remove_event);*/
        /*for(int i = 0; i < 5; i++) {
            Button btn = new Button(this);
            btn.setId(i);
            btn.setText("dynamic buttion " + i);
            ll.addView(btn);

        }*/
    }//end of onCreate
}//end of class
