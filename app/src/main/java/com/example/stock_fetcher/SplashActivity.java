package com.example.stock_fetcher;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_splash);

        // Apply the fade-in animation
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        findViewById(R.id.splash_root_layout).startAnimation(fadeIn);

        // Delay for 3 seconds, then proceed to the MainActivity with fade-out animation
        new Handler().postDelayed(() -> {
            // Apply fade-out animation
            Animation fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
            findViewById(R.id.splash_root_layout).startAnimation(fadeOut);

            // Start MainActivity after the fade-out animation finishes
            fadeOut.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {}

                @Override
                public void onAnimationEnd(Animation animation) {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // Close the splash screen activity
                }

                @Override
                public void onAnimationRepeat(Animation animation) {}
            });
        }, 3000); // 3-second delay
    }
}
