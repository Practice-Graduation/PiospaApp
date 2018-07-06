package com.ptit.baobang.piospaapp.ui.activities.test;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.ui.base.BaseActivity;

import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class TestActivity extends BaseActivity implements View.OnClickListener, SendOrderRequest.SendOrderRequestOnResult {

    private EditText editFullName;
    private EditText editAmount;
    private EditText editEmail;
    private EditText editPhoneNumber;
    private EditText editAddress;
    private Button btnSendOrder;

    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        initView();
    }

    private void initView() {
        editFullName = (EditText) findViewById(R.id.activity_main_editFullName);
        editAmount = (EditText) findViewById(R.id.activity_main_editAmount);
        editEmail = (EditText) findViewById(R.id.activity_main_editEmail);
        editPhoneNumber = (EditText) findViewById(R.id.activity_main_editPhoneNumber);
        editAddress = (EditText) findViewById(R.id.activity_main_editAddress);
        btnSendOrder = (Button) findViewById(R.id.activity_main_btnSendOrder);

        scrollView = (ScrollView) findViewById(R.id.activity_main_scrollView);


        btnSendOrder.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.activity_main_btnSendOrder:
                String fullName = editFullName.getText().toString();
                String amount = editAmount.getText().toString();
                String email = editEmail.getText().toString();
                String phoneNumber = editPhoneNumber.getText().toString();
                String address = editAddress.getText().toString();

                if (fullName.equalsIgnoreCase("")) {
                    showMessage("Thong bao", "Loi ten hoa don", SweetAlertDialog.WARNING_TYPE);
                    return;
                }
                if (amount.equalsIgnoreCase("") || Integer.valueOf(amount) >= 2000) {
                    showMessage("Thong bao", "Loi so luong", SweetAlertDialog.WARNING_TYPE);
                    return;
                }
                if (email.equalsIgnoreCase("")) {
                    showMessage("Thong bao", "Loi email", SweetAlertDialog.WARNING_TYPE);
                    return;
                }

                if (phoneNumber.equalsIgnoreCase("")) {
                    showMessage("Thong bao", "Loi dien thoai", SweetAlertDialog.WARNING_TYPE);
                    return;
                }
                if (address.equalsIgnoreCase("")) {
                    showMessage("Thong bao", "Loi dia chi", SweetAlertDialog.WARNING_TYPE);
                    return;
                }

                showLoading("Loading");
                sendOrderObject(fullName, amount, email, phoneNumber, address);

                break;
        }
    }
    private void sendOrderObject(String fullName, String amount, String email, String phoneNumber, String address) {
        SendOrderBean sendOrderBean = new SendOrderBean();
        sendOrderBean.setFunc("sendOrder");
        sendOrderBean.setVersion("1.0");
        sendOrderBean.setMerchantID(Constant.MERCHANT_ID);
        sendOrderBean.setMerchantAccount("babangb5@gmail.com");
        sendOrderBean.setOrderCode("123456DEMO");
        sendOrderBean.setTotalAmount(Integer.valueOf(amount));
        sendOrderBean.setCurrency("vnd");
        sendOrderBean.setLanguage("vi");
        sendOrderBean.setReturnUrl(Constant.RETURN_URL);
        sendOrderBean.setCancelUrl(Constant.CANCEL_URL);
        sendOrderBean.setNotifyUrl(Constant.NOTIFY_URL);
        sendOrderBean.setBuyerFullName(fullName);
        sendOrderBean.setBuyerEmail(email);
        sendOrderBean.setBuyerMobile(phoneNumber);
        sendOrderBean.setBuyerAddress(address);

        String checksum = getChecksum(sendOrderBean);
        sendOrderBean.setChecksum(checksum);

        SendOrderRequest sendOrderRequest = new SendOrderRequest();
        sendOrderRequest.execute(getApplicationContext(), sendOrderBean);
        sendOrderRequest.getSendOrderRequestOnResult(this);

    }

    private String getChecksum(SendOrderBean sendOrderBean) {
        String stringSendOrder = sendOrderBean.getFunc() + "|" +
                sendOrderBean.getVersion() + "|" +
                sendOrderBean.getMerchantID() + "|" +
                sendOrderBean.getMerchantAccount() + "|" +
                sendOrderBean.getOrderCode() + "|" +
                sendOrderBean.getTotalAmount() + "|" +
                sendOrderBean.getCurrency() + "|" +
                sendOrderBean.getLanguage() + "|" +
                sendOrderBean.getReturnUrl() + "|" +
                sendOrderBean.getCancelUrl() + "|" +
                sendOrderBean.getNotifyUrl() + "|" +
                sendOrderBean.getBuyerFullName() + "|" +
                sendOrderBean.getBuyerEmail() + "|" +
                sendOrderBean.getBuyerMobile() + "|" +
                sendOrderBean.getBuyerAddress() + "|" +
                Constant.MERCHANT_PASSWORD;
        String checksum = Commons.md5(stringSendOrder);

        return checksum;
    }

    @Override
    public void onSendOrderRequestOnResult(boolean result, String data) {

        if (result == true) {
            try {
                JSONObject objResult = new JSONObject(data);
                String responseCode = objResult.getString("response_code");
                if (responseCode.equalsIgnoreCase("00")) {
                    hideLoading();
                    String tokenCode = objResult.getString("token_code");
                    String checkoutUrl = objResult.getString("checkout_url");

                    Log.e("TAg", checkoutUrl);
                    Log.e("TAg", tokenCode);

                } else {
                    hideLoading(Commons.getCodeError(this, responseCode), false);
                }
            } catch (Exception ex) {
                ex.fillInStackTrace();
            }
        }
    }
}
