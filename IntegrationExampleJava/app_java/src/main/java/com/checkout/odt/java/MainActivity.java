package com.checkout.odt.java;

import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.wallet.PaymentDataRequest;
import com.checkout.odt.*;
import com.checkout.odt.threeDSecure.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    private static int PAY_ACTIVITY_REQUEST = 888;
    private static String SECRET = "Your secret";
    private static int PROJECT_ID = 123;
    private static String RANDOM_PAYMENT_ID = "test_integration_odt_" + getRandomNumber();

    //STEP 1: Create payment info object with product information
    private ODTPaymentInfo paymentInfo = getPaymentInfoOnlyRequiredParams(); // getPaymentInfoAllParams

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //STEP 2: Signature should be generated on your server and delivered to your app
        String signature = SignatureGenerator.generateSignature(getParamsForSigning(), SECRET);

        //STEP 3: Sign payment info object
        setSignature(signature);

        //STEP 4: Create the intent of SDK
        Intent SDKIntent = ODTPaySDK.buildIntent(this, paymentInfo);

        //STEP 5: Present Checkout UI
        startActivityForResult(SDKIntent, PAY_ACTIVITY_REQUEST);

        //Additional STEP (if necessary): add additional fields
        setupAdditionalFields();

        //Additional STEP (if necessary): add recurrent info
        setupRecurrentInfo();

        //Additional STEP (if necessary): add 3DS
        setupThreeDSecureParams();

        //Additional STEP (if necessary): add google pay
        setupGooglePay();

        //Additional STEP (if necessary): custom behaviour of SDK
        setupScreenDisplayModes();
        //Or you can do this like that:
        //addODTScreenDisplayModes();
    }

    private static String getRandomNumber() {
        int randomNumber = (new Random().nextInt(9999) + 1000);
        return Integer.toString(randomNumber);
    }

    // Handle SDK result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAY_ACTIVITY_REQUEST) {

            switch (resultCode) {
                case ODTPaySDK.RESULT_SUCCESS: {
                    break;
                }
                case ODTPaySDK.RESULT_CANCELLED: {
                    break;
                }
                case ODTPaySDK.RESULT_DECLINE: {
                    break;
                }
                case ODTPaySDK.RESULT_FAILED: {
                    break;
                }
                default: {
                    break;
                }
            }

            if (data != null && data.hasExtra(ODTPaySDK.DATA_INTENT_EXTRA_ERROR)) {
                String error = data.getStringExtra(ODTPaySDK.DATA_INTENT_EXTRA_ERROR);
            }

            if (data != null && data.hasExtra(ODTPaySDK.DATA_INTENT_EXTRA_TOKEN)) {
                String token = data.getStringExtra(ODTPaySDK.DATA_INTENT_EXTRA_TOKEN);
            }
        }
    }

    // Payment Info
    ODTPaymentInfo getPaymentInfoOnlyRequiredParams() {
        return new ODTPaymentInfo(
                PROJECT_ID, // project ID that is assigned to you
                RANDOM_PAYMENT_ID, // payment ID to identify payment in your system
                100, // 1.00
                "USD"
        );
    }

    ODTPaymentInfo getPaymentInfoAllParams() {
        return new ODTPaymentInfo(
                PROJECT_ID, // project ID that is assigned to you
                RANDOM_PAYMENT_ID, // payment ID to identify payment in your system
                100, // 1.00
                "USD",
                "T-shirt with dog print",
                "10", // unique ID assigned to your customer
                ""
        );
    }

    //Get params for signing payment (do it only after create paymentInfo object)
    private String getParamsForSigning()  {
        return paymentInfo.getParamsForSignature();
    }

    //Getters for all params payment info
    private String getSignature() {
        return paymentInfo.getSignature();
    }

    private Integer getProjectId() {
        return paymentInfo.getProjectId();
    }

    private String getPaymentId() {
        return paymentInfo.getPaymentId();
    }

    private Long getPaymentAmount()  {
        return paymentInfo.getPaymentAmount();
    }

    private String getPaymentCurrency() {
        return paymentInfo.getPaymentCurrency();
    }

    private String getPaymentDescription() {
        return paymentInfo.getPaymentDescription();
    }

    private String getCustomerId() {
        return paymentInfo.getCustomerId();
    }

    private String getRegionCode() {
        return paymentInfo.getRegionCode();
    }

    private String getLanguageCode() { //Default value is mobile device language
        return paymentInfo.getLanguageCode();
    }

    private String getToken() {
        return paymentInfo.getToken();
    }

    private String getReceiptData() {
        return paymentInfo.getReceiptData();
    }

    private Integer getBankId() {
        return paymentInfo.getBankId();
    }

    private Boolean getHideSavedWallets() {
        return paymentInfo.getHideSavedWallets();
    }

    private String getForcePaymentMethod() {
        return paymentInfo.getForcePaymentMethod();
    }

    private ODTPaymentInfo.ActionType getAction()  { //Default payment action type is ActionType.Sale
        return paymentInfo.getAction();
    }

    //Setters for payment info
    private void setSignature(String signature) {
        paymentInfo.setSignature(signature);
    }
    //Set the custom language code (see the ISO 639-1 codes list)
    private void setLanguageCode() {
        paymentInfo.setLanguageCode("language code");
    }

    private void setToken() {
        paymentInfo.setToken("token");
    }

    private void setReceiptData() {
        paymentInfo.setReceiptData("receipt data");
    }

    // if you want to hide the saved cards, pass the value - true
    private void setHideSavedWallets() {
        paymentInfo.setHideSavedWallets(false);
    }

    // For forced opening of the payment method, pass its code. Example: qiwi, card ...
    private void setForcePaymentMethod() {
        paymentInfo.setForcePaymentMethod("card");
    }

    private void setBankId() {
        paymentInfo.setBankId(123);
    }


    //Additional getters and setters
    //RecurrentInfo
    private ODTRecurrentInfo getODTRecurrentInfo() {
        return paymentInfo.getODTRecurrentInfo();
    }
    private void setRecurrentInfo(ODTRecurrentInfo recurrentInfo) {
        paymentInfo.setRecurrent(recurrentInfo);
    }
    //Screen Display Mode
    private List<ODTScreenDisplayMode> getScreenDisplayModes() {
        return paymentInfo.getODTScreenDisplayModes();
    }
    private void setODTScreenDisplayModes(List<ODTScreenDisplayMode> odtScreenDisplayModes) {
        paymentInfo.setODTScreenDisplayMode(odtScreenDisplayModes);
    }
    //Alternative variant of setter
    private void addODTScreenDisplayModes() {
        paymentInfo
                .addODTScreenDisplayMode("hide_decline_final_page")
                .addODTScreenDisplayMode("hide_success_final_page");
    }
    //AdditionalFields
    private ODTAdditionalField[] getODTAdditionalFields()  {
        return paymentInfo.getODTAdditionalFields();
    }
    private void setODTAdditionalFields(ODTAdditionalField[] odtAdditionalFields) {
        paymentInfo.setODTAdditionalFields(odtAdditionalFields);
    }
    //3DS Info
    private ODTThreeDSecureInfo getODTThreeDSecureInfo() {
        return paymentInfo.getODTThreeDSecureInfo();
    }
    private void setODTThreeDSecureInfo(ODTThreeDSecureInfo odtThreeDSecureInfo)  {
        paymentInfo.setODTThreeDSecureInfo(odtThreeDSecureInfo);
    }
    //Setters for custom payment action type (Auth, Tokenize, Verify)
    private void setDMSPayment() {
        paymentInfo.setAction(ODTPaymentInfo.ActionType.Auth);
    }
    private void setActionTokenize() {
        paymentInfo.setAction(ODTPaymentInfo.ActionType.Tokenize);
    }
    private void setActionVerify() {
        paymentInfo.setAction(ODTPaymentInfo.ActionType.Verify);
    }
    private void setAction(ODTPaymentInfo.ActionType action) {
        paymentInfo.setAction(action);
    }

    private void setupRecurrentInfo() {
        ODTRecurrentInfo recurrentInfo = new ODTRecurrentInfo(
                "R", // type
                "20", // expiry day
                "11", // expiry month
                "2030", // expiry year
                "M", // recurrent period
                "12:00:00", // start time
                "12-02-2020", // start date
                "your_recurrent_id"); // recurrent payment ID
        // Additional options if needed
        recurrentInfo.setAmount(1000);
        ODTRecurrentInfoSchedule[] odtRecurrentInfoSchedules = new ODTRecurrentInfoSchedule[] {
                new ODTRecurrentInfoSchedule("20-10-2020", 1000),
                new ODTRecurrentInfoSchedule("20-10-2020", 1000)
        };
        recurrentInfo.setSchedule(odtRecurrentInfoSchedules);
        setRecurrentInfo(recurrentInfo);
    }

    private void setupAdditionalFields() {
        ODTAdditionalField[] odtAdditionalFields = new ODTAdditionalField[] {
                new ODTAdditionalField(ODTAdditionalFieldEnums.AdditionalFieldType.customer_first_name, "Mark"),
                new ODTAdditionalField(ODTAdditionalFieldEnums.AdditionalFieldType.billing_country, "US")
        };
        setODTAdditionalFields(odtAdditionalFields);
    }

    private void setupScreenDisplayModes() {
        ArrayList<ODTScreenDisplayMode> odtScreenDisplayModes = new ArrayList<>();
        odtScreenDisplayModes.add(ODTScreenDisplayMode.HIDE_SUCCESS_FINAL_PAGE);
        odtScreenDisplayModes.add(ODTScreenDisplayMode.HIDE_DECLINE_FINAL_PAGE);
        setODTScreenDisplayModes(odtScreenDisplayModes);
    }

    // Setup 3D Secure parameters
    private void setupThreeDSecureParams() {
        ODTThreeDSecureInfo threeDSecureInfo = new ODTThreeDSecureInfo();

        ODTThreeDSecurePaymentInfo threeDSecurePaymentInfo = new ODTThreeDSecurePaymentInfo();

        threeDSecurePaymentInfo
                .setReorder("01") // This parameter indicates whether the cardholder is reordering previously purchased merchandise.
                .setPreorderPurchase("01") // This parameter indicates whether cardholder is placing an order for merchandise with a future availability or release date.
                .setPreorderDate("01-10-2019") // The date the preordered merchandise will be available.Format: dd-mm-yyyy.
                .setChallengeIndicator("01") // This parameter indicates whether challenge flow is requested for this payment.
                .setChallengeWindow("01"); // The dimensions of a window in which authentication page opens.

        ODTThreeDSecureGiftCardInfo threeDSecureGiftCardInfo = new ODTThreeDSecureGiftCardInfo();

        threeDSecureGiftCardInfo
                .setAmount(12345) // Amount of payment with prepaid or gift card denominated in minor currency units.
                .setCurrency("USD") // Currency of payment with prepaid or gift card in the ISO 4217 alpha-3 format
                .setCount(1); // Total number of individual prepaid or gift cards/codes used in purchase.

        threeDSecurePaymentInfo.setGiftCard(threeDSecureGiftCardInfo); // object with information about payment with prepaid card or gift card.

        ODTThreeDSecureCustomerInfo threeDSecureCustomerInfo = new ODTThreeDSecureCustomerInfo();
        threeDSecureCustomerInfo
                .setAddressMatch("Y") //The parameter indicates whether the customer billing address matches the address specified in the shipping object.
                .setHomePhone("79105211111") // Customer home phone number.
                .setWorkPhone("73141211111") // Customer work phone number.
                .setBillingRegionCode("ABC"); // State, province, or region code in the ISO 3166-2 format. Example: SPE for Saint Petersburg, Russia.

        ODTThreeDSecureAccountInfo threeDSecureAccountInfo = new ODTThreeDSecureAccountInfo(); // object with account information on record with merchant

        threeDSecureAccountInfo
                .setActivityDay(22) // Number of card payment attempts in the last 24 hours.
                .setActivityYear(222) // Number of card payment attempts in the last 365 days.
                .setAdditional("gamer12345") // Additional customer account information, for instance arbitrary customer ID.
                .setAgeIndicator("01") // Number of days since the customer account was created.
                .setDate("01-10-2019") // Account creation date.
                .setChangeDate("01-10-2019") // Last account change date except for password change or password reset.
                .setChangeIndicator("01") // Number of days since last customer account update, not including password change or reset.
                .setPassChangeDate("01") // Last password change or password reset date.
                .setPassChangeIndicator("01-10-2019") // Number of days since the last password change or reset.
                .setProvisionAttempts(16) // Number of attempts to add card details in customer account in the last 24 hours.
                .setPurchaseNumber(12) // Number of purchases with this cardholder account in the previous six months.
                .setAuthData("login_0102") // Any additional log in information in free text.
                .setAuthTime("01-10-2019 13:12") // Account log on date and time.
                .setAuthMethod("01") // Authentication type the customer used to log on to the account when placing the order.
                .setSuspiciousActivity("01") // Suspicious activity detection result.
                .setPaymentAge("01-10-2019") // Card record creation date.
                .setPaymentAgeIndicator("01"); //  Number of days since the payment card details were saved in a customer account.

        ODTThreeDSecureShippingInfo threeDSecureShippingInfo = new ODTThreeDSecureShippingInfo(); // object that contains shipment details

        threeDSecureShippingInfo
                .setType("01") //Shipment indicator.
                .setDeliveryTime("01") //Shipment terms.
                .setDeliveryEmail("test@gmail.com") // The email to ship purchased digital content, if the customer chooses email delivery.
                .setAddressUsageIndicator("01") // Number of days since the first time usage of the shipping address.
                .setAddressUsage("01-10-2019") // First shipping address usage date in the dd-mm-yyyy format.
                .setCity("Moscow") // Shipping city.
                .setCountry("RU") // Shipping country in the ISO 3166-1 alpha-2 format
                .setAddress("Lenina street 12") // Shipping address.
                .setPostal("109111") // Shipping postbox number.
                .setRegionCode("MOW") // State, province, or region code in the ISO 3166-2 format.
                .setNameIndicator("01"); // Shipment recipient flag.

        ODTThreeDSecureMpiResultInfo threeDSecureMpiResultInfo = new ODTThreeDSecureMpiResultInfo(); // object that contains information about previous customer authentication

        threeDSecureMpiResultInfo
                .setAcsOperationId("321412-324-sda23-2341-adf12341234") // The ID the issuer assigned to the previous customer operation and returned in the acs_operation_id parameter inside the callback with payment processing result. Maximum 30 characters.
                .setAuthenticationFlow("01") // The flow the issuer used to authenticate the cardholder in the previous operation and returned in the authentication_flow parameter of the callback with payment processing results.
                .setAuthenticationTimestamp("21323412321324"); // Date and time of the previous successful customer authentication as returned in the mpi_timestamp parameter inside the callback message with payment processing result.

        threeDSecureCustomerInfo
                .setAccountInfo(threeDSecureAccountInfo) // object with account information on record with merchant
                .setMpiResultInfo(threeDSecureMpiResultInfo) // object that contains information about previous customer authentication
                .setShippingInfo(threeDSecureShippingInfo); // object that contains shipment details


        threeDSecureInfo.setThreeDSecureCustomerInfo(threeDSecureCustomerInfo);
        threeDSecureInfo.setThreeDSecurePaymentInfo(threeDSecurePaymentInfo);

        setODTThreeDSecureInfo(threeDSecureInfo);
    }

    private void setupGooglePay() {
        paymentInfo.setMerchantId("your merchant id");
        paymentInfo.setPaymentDataRequest(PaymentDataRequest.fromJson(GooglePayJsonParams.ExampleJSON));
    }
}
