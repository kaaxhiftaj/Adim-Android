package com.adim.techease.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
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
import com.adim.techease.controllers.VoteModel;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kaxhiftaj on 10/22/17.
 */

public class VoteAdapter extends  RecyclerView.Adapter<VoteAdapter.MyViewHolder> {

    List<VoteModel> voteModel;
    Context context;
    String picName, voteContestentId, UserId;
    SharedPreferences sharedPreferences;
    String getPercentatge;

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
        voteContestentId = String.valueOf(model.getVoteContestentID());
        picName = String.valueOf(model.getImage());
        Glide.with(context).load("http://adadigbomma.com/panel/images/gallery/" + model.getImage()).into(holder.imageVote);
        holder.imageVote.getDrawable();
        holder.textviewTitle.setText(model.getTitle());
        holder.textviewVotes.setText(model.getVote());
        holder.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                Uri screenshotUri = Uri.parse("http://adadigbomma.com/panel/images/gallery/"+model.getImage());
                sharingIntent.setType("image/*");
                sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                context.startActivity(Intent.createChooser(sharingIntent, "Share image using"));
            }
        });
        holder.btnVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    apiCall();
                holder.textviewVotes.setText(getPercentatge);
            }
        });

    }

    @Override
    public int getItemCount() {
        return voteModel.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  {
        ImageView imageVote;
        TextView textviewTitle, textviewVotes;
        LinearLayout linearLayout;
        Button btnVote, btnShare;
        Typeface typeface;

        public MyViewHolder(View itemView) {
            super(itemView);
            typeface = Typeface.createFromAsset(context.getAssets(), "myfont.ttf");
            imageVote = (ImageView) itemView.findViewById(R.id.ivVote);
            textviewTitle = (TextView) itemView.findViewById(R.id.tvVoteImageTitle);
            textviewVotes = (TextView) itemView.findViewById(R.id.tvVote);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.llVote);
            btnVote = (Button) itemView.findViewById(R.id.btnVote);
            textviewVotes.setTypeface(typeface);
            textviewTitle.setTypeface(typeface);
            btnVote.setTypeface(typeface);
            btnShare = (Button) itemView.findViewById(R.id.btnShare);
            btnShare.setTypeface(typeface);
            btnShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(Intent.ACTION_PICK,
//                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    ((Activity)context).startActivityForResult(intent, 0);
                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    Uri screenshotUri = Uri.parse("http://adadigbomma.com/panel/images/gallery/"+picName);
                    sharingIntent.setType("image/*");
//                    sharingIntent.putExtra(Intent.EXTRA_SUBJECT,String.valueOf(textviewTitle.getText()));
//                    String sAux =textviewTitle.getText().toString()+"\n"+textviewVotes.getText().toString();
//                    sharingIntent.putExtra(Intent.EXTRA_TEXT, sAux);
                    sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                    context.startActivity(Intent.createChooser(sharingIntent, "Share image using"));

                }
            });
        }


    }

    public void apiCall() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Configuration.USER_URL + "App/voteher/" + UserId + "/" + voteContestentId
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("zma  vote response", Configuration.USER_URL + "App/voteher/" + UserId + voteContestentId + response);
                if (response.contains("true")) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArr = jsonObject.getJSONArray("user");
                        for (int i = 0; i < jsonArr.length(); i++) {
                            JSONObject temp = jsonArr.getJSONObject(i);
                            getPercentatge=temp.getString("percentage");
                            Toast.makeText(context, String.valueOf(temp.getString("percentage")), Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("zma error", String.valueOf(e.getCause()));
                    }


                } else {
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //DialogUtils.sweetAlertDialog.dismiss();
                // DialogUtils.showErrorTypeAlertDialog(getActivity(), "Server error");
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