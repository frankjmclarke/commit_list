package com.fclarke.commitlist


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.bottom_sheet_persistent.view.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class BottomSheetEx : BottomSheetDialogFragment() {

    private var mBottomSheetListener: BottomSheetListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.bottom_sheet_persistent, container, false)

        val avatarUrl = getArguments()?.getString("avatarUrl")
        val dateIn = getArguments()?.getString("dateIn")
        val loginOut = getArguments()?.getString("loginOut")
        val htmlUrl = getArguments()?.getString("htmlUrl")
        val sha = getArguments()?.getString("sha")
        val message = getArguments()?.getString("message")
        val iv: ImageView = v.findViewById(R.id.imageView1)
        Picasso.get().load(avatarUrl).into(iv)

        val dateInV: TextView = v.findViewById(R.id.dateIn)
        val loginOutV: TextView = v.findViewById(R.id.loginOut)
        val messageV: TextView = v.findViewById(R.id.message)
        val shaV: TextView = v.findViewById(R.id.sha)
        shaV.setText("SHA: " + sha)
        dateInV.setText("Date: " + dateIn)
        loginOutV.setText("Login: " + loginOut)
        messageV.setText(message)

        v.imageView1.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(htmlUrl)
            startActivity(intent)
        }

        return v
    }

    interface BottomSheetListener {
        fun onOptionClick(text: String)
    }

}