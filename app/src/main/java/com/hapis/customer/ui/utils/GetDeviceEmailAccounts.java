package com.hapis.customer.ui.utils;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.hapis.customer.logger.LOG;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahul on 4/25/2017.
 */

public class GetDeviceEmailAccounts {

    private Context mContext;
    public static int GMAIL_ACCOUNT = 0;
    public static int ALL_ACCOUNTS = 1;
    private int emailType;
    private FetchEmailAccounts mFetchEmailAccounts;

    public GetDeviceEmailAccounts(Context context, FetchEmailAccounts fetchEmailAccounts, int emailType) {
        this.mContext = context;
        this.emailType = emailType;
        this.mFetchEmailAccounts = fetchEmailAccounts;
        getEmails();
    }

    public interface FetchEmailAccounts {
        void onFetchingEmailAccounts(List<EmailAccount> emailAccounts);
    }

    private void getEmails() {
        new AsyncTask<Void, Void, List<EmailAccount>>() {
            private ProgressDialog pDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
               /* pDialog = new ProgressDialog(mContext);
                pDialog.setMessage("Reading email accounts");
                pDialog.show();*/
            }

            @Override
            protected List<EmailAccount> doInBackground(Void... params) {
                Account[] accounts;
                if (emailType == GMAIL_ACCOUNT) {
                    // Fetch only gmail ids
                    accounts = AccountManager.get(mContext).getAccountsByType("com.google");
                } else {
                    // Fetch all emails (including gmail)
                    accounts = AccountManager.get(mContext).getAccounts();
                }
                List<EmailAccount> emails = new ArrayList<EmailAccount>();
                try {
                    for (Account account : accounts) {
                        if (account.name.contains("@"))
                            emails.add(new EmailAccount(account.name, account.type));
                    }
                } catch (Exception e) {
                    LOG.i("Exception", "Exception:" + e);
                }
                return emails;
            }

            @Override
            protected void onPostExecute(List<EmailAccount> result) {
                super.onPostExecute(result);
                mFetchEmailAccounts.onFetchingEmailAccounts(result);
            }
        }.execute();
    }
}
