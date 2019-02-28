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
import com.hapis.customer.exception.HapisException;
import com.hapis.customer.networking.json.JSONAdaptor;
import com.hapis.customer.ui.BookAppointmentActivity;
import com.hapis.customer.ui.ConsultationActivity;
import com.hapis.customer.ui.DashboardActivity;
import com.hapis.customer.ui.adapters.ClosedAppointmentSchedulesRecyclerViewAdapter;
import com.hapis.customer.ui.adapters.datamodels.DateItem;
import com.hapis.customer.ui.adapters.datamodels.GroupDataGeneralItem;
import com.hapis.customer.ui.adapters.datamodels.GroupDataListItem;
import com.hapis.customer.ui.custom.dialogplus.OnClickListener;
import com.hapis.customer.ui.custom.recyclerviewanimations.RecyclerviewClickListeners;
import com.hapis.customer.ui.custom.recyclerviewanimations.animators.SlideInUpAnimator;
import com.hapis.customer.ui.models.appointments.AppointmentRequest;
import com.hapis.customer.ui.view.BaseView;
import com.hapis.customer.ui.view.ClosedAppointmentSchedulesFragmentView;
import com.hapis.customer.ui.view.ClosedAppointmentSchedulesFragmentViewModal;
import com.hapis.customer.utils.DateUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ClosedAppointmentSchedulesFrag extends BaseAbstractFragment<ClosedAppointmentSchedulesFragmentViewModal> implements
        ClosedAppointmentSchedulesFragmentView, ClosedAppointmentSchedulesRecyclerViewAdapter.ClosedAppointmentScheduleAdapterListeners {

    private static final String TAG = ClosedAppointmentSchedulesFrag.class.getName();

    private FloatingActionButton createAnAppointment;

    private AppCompatTextView list_empty_tv;
    private RecyclerView history_appointments_rv;
    private ClosedAppointmentSchedulesRecyclerViewAdapter mAdapter;

    public ClosedAppointmentSchedulesFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_expired_schedules, container, false);

        history_appointments_rv = v.findViewById(R.id.history_appointments_rv);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        history_appointments_rv.setLayoutManager(mLayoutManager);
        history_appointments_rv.setHasFixedSize(true);

        history_appointments_rv.setItemAnimator(new SlideInUpAnimator());
        history_appointments_rv.addItemDecoration(new RecyclerviewClickListeners.VerticalSpaceItemDecoration(10));

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
        return ClosedAppointmentSchedulesFragmentViewModal.class;
    }

    @Override
    protected BaseView getViewImpl() {
        return this;
    }

    @Override
    public void followupAppointment(AppointmentRequest appointmentRequest, int selectedIndex) {
        Toast.makeText(getActivity(), "Clicked to followup", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void viewConsultationDetails(AppointmentRequest appointmentRequest, int selectedIndex) {
        if(appointmentRequest != null){
            try {
                String consultation = JSONAdaptor.toJSON(appointmentRequest);
                if(consultation != null && consultation.length() > 0){
                    Intent intent = new Intent(getActivity(), ConsultationActivity.class);
                    intent.putExtra(ConsultationActivity.APPOINTMENT_DETAILS_TAG, consultation);
                    getActivity().startActivity(intent);
                }
            } catch (HapisException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void showError(String errorMsg, OnClickListener onClickListener, String positiveLbl, String negativeLbl, String status) {
        ((DashboardActivity)getActivity()).showError(errorMsg, onClickListener, positiveLbl, negativeLbl, status);
    }

    @Override
    public void failedToProcess(String errorMsg) {
        list_empty_tv.setVisibility(View.VISIBLE);
        history_appointments_rv.setVisibility(View.GONE);
    }

    @Override
    public void fetchEnterpriseDetails(List<AppointmentRequest> appointmentRequests) {
        mViewModal.getEnterpriseDetails(appointmentRequests);
    }

    @Override
    public void showUpComingAppointments(List<AppointmentRequest> appointmentRequests) {
        new RefreshDataToGroupedHashmap().execute(appointmentRequests);
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
                if (history_appointments_rv != null) {
                    history_appointments_rv.setVisibility(View.VISIBLE);
                    list_empty_tv.setVisibility(View.GONE);
                    mAdapter = new ClosedAppointmentSchedulesRecyclerViewAdapter(consolidatedList, ClosedAppointmentSchedulesFrag.this);
                    history_appointments_rv.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
            }
        }
    }
}
