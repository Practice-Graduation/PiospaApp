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
import com.ptit.baobang.piospaapp.data.model.Province;
import com.ptit.baobang.piospaapp.ui.listener.OnItemClickListener;
import com.ptit.baobang.piospaapp.utils.VNCharacterUtils;

import java.util.ArrayList;
import java.util.List;

public class ProvinceAdapter extends RecyclerView.Adapter<ProvinceAdapter.ProvinceHolder> implements Filterable{

    private Context mContext;
    private List<Province> mItems;
    private List<Province> mFilterItems;
    private OnItemClickListener mListener;
    private Province mProvinceSelected = null;

    public ProvinceAdapter(Context mContext, List<Province> mItems) {
       this.mContext = mContext;
       this.mFilterItems = mItems;
       this.mItems = new ArrayList<>(mItems);
    }

    public void setmListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ProvinceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_address, parent, false);
        return new ProvinceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProvinceHolder holder, int position) {
            holder.bindView(mFilterItems.get(position));
            if(mProvinceSelected != null &&
                    mProvinceSelected.getProvinceId() == mFilterItems.get(position).getProvinceId()){
                holder.setChecked(true);
            }else{
                holder.setChecked(false);
            }
    }

    @Override
    public int getItemCount() {
        return mFilterItems.size();
    }

    public void setmProvinceSelected(Province mProvinceSelected) {
        this.mProvinceSelected = mProvinceSelected;
    }

    @Override
    public Filter getFilter() {
        return new ProvinceFilter();
    }

    public class ProvinceHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        ImageView imgStatus;
        public ProvinceHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            imgStatus = itemView.findViewById(R.id.imgStatus);
            itemView.setOnClickListener(v -> {
                if(mListener != null){
                    mListener.onItemSelected(getAdapterPosition());
                    mProvinceSelected = mFilterItems.get(getAdapterPosition());
                    notifyDataSetChanged();
                }
            });
        }

        public void bindView(Province province) {
            txtName.setText(province.getName());
        }
        public void setChecked(boolean checked){
            if(checked){
                imgStatus.setVisibility(View.VISIBLE);
            }else{
                imgStatus.setVisibility(View.GONE);
            }
        }
    }

    private class ProvinceFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filterSeq = constraint.toString().toLowerCase();
            if (filterSeq.length() > 0) {
                ArrayList<Province> filter = new ArrayList<>();
                for (Province province : mItems) {
                    // the filtering itself:
                    if (checkContaintKey(province, filterSeq))
                        filter.add(province);
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

        private boolean checkContaintKey(Province province, String filterSeq) {
            if(province.getName().trim().toLowerCase().contains(filterSeq.trim())){
                return true;
            }

            String noSign = VNCharacterUtils.removeAccent(province.getName()).toLowerCase().trim();
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
