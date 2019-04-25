package io.github.ashik619.mylibraryproject;



import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import io.github.ashik619.comexampleandroidrinkimylibraryproject.LetsServiceHeroIntegration;


public class MainActivity extends AppCompatActivity implements
        OnItemSelectedListener {
TextView textView;
Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
textView =findViewById(R.id.detailsText);
button =findViewById(R.id.updateButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             LetsServiceHeroIntegration rssFeedProvider =new LetsServiceHeroIntegration(MainActivity.this,"MERTYUW123456789");
            }
        });

    }

    public void updateDetail(String uri) {  //


    }

    @Override
    public void onRssItemSelected(String text) {
        boolean test =true;
        DetailFragment fragment = new DetailFragment();
        fragment.setText1(text);

    }
}
