package com.aula.gridpedido;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        TextView imgLogo    = findViewById(R.id.imgLogo);
        TextView  textSlogan = findViewById(R.id.textSlogan);
        LinearLayout textPromo  = findViewById(R.id.textPromo);

        // Animação de entrada do logo (fade + zoom)
        AnimationSet animLogo = new AnimationSet(true);

        AlphaAnimation fadeIn = new AlphaAnimation(0f, 1f);
        fadeIn.setDuration(1200);

        ScaleAnimation scaleIn = new ScaleAnimation(
                0.5f, 1f, 0.5f, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        scaleIn.setDuration(1200);

        animLogo.addAnimation(fadeIn);
        animLogo.addAnimation(scaleIn);
        imgLogo.startAnimation(animLogo);

        // Animação do slogan (fade com delay)
        AlphaAnimation animSlogan = new AlphaAnimation(0f, 1f);
        animSlogan.setDuration(1000);
        animSlogan.setStartOffset(1000);
        animSlogan.setFillAfter(true);
        textSlogan.startAnimation(animSlogan);

        // Animação da promoção (fade com delay maior)
        AlphaAnimation animPromo = new AlphaAnimation(0f, 1f);
        animPromo.setDuration(1000);
        animPromo.setStartOffset(2000);
        animPromo.setFillAfter(true);
        textPromo.startAnimation(animPromo);

    }

    public void api(View view) {
        Intent rota = new Intent(this, MainActivity.class);
        rota.putExtra("opc", 1);
        startActivity(rota);
    }

    public void fixa(View view) {
        Intent rota = new Intent(this, MainActivity.class);
        rota.putExtra("opc", 0);
        startActivity(rota);
    }
}