package com.example.imagefinder.util

import UserModel
import android.util.Log
import com.example.imagefinder.model.Dog
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

object DataFinder {

    /*fun getData(url: String): Observable<Dog> {

        return Observable.fromCallable {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(url)
                .build()

            val response: Response = client.newCall(request).execute()
            val responseData = response.body()?.string()

            // Use Gson library to parse JSON response into User object
            val gson = Gson()
            val dog = gson.fromJson(responseData, Dog::class.java)

            dog
        }.subscribeOn(Schedulers.io())
    }*/

    /*fun getData(url: String): String {
        var connection: HttpURLConnection? = null
        val response = StringBuilder()

        try {
            // Create URL object
            val urlObject = URL(url)

            // Open connection
            connection = urlObject.openConnection() as HttpURLConnection

            // Set request method
            connection.requestMethod = "GET"

            // Get response code
            val responseCode = connection.responseCode

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read response data
                val reader = BufferedReader(InputStreamReader(connection.inputStream))
                var line: String?

                while (reader.readLine().also { line = it } != null) {
                    response.append(line)
                }

                Log.d("TAG","response -> $response")

                // Close the reader
                reader.close()
            } else {
                throw IOException("HTTP error code: $responseCode")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            // Disconnect the connection
            connection?.disconnect()
        }

        return response.toString()
    }*/

    suspend fun getData(url: String): Dog = withContext(Dispatchers.IO) {
        var connection: HttpURLConnection? = null
        val response = StringBuilder()
        var data = Dog()
        try {
            // Create URL object
            val urlObject = URL(url)

            // Open connection
            connection = urlObject.openConnection() as HttpURLConnection

            // Set request method
            connection.requestMethod = "GET"

            // Get response code
            val responseCode = connection.responseCode

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read response data
                val reader = BufferedReader(InputStreamReader(connection.inputStream))
                val responseData = reader.use(BufferedReader::readText)

                // Parse JSON into User object
                val gson = Gson()
                data = gson.fromJson(responseData, Dog::class.java)

                // Close the reader
                reader.close()
            } else {
                throw IOException("HTTP error code: $responseCode")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            // Disconnect the connection
            connection?.disconnect()
        }

        data
    }

    suspend fun getProfileData(url: String): UserModel = withContext(Dispatchers.IO) {
        var connection: HttpURLConnection? = null
        val response = StringBuilder()
        var data = UserModel()
        try {
            // Create URL object
            val urlObject = URL(url)

            // Open connection
            connection = urlObject.openConnection() as HttpURLConnection

            // Set request method
            connection.requestMethod = "GET"

            // Get response code
            val responseCode = connection.responseCode

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read response data
                val reader = BufferedReader(InputStreamReader(connection.inputStream))
                val responseData = reader.use(BufferedReader::readText)

                // Parse JSON into User object
                val gson = Gson()
                try {
                    data = gson.fromJson(responseData, UserModel::class.java)
                } catch (e: Exception) {
                    Log.d("TAG", "exception is -> ${e.localizedMessage}")
                }

                Log.d("TAG", "data -> $data")

                // Close the reader
                reader.close()
            } else {
                throw IOException("HTTP error code: $responseCode")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            // Disconnect the connection
            connection?.disconnect()
        }

        data
    }


}
