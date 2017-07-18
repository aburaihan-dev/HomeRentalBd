package io.github.arsrabon.m.homerentalbd.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.List;

import io.github.arsrabon.m.homerentalbd.R;
import io.github.arsrabon.m.homerentalbd.adapters.RentalAdsAdapter;
import io.github.arsrabon.m.homerentalbd.model.Area;
import io.github.arsrabon.m.homerentalbd.model.AreaResponse;
import io.github.arsrabon.m.homerentalbd.model.RentTypeResponse;
import io.github.arsrabon.m.homerentalbd.model.RentTypes;
import io.github.arsrabon.m.homerentalbd.model.RentalAd;
import io.github.arsrabon.m.homerentalbd.model.RentalAdResponse;
import io.github.arsrabon.m.homerentalbd.rest.ApiClient;
import io.github.arsrabon.m.homerentalbd.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DefaultActivity extends AppCompatActivity implements Drawer.OnDrawerItemClickListener {

    private Toolbar myToolbar;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    private AccountHeader headerResult;
    private Drawer result;
    private boolean flag;

    ApiInterface apiService;
    List<RentTypes> rentTypes;
    List<Area> areas;
    List<RentalAd> rentalAds;

    Spinner rentType;
    Spinner areaList;
    RecyclerView rentalsRecyclerView;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setMyToolbar(); // Toolbar Setter
        navDrawerMaker(); // NavDrawer Maker

        areaList = (Spinner) findViewById(R.id.spin_area);
        rentType = (Spinner) findViewById(R.id.spin_type);
        rentalsRecyclerView = (RecyclerView) findViewById(R.id.rentals_recylerView);

        // initialize ApiInterface for use
        apiService = ApiClient.getClient().create(ApiInterface.class);

        progressTrackerAnimate();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Call<RentTypeResponse> rentTypeResponseCall = apiService.getRentTypes();
                rentTypeResponseCall.enqueue(new Callback<RentTypeResponse>() {
                    @Override
                    public void onResponse(Call<RentTypeResponse> call, Response<RentTypeResponse> response) {
                        if (response.isSuccessful()){
                            rentTypes = response.body().getRentTypesList();
                            setRentTypeSpinner();
                            Log.d("rentType", String.valueOf(rentTypes.size()));
                        }else {
                            Log.d("onResponse: ", "crap");
                            Toast.makeText(DefaultActivity.this, "Check Internet Connection.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RentTypeResponse> call, Throwable t) {

                    }
                });
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Call<AreaResponse> areaResponseCall = apiService.getAreas();
                areaResponseCall.enqueue(new Callback<AreaResponse>() {
                    @Override
                    public void onResponse(Call<AreaResponse> call, Response<AreaResponse> response) {
                        if (response.isSuccessful()){
                            areas = response.body().getAreaList();
                            Log.d("area", String.valueOf(areas.size()));
                            setAreaSpinner();
                        }else {
                            Log.d("onResponse: ", "crap");
                            Toast.makeText(DefaultActivity.this, "Check Internet Connection.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<AreaResponse> call, Throwable t) {

                    }
                });
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Call<RentalAdResponse> rentalAdResponseCall = apiService.getRentalAds();
                rentalAdResponseCall.enqueue(new Callback<RentalAdResponse>() {
                    @Override
                    public void onResponse(Call<RentalAdResponse> call, Response<RentalAdResponse> response) {
                        if (response.isSuccessful()) {
                            rentalAds = response.body().getRentalAds();
                            Log.d("rents", String.valueOf(rentalAds.size()));
                            setRentalAdsView();
                            progress.dismiss();
                        } else {
                            Log.d("onResponse: ", "crap");
                            Toast.makeText(DefaultActivity.this, "Check Internet Connection.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RentalAdResponse> call, Throwable t) {

                    }
                });
            }
        }).start();

    }

    public void progressTrackerAnimate(){
        progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
    }

    public void setRentalAdsView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rentalsRecyclerView.setLayoutManager(linearLayoutManager);

        try {
            RentalAdsAdapter rentalAdsAdapter = new RentalAdsAdapter(rentalAds, DefaultActivity.this);
            rentalsRecyclerView.setAdapter(rentalAdsAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setRentTypeSpinner() {
        ArrayAdapter<RentTypes> rtypes = new ArrayAdapter<RentTypes>(getBaseContext(), R.layout.spinner_item, rentTypes);
        rtypes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rentType.setAdapter(rtypes);
        rentType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                                Toast.makeText(DefaultView_Activity.this, "hello", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void setAreaSpinner() {
        ArrayAdapter<Area> areaAdapter = new ArrayAdapter<Area>(getBaseContext(), R.layout.spinner_item, areas);
        areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        areaList.setAdapter(areaAdapter);
        areaList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                                Toast.makeText(DefaultView_Activity.this, "hello", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void setMyToolbar() {
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
//        getSupportActionBar().setTitle("Create Rent Advertisement");
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
                            Toast.makeText(DefaultActivity.this, "hello", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(DefaultActivity.this, "hello", Toast.LENGTH_SHORT).show();
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

        Intent xintent;
        switch ((int) drawerItem.getIdentifier()) {
            case R.id.menu_home:
                xintent = new Intent(DefaultActivity.this, DefaultActivity.class);
                startActivity(xintent);
                finish();
                break;
            case R.id.menu_signin:
                xintent = new Intent(getBaseContext(), SignIn_activity.class);
                startActivity(xintent);
                finish();
                break;
            case R.id.menu_profile:
                xintent = new Intent(getBaseContext(), EditUserProfile.class);
                startActivity(xintent);
                finish();
                break;
            case R.id.menu_logout:
                firebaseAuth.signOut();
                xintent = new Intent(DefaultActivity.this, DefaultActivity.class);
                startActivity(xintent);
                finish();
                break;
            case R.id.menu_add_rental:
                xintent = new Intent(getBaseContext(), CreateRentalAd.class);
                startActivity(xintent);
                finish();
        }
        return false;
    }
}
