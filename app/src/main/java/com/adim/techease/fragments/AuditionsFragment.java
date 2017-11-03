package com.adim.techease.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.adim.techease.R;
import com.adim.techease.controllers.VolleyMultipartRequest;
import com.adim.techease.utils.Configuration;
import com.adim.techease.utils.DialogUtils;
import com.adim.techease.utils.GeneralUtils;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static com.adim.techease.utils.GeneralUtils.*;
import static com.facebook.FacebookSdk.getApplicationContext;


public class AuditionsFragment extends Fragment {

    EditText name, email, state,loc,age,phone;
    MaterialSpinner city;
    ImageView image;
    String strName, strEmail, strState , strCity,strLoc,strAge,strPhone;
    final int CAMERA_CAPTURE = 1;
    final int RESULT_LOAD_IMAGE = 2;
    File file;
    Button sendButton;
    Typeface typeface;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View v = inflater.inflate(R.layout.fragment_auditions, container, false);
        typeface=Typeface.createFromAsset(getActivity().getAssets(),"myfont.ttf");
        name = (EditText) v.findViewById(R.id.et_name);
        email = (EditText) v.findViewById(R.id.et_email_audition);
        age=(EditText)v.findViewById(R.id.etAgeAudition);
        phone=(EditText)v.findViewById(R.id.etPhoneAudition);
        state = (EditText) v.findViewById(R.id.et_stateofRegion);
        image = (ImageView) v.findViewById(R.id.image);
        sendButton = (Button)v.findViewById(R.id.sendButton);
        sendButton.setTypeface(typeface);
        name.setTypeface(typeface);
        email.setTypeface(typeface);
        age.setTypeface(typeface);
        phone.setTypeface(typeface);
        state.setTypeface(typeface);


        city = (MaterialSpinner) v.findViewById(R.id.city);

        city.setItems("Asaba", "Awka", "Enugu", "Lagos", "Port-harcot", "Abuja");
        city.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                strCity = item;
                Toast.makeText(getActivity(), strCity, Toast.LENGTH_SHORT).show();
            }
        });


        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final CharSequence[] itemedit = {"Take Photo", "Choose Image", "Cancel"};
                AlertDialog.Builder build = new AlertDialog.Builder(getActivity());
                build.setTitle("Add Photo!");
                build.setItems(itemedit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        // boolean result=Utility.checkPermission(UsersDetails.this);
                        if (itemedit[item].equals("Take Photo")) {
                            cameraIntent();
                        } else if (itemedit[item].equals("Choose Image")) {
                            galleryIntent();
                        } else if (itemedit[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                build.show();
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDataInput();
            }
        });
        return v ;
    }

    public void onDataInput() {
        strEmail = email.getText().toString();
        strName = name.getText().toString();
        strState = name.getText().toString();
        strCity = city.getText().toString();
        strAge= age.getText().toString();
        strPhone=phone.getText().toString();

        if ((!android.util.Patterns.EMAIL_ADDRESS.matcher(strEmail).matches())) {
            email.setError("Please enter valid email id");
        } else if (name.equals("")) {
            name.setError("Please enter your Name");
        } else if (state.equals("")) {
            state.setError("Please enter your State");
        } else if (name.equals("")) {
            name.setError("Please Select your City");
        } else if(phone.equals("")){
            phone.setError("Please enter your number");
        }else if(age.equals("")){
            age.setError("Please enter your age");
        }
        else {
            DialogUtils.showProgressSweetDialog(getActivity(), " Submiting your Application");
            apiCall();
        }
    }



    public void apiCall() {
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, Configuration.USER_URL + "App/audition", new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                DialogUtils.sweetAlertDialog.dismiss();
                Log.d("zma response", String.valueOf(Configuration.USER_URL+ "App/audition"+"\n"+response));
                if (response.statusCode == 200) {
                    email.setText("");
                    name.setText("");
                     age.setText("");
                    phone.setText("");
                    image.setImageDrawable(null);
                    Toast.makeText(getActivity(), "Documents Uploaded", Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                String errorMessage = "Unknown error";
                if (networkResponse == null) {
                    DialogUtils.sweetAlertDialog.dismiss();
                    if (error.getClass().equals(TimeoutError.class)) {
                        errorMessage = "Request timeout";
                    } else if (error.getClass().equals(NoConnectionError.class)) {
                        errorMessage = "Failed to connect server";
                    }
                } else {
                    DialogUtils.sweetAlertDialog.dismiss();
                    String result = new String(networkResponse.data);
                    Log.d("zma error response", String.valueOf(result));
                    DialogUtils.showWarningAlertDialog(getActivity(),result);
                    try {
                        JSONObject response = new JSONObject(result);
                        Log.d("zma response, obj", String.valueOf(response));
                        String status = response.getString("status");
                        String message = response.getString("message");
                        Log.d("zma response, obj, s", String.valueOf(status));
                        if (networkResponse.statusCode == 404) {
                            errorMessage = "Resource not found";
                        } else if (networkResponse.statusCode == 401) {
                            errorMessage = message + " Please login again";
                        } else if (networkResponse.statusCode == 400) {
                            errorMessage = message + " Check your inputs";
                        } else if (networkResponse.statusCode == 500) {
                            errorMessage = message + " Something is getting wrong";
                        } else if (networkResponse.statusCode == 406) {
                            errorMessage = message + "Please decrease your video size";
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.d("zma Error", errorMessage);
                DialogUtils.showWarningAlertDialog(getActivity(),errorMessage);
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name" , strName);
                params.put("email", strEmail);
                params.put("state" , strState);
                params.put("location",strCity);
                params.put("age",strAge);
                params.put("phone",strPhone);
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                // file name could found file base or direct access from real path
                // for now just get bitmap data from ImageView
                String mimeType = getMimeTypeofFile(file);
                params.put("files", new DataPart("files", GeneralUtils.getByteArrayFromFile(file), mimeType));
                Log.d("zma photo params", file.getPath());
                return params;
            }
        };
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(200000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(volleyMultipartRequest);
    }


    public void cameraIntent() {

        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(captureIntent, CAMERA_CAPTURE);

    }

    public void galleryIntent() {

        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            file = new File(GeneralUtils.getRealPathFromURI(getApplicationContext(), selectedImage));
            Log.d("zma file", file.getPath());
            cursor.close();
            image.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        } else if (requestCode == CAMERA_CAPTURE && null != data) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            image.setImageBitmap(photo);

            Uri tempUri = GeneralUtils.getImageUri(getApplicationContext(), photo);
            file = new File(GeneralUtils.getRealPathFromURI(getApplicationContext(), tempUri));
            Log.d("zma file", file.getPath());
        }
    }
}