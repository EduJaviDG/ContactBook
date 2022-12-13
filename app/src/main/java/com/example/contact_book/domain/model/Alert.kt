package com.example.contact_book.domain.model

import android.app.AlertDialog
import android.content.Context
import androidx.core.content.ContextCompat
import com.example.contact_book.R

abstract class Alert {

companion object{

    fun showSuccess(message: String, context: Context, resultBoolean: (Boolean) -> Unit) {

        val builder = AlertDialog.Builder(context,R.style.AlertDialogCustom)

        builder.setTitle(R.string.success)

        builder.setMessage(message)

        builder.setPositiveButton(R.string.accept){ _,_ ->

            resultBoolean(true)

        }

        val dialog: AlertDialog = builder.create().apply {

            val backgroundDrawable = ContextCompat.
            getDrawable(context, R.drawable.custom_alert_success)

            window?.setBackgroundDrawable(backgroundDrawable)
        }

        dialog.show()

        val color = ContextCompat.getColor(context, R.color.success)

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(color)

    }


    fun showError(message: String, context: Context){

        val builder = AlertDialog.Builder(context,R.style.AlertDialogCustom)

        builder.setTitle(R.string.error)

        builder.setMessage(message)

        builder.setPositiveButton(R.string.accept,null)

        val dialog = builder.create().apply {

            val backgroundDrawable = ContextCompat.getDrawable(context, R.drawable.custom_alert_error)

            window?.setBackgroundDrawable(backgroundDrawable)
        }

        dialog.show()

        val color = ContextCompat.getColor(context, android.R.color.holo_red_dark)

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(color)


    }

    fun showDelete(message: String, title: String, context: Context,resultBoolean: (Boolean) ->Unit){

        val builder = AlertDialog.Builder(context,R.style.AlertDialogCustomDelete)

        builder.setTitle(title)

        builder.setMessage(message)

        builder.setPositiveButton(R.string.accept){_,_ -> resultBoolean(true) }

        builder.setNegativeButton(R.string.cancel){_,_ ->}

        val dialog = builder.create().apply {

            val backgroundDrawable = ContextCompat.getDrawable(context,R.drawable.custom_alert_error)

            window?.setBackgroundDrawable(backgroundDrawable)

        }

        dialog.show()

        val color = ContextCompat.getColor(context, android.R.color.holo_red_dark)

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(color)

        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(color)

    }




}


    }