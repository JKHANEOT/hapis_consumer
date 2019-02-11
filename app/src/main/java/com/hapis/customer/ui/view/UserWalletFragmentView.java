package com.hapis.customer.ui.view;

import com.hapis.customer.ui.models.wallet.RechargeRequest;

import java.util.List;

public interface UserWalletFragmentView extends BaseView {

    void updateAvailableBalance(Double balance);

    void amountEnteredProceedNext(String enteredAmount);

    void rechargeWalletAccountDone();

    void failedToRecharge();

    void updatePastRecharges(List<RechargeRequest> rechargeRequests);
}
