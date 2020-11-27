package com.example.eranp.clientpage;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class CustomersPage extends Activity {

    private ListView listViewCustomers;
   private List<Customer> customers;
    private List<Problem> problems;
    private List<Device> devices;
    private DatabaseReference databaseCustomers, databaseProblem, databaseDevice,db;
    private String option1u, option2u, option3u;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers_page);
        db = FirebaseDatabase.getInstance().getReference();
        databaseCustomers = FirebaseDatabase.getInstance().getReference("customer");
        databaseProblem = FirebaseDatabase.getInstance().getReference("problem");
        databaseDevice = FirebaseDatabase.getInstance().getReference("device");
         option1u = getResources().getString(R.string.option1u);
         option2u = getResources().getString(R.string.option2u);
         option3u = getResources().getString(R.string.option3u);
        listViewCustomers = (ListView)findViewById(R.id.listViewCostumers);
        customers = new ArrayList<>();
        devices = new ArrayList<>();
        problems = new ArrayList<>();

        Intent intentl = getIntent();


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.nav_allCust:
                        Intent intent2 = new Intent(CustomersPage.this, CustomersProblemsPage.class);
                        startActivity(intent2);

                        break;
                    case R.id.nav_AddCustomer:
                        Intent intent3 = new Intent(CustomersPage.this, MainActivity.class);
                        startActivity(intent3);
                        break;

                }


                return false;
            }
        });

        listViewCustomers.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Customer customer = customers.get(i);
                showUpdateDeleteDialogCustomer(customer.getCustomerId(), customer.getfName() + " " + customer.getlName(),customer.getfName(),customer.getlName(),customer.getPhoneNum(),customer.geteMail());
                return true;
            }
        });

        listViewCustomers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Customer customer = customers.get(i);
                Problem problem = problems.get(i);
                Device device = devices.get(i);
                Intent intent = new Intent(CustomersPage.this, ClientPageData.class);
                intent.putExtra("id", customer.getCustomerId());
                intent.putExtra("fName", customer.getfName());
                intent.putExtra("lName", customer.getlName());
                intent.putExtra("phone", customer.getPhoneNum());
                intent.putExtra("email", customer.geteMail());
                intent.putExtra("problemShort", problem.getProDeviceDet());
                intent.putExtra("nameDevice", device.getDeviceName());
                intent.putExtra("modelDevice", device.getDeviceModel());
                intent.putExtra("problemFull", problem.getProDeviceDet());
                intent.putExtra("date", problem.getDataArrived());
                startActivity(intent);

            }
        });




    }


    private void showUpdateDeleteDialog(final String customerId, String customerName) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);
        final EditText editTextFName = (EditText) dialogView.findViewById(R.id.ET_fName);
        final EditText editTextLName = (EditText) dialogView.findViewById(R.id.ET_lName);
        final EditText editTextPhone = (EditText) dialogView.findViewById(R.id.ET_phone);
        final EditText editTextEmail = (EditText) dialogView.findViewById(R.id.ET_email);
        final EditText editTextProDetDev = (EditText) dialogView.findViewById(R.id.ET_proDetDevice);


        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateArtist);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteArtist);

        dialogBuilder.setTitle(customerName);
        final AlertDialog b = dialogBuilder.create();
        b.show();


        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fName = editTextFName.getText().toString().trim();
                String lName = editTextLName.getText().toString().trim();
                String phone = editTextPhone.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();
                String proDetDevice = editTextProDetDev.getText().toString().trim();
                if (!TextUtils.isEmpty(fName)) {
                    updateCustomer(customerId, fName, lName,phone,email, proDetDevice);
                    b.dismiss();
                }
            }
        });


        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteCustomer(customerId);

            }
        });
    }
    private boolean deleteCustomer(String id) {
        //getting the specified Customer reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("customer").child(id);
        DatabaseReference dRP = FirebaseDatabase.getInstance().getReference("device").child(id);
        DatabaseReference dRD = FirebaseDatabase.getInstance().getReference("problem").child(id);

        //removing Customer
        dR.removeValue();
        dRP.removeValue();
        dRD.removeValue();

        Toast.makeText(getApplicationContext(), "Customer Deleted", Toast.LENGTH_LONG).show();

        return true;
    }

    private boolean updateCustomer(String id, String fName, String lName, String phone, String email, String proDetDevice) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("customer").child(id);
        DatabaseReference dRp = FirebaseDatabase.getInstance().getReference("problem").child(id);

        //updating Customer
        Customer customer= new Customer(id, fName, lName, email,phone);
        dR.setValue(customer);
        DateFormat df = new SimpleDateFormat("d MMM yyyy, HH:mm");
        String date = df.format(Calendar.getInstance().getTime());
        String shortProDeviceDet = string3Words(proDetDevice);
        Problem problem= new Problem(id, date,proDetDevice,0, shortProDeviceDet );
        dRp.setValue(problem);
        Toast.makeText(getApplicationContext(), "Customer Updated", Toast.LENGTH_LONG).show();
        return true;
    }
    private boolean updateCustomerData(String id, String fName, String lName, String phone, String email, String proDetDevice, int urgency,String nameDevice, String modelDevice ) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("customer").child(id);
        DatabaseReference dRp = FirebaseDatabase.getInstance().getReference("problem").child(id);
        DatabaseReference dRD = FirebaseDatabase.getInstance().getReference("device").child(id);
        DatabaseReference dRPCD = FirebaseDatabase.getInstance().getReference("problemDeviceCustomer").child(id);

        //updating artist
        Customer customer= new Customer(id, fName, lName, email,phone);
        dR.setValue(customer);
        DateFormat df = new SimpleDateFormat("d MMM yyyy");
        String date = df.format(Calendar.getInstance().getTime());
        String shortProDeviceDet = string3Words(proDetDevice);
        Problem problem= new Problem(id, date,proDetDevice,urgency, shortProDeviceDet );
        dRp.setValue(problem);
        Device device = new Device(id,modelDevice, nameDevice );
        dRD.setValue(device);
        ProblemDeviceCustomer pDC = new ProblemDeviceCustomer(id,fName,lName,phone, email, modelDevice, nameDevice,shortProDeviceDet, proDetDevice, date, urgency  );
        dRPCD.setValue(pDC);

        Toast.makeText(getApplicationContext(), "Customer Updated", Toast.LENGTH_LONG).show();
        return true;
    }
    private void showUpdateDeleteDialogCustomer(final String customerId, String customerName, String fName, String lName, String phone, String email) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(CustomersPage.this);
        final LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog_client_page, null);
        dialogBuilder.setView(dialogView);


        final EditText editTextFName = (EditText) dialogView.findViewById(R.id.ET_fNameCP);
        final EditText editTextLName = (EditText) dialogView.findViewById(R.id.ET_lNameCP);
        final EditText editTextPhone = (EditText) dialogView.findViewById(R.id.ET_phoneCP);
        final EditText editTextEmail = (EditText) dialogView.findViewById(R.id.ET_emailCP);
        final EditText editTextNameDevice = (EditText) dialogView.findViewById(R.id.ET_nameDeviceCP);
        final EditText editTextModelDevice = (EditText) dialogView.findViewById(R.id.ET_modelDeviceCP);
        final EditText editTextProDetDev = (EditText) dialogView.findViewById(R.id.ET_proDetDeviceCP);
        editTextFName.setText(fName);
        editTextLName.setText(lName);
        editTextPhone.setText(phone);
        editTextEmail.setText(email);


        final Spinner spinnerU = (Spinner) dialogView.findViewById(R.id.spinner_intUr);

        final List<String> list1 = new ArrayList<String>();
        list1.add(option1u);
        list1.add(option2u);
        list1.add(option3u);
        final ArrayAdapter<String> adp = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list1);
        adp.setDropDownViewResource(android.R.layout.select_dialog_item);
        spinnerU.setAdapter(adp);


        final int urgency = spinnerUrgency(spinnerU);

        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateArtist);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteArtist);
        dialogBuilder.setTitle(customerName);
        final AlertDialog b = dialogBuilder.create();
        b.show();


        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fName = editTextFName.getText().toString().trim();
                String lName = editTextLName.getText().toString().trim();
                String phone = editTextPhone.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();
                String proDetDevice = editTextProDetDev.getText().toString().trim();
                String nameDevice = editTextNameDevice.getText().toString().trim();
                String modelDevice = editTextModelDevice.getText().toString().trim();
                int urgency = spinnerUrgency(spinnerU);


                if (!TextUtils.isEmpty(fName)) {
                    updateCustomerData(customerId, fName, lName, phone, email, proDetDevice, urgency, nameDevice, modelDevice);
                    b.dismiss();
                }
            }
        });
    }





    private int spinnerUrgency(Spinner spinner){
        String optionSelect = String.valueOf(spinner.getSelectedItem());
        if(optionSelect.equals(option1u))
            return 1;
        if(optionSelect.equals(option2u))
            return 2;
        if(optionSelect.equals(option3u))
            return 3;
        return 0;
    }


    private String string3Words(String s) {
        String[] splitted = s.split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < splitted.length; i++) {
            sb.append(splitted[i] + " ");
            if (i == 2) {
                break;
            }
        }
        String newS = sb.toString();
        return newS;
    }
    @Override
    protected void onStart() {
        super.onStart();

        databaseDevice.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                devices.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting customer
                    Device device = postSnapshot.getValue(Device.class);
                    //adding customer to the list
                    devices.add(device);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        databaseProblem.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                problems.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting customer
                    Problem problem = postSnapshot.getValue(Problem.class);
                    //adding customer to the list
                    problems.add(problem);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //attaching value event listener
        databaseCustomers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous artist list
                customers.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting customer
                    Customer customer = postSnapshot.getValue(Customer.class);
                    //adding customer to the list
                    customers.add(customer);

                }
           //     Intent intent2 = getIntent();
         //      DisplayItem [] displayItem = (DisplayItem[]) intent2.getExtras().getSerializable("DisplayItem");

                //creating adapter
               CustomerAdapter customerAdapter = new CustomerAdapter(CustomersPage.this,customers );
                //attaching adapter to the listview
                listViewCustomers.setAdapter(customerAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
    public static long getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

}
