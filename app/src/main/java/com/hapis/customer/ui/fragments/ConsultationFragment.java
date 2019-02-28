package com.hapis.customer.ui.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hapis.customer.R;
import com.hapis.customer.ui.ConsultationActivity;
import com.hapis.customer.ui.custom.dialogplus.OnClickListener;
import com.hapis.customer.ui.models.appointments.AppointmentRequest;
import com.hapis.customer.ui.models.consultation.Drug;
import com.hapis.customer.ui.models.consultation.Ointment;
import com.hapis.customer.ui.models.consultation.Soap;
import com.hapis.customer.ui.models.consultation.Syrup;
import com.hapis.customer.ui.models.enums.PaymentMode;
import com.hapis.customer.ui.utils.HapisSlotUtils;
import com.hapis.customer.ui.view.BaseView;
import com.hapis.customer.ui.view.ConsultationFragmentView;
import com.hapis.customer.ui.view.ConsultationFragmentViewModal;
import com.hapis.customer.utils.Util;

import java.util.ArrayList;
import java.util.List;

public class ConsultationFragment extends BaseAbstractFragment<ConsultationFragmentViewModal> implements ConsultationFragmentView {

    public static final String TAG = ConsultationFragment.class.getName();

    public ConsultationFragment() {
        // Required empty public constructor
    }

    private AppCompatTextView doctor_name_val_tv, hospital_name_val_tv, appointment_val_tv, payment_mode_val_tv, fee_val_tv;

    private AppCompatEditText your_notes, input_doctor_prescription, doctor_appointment_notes;

    private List<Drug> mPrescription = new ArrayList<>();

    public void setPrescription(List<Drug> prescription) {
        if(prescription != null && prescription.size() > 0){
            mPrescription = new ArrayList<>();
            mPrescription.addAll(prescription);

            input_doctor_prescription.setText("");
            input_doctor_prescription.setText(Html.fromHtml(getPrescriptionSummary()));
        }else{
            mPrescription = null;
            input_doctor_prescription.setText("");
        }
    }

    private String getPrescriptionSummary(){
        StringBuilder stringBuilder = new StringBuilder();

        int index = -1;

        for(final Drug drug : mPrescription){

            String drugType = "Tablet";

            if(drug instanceof Syrup)
                drugType = "Syrup";
            else if(drug instanceof Ointment)
                drugType = "Ointment";
            else if(drug instanceof Soap)
                drugType = "Soap";

            final int noOfDays = drug.getNoOfDays() == null ? 1 : drug.getNoOfDays().intValue();
//            stringBuilder.append("<font color='red'><u><b>"+drug.getName() +" for "+String.valueOf(noOfDays)+(noOfDays > 1 ? " days" : " day")+": ("+drugType+")</b></u></font>"+ "<br />");

            String apply = "Consume";

            if(drug instanceof Ointment || drug instanceof Soap)
                apply = "Apply";

            stringBuilder.append("<font color='red'><u><b>"+drugType+" : "+drug.getName()+"</b></u></font>"+ "<br />");
            stringBuilder.append("<font color='blue'><u><b>"+apply+" : "+String.valueOf(noOfDays)+(noOfDays > 1 ? " days" : " day")+"</b></u></font>"+ "<br />");

            if(drug.getBeforeMorningDose() != null && drug.getBeforeMorningDose().length() > 0){
                stringBuilder.append(apply + " "+drug.getBeforeMorningDose() +" dose in the morning before food"+"<br />");
            }
            if(drug.getMorningDose() != null && drug.getMorningDose().length() > 0){
                stringBuilder.append(apply + " "+drug.getMorningDose() +" dose in the morning after food"+"<br />");
            }
            if(drug.getBeforeNoonDose() != null && drug.getBeforeNoonDose().length() > 0){
                stringBuilder.append(apply + " "+drug.getBeforeNoonDose() +" dose in the afternoon before food"+"<br />");
            }
            if(drug.getNoonDose() != null && drug.getNoonDose().length() > 0){
                stringBuilder.append(apply + " "+drug.getNoonDose() +" dose in the afternoon after food"+"<br />");
            }
            if(drug.getBeforeNightDose() != null && drug.getBeforeNightDose().length() > 0){
                stringBuilder.append(apply + " "+drug.getBeforeNightDose() +" dose in the night before food"+"<br />");
            }
            if(drug.getNightDose() != null && drug.getNightDose().length() > 0){
                stringBuilder.append(apply + " "+drug.getNightDose() +" dose in the night after food"+"<br />");
            }

            ++index;

            if(index < mPrescription.size()-1)
                stringBuilder.append("<br />");
        }

        return stringBuilder.toString();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.patient_appointment_info_history_fragment, container, false);

        doctor_name_val_tv = v.findViewById(R.id.doctor_name_val_tv);
        hospital_name_val_tv = v.findViewById(R.id.hospital_name_val_tv);
        appointment_val_tv = v.findViewById(R.id.appointment_val_tv);
        payment_mode_val_tv = v.findViewById(R.id.payment_mode_val_tv);
        fee_val_tv = v.findViewById(R.id.fee_val_tv);
        your_notes = v.findViewById(R.id.your_notes);
        input_doctor_prescription = v.findViewById(R.id.input_doctor_prescription);
        doctor_appointment_notes = v.findViewById(R.id.doctor_appointment_notes);

        new LoadPatientInformation().execute(((ConsultationActivity)getActivity()).getAppointmentRequest());

        return v;
    }

    class LoadPatientInformation extends AsyncTask<AppointmentRequest, Void, Void>{

        @Override
        protected Void doInBackground(AppointmentRequest... appointmentRequests) {
            loadPatientInformation(appointmentRequests[0]);
            return null;
        }
    }

    private void loadPatientInformation(final AppointmentRequest appointmentRequest){
        if(getActivity() != null){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if(appointmentRequest.getDoctorName() != null && appointmentRequest.getDoctorName().length() > 0){
                        doctor_name_val_tv.setText(appointmentRequest.getDoctorName());
                    }

                    if(appointmentRequest.getHospitalName() != null && appointmentRequest.getHospitalName().length() > 0){
                        hospital_name_val_tv.setText(appointmentRequest.getHospitalName());
                    }

                    if(appointmentRequest.getAppointmentDate() != null && appointmentRequest.getAppointmentDate().length() > 0){

                        StringBuilder stringBuilder = new StringBuilder();

                        stringBuilder.append(appointmentRequest.getAppointmentDate());
                        if(appointmentRequest.getSlotBooked() != null && appointmentRequest.getSlotBooked().intValue() > 0) {
                            stringBuilder.append(" at ");
                            stringBuilder.append(HapisSlotUtils.getSlotName(appointmentRequest.getSlotBooked()));
                        }

                        appointment_val_tv.setText(stringBuilder.toString());
                    }

                    if(appointmentRequest.getNotes() != null) {
                        your_notes.setText(appointmentRequest.getNotes());
                    }

                    if(appointmentRequest.getPaymentMode() != null) {
                        payment_mode_val_tv.setText(PaymentMode.getValue(appointmentRequest.getPaymentMode()));
                    }

                    if(appointmentRequest.getFee() != null) {
                        fee_val_tv.setText(Util.getFormattedAmount(appointmentRequest.getFee()));
                    }

                    if(appointmentRequest.getPrescription() != null){

                        List<Drug> drugs = new ArrayList<>();

                        if(appointmentRequest.getPrescription().getTablets() != null && appointmentRequest.getPrescription().getTablets().size() > 0){
                            drugs.addAll(appointmentRequest.getPrescription().getTablets());
                        }

                        if(appointmentRequest.getPrescription().getSyrups() != null && appointmentRequest.getPrescription().getSyrups().size() > 0){
                            drugs.addAll(appointmentRequest.getPrescription().getSyrups());
                        }

                        if(appointmentRequest.getPrescription().getOintments() != null && appointmentRequest.getPrescription().getOintments().size() > 0){
                            drugs.addAll(appointmentRequest.getPrescription().getOintments());
                        }

                        if(appointmentRequest.getPrescription().getSoap() != null && appointmentRequest.getPrescription().getSoap().size() > 0){
                            drugs.addAll(appointmentRequest.getPrescription().getSoap());
                        }

                        setPrescription(drugs);
                    }

                    if(appointmentRequest.getNotes() != null && appointmentRequest.getNotes().length() > 0){
                        doctor_appointment_notes.setText(appointmentRequest.getNotes());
                    }
                }
            });
        }
    }

    @Override
    protected Class getViewModalClass() {
        return ConsultationFragmentViewModal.class;
    }

    @Override
    protected BaseView getViewImpl() {
        return this;
    }

    @Override
    public void showError(String errorMsg, OnClickListener onClickListener, String positiveLbl, String negativeLbl, String status) {
        ((ConsultationActivity)getActivity()).showError(errorMsg, onClickListener, positiveLbl, negativeLbl, status);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
