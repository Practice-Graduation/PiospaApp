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
import com.ptit.baobang.piospaapp.data.model.Ward;
import com.ptit.baobang.piospaapp.ui.listener.OnItemClickListener;

import java.util.List;

public class WardAdapter extends RecyclerView.Adapter<WardAdapter.WardHolder> {

    private Context mContext;
    private List<Ward> mItems;
    private OnItemClickListener mListener;
    private Ward mWardSelected = null;

    public WardAdapter(Context mContext, List<Ward> mItems) {
       this.mContext = mContext;
       this.mItems = mItems;
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
            holder.bindView(mItems.get(position));
            if(mWardSelected != null &&
                    mWardSelected.getWardid() == mItems.get(position).getWardid()){
                holder.setChecked(true);
            }else{
                holder.setChecked(false);
            }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setmWardSelected(Ward mWardSelected) {
        this.mWardSelected = mWardSelected;
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
                    mWardSelected = mItems.get(getAdapterPosition());
                }
            });
        }

        public void bindView(Ward Ward) {
            txtName.setText(Ward.getName());
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
