package io.github.ashik619.comexampleandroidrinkimylibraryproject;

import android.content.Context;
import android.content.Intent;

/**
 * Created by dilip on 3/4/19.
 */

public class LetsServiceHeroIntegration {

    String mobileNumber;
    Context context;

    public LetsServiceHeroIntegration(Context context, String mobileNumber) {
            this.context = context;
            this.mobileNumber = mobileNumber;
            Intent i = new Intent(context.getApplicationContext(), LetsServiceHeroIntegrationActivity.class);
            i.putExtra("id",mobileNumber);
            context.getApplicationContext().startActivity(i);
    }

}
