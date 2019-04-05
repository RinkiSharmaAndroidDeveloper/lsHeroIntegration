package io.github.ashik619.comexampleandroidrinkimylibraryproject;

import android.content.Context;
import android.content.Intent;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by dilip on 3/4/19.
 */

public class RssFeedProvider {

        String mobileNumber;
        Context context;

    public RssFeedProvider(Context context, String mobileNumber) {
            this.context = context;
            this.mobileNumber = mobileNumber;
        Intent i = new Intent(context.getApplicationContext(), RssTestActivity.class);
        i.putExtra("id",mobileNumber);
        context.getApplicationContext().startActivity(i);

    }

}
