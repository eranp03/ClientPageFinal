package com.example.eranp.clientpage;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import static android.graphics.Color.BLUE;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;
import static android.graphics.Color.WHITE;
import static android.graphics.Color.blue;
import static com.example.eranp.clientpage.R.layout.customer_row;

/**
 * Created by Eran P on 16/04/2018.
 */

public class CustomerProblemAdapter extends ArrayAdapter<ProblemDeviceCustomer> {

    private Activity context;
    List<ProblemDeviceCustomer> objects ;



    public CustomerProblemAdapter(Activity context, ArrayList<ProblemDeviceCustomer> items) {
        super(context, R.layout.customer_problem_row, items);
        this.context = context;
        this.objects = items;


    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        // Create holder
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.customer_problem_row, null, true);
        TextView fName = (TextView) listViewItem.findViewById(R.id.textVFNameCp);
        TextView lName = (TextView) listViewItem.findViewById(R.id.textVLnameCp);
        TextView phone = (TextView)listViewItem.findViewById(R.id.textVTeleleCp);
        TextView dateArrived = (TextView)listViewItem.findViewById(R.id.tVDateCp);
        ImageView urgentcP = (ImageView) listViewItem.findViewById(R.id.urgentcP);

        TextView proDeviceShort = (TextView)listViewItem.findViewById(R.id.pro_device_det_shortCp);
        ProblemDeviceCustomer problemDC = getItem(position);

                fName.setText(problemDC.getfNamePDC());
                lName.setText(problemDC.getlNamePDC());
                phone.setText(problemDC.getPhoneNumPDC());
                dateArrived.setText(problemDC.getDataArrivedPDC());
                proDeviceShort.setText(problemDC.getProDevDetShortPDC());
                if(problemDC.getUrgencyPDC()==1)
                    urgentcP.setBackgroundResource(R.drawable.urgent_3);
             if(problemDC.getUrgencyPDC()==2)
              urgentcP.setBackgroundResource(R.drawable.urgent_2);
             if(problemDC.getUrgencyPDC()==3)
             urgentcP.setBackgroundResource(R.drawable.urgent_1);


        return listViewItem;

    }
}
