package io.github.ashik619.comexampleandroidrinkimylibraryproject;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.victoralbertos.breadcumbs_view.BreadcrumbsView;

/**
 * Created by dilip on 24/4/19.
 */

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.MyViewHolder> {

    private List<QuestionsData> moviesList;
    Context context;
    AsyncResult<AnswersData> asyncResult_clickPayTm;
    int steps=3;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView QuestionName;
              RatingBar  userRating;

        public MyViewHolder(View view) {
            super(view);
            QuestionName=(TextView) view.findViewById(R.id.question_name);
            userRating=(RatingBar) view.findViewById(R.id.ratingBar1);

        }
    }


    public FeedbackAdapter(List<QuestionsData> moviesList, Context context, AsyncResult<AnswersData> asyncResult_clickPayTm) {
        this.moviesList = moviesList;
        this.context = context;
        this.asyncResult_clickPayTm=asyncResult_clickPayTm;
    }

    @Override
    public FeedbackAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feedback_list, parent, false);

        return new FeedbackAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FeedbackAdapter.MyViewHolder holder, final int position) {
        final QuestionsData movie = moviesList.get(position);


        holder.QuestionName.setText(movie.getQ1());
        Drawable drawable = holder.userRating.getProgressDrawable();
        drawable.setColorFilter(Color.parseColor("#9b9b9b"), PorterDuff.Mode.SRC_ATOP);
 /*    holder.userRating.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             String rating1=String.valueOf(holder.userRating.getRating());
             AnswersData answersData =new AnswersData(moviesList.get(position).getQ1(),rating1);
             asyncResult_clickPayTm.success(answersData);
         }
     });*/

    /*    holder.userRating.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    String rating1=String.valueOf(holder.userRating.getRating());
                    AnswersData answersData =new AnswersData(moviesList.get(position).getQ1(),rating1);
                    asyncResult_clickPayTm.success(answersData);
                    // TODO perform your action here
                }
                return true;
            }
        });*/
        holder.userRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                String rating1=String.valueOf(ratingBar.getRating());
                AnswersData answersData =new AnswersData(moviesList.get(position).getQ1(),rating1);
                asyncResult_clickPayTm.success(answersData);
                // place intent for new activity

            }
        });

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}