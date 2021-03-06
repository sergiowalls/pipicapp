package com.apps.awesome.smartpipican;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Set;

public class PipicanProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pipican_profile);

        String pipicanName = getIntent().getStringExtra("PIPICAN_NAME");
        if (pipicanName != null) {
            Pipican pipican = null;
            pipican = obtainPipicanFromName(pipicanName);
            if (pipican != null) {
                TextView nameView = (TextView) findViewById(R.id.name);
                nameView.setText(pipican.getTitle());

                TextView addressView = (TextView) findViewById(R.id.address);
                addressView.setText(pipican.getAddress());

                LinearLayout facilitiesLayout = (LinearLayout) findViewById(R.id.facilities);
                Set<Pipican.Facility> facilities = pipican.getFacilities();

                for (Pipican.Facility f : facilities) {
                    ImageView facilityImage = new ImageView(this);
                    facilityImage.setImageResource(getDrawable(f));
                    facilityImage.setAdjustViewBounds(true);
                    facilitiesLayout.addView(facilityImage);
                }
                ImageView view = (ImageView) findViewById(R.id.image_pipican);
                view.setImageResource(getImage(pipican.getTitle()));
                view.setAdjustViewBounds(true);
            }
        }


    }

    public static Pipican obtainPipicanFromName(String pipicanName) {
        switch (pipicanName) {
            case "royalPipi":
                return PipicanSeeder.getRoyalPipi();
            case "PipicanA5":
                return PipicanSeeder.getA5();
            case "pipicanEusebiGuell":
                return PipicanSeeder.getEusebiGuell();
        }
        return null;
    }

    private int getImage(String title) {
        switch (title) {
            case "pipicanEusebiGuell":
                return R.drawable.pipican_eusebi;
            case "PipicanA5":
                return R.drawable.pipican_a5;
            case "royalPipi":
                return R.drawable.pipican_royal;
        }
        return 0;
    }

    private int getDrawable(Pipican.Facility f) {
        switch (f) {
            case WATER:
                return R.drawable.water;
            case BAGS:
                return R.drawable.bags;
            case BIG_SIZE:
                return R.drawable.big_size;
            case SPECIAL_ZONES:
                return R.drawable.special_zone;
            case AGILITY:
                return R.drawable.agility;
        }
        return 0;
    }
}

