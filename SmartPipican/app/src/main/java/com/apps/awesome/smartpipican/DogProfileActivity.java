package com.apps.awesome.smartpipican;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;

public class DogProfileActivity extends AppCompatActivity {

    private EditText nameView;
    private EditText ageView;
    private RadioButton maleView;
    private RadioButton femaleView;
    private EditText raceView;
    private TextView specialitiesView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_profile);

        String dogName = getIntent().getStringExtra("DOG_NAME");
        if (dogName != null) {
            Dog dog = null;
            switch (dogName) {
                case "Angus":
                    dog = DogSeeder.getAngus();
                    break;
                case "Kira":
                    dog = DogSeeder.getKira();
                    break;
                case "Rex":
                    dog = DogSeeder.getRex();
                    break;
            }
            if (dog != null) {
                nameView = (EditText) findViewById(R.id.name);
                nameView.setText(dog.getName());

                ageView = (EditText) findViewById(R.id.age);
                ageView.setText(String.valueOf(dog.getAge()));

                maleView = (RadioButton) findViewById(R.id.male);
                if (dog.isMale()) maleView.toggle();

                femaleView = (RadioButton) findViewById(R.id.female);
                if (dog.isFemale()) femaleView.toggle();

                raceView = (EditText) findViewById(R.id.race);
                raceView.setText(dog.getRace());

                List<String> personality = dog.getPersonality();
                String specialities = "";
                for (String s : personality) {
                    specialities = specialities.concat(s).concat(", ");
                }
                specialities = specialities.substring(0, specialities.length() - 2);
                specialitiesView = (TextView) findViewById(R.id.specialities);
                specialitiesView.setText(specialities);

            }
        }


    }

    public void onSexSelected(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.male:
                if (checked)
                    // Pirates are the best
                    break;
            case R.id.female:
                if (checked)
                    // Ninjas rule
                    break;
        }
    }
}

