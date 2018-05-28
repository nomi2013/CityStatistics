package com.nk.citystatistics.ui.cities.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.nk.citystatistics.R;
import com.nk.citystatistics.db.model.CityInfo;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by Noman Khan on 29/05/18.
 */
public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder> {

    private List<CityInfo> infoList;
    private Context context;


    @Inject
    CityAdapter() {
        infoList = new ArrayList<>();
    }

    public class CityViewHolder extends RecyclerView.ViewHolder {

        private TextView tvCityName, tvState, tvPopulation;

        public ConstraintLayout viewBackground, viewForeground;

        private CityViewHolder(View itemView) {
            super(itemView);

            tvCityName = itemView.findViewById(R.id.tvCityName);
            tvState = itemView.findViewById(R.id.tvState);
            tvPopulation = itemView.findViewById(R.id.tvPopulation);

            viewBackground = itemView.findViewById(R.id.view_background);
            viewForeground = itemView.findViewById(R.id.view_foreground);
        }


    }

    @NonNull
    @Override
    public CityAdapter.CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.city_list_row, parent, false);
        return new CityViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CityAdapter.CityViewHolder holder, int position) {
        holder.tvCityName.setText(getCityName(position));
        holder.tvState.setText(getSate(position));
        holder.tvPopulation.setText(getCityPopulation(infoList.get(position).cityPopulation));
    }


    @Override
    public int getItemCount() {
        return infoList.size();
    }

    public void upadateAdapter(List<CityInfo> cityInfo, RecyclerView rcv) {
        infoList.clear();
        infoList.addAll(cityInfo);
        rcv.post(this::notifyDataSetChanged);
    }


    public void removeItem(int position, RecyclerView rcv) {
        infoList.remove(position);
        rcv.post(() -> notifyItemRemoved(position));
    }

    public void addItemAtPosition(CityInfo cityInfo, int position, RecyclerView rcv) {
        infoList.add(cityInfo);
        rcv.post(() -> notifyDataSetChanged());
    }


    public void restoreItem(CityInfo cityInfo, int position, RecyclerView rcv) {
        infoList.add(position, cityInfo);
        rcv.post(() -> notifyItemInserted(position));

    }

    @NonNull
    private String getSate(int position) {
        return context.getString(R.string.state_colon) + infoList.get(position)
                .state;
    }

    @NonNull
    private String getCityName(int position) {
        return context.getString(R.string.city_colon) + infoList.get(position)
                .cityName;
    }

    private String getCityPopulation(int population) {
        try {
            return String.valueOf(population);
        } catch (NumberFormatException e) {
            return "";
        } catch (Exception e) {
            return "";
        }
    }

}
