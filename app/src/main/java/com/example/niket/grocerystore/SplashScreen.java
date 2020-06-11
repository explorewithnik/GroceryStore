package com.example.niket.grocerystore;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.niket.grocerystore.Activities.Login;

public class SplashScreen extends AppCompatActivity {

    TextView logoName;
    ImageView sale, girl, basket, girl2, supermarket, man, receptionist, cycle,groceries;
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        lottieAnimationView = findViewById(R.id.animation_view);
        groceries = findViewById(R.id.animation_view2);

        basket = findViewById(R.id.basket);
        girl = findViewById(R.id.girl);
        sale = findViewById(R.id.sale);
        girl2 = findViewById(R.id.girl2);
        supermarket = findViewById(R.id.supermarket);
        man = findViewById(R.id.man);
        receptionist = findViewById(R.id.receptionist);
        cycle = findViewById(R.id.cycle);
        logoName = findViewById(R.id.logo);

        new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(SplashScreen.this, Login.class);
                        startActivity(intent);
                        finish();
                    }
                }, 4000);
    }
}
