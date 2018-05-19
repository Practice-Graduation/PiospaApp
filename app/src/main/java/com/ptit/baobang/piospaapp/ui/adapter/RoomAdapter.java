package com.ptit.baobang.piospaapp.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.model.Room;
import com.ptit.baobang.piospaapp.ui.listener.OnItemClickListener;

import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomHolder> {

    private Context mContext;
    private List<Room> mRooms;
    private int mSelectedPosition = -1;

    public RoomAdapter(Context mContext, List<Room> mRooms) {
        this.mContext = mContext;
        this.mRooms = mRooms;
    }

    @NonNull
    @Override
    public RoomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_room, parent, false);
        return new RoomHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomHolder holder, int position) {
        holder.bindView(holder, mRooms.get(position), position);
    }

    public int getSelectedPosition() {
        return mSelectedPosition;
    }

    @Override
    public int getItemCount() {
        return mRooms.size();
    }

    public void setSelectedView(View selectedView) {
//           selectedView.setSelected(true);
    }

    public class RoomHolder extends RecyclerView.ViewHolder {

        TextView txtRoomName;

        public RoomHolder(View itemView) {
            super(itemView);
            txtRoomName = itemView.findViewById(R.id.txtRoomName);
            addEvents(itemView);
        }

        private void addEvents(View itemView) {

        }

        public void bindView(RoomHolder holder, Room room, int position) {
            txtRoomName.setText(room.getRoomName());

            itemView.setOnClickListener(v -> {
                Toast.makeText(mContext, "Click", Toast.LENGTH_SHORT).show();
                mSelectedPosition = getAdapterPosition();
                setSelectedView(v);
                if(mListener != null){
                    mListener.onItemSelected(getSelectedPosition());
                }
            });

        }
    }

    private OnItemClickListener mListener;
    public void setOnItemClick(OnItemClickListener callBack){
        this.mListener = callBack;
    }
}
