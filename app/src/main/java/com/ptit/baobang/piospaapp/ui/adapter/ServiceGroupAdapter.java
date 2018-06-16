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
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;
import com.ptit.baobang.piospaapp.ui.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ServiceGroupAdapter<P extends BasePresenter> extends RecyclerView.Adapter<ServiceGroupAdapter.ServiceGroupHolder> {
    private static final String TAG = "ProductGroupAdapter";
    private Context mContext;
    private List<ServiceGroup> mServiceGroups;
    private APIService mApiService;
    private P mPresenter;

    public ServiceGroupAdapter(Context mContext, List<ServiceGroup> mServiceGroups, P mPresenter, APIService mApiService) {
        this.mContext = mContext;
        this.mServiceGroups = mServiceGroups;
        this.mApiService = mApiService;
        this.mPresenter = mPresenter;
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
    }

    @Override
    public int getItemCount() {
        return mServiceGroups.size();
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
            rvGroupProduct = itemView.findViewById(R.id.rvProducts);
            txtMore = itemView.findViewById(R.id.txtMore);
            servicePrices = new ArrayList<>();
            adapter = new ServiceAdapter(mContext, servicePrices);
            rvGroupProduct.setLayoutManager(new LinearLayoutManager(mContext,
                    LinearLayoutManager.HORIZONTAL, false));
            rvGroupProduct.setAdapter(adapter);
        }

        void binView(ServiceGroup serviceGroup) {
            txtGroupName.setText(serviceGroup.getServiceGroupName());


            mPresenter.getCompositeDisposable().add(
                    mApiService.getServicePriceByGroupId(serviceGroup.getServiceGroupId())
                            .subscribeOn(Schedulers.computation())
                            .observeOn(AndroidSchedulers.mainThread())
                            .unsubscribeOn(Schedulers.io())
                            .subscribe(this::hanleRespone, this::handleError)
            );

            adapter.setOnClickListener(position -> {
                mItemSelected.itemSelected(servicePrices.get(position));
            });
            txtMore.setOnClickListener(v -> {
                if (mMoreListener != null) {
                    mMoreListener.onItemSelected(getAdapterPosition());
                }
            });
        }

        private void handleError(Throwable throwable) {

        }

        private void hanleRespone(EndPoint<List<ServicePrice>> listEndPoint) {
            servicePrices.clear();
            List<ServicePrice> list = listEndPoint.getData();
            servicePrices.addAll(list);
            adapter.notifyDataSetChanged();
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
