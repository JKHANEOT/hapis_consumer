package com.hapis.customer.ui.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hapis.customer.R;
import com.hapis.customer.networking.json.JSONAdaptor;
import com.hapis.customer.ui.BookAppointmentActivity;
import com.hapis.customer.ui.DashboardActivity;
import com.hapis.customer.ui.adapters.UpComingSchedulesRecyclerViewAdapter;
import com.hapis.customer.ui.adapters.datamodels.DateItem;
import com.hapis.customer.ui.adapters.datamodels.GroupDataGeneralItem;
import com.hapis.customer.ui.adapters.datamodels.GroupDataListItem;
import com.hapis.customer.ui.custom.dialogplus.DialogPlus;
import com.hapis.customer.ui.custom.dialogplus.OnClickListener;
import com.hapis.customer.ui.custom.recyclerviewanimations.RecyclerviewClickListeners;
import com.hapis.customer.ui.custom.recyclerviewanimations.animators.SlideInUpAnimator;
import com.hapis.customer.ui.models.appointments.AppointmentRequest;
import com.hapis.customer.ui.utils.DialogIconCodes;
import com.hapis.customer.ui.view.BaseView;
import com.hapis.customer.ui.view.UpComingSchedulesFragmentView;
import com.hapis.customer.ui.view.UpComingSchedulesFragmentViewModal;
import com.hapis.customer.utils.DateUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class UpComingSchedulesFrag extends BaseAbstractFragment<UpComingSchedulesFragmentViewModal> implements UpComingSchedulesFragmentView,
        UpComingSchedulesRecyclerViewAdapter.UpComingScheduleAdapterListeners {

    private FloatingActionButton createAnAppointment;

    private View v;
    private static final String TAG = UpComingSchedulesFrag.class.getName();
    private AppCompatTextView list_empty_tv;
    private RecyclerView upcoming_appointments_rv;
    private UpComingSchedulesRecyclerViewAdapter mAdapter;

    public UpComingSchedulesFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_up_coming_schedules, container, false);

        upcoming_appointments_rv = v.findViewById(R.id.upcoming_appointments_rv);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        upcoming_appointments_rv.setLayoutManager(mLayoutManager);
        upcoming_appointments_rv.setHasFixedSize(true);

        upcoming_appointments_rv.setItemAnimator(new SlideInUpAnimator());
        upcoming_appointments_rv.addItemDecoration(new RecyclerviewClickListeners.VerticalSpaceItemDecoration(10));

        list_empty_tv = v.findViewById(R.id.list_empty_tv);

        createAnAppointment = v.findViewById(R.id.fab_create_an_appointment);
        createAnAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BookAppointmentActivity.class);
                startActivity(intent);
            }
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        mViewModal.getUpcomingAppointments();
    }

    @Override
    protected Class getViewModalClass() {
        return UpComingSchedulesFragmentViewModal.class;
    }

    @Override
    protected BaseView getViewImpl() {
        return this;
    }

    @Override
    public void showError(String errorMsg, OnClickListener onClickListener, String positiveLbl, String negativeLbl, String status) {
        ((DashboardActivity)getActivity()).showError(errorMsg, onClickListener, positiveLbl, negativeLbl, status);
    }

    @Override
    public void failedToProcess(String errorMsg) {
        list_empty_tv.setVisibility(View.VISIBLE);
        upcoming_appointments_rv.setVisibility(View.GONE);
    }

    @Override
    public void fetchEnterpriseDetails(List<AppointmentRequest> appointmentRequests) {
        mViewModal.getEnterpriseDetails(appointmentRequests);
    }

    @Override
    public void showUpComingAppointments(List<AppointmentRequest> appointmentRequests) {
        new RefreshDataToGroupedHashmap().execute(appointmentRequests);
    }

    @Override
    public void cancelAppointmentSuccess(final AppointmentRequest appointmentRequest, final int selectedIndex) {
        OnClickListener onClickListener = new OnClickListener() {
            @Override
            public void onClick(DialogPlus dialog, View view) {
                switch (view.getId()){
                    case R.id.positive_btn:{
                        dialog.dismiss();
                        GroupDataGeneralItem generalItem = new GroupDataGeneralItem();
                        generalItem.setHapisModel(appointmentRequest);
                        mAdapter.updateAppointment(generalItem, selectedIndex);
                        break;
                    }
                }
            }
        };
        showError(getResources().getString(R.string.appointment_cancelled), onClickListener, getResources().getString(R.string.cancelled), null, DialogIconCodes.DIALOG_SUCCESS.getIconCode());
    }

    class RefreshDataToGroupedHashmap extends AsyncTask<List<AppointmentRequest>, Void, List<GroupDataListItem>> {

        @Override
        protected List<GroupDataListItem> doInBackground(List<AppointmentRequest>... lists) {

            Map<String, List<AppointmentRequest>> groupedHashmap = null;
            List<GroupDataListItem> consolidatedList = null;

            List<AppointmentRequest> appointmentRequests = lists[0];

            if (appointmentRequests != null && appointmentRequests.size() > 0) {
                groupedHashmap = new TreeMap<>(Collections.reverseOrder(new DateUtil.StringDateComparator(DateUtil.DATE_FORMAT_EEE_MMM_d_YY)));
                for (AppointmentRequest appointmentRequest : appointmentRequests) {
                    if (appointmentRequest.getAppointmentDate() != null) {

                        String convertedDate = DateUtil.convertDateToDateStr(DateUtil.convertDateStrToDate(appointmentRequest.getAppointmentDate(), DateUtil.DATE_FORMAT_dd_MM_yyyy_SEP_HIPHEN), DateUtil.DATE_FORMAT_EEE_MMM_d_YY);
                        List<AppointmentRequest> groupedList = null;

                        if (groupedHashmap.containsKey(convertedDate)) {
                            groupedList = groupedHashmap.get(convertedDate);
                            groupedList.add(appointmentRequest);
                            groupedHashmap.put(convertedDate, groupedList);
                        } else {
                            groupedList = new ArrayList<>();
                            groupedList.add(appointmentRequest);
                            groupedHashmap.put(convertedDate, groupedList);
                        }
                    }
                }

                if (groupedHashmap != null && groupedHashmap.size() > 0) {
                    consolidatedList = new ArrayList<>();

                    for (String date : groupedHashmap.keySet()) {
                        DateItem dateItem = new DateItem();
                        dateItem.setDate(date);
                        consolidatedList.add(dateItem);

                        List<AppointmentRequest> individualPaymentHistoryList = groupedHashmap.get(date);

                        for (AppointmentRequest serviceRequestInvoiceModel : individualPaymentHistoryList) {
                            GroupDataGeneralItem generalItem = new GroupDataGeneralItem();
                            generalItem.setHapisModel(serviceRequestInvoiceModel);
                            consolidatedList.add(generalItem);
                        }
                    }
                }
            }

            return consolidatedList;
        }

        @Override
        protected void onPostExecute(List<GroupDataListItem> consolidatedList) {
            if (consolidatedList != null && consolidatedList.size() > 0) {
                if (upcoming_appointments_rv != null) {
                    upcoming_appointments_rv.setVisibility(View.VISIBLE);
                    list_empty_tv.setVisibility(View.GONE);
                    mAdapter = new UpComingSchedulesRecyclerViewAdapter(consolidatedList, UpComingSchedulesFrag.this);
                    upcoming_appointments_rv.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public void viewAppointmentDetails(AppointmentRequest appointmentRequest, int selectedIndex) {
        Intent intent = new Intent(getActivity(), BookAppointmentActivity.class);
        try {
            String appointmentRequestJson = JSONAdaptor.toJSON(appointmentRequest);
            if(appointmentRequestJson != null && appointmentRequestJson.length() > 0){
                intent.putExtra(BookAppointmentActivity.APPOINTMENT_DETAILS_TAG, appointmentRequestJson);
                startActivity(intent);
            }else
                showError(getResources().getString(R.string.details_not_available), null, null, null, DialogIconCodes.DIALOG_NOT_AVAILABLE.getIconCode());
        } catch (Exception e) {
            e.printStackTrace();
            showError(getResources().getString(R.string.details_not_available), null, null, null, DialogIconCodes.DIALOG_NOT_AVAILABLE.getIconCode());
        }
    }

    @Override
    public void rescheduleAppointment(AppointmentRequest appointmentRequest, int selectedIndex) {
        Toast.makeText(getActivity(), "Clicked to reschedule", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void cancelAppointment(final AppointmentRequest appointmentRequest, final int selectedIndex) {
        OnClickListener onClickListener = new OnClickListener() {
            @Override
            public void onClick(DialogPlus dialog, View view) {
                switch (view.getId()){
                    case R.id.positive_btn:{
                        dialog.dismiss();
                        mViewModal.cancelAppointment(appointmentRequest, selectedIndex);
                        break;
                    }
                    case R.id.negative_btn:{
                        dialog.dismiss();
                        break;
                    }
                }
            }
        };
        showError(getResources().getString(R.string.would_you_like_to_cancel_appointment), onClickListener, getResources().getString(R.string.confirm), getResources().getString(R.string.cancel), DialogIconCodes.DIALOG_DK_UPDATE.getIconCode());
    }
}
