package com.ptit.baobang.piospaapp.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.CustomerServicePriceList;
import com.ptit.baobang.piospaapp.data.model.ServiceGroup;
import com.ptit.baobang.piospaapp.data.model.ServicePrice;
import com.ptit.baobang.piospaapp.data.network.api.APIService;
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.ui.base.BaseView;
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
    private ServiceAdapter[] mServiceAdapters;
    private CustomerServicePriceList[] mServiceLists;
    private APIService mApiService;
    private BaseView mView;
    public ServiceGroupAdapter(Context mContext, List<ServiceGroup> mServiceGroups, BaseView mView, APIService mApiService) {
        this.mContext = mContext;
        this.mServiceGroups = mServiceGroups;
        this.mApiService = mApiService;
        this.mView = mView;

        getServiceAdapters();
    }

    @NonNull
    @Override
    public ServiceGroupHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_recycle_product, parent, false);
        return new ServiceGroupHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceGroupAdapter.ServiceGroupHolder holder, int position) {
        holder.binView(mServiceGroups.get(position));
        Log.e("onBindViewHolder", mServiceGroups.get(position).getServiceGroupName());
        ServiceAdapter adapter = mServiceAdapters[position];
        if (adapter == null) {
            holder.itemView.setVisibility(View.GONE);
        }else{
            Log.e("onBindViewHolder", adapter.getItemCount() + "");
            if(adapter.getItemCount() > 0){
                holder.itemView.setVisibility(View.VISIBLE);
            }else{
                holder.itemView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mServiceGroups.size();
    }

    public void filter(String s) {
        for (ServiceAdapter serviceAdapter : mServiceAdapters) {
            serviceAdapter.getFilter().filter(s);
            notifyDataSetChanged();
        }
    }

    public void getServiceAdapters() {
        mServiceAdapters = new ServiceAdapter[mServiceGroups.size()];
        mServiceLists = new CustomerServicePriceList[mServiceGroups.size()];
        for (int i = 0; i < mServiceGroups.size(); i++) {
            int index = i;

            mApiService.getTopTenServicePriceByGroupId(mServiceGroups.get(index).getServiceGroupId()).enqueue(new Callback<EndPoint<List<ServicePrice>>>() {
                @Override
                public void onResponse(Call<EndPoint<List<ServicePrice>>> call, Response<EndPoint<List<ServicePrice>>> response) {
                    hanleRespone(index, response.body());
                }

                @Override
                public void onFailure(Call<EndPoint<List<ServicePrice>>> call, Throwable t) {
                    handleError(t);
                }

                private void handleError(Throwable throwable) {

                }

                private void hanleRespone(int index, EndPoint<List<ServicePrice>> listEndPoint) {
                    List<ServicePrice> servicePrices = new ArrayList<>();
                    servicePrices.addAll(listEndPoint.getData());
                    mServiceLists[index] = new CustomerServicePriceList(servicePrices);
                    ServiceAdapter adapter = new ServiceAdapter(mContext, servicePrices, R.layout.item_product);
                    mServiceAdapters[index] = adapter;
                    notifyDataSetChanged();
                    if(checkComplete(mServiceAdapters)){
                        mView.hideLoading();
                    }
                }
            });

        }
    }

    private boolean checkComplete(ServiceAdapter[] mServiceAdapters) {
        for(ServiceAdapter adapter : mServiceAdapters){
            if(adapter == null){
                return false;
            }
        }
        return true;
    }


    class ServiceGroupHolder extends RecyclerView.ViewHolder {

        TextView txtGroupName;
        TextView txtMore;
        RecyclerView rvGroupProduct;
        List<ServicePrice> servicePrices;
        ServiceAdapter adapter;

        ServiceGroupHolder(View itemView) {
            super(itemView);
            txtGroupName = itemView.findViewById(R.id.txtGroupName);
            txtMore = itemView.findViewById(R.id.txtMore);
            servicePrices = new ArrayList<>();
            adapter = new ServiceAdapter(mContext, servicePrices, R.layout.item_product);

        }

        void binView(ServiceGroup serviceGroup) {
            txtGroupName.setText(serviceGroup.getServiceGroupName());



            adapter.setOnClickListener(position -> {
                mItemSelected.itemSelected(servicePrices.get(position));
            });
            txtMore.setOnClickListener(v -> {
                if (mMoreListener != null) {
                    mMoreListener.onItemSelected(getAdapterPosition());
                }
            });

            if (mServiceAdapters.length > getAdapterPosition()) {
                rvGroupProduct = itemView.findViewById(R.id.rvProducts);
                ServiceAdapter adapter = mServiceAdapters[getAdapterPosition()];
                if (adapter != null) {
                    rvGroupProduct.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
                    rvGroupProduct.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    adapter.setOnClickListener(position -> mItemSelected.itemSelected(mServiceLists[getAdapterPosition()].getData().get(position)));
                }
            }
        }


    }

    private OnItemClickListener mMoreListener;
    private OnSelectedItem mItemSelected;

    public void setOnItemClickMoreListerner(OnItemClickListener callBack) {
        this.mMoreListener = callBack;
    }

    public void setmItemSelected(OnSelectedItem mItemSelected) {
        this.mItemSelected = mItemSelected;
    }

    public interface OnSelectedItem {
        void itemSelected(ServicePrice servicePrice);
    }
}
