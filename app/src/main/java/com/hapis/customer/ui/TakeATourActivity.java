package com.hapis.customer.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.hapis.customer.R;
import com.hapis.customer.ui.view.BaseView;
import com.hapis.customer.ui.view.TakeATourView;
import com.hapis.customer.ui.view.TakeATourViewModal;

public class TakeATourActivity extends BaseFragmentActivity<TakeATourViewModal> implements TakeATourView {

    private AppCompatButton btnSignIn, btnSignUp;
    private AppCompatImageView bgIV;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_atour);

        bgIV = findViewById(R.id.bg_iv);
        Glide.with(this)
                .asBitmap()
                .load(R.drawable.landing_bg)
                .apply(RequestOptions
                        .noTransformation()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop())
                .into(bgIV);

        btnSignIn = findViewById(R.id.btn_signin);
        btnSignUp = findViewById(R.id.btn_signup);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModal.handleClick(R.id.btn_signin);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModal.handleClick(R.id.btn_signup);
            }
        });
    }

    @Override
    protected Class getViewModalClass() {
        return TakeATourViewModal.class;
    }

    @Override
    protected BaseView getViewImpl() {
        return this;
    }

    @Override
    public void viewSignUp() {

        Intent intent = new Intent(TakeATourActivity.this, SigupActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        try {
            finishAffinity();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void viewSignIn() {
        Intent intent = new Intent(TakeATourActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        try {
            finishAffinity();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
