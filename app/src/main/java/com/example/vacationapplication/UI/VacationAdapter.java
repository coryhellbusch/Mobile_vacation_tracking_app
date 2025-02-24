package com.example.vacationapplication.UI;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vacationapplication.R;
import com.example.vacationapplication.entities.Vacation;

import java.util.List;

public class VacationAdapter extends RecyclerView.Adapter<VacationAdapter.VacationViewHolder>{
    private List<Vacation> mVacations;
    private final Context context;
    private final LayoutInflater mInflater;

    public VacationAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;

    }


    public class VacationViewHolder extends RecyclerView.ViewHolder {
        private final TextView vacationItemView;

        public VacationViewHolder(@NonNull View itemView) {
            super(itemView);
            vacationItemView=itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(view -> {
                int position=getAdapterPosition();
                final Vacation current=mVacations.get(position);
                Intent intent=new Intent(context,VacationDetails.class);
                intent.putExtra("vacationId", current.getVacationId());
                intent.putExtra("vacationLocation", current.getVacationLocation());
                intent.putExtra("hotel", current.getHotel());
                intent.putExtra("vacationStart", current.getVacationStart());
                intent.putExtra("vacationEnd", current.getVacationEnd());
                context.startActivity(intent);
            });
        }
    }

    @NonNull
    @Override
    public VacationAdapter.VacationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=mInflater.inflate(R.layout.vacation_list_item,parent,false);
        return new VacationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VacationAdapter.VacationViewHolder holder, int position) {
        if(mVacations!=null){
            Vacation current=mVacations.get(position);
            String name = current.getVacationLocation();
            holder.vacationItemView.setText(name);
        } else {
            holder.vacationItemView.setText("No location selected.");
        }
    }

    @Override
    public int getItemCount() {
        if(mVacations!=null)
            return mVacations.size();
        else return 0;
    }

    public void setVacations(List<Vacation> vacations){
        mVacations=vacations;
        notifyDataSetChanged();
    }

}
