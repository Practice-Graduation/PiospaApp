package com.ptit.baobang.piospaapp.ui.activities.order;

import com.ptit.baobang.piospaapp.data.model.OrderStatus;
import com.ptit.baobang.piospaapp.ui.base.BaseView;

import java.util.List;

public interface IOrderView extends BaseView {
    void addTabLayout(List<OrderStatus> orderStatuses);
}
