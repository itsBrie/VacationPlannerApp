package com.example.myvacation.UI;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myvacation.Database.Repository;
import com.example.myvacation.Entities.Excursion;
import com.example.myvacation.Entities.Vacation;
import com.example.myvacation.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class VacationDetails extends AppCompatActivity {

    private Repository repository;
    String vacationName;
    String vacationHotelName;
    String vacationStartDate;
    String vacationEndDate;
    int vacationID;
    EditText editVacationTitle;
    EditText editVacationHotelName;
    TextView editVacationStartDate;
    TextView editVacationEndDate;
    Vacation currentVacation;
    int numExcursions;

    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar myCalendarStart = Calendar.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vacation_details);
        FloatingActionButton VacationDetailsAddExcursionBtn = findViewById(R.id.VacationDetailsAddExcursionBtn); //Define the Floating Action Button

        //RECYCLER VIEW- BOUND BY ID
        editVacationTitle = findViewById(R.id.VacationTitle);
        editVacationHotelName = findViewById(R.id.VacationHotelName);
        editVacationStartDate = findViewById(R.id.VacationStartDate);
        editVacationEndDate = findViewById(R.id.VacationEndDate);

        // RECEIVING THE INFORMATION FROM THE ONCLICK VACATION ADAPTER
        vacationID = getIntent().getIntExtra("id", -1);
        vacationName = getIntent().getStringExtra("vacationTitle");
        vacationHotelName = getIntent().getStringExtra("vacationHotel");
        vacationStartDate = getIntent().getStringExtra("vacationStartDate");
        vacationEndDate = getIntent().getStringExtra("vacationEndDate");

        //SETTING RECYCLER VIEW TO THE FORM
        editVacationTitle.setText(vacationName);
        editVacationHotelName.setText(vacationHotelName);
        editVacationStartDate.setText(vacationStartDate);
        editVacationEndDate.setText(vacationEndDate);

        VacationDetailsAddExcursionBtn.setOnClickListener(new View.OnClickListener() { //Start the onClickListener for this button
            @Override
            public void onClick(View v) { //define the view
                Intent intent = new Intent(VacationDetails.this, ExcursionDetails.class); //The intent is to move from this view to the next
                intent.putExtra("vacationID", vacationID);
                intent.putExtra("vacationStartDate", editVacationStartDate.getText());
                intent.putExtra("vacationEndDate", editVacationEndDate.getText());
                startActivity(intent); //start intent on this click activity
            }
        });

        // VACATION DATE
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        startDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, monthOfYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabelStart();
            }
        };

        endDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, monthOfYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabelEnd();
            }
        };

        editVacationStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;
                String info = editVacationStartDate.getText().toString();
                if (info.equals("")) info = "12/01/2024";
                try {
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(VacationDetails.this, startDate, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        editVacationEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;
                String info = editVacationEndDate.getText().toString();
                if (info.equals("")) info = "12/01/2024";
                try {
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(VacationDetails.this, endDate, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

//EXCURSION RECYCLER VIEW FOR VACATION DETAILS
        RecyclerView recyclerView = findViewById(R.id.excursionrecyclerview);
        repository = new Repository(getApplication());
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        excursionAdapter.setExcursions(repository.getAllExcursions());
        List<Excursion> filteredExcursions = new ArrayList<>();
        for (Excursion e : repository.getAllExcursions()) {
            if (e.getVacationID() == vacationID) filteredExcursions.add(e);
        }
        excursionAdapter.setExcursions(filteredExcursions);
    }
    private void updateLabelStart() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editVacationStartDate.setText(sdf.format(myCalendarStart.getTime()));
    }

    private void updateLabelEnd() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editVacationEndDate.setText(sdf.format(myCalendarStart.getTime()));

    }

    //MENU -- menu_vacation_list.xml
    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //This ensures the menu shows up on this page
        getMenuInflater().inflate(R.menu.menu_vacation_list, menu);
        return true;
    }


}