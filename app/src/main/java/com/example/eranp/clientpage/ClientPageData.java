package com.example.eranp.clientpage;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

public class ClientPageData extends Activity {

    private static final int RQS_PICKCONTACT = 1;

    public static String stringSpaceTextWhatsapp(String s) {
        String s1 = "";

        int stringLength = s.length();
        for (int charIndex = 0; charIndex < stringLength; charIndex++) {
            char c = s.charAt(charIndex);
            if (c == ' ') {
                //text url whatsapp api
                s1 += "%20";
            } else {
                s1 += c;
            }
        }
        return s1;
    }

    public static String stringSpacePhoneWhatsapp(String s) {
        char c = s.charAt(0);
        if (c == '0') {
            s = s.substring(1);
            s = "+972" + s;
        }
        return s;
    }

    private void sendEmail(String mail, String subject, String message) {
        //Getting content for email
        option = String.valueOf(spinner.getSelectedItem());


        //Creating SendMail object

        SendMail sm = new SendMail(this, mail, subject, message);
        sm.execute();


    }


    private Spinner spinner;
    private String option1, option2, option3, option4, option5, messageF, messageNF, subj, messageFW, messageNFW, phone, option1u, option2u, option3u;
    private TextView tV_nameDevice, tV_fName, tV_lName, tV_email, tV_phoneNum, tV_modelDevice, tV_problemShort;
    private ArrayAdapter<CharSequence> adapter;
    private Button save;
    private String option;
    private Button set;
    private Button delete;
    private Button setP;
    private Button call;
    private Button cCase;

    private DatabaseReference databaseCustomer, databaseDevice, databaseProblem;
    private List<ProblemDeviceCustomer> problemDeviceCustomer;
    private DatabaseReference databaseProblemDeviceCustomer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_page_data);

        databaseCustomer = FirebaseDatabase.getInstance().getReference("customer");
        databaseDevice = FirebaseDatabase.getInstance().getReference("device");
        databaseProblem = FirebaseDatabase.getInstance().getReference("problem");
        databaseProblemDeviceCustomer = FirebaseDatabase.getInstance().getReference("problemDeviceCustomer");
        problemDeviceCustomer = new ArrayList<>();
        option1 = getResources().getString(R.string.option1);
        option2 = getResources().getString(R.string.option2);
        option3 = getResources().getString(R.string.option3);
        option4 = getResources().getString(R.string.option4);
        option5 = getResources().getString(R.string.option5);

        option1u = getResources().getString(R.string.option1u);
        option2u = getResources().getString(R.string.option2u);
        option3u = getResources().getString(R.string.option3u);


        messageF = getResources().getString(R.string.messF);
        messageNF = getResources().getString(R.string.messNF);
        subj = getResources().getString(R.string.subj);
        tV_nameDevice = (TextView) findViewById(R.id.nameDevice_TV);
        tV_modelDevice = (TextView) findViewById(R.id.modelDevice_TV);
        tV_fName = (TextView) findViewById(R.id.fName_TV);
        tV_lName = (TextView) findViewById(R.id.lName_TV);
        tV_phoneNum = (TextView) findViewById(R.id.phone_TV);
        tV_email = (TextView) findViewById(R.id.mail_TV);
        tV_problemShort = (TextView) findViewById(R.id.descOfTProb_TV);
        save = (Button) findViewById(R.id.save_btn);
        delete = (Button) findViewById(R.id.delete_btn);
        set = (Button) findViewById(R.id.set_btn);
        setP = (Button) findViewById(R.id.setP_btn);
        call = (Button) findViewById(R.id.callN_btn);
        delete = (Button) findViewById(R.id.delete_btn);
        cCase = (Button)findViewById(R.id.closeCase_Btn);
        spinner = (Spinner) findViewById(R.id.spinner);
        delete = (Button)findViewById(R.id.delete_btn);


        final List<String> list = new ArrayList<String>();
        list.add(option1);
        list.add(option2);
        list.add(option3);
        list.add(option4);
        list.add(option5);
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);



        Intent intentc = getIntent();
        final String id = intentc.getStringExtra("id");
        final String fName = intentc.getStringExtra("fName");
        final String lName = intentc.getStringExtra("lName");
        final String phone = intentc.getStringExtra("phone");
        final String email = intentc.getStringExtra("email");
        final String modelDevice = intentc.getStringExtra("modelDevice");
        final String nameDevice = intentc.getStringExtra("nameDevice");
        final String problemShort = intentc.getStringExtra("problemShort");
        final String problemFull = intentc.getStringExtra("problemFull");
        final String date = intentc.getStringExtra("date");

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCustomer(id);

            }
        });

        tV_fName.setText(fName);
        tV_lName.setText(lName);
        tV_email.setText(email);
        tV_phoneNum.setText(phone);
        tV_nameDevice.setText(nameDevice);
        tV_modelDevice.setText(modelDevice);
        tV_problemShort.setText(problemShort);

        tV_problemShort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(ClientPageData.this)
                        .setTitle("The full problem")
                        .setMessage(problemFull)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();
                            }
                        }).show();
            }
        });
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showUpdateDeleteDialog(id, fName + " " + lName, fName,lName,phone, email, nameDevice, modelDevice, problemShort );
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ClientPageData.this,
                        "Spinner : " + String.valueOf(spinner.getSelectedItem()),
                        Toast.LENGTH_SHORT).show();
                option = String.valueOf(spinner.getSelectedItem());
                if ((option.equals(option4)) || (option.equals(option5))) {
                    if (option.equals(option4)) {
                        sendEmail(email, subj, messageF);

                        messageF = stringSpaceTextWhatsapp(messageF);
                        String FILE_NAME = fName + " " + lName + "fix.txt";
                        String text = "First name: " + fName + "\n" + "last name: " + lName + "\n" + "Name Device" + nameDevice;
                        FileOutputStream fos = null;
                        try {
                            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
                            fos.write(text.getBytes());
                            Toast.makeText(ClientPageData.this, "saved to: " + getFilesDir() + "/" + FILE_NAME, Toast.LENGTH_LONG).show();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            if (fos != null) {
                                try {
                                    fos.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }


                        AlertDialog.Builder builder = new AlertDialog.Builder(ClientPageData.this);

                        builder.setTitle("Do you want to send whatsapp to the customer");
                        builder.setMessage("Do you?");

                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                // Do nothing but close the dialog
                                String phoneIsr = stringSpacePhoneWhatsapp(phone);
                                Intent intent = new Intent();
                                intent.setAction(Intent.ACTION_VIEW);
                                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                                intent.setData(Uri.parse("https://api.whatsapp.com/send?phone=" + phoneIsr + "&text=" + messageF));
                                startActivity(intent);

                                dialog.dismiss();
                            }
                        });
                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                // Do nothing
                                dialog.dismiss();
                            }
                        });

                        AlertDialog alert = builder.create();
                        alert.show();

                    }

                }


                if (option.equals(option5)) {
                    sendEmail(email, subj, messageNF);

                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    messageNF = stringSpaceTextWhatsapp(messageNF);
                    AlertDialog.Builder builder = new AlertDialog.Builder(ClientPageData.this);

                    builder.setTitle("Do you want to send whatsapp to the customer");
                    builder.setMessage("Do you?");

                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing but close the dialog
                            String phoneIsr1 = stringSpacePhoneWhatsapp(phone);
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.addCategory(Intent.CATEGORY_BROWSABLE);
                            intent.setData(Uri.parse("https://api.whatsapp.com/send?phone=" + phoneIsr1 + "&text=" + messageF));
                            startActivity(intent);

                            dialog.dismiss();
                        }
                    });

                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            // Do nothing
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                    setP.setVisibility(View.GONE);
                }
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                if (ActivityCompat.checkSelfPermission(ClientPageData.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    // ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    // public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    // int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(intent);
            }
        });

        cCase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCloseCaseDialog(id,fName + " " + lName, phone, nameDevice,modelDevice,problemShort, date );

            }
        });



    }

    private void showUpdateDeleteDialog(final String customerId, String customerName, String fName, String lName, String phone, String email,String nameDevice, String modelDevice, String proDetShort) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ClientPageData.this);
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
        editTextNameDevice.setText(nameDevice);
        editTextModelDevice.setText(modelDevice);
        editTextProDetDev.setText(proDetShort);



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
                    updateCustomer(customerId, fName, lName,phone,email, proDetDevice , urgency, nameDevice, modelDevice);
                    b.dismiss();
                }
            }
        });


        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(ClientPageData.this)
                        .setMessage("Are you sure you want to delete?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                deleteCustomer(customerId);
                                b.dismiss();
                                Intent intent = new Intent(ClientPageData.this, CustomersProblemsPage.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();




            }
        });

    }



    private boolean updateCustomer(String id, String fName, String lName, String phone, String email, String proDetDevice, int urgency,String nameDevice, String modelDevice ) {
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
    private boolean deleteCustomer(String id) {
        //getting the specified Customer reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("customer").child(id);
        DatabaseReference dRP = FirebaseDatabase.getInstance().getReference("device").child(id);
        DatabaseReference dRD = FirebaseDatabase.getInstance().getReference("problem").child(id);
        DatabaseReference dRPDC = FirebaseDatabase.getInstance().getReference("problemDeviceCustomer").child(id);

        //removing Customer
        dR.removeValue();
        dRP.removeValue();
        dRD.removeValue();
        dRPDC.removeValue();

        Toast.makeText(getApplicationContext(), "Customer Deleted", Toast.LENGTH_LONG).show();

        return true;
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (resultCode == RESULT_OK) {
            if (requestCode == RQS_PICKCONTACT) {


            }
        }
    }



    public static String stringphoneISR(String s) {
        String s1 = "";
        String s2 ="";

        int stringLength = s.length();
        for (int charIndex = 0; charIndex < stringLength; charIndex++) {
            char c = s.charAt(charIndex);
            if (c == '-') {

                s1 += "";
            } else {

                s1 += c;
            }
            if(c=='+')
            {

                s2 = "0"+ s.substring(4);
                return  s2;
            }

        }
        return s1;
    }



    private void showCloseCaseDialog(final String customerId, String customerName, String phone, String nameDevice, String modelDevice, String proDetShort, final String date) {

        AlertDialog.Builder dialogBuilder1 = new AlertDialog.Builder(ClientPageData.this);
        final LayoutInflater inflater = getLayoutInflater();
        final View dialogViewC = inflater.inflate(R.layout.show_close_case_dialog, null);
        dialogBuilder1.setView(dialogViewC);

        final DatabaseReference databaseCase;
        databaseCase = FirebaseDatabase.getInstance().getReference("case");

        final TextView textViewFuName = (TextView) dialogViewC.findViewById(R.id.TV_fullNameC);
        final TextView textViewDate = (TextView) dialogViewC.findViewById(R.id.TV_date);
        final TextView textViewPhone = (TextView) dialogViewC.findViewById(R.id.TV_phoneC);
        final TextView textViewNameDevice = (TextView) dialogViewC.findViewById(R.id.TV_nameDeviceC);
        final TextView textViewModelDevice = (TextView) dialogViewC.findViewById(R.id.TV_modelDeviceC);
        final TextView textViewProDetDev = (TextView) dialogViewC.findViewById(R.id.TV_problemC);


        final String worker;
        textViewFuName .setText(customerName);
        textViewPhone.setText(phone);
        textViewNameDevice.setText(nameDevice);
        textViewModelDevice.setText(modelDevice);
        textViewProDetDev.setText(proDetShort);
        final Spinner spinnerWorker = (Spinner) dialogViewC.findViewById(R.id.spinner_worker);
        final List<String> list1 = new ArrayList<String>();
        list1.add(getResources().getString(R.string.Eran));
        list1.add(getResources().getString(R.string.Omer));
        list1.add(getResources().getString(R.string.Shlomi));
        final ArrayAdapter<String> adp = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list1);
        adp.setDropDownViewResource(android.R.layout.select_dialog_item);
        spinnerWorker.setAdapter(adp);



        final EditText  editTextPrice = (EditText) dialogViewC.findViewById(R.id.ET_priceC);
        final EditText  editTextCashNum = (EditText) dialogViewC.findViewById(R.id.ET_cashNum);
        final CheckBox checkBoxFix = (CheckBox) dialogViewC.findViewById(R.id.CB_fixC);
        editTextPrice.addTextChangedListener(new NumberTextWatcher(editTextPrice, "#,###"));
        final Button buttonSave = (Button) dialogViewC.findViewById(R.id.BTN_saveC);
        DateFormat df = new SimpleDateFormat("d MMM yyyy");
        final  String dateCase = df.format(Calendar.getInstance().getTime());
       worker = String.valueOf(spinnerWorker.getSelectedItem());
        dialogBuilder1.setTitle(customerName);
        final AlertDialog b = dialogBuilder1.create();
        b.show();


        buttonSave.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               final String price;
               final boolean bFix;
               if(checkBoxFix.isChecked()==true)
                   bFix = true;
               else {
                   bFix = false;
                   Log.d("boolean","bFix=false");
               }

               price = editTextPrice.getText().toString();
               if ((!TextUtils.isEmpty(price))||(!TextUtils.isEmpty(price)))
               {
                   Case caseC = new Case(textViewFuName.getText().toString(), textViewPhone.getText().toString(), textViewProDetDev.getText().toString(), textViewNameDevice.getText().toString(), textViewModelDevice.getText().toString(), price, worker, bFix, dateCase, editTextCashNum.getText().toString());
                   databaseCase.child(customerId).setValue(caseC);
                   Toast.makeText(ClientPageData.this , "Case added, and Customer data deleted", Toast.LENGTH_LONG).show();
               }
               else
                   Toast.makeText(ClientPageData.this , "Enter Price please", Toast.LENGTH_LONG).show();
               deleteCustomerData(customerId);
                b.dismiss();
           }
       });

    }
    private boolean deleteCustomerData(String id) {
        //getting the specified Customer reference

        DatabaseReference dRP = FirebaseDatabase.getInstance().getReference("device").child(id);
        DatabaseReference dRD = FirebaseDatabase.getInstance().getReference("problem").child(id);
        DatabaseReference dRPDC = FirebaseDatabase.getInstance().getReference("problemDeviceCustomer").child(id);

        //removing Customer
        dRP.removeValue();
        dRD.removeValue();
        dRPDC.removeValue();

        Toast.makeText(getApplicationContext(), "Customer Data Deleted", Toast.LENGTH_LONG).show();

        return true;
    }




}



