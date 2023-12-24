package com.ianbl8.donationx.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.FragmentActivity
import com.ianbl8.donationx.R
import com.makeramen.roundedimageview.RoundedTransformationBuilder
import com.squareup.picasso.Transformation
import java.io.IOException

fun createLoader(activity: FragmentActivity): AlertDialog {
    val loaderBuilder = AlertDialog.Builder(activity).setCancelable(true).setView(R.layout.loading)
    val loader = loaderBuilder.create()
    loader.setTitle(R.string.app_name)
    loader.setIcon(R.mipmap.ic_launcher_donationx_round)
    return loader
}

fun showLoader(loader: AlertDialog, message: String) {
    if (!loader.isShowing) {
        loader.setTitle(message)
        loader.show()
    }
}

fun hideLoader(loader: AlertDialog) {
    if (loader.isShowing) {
        loader.dismiss()
    }
}

fun serviceUnavailableMessage(activity: FragmentActivity) {
    Toast.makeText(
        activity,
        "Donation service unavailable. Please try again later.",
        Toast.LENGTH_LONG
    ).show()
}

fun serviceAvailableMessage(activity: FragmentActivity) {
    Toast.makeText(
        activity,
        "Donation service contacted successfully.",
        Toast.LENGTH_LONG
    ).show()
}

fun customTransformation(): Transformation =
    RoundedTransformationBuilder()
        .borderColor(Color.WHITE)
        .borderWidthDp(2F)
        .cornerRadiusDp(35F)
        .oval(false)
        .build()

fun showImagePicker(intentLauncher: ActivityResultLauncher<Intent>) {
    var chooseFile = Intent(Intent.ACTION_OPEN_DOCUMENT)
    chooseFile.type = "image/*"
    chooseFile = Intent.createChooser(chooseFile, R.string.select_profile_image.toString())
    intentLauncher.launch(chooseFile)
}

fun readImageUri(resultCode: Int, data: Intent?): Uri? {
    var uri: Uri? = null
    if (resultCode == Activity.RESULT_OK && data != null && data.data != null) {
        try {
            uri = data.data
        }
        catch (e: IOException) {
            e.printStackTrace()
        }
    }
    return uri
}