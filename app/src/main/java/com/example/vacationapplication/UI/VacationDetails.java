package com.example.vacationapplication.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vacationapplication.R;
import com.example.vacationapplication.database.Repository;
import com.example.vacationapplication.entities.Vacation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class VacationDetails extends AppCompatActivity {

    String location;
    String hotel;
    String startDate;
    String endDate;
    int vacationID;
    EditText editLocation;
    EditText editHotel;
    EditText editStart;
    EditText editEnd;
    Repository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vacation_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        FloatingActionButton fab=findViewById(R.id.floatingActionButton2);
        editLocation = findViewById(R.id.locationtext);
        editHotel = findViewById(R.id.hoteltext);
        editStart = findViewById(R.id.starttext);
        editEnd = findViewById(R.id.endtext);
        vacationID = getIntent().getIntExtra("vacationId", -1);
        location = getIntent().getStringExtra("vacationLocation");
        hotel = getIntent().getStringExtra("hotel");
        startDate = getIntent().getStringExtra("vacationStart");
        endDate = getIntent().getStringExtra("vacationEnd");
        editLocation.setText(location);
        editHotel.setText(hotel);
        editStart.setText(startDate);
        editEnd.setText(endDate);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(VacationDetails.this, ExcursionDetails.class);
                startActivity(intent);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.excursionrecyclerview);
        repository = new Repository(getApplication());
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        excursionAdapter.setExcursions(repository.getAllExcursions());
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacationdetails,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.vacationsave){
            Vacation vacation;
            if(vacationID==-1){
                if(repository.getmAllVacations().isEmpty()) vacationID = 1;
                else vacationID = repository.getmAllVacations().get(repository.getmAllVacations().size() - 1).getVacationId() + 1;
                vacation = new Vacation(vacationID, editLocation.getText().toString(),editHotel.getText().toString(),editStart.getText().toString(),editEnd.getText().toString());
                repository.insert(vacation);
                this.finish();
            }
        else {
            vacation = new Vacation(vacationID, editLocation.getText().toString(),editHotel.getText().toString(),editStart.getText().toString(),editEnd.getText().toString());
            repository.update(vacation);
            this.finish();
            }
        }
        return true;
    }

}