package io.github.arsrabon.m.homerentalbd.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import io.github.arsrabon.m.homerentalbd.R;
import io.github.arsrabon.m.homerentalbd.activities.RentDetailView;
import io.github.arsrabon.m.homerentalbd.model.RentalAd;


/**
 * Created by msrabon on 12/14/16.
 */

public class RentalAdsAdapter extends RecyclerView.Adapter<RentalAdsAdapter.RentalAdsViewHolder> {

    private List<RentalAd> rentalAdsList;
    private Context context;

    public RentalAdsAdapter(List<RentalAd> rentalAdsList, Context context) {
        this.rentalAdsList = rentalAdsList;
        this.context = context;
    }

    @Override
    public RentalAdsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rentalads_carditem, parent, false);
        RentalAdsViewHolder adsViewHolder = new RentalAdsViewHolder(view);
        return adsViewHolder;
    }

    @Override
    public void onBindViewHolder(RentalAdsViewHolder holder, final int position) {
        holder.bannerImg.setImageResource(R.drawable.ft_1);
        holder.banner.setText(rentalAdsList.get(position).getBanner());
        holder.area.setText(rentalAdsList.get(position).getArea());
        holder.type.setText(rentalAdsList.get(position).getType());
        holder.beds.setText("Beds: " + String.valueOf(rentalAdsList.get(position).getBeds()));
        holder.size.setText("Size: " + String.valueOf(rentalAdsList.get(position).getSize()));
        holder.rentprice.setText("Cost/Mo: " + String.valueOf(rentalAdsList.get(position).getRentprice()) + "BDT");
        holder.reviews.setText("Reviews: " + String.valueOf(rentalAdsList.get(position).getReviews()));
        holder.available.setText("Available From: " + rentalAdsList.get(position).getAvailable());
        holder.ratingBar.setRating((float) rentalAdsList.get(position).getAvgrating());
        holder.rentsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RentDetailView.class);
                intent.putExtra("rent_id",rentalAdsList.get(position).getId());
                intent.putExtra("rent_rev",String.valueOf(rentalAdsList.get(position).getReviews()));
                intent.putExtra("rent_ratings",rentalAdsList.get(position).getAvgrating());
                context.startActivity(intent);
                ((Activity) context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return rentalAdsList.size();
    }

    public static class RentalAdsViewHolder extends RecyclerView.ViewHolder {
        CardView rentsView;
        ImageView bannerImg;
        TextView banner;
        TextView area;
        TextView type;
        TextView beds;
        TextView size;
        TextView reviews;
        TextView rentprice;
        TextView available;
        RatingBar ratingBar;

        public RentalAdsViewHolder(View itemView) {
            super(itemView);
            rentsView = (CardView) itemView.findViewById(R.id.rentals_cardview);
            bannerImg = (ImageView) itemView.findViewById(R.id.imgview_big);
            banner = (TextView) itemView.findViewById(R.id.txt_banner);
            area = (TextView) itemView.findViewById(R.id.txt_area);
            type = (TextView) itemView.findViewById(R.id.txt_type);
            beds = (TextView) itemView.findViewById(R.id.txt_bedrooms);
            size = (TextView) itemView.findViewById(R.id.txt_aptsize);
            rentprice = (TextView) itemView.findViewById(R.id.txt_price);
            reviews = (TextView) itemView.findViewById(R.id.txt_views);
            available = (TextView) itemView.findViewById(R.id.txt_avail);
            ratingBar = (RatingBar) itemView.findViewById(R.id.rents_ratings);
        }
    }
}
