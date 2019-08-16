package io.github.ashik619.mylibraryproject;



import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import io.github.ashik619.comexampleandroidrinkimylibraryproject.LetsServiceHeroIntegration;
import io.github.ashik619.comexampleandroidrinkimylibraryproject.MapActivity;


public class MainActivity extends Activity implements
        OnItemSelectedListener {
TextView textView;
Button button,viewList;
EditText vinNumber;
String vinNUm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
textView =findViewById(R.id.detailsText);

        button =findViewById(R.id.create_appt);
        viewList =findViewById(R.id.View_list);
        vinNumber =findViewById(R.id.vin_number);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
   vinNUm = vinNumber.getText().toString();
                if(TextUtils.isEmpty(vinNUm)){
                    vinNumber.setError("Please enter valid vinNumber");
                }else {
                    LetsServiceHeroIntegration rssFeedProvider =new LetsServiceHeroIntegration();
                    rssFeedProvider.LetsServiceHeroCreateAppointment(MainActivity.this,vinNUm,"2019-08-16","16:28");
                }


            }
        });

        viewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
   vinNUm = vinNumber.getText().toString();
                if(TextUtils.isEmpty(vinNUm)){
                    vinNumber.setError("Please enter valid vinNumber");
                }else {

                    LetsServiceHeroIntegration rssFeedProvider =new LetsServiceHeroIntegration(MainActivity.this,vinNUm);
                }

            }
        });

    }


    @Override
    public void onRssItemSelected(String text) {
        boolean test =true;
        DetailFragment fragment = new DetailFragment();
        fragment.setText1(text);

    }
}
