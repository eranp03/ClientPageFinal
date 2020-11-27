package com.example.eranp.clientpage;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.support.v4.app.ActionBarDrawerToggle;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity{
    private static final int RQS_PICKCONTACT = 1;

    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }


    private EditText fName_ET, lName_ET, phoneNum_ET, eMail_ET, deviceModel_ET, nameDevice_ET;
    private Button nextPage;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private ImageView phoneImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);



        fName_ET = (EditText) findViewById(R.id.fName_ET);
        lName_ET = (EditText) findViewById(R.id.lName_ET);
        phoneNum_ET = (EditText) findViewById(R.id.phone_ET);
        eMail_ET = (EditText) findViewById(R.id.mail_ET);
        nameDevice_ET = (EditText) findViewById(R.id.name_device);
        deviceModel_ET = (EditText) findViewById(R.id.model_device);
        nextPage = (Button) findViewById(R.id.next_btn);
        phoneImg = (ImageView) findViewById(R.id.phone_img);




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
                        Intent intent1 = new Intent(MainActivity.this, CustomersPage.class);
                        startActivity(intent1);

                        break;

                    case R.id.nav_allCust:
                        Intent intent2 = new Intent(MainActivity.this, CustomersProblemsPage.class);
                        startActivity(intent2);

                        break;
                    case R.id.nav_AddCustomer:
                        Intent intent3 = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent3);
                        break;

                }


                return false;
            }
        });



        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                String fName = fName_ET.getText().toString().trim();
                String lName = lName_ET.getText().toString().trim();
                String email = eMail_ET.getText().toString().trim();
                String phone = phoneNum_ET.getText().toString().trim();
                String nameDevice = nameDevice_ET.getText().toString().trim();
                String modelDevice = deviceModel_ET.getText().toString().trim();

                intent.putExtra("modelDevice", modelDevice);
                intent.putExtra("fName", fName);
                intent.putExtra("lName", lName);
                intent.putExtra("phone", phone);
                intent.putExtra("email", email);
                intent.putExtra("nameDevice", nameDevice);
                startActivity(intent);
            }
        });

        phoneImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start activity to get contact
                final Uri uriContact = ContactsContract.Contacts.CONTENT_URI;
                Intent intentPickContact = new Intent(Intent.ACTION_PICK, uriContact);
                startActivityForResult(intentPickContact, RQS_PICKCONTACT);
            }
        });

    }


    public static String stringphoneISR(String s) {
        String s1 = "";
        String s2 = "";

        int stringLength = s.length();
        for (int charIndex = 0; charIndex < stringLength; charIndex++) {
            char c = s.charAt(charIndex);
            if (c == '-') {

                s1 += "";
            } else {

                s1 += c;
            }
            if (c == '+') {

                s2 = "0" + s.substring(4);
                return s2;
            }

        }
        return s1;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (resultCode == RESULT_OK) {
            if (requestCode == RQS_PICKCONTACT) {

                Uri returnUri = data.getData();
                Cursor cursor = getContentResolver().query(returnUri, null, null, null, null);

                if (cursor.moveToNext()) {
                    int columnIndex_ID = cursor.getColumnIndex(ContactsContract.Contacts._ID);
                    String contactID = cursor.getString(columnIndex_ID);

                    int columnIndex_HASPHONENUMBER = cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
                    String stringHasPhoneNumber = cursor.getString(columnIndex_HASPHONENUMBER);

                    if (stringHasPhoneNumber.equalsIgnoreCase("1")) {
                        Cursor cursorNum = getContentResolver().query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactID,
                                null,
                                null);


                        //Get the first phone number
                        if (cursorNum.moveToNext()) {
                            int columnIndex_number = cursorNum.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

                            String stringNumber = cursorNum.getString(columnIndex_number);
                            String phoneIsr = stringphoneISR(stringNumber);
                            phoneNum_ET.setText(phoneIsr);

                        }

                    } else {
                        phoneNum_ET.setText("NO Phone Number");
                    }


                } else {
                    Toast.makeText(getApplicationContext(), "NO data!", Toast.LENGTH_LONG).show();
                }
            }
        }
    }


}





