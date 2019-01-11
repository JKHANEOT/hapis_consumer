package com.hapis.customer.ui.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hapis.customer.HapisApplication;
import com.hapis.customer.R;
import com.hapis.customer.ui.BaseFragmentActivity;
import com.hapis.customer.ui.BookAppointmentActivity;
import com.hapis.customer.ui.callback.CommonSearchCallBack;
import com.hapis.customer.ui.callback.SelectDateAndTimeSlotCallBack;
import com.hapis.customer.ui.callback.SelectPreferredLocationCallBack;
import com.hapis.customer.ui.custom.materialedittext.MaterialEditText;
import com.hapis.customer.ui.fragments.search.enterprise.doctor.DoctorSearchByEnterpriseCallBack;
import com.hapis.customer.ui.fragments.search.enterprise.doctor.DoctorSearchByEnterpriseDialogFragment;
import com.hapis.customer.ui.fragments.search.enterprise.enterprises.EnterpriseSearchCallBack;
import com.hapis.customer.ui.fragments.search.enterprise.enterprises.EnterpriseSearchDialogFragment;
import com.hapis.customer.ui.fragments.timeslot.TimeSlotDialogFragment;
import com.hapis.customer.ui.models.appointments.DoctorDetails;
import com.hapis.customer.ui.models.enterprise.EnterpriseRequest;
import com.hapis.customer.ui.models.enums.EnterpriseTypeEnum;
import com.hapis.customer.ui.models.enums.MasterDataUtils;
import com.hapis.customer.ui.utils.DialogIconCodes;
import com.hapis.customer.ui.utils.EditTextUtils;
import com.hapis.customer.ui.view.BaseView;
import com.hapis.customer.ui.view.BaseViewModal;
import com.hapis.customer.ui.view.BookAppointmentFragmentView;
import com.hapis.customer.ui.view.BookAppointmentFragmentViewModal;
import com.hapis.customer.utils.DateUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookAppointmentFragment extends BaseAbstractFragment<BookAppointmentFragmentViewModal> implements BookAppointmentFragmentView, SelectPreferredLocationCallBack {

    public static final String TAG = BookAppointmentFragment.class.getName();

    private View v;
    private AppCompatTextView listEmptyTv;

    public BookAppointmentFragment() {
        // Required empty public constructor
    }

    private RelativeLayout select_preferred_location_rl, select_enterprise_rl, select_specialization_rl, select_doctor_rl, select_date_rl, select_time_slot_rl, appointment_you_are_following_up_rl;
    private MaterialEditText select_preferred_location_edittext, select_enterprise_edittext, select_specialization_edittext, select_doctor_edittext, select_date_edittext, select_time_slot_edittext, appointment_you_are_following_up_edittext;

    private AppCompatButton reset_button, book_button, new_appointment_type_button, followup_appointment_type_button;
    private LinearLayout new_appointment_type_ll,followup_appointment_type_ll;

    private int appointmentTypeId;

    private void updateAppointmentType(int clickedAppointmentTypeId){
        switch (clickedAppointmentTypeId){
            case R.id.new_appointment_type_button:{
                if(appointmentTypeId == clickedAppointmentTypeId){//Deselect
                    appointmentTypeId = 0;
                    new_appointment_type_ll.setBackground(getResources().getDrawable(R.drawable.rounded_corner_button_grey));
                    new_appointment_type_button.setTextColor(getResources().getColor(R.color.gray));

                    appointment_you_are_following_up_edittext.setText("");

                    //TODO:Refresh UI.
                }else{//Select
                    appointmentTypeId = clickedAppointmentTypeId;
                    new_appointment_type_ll.setBackground(getResources().getDrawable(R.drawable.rounded_corner_button_green));
                    new_appointment_type_button.setTextColor(getResources().getColor(R.color.md_green_500));
                    followup_appointment_type_ll.setBackground(getResources().getDrawable(R.drawable.rounded_corner_button_grey));
                    followup_appointment_type_button.setTextColor(getResources().getColor(R.color.gray));

                    appointment_you_are_following_up_edittext.setText("");

                    appointment_you_are_following_up_rl.setVisibility(View.GONE);
                    //TODO:Refresh UI.
                }
                break;
            }
            case R.id.followup_appointment_type_button:{
                if(appointmentTypeId == clickedAppointmentTypeId){//Deselect
                    appointmentTypeId = 0;
                    followup_appointment_type_ll.setBackground(getResources().getDrawable(R.drawable.rounded_corner_button_grey));
                    followup_appointment_type_button.setTextColor(getResources().getColor(R.color.gray));

                    appointment_you_are_following_up_edittext.setText("");

                    appointment_you_are_following_up_rl.setVisibility(View.GONE);
                    //TODO:Refresh UI.
                }else{//Select
                    appointmentTypeId = clickedAppointmentTypeId;
                    new_appointment_type_ll.setBackground(getResources().getDrawable(R.drawable.rounded_corner_button_grey));
                    new_appointment_type_button.setTextColor(getResources().getColor(R.color.gray));
                    followup_appointment_type_ll.setBackground(getResources().getDrawable(R.drawable.rounded_corner_button_green));
                    followup_appointment_type_button.setTextColor(getResources().getColor(R.color.md_green_500));

                    appointment_you_are_following_up_edittext.setText("");

                    appointment_you_are_following_up_rl.setVisibility(View.VISIBLE);
                    //TODO:Refresh UI.
                }
                break;
            }
        }
    }

    View.OnClickListener bookButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(appointmentTypeId == 0){
                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.please_select_appointment_type), Toast.LENGTH_SHORT).show();
            }else if(mSelectedLocation == null){
                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.please_select_preferred_location), Toast.LENGTH_SHORT).show();
            }else if(selectedEnterpriseRequest == null){
                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.please_select_enterprise), Toast.LENGTH_SHORT).show();
            }else if(EditTextUtils.isEmpty(select_specialization_edittext))
                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.please_select_specialization), Toast.LENGTH_SHORT).show();
            else if(selectedDoctorDetails == null)
                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.please_select_doctor), Toast.LENGTH_SHORT).show();
            else if(EditTextUtils.isEmpty(select_date_edittext))
                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.please_select_appointment_date), Toast.LENGTH_SHORT).show();
            else if(mSelectedTimeSlot != null && mSelectedTimeSlot.size() == 0)
                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.please_select_a_time_slot_to_continue), Toast.LENGTH_SHORT).show();
            else{
                String appointmentDate = EditTextUtils.getText(select_date_edittext);
                String doctorCode = selectedDoctorDetails.getDoctorCode();
                String hospitalCode = selectedEnterpriseRequest.getEnterpriseCode();
                int slotBooked = selectedIndex;

                ((BookAppointmentActivity)getActivity()).showProgressDialog(getActivity(), getResources().getString(R.string.please_wait));

                mViewModal.createAppointment(appointmentDate, doctorCode, hospitalCode, slotBooked);
            }
        }
    };

    View.OnClickListener resetButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //TODO:
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_book_appointment, container, false);

        appointment_you_are_following_up_rl = v.findViewById(R.id.appointment_you_are_following_up_rl);
        appointment_you_are_following_up_edittext = v.findViewById(R.id.appointment_you_are_following_up_edittext);

        reset_button = v.findViewById(R.id.reset_button);
        reset_button.setOnClickListener(resetButtonOnClickListener);

        book_button = v.findViewById(R.id.book_button);
        book_button.setOnClickListener(bookButtonOnClickListener);

        new_appointment_type_button = v.findViewById(R.id.new_appointment_type_button);
        followup_appointment_type_button = v.findViewById(R.id.followup_appointment_type_button);

        new_appointment_type_ll = v.findViewById(R.id.new_appointment_type_ll);
        new_appointment_type_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAppointmentType(R.id.new_appointment_type_button);
            }
        });
        followup_appointment_type_ll = v.findViewById(R.id.followup_appointment_type_ll);
        followup_appointment_type_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAppointmentType(R.id.followup_appointment_type_button);
            }
        });

        select_preferred_location_rl = v.findViewById(R.id.select_preferred_location_rl);
        select_preferred_location_edittext = v.findViewById(R.id.select_preferred_location_edittext);

        select_preferred_location_edittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectLocationFragment();
            }
        });

        select_enterprise_rl = v.findViewById(R.id.select_enterprise_rl);
        select_enterprise_edittext = v.findViewById(R.id.select_enterprise_edittext);

        select_enterprise_edittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSelectedLocation != null && mSelectedLocation.size() > 0 && mSelectedLocation.size() == 3) {
                    ((BookAppointmentActivity) getActivity()).showProgressDialog(getActivity(), getResources().getString(R.string.please_wait));
                    mViewModal.getEnterprisesBy(EnterpriseTypeEnum.HOSPITAL.code(), mSelectedLocation.get(2));
                }else{
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.please_select_preferred_location), Toast.LENGTH_SHORT).show();
                }
            }
        });

        select_specialization_rl = v.findViewById(R.id.select_specialization_rl);
        select_specialization_edittext = v.findViewById(R.id.select_specialization_edittext);

        select_specialization_edittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonSearchDialogFragment dialog =
                        CommonSearchDialogFragment.newInstance((BaseFragmentActivity)getActivity(), new CommonSearchCallBack() {
                            @Override
                            public void updateSelectedValue(String selectedValue) {
                                select_specialization_edittext.setText(selectedValue);
                            }
                        }, MasterDataUtils.getSpecialisation(), "Specialisation");
                dialog.setCancelable(false);
                dialog.show(getActivity().getSupportFragmentManager(), SelectPreferredLocationDialogFragment.TAG);
            }
        });

        select_doctor_rl = v.findViewById(R.id.select_doctor_rl);
        select_doctor_edittext = v.findViewById(R.id.select_doctor_edittext);

        select_doctor_edittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(EditTextUtils.isEmpty(select_specialization_edittext))
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.please_select_specialization), Toast.LENGTH_SHORT).show();
                else if(selectedEnterpriseRequest == null)
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.please_select_enterprise), Toast.LENGTH_SHORT).show();
                else{
                    ((BookAppointmentActivity)getActivity()).showProgressDialog(getActivity(), getResources().getString(R.string.please_wait));
                    mViewModal.getDoctorsByEnterprise(selectedEnterpriseRequest.getEnterpriseCode(), EditTextUtils.getText(select_specialization_edittext));
                }
            }
        });

        select_date_rl = v.findViewById(R.id.select_date_rl);
        select_date_edittext = v.findViewById(R.id.select_date_edittext);

        select_date_edittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BaseFragmentActivity<BaseViewModal>) getActivity()).hideSoftKeyPad();
                ((BaseFragmentActivity<BaseViewModal>) getActivity()).setDateResultTo(select_date_edittext, null, true);
                getActivity().showDialog(BaseFragmentActivity.DATE_PICKER_ID1);
            }
        });

        select_time_slot_rl = v.findViewById(R.id.select_time_slot_rl);
        select_time_slot_edittext = v.findViewById(R.id.select_time_slot_edittext);

        select_time_slot_edittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedDoctorDetails == null)
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.please_select_doctor), Toast.LENGTH_SHORT).show();
                else if(EditTextUtils.isEmpty(select_date_edittext))
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.please_select_appointment_date), Toast.LENGTH_SHORT).show();
                else if(selectedEnterpriseRequest == null)
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.please_select_enterprise), Toast.LENGTH_SHORT).show();
                else{
                    String selectedDateForAppointment = EditTextUtils.getText(select_date_edittext);
                    selectedDateForAppointment = DateUtil.changeDateFormat(selectedDateForAppointment, DateUtil.DATE_FORMAT_dd_MM_yyyy_SEP_HIPHEN, DateUtil.DATE_FORMAT_yyyy_MM_dd_SEP_HIPHEN);

                    mViewModal.getTimeSlotByDoctorEnterpriseAndDate(selectedDoctorDetails.getDoctorCode(), selectedDateForAppointment, selectedEnterpriseRequest.getEnterpriseCode());
                }
            }
        });

        updateAppointmentType(R.id.new_appointment_type_button);

        return v;
    }

    private EnterpriseRequest selectedEnterpriseRequest;

    @Override
    protected Class getViewModalClass() {
        return BookAppointmentFragmentViewModal.class;
    }

    @Override
    protected BaseView getViewImpl() {
        return this;
    }

    @Override
    public void updateEnterpriseByTypeAndCity(List<EnterpriseRequest> enterpriseRequestList) {

        EnterpriseSearchDialogFragment dialog =
                EnterpriseSearchDialogFragment.newInstance((BaseFragmentActivity)getActivity(), new EnterpriseSearchCallBack() {
                    @Override
                    public void updateSelectedValue(EnterpriseRequest enterpriseRequest) {
                        selectedEnterpriseRequest = enterpriseRequest;
                        select_enterprise_edittext.setText(selectedEnterpriseRequest.getEnterpriseName());
                    }
                }, enterpriseRequestList, "Hospital");
        dialog.setCancelable(false);
        dialog.show(getActivity().getSupportFragmentManager(), SelectPreferredLocationDialogFragment.TAG);

        ((BookAppointmentActivity)getActivity()).dismissProgressDialog();
    }

    private DoctorDetails selectedDoctorDetails;

    @Override
    public void failedToProcess(String errorMsg) {
        ((BookAppointmentActivity)getActivity()).dismissProgressDialog();
        ((BookAppointmentActivity)getActivity()).showError(errorMsg, null, null, null, DialogIconCodes.DIALOG_FAILED.getIconCode());
    }

    @Override
    public void updateDoctorsByEnterpriseAndSpecialization(List<DoctorDetails> doctorDetailsList) {
        DoctorSearchByEnterpriseDialogFragment dialog =
                DoctorSearchByEnterpriseDialogFragment.newInstance((BaseFragmentActivity)getActivity(), new DoctorSearchByEnterpriseCallBack() {
                    @Override
                    public void updateSelectedValue(DoctorDetails doctorDetails) {
                        selectedDoctorDetails = doctorDetails;
                        select_doctor_edittext.setText(selectedDoctorDetails.getFullName());
                    }
                }, doctorDetailsList, "Doctors");
        dialog.setCancelable(false);
        dialog.show(getActivity().getSupportFragmentManager(), SelectPreferredLocationDialogFragment.TAG);

        ((BookAppointmentActivity)getActivity()).dismissProgressDialog();
    }

    private List<String> mSelectedTimeSlot;
    private int selectedIndex;

    @Override
    public void updateDoctorAvailableTimeSlot(List<String> availableTimeSlot) {
        TimeSlotDialogFragment dialog = TimeSlotDialogFragment.newInstance((BaseFragmentActivity)getActivity(), new SelectDateAndTimeSlotCallBack() {
            @Override
            public void updateSelectedDate(List<String> selectedDate) {

            }

            @Override
            public void updateSelectedTime(List<String> selectedTime) {

            }

            @Override
            public void updateSelectedTime(List<String> selectedTime, int index) {
                mSelectedTimeSlot = selectedTime;
                select_time_slot_edittext.setText(mSelectedTimeSlot.get(0));
                selectedIndex = index;
            }
        }, availableTimeSlot, mSelectedTimeSlot);
        dialog.show(getActivity().getSupportFragmentManager(), SelectBookingTimeSlotDialogFragment.TAG);
    }

    @Override
    public void createAppointment(String msg) {
        ((BookAppointmentActivity)getActivity()).dismissProgressDialog();
        AppointmentBookingDetailsDialogFrag dialog = AppointmentBookingDetailsDialogFrag.newInstance(selectedDoctorDetails, selectedEnterpriseRequest, EditTextUtils.getText(select_time_slot_edittext), EditTextUtils.getText(select_date_edittext));
        dialog.show(getActivity().getSupportFragmentManager(), AppointmentBookingDetailsDialogFrag.TAG);
        /*if(msg != null && msg.length() > 0){

            OnClickListener onClickListener = new OnClickListener() {
                @Override
                public void onClick(DialogPlus dialog, View view) {
                    switch (view.getId()){
                        case R.id.positive_btn:
                        {
                            Intent intent = new Intent(getActivity(), DashboardActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            try {
                                getActivity().finishAffinity();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                        case R.id.negative_btn:
                        {
                            Intent intent = new Intent(getActivity(), DashboardActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            try {
                                getActivity().finishAffinity();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                    }
                }
            };

            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append(selectedDoctorDetails.getFullName()+" - "+selectedDoctorDetails.getSpecialization()+"\n");

            EnterpriseAddressRequest enterpriseAddressRequest = null;

            if((selectedEnterpriseRequest.getAddresses() != null && !selectedEnterpriseRequest.getAddresses().isEmpty())){
                enterpriseAddressRequest = selectedEnterpriseRequest.getAddresses().iterator().next();
            }

            stringBuilder.append(selectedEnterpriseRequest.getEnterpriseName()+" - "+(enterpriseAddressRequest != null ? enterpriseAddressRequest.getAddressLine1()
                    + " "+enterpriseAddressRequest.getCity()+ " "+enterpriseAddressRequest.getPostalCode() : "")+"\n");
            stringBuilder.append("Need Cab ?");

//            ((BookAppointmentActivity)getActivity()).showError(msg, onClickListener, getResources().getString(R.string.ok), null, );
            AlertUtil.showAlert(getActivity(), msg, stringBuilder.toString(), "Yes", "No", onClickListener, DialogIconCodes.DIALOG_SUCCESS.getIconCode());
        }*/
    }

    private List<String> mSelectedLocation;

    public void showSelectLocationFragment() {

        new LoadLocationDetailsFromFile().execute();
    }

    @Override
    public void updateSelectedLocation(List<String> selectedLocation) {
        mSelectedLocation = selectedLocation;
        if(mSelectedLocation != null && mSelectedLocation.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(mSelectedLocation.get(2)+"-");
            stringBuilder.append(mSelectedLocation.get(1)+"-");
            stringBuilder.append(mSelectedLocation.get(0));

            select_preferred_location_edittext.setText(stringBuilder.toString());
        }
    }

    class LoadLocationDetailsFromFile extends AsyncTask<Void, Void, Map<String, Map<String, List<String>>>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            ((BookAppointmentActivity)getActivity()).showProgressDialog(getActivity(), "Loading location please wait..");
        }

        @Override
        protected Map<String, Map<String, List<String>>> doInBackground(Void... voids) {

            Map<String, Map<String, List<String>>> locationList = new HashMap<>();

            List<String> city = new ArrayList<>();
            city.add("Bangalore");
            city.add("Mysore");

            HashMap<String, List<String>> state = new HashMap<>();
            state.put("Karnataka", city);

            locationList.put("India", state);

            /*String countryJson = getAssetJsonData("countries.json");
            List<CountryModel> countryJsonModels = JSONAdaptor.getGson().fromJson(countryJson, new TypeToken<List<CountryModel>>() {
            }.getType());
            String stateJson = getAssetJsonData("cities.json");
            List<StateModel> stateJsonModels  = JSONAdaptor.getGson().fromJson(countryJson, new TypeToken<List<StateModel>>() {
            }.getType());
            String cityJson = getAssetJsonData("states.json");
            List<CityModel> cityJsonModels = JSONAdaptor.getGson().fromJson(countryJson, new TypeToken<List<CityModel>>() {
            }.getType());*/

            return locationList;
        }

        @Override
        protected void onPostExecute(Map<String, Map<String, List<String>>> locationList) {
            super.onPostExecute(locationList);

            ((BookAppointmentActivity)getActivity()).dismissProgressDialog();

            SelectPreferredLocationDialogFragment dialog =
                    SelectPreferredLocationDialogFragment.newInstance(BookAppointmentFragment.this, locationList, mSelectedLocation);
            dialog.setCancelable(false);
            dialog.show(getActivity().getSupportFragmentManager(), SelectPreferredLocationDialogFragment.TAG);
        }
    }

    private String getAssetJsonData(final String josnFileName) {
        String json = null;
        try {
            InputStream is = HapisApplication.getApplication().getAssets().open(josnFileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        Log.e("data", json);
        return json;

    }
}
