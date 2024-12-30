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

import androidx.appcompat.app.AppCompatActivity;

import com.example.myvacation.Database.Repository;
import com.example.myvacation.Entities.Excursion;
import com.example.myvacation.Entities.Vacation;
import com.example.myvacation.R;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ExcursionDetails extends AppCompatActivity {

    String name;
    int vacationID;
    int excursionID;
    EditText editName;
    String excursionDate;
    TextView editDate;
    Repository repository;
    Excursion currentExcursion;

    DatePickerDialog.OnDateSetListener startDate;
    final Calendar myCalendarStart = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excursion_details);
        //TODO: Add the Excursion Date to ExcursionDetails
        repository = new Repository(getApplication());

        name = getIntent().getStringExtra("excursionName");
        editName = findViewById(R.id.excursionName);
        editName.setText(name);

        excursionDate = getIntent().getStringExtra("excursionDate");
        editDate = findViewById(R.id.excursionDate);
        editDate.setText(excursionDate);

        vacationID = getIntent().getIntExtra("vacationID", -1);
        excursionID = getIntent().getIntExtra("excursionID", -1);


        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        ArrayList<Vacation> vacationArrayList = new ArrayList<>();
        vacationArrayList.addAll(repository.getAllVacations());

        ArrayList<Integer> vacationIdList = new ArrayList<>();
        for (Vacation vacation : vacationArrayList) {
            vacationIdList.add(vacation.getVacationID());
        }

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


        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Date date;
                String info = editDate.getText().toString();
                if (info.equals("")) info = "12/01/2024";
                try {
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(ExcursionDetails.this, startDate, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private Vacation getVacationById(int vacationID) {
        for (Vacation vacation : repository.getAllVacations()) {
            if (vacation.getVacationID() == vacationID) {
                return vacation;
            }
        }
        return null; // Return null if no matching Vacation is found
    }

    private boolean isValidDate(int vacationID, String excursionDate) {
        Vacation vacation = getVacationById(vacationID);
        if (vacation == null) {
            return false; // Vacation not found
        }
        String vacationStartDate = vacation.getVacationStartDate(); // Assumes Vacation entity has getStartDate()
        String vacationEndDate = vacation.getVacationEndDate(); // Assumes Vacation entity has getEndDate()

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        try {
            Date updatedExcursionDate = sdf.parse(excursionDate);
            Date vacationStart = sdf.parse(vacationStartDate);
            Date vacationEnd = sdf.parse(vacationEndDate);

            return excursionDate!= null &&
                    updatedExcursionDate.after(vacationStart) &&
                    updatedExcursionDate.before(vacationEnd);
        } catch (ParseException e) {
            e.printStackTrace();
            return false; // Invalid date parsing
        }
    }


    private void updateLabelStart() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editDate.setText(sdf.format(myCalendarStart.getTime()));
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_excursiondetails, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        // return true;
//                Intent intent=new Intent(PartDetails.this,MainActivity.class);
//                startActivity(intent);
//                return true;
        if (item.getItemId() == R.id.saveExcursion) { //SAVING EXCURSION
            if (!isValidDate(vacationID, editDate.getText().toString())) {
                Toast.makeText(this, "Invalid date. Please select a date within the vacation period.", Toast.LENGTH_LONG).show();
                return true; // Prevent saving if date is invalid
            }
            Excursion excursion;
            if (excursionID == -1) {
                if (repository.getAllExcursions().size() == 0)
                    excursionID = 1;
                else
                    excursionID = repository.getAllExcursions().get(repository.getAllExcursions().size() - 1).getExcursionID() + 1;
                excursion = new Excursion(editName.getText().toString(), editDate.getText().toString(), vacationID, excursionID);
                repository.add(excursion);
            } else {
                excursion = new Excursion(editName.getText().toString(), editDate.getText().toString(), vacationID, excursionID);
                repository.update(excursion);
            }

            return true;
        }

        if (item.getItemId() == R.id.deleteExcursion) { //DELETE EXCURSION
            for (Excursion excursion : repository.getAllExcursions()) {
                if (excursion.getExcursionID() == excursionID) currentExcursion = excursion;
            }
            repository.delete(currentExcursion);
            Toast.makeText(ExcursionDetails.this, currentExcursion.getExcursionName() + " was deleted", Toast.LENGTH_LONG).show();
            ExcursionDetails.this.finish();
            return true;

        }
        if (item.getItemId() == R.id.alertExcursion) { //CREATING ALERT FOR EXCURSION
            String dateFromScreen = editDate.getText().toString();
            String myFormat = "MM/dd/yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            Date myDate = null;
            try {
                myDate = sdf.parse(dateFromScreen);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Long trigger = myDate.getTime(); //sets up pending intent
            Intent intent = new Intent(ExcursionDetails.this, MyReceiver.class);
            String excursionName = editName.getText().toString();
            String message = excursionName + " Starts Today!";
            intent.putExtra("start", message);
            PendingIntent sender = PendingIntent.getBroadcast(ExcursionDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}