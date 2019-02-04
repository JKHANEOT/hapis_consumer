package com.hapis.customer.ui;

import android.os.Bundle;

import com.hapis.customer.R;
import com.hapis.customer.networking.json.JSONAdaptor;
import com.hapis.customer.ui.custom.dialogplus.OnClickListener;
import com.hapis.customer.ui.fragments.BookAppointmentFragment;
import com.hapis.customer.ui.models.appointments.AppointmentRequest;
import com.hapis.customer.ui.utils.AlertUtil;
import com.hapis.customer.ui.utils.DialogIconCodes;
import com.hapis.customer.ui.view.BaseView;
import com.hapis.customer.ui.view.BookAppointmentView;
import com.hapis.customer.ui.view.BookAppointmentViewModal;

public class BookAppointmentActivity extends BaseFragmentActivity<BookAppointmentViewModal> implements BookAppointmentView {

    public static final String APPOINTMENT_DETAILS_TAG = "APPOINTMENT_DETAILS";

    private AppointmentRequest appointmentRequest;

    public AppointmentRequest getAppointmentRequest() {
        return appointmentRequest;
    }

    @Override
    protected Class getViewModalClass() {
        return BookAppointmentViewModal.class;
    }

    @Override
    protected BaseView getViewImpl() {
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        if(getIntent() != null){
            String appointmentRequestJson = getIntent().getStringExtra(APPOINTMENT_DETAILS_TAG);
            if(appointmentRequestJson != null && appointmentRequestJson.length() > 0){
                try {
                    appointmentRequest = (AppointmentRequest)JSONAdaptor.fromJSON(appointmentRequestJson, AppointmentRequest.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        setUpNavigationDrawer(appointmentRequest == null ? getResources().getString(R.string.book_appointment) : getResources().getString(R.string.appointment_details), null, true, null);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new BookAppointmentFragment(), BookAppointmentFragment.TAG).addToBackStack(null).commit();
    }

    @Override
    public void showError(String errorMsg, OnClickListener onClickListener, String positiveLbl, String negativeLbl, String status) {
        if(onClickListener == null){
            AlertUtil.showAlert(BookAppointmentActivity.this, getResources().getString(R.string.book_appointment), errorMsg, DialogIconCodes.DIALOG_FAILED.getIconCode());
        }else{
            if(positiveLbl != null && positiveLbl.length() > 0 && (negativeLbl == null || (negativeLbl != null && negativeLbl.length() == 0)))
                AlertUtil.showAlert(BookAppointmentActivity.this, getResources().getString(R.string.book_appointment), errorMsg, positiveLbl, onClickListener, status);
            else if(positiveLbl != null && positiveLbl.length() > 0 && negativeLbl != null && negativeLbl.length() > 0)
                AlertUtil.showAlert(BookAppointmentActivity.this, getResources().getString(R.string.book_appointment), errorMsg, positiveLbl, negativeLbl, onClickListener, status);
            else
                AlertUtil.showAlert(BookAppointmentActivity.this, getResources().getString(R.string.book_appointment), errorMsg, DialogIconCodes.DIALOG_FAILED.getIconCode());
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }
}

