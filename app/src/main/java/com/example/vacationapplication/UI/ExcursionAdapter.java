package com.example.vacationapplication.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vacationapplication.R;
import com.example.vacationapplication.entities.Excursion;
import com.example.vacationapplication.entities.Vacation;

import java.util.List;

public class ExcursionAdapter extends RecyclerView.Adapter<ExcursionAdapter.ExcursionViewHolder> {
    private List<Excursion> mExcursions;
    private final Context context;
    private final LayoutInflater mInflater;

    public ExcursionAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public class ExcursionViewHolder extends RecyclerView.ViewHolder {
        private final TextView excursionItemView;
        private final TextView excursionItemView2;

        public ExcursionViewHolder(@NonNull View itemView) {
            super(itemView);
            excursionItemView=itemView.findViewById(R.id.textView3);
            excursionItemView2=itemView.findViewById(R.id.textView4);
            itemView.setOnClickListener(view -> {
                int position=getAdapterPosition();
                final Excursion current=mExcursions.get(position);
                Intent intent=new Intent(context, ExcursionDetails.class);
                intent.putExtra("excursionId", current.getExcursionId());
                intent.putExtra("excursionName", current.getExcursionName());
                intent.putExtra("excursionDate", current.getExcursionDate());
                intent.putExtra("vacationId", current.getVacationId());
                context.startActivity(intent);
            });
        }
    }

    @Override
    public ExcursionAdapter.ExcursionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=mInflater.inflate(R.layout.excursion_list_item,parent,false);
        return new ExcursionAdapter.ExcursionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExcursionAdapter.ExcursionViewHolder holder, int position) {
        if(mExcursions!=null){
            Excursion current=mExcursions.get(position);
            String name = current.getExcursionName();
            int excurID = current.getExcursionId();
            holder.excursionItemView.setText(name);
            holder.excursionItemView2.setText(Integer.toString(excurID));
        } else {
            holder.excursionItemView.setText("No excursion selected.");
            holder.excursionItemView2.setText("No excursion ID");
        }
    }

    @Override
    public int getItemCount() {
        if(mExcursions != null)
            return mExcursions.size();
        else return 0;
    }

    public void setExcursions(List<Excursion> excursions){
        mExcursions=excursions;
        notifyDataSetChanged();
    }

}
