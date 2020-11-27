package com.example.eranp.clientpage;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class CaseAdapter extends ArrayAdapter<Case> {
    private Activity context;
    List<Case> cases ;



        public CaseAdapter(Activity context, List<Case>cases) {
            super(context, R.layout.customer_row, cases);
            this.context = context;
            this.cases = cases;

        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.customer_row, null, true);

            TextView textViewFullName = (TextView) listViewItem.findViewById(R.id.textVFName);
            TextView textViewLName = (TextView) listViewItem.findViewById(R.id.textVLname);
            TextView textViewTele = (TextView) listViewItem.findViewById(R.id.textVTelele);
           Case aCase = cases.get(position);
                textViewFullName.setText(aCase.getFullName());


            return listViewItem;

        }
}

