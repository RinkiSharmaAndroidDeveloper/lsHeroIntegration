package io.github.ashik619.comexampleandroidrinkimylibraryproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RssTestActivity extends AppCompatActivity {
    TextView textView;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss_test);

        textView =findViewById(R.id.detailsText);
        button =findViewById(R.id.updateButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDetail("testing");
            }
        });


    }

    public void updateDetail(String uri) {  //
        String number = RssFeedProvider
                .generateRandom(4);
        textView.setText(number);

    }
}
