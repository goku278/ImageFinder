package com.example.imagefinder.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.imagefinder.R
import com.example.imagefinder.model.Profile
import io.paperdb.Paper
import kotlinx.android.synthetic.main.fragment_profile.civImage
import kotlinx.android.synthetic.main.fragment_profile.tvDob
import kotlinx.android.synthetic.main.fragment_profile.tvEmail
import kotlinx.android.synthetic.main.fragment_profile.tvLocation
import kotlinx.android.synthetic.main.fragment_profile.tvName
import kotlinx.android.synthetic.main.fragment_profile.tvReg

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {

            Paper.init(context)
        } catch (e: Exception) {
        }

//         Perform any additional setup or view manipulation here

        var p = Paper.book().read<Profile>("profile", Profile())

        tvName?.text = p.name
        tvLocation?.text = p.location
        tvEmail?.text = p.email
        tvDob?.text = p.dob
        tvReg?.text = p.noDays

        Glide.with(context!!).load(p.image).into(civImage)
    }
}