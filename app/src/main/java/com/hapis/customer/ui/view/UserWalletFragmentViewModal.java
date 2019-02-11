package com.hapis.customer.ui.view;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.hapis.customer.database.repository.UserWalletRepository;
import com.hapis.customer.ui.models.HapisModel;
import com.hapis.customer.ui.models.wallet.RechargeResponse;
import com.hapis.customer.ui.models.wallet.RechargeResponseList;

public class UserWalletFragmentViewModal extends BaseViewModal<UserWalletFragmentView> {

    private String TAG = UserWalletFragmentViewModal.class.getName();

    private UserWalletRepository userWalletRepository;

    public UserWalletFragmentViewModal(LifecycleOwner owner) {
        super(owner);

        userWalletRepository = new UserWalletRepository();
    }

    @Override
    protected void initObservableData() {
    }

    @Override
    protected void handleChangedDataModal(HapisModel data) {

    }

    public void getAvailableWalletBalance() {

        MutableLiveData<Double> mutableLiveData = new MutableLiveData<>();

        userWalletRepository.new GetAvailableWalletBalance(mutableLiveData).execute();
        mutableLiveData.observe(mOwner, new Observer<Double>() {
            @Override
            public void onChanged(@Nullable Double walletAccountBalance) {
                mView.updateAvailableBalance(walletAccountBalance);
            }
        });
    }

    public void amountEnteredProceedNext(String enteredAmount){
        mView.amountEnteredProceedNext(enteredAmount);
    }

    public void proceedWithRecharge(String enteredAmount, String enteredCardNumber, String enteredCardValidity, String enteredCardCvv) {
        MutableLiveData<RechargeResponse> mutableLiveData = new MutableLiveData<>();

        userWalletRepository.new RechargeWalletAccount(mutableLiveData, enteredAmount, enteredCardNumber, enteredCardValidity, enteredCardCvv).execute();
        mutableLiveData.observe(mOwner, new Observer<RechargeResponse>() {
            @Override
            public void onChanged(@Nullable RechargeResponse walletAccountBalance) {
                if(walletAccountBalance != null && walletAccountBalance.getMessage() != null){
                    mView.rechargeWalletAccountDone();
                }else{
                    mView.failedToRecharge();
                }
            }
        });
    }

    public void getPastRecharges() {

        MutableLiveData<RechargeResponseList> mutableLiveData = new MutableLiveData<>();

        userWalletRepository.new GetPastRechargesWalletAccount(mutableLiveData).execute();
        mutableLiveData.observe(mOwner, new Observer<RechargeResponseList>() {
            @Override
            public void onChanged(@Nullable RechargeResponseList rechargeResponseList) {
                mView.updatePastRecharges(rechargeResponseList.getResults());
            }
        });
    }
}
