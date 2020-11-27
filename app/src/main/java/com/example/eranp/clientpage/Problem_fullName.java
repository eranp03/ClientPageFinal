package com.example.eranp.clientpage;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Problem_fullName extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_full_name);


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        Query query = reference.child("customer").orderByChild("id").equalTo(0);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
            if(dataSnapshot.exists())
            {
                for(DataSnapshot customer : dataSnapshot.getChildren())
                {


                }
            }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
