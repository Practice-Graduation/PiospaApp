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
import com.ptit.baobang.piospaapp.data.model.Room;
import com.ptit.baobang.piospaapp.ui.listener.OnItemClickListener;
import com.ptit.baobang.piospaapp.utils.VNCharacterUtils;

import java.util.ArrayList;
import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ProvinceHolder> implements Filterable{

    private Context mContext;
    private List<Room> mItems;
    private List<Room> mFilterItems;
    private OnItemClickListener mListener;
    private Room mRoomSelected = null;

    public RoomAdapter(Context mContext, List<Room> mItems) {
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
        View view = inflater.inflate(R.layout.item_room, parent, false);
        return new ProvinceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProvinceHolder holder, int position) {
            holder.bindView(mFilterItems.get(position));
            if(mRoomSelected != null &&
                    mRoomSelected.getRoomId() == mFilterItems.get(position).getRoomId()){
                holder.setChecked(true);
            }else{
                holder.setChecked(false);
            }
    }

    @Override
    public int getItemCount() {
        return mFilterItems.size();
    }

    public void setmRoomSelected(Room mRoomSelected) {
        this.mRoomSelected = mRoomSelected;
    }

    @Override
    public Filter getFilter() {
        return new RoomFilter();
    }

    public class ProvinceHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtServing;
        ImageView imgStatus;

        public ProvinceHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            imgStatus = itemView.findViewById(R.id.imgStatus);
            txtServing = itemView.findViewById(R.id.txtServing);
            itemView.setOnClickListener(v -> {
                if(mListener != null){
                    mListener.onItemSelected(getAdapterPosition());
                    mRoomSelected = mFilterItems.get(getAdapterPosition());
                    notifyDataSetChanged();
                }
            });
        }

        public void bindView(Room room) {
            txtName.setText(room.getRoomName());
            txtServing.setText("Còn trống " + (room.getRoomLimit() - room.getServing()));
        }
        public void setChecked(boolean checked){
            if(checked){
                imgStatus.setVisibility(View.VISIBLE);
            }else{
                imgStatus.setVisibility(View.GONE);
            }
        }
    }

    private class RoomFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filterSeq = constraint.toString().toLowerCase();
            if (filterSeq.length() > 0) {
                ArrayList<Room> filter = new ArrayList<>();
                for (Room room : mItems) {
                    // the filtering itself:
                    if (checkContaintKey(room, filterSeq))
                        filter.add(room);
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

        private boolean checkContaintKey(Room room, String filterSeq) {
            if(room.getRoomName().trim().toLowerCase().contains(filterSeq.trim())){
                return true;
            }

            String noSign = VNCharacterUtils.removeAccent(room.getRoomName()).toLowerCase().trim();
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
