package io.github.ashik619.comexampleandroidrinkimylibraryproject;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

/**
 * Created by dilip on 21/6/19.
 */

public class FeedbackAnswerAdapter  extends RecyclerView.Adapter<FeedbackAnswerAdapter.MyViewHolder> {

    private List<AnswersData> moviesList;
    Context context;

    int steps=3;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView QuestionName;
        RatingBar userRating;

        public MyViewHolder(View view) {
            super(view);
            QuestionName=(TextView) view.findViewById(R.id.question_name);
            userRating=(RatingBar) view.findViewById(R.id.ratingBar1);

        }
    }


    public FeedbackAnswerAdapter(List<AnswersData> moviesList, Context context) {
        this.moviesList = moviesList;
        this.context = context;

    }

    @Override
    public FeedbackAnswerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feedback_list, parent, false);

        return new FeedbackAnswerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FeedbackAnswerAdapter.MyViewHolder holder, final int position) {
        final AnswersData movie = moviesList.get(position);


        holder.QuestionName.setText(movie.getQuestion());
        Drawable drawable = holder.userRating.getProgressDrawable();
        drawable.setColorFilter(Color.parseColor("#9b9b9b"), PorterDuff.Mode.SRC_ATOP);
        holder.userRating.setRating(Float.parseFloat(movie.rating));
        holder.userRating.setIsIndicator(true);
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

}
