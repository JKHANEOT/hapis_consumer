/**
 * 
 */
package com.hapis.customer.ui.utils;

/**
 * @author Hidayathulla.Khan
 *
 */
public final class HapisSlotUtils {

	private HapisSlotUtils() {

	}

	public static String getSlotName(int slot) {
		switch (slot) {
		case AppointmentConstants.SLOT_1:
			return "9.00 AM";
		case AppointmentConstants.SLOT_2:
			return "9.15 AM";
		case AppointmentConstants.SLOT_3:
			return "9.30 AM";
		case AppointmentConstants.SLOT_4:
			return "9.45 AM";
		case AppointmentConstants.SLOT_5:
			return "10.00 AM";
		case AppointmentConstants.SLOT_6:
			return "10.15 AM";
		case AppointmentConstants.SLOT_7:
			return "10.30 AM";
		case AppointmentConstants.SLOT_8:
			return "10.45 AM";
		case AppointmentConstants.SLOT_9:
			return "11.00 AM";
		case AppointmentConstants.SLOT_10:
			return "11.15 AM";
		case AppointmentConstants.SLOT_11:
			return "11.30 AM";
		case AppointmentConstants.SLOT_12:
			return "11.45 AM";
		case AppointmentConstants.SLOT_13:
			return "12.00 PM";
		case AppointmentConstants.SLOT_14:
			return "12.15 PM";
		case AppointmentConstants.SLOT_15:
			return "12.30 PM";
		case AppointmentConstants.SLOT_16:
			return "12.45 PM";
		// Lunch break
		case AppointmentConstants.SLOT_17:
			return "02.00 PM";
		case AppointmentConstants.SLOT_18:
			return "02.15 PM";
		case AppointmentConstants.SLOT_19:
			return "02.30 PM";
		case AppointmentConstants.SLOT_20:
			return "02.45 PM";
		case AppointmentConstants.SLOT_21:
			return "03.00 PM";
		case AppointmentConstants.SLOT_22:
			return "03.15 PM";
		case AppointmentConstants.SLOT_23:
			return "03.30 PM";
		case AppointmentConstants.SLOT_24:
			return "03.45 PM";
		case AppointmentConstants.SLOT_25:
			return "04.00 PM";
		case AppointmentConstants.SLOT_26:
			return "04.15 PM";
		// Tea break
		case AppointmentConstants.SLOT_27:
			return "05.00 PM";
		case AppointmentConstants.SLOT_28:
			return "05.15 PM";
		case AppointmentConstants.SLOT_29:
			return "05.30 PM";
		case AppointmentConstants.SLOT_30:
			return "05.45 PM";
		case AppointmentConstants.SLOT_31:
			return "06.00 PM";
		case AppointmentConstants.SLOT_32:
			return "06.15 PM";
		case AppointmentConstants.SLOT_33:
			return "06.30 PM";
		case AppointmentConstants.SLOT_34:
			return "06.45 PM";
		case AppointmentConstants.SLOT_35:
			return "07.00 PM";
		case AppointmentConstants.SLOT_36:
			return "07.15 PM";
		case AppointmentConstants.SLOT_37:
			return "07.30 PM";
		case AppointmentConstants.SLOT_38:
			return "07.45 PM";
		case AppointmentConstants.SLOT_39:
			return "08.00 PM";
		case AppointmentConstants.SLOT_40:
			return "08.15 PM";
		case AppointmentConstants.SLOT_41:
			return "08.30 PM";
		case AppointmentConstants.SLOT_42:
			return "08.45 PM";

		default:
			return null;
		}
	}

	/*public static List<String> convertStringToList(String value, String delimiter) {
		String[] strArray = StringUtils.tokenizeToStringArray(value, delimiter);
		return new ArrayList<>(Arrays.asList(strArray));
	}*/
}
