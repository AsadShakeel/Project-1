package com.lab42.maham.senseilocater;

import android.Manifest;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.test.mock.MockPackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileFragment extends Fragment implements View.OnClickListener{

    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    GPSTracker gps;

    ToggleButton toogle;
    Button btn_edit = null;
    EditText et_name = null;
    EditText et_education = null;
    EditText et_experience = null;
    Button btn_submit = null;
    ImageView img_edit_profile_activity = null;

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

        et_name = (EditText) view.findViewById(R.id.et_name);
        et_education = (EditText) view.findViewById(R.id.et_education);
        et_experience = (EditText) view.findViewById(R.id.et_experience);
        btn_edit = (Button) view.findViewById(R.id.btn_edit);
        btn_submit = (Button) view.findViewById(R.id.btn_submit);
        img_edit_profile_activity = (ImageView) view.findViewById(R.id.img_edit_profile_activity);

        btn_edit.setOnClickListener(this);
        btn_submit.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_edit:

                //code to get image from gallery and upload
                Toast.makeText(getContext(),"Edit image here", Toast.LENGTH_SHORT).show();

                break;
            case R.id.btn_submit:
                Toast.makeText(getContext(),"asadasadasad",Toast.LENGTH_SHORT).show();
                Teacher t = new Teacher();
                et_name.toString();
                t.setId("7");
                t.setName(et_name.getText().toString());
                t.setEducation(et_education.getText().toString());

                t.setEmail("lkjlkj");
                t.setLocation("pk");
                t.setPassword("lkjlkj");
                t.setPost("Junior");
                t.setAvailable("false");

                new MyAsyncTask().execute(t.toString(), t.getId());
                break;
            default:
                break;
        }
    }

    public class Teacher{
        private String Id;
        private String Name;
        private String Email;
        private String Location;
        private String Education;
        private String Password;
        private String Post;
        private String Available;

        public String getId() {
            return Id;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getEmail() {
            return Email;
        }

        public void setEmail(String email) {
            Email = email;
        }

        public String getLocation() {
            return Location;
        }

        public void setLocation(String location) {
            Location = location;
        }

        public String getEducation() {
            return Education;
        }

        public void setEducation(String education) {
            Education = education;
        }

        public String getPassword() {
            return Password;
        }

        public void setPassword(String password) {
            Password = password;
        }

        public String getPost() {
            return Post;
        }

        public void setPost(String post) {
            Post = post;
        }

        public String getAvailable() {
            return Available;
        }

        public void setAvailable(String available) {
            Available = available;
        }

        public void setId(String id) {
            this.Id = id;
        }
    }


    public class MyAsyncTask extends AsyncTask<String , String , String> {

        @Override
        protected String doInBackground(String... params) {
            String t = params[0];
            String id = params[1];

            HttpURLConnection urlConnection = null;
            URL url = null;
            try {
                url = new URL("http://senseilocatorwebservices.apphb.com/api/Teacher/");// + id);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.connect();

                JSONObject obj = new JSONObject();
                obj.put("Id", "7");
                obj.put("Name", "Fahad");
                obj.put("Email","fff");
                obj.put("Location","fati");
                obj.put("Education","Phd");
                obj.put("Password","123");
                obj.put("Post","junior");
                obj.put("Available","false");

                String temp = obj.toString();

                DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
                wr.writeBytes(temp);

            } catch (Exception e){
                e.printStackTrace();
            }
            finally {
                urlConnection.disconnect();
            }
            return "Successful";
        }

        @Override
        protected void onPostExecute(String o) {
            super.onPostExecute(o);
            Toast.makeText(getContext(),o,Toast.LENGTH_SHORT).show();
        }
    }
    
}
