package io.github.ashik619.mylibraryproject;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;



/**
 * Created by dilip on 3/4/19.
 */

public class DetailFragment extends Fragment  {
   // public static final String EXTRA_TEXT ="text";
     OnItemSelectedListener listener;
    TextView textView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details,
                container, false);
        Button button = (Button) view.findViewById(R.id.updateButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDetail("testing");
            }
        });
        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemSelectedListener) {
            listener = (OnItemSelectedListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement MyListFragment.OnItemSelectedListener");
        }
    }

    // triggers update of the details fragment
    public void updateDetail(String uri) {  //


        //listener.onRssItemSelected(number);
    }
/*
    @Override
    public void onRssItemSelected(String text) {
        textView.setText(text);
    }*/


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
           // String text = bundle.getString(EXTRA_TEXT);
         //   setText(text);
        }
    }

    public void setText1(String text) {
        textView = (TextView) getView().findViewById(R.id.detailsText);
        textView.setText(text.toString());

    }

}

