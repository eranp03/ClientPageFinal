package com.example.eranp.clientpage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Main2Activity extends AppCompatActivity {



    private EditText pro_device_det;
    private Button saveDataBase, btnViewAll ;
    private DatabaseReference databaseCustomer, databaseDevice, databaseProblem, databaseProblemDeviceCustomer;
    private Spinner spinner ,spinner1;
    private String option1, option2,option3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main3);

        spinner = (Spinner)findViewById(R.id.spinner_intU);
        spinner1 = (Spinner)findViewById(R.id.spinner);

        databaseCustomer = FirebaseDatabase.getInstance().getReference("customer");
        databaseDevice = FirebaseDatabase.getInstance().getReference("device");
        databaseProblem = FirebaseDatabase.getInstance().getReference("problem");
        databaseProblemDeviceCustomer = FirebaseDatabase.getInstance().getReference("problemDeviceCustomer");
        option1 = getResources().getString(R.string.option1u);
        option2 = getResources().getString(R.string.option2u);
        option3 = getResources().getString(R.string.option3u);
        saveDataBase = (Button) findViewById(R.id.save_btn);
        final List<String> list = new ArrayList<String>();
        list.add(option1);
        list.add(option2);
        list.add(option3);
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        pro_device_det = (EditText) findViewById(R.id.pro_device_det);
        final Intent intent = getIntent();
        final String fName = intent.getStringExtra("fName");
        final String lName = intent.getStringExtra("lName");
        final String phone = intent.getStringExtra("phone");
        final String email = intent.getStringExtra("email");
        final String modelDevice = intent.getStringExtra("modelDevice");
        final String nameDevice = intent.getStringExtra("nameDevice");


        saveDataBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                String proDeviceDet = pro_device_det.getText().toString().trim();
                String shortProDeviceDet = string3Words(proDeviceDet);
                DateFormat df = new SimpleDateFormat("d MMM yyyy");
                String date = df.format(Calendar.getInstance().getTime());
                String id = databaseCustomer.push().getKey();


                if ((!TextUtils.isEmpty(proDeviceDet))) {
                    Customer customer = new Customer(id, fName, lName, email, phone);
                    databaseCustomer.child(id).setValue(customer);
                    Device device = new Device(id, nameDevice, modelDevice);
                    databaseDevice.child(id).setValue(device);
                    int urgency = spinnerUrgency(spinner);
                    Problem problem = new Problem(id, date, proDeviceDet, urgency, shortProDeviceDet);
                    databaseProblem.child(id).setValue(problem);
                    ProblemDeviceCustomer pDC = new ProblemDeviceCustomer(id, fName,lName, phone,email, modelDevice, nameDevice,shortProDeviceDet, proDeviceDet, date, urgency);
                    databaseProblemDeviceCustomer.child(id).setValue(pDC);



                      Toast.makeText(Main2Activity.this , "Customer added", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(Main2Activity.this, "Please write on an empty cell", Toast.LENGTH_LONG).show();
                }

            }
        });








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
    private int spinnerUrgency(Spinner spinner){
       String optionSelect = String.valueOf(spinner.getSelectedItem());
        if(optionSelect.equals(option1))
            return 1;
        if(optionSelect.equals(option2))
            return 2;
        if(optionSelect.equals(option3))
            return 3;
        return 0;
    }

}
