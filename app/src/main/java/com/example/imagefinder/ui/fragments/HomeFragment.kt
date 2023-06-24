package com.example.imagefinder.ui.fragments

import UserModel
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.imagefinder.R
import com.example.imagefinder.model.Dog
import com.example.imagefinder.model.Profile

import com.example.imagefinder.util.DataFinder
import io.paperdb.Paper
import kotlinx.android.synthetic.main.fragment_home.ivImage
import kotlinx.android.synthetic.main.fragment_home.ivRefresh
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class HomeFragment : Fragment(), CoroutineScope {

    private lateinit var image: String

    private var job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        Paper.init(context)
        launch {
            val result = fetchData()
            onResult(result) // onResult is called on the main thread
        }

        launch {
            val result = getProfileData()
            onResult2(result) // onResult is called on the main thread
        }

        ivRefresh?.setOnClickListener {
            launch {
                val result = fetchData()
                onResult(result) // onResult is called on the main thread
            }
        }

    }

    private fun onResult2(result: UserModel) {
        var loc = result.results?.get(0)?.location
//        var n = result.results?.get(0)?.name
        var d = result.results?.get(0)?.dob?.date?.split("[a-zA-Z]".toRegex())
        var r = result.results?.get(0)?.registered?.date?.split("[a-zA-Z]".toRegex())
        Log.d(
            "TAG", "result -> ${result.results?.get(0)?.name}\n" +
                    "${loc?.country} \t ${loc?.state} \t ${loc?.city}\n" +
                    "${result.results?.get(0)?.email}\n" +
                    "${d?.get(0)}\n" +
                    "${r?.get(0)}\n" +
                    "${result.results?.get(0)?.picture?.large}"
        )
        var p = Profile()

        p.name = result.results?.get(0)?.name?.first + " " + result.results?.get(0)?.name?.last
        p.location = "${loc?.country}  ${loc?.state}  ${loc?.city}\n"
        p.email = result.results?.get(0)?.email
        p.dob = d?.get(0)
        p.noDays = r?.get(0)
        p.image = result.results?.get(0)?.picture?.large

        try {
            Paper.init(context)
            Paper.book().write("profile", p)
        } catch (e: Exception) {
        }
    }

    private fun onResult(result: Dog) {
        Log.d("TAG", "result -> $result")
        Glide.with(context!!).load(result.message).into(ivImage)
    }

    suspend fun fetchData(): Dog {
        return DataFinder.getData("https://dog.ceo/api/breeds/image/random")
    }

    suspend fun getProfileData(): UserModel {
        return DataFinder.getProfileData("https://randomuser.me/api/")
    }

}