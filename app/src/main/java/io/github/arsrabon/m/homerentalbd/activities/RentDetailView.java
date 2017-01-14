package io.github.arsrabon.m.homerentalbd.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.List;

import io.github.arsrabon.m.homerentalbd.R;
import io.github.arsrabon.m.homerentalbd.model.Rent;
import io.github.arsrabon.m.homerentalbd.model.RentResponse;
import io.github.arsrabon.m.homerentalbd.rest.ApiClient;
import io.github.arsrabon.m.homerentalbd.rest.ApiInterface;
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

    boolean isImageFitToScreen;
    ImageButton btn_addtoWishList;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private ApiInterface apiService;

    Intent intent;

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
        int position = intent.getIntExtra("rent_id", 0);
        final String treviews = intent.getStringExtra("rent_rev");
        final double rating = intent.getDoubleExtra("rent_ratings",0);
        Call<RentResponse> rentCall = apiService.getSingleRentalAd(position);

        Log.d("rent_id", String.valueOf(position));
        Log.d("rent_id", String.valueOf(treviews));
        Log.d("rent_id", String.valueOf(rating));

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

        img_one = (ImageView) findViewById(R.id.img_one);
        img_two = (ImageView) findViewById(R.id.img_two);
        img_three = (ImageView) findViewById(R.id.img_three);

        btn_addtoWishList = (ImageButton) findViewById(R.id.btn_addtowishlist);

        rentCall.enqueue(new Callback<RentResponse>() {
            @Override
            public void onResponse(Call<RentResponse> call, Response<RentResponse> response) {
                List<Rent> rents = response.body().getRents();
                Rent rent = rents.get(0);
                banner.setText(rent.getBanner());
                beds.setText("Bed Room's: " + String.valueOf(rent.getBeds()));
                baths.setText("Bed Room's: " + String.valueOf(rent.getBaths()));
                size.setText("Size: " + String.valueOf(rent.getSize()) + "SQFT");
                floordetails.setText("Details: " + rent.getFloordetails());
                lift_parking.setText("lift: " + rent.isLift() + " Parking: " + rent.isParking());
                address.setText("Address: " + rent.getAddress());
                rentprice.setText("Rent: " + String.valueOf(rent.getRentprice())+"BDT/Month");
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

        btn_addtoWishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(RentDetailView.this, "added", Toast.LENGTH_SHORT).show();
            }
        });
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
        return false;
    }

}
