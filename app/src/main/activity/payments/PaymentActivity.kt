package com.bayuaji.midtrans.payments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.bayuaji.midtrans.R
import com.midtrans.sdk.corekit.core.MidtransSDK
import com.midtrans.sdk.corekit.core.TransactionRequest
import com.midtrans.sdk.corekit.core.UIKitCustomSetting
import com.midtrans.sdk.corekit.core.themes.CustomColorTheme
import com.midtrans.sdk.corekit.models.CustomerDetails
import com.midtrans.sdk.corekit.models.ItemDetails
import com.midtrans.sdk.uikit.SdkUIFlowBuilder

class PaymentActivity : AppCompatActivity() {
    private var btnPesan: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        bindViews()
        initActionButtons()
        initMidtransSdk()
    }

    private fun initMidtransSdk() {
        val sdkUIFlowBuilder: SdkUIFlowBuilder = SdkUIFlowBuilder.init()
            .setClientKey("SB-Mid-client-qk4Ar-kOiBOHaqxl") // client_key is mandatory
            .setContext(this) // context is mandatory
            .setTransactionFinishedCallback {
                // Handle finished transaction here.
            } // set transaction finish callback (sdk callback)
            .setMerchantBaseUrl("http://192.168.18.22/mazbaystore/public/") //set merchant url
            .setUIkitCustomSetting(uiKitCustomSetting())
            .enableLog(true) // enable sdk log
            .setColorTheme(CustomColorTheme("#FFE51255", "#B61548", "#FFE51255")) // will replace theme on snap theme on MAP
            .setLanguage("id")
        sdkUIFlowBuilder.buildSDK()
    }

    private fun initTransactionRequest(price: Double): TransactionRequest {
        // Create new Transaction Request
        val transactionRequestNew = TransactionRequest("mazbay-store-"+System.currentTimeMillis().toString() + "", price)
//        transactionRequestNew.itemDetails = initItemDetails(price)
//        transactionRequestNew.customerDetails = initCustomerDetails()
        return transactionRequestNew
    }

    private fun initCustomerDetails(): CustomerDetails? {
        val customer_details = CustomerDetails()
        customer_details.customerIdentifier = "Naruto"
        customer_details.firstName = "Naruto"
        customer_details.lastName = "Uzumaki"
        customer_details.email = "narutouzumaki@test.com"
        customer_details.phone = "+625200411423"

        return customer_details
    }

    private fun initActionButtons() {
        btnPesan!!.setOnClickListener {
            val etHarga: EditText = findViewById(R.id.etHarga)
            val getHarga = etHarga.text.toString().toDouble()
            MidtransSDK.getInstance().transactionRequest = initTransactionRequest(getHarga)
            MidtransSDK.getInstance().startPaymentUiFlow(this@PaymentActivity)
        }
    }

    private fun initItemDetails(price: Double): ArrayList<ItemDetails> {
        val itemDetail_1: ItemDetails = ItemDetails("ProdukID", price, 1, "Item 1")
        val item_array = ArrayList<ItemDetails>()

        item_array.add(itemDetail_1)

        return item_array
    }

    private fun bindViews() {
        btnPesan = findViewById(R.id.btnPesan)
    }

    private fun uiKitCustomSetting(): UIKitCustomSetting {
        val uIKitCustomSetting = UIKitCustomSetting()
        uIKitCustomSetting.isSkipCustomerDetailsPages = true
        uIKitCustomSetting.isShowPaymentStatus = true
        return uIKitCustomSetting
    }
}