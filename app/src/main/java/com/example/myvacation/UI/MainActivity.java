package com.example.myvacation.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myvacation.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.enterBtn);
        button.setOnClickListener(new View.OnClickListener() { //When button is clicked the next screen is initiated
            @Override
            public void onClick(View v) { //On Click event, the 'intent' is to go from this MainActivity class to VacationList
                Intent intent = new Intent(MainActivity.this, VacationList.class);
                intent.putExtra("test", "Information");
                startActivity(intent);
            }
        });
    }
}