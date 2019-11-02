package com.example.watchandlog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
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

     private String button_name="";
     private  TableLayout ll;
    private Button pay;
    private int k;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance_sheet);
        final Bundle bundle=getIntent().getExtras();
        final String button_name=bundle.getString("button_name");
        this.button_name=button_name;
        ll = (TableLayout) findViewById(R.id.amount_table);
        k=0;
        //final HashMap<String, Double> user_contribution = (HashMap<String, Double>) bundle.getSerializable("user_contribution");
        DatabaseReference reff3 = FirebaseDatabase.getInstance().getReference("accounts");
        reff3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String,Double>user_contribution=new HashMap<>();


                for(DataSnapshot keynode: dataSnapshot.getChildren()) {


                    account acc = keynode.getValue(account.class);
                    if (acc.getAccount_holder().equals(button_name)) {
                        user_contribution = acc.getUser_contribution();
                        Set<String> keys = user_contribution.keySet();
                        double amount = 0.0;
                        //k = 0;
                        int count=0;
                        for(String i: keys)
                        {
                            amount=user_contribution.get(i);
                            if (amount!=0 && i.compareToIgnoreCase(button_name)!=0)
                            {
                                count++;
                            }
                        }

                        for (String i : keys) {
                            amount = user_contribution.get(i);
                            final TableRow row = new TableRow(getApplicationContext());
                            //row.setId(1000+k);
                            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                            row.setLayoutParams(lp);
                            final TextView name = new TextView(getApplicationContext());
                            pay=new Button(getApplicationContext());
                            pay.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Button clicked_button=(Button)v;
                                    String button_text= clicked_button.getText().toString();//     v.getText().toString();
                                    //Toast toast=Toast.makeText(getApplicationContext(),""+v.toString(),Toast.LENGTH_SHORT);
                                    //toast.show();

                                    if(button_text.compareToIgnoreCase("pay")==0)
                                    {
                                        pay.setText("Pending...");
                                    }
                                    else if(button_text.compareToIgnoreCase("received")==0)
                                    {
                                        //Toast toast=Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_SHORT);
                                        //toast.show();
                                        //Intent intent=new Intent(getApplicationContext(),option_page.class);
                                        //startActivity(intent);
                                        //finish();

                                        //DELETING PARTICULAR ROW OF TABLE
                                        //
                                        final TableRow new_row=(TableRow) clicked_button.getParent();
                                        TextView new_view=(TextView)new_row.getChildAt(0);
                                        final String name_of_payer=new_view.getText().toString().split(" ")[6];
                                        //delete_row(new_row);

                                        //Toast toast=Toast.makeText(getApplicationContext(),""+name_of_payer,Toast.LENGTH_SHORT);
                                        //toast.show();
                                        settle_the_scores(name_of_payer,new_row);
                                    }



                                }
                            });

                            //pay = new Button(getApplicationContext());
                            //pay.setId(2000+k);

                            if(amount<0 && k<count && i.compareToIgnoreCase(button_name)!=0)
                            {
                                name.setText("You need to pay " + i + " Rs." + abs(amount));
                                pay.setText("Pay");
                                row.addView(name);
                                //row.addView(pay);
                                ll.addView(row, k++);
                                //Toast toast=Toast.makeText(getApplicationContext(),""+k,Toast.LENGTH_SHORT);
                                //toast.show();
                            }

                            else if (amount > 0 && k<count && i.compareToIgnoreCase(button_name)!=0) {
                                name.setText("You need to receive Rs."+amount+" from "+i);
                                pay.setText("received");
                                row.addView(name);
                                row.addView(pay);
                                ll.addView(row,k++);
                                //Toast toast=Toast.makeText(getApplicationContext(),""+k,Toast.LENGTH_SHORT);
                                //toast.show();

                            }
                            else
                            {
                                continue;
                            }




                        }


                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });








    }
    public final void settle_the_scores(final String name_of_payer,final TableRow new_row)
    {
        final DatabaseReference reff=FirebaseDatabase.getInstance().getReference("accounts");
        ll.removeView((View)new_row);
        ll.invalidate();
        ll.refreshDrawableState();


        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for(DataSnapshot keynode:dataSnapshot.getChildren())
                {
                    account acc=keynode.getValue(account.class);
                    String key=keynode.getKey();
                    Map<String,Double>user=acc.getUser_contribution();
                    if(acc.getAccount_holder().equals(button_name))
                    {
                        reff.child(key).child("user_contribution").child(name_of_payer).setValue(0);
                        double amount=user.get(button_name);
                        double paid=user.get(name_of_payer);
                        reff.child(key).child("user_contribution").child(button_name).setValue(amount-paid);
                    }
                    else if(acc.getAccount_holder().equals(name_of_payer))
                    {
                        reff.child(key).child("user_contribution").child(button_name).setValue(0);
                        double paid=abs(user.get(button_name));
                        double amount=user.get(name_of_payer);
                        reff.child(key).child("user_contribution").child(name_of_payer).setValue(amount+paid);

                    }
                    else
                    {
                        continue;
                    }
                }
                //Toast toast=Toast.makeText(getApplicationContext(),""+button_name,Toast.LENGTH_SHORT);
                //toast.show();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void delete_row(TableRow new_row)
    {
        ll.removeView(new_row);
        return;
    }
}

