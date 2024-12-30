package com.example.myvacation.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myvacation.Database.Repository;
import com.example.myvacation.Entities.Vacation;
import com.example.myvacation.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class VacationList extends AppCompatActivity {
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_list);
        FloatingActionButton addVacationBtn=findViewById(R.id.addVacationBtn); //Define the Floating Action Button
        addVacationBtn.setOnClickListener(new View.OnClickListener() { //Start the onClickListener for this button
            @Override
            public void onClick(View v) { //define the view
                Intent intent =new Intent(VacationList.this,VacationDetails.class); //The intent is to move from this view to the next
                startActivity(intent); //start intent on this click activity
            }
        });
        //Notifying 'onCreate' that there is a RecyclerView on the page
        RecyclerView recyclerView=findViewById(R.id.vacationlistrecyclerview);
        repository=new Repository(getApplication()); //access the repository
        List<Vacation> allVacations=repository.getAllVacations(); //to get a list of all the vacations
        final VacationAdapter vacationAdapter=new VacationAdapter(this); //calling adapter
        recyclerView.setAdapter(vacationAdapter);// and setting the adapter on the recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vacationAdapter.setVacations(allVacations); //using the method created in the adapter to set the vacation to show changing vacations
    }

    @Override
    protected void onResume(){
        super.onResume();
        List<Vacation>allVacations=repository.getAllVacations();
        RecyclerView recyclerView=findViewById(R.id.vacationlistrecyclerview);
        final VacationAdapter vacationAdapter = new VacationAdapter(this);
        recyclerView.setAdapter(vacationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vacationAdapter.setVacations((allVacations));
    }

}
