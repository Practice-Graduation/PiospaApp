package com.ptit.baobang.piospaapp.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.List;

import butterknife.BindView;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceHolder>{

    private Context mContext;
    private List<ServicePrice> mServicePrices;
    private OnItemClickListener mListener;

    public ServiceAdapter(Context mContext, List<ServicePrice> mServicePrices){
        this.mContext = mContext;
        this.mServicePrices = mServicePrices;
    }

    @NonNull
    @Override
    public ServiceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_product, parent, false);
        return new ServiceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceHolder holder, int position) {
        holder.binView(mServicePrices.get(position));
    }

    @Override
    public int getItemCount() {
        return mServicePrices.size();
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
            itemView.getLayoutParams().width = ScreenUtils.getScreenWidth(mContext) / AppConstants.SPAN_COUNT;
            img = itemView.findViewById(R.id.img);
            txtName = itemView.findViewById(R.id.txtName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            itemView.setOnClickListener(v -> {
                if(mListener != null){
                    mListener.onItemSelected(getAdapterPosition());
                }
            });
        }

        void binView(ServicePrice servicePrice) {

            String image = "", name = "", price = "";

            if(servicePrice.getService() != null){
                Service service = servicePrice.getService();
                image = service.getImage();
                name = service.getServiceName();
                price = CommonUtils.formatToCurrency(servicePrice.getRetailPrice());
            }else if(servicePrice.getServicePackage() != null){
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
}
