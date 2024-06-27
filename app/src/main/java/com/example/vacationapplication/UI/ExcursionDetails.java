package com.example.vacationapplication.UI;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.util.Locale;

public class ExcursionDetails extends AppCompatActivity {

    String name;
    int excursionId;
    int vacationId;

    String excursionDate;

    EditText editName;
    TextView editDate;
    Repository repository;
    Excursion currentExcursion;
    int numExcursions;
    DatePickerDialog.OnDateSetListener startDate;
    final Calendar myCalenderStart = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_excurstion_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        repository = new Repository(getApplication());
        name = getIntent().getStringExtra("excursionName");
        editName = findViewById(R.id.excursionName);
        editName.setText(name);
        excursionId = getIntent().getIntExtra("excursionId", -1);
        vacationId = getIntent().getIntExtra("vacationId", -1);
        excursionDate = getIntent().getStringExtra("excursionDate");
        editDate = findViewById(R.id.excursionDate);
        editDate.setText(excursionDate);

        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;
                String info = editDate.getText().toString();
                if (info.isEmpty()) info = "07/01/24";
                try {
                    myCalenderStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(ExcursionDetails.this, startDate, myCalenderStart.get(Calendar.YEAR), myCalenderStart
                        .get(Calendar.MONTH), myCalenderStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        startDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalenderStart.set(Calendar.YEAR, year);
                myCalenderStart.set(Calendar.MONTH, month);
                myCalenderStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelStart();
            }
        };
        Spinner spinner = findViewById(R.id.spinner);
        ArrayList<Vacation> vacationArrayList = new ArrayList<>();

        vacationArrayList.addAll(repository.getmAllVacations());

        ArrayAdapter<Vacation>vacationAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,vacationArrayList);
        spinner.setAdapter(vacationAdapter);
        spinner.setSelection(0);
    }

    private void updateLabelStart() {
            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            editDate.setText(sdf.format(myCalenderStart.getTime()));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_excursiondetails, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        Excursion excursion;

        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }

        if (item.getItemId() == R.id.excursionsave) {

            if (vacationId == -1) {
                Toast.makeText(this, "Vacation ID is not set", Toast.LENGTH_LONG).show();
                return false;
            }

            if (excursionId == -1) {
                if (repository.getAllExcursions().isEmpty()) excursionId = 1;
                else
                    excursionId = repository.getAllExcursions().get(repository.getAllExcursions().size() -1).getExcursionId() + 1;
                excursion = new Excursion(excursionId, editName.getText().toString(), editDate.getText().toString(), vacationId);
                repository.insert(excursion);
                this.finish();
            } else {
                excursion = new Excursion(excursionId, editName.getText().toString(), editDate.getText().toString(), vacationId);
                repository.update(excursion);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}