package com.example.suneetsri.speedcheck;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBufferResponse;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class HospitalActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG ="MAP HOSPITAL" ;
    private GeoDataClient geoDataClient;
    private PlaceDetectionClient placeDetectionClient;
    private GoogleApiClient googleApiClient;
    TextView textView;




    int PLACE_PICKER_REQUEST = 1;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital);
        geoDataClient= Places.getGeoDataClient(this,null);
        placeDetectionClient=Places.getPlaceDetectionClient(this,null);
        googleApiClient=new GoogleApiClient.Builder(this).
                addApi(Places.GEO_DATA_API).
                addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this,this)
                .build();
        textView=findViewById(R.id.hospitalTextVew);


        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        AutocompleteFilter autocompleteFilter=new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ESTABLISHMENT)
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_GEOCODE)
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_REGIONS).build();

        autocompleteFragment.setFilter(autocompleteFilter);
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(final Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName());
                textView.setText(place.getName()+"\n"+place.getAddress() +" \n" +place.getPhoneNumber()+"\n"+place.getLatLng()+"\n"+place.getLocale());

                geoDataClient.getPlaceById(place.getId()).addOnCompleteListener(new OnCompleteListener<PlaceBufferResponse>() {
                    @Override
                    public void onComplete(@NonNull Task<PlaceBufferResponse> task) {
                        if (task.isSuccessful()) {
                            PlaceBufferResponse places = task.getResult();
                            Place myPlace = places.get(0);
                            Log.e(TAG, "Place found: " + myPlace.getName());

                            textView.setText(place.getName()+"\n"+place.getAddress() +" \n" +place.getPhoneNumber()+"\n"+place.getLatLng()+"\n"+place.getLocale());
                            Toast.makeText(HospitalActivity.this, ""+myPlace.getAddress(), Toast.LENGTH_SHORT).show();
                            places.release();
                        } else {
                            Toast.makeText(HospitalActivity.this, "Place Not Found", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "Place not found.");
                        }

                    }
                });

            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Connection Failed", Toast.LENGTH_SHORT).show();
    }
}
