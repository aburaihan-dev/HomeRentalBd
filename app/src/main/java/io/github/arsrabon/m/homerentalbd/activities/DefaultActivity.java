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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import retrofit2.*;


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

    TextView showall;
    Spinner rentType;
    Spinner areaList;
    RecyclerView rentalsRecyclerView;

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

        Call<RentTypeResponse> rentTypeResponseCall = apiService.getRentTypes();
        rentTypeResponseCall.enqueue(new Callback<RentTypeResponse>() {
            @Override
            public void onResponse(Call<RentTypeResponse> call, Response<RentTypeResponse> response) {
                rentTypes = response.body().getRentTypesList();
                setRentTypeSpinner();
                Log.d("rentType", String.valueOf(rentTypes.size()));
            }

            @Override
            public void onFailure(Call<RentTypeResponse> call, Throwable t) {

            }
        });

        Call<AreaResponse> areaResponseCall = apiService.getAreas();
        areaResponseCall.enqueue(new Callback<AreaResponse>() {
            @Override
            public void onResponse(Call<AreaResponse> call, Response<AreaResponse> response) {
                areas = response.body().getAreaList();
                Log.d("area", String.valueOf(areas.size()));
                setAreaSpinner();
            }

            @Override
            public void onFailure(Call<AreaResponse> call, Throwable t) {

            }
        });

        Call<RentalAdResponse> rentalAdResponseCall = apiService.getRentalAds();
        rentalAdResponseCall.enqueue(new Callback<RentalAdResponse>() {
            @Override
            public void onResponse(Call<RentalAdResponse> call, Response<RentalAdResponse> response) {
                if(response.isSuccessful()){
                    rentalAds = response.body().getRentalAds();
                    Log.d("rents", String.valueOf(rentalAds.size()));
                    setRentalAdsView();
                }else {
                    Log.d("onResponse: ","crap");
                }
            }

            @Override
            public void onFailure(Call<RentalAdResponse> call, Throwable t) {

            }
        });

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

        Intent intent;
        switch ((int) drawerItem.getIdentifier()) {
            case R.id.menu_home:
                intent = new Intent(DefaultActivity.this, DefaultActivity.class);
                if (!flag) {
                    startActivity(intent);
                    finish();
                }
        }
        return false;
    }
}
