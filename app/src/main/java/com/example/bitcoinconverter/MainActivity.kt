package com.example.bitcoinconverter

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val service = HttpService();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            ConvertTask().execute();
        }

    }

    private fun convert(btcValue: Double) {
        try {
            val valor = value.text.toString().toDouble();
            val result = valor / btcValue;
            resultado.text = "%.2f".format(result) + " BTC";
        } catch (e: Exception) {
            println(e.localizedMessage)
        }
    }

    inner class ConvertTask: AsyncTask<Void, Void, Double?>() {
        override fun doInBackground(vararg params: Void?): Double? {
            return service.requestLastPrice();
        }

        override fun onPostExecute(result: Double?) {
            super.onPostExecute(result)
            if (result != null) {
                convert(result);
            }
        }
    }


}