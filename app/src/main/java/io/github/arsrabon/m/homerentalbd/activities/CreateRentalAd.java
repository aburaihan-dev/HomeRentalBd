package io.github.arsrabon.m.homerentalbd.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.zip.Inflater;

import io.github.arsrabon.m.homerentalbd.R;

public class CreateRentalAd extends AppCompatActivity implements Drawer.OnDrawerItemClickListener, DatePickerDialog.OnDateSetListener {

    FirebaseUser firebaseUser;
    FirebaseStorage firebaseStorage;

    Toolbar myToolbar;
    AccountHeader headerResult;
    Drawer result;

    Button datePickerButton;

    boolean flag;
    private TextView dateTextView;
    private Inflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createrentalad);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);

        setMyToolbar(); // Toolbar Setter
        navDrawerMaker(); // NavDrawer Maker

        datePickerButton = (Button) findViewById(R.id.myDatePicker);
        dateTextView = (TextView) findViewById(R.id.show_pickedDate);

        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        CreateRentalAd.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setVersion(DatePickerDialog.Version.VERSION_2);
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });

    }


    public void setMyToolbar() {
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Create Rent Advertisement");
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
                            Toast.makeText(CreateRentalAd.this, "hello", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(CreateRentalAd.this, "hello", Toast.LENGTH_SHORT).show();
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
                intent = new Intent(getBaseContext(), DefaultActivity.class);
                startActivity(intent);
                finish();

        }
        return false;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String month = null;
        if ((monthOfYear + 1) < 10) {
            month = "0" + (monthOfYear + 1);
        } else {
            month = String.valueOf((monthOfYear + 1));
        }
        String date = year + "-" + month + "-" + dayOfMonth;
        dateTextView.setText(date);
    }
}
