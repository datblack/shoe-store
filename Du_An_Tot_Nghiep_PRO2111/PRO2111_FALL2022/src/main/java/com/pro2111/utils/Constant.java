/**
 * DATN_FALL2022, 2022
 * Constant.java, BUI_QUANG_HIEU
 */
package com.pro2111.utils;

/**
 * @author BUI_QUANG_HIEU
 *
 */
public class Constant {
	// bill
	public static final int STATUS_BILL_WAIT_FOR_PAY = 0;
	public static final int STATUS_BILL_WAIT_FOR_CONFIRMATION = 1;
	public static final int STATUS_BILL_WAIT_FOR_DELIVERY = 2;
	public static final int STATUS_BILL_WAIT_FOR_DELIVERING = 3;
	public static final int STATUS_BILL_SUCCESS = 4;
	public static final int STATUS_BILL_CANCEL = 5;

	public static final int BILL_TYPE_ONLINE = 0;
	public static final int BILL_TYPE_OFFLINE = 1;
	public static final int RATE = 100;

	public static final String BILL_WAIT_FOR_PAY = "Chờ thanh toán";
	public static final String BILL_WAIT_FOR_CONFIRMATION = "Chờ xác nhận";
	public static final String BILL_WAIT_FOR_DELIVERY = "Chờ giao";
	public static final String BILL_WAIT_FOR_DELIVERING = "Đang giao";
	public static final String BILL_SUCCESS = "Hoàn thành";
	public static final String BILL_CANCEL = "Hủy";

	public static final int STATUS_MIN_BILL = 0;
	public static final int STATUS_MAX_BILL = 5;

	// bill detail
	public static final int STATUS_BILL_DETAIL_OK = 0;
	public static final int STATUS_BILL_DETAIL_RETURN_OK = 1;
	public static final int STATUS_BILL_DETAIL_CONFIRM_RETURN = 2;
	public static final int STATUS_BILL_DETAIL_RETURN_FAIL = 3;
	public static final int STATUS_BILL_DETAIL_RETURN_CUSTOMER = 4;

	public static final String KEY_RETURN_MONEY = "Hoàn tiền";

	// dashboard
	public static final String TODAY = "today";
	public static final String YESTERDAY = "yesterday";
	public static final String SEVEN_DAYS = "7days";
	public static final String THIS_MONTH = "thisMonth";
	public static final String LAST_MONTH = "lastMonth";
	public static final String THIS_YEAR = "thisYEAR";
	public static final String TYPE_SELLING = "selling";
	public static final String TYPE_NO_SELLING = "noSelling";
	public static final int TOP_DEFAULT = -1;
	public static final int TOP_MIN = 1;
	public static final int YEAR_DEFAULT = -1;
	public static final int STATUS_DEFAULT = -1;

	// setting
	public static final int SETTING_ID_DEFAULT = 1;

	// resource
	public static final String CLASSPATH_APPLICATION_PROPERTIES = "classpath:application.properties";
	public static final String APPLICATION_PROPERTIES = "application.properties";
	public static final String KEY_USERNAME = "spring.mail.username";
	public static final String KEY_PASSWORD = "spring.mail.password";
	public static final String PATH_PROPERTIES_I18N = "i18n.global";
	public static final String ADMIN_NAV = "Admin.nav";

	// validate update price
	public static int INDEX_NAME_VALIDATE = 16;

	// option optionValue
	public static final int OPTION_STATUS_TRUE = 1;
	public static final int OPTION_VALUE_STATUS_TRUE = 1;

	// product
	public static final int PRODUCT_STATUS_TRUE = 1;

	public static final String URI_PDF = "/pdf/print/";

	public static final String NAME_PROJECT = "PRO2111_FALL2022";

	public static final String NAME_FILE_EXCEL_PRODUCT_VARIANT = "QuanLySanPham";

	public static final int QUANTITY_DEFAULT = -1;

	// BillDetailReturn
	public static final int RETURN_TYPE_SUCCESS = 1;
	public static final int RETURN_TYPE_ERROR = 0;

	// StatusBillBean
	public static final String KEY_EMPTY_LIST = "emptyList";

	// Product
	public static final int STATUS_TRUE_PRODUCT = 1;
	public static final int STATUS_FALSE_PRODUCT = 0;

	// ProductOption
	public static final int STATUS_TRUE_PRODUCT_OPTION = 1;
	public static final int STATUS_FALSE_PRODUCT_OPTION = 0;

	// ProductVariant
	public static final int STATUS_TRUE_PRODUCT_VARIANT = 1;
	public static final int STATUS_FALSE_PRODUCT_VARIANT = 0;

	// VariantValue
	public static final int STATUS_TRUE_VARIANT_VALUE = 1;
	public static final int STATUS_FALSE_VARIANT_VALUE = 0;

	// Option
	public static final int STATUS_TRUE_OPTION = 1;
	public static final int STATUS_FALSE_OPTION = 0;
	public static final int IS_SHOW_OPTION = 1;
	public static final int NOT_IS_SHOW_OPTION = 1;

	// User
	public static final int ROLE_CUSTOMER_USER = 1;
	public static final int ROLE_STAFF_USER = 2;
	public static final int ROLE_ADMIN_USER = 3;
	public static final int STATUS_ACTIVE_USER = 1;
	public static final int STATUS_NO_ACTIVE_USER = 0;
	public static final int STATUS_CONFIRM_ACTIVE_USER = 2;
	public static final String PASSWORD_DEFAULT_USER = "12345678";
	
	//Image
	public static final int STATUS_TRUE_IMAGE = 1;
	public static final int STATUS_FALSE_IMAGE = 0;

}
