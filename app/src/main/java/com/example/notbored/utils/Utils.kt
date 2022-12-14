package com.example.notbored.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.CheckBox
import com.example.notbored.model.BoredPreferences

const val TAG = "bootcamp"
const val BORED_PREFERENCES = "bored"


object Utils {

    /**
     * receives a checkbox and creates a custom one with a spannable clickable string
     * for terms and conditions
     * @param checkBox [CheckBox] checkbox for terms and conditions
     * @param function executes this functions when clickable string is clicked
     * */
    fun customCheckBox(checkBox: CheckBox, function: () -> Unit) {
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                widget.cancelPendingInputEvents()
                function()
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = true
                ds.isFakeBoldText = true
            }
        }

        val partialLinkText = SpannableString("Agree on Terms and Conditions")
        partialLinkText.setSpan(
            clickableSpan,
            9,
            partialLinkText.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        checkBox.text = partialLinkText
        checkBox.movementMethod = LinkMovementMethod.getInstance()
    }

    /**
     * @return the desired value from shared preferences folder
     * @param key [BoredPreferences] enum class with specific preference value
     * @sample BoredPreferences.PARTICIPANTS
     * @sample BoredPreferences.MAX_PRICE
     * @sample BoredPreferences.MIN_PRICE
     * */
    fun getSharedValue(context: Context, key: BoredPreferences): Any {
        val boredPreferences = context.getSharedPreferences(BORED_PREFERENCES, MODE_PRIVATE)
        val editPrefs = boredPreferences.edit()
        val value = when (key) {
            BoredPreferences.PARTICIPANTS -> boredPreferences.getInt(key.value, 0)
            BoredPreferences.MAX_PRICE -> boredPreferences.getFloat(key.value, 0f)
            BoredPreferences.MIN_PRICE -> boredPreferences.getFloat(key.value, 0f)
        }
        editPrefs.apply()
        return value

    }


}