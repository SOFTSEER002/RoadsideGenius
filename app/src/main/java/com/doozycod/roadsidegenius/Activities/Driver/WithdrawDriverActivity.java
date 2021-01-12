package com.doozycod.roadsidegenius.Activities.Driver;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.doozycod.roadsidegenius.R;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class WithdrawDriverActivity extends AppCompatActivity {
    MaterialCheckBox materialCheckBox;
    TextInputEditText textInputEditText;
    Button withdraw;
    TextInputLayout textInput;
    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_driver);
        textInput = findViewById(R.id.textInput);
        materialCheckBox = findViewById(R.id.checkBox);
        textInputEditText = findViewById(R.id.amount);
        withdraw = findViewById(R.id.withdrawButton);
        backButton = findViewById(R.id.backButton);
        if (materialCheckBox.isChecked()) {
            textInput.setEnabled(false);
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        materialCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (materialCheckBox.isChecked()) {
                    textInput.setEnabled(false);

                } else {
                    textInput.setEnabled(true);

                }
            }
        });
    }
}