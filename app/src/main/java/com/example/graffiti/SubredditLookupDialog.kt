package com.example.graffiti

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment

class SubredditLookupDialog() : DialogFragment() {

    lateinit var btn: Button
    lateinit var input: EditText

    interface SubredditDialogListener {
        fun onSubredditDialogSearched(dialog: SubredditLookupDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.gotosub_dialog, container, false)
        dialog?.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        input = rootView.findViewById(R.id.sub_name_input)
        btn = rootView.findViewById(R.id.search_sub_btn)
        try {
            val ctx = context as MainActivity
            ctx.onSubredditDialogSearched(this)
        } catch (e: Exception) {
            println(e)
        }
        return rootView
    }
}