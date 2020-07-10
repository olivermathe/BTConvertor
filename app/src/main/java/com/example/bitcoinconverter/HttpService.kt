package com.example.bitcoinconverter

import android.util.Log
import org.json.JSONArray
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.Charset
import java.nio.file.Paths

class HttpService {

    val HOST = "https://www.mercadobitcoin.net/api/BTC/ticker/";

    fun requestLastPrice() : Double? {
        val data = request()?.getJSONObject("ticker")
        return data?.getString("last")?.toDouble();
    }

    private fun request(): JSONObject? {
        try {
            val url = URL(HOST)
            val conn = connect(url)
            val responseCode = conn.responseCode
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw IOException("Error on connect STATUS CODE: $responseCode");
            }
            val inputStream = conn.inputStream
            val jsonString = streamToString(inputStream)
            return JSONObject(jsonString)
        } catch (e: IOException) {
            println("sdihbasdhbasjdb")
            Log.e("Error: ", e.message);
            e.printStackTrace();
        }
        return null
    }

    private fun connect(url: URL): HttpURLConnection {
        val second = 1000
        val connection = (url.openConnection() as HttpURLConnection).apply {
            readTimeout = 10 * second
            connectTimeout = 15 * second
            requestMethod = "GET"
            doInput = true
            doOutput = false
        }
        connection.connect()
        return connection
    }

    private fun streamToString(inputStream: InputStream):String{
        val buffer =ByteArray(1024)
        val bigBuffer = ByteArrayOutputStream()
        var bytesRead: Int
        while (true){
            bytesRead = inputStream.read(buffer)
            if (bytesRead == -1) break
            bigBuffer.write(buffer,0,bytesRead)
        }
        return String(bigBuffer.toByteArray(), Charset.forName("UTF-8"))
    }

}