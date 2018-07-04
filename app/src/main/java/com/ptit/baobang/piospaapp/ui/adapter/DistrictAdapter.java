package com.ptit.baobang.piospaapp.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.model.District;
import com.ptit.baobang.piospaapp.ui.listener.OnItemClickListener;

import java.util.List;

public class DistrictAdapter extends RecyclerView.Adapter<DistrictAdapter.DistrictHolder> {

    private Context mContext;
    private List<District> mItems;
    private OnItemClickListener mListener;
    private District mDistrictSelected = null;

    public DistrictAdapter(Context mContext, List<District> mItems) {
       this.mContext = mContext;
       this.mItems = mItems;
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
            holder.bindView(mItems.get(position));
            if(mDistrictSelected != null &&
                    mDistrictSelected.getDistrictid() == mItems.get(position).getDistrictid()){
                holder.setChecked(true);
            }else{
                holder.setChecked(false);
            }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setmDistrictSelected(District mDistrictSelected) {
        this.mDistrictSelected = mDistrictSelected;
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
                    mDistrictSelected = mItems.get(getAdapterPosition());
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
}
