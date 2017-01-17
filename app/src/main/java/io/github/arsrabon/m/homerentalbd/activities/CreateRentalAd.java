package io.github.arsrabon.m.homerentalbd.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.zip.Inflater;

import io.github.arsrabon.m.homerentalbd.R;
import io.github.arsrabon.m.homerentalbd.model.Area;
import io.github.arsrabon.m.homerentalbd.model.AreaResponse;
import io.github.arsrabon.m.homerentalbd.model.PostResponse;
import io.github.arsrabon.m.homerentalbd.model.RentTypeResponse;
import io.github.arsrabon.m.homerentalbd.model.RentTypes;
import io.github.arsrabon.m.homerentalbd.rest.ApiClient;
import io.github.arsrabon.m.homerentalbd.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateRentalAd extends AppCompatActivity implements Drawer.OnDrawerItemClickListener, DatePickerDialog.OnDateSetListener {

    FirebaseUser firebaseUser;
    FirebaseStorage firebaseStorage;

    Toolbar myToolbar;
    AccountHeader headerResult;
    Drawer result;

    TextView dateTextView;
    EditText edit_banner;
    EditText edit_bedRooms;
    EditText edit_bathRooms;
    EditText edit_floordetails;
    EditText edit_address;
    EditText edit_rentCost;
    EditText edit_rentdetails;
    CheckBox chk_lift;
    CheckBox chk_parking;
    Button datePickerButton;
    Button pickBannerImg;
    Button btn_publish;
    Button btn_preview;
    Spinner spinner_area;
    Spinner spinner_type;

    ImageView banner_img;
    ImageView other_1_img;
    ImageView other_2_img;

    List<Area> areas;
    List<RentTypes> types;

    Area area;
    RentTypes type;
    String date;

    boolean flag;

    private Inflater inflater;
    private ApiInterface apiService;

    List<Bitmap> bitmapList = new ArrayList<>();
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createrentalad);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);

        setMyToolbar(); // Toolbar Setter
        navDrawerMaker(); // NavDrawer Maker

        datePickerButton = (Button) findViewById(R.id.myDatePicker);
        btn_preview = (Button) findViewById(R.id.btn_preview);
        btn_publish = (Button) findViewById(R.id.btn_publish);

        pickBannerImg = (Button) findViewById(R.id.banner_img);
        edit_banner = (EditText) findViewById(R.id.edit_banner);
        edit_bedRooms = (EditText) findViewById(R.id.edit_bedrooms);
        edit_bathRooms = (EditText) findViewById(R.id.edit_bathRooms);
        edit_floordetails = (EditText) findViewById(R.id.edit_floordetails);
        edit_rentCost = (EditText) findViewById(R.id.edit_rentCost);
        edit_rentdetails = (EditText) findViewById(R.id.edit_rentdetails);
        edit_address = (EditText) findViewById(R.id.edit_address);

        chk_lift = (CheckBox) findViewById(R.id.chk_lift);
        chk_parking = (CheckBox) findViewById(R.id.chk_parking);

        dateTextView = (TextView) findViewById(R.id.show_pickedDate);
        spinner_area = (Spinner) findViewById(R.id.spin_area);
        spinner_type = (Spinner) findViewById(R.id.spin_type);

        banner_img = (ImageView) findViewById(R.id.test_banner_img);
        other_1_img = (ImageView) findViewById(R.id.test_other_img_1);
        other_2_img = (ImageView) findViewById(R.id.test_other_img_2);

        // initialize ApiInterface for use
        apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<RentTypeResponse> rentTypeResponseCall = apiService.getRentTypes();
        rentTypeResponseCall.enqueue(new Callback<RentTypeResponse>() {
            @Override
            public void onResponse(Call<RentTypeResponse> call, Response<RentTypeResponse> response) {
                types = response.body().getRentTypesList();
                setRentTypeSpinner();
//                Log.d("rentType", String.valueOf(types.size()));
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
//                Log.d("area", String.valueOf(areas.size()));
                setAreaSpinner();
            }

            @Override
            public void onFailure(Call<AreaResponse> call, Throwable t) {

            }
        });

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
                dpd.setVersion(DatePickerDialog.Version.VERSION_1);
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });

        pickBannerImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count <= 3) {
                    count++;
                    Intent intent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 0);
                } else {
                    Toast.makeText(getBaseContext(), "You can't add more than 3 images now.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Your Rent is posted Successfully , Other users will see it in a short moment.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getBaseContext(),DefaultActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void postRentalAd(){
        String banner = edit_banner.getText().toString();
        String address = edit_address.getText().toString();
        String floordetails = edit_floordetails.getText().toString();
        String rentdetails = edit_rentdetails.getText().toString();
        int rentCost = Integer.parseInt(edit_rentCost.getText().toString().trim());
        boolean lift = chk_lift.isChecked();
        boolean parking = chk_parking.isChecked();

        if(banner.length()>0 && address.length()>0 && floordetails.length()>0 && rentdetails.length()>0 && rentCost>0 && area!=null && type!=null){
//            Call<PostResponse> postResponseCall = apiService.
        }
    }

    public void showImages() {
        if (bitmapList.size() == 1) {
            banner_img.setVisibility(View.VISIBLE);
            banner_img.setImageBitmap(bitmapList.get(0));
        }
        if (bitmapList.size() == 2) {
            other_1_img.setVisibility(View.VISIBLE);
            other_1_img.setImageBitmap(bitmapList.get(1));
        }
        if (bitmapList.size() == 3) {
            other_2_img.setVisibility(View.VISIBLE);
            other_2_img.setImageBitmap(bitmapList.get(2));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Uri targetUri = data.getData();
            Bitmap bitmap;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
//                banner_img.setImageBitmap(bitmap);
                bitmapList.add(bitmap);
                Log.d("onActivityResult: ", String.valueOf(bitmapList.size()));
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            showImages();
        }
    }

    public void setRentTypeSpinner() {
        ArrayAdapter<RentTypes> rtypes = new ArrayAdapter<RentTypes>(getBaseContext(), R.layout.spinner_item, types);
        rtypes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_type.setAdapter(rtypes);
        spinner_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getBaseContext(), "hello", Toast.LENGTH_SHORT).show();
                type = types.get(adapterView.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                type = types.get(0);
            }
        });
    }

    public void setAreaSpinner() {
        ArrayAdapter<Area> areaAdapter = new ArrayAdapter<Area>(getBaseContext(), R.layout.spinner_item, areas);
        areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_area.setAdapter(areaAdapter);
        spinner_area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getBaseContext(), "hello", Toast.LENGTH_SHORT).show();
                area = areas.get(adapterView.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                area = areas.get(0);
            }
        });
    }


    public void setMyToolbar() {
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Create Rental Ad");
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

        date = year + "-" + month + "-" + dayOfMonth;
        dateTextView.setText(date);
    }

    public void myRentDiscardAlert() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
