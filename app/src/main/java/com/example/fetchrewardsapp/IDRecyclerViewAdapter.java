package com.example.fetchrewardsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class IDRecyclerViewAdapter extends RecyclerView.Adapter<IDRecyclerViewAdapter.ViewHolder> {
    private ArrayList<String> listIDs;
    private Context context;
    private OnIDRecyclerViewClickListener onIDRecyclerViewClickListener;

    // Constructor to initialize the adapter
    public IDRecyclerViewAdapter(Context context, ArrayList<String> listIDs, OnIDRecyclerViewClickListener onIDRecyclerViewClickListener) {
        this.context = context;
        this.listIDs = listIDs;
        this.onIDRecyclerViewClickListener = onIDRecyclerViewClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each list item
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.id_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Bind data to each ViewHolder
        holder.textView.setText(listIDs.get(position));
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // When the button is clicked, invoke the callback method with the adapter position
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onIDRecyclerViewClickListener.OnClick(adapterPosition);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        // Return the size of the data list
        return listIDs.size();
    }

    // ViewHolder class to hold the views of each list item
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        Button button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize views
            textView = itemView.findViewById(R.id.list_id_name);
            button = itemView.findViewById(R.id.viewIDs);
        }
    }

    // Interface to handle item click events
    public interface OnIDRecyclerViewClickListener {
        void OnClick(int pos);
    }
}
