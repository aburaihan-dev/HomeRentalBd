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


import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import io.github.arsrabon.m.homerentalbd.R;


public class EditUserProfile extends AppCompatActivity implements Drawer.OnDrawerItemClickListener {

    private Drawer result;
    private AccountHeader headerResult;
    private Toolbar myToolbar;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private boolean flag = false;

    Button btn_Edit;
    Button btn_Save;
    Button btn_Skip;

    EditText edit_userFullname;
    EditText edit_userName;
    EditText edit_userEmail;
    EditText edit_userMobileNo;
    EditText edit_userTelephone;

    Spinner area_spinner;
    Spinner city_spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_userprofile);

        Intent intent = getIntent();
        String uID = intent.getStringExtra("UserId");
        setSharedPreferences(); // creates Sharedpref. and editor instances
        setMyToolbar(); // Toolbar Setter
        navDrawerMaker(); // NavDrawer Maker

        edit_userFullname = (EditText) findViewById(R.id.edit_userFullName);
        edit_userName = (EditText) findViewById(R.id.edit_userFullName);
        edit_userEmail = (EditText) findViewById(R.id.edit_userEmail);
        edit_userMobileNo = (EditText) findViewById(R.id.edit_userMobileNo);
        edit_userTelephone = (EditText) findViewById(R.id.edit_userPhoneNo);

        area_spinner = (Spinner) findViewById(R.id.spin_area);
        city_spinner = (Spinner) findViewById(R.id.spin_city);

        btn_Edit = (Button) findViewById(R.id.btn_EditProfile);
        btn_Save = (Button) findViewById(R.id.btn_SaveProfile);
        btn_Skip = (Button) findViewById(R.id.btn_SkipToHome);



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

    public void setSharedPreferences() {
        sharedPreferences = getSharedPreferences(String.valueOf(R.string.MyPreference), MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putBoolean("loggedin", false);
        editor.commit();
    }

    public void setMyToolbar() {
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    public void navDrawerMaker() {
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(new ProfileDrawerItem().withName("Sign In / Sign Up")
                        .withEmail("AndroidStudio@gmail.com")
                        .withIcon(getResources().getDrawable(R.drawable.profile2)))
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();

        Log.d("CheckPref", String.valueOf(sharedPreferences.getBoolean("loggedin", false)));

        if (sharedPreferences.getBoolean("loggedin", false)) {
            result = new DrawerBuilder()
                    .withActivity(this)
                    .withToolbar(myToolbar)
                    .withAccountHeader(headerResult)
                    .inflateMenu(R.menu.drawer_menu_secondary)
                    .build();
        } else {
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
