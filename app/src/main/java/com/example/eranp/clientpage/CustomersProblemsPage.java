package com.example.eranp.clientpage;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CustomersProblemsPage extends Activity {

    ListView listViewCustomersProblems;
    List<ProblemDeviceCustomer> problemDeviceCustomer;
    DatabaseReference databaseProblemDeviceCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers_problems_page);
        listViewCustomersProblems = (ListView)findViewById(R.id.LVCustomerProblem);
        databaseProblemDeviceCustomer = FirebaseDatabase.getInstance().getReference("problemDeviceCustomer");
        problemDeviceCustomer = new ArrayList<>();

        final Intent intent = getIntent();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_custL:
                        Intent intent1 = new Intent(CustomersProblemsPage.this, CustomersPage.class);
                        startActivity(intent1);

                        break;


                    case R.id.nav_AddCustomer:
                        Intent intent3 = new Intent(CustomersProblemsPage.this, MainActivity.class);
                        startActivity(intent3);
                        break;

                }


                return false;
            }
        });

        listViewCustomersProblems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ProblemDeviceCustomer pDC = problemDeviceCustomer.get(i);

                Intent intent1 = new Intent(CustomersProblemsPage.this, ClientPageData.class);
                intent1.putExtra("id" ,pDC.getProblemDeviceCustomerId());
                intent1.putExtra("fName", pDC.getfNamePDC());
                intent1.putExtra("lName", pDC.getlNamePDC());
                intent1.putExtra("phone", pDC.getPhoneNumPDC());
                intent1.putExtra("problemShort", pDC.getProDevDetShortPDC());
                intent1.putExtra("nameDevice", pDC.getDeviceNamePDC());
                intent1.putExtra("modelDevice", pDC.getDeviceModelPDC());
                intent1.putExtra("email", pDC.geteMailPDC());
                intent1.putExtra("problemFull",pDC.getProDevDetPDC());
                intent.putExtra("date", pDC.getDataArrivedPDC());
                startActivity(intent1);

            }
        });
        listViewCustomersProblems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                new AlertDialog.Builder(CustomersProblemsPage.this)
                        .setMessage("Are you sure you want to delete?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                deleteCustomer(problemDeviceCustomer.get(i).getProblemDeviceCustomerId());
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();



                return false;
            }
        });




    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseProblemDeviceCustomer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                problemDeviceCustomer.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting customer
                    ProblemDeviceCustomer pDC = postSnapshot.getValue(ProblemDeviceCustomer.class);
                    //adding customer to the list
                    problemDeviceCustomer.add(pDC);
                }

                CustomerProblemAdapter customerProblemAdapter = new CustomerProblemAdapter(CustomersProblemsPage.this, (ArrayList<ProblemDeviceCustomer>) problemDeviceCustomer);
                //attaching adapter to the listview
                listViewCustomersProblems.setAdapter(customerProblemAdapter);



            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
    private boolean deleteCustomer(String id) {
        //getting the specified Customer reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("customer").child(id);
        DatabaseReference dRP = FirebaseDatabase.getInstance().getReference("device").child(id);
        DatabaseReference dRD = FirebaseDatabase.getInstance().getReference("problem").child(id);
        DatabaseReference dPDC = FirebaseDatabase.getInstance().getReference("problemDeviceCustomer").child(id);

        //removing Customer
        dR.removeValue();
        dRP.removeValue();
        dRD.removeValue();
        dPDC.removeValue();

        Toast.makeText(getApplicationContext(), "Customer Deleted", Toast.LENGTH_LONG).show();

        return true;
    }

}
