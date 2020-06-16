package org.project.poolreservation.recyclerviews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.project.poolreservation.R;
import org.project.poolreservation.activities.DayOfWeekActivity;
import org.project.poolreservation.models.DayOfWeek;

import java.util.List;

public class DayOfWeekAdapter extends RecyclerView.Adapter<DayOfWeekAdapter.ViewHolder> {

    private List<DayOfWeek>items;
    private Context context;

    public DayOfWeekAdapter(List<DayOfWeek> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_of_week_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.day.setText(items.get(position).getDay());
        holder.date.setText(items.get(position).getDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((DayOfWeekActivity)context).showTimeOfDay(items.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView day,date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            day=itemView.findViewById(R.id.day);
            date=itemView.findViewById(R.id.date);

        }
    }
}
