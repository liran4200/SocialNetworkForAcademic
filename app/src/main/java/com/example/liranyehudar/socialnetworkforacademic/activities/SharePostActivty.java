package com.example.liranyehudar.socialnetworkforacademic.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.liranyehudar.socialnetworkforacademic.R;

public class SharePostActivty extends AppCompatActivity {

    Button btnShare;
    EditText edtPost;
    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_post_activty);
        bindUI();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharePostActivty.super.onBackPressed();
            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String post = edtPost.getText().toString().trim();
                if(post.length() == 0) {
                    Toast.makeText(v.getContext(),"Please write a post",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent returnData = new Intent();
                returnData.putExtra("post",post);
                setResult(RESULT_OK,returnData);
                finish();
            }
        });

    }

    private void bindUI() {
        btnShare = findViewById(R.id.btn_share);
        edtPost = findViewById(R.id.edt_posting);
        imgBack = findViewById(R.id.imagView_back);
    }
}
