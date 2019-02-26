package com.hapis.customer.ui.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.hapis.customer.R;
import com.hapis.customer.ui.DashboardActivity;
import com.hapis.customer.ui.adapters.SelectRideAdapter;
import com.hapis.customer.ui.callback.BookRideInitDialogCallBack;
import com.hapis.customer.ui.custom.recyclerviewanimations.RecyclerviewClickListeners;
import com.hapis.customer.ui.models.appointments.DoctorDetails;
import com.hapis.customer.ui.models.enterprise.EnterpriseAddressRequest;
import com.hapis.customer.ui.models.enterprise.EnterpriseRequest;

public class AppointmentBookingDetailsDialogFrag extends DialogFragment {

    public static final String TAG = AppointmentBookingDetailsDialogFrag.class.getName();

    public static final String selectedDoctorDetails_TAG = "selectedDoctorDetails";
    public static final String selectedEnterpriseRequest_TAG = "selectedEnterpriseRequest";
    public static final String select_time_slot_TAG = "select_time_slot";
    public static final String appointmentDate_TAG = "appointmentDate";

    private DoctorDetails selectedDoctorDetails;
    private EnterpriseRequest selectedEnterpriseRequest;

    public DoctorDetails getSelectedDoctorDetails() {
        return selectedDoctorDetails;
    }

    public void setSelectedDoctorDetails(DoctorDetails selectedDoctorDetails) {
        this.selectedDoctorDetails = selectedDoctorDetails;
    }

    public EnterpriseRequest getSelectedEnterpriseRequest() {
        return selectedEnterpriseRequest;
    }

    public void setSelectedEnterpriseRequest(EnterpriseRequest selectedEnterpriseRequest) {
        this.selectedEnterpriseRequest = selectedEnterpriseRequest;
    }

    public AppointmentBookingDetailsDialogFrag() {
        // Required empty public constructor
    }

    private BookRideInitDialogCallBack mBookRideInitDialogCallBack;

    public void setBookRideInitDialogCallBack(BookRideInitDialogCallBack bookRideInitDialogCallBack) {
        mBookRideInitDialogCallBack = bookRideInitDialogCallBack;
    }

    public static AppointmentBookingDetailsDialogFrag newInstance(BookRideInitDialogCallBack bookRideInitDialogCallBack, DoctorDetails selectedDoctorDetails, EnterpriseRequest selectedEnterpriseRequest, String select_time_slot, String appointmentDate) {
        AppointmentBookingDetailsDialogFrag f = new AppointmentBookingDetailsDialogFrag();
        f.setBookRideInitDialogCallBack(bookRideInitDialogCallBack);
        Gson gson = new Gson();
        String selectedDoctorDetailsJson = gson.toJson(selectedDoctorDetails);
        String selectedEnterpriseRequestJson = gson.toJson(selectedEnterpriseRequest);
        Bundle args = new Bundle();
        args.putString(appointmentDate_TAG, appointmentDate);
        args.putString(select_time_slot_TAG, select_time_slot);
        args.putString(selectedDoctorDetails_TAG, selectedDoctorDetailsJson);
        args.putString(selectedEnterpriseRequest_TAG, selectedEnterpriseRequestJson);
        f.setArguments(args);
        f.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        // Add back button listener
        dialog.setOnKeyListener(new Dialog.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
                // getAction to make sure this doesn't double fire
                if (keyCode == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_UP) {
//                    mOnItemClickListenerCouponActivity.onDismissClick(couponModel);
//                    dismiss();
                    return true; // Capture onKey
                }
                return false; // Don't capture
            }
        });

        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.MyDialog);

        if (getArguments() != null) {
            Gson gson = new Gson();

            String selectedDoctorDetailsJson = getArguments().getString(selectedDoctorDetails_TAG);
            String selectedEnterpriseRequestJson = getArguments().getString(selectedEnterpriseRequest_TAG);

            selectedDoctorDetails = gson.fromJson(selectedDoctorDetailsJson, DoctorDetails.class);
            setSelectedDoctorDetails(selectedDoctorDetails);
            selectedEnterpriseRequest = gson.fromJson(selectedEnterpriseRequestJson, EnterpriseRequest.class);
            setSelectedEnterpriseRequest(selectedEnterpriseRequest);

            String appointmentDate = getArguments().getString(appointmentDate_TAG);
            setAppointmentDate(appointmentDate);
            String select_time_slot = getArguments().getString(select_time_slot_TAG);
            setSelect_time_slot(select_time_slot);

        }
    }

    private String select_time_slot, appointmentDate;

    public String getSelect_time_slot() {
        return select_time_slot;
    }

    public void setSelect_time_slot(String select_time_slot) {
        this.select_time_slot = select_time_slot;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    private AppCompatImageView doctor_logo, location_iv, clock_iv, previousMonthButton, nextMonthButton;
    private AppCompatTextView doctor_name_tv, doctor_specilization_tv, address_tv, date_time_tv, book_cab_tv;
    private LinearLayout location_ll, schedule_ll, previous_month_ll, next_month_ll, bottom_rl, cab_booking_ll, skip_cab_booking_ll;
    private RelativeLayout cab_booking_rl, book_ride_types_rl;
    private RecyclerView ride_types_rv;

    private SelectRideAdapter mAdapter;

    private int rideIcon;

    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        doctor_logo = v.findViewById(R.id.doctor_logo);
        location_iv = v.findViewById(R.id.location_iv);
        clock_iv = v.findViewById(R.id.clock_iv);

        doctor_name_tv = v.findViewById(R.id.doctor_name_tv);
        if(selectedDoctorDetails.getFullName() != null)
            doctor_name_tv.setText("Dr. "+selectedDoctorDetails.getFullName());

        doctor_specilization_tv = v.findViewById(R.id.doctor_specilization_tv);
        if(selectedDoctorDetails.getSpecialization() != null)
            doctor_specilization_tv.setText(selectedDoctorDetails.getSpecialization());

        address_tv = v.findViewById(R.id.address_tv);

        EnterpriseAddressRequest enterpriseAddressRequest = null;

        if((selectedEnterpriseRequest.getAddresses() != null && !selectedEnterpriseRequest.getAddresses().isEmpty())){
            enterpriseAddressRequest = selectedEnterpriseRequest.getAddresses().iterator().next();
        }

        if(enterpriseAddressRequest != null){
            String address = selectedEnterpriseRequest.getEnterpriseName()+" - "+(enterpriseAddressRequest != null ? enterpriseAddressRequest.getAddressLine1()
                    + " "+enterpriseAddressRequest.getCity()+ " "+enterpriseAddressRequest.getPostalCode() : "");
            if(address != null && address.length() > 0){
                address_tv.setText(address);
            }
        }

        date_time_tv = v.findViewById(R.id.date_time_tv);

        if(getAppointmentDate() != null && getSelect_time_slot() != null){
            date_time_tv.setText(getAppointmentDate()+" @ "+getSelect_time_slot());
        }

        book_cab_tv = v.findViewById(R.id.book_cab_tv);
        location_ll = v.findViewById(R.id.location_ll);
        schedule_ll = v.findViewById(R.id.schedule_ll);

        previous_month_ll = v.findViewById(R.id.previous_month_ll);
        previousMonthButton = v.findViewById(R.id.previous_month_bttn);
        next_month_ll = v.findViewById(R.id.next_month_ll);
        nextMonthButton = v.findViewById(R.id.next_month_bttn);

        bottom_rl = v.findViewById(R.id.bottom_rl);
        cab_booking_ll = v.findViewById(R.id.cab_booking_ll);
        cab_booking_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBookRideInitDialogCallBack.bookRide(rideIcon);
                dismiss();
            }
        });
        skip_cab_booking_ll = v.findViewById(R.id.skip_cab_booking_ll);
        skip_cab_booking_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBookRideInitDialogCallBack.bookRideSkip();
                dismiss();
            }
        });
        cab_booking_rl = v.findViewById(R.id.cab_booking_rl);
        book_ride_types_rl = v.findViewById(R.id.book_ride_types_rl);

        ride_types_rv = v.findViewById(R.id.ride_types_rv);
        ride_types_rv.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        ride_types_rv.setLayoutManager(mLayoutManager);
        ride_types_rv.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new SelectRideAdapter(getActivity(), getTitles(), getDrawableIds());

//        int selectedIndex = 0;
//        mAdapter.setSelectedPosition(selectedIndex);
//        ride_types_rv.scrollToPosition(selectedIndex);

        rideIcon = getDrawableIds()[0];

        ride_types_rv.setAdapter(mAdapter);

        ride_types_rv.addOnItemTouchListener(new RecyclerviewClickListeners.RecyclerTouchListener(getActivity(),
                ride_types_rv, new RecyclerviewClickListeners.ClickListener() {
            @Override
            public void onClick(final View view, final int position) {
                mAdapter.setSelectedPosition(position);
                mAdapter.notifyDataSetChanged();
                rideIcon = getDrawableIds()[position];
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager)ride_types_rv.getLayoutManager();

        previousMonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleToScrollPreviousItems(linearLayoutManager);
            }
        });
        previous_month_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleToScrollPreviousItems(linearLayoutManager);
            }
        });
        nextMonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleToScrollNextItems(linearLayoutManager);
            }
        });
        next_month_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleToScrollNextItems(linearLayoutManager);
            }
        });

        ride_types_rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                firstVisiblePosition = linearLayoutManager.findFirstVisibleItemPosition();// firstVisiblePosition == 0 then left arrow will be dull and right arrow will be white. || firstVisiblePosition == 1 then left arrow will be white and right arrow will be white.
                firstCompletelyVisiblePosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                lastVisiblePosition = linearLayoutManager.findLastVisibleItemPosition();
                lastCompletelyVisiblePosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();//when lastCompletelyVisiblePosition == mAdapter.getItemCount()-1 then left arrow will be white and right arrow will be dull.
                handleOnScrollItems();
            }
        });
    }

    private int firstVisiblePosition, firstCompletelyVisiblePosition, lastVisiblePosition, lastCompletelyVisiblePosition;

    private void handleOnScrollItems() {

        if(firstVisiblePosition == 0){
            previousMonthButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_curved_arrow_left));
            nextMonthButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_curved_arrow_right_dark));
        }else if(firstVisiblePosition > 0){
            previousMonthButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_curved_arrow_left_dark));
            nextMonthButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_curved_arrow_right_dark));
        }

        if(lastCompletelyVisiblePosition == mAdapter.getItemCount()-1){
            previousMonthButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_curved_arrow_left_dark));
            nextMonthButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_curved_arrow_right));
        }
    }

    private void handleToScrollNextItems(final LinearLayoutManager linearLayoutManager) {
        if(lastVisiblePosition < mAdapter.getItemCount()-1){
            linearLayoutManager.scrollToPosition(lastVisiblePosition+1);
        }
    }

    private void handleToScrollPreviousItems(final LinearLayoutManager linearLayoutManager) {
        if(firstVisiblePosition > 0){
            linearLayoutManager.scrollToPosition(firstVisiblePosition-1);
        }
    }

    private String[] getTitles() {
        return getActivity().getResources().getStringArray(R.array.ride_types_titles);
    }

    private int[] getDrawableIds() {
        return new int[]{R.drawable.ic_auto, R.drawable.ic_taxi, R.drawable.ic_txi, R.drawable.ic_prime, R.drawable.ic_share_car, R.drawable.ic_rentals, R.drawable.ic_cab}/*getActivity().getResources().getIntArray(R.array.ride_types_icons)*/;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.create_appointment_confirmation_dialog, container, false);
    }

    @Override
    public void onResume() {
        // Get existing layout params for the window
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        // Assign window properties to fill the parent
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((WindowManager.LayoutParams) params);
        // Call super onResume after sizing
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }
}
