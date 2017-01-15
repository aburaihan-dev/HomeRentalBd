package io.github.arsrabon.m.homerentalbd.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import io.github.arsrabon.m.homerentalbd.R;
import io.github.arsrabon.m.homerentalbd.model.User;
import io.github.arsrabon.m.homerentalbd.model.UserResponse;
import io.github.arsrabon.m.homerentalbd.rest.ApiClient;
import io.github.arsrabon.m.homerentalbd.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditUserProfile extends AppCompatActivity implements Drawer.OnDrawerItemClickListener {

    private Drawer result;
    private AccountHeader headerResult;
    private Toolbar myToolbar;


    private boolean flag = false;

    Button btn_Edit;
    Button btn_Save;
    Button btn_Skip;

    EditText edit_userFullname;
    EditText edit_userName;
    EditText edit_userEmail;
    EditText edit_userMobileNo;
    EditText edit_userAddress;

    Spinner area_spinner;
    Spinner city_spinner;
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private ApiInterface apiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_userprofile);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        apiService = ApiClient.getClient().create(ApiInterface.class);

        setMyToolbar(); // Toolbar Setter
        navDrawerMaker(); // NavDrawer Maker

        edit_userFullname = (EditText) findViewById(R.id.edit_userFullName);
        edit_userName = (EditText) findViewById(R.id.edit_userFullName);
        edit_userEmail = (EditText) findViewById(R.id.edit_userEmail);
        edit_userMobileNo = (EditText) findViewById(R.id.edit_userMobileNo);
        edit_userAddress = (EditText) findViewById(R.id.edit_userAddress);

        btn_Edit = (Button) findViewById(R.id.btn_EditProfile);
        btn_Save = (Button) findViewById(R.id.btn_SaveProfile);
        btn_Skip = (Button) findViewById(R.id.btn_SkipToHome);

        Call<UserResponse> userResponseCall = apiService.getUserByUserId(firebaseUser.getUid());
        userResponseCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                UserResponse userResponse = response.body();
                User user = userResponse.getUser();
                edit_userName.setText(user.getUsername());
                edit_userFullname.setText(user.getFullname());
                edit_userEmail.setText(user.getEmail());
                edit_userMobileNo.setText(user.getMobile_no());
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {

            }
        });

        btn_Save.setVisibility(View.GONE);
        btn_Skip.setVisibility(View.GONE);

        btn_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = true;
                btn_Edit.setVisibility(View.GONE);
                btn_Save.setVisibility(View.VISIBLE);
                btn_Skip.setVisibility(View.VISIBLE);
            }
        });

        btn_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                btn_Edit.setVisibility(View.VISIBLE);
                btn_Save.setVisibility(View.GONE);
                btn_Skip.setVisibility(View.GONE);
            }
        });

        btn_Skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditUserProfile.this, DefaultActivity.class);
                if (flag) {
//                    startActivity(intent);
                } else {
//                    startActivity(intent);
                }
            }
        });

    }


    public void setMyToolbar() {
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
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
                            Toast.makeText(getBaseContext(), "hello", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(getBaseContext(), "hello", Toast.LENGTH_SHORT).show();
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
        switch ((int) drawerItem.getIdentifier()) {
            case R.id.menu_home:
                xIntent = new Intent(EditUserProfile.this, DefaultActivity.class);
                if (flag) {

                } else {
                    startActivity(xIntent);
                    finish();
                }
                break;
        }

        return false;
    }
}
