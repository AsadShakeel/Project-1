package com.lab42.maham.senseilocater;


import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.test.mock.MockPackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileFragment extends Fragment {

    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    GPSTracker gps;

    ToggleButton toogle;

    public EditProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        try
        {
            if (ActivityCompat.checkSelfPermission(getContext(), mPermission)!= MockPackageManager.PERMISSION_GRANTED)
            {
                //ActivityCompat.requestPermissions(getActivity(), new String[]{mPermission},REQUEST_CODE_PERMISSION);
                // If any permission above not allowed by user, this condition willexecute every time, else your else part will work
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        toogle=(ToggleButton ) view.findViewById(R.id.toogle_location);

        toogle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (isChecked)
                {
                    // The toggle is enabled
                    gps = new GPSTracker(getContext());

                    // check if GPS enabled
                    if(gps.canGetLocation())
                    {

                        double latitude = gps.getLatitude();
                        double longitude = gps.getLongitude();
                        String msg;
                        msg="Your Location is - \nLat: " + latitude + "\nLong: "+ longitude;

                        // \n is for new line
                        //Toast.makeText(this, "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        // can't get location
                        // GPS or Network is not enabled
                        // Ask user to enable GPS/network in settings
                        //Toast.makeText(this,"Your GPS is disabled",Toast.LENGTH_SHORT).show();
                        Toast.makeText(getContext(), "Your GPS is  disable", Toast.LENGTH_LONG).show();
                        gps.showSettingsAlert();
                    }

                } else {
                    // The toggle is disabled
                }
            }
        });
        return view;
    }

}
