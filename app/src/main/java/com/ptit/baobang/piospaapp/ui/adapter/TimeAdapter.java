package com.ptit.baobang.piospaapp.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.network.firebase.BookingTimeFB;
import com.ptit.baobang.piospaapp.ui.listener.OnItemClickListener;

import java.util.List;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.TimeHolder> {

    private Context mContext;
    private List<BookingTimeFB> mTimes;
    private OnItemClickListener mListener;

    public TimeAdapter(Context mContext, List<BookingTimeFB> mTimes) {
        this.mContext = mContext;
        this.mTimes = mTimes;
    }

    public void setmListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public TimeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_time, parent, false);
        return new TimeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeHolder holder, int position) {
        holder.bindView(holder.itemView, mTimes.get(position));
    }

    @Override
    public int getItemCount() {
        return mTimes.size();
    }

    public class TimeHolder extends RecyclerView.ViewHolder {

        TextView txtTime, txtStatus;
        Button btnBook;

        public TimeHolder(View itemView) {
            super(itemView);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            btnBook = itemView.findViewById(R.id.btnBookApointment);
        }

        public void bindView(View itemView, BookingTimeFB bookingTimeFB) {
            txtTime.setText(bookingTimeFB.getTimeStart());
            if(bookingTimeFB.isBooking()){
                txtStatus.setText("Đã được đặt");
                txtStatus.setTextColor(mContext.getResources().getColor(R.color.green));
                btnBook.setEnabled(false);
                btnBook.setBackgroundColor(mContext.getResources().getColor(R.color.gray));

            }else{
                txtStatus.setText("Có thể đặt hẹn");
                btnBook.setEnabled(true);
            }

            btnBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null){
                        mListener.onItemSelected(getAdapterPosition());
                    }
                }
            });
        }
    }
}
