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
    public boolean onOptionsItemSelected(MenuItem item) {
        //SAVING A VACATION
        if (item.getItemId() == R.id.SaveVacationMenu) {

            String startDateInput = editVacationStartDate.getText().toString();
            String endDateInput = editVacationEndDate.getText().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
            try {
                Date startDate = sdf.parse(startDateInput);
                Date endDate = sdf.parse(endDateInput);

                // Validate that the end date is after the start date
                if (endDate != null && startDate != null && !endDate.after(startDate)) {
                    Toast.makeText(this, "End date must be after start date.", Toast.LENGTH_LONG).show();
                    return true; // Prevent saving if validation fails
                }
            } catch (ParseException e) {
                e.printStackTrace();
                Toast.makeText(this, "Invalid date format.", Toast.LENGTH_LONG).show();
                return true; // Prevent saving if dates are invalid
            }

            if (editVacationTitle.getText().toString().isEmpty()){ //Vacation Title Validation
                Toast.makeText(this, "Please enter a Vacation Title", Toast.LENGTH_LONG).show();
                return true; // Prevent saving if validation fails
            }

            if (editVacationHotelName.getText().toString().isEmpty()){ //Hotel Name Validation
                Toast.makeText(this, "Please enter a Hotel Name", Toast.LENGTH_LONG).show();
                return true; // Prevent saving if validation fails
            }

            Vacation vacation;
            if (vacationID == -1) {
                if (repository.getAllVacations().isEmpty()) vacationID = 1;
                else
                    vacationID = repository.getAllVacations().get(repository.getAllVacations().size() - 1).getVacationID() + 1;
                vacation = new Vacation(vacationID,
                        editVacationTitle.getText().toString(),
                        editVacationHotelName.getText().toString(),
                        editVacationStartDate.getText().toString(),
                        editVacationEndDate.getText().toString());
                repository.add(vacation);
                this.finish();
            } else {
                vacation = new Vacation(vacationID,
                        editVacationTitle.getText().toString(),
                        editVacationHotelName.getText().toString(),
                        editVacationStartDate.getText().toString(),
                        editVacationEndDate.getText().toString());
                repository.update(vacation);
                this.finish();
            }
        }
        if (item.getItemId() == R.id.DeleteVacationMenu) { //DELETE VACATION - Cant Delete with Excursions
            for (Vacation vacation : repository.getAllVacations()) {
                if (vacation.getVacationID() == vacationID) currentVacation = vacation;
            }
            numExcursions = 0;
            for (Excursion excursion : repository.getAllExcursions()) {
                if (excursion.getVacationID() == vacationID) ++numExcursions;
            }
            if (numExcursions == 0) {
                repository.delete(currentVacation);
                Toast.makeText(VacationDetails.this, currentVacation.getVacationTitle() + " was deleted", Toast.LENGTH_LONG).show();
                VacationDetails.this.finish();
            } else {
                Toast.makeText(VacationDetails.this, "Cant delete a Vacation with Excursions", Toast.LENGTH_LONG).show();
            }
            return true;
        }
        if (item.getItemId() == R.id.ShareVacationMenu) { //SHARE VACATION
            String hotelName = editVacationHotelName.getText().toString();
            String vacationStartDate = editVacationStartDate.getText().toString();
            String vacationEndDate = editVacationEndDate.getText().toString();

            List<Excursion> excursions = new ArrayList<>();
            for (Excursion excursion : repository.getAllExcursions()) {
                if (excursion.getVacationID() == vacationID) {
                    excursions.add(excursion);
                }
            }
            StringBuilder excursionDetails = new StringBuilder();
            if (!excursions.isEmpty()) {
                excursionDetails.append("\nExcursions:\n");
                for (Excursion excursion : excursions) {
                    excursionDetails.append(" - ")
                            .append(excursion.getExcursionName())
                            .append(" on ")
                            .append(excursion.getExcursionDate())
                            .append("\n");
                }
            } else {
                excursionDetails.append("\nNo excursions planned.");
            }

            String extraText = "Hotel Name: " + hotelName +
                    " \n Start Date: " + vacationStartDate +
                    " \n End Date: " + vacationEndDate + excursionDetails;
            Intent sentIntent = new Intent();
            sentIntent.setAction(Intent.ACTION_SEND);
            sentIntent.setType("text/plain");
            sentIntent.putExtra(Intent.EXTRA_TITLE, editVacationTitle.getText().toString());
            sentIntent.putExtra(Intent.EXTRA_TEXT, extraText);
            Intent shareIntent = Intent.createChooser(sentIntent, null);
            startActivity(shareIntent);
            return true;
        }
        if (item.getItemId() == R.id.AlertVacationMenu) { //ALERT VACATION
            String startDateFromScreen=editVacationStartDate.getText().toString();
            String endDateFromScreen=editVacationEndDate.getText().toString();
            String myFormat = "MM/dd/yyyy";
            SimpleDateFormat sdf= new SimpleDateFormat(myFormat, Locale.US);
            String currentDate = sdf.format(new Date());
            Date myStartDate=null;
            Date myEndDate=null;
            if (currentDate.equals(startDateFromScreen)) {
                try {
                    myStartDate = sdf.parse(startDateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long trigger = myStartDate.getTime(); //sets up pending intent
                Intent intent = new Intent(VacationDetails.this, MyReceiver.class);
                String vacationName = editVacationTitle.getText().toString();
                String message = vacationName + " Starts Today!";
                intent.putExtra("start", message);
                PendingIntent sender = PendingIntent.getBroadcast(VacationDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);

            } if (currentDate.equals(endDateFromScreen)){
                try {
                    myEndDate = sdf.parse(endDateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long trigger = myEndDate.getTime(); //sets up pending intent
                Intent intent = new Intent(VacationDetails.this, MyReceiver.class);
                String vacationName = editVacationTitle.getText().toString();
                String message = vacationName + " Ends Today!";
                intent.putExtra("start", message);
                PendingIntent sender = PendingIntent.getBroadcast(VacationDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);

            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}