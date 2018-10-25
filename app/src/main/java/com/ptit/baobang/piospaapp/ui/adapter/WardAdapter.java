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
import com.ptit.baobang.piospaapp.data.model.Ward;
import com.ptit.baobang.piospaapp.ui.listener.OnItemClickListener;
import com.ptit.baobang.piospaapp.utils.VNCharacterUtils;

import java.util.ArrayList;
import java.util.List;

public class WardAdapter extends RecyclerView.Adapter<WardAdapter.WardHolder> implements Filterable{

    private Context mContext;
    private List<Ward> mItems;
    private List<Ward> mFilterItems;
    private OnItemClickListener mListener;
    private Ward mWardSelected = null;

    public WardAdapter(Context mContext, List<Ward> mItems) {
       this.mContext = mContext;
       this.mFilterItems = mItems;
    }

    public void setmListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public WardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_address, parent, false);
        return new WardHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WardHolder holder, int position) {
            holder.bindView(mFilterItems.get(position));
            if(mWardSelected != null &&
                    mWardSelected.getWardId() == mFilterItems.get(position).getWardId()){
                holder.setChecked(true);
            }else{
                holder.setChecked(false);
            }
    }

    @Override
    public int getItemCount() {
        return mFilterItems.size();
    }

    public void setmWardSelected(Ward mWardSelected) {
        this.mWardSelected = mWardSelected;
    }

    @Override
    public Filter getFilter() {
        return new WardFilter();
    }

    public class WardHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        ImageView imgStatus;
        public WardHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            imgStatus = itemView.findViewById(R.id.imgStatus);
            itemView.setOnClickListener(v -> {
                if(mListener != null){
                    mListener.onItemSelected(getAdapterPosition());
                    mWardSelected = mFilterItems.get(getAdapterPosition());
                }
            });
        }

        public void bindView(Ward Ward) {
            txtName.setText(Ward.getType() + " " + Ward.getName());
        }
        public void setChecked(boolean checked){
            if(checked){
                imgStatus.setVisibility(View.VISIBLE);
            }else{
                imgStatus.setVisibility(View.GONE);
            }
        }
    }

    private class WardFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filterSeq = constraint.toString().toLowerCase();
            if (filterSeq.length() > 0) {
                ArrayList<Ward> filter = new ArrayList<>();
                for (Ward ward : mItems) {
                    // the filtering itself:
                    if (checkContaintKey(ward, filterSeq))
                        filter.add(ward);
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
        private boolean checkContaintKey(Ward ward, String filterSeq) {
            if(ward.getName().trim().toLowerCase().contains(filterSeq.trim())){
                return true;
            }

            String noSign = VNCharacterUtils.removeAccent(ward.getName()).toLowerCase().trim();
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
