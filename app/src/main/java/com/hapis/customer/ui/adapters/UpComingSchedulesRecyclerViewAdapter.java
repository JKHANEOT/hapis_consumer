package com.hapis.customer.ui.adapters;

import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hapis.customer.R;
import com.hapis.customer.ui.adapters.datamodels.DateItem;
import com.hapis.customer.ui.adapters.datamodels.GroupDataGeneralItem;
import com.hapis.customer.ui.adapters.datamodels.GroupDataListItem;
import com.hapis.customer.ui.models.appointments.AppointmentRequest;
import com.hapis.customer.ui.models.enums.AppointmentStatusEnum;
import com.hapis.customer.ui.utils.HapisSlotUtils;
import com.hapis.customer.utils.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Javeed on 2/27/2018.
 */

public class UpComingSchedulesRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

//    https://krtkush.github.io/2016/07/08/android-recyclerview-grouping-data.html

    private List<GroupDataListItem> mConsolidatedList = new ArrayList<>();

    public UpComingSchedulesRecyclerViewAdapter(List<GroupDataListItem> consolidatedList, UpComingScheduleAdapterListeners upComingScheduleAdapterListeners) {
        mConsolidatedList.addAll(consolidatedList);
        mUpComingScheduleAdapterListeners = upComingScheduleAdapterListeners;
    }

    @Override
    public int getItemViewType(int position) {
        return mConsolidatedList.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return mConsolidatedList != null ? mConsolidatedList.size() : 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {

            case GroupDataListItem.TYPE_GENERAL:
                View v1 = inflater.inflate(R.layout.common_appointment_adapter_row, parent, false);
                viewHolder = new GeneralViewHolder(v1);
                break;

            case GroupDataListItem.TYPE_DATE:
                View v2 = inflater.inflate(R.layout.grouped_data_date, parent, false);
                viewHolder = new DateViewHolder(v2);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        switch (viewHolder.getItemViewType()) {

            case GroupDataListItem.TYPE_GENERAL: {
                final GroupDataGeneralItem generalItem
                        = (GroupDataGeneralItem) mConsolidatedList.get(position);
                final GeneralViewHolder generalViewHolder
                        = (GeneralViewHolder) viewHolder;

                final AppointmentRequest appointmentRequest = (AppointmentRequest)generalItem.getHapisModel();

                if(appointmentRequest.getDoctorDetails() != null){
                    generalViewHolder.doctor_title_tv.setText(appointmentRequest.getDoctorDetails().getUserName());
                }
                if(appointmentRequest.getEnterpriseRequest() != null){
                    generalViewHolder.hospital_title_tv.setText(appointmentRequest.getEnterpriseRequest().getEnterpriseName());
                    if(appointmentRequest.getEnterpriseRequest().getAddresses() != null && appointmentRequest.getEnterpriseRequest().getAddresses().size() > 0)
                        generalViewHolder.appointment_address_tv.setText(Util.getFormattedAddress(appointmentRequest.getEnterpriseRequest().getAddresses().iterator().next()));
                }

                StringBuilder stringBuilder = new StringBuilder();

                stringBuilder.append(appointmentRequest.getAppointmentDate());
                if(appointmentRequest.getSlotBooked() != null && appointmentRequest.getSlotBooked().intValue() > 0) {
                    stringBuilder.append(" at ");
                    stringBuilder.append(HapisSlotUtils.getSlotName(appointmentRequest.getSlotBooked()));
                }

                generalViewHolder.appointment_date_tv.setText(stringBuilder.toString());
                generalViewHolder.menu_over_flow_img_btn.setVisibility(View.VISIBLE);
                generalViewHolder.item_card_lay.setCardBackgroundColor(generalViewHolder.item_card_lay.getResources().getColor(R.color.new_ui_white));
                if(appointmentRequest.getState() != null && appointmentRequest.getState().intValue() != AppointmentStatusEnum.CANCEL.code()) {

                    generalViewHolder.menu_over_flow_img_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showPopupMenu(view, position, appointmentRequest);
                        }
                    });
                }else{
                    generalViewHolder.menu_over_flow_img_btn.setVisibility(View.GONE);
                    generalViewHolder.item_card_lay.setCardBackgroundColor(generalViewHolder.item_card_lay.getResources().getColor(R.color.semi_transparent_black));
                }

                generalViewHolder.view_appointment_details_rl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mUpComingScheduleAdapterListeners.viewAppointmentDetails(appointmentRequest, position);
                    }
                });

                break;
            }

            case GroupDataListItem.TYPE_DATE: {
                final DateItem dateItem
                        = (DateItem) mConsolidatedList.get(position);
                final DateViewHolder dateViewHolder
                        = (DateViewHolder) viewHolder;
                dateViewHolder.date_tv.setText(dateItem.getDate());

                break;
            }
        }
    }

    private void showPopupMenu(View view,int position,AppointmentRequest appointmentRequest) {
//        http://stackoverflow.com/questions/34641240/toolbar-inside-cardview-to-create-a-popup-menu-overflow-icon
        PopupMenu popup = new PopupMenu(view.getContext(),view );
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.up_coming_appointments_item_overflow_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(position, appointmentRequest));
        popup.show();
    }

    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        private int mPosition;
        private AppointmentRequest mAppointmentRequest;

        public MyMenuItemClickListener(int positon, AppointmentRequest appointmentRequest) {
            mPosition = positon;
            mAppointmentRequest = appointmentRequest;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {

            switch (menuItem.getItemId()) {

                case R.id.menu_reschedule: {
                    mUpComingScheduleAdapterListeners.rescheduleAppointment(mAppointmentRequest, mPosition);
                    return true;
                }
                case R.id.menu_cancel: {
                    mUpComingScheduleAdapterListeners.cancelAppointment(mAppointmentRequest, mPosition);
                    return true;
                }
                default:
            }
            return false;
        }
    }

    // ViewHolder for date row item
    class DateViewHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView date_tv;

        public DateViewHolder(View v) {
            super(v);

            date_tv = v.findViewById(R.id.date_tv);
        }
    }

    // View holder for general row item
    class GeneralViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout view_appointment_details_rl;
        private CardView item_card_lay;
        private ImageView hospital_icon;
        private AppCompatImageButton menu_over_flow_img_btn;
        private TextView hospital_title_tv,doctor_title_tv, appointment_date_tv,appointment_address_tv;

        public GeneralViewHolder(View v) {
            super(v);

            view_appointment_details_rl = v.findViewById(R.id.view_appointment_details_rl);
            item_card_lay = v.findViewById(R.id.item_card_lay);
            hospital_icon = v.findViewById(R.id.hospital_icon);
            menu_over_flow_img_btn = v.findViewById(R.id.menu_over_flow_img_btn);
            hospital_title_tv = v.findViewById(R.id.hospital_title_tv);
            doctor_title_tv = v.findViewById(R.id.doctor_title_tv);
            appointment_date_tv = v.findViewById(R.id.appointment_date_tv);
            appointment_address_tv = v.findViewById(R.id.appointment_address_tv);
        }
    }

    private UpComingScheduleAdapterListeners mUpComingScheduleAdapterListeners;

    public interface UpComingScheduleAdapterListeners {
        void viewAppointmentDetails(final AppointmentRequest appointmentRequest, int selectedIndex);
        void rescheduleAppointment(final AppointmentRequest appointmentRequest, int selectedIndex);
        void cancelAppointment(final AppointmentRequest appointmentRequest, int selectedIndex);
    }

    public void updateAppointment(final GroupDataGeneralItem groupDataGeneralItem, final int selectedIndex){
        mConsolidatedList.set(selectedIndex, groupDataGeneralItem);
        notifyItemChanged(selectedIndex);
    }
}
