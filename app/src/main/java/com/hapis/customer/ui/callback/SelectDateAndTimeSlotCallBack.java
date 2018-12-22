package com.hapis.customer.ui.callback;

import java.util.List;

public interface SelectDateAndTimeSlotCallBack {
    void updateSelectedDate(List<String> selectedDate);
    void updateSelectedTime(List<String> selectedTime);
    void updateSelectedTime(List<String> selectedTime, int index);
}
