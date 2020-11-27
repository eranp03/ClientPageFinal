package com.example.eranp.clientpage;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import static android.media.CamcorderProfile.get;


public class CustomerAdapter extends ArrayAdapter<Customer> {
    private Activity context;
    List<Customer> customers ;



        public CustomerAdapter(Activity context, List<Customer>customers) {
            super(context, R.layout.customer_row, customers);
            this.context = context;
            this.customers = customers;

        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.customer_row, null, true);

            TextView textViewFName = (TextView) listViewItem.findViewById(R.id.textVFName);
            TextView textViewLName = (TextView) listViewItem.findViewById(R.id.textVLname);
            TextView textViewTele = (TextView) listViewItem.findViewById(R.id.textVTelele);
           Customer customer = customers.get(position);
                textViewFName.setText(customer.getfName());
                textViewLName.setText(customer.getlName());
                textViewTele.setText(customer.getPhoneNum());

            return listViewItem;

        }
}

