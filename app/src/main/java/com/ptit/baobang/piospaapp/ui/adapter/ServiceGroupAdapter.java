package com.ptit.baobang.piospaapp.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.model.ServiceGroup;
import com.ptit.baobang.piospaapp.data.model.ServicePrice;
import com.ptit.baobang.piospaapp.data.network.api.APIService;
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.ui.fragments.fragment_service.IServiceView;
import com.ptit.baobang.piospaapp.ui.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceGroupAdapter extends RecyclerView.Adapter<ServiceGroupAdapter.ServiceGroupHolder> {
    private static final String TAG = "ProductGroupAdapter";
    private Context mContext;
    private List<ServiceGroup> mServiceGroups;
    private APIService mApiService;
    private IServiceView mView;

    public ServiceGroupAdapter(Context mContext, List<ServiceGroup> mServiceGroups, APIService mApiService) {
        this.mContext = mContext;
        this.mServiceGroups = mServiceGroups;
        this.mApiService = mApiService;
    }

    @NonNull
    @Override
    public ServiceGroupHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_recycle_product, parent, false);
        return new ServiceGroupHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceGroupHolder holder, int position) {
        holder.binView(mServiceGroups.get(position));
    }

    @Override
    public int getItemCount() {
        return mServiceGroups.size();
    }
    public class ServiceGroupHolder extends RecyclerView.ViewHolder {

        TextView txtGroupName;
        TextView txtMore;
        RecyclerView rvGroupProduct;

        ServiceGroupHolder(View itemView) {
            super(itemView);
            txtGroupName = itemView.findViewById(R.id.txtGroupName);
            rvGroupProduct = itemView.findViewById(R.id.rvProducts);
            txtMore = itemView.findViewById(R.id.txtMore);
        }

        void binView(ServiceGroup serviceGroup) {
            txtGroupName.setText(serviceGroup.getServiceGroupName());
            List<ServicePrice> servicePrices = new ArrayList<>();
            ServiceAdapter adapter = new ServiceAdapter(mContext, servicePrices);
            rvGroupProduct.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            rvGroupProduct.setAdapter(adapter);
            mApiService.getServicePriceByGroupId(serviceGroup.getServiceGroupId()).enqueue(new Callback<EndPoint<List<ServicePrice>>>() {
                @Override
                public void onResponse(Call<EndPoint<List<ServicePrice>>> call, Response<EndPoint<List<ServicePrice>>> response) {
                    if (response.isSuccessful()) {
                        servicePrices.clear();
                        List<ServicePrice> list = response.body().getData();
                        servicePrices.addAll(list);
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<EndPoint<List<ServicePrice>>> call, Throwable t) {

                }
            });
            adapter.setOnClickListener(position -> {
                if(mView != null){
                    mView.openServiceDetailActivity(servicePrices.get(position).getServicePriceId());
                }
            });
            txtMore.setOnClickListener(v -> {
                if(mListener != null){
                    mListener.onItemSelected(getAdapterPosition());
                }
            });
        }
    }

    public void setServiceView(IServiceView mView) {
        this.mView = mView;
    }

    private OnItemClickListener mListener;

    public void setOnItemClickListerner(OnItemClickListener callBack){
        this.mListener = callBack;
    }
}
