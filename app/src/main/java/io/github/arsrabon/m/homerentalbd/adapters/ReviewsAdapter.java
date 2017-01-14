package io.github.arsrabon.m.homerentalbd.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import io.github.arsrabon.m.homerentalbd.R;
import io.github.arsrabon.m.homerentalbd.model.Reviews;

/**
 * Created by msrabon on 1/15/17.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder> {

    private List<Reviews> reviewsList;

    public ReviewsAdapter(List<Reviews> reviewsList) {
        this.reviewsList = reviewsList;
    }

    @Override
    public ReviewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_carditem, parent, false);
        ReviewsViewHolder adsViewHolder = new ReviewsViewHolder(view);
        return adsViewHolder;
    }

    @Override
    public void onBindViewHolder(ReviewsViewHolder holder, int position) {
        holder.username.setText(reviewsList.get(position).getUsername());
        holder.review.setText(reviewsList.get(position).getReview());
        holder.ratingBar.setRating(reviewsList.get(position).getRating());
        Log.d("onBindViewHolder: ",reviewsList.get(position).getReview());
    }

    @Override
    public int getItemCount() {
        return reviewsList.size();
    }

    public static class ReviewsViewHolder extends RecyclerView.ViewHolder{
        TextView username;
        TextView review;
        RatingBar ratingBar;
        public ReviewsViewHolder(View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.txt_username);
            review = (TextView) itemView.findViewById(R.id.txt_rentreview);
            ratingBar = (RatingBar) itemView.findViewById(R.id.rents_ratings);
        }
    }
}
