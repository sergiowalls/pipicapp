package com.apps.awesome.smartpipican;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RadioButton;

import java.util.List;

public class DogProfileActivity extends AppCompatActivity {

    private AutoCompleteTextView raceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_profile);

        raceView = (AutoCompleteTextView) findViewById(R.id.race);

    }

    private void addItemsToAutocomplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(DogProfileActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        raceView.setAdapter(adapter);
    }

    public void onSexSelected(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
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

