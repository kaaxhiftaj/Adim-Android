package com.adim.techease.Adapter;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.adim.techease.R;
import com.adim.techease.activities.AuthOptionScreen;
import com.adim.techease.controllers.VoteModel;
import com.adim.techease.fragments.VoteFragment;
import com.adim.techease.utils.Configuration;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.facebook.share.ShareApi;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.thefinestartist.utils.content.ContextUtil.createPackageContext;
import static com.thefinestartist.utils.content.ContextUtil.getResources;
import static com.thefinestartist.utils.content.ContextUtil.startActivity;

/**
 * Created by kaxhiftaj on 10/22/17.
 */

public class VoteAdapter extends  RecyclerView.Adapter<VoteAdapter.MyViewHolder> {

    List<VoteModel> voteModel;
    Context context;
    String picName, voteContestentId, UserId;
    SharedPreferences sharedPreferences;
    String getPercentatge;
    SweetAlertDialog pDialog;

    public VoteAdapter(Context context, List<VoteModel> voteModel) {
        this.voteModel = voteModel;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_vote, parent, false);

        return new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final VoteModel model = voteModel.get(position);
        sharedPreferences = context.getSharedPreferences(Configuration.MY_PREF, Context.MODE_PRIVATE);
        UserId = sharedPreferences.getString("user_Id", "");

        picName = String.valueOf(model.getImage());
        Glide.with(context).load("http://adadigbomma.com/panel/images/gallery/" + model.getImage()).into(holder.imageVote);
        holder.imageVote.getDrawable();
        holder.textviewTitle.setText(model.getTitle());
        holder.textviewVotes.setText(model.getVote()+"votes");
        holder.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Adim");
                    String sAuxLink = "I have voted for " + model.getTitle() + ". Vote for your favourite one here. https://play.google.com/store/apps/details?id=com.adim.techease";
                    intent.putExtra(Intent.EXTRA_TEXT, sAuxLink);
                    context.startActivity(Intent.createChooser(intent, "choose one"));
                } catch(Exception e) {
                    //e.toString();
                }
            }
        });

        holder.btnVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!UserId.equals(""))
                {
                    voteContestentId = String.valueOf(model.getVoteContestentID());

                    apiCall(voteContestentId);
                    pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
                    pDialog .setTitleText("Adding your vote");
                    pDialog.show();
                }
                else
                {
                    pDialog  = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
                           pDialog .setTitleText("Alert");
                    pDialog.setContentText("Won't be able vote unless you Signin");
                    pDialog.setConfirmText("Sigin/Signup");
                    pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    context.startActivity(new Intent(context, AuthOptionScreen.class));
                                }
                            });
                    pDialog.show();

                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return voteModel.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  {
        ImageView imageVote;
        TextView textviewTitle;
        Button  textviewVotes;
        LinearLayout linearLayout;
        Button btnVote, btnShare;
        Typeface typeface;

        public MyViewHolder(View itemView) {
            super(itemView);
            typeface = Typeface.createFromAsset(context.getAssets(), "myfont.ttf");
            imageVote = (ImageView) itemView.findViewById(R.id.ivVote);
            textviewTitle = (TextView) itemView.findViewById(R.id.tvVoteImageTitle);
            textviewVotes = (Button) itemView.findViewById(R.id.tvVote);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.llVote);
            btnVote = (Button) itemView.findViewById(R.id.btnVote);
            textviewVotes.setTypeface(typeface);
            textviewTitle.setTypeface(typeface);
            btnVote.setTypeface(typeface);
            btnShare = (Button) itemView.findViewById(R.id.btnShare);
            btnShare.setTypeface(typeface);

        }


    }

    public void apiCall(final String id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Configuration.USER_URL + "App/voteher/" + UserId + "/" + id
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("zma url", Configuration.USER_URL + "App/voteher/" + UserId + "/" + id);
                if (response.contains("true")) {
                    pDialog.dismiss();

                    final  SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context,SweetAlertDialog.SUCCESS_TYPE);
                    sweetAlertDialog.setTitleText("Vote");
                    sweetAlertDialog.setContentText("Your vote has been added");
                    sweetAlertDialog.setCancelable(true);
                    sweetAlertDialog.show();
                    Fragment fragment = new VoteFragment();
                    ((AppCompatActivity)context).getFragmentManager().beginTransaction().replace(R.id.mainFrame , fragment).commit();


                } else {
                    pDialog.dismiss();

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String jsonObject1 = jsonObject.getString("message");

                            final  SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context,SweetAlertDialog.WARNING_TYPE);
                            sweetAlertDialog.setTitleText("Vote");
                            sweetAlertDialog.setContentText(jsonObject1);
                            sweetAlertDialog.setCancelable(true);
                            sweetAlertDialog.show();


                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("zma error", String.valueOf(e.getCause()));
                    }

                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();

                Log.d("zma volley error", String.valueOf(error.getCause()));

            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded;charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }

        };
        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(200000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(stringRequest);
    }

}