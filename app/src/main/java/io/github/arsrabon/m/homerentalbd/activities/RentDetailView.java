package io.github.arsrabon.m.homerentalbd.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.io.IOException;
import java.util.List;

import io.github.arsrabon.m.homerentalbd.R;
import io.github.arsrabon.m.homerentalbd.adapters.RentalAdsAdapter;
import io.github.arsrabon.m.homerentalbd.adapters.ReviewsAdapter;
import io.github.arsrabon.m.homerentalbd.model.PostResponse;
import io.github.arsrabon.m.homerentalbd.model.Rent;
import io.github.arsrabon.m.homerentalbd.model.RentResponse;
import io.github.arsrabon.m.homerentalbd.model.Reviews;
import io.github.arsrabon.m.homerentalbd.model.ReviewsResponse;
import io.github.arsrabon.m.homerentalbd.rest.ApiClient;
import io.github.arsrabon.m.homerentalbd.rest.ApiInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RentDetailView extends AppCompatActivity implements Drawer.OnDrawerItemClickListener {

    private Drawer result;
    private AccountHeader headerResult;
    private Toolbar myToolbar;

    TextView banner;
    TextView beds;
    TextView baths;
    TextView size;
    TextView floordetails;
    TextView lift_parking;
    TextView rentprice;
    TextView rentdetails;
    TextView address;
    TextView reviews;
    TextView available;
    TextView posted_at;

    RatingBar ratings;
    ImageView img_one;
    ImageView img_two;
    ImageView img_three;

    RecyclerView recyclerView_reviews;
    Button btn_showReviews;
    Button btn_postReview;

    boolean isImageFitToScreen;
    ImageButton btn_addtoWishList;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private ApiInterface apiService;

    Intent intent;
    private boolean showFlag = true;

    Rent rent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_detailview);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReferenceFromUrl("gs://homerental-d2009.appspot.com");

        intent = getIntent();
        // initialize ApiInterface for use
        apiService = ApiClient.getClient().create(ApiInterface.class);
        final int position = intent.getIntExtra("rent_id", 0);
        final String treviews = intent.getStringExtra("rent_rev");
        final double rating = intent.getDoubleExtra("rent_ratings", 0);
        Call<RentResponse> rentCall = apiService.getSingleRentalAd(position);

//        Log.d("rent_id", String.valueOf(position));
//        Log.d("rent_id", String.valueOf(treviews));
//        Log.d("rent_id", String.valueOf(rating));

        setMyToolbar(); // Toolbar Setter
        navDrawerMaker(); // NavDrawer Maker

        banner = (TextView) findViewById(R.id.lbl_banner);
        beds = (TextView) findViewById(R.id.txt_totalBedRoom);
        baths = (TextView) findViewById(R.id.txt_baths);
        size = (TextView) findViewById(R.id.txt_size);
        floordetails = (TextView) findViewById(R.id.txt_floordetails);
        lift_parking = (TextView) findViewById(R.id.txt_parking);
        rentprice = (TextView) findViewById(R.id.txt_rentprice);
        rentdetails = (TextView) findViewById(R.id.txt_rentdetails);
        address = (TextView) findViewById(R.id.txt_address);
        reviews = (TextView) findViewById(R.id.txt_ratings);
        available = (TextView) findViewById(R.id.txt_available);
        posted_at = (TextView) findViewById(R.id.txt_posted_at);
        ratings = (RatingBar) findViewById(R.id.rents_ratings);

        recyclerView_reviews = (RecyclerView) findViewById(R.id.viewReviews);
        btn_showReviews = (Button) findViewById(R.id.btn_showreviews);
        btn_postReview = (Button) findViewById(R.id.btn_postreviews);

        img_one = (ImageView) findViewById(R.id.img_one);
        img_two = (ImageView) findViewById(R.id.img_two);
        img_three = (ImageView) findViewById(R.id.img_three);

        btn_addtoWishList = (ImageButton) findViewById(R.id.btn_addtowishlist);

        rentCall.enqueue(new Callback<RentResponse>() {
            @Override
            public void onResponse(Call<RentResponse> call, Response<RentResponse> response) {
                List<Rent> rents = response.body().getRents();

                rent = rents.get(0);
                banner.setText(rent.getBanner());
                beds.setText("Bed Room's: " + String.valueOf(rent.getBeds()));
                baths.setText("Bed Room's: " + String.valueOf(rent.getBaths()));
                size.setText("Size: " + String.valueOf(rent.getSize()) + "SQFT");
                floordetails.setText("Details: " + rent.getFloordetails());
                lift_parking.setText("lift: " + rent.isLift() + " Parking: " + rent.isParking());
                address.setText("Address: " + rent.getAddress());
                rentprice.setText("Rent: " + String.valueOf(rent.getRentprice()) + "BDT/Month");
                rentdetails.setText("Others: " + rent.getRentdetails());
                available.setText("Available From: " + rent.getAvailable());
                posted_at.setText("Ad Posted @" + rent.getCreated_at());
                reviews.setText("Total Reviews: " + treviews);
                ratings.setRating((float) rating);
            }

            @Override
            public void onFailure(Call<RentResponse> call, Throwable t) {

            }
        });

        img_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadPhoto(img_one);
            }
        });

        img_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadPhoto(img_two);
            }
        });

        img_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadPhoto(img_three);
            }
        });

        btn_showReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showReviews(position);
            }
        });

        btn_postReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postReview();
            }
        });

        btn_addtoWishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(RentDetailView.this, "added", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showReviews(int position) {
        if (showFlag) {
            recyclerView_reviews.setVisibility(View.VISIBLE);
            Call<ReviewsResponse> reviewsResponseCall = apiService.getReviews(position);
            reviewsResponseCall.enqueue(new Callback<ReviewsResponse>() {
                @Override
                public void onResponse(Call<ReviewsResponse> call, Response<ReviewsResponse> response) {
                    if (response.isSuccessful()) {
                        List<Reviews> reviewsList = response.body().getReviewsList();
                        Log.d("revsize", String.valueOf(reviewsList.size()));
                        setReviewsView(reviewsList);
                    } else {
                        Log.d("sjd", "onResponse: ");
                    }
                }

                @Override
                public void onFailure(Call<ReviewsResponse> call, Throwable t) {

                }
            });
            showFlag = false;
            btn_showReviews.setText("Hide Reviews");
        } else {
            recyclerView_reviews.setVisibility(View.GONE);
            btn_showReviews.setText("Show Reviews");
            showFlag = true;
        }
    }

    public void setReviewsView(List<Reviews> reviewsList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView_reviews.setLayoutManager(linearLayoutManager);

        try {
            ReviewsAdapter reviewsAdapter = new ReviewsAdapter(reviewsList);
            recyclerView_reviews.setAdapter(reviewsAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadPhoto(ImageView imageView) {

        ImageView tempImageView = imageView;


        AlertDialog.Builder imageDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);

        View layout = inflater.inflate(R.layout.custom_fullimagedialog,
                (ViewGroup) findViewById(R.id.layout_root));
        ImageView image = (ImageView) layout.findViewById(R.id.fullimage);
        image.setImageDrawable(tempImageView.getDrawable());
        imageDialog.setView(layout);
        imageDialog.setPositiveButton(getString(R.string.ok_button), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }

        });
        imageDialog.create();
        imageDialog.show();
    }

    private void postReview() {
        AlertDialog.Builder postReviewDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);

        View layout = inflater.inflate(R.layout.postreview_dialog,
                (ViewGroup) findViewById(R.id.post_reviewHolder));
        final EditText edt_review = (EditText) layout.findViewById(R.id.edt_review);
        final RatingBar postrent_rating = (RatingBar) layout.findViewById(R.id.postrent_rating);
        postrent_rating.setIsIndicator(false);
        postReviewDialog.setView(layout);
        postReviewDialog.setPositiveButton(getString(R.string.post_button), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                String rev = edt_review.getText().toString();
                int rating = (int) postrent_rating.getRating();
                if (rating > 0 && rev.length() > 10) {
                    Call<PostResponse> postResponseCall = apiService.postReviews(rent.getId(), rent.getUser_id(), rating, rev);
                    postResponseCall.enqueue(new Callback<PostResponse>() {
                        @Override
                        public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                            PostResponse postResponse = response.body();
                            Log.d("onResponse: ",postResponse.getMessage());
                            showFlag=true;
                            showReviews(rent.getId());
                        }

                        @Override
                        public void onFailure(Call<PostResponse> call, Throwable t) {

                        }
                    });
                } else {
                    Toast.makeText(RentDetailView.this, "Please give rating And Write a review for this Ad", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }

        });
        postReviewDialog.create();
        postReviewDialog.show();
    }

    public void setMyToolbar() {
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    public void navDrawerMaker() {

        if (firebaseUser != null) {
            headerResult = new AccountHeaderBuilder()
                    .withActivity(this)
                    .withHeaderBackground(R.drawable.header)
                    .addProfiles(new ProfileDrawerItem().withName(firebaseUser.getDisplayName())
                            .withEmail(firebaseUser.getEmail())
                            .withIcon(getResources().getDrawable(R.drawable.profile2)))
                    .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                        @Override
                        public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                            Toast.makeText(RentDetailView.this, "hello", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    })
                    .build();
            result = new DrawerBuilder()
                    .withActivity(this)
                    .withToolbar(myToolbar)
                    .withAccountHeader(headerResult)
                    .inflateMenu(R.menu.drawer_menu_secondary)
                    .build();
        } else {
            headerResult = new AccountHeaderBuilder()
                    .withActivity(this)
                    .withHeaderBackground(R.drawable.header)
                    .addProfiles(new ProfileDrawerItem().withName("Sign In / Sign Up")
                            .withEmail("AndroidStudio@gmail.com")
                            .withIcon(getResources().getDrawable(R.drawable.profile2)))
                    .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                        @Override
                        public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                            Toast.makeText(RentDetailView.this, "hello", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    })
                    .build();
            result = new DrawerBuilder()
                    .withActivity(this)
                    .withToolbar(myToolbar)
                    .withAccountHeader(headerResult)
                    .inflateMenu(R.menu.drawer_menu)
                    .build();
        }
        result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
        result.setOnDrawerItemClickListener(this);
    }

    @Override
    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

        Intent xIntent;
        switch ((int) drawerItem.getIdentifier()){
            case R.id.menu_home : xIntent = new Intent(getBaseContext(),DefaultActivity.class);
                startActivity(xIntent);
                finish();
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getBaseContext(),DefaultActivity.class);
        startActivity(intent);
        finish();
    }
}
