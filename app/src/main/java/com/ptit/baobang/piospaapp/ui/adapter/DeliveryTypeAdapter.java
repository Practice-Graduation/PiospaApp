package com.ptit.baobang.piospaapp.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.model.OrderDeliveryType;
import com.ptit.baobang.piospaapp.ui.listener.OnItemClickListener;
import com.ptit.baobang.piospaapp.utils.CommonUtils;

import java.util.List;

public class DeliveryTypeAdapter  extends RecyclerView.Adapter<DeliveryTypeAdapter.ViewHolder> {

    private int mSelectedItem = -1;
    private Context mContext;
    private List<OrderDeliveryType> mItems;
    private OnItemClickListener mListener;

    public DeliveryTypeAdapter(Context mContext, List<OrderDeliveryType> mItems) {
        this.mContext = mContext;
        this.mItems = mItems;
    }

    public void setmListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        final View view = inflater.inflate(R.layout.radio_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setText(mItems.get(position).getOrderDeliveryTypeName() + " - " + CommonUtils.formatToCurrency(mItems.get(position).getPrice()));
        holder.rbOption.setChecked(mSelectedItem == position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        RadioButton rbOption;
        TextView txtDescription;

        ViewHolder(View itemView) {
            super(itemView);
            rbOption = itemView.findViewById(R.id.rbOption);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            View.OnClickListener clickListener = v -> {
                mSelectedItem = getAdapterPosition();
                notifyDataSetChanged();
                if(mListener != null){
                    mListener.onItemSelected(mSelectedItem);
                }
            };
            itemView.setOnClickListener(clickListener);
            rbOption.setOnClickListener(clickListener);
        }

        public void setText(String s){
            txtDescription.setText(s);
        }

    }

}
