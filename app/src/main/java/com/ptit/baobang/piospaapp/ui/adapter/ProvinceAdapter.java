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
import com.ptit.baobang.piospaapp.data.model.Province;
import com.ptit.baobang.piospaapp.ui.listener.OnItemClickListener;

import java.util.List;

public class ProvinceAdapter extends RecyclerView.Adapter<ProvinceAdapter.ProvinceHolder> {

    private Context mContext;
    private List<Province> mItems;
    private OnItemClickListener mListener;
    private Province mProvinceSelected = null;

    public ProvinceAdapter(Context mContext, List<Province> mItems) {
       this.mContext = mContext;
       this.mItems = mItems;
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
            holder.bindView(mItems.get(position));
            if(mProvinceSelected != null &&
                    mProvinceSelected.getProvinceid() == mItems.get(position).getProvinceid()){
                holder.setChecked(true);
            }else{
                holder.setChecked(false);
            }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setmProvinceSelected(Province mProvinceSelected) {
        this.mProvinceSelected = mProvinceSelected;
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
                    mProvinceSelected = mItems.get(getAdapterPosition());
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
}
