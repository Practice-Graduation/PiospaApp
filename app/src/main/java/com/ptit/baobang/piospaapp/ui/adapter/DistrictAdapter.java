package com.ptit.baobang.piospaapp.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.model.District;
import com.ptit.baobang.piospaapp.ui.listener.OnItemClickListener;
import com.ptit.baobang.piospaapp.utils.VNCharacterUtils;

import java.util.ArrayList;
import java.util.List;

public class DistrictAdapter extends RecyclerView.Adapter<DistrictAdapter.DistrictHolder> implements Filterable{

    private Context mContext;
    private List<District> mItems;
    private List<District> mFilterItems;
    private OnItemClickListener mListener;
    private District mDistrictSelected = null;

    public DistrictAdapter(Context mContext, List<District> mItems) {
       this.mContext = mContext;
       this.mFilterItems = mItems;
       this.mItems = new ArrayList<>(mFilterItems);
    }

    public void setmListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public DistrictHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_address, parent, false);
        return new DistrictHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DistrictHolder holder, int position) {
            holder.bindView(mFilterItems.get(position));
            if(mDistrictSelected != null &&
                    mDistrictSelected.getDistrictId() == mFilterItems.get(position).getDistrictId()){
                holder.setChecked(true);
            }else{
                holder.setChecked(false);
            }
    }

    @Override
    public int getItemCount() {
        return mFilterItems.size();
    }

    public void setmDistrictSelected(District mDistrictSelected) {
        this.mDistrictSelected = mDistrictSelected;
    }

    @Override
    public Filter getFilter() {
        return new DistrictFilter();
    }

    public class DistrictHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        ImageView imgStatus;
        public DistrictHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            imgStatus = itemView.findViewById(R.id.imgStatus);
            itemView.setOnClickListener(v -> {
                if(mListener != null){
                    mListener.onItemSelected(getAdapterPosition());
                    mDistrictSelected = mFilterItems.get(getAdapterPosition());
                }
            });
        }

        public void bindView(District district) {
            txtName.setText(district.getType() + " " + district.getName());
        }
        public void setChecked(boolean checked){
            if(checked){
                imgStatus.setVisibility(View.VISIBLE);
            }else{
                imgStatus.setVisibility(View.GONE);
            }

        }
    }

    private class DistrictFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filterSeq = constraint.toString().toLowerCase();
            if (filterSeq.length() > 0) {
                ArrayList<District> filter = new ArrayList<>();
                for (District district : mItems) {
                    // the filtering itself:
                    if (checkContaintKey(district, filterSeq))
                        filter.add(district);
                }
                mFilterItems.clear();
                mFilterItems.addAll(filter);

            } else {
                // add all objects
                mFilterItems.clear();
                mFilterItems.addAll(mItems);
            }
            FilterResults result = new FilterResults();
            result.values = mFilterItems;
            result.count = mFilterItems.size();
            return result;
        }
        private boolean checkContaintKey(District district, String filterSeq) {
            if(district.getName().trim().toLowerCase().contains(filterSeq.trim())){
                return true;
            }

            String noSign = VNCharacterUtils.removeAccent(district.getName()).toLowerCase().trim();
            String filNoSign = VNCharacterUtils.removeAccent(filterSeq).toLowerCase().trim();
            if(noSign.contains(filNoSign)){
                return true;
            }

            return false;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            notifyDataSetChanged();
        }
    }

}
