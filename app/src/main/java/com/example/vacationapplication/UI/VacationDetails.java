package com.example.vacationapplication.UI;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vacationapplication.R;
import com.example.vacationapplication.database.Repository;
import com.example.vacationapplication.entities.Excursion;
import com.example.vacationapplication.entities.Vacation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class VacationDetails extends AppCompatActivity {

    String location;
    String hotel;
    String startDate;
    String endDate;
    int vacationID;
    EditText editLocation;
    EditText editHotel;
    TextView editStart;
    TextView editEnd;
    Repository repository;
    Vacation currentVacation;
    int numVacations;
    DatePickerDialog.OnDateSetListener startingDate;
    DatePickerDialog.OnDateSetListener endingDate;
    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();

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
        editStart = findViewById(R.id.vacationstart);
        editEnd = findViewById(R.id.vacationend);
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
                intent.putExtra("vacationId", vacationID);
                startActivity(intent);
            }
        });

        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;
                String info = editStart.getText().toString();
                if(info.equals("")) info = "06/01/24";
                try{
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(VacationDetails.this, startingDate, myCalendarStart.get(Calendar.YEAR), myCalendarStart
                        .get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        editEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;
                String info = editStart.getText().toString();
                if(info.equals("")) info = "06/01/24";
                try{
                    myCalendarEnd.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(VacationDetails.this, endingDate, myCalendarEnd.get(Calendar.YEAR), myCalendarEnd
                        .get(Calendar.MONTH), myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        startingDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, month);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelStart();
            }
        };

        endingDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendarEnd.set(Calendar.YEAR, year);
                myCalendarEnd.set(Calendar.MONTH, month);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelEnd();
            }
        };

        RecyclerView recyclerView = findViewById(R.id.excursionrecyclerview);
        repository = new Repository(getApplication());
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Excursion> filteredExcursions = new ArrayList<>();
        for (Excursion e : repository.getAllExcursions()) {
            if (e.getVacationId() == vacationID) filteredExcursions.add(e);
        }
        excursionAdapter.setExcursions(filteredExcursions);
    }

    public void updateLabelStart() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editStart.setText(sdf.format(myCalendarStart.getTime()));
    }

    public void updateLabelEnd() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editEnd.setText(sdf.format(myCalendarEnd.getTime()));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacationdetails,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        Vacation vacation;

        try{
            if(item.getItemId()==R.id.vacationsave){
               Date vacationStart = sdf.parse(editStart.getText().toString());
               Date vacationEnd = sdf.parse(editEnd.getText().toString());

                if (vacationEnd.before(vacationStart)) {
                    Toast.makeText(VacationDetails.this, "Vacation ending date must be after the vacation start date!", Toast.LENGTH_LONG).show();
                    return false;
                }

                if (vacationID == -1) {
                    if (repository.getmAllVacations().isEmpty()) vacationID = 1;
                    else
                        vacationID = repository.getmAllVacations().get(repository.getmAllVacations().size() - 1).getVacationId() + 1;
                    vacation = new Vacation(vacationID, editLocation.getText().toString(), editHotel.getText().toString(), editStart.getText().toString(), editEnd.getText().toString());
                    repository.insert(vacation);
                    this.finish();
                } else {
                    vacation = new Vacation(vacationID, editLocation.getText().toString(), editHotel.getText().toString(), editStart.getText().toString(), editEnd.getText().toString());
                    repository.update(vacation);
                    this.finish();
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (item.getItemId()==R.id.share) {
            Intent sentIntent = new Intent();
            sentIntent.setAction(Intent.ACTION_SEND);
            sentIntent.putExtra(Intent.EXTRA_TITLE,editLocation.getText().toString());
            sentIntent.putExtra(Intent.EXTRA_TEXT, "Location: " + editLocation.getText().toString() + "\n" + "Hotel: " + editHotel.getText().toString() + "\n" +
                    "Start date: " + editStart.getText().toString() + "\n" + "End date: " + editEnd.getText().toString());
            sentIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sentIntent,null);
            startActivity(shareIntent);
            return true;
        }

        if (item.getItemId()==R.id.notifystart) {
            String dateFromScreen = editStart.getText().toString();
            Date myDate = null;
            try{
                myDate = sdf.parse(dateFromScreen);

            } catch (ParseException e) {
                e.printStackTrace();
            }

            Long trigger = myDate.getTime();
            Intent intent = new Intent(VacationDetails.this, MyReceiver.class);
            intent.putExtra("key", "Your vacation to " + editLocation.getText().toString() + " starts today, " + editStart.getText().toString() + "!");
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            PendingIntent sender = PendingIntent.getBroadcast(VacationDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_MUTABLE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, myDate.getTime(), sender);

            return true;
        }

        if(item.getItemId()==R.id.notifyend) {
            String dateFromScreen = editEnd.getText().toString();
            Date myDate = null;
            try{
                myDate = sdf.parse(dateFromScreen);

            } catch (ParseException e) {
                e.printStackTrace();
            }

            Long trigger = myDate.getTime();
            Intent intent = new Intent(VacationDetails.this, MyReceiver.class);
            intent.putExtra("key", "Your vacation to " + editLocation.getText().toString() + " ends today, " + editEnd.getText().toString() + "!");
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            PendingIntent sender = PendingIntent.getBroadcast(VacationDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_MUTABLE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, myDate.getTime(), sender);

            return true;
        }

        if(item.getItemId()==R.id.vacationdelete){
            for(Vacation vacay:repository.getmAllVacations()){
                if(vacay.getVacationId()==vacationID)currentVacation=vacay;
            }
            numVacations = 0;
            for(Excursion excursion: repository.getAllExcursions()){
                if(excursion.getVacationId()==vacationID)++numVacations;
            }
            if(numVacations==0){
                repository.delete(currentVacation);
                Toast.makeText(VacationDetails.this,currentVacation.getVacationLocation() + " was deleted",Toast.LENGTH_LONG).show();
                VacationDetails.this.finish();
            }
            else{
                Toast.makeText(VacationDetails.this, "Can't delete a vacation with excursions",Toast.LENGTH_LONG).show();
            }
        }
        return true;
    }
    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.excursionrecyclerview);
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Excursion> filteredExcursions = new ArrayList<>();
        for (Excursion e : repository.getAllExcursions()) {
            if (e.getVacationId() == vacationID) filteredExcursions.add(e);
        }
        excursionAdapter.setExcursions(filteredExcursions);
    }

}