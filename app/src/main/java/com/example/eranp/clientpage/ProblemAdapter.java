package com.example.eranp.clientpage;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Eran P on 23/01/2018.
 */

public class ProblemAdapter extends ArrayAdapter<Problem> {
    private Activity context;
    List<Problem> problems ;

    public ProblemAdapter(Activity context, List<Problem> problems) {
        super(context, R.layout.problem_row, problems);
        this.context = context;
        this.problems = problems;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.problem_row, null, true);

        TextView descOfTProbShort = (TextView) listViewItem.findViewById(R.id.descOfTProb_TV);
        TextView dateArrived = (TextView) listViewItem.findViewById(R.id.dateArrived_TV);


        Problem problem = problems.get(position);
        dateArrived.setText(problem.getDataArrived());
        descOfTProbShort.setText(problem.getProDevDetShort());


        return listViewItem;

    }
}
