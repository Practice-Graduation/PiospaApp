package com.ptit.baobang.piospaapp.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.model.Service;
import com.ptit.baobang.piospaapp.data.model.ServicePackage;
import com.ptit.baobang.piospaapp.data.model.ServicePrice;
import com.ptit.baobang.piospaapp.ui.listener.OnItemClickListener;
import com.ptit.baobang.piospaapp.utils.AppConstants;
import com.ptit.baobang.piospaapp.utils.CommonUtils;
import com.ptit.baobang.piospaapp.utils.ScreenUtils;
import com.ptit.baobang.piospaapp.utils.VNCharacterUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceHolder> implements Filterable {

    private Context mContext;
    private List<ServicePrice> mFilterServicePrices;
    private List<ServicePrice> mServicePrices;
    private OnItemClickListener mListener;
    private int resource;

    public ServiceAdapter(Context mContext, List<ServicePrice> mServicePrices, int resource) {
        this.mContext = mContext;
        this.resource = resource;
        this.mFilterServicePrices = mServicePrices;
        mServicePrices = new ArrayList<>();
        mServicePrices.addAll(mFilterServicePrices);
    }

    @NonNull
    @Override
    public ServiceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(resource, parent, false);
        return new ServiceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceHolder holder, int position) {
        holder.binView(mFilterServicePrices.get(position));
    }

    @Override
    public int getItemCount() {
        return mFilterServicePrices.size();
    }

    @Override
    public Filter getFilter() {
        return new ServiceFilter();
    }

    public class ServiceHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img)
        ImageView img;
        @BindView(R.id.txtName)
        TextView txtName;
        @BindView(R.id.txtPrice)
        TextView txtPrice;

        ServiceHolder(View itemView) {
            super(itemView);
            itemView.getLayoutParams().width = (ScreenUtils.getScreenWidth(mContext) - AppConstants.SPAN_COUNT * AppConstants.MARGIN) / AppConstants.SPAN_COUNT;
            itemView.getLayoutParams().height =
                    itemView.getLayoutParams().height > ViewGroup.LayoutParams.WRAP_CONTENT ?
                            itemView.getLayoutParams().height : ViewGroup.LayoutParams.WRAP_CONTENT;
            img = itemView.findViewById(R.id.img);
            txtName = itemView.findViewById(R.id.txtName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            itemView.setOnClickListener(v -> {
                if (mListener != null) {
                    mListener.onItemSelected(getAdapterPosition());
                }
            });
        }

        void binView(ServicePrice servicePrice) {

            String image = "", name = "", price = "";

            if (servicePrice.getService() != null) {
                Service service = servicePrice.getService();
                image = service.getImage();
                name = service.getServiceName();
                price = CommonUtils.formatToCurrency(servicePrice.getRetailPrice());
            } else if (servicePrice.getServicePackage() != null) {
                ServicePackage servicePackage = servicePrice.getServicePackage();
                image = servicePackage.getImage();
                name = servicePackage.getServicePackageName();
                price = CommonUtils.formatToCurrency(servicePrice.getAllPrice());
            }

            RequestOptions options = new RequestOptions()
                    .placeholder(R.drawable.paceholder)
                    .error(R.drawable.error)
                    .fitCenter();
            Glide.with(mContext)
                    .load(image)
                    .apply(options)
                    .into(img);
            txtName.setText(name);
            txtPrice.setText(price);

        }
    }

    public void setOnClickListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

    private class ServiceFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filterSeq = constraint.toString().toLowerCase();
            Log.e("mCus", mServicePrices.size() + "");
            Log.e("mFil", mFilterServicePrices.size() + "");
            Log.e("filterSeq", filterSeq);
            if (filterSeq.length() > 0) {
                ArrayList<ServicePrice> filter = new ArrayList<>();
                for (ServicePrice servicePrice : mServicePrices) {
                    // the filtering itself:
                    if (checkContaintKey(servicePrice, filterSeq))
                        filter.add(servicePrice);
                }
                mFilterServicePrices.clear();
                mFilterServicePrices.addAll(filter);

            } else {
                // add all objects
                mFilterServicePrices.clear();
                mFilterServicePrices.addAll(mServicePrices);
            }
            FilterResults result = new FilterResults();
            result.values = mFilterServicePrices;
            result.count = mFilterServicePrices.size();
            Log.e("result", result.count + "");
            return result;
        }

        private boolean checkContaintKey(ServicePrice servicePrice, String filterSeq) {
            if (servicePrice.getService() != null) {
                if (servicePrice.getService().getServiceName().trim().toLowerCase().contains(filterSeq.trim())) {
                    return true;
                }
                if(VNCharacterUtils.removeAccent(servicePrice.getService().getServiceName()).trim().contains(VNCharacterUtils.removeAccent(filterSeq).trim())){
                    return true;
                }
            }
            if (servicePrice.getServicePackage() != null) {
                if (servicePrice.getServicePackage().getServicePackageName().trim().toLowerCase().contains(filterSeq.trim())) {
                    return true;
                }
                if(VNCharacterUtils.removeAccent(servicePrice.getServicePackage().getServicePackageName()).toLowerCase().trim().contains(VNCharacterUtils.removeAccent(filterSeq).toLowerCase().trim())){
                    return true;
                }
            }
            if (String.valueOf(servicePrice.getAllPrice()).trim().toLowerCase().contains(filterSeq.trim())) {
                return true;
            }
            if (String.valueOf(servicePrice.getRetailPrice()).trim().toLowerCase().contains(filterSeq.trim())) {
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
