package com.ivan200.theming.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.TypedValue
import android.view.KeyEvent
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.ivan200.theming.Theming
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

//
// Created by Ivan200 on 28.02.2020.
//

@Parcelize
data class AlertDialogFragmentBuilder(
    var title: String? = null,
    var message: String? = null,
    var positiveButton: String? = null,
    var negativeButton: String? = null,
    var neutralButton: String? = null,
    var errorIcon: Boolean? = null,
    var cancellable: Boolean? = null,
    var items: List<String>? = null,
    var checkedItem: Int = -1,
    var checkedItems: MutableList<Boolean>? = null,
    var throwable: Throwable? = null
) : Parcelable {
    @IgnoredOnParcel
    private var titleId: Int? = null

    @IgnoredOnParcel
    private var messageId: Int? = null

    @IgnoredOnParcel
    private var positiveButtonId: Int? = null

    @IgnoredOnParcel
    private var negativeButtonId: Int? = null

    @IgnoredOnParcel
    private var neutralButtonId: Int? = null

    fun withTitle(title: CharSequence) = apply { this.title = title.toString() }
    fun withMessage(message: CharSequence) = apply { this.message = message.toString() }
    fun withPositiveButton(positiveButton: CharSequence) = apply { this.positiveButton = positiveButton.toString() }
    fun withNegativeButton(negativeButton: CharSequence) = apply { this.negativeButton = negativeButton.toString() }
    fun withNeutralButton(neutralButton: CharSequence) = apply { this.neutralButton = neutralButton.toString() }
    fun withTitle(titleId: Int) = apply { this.titleId = titleId }
    fun withMessage(messageId: Int) = apply { this.messageId = messageId }
    fun withPositiveButton(positiveButtonId: Int) = apply { this.positiveButtonId = positiveButtonId }
    fun withNegativeButton(negativeButtonId: Int) = apply { this.negativeButtonId = negativeButtonId }
    fun withNeutralButton(neutralButtonId: Int) = apply { this.neutralButtonId = neutralButtonId }
    fun withCancellable(cancellable: Boolean) = apply { this.cancellable = cancellable }
    fun withErrorIcon() = apply { this.errorIcon = true }
    fun withoutErrorIcon() = apply { this.errorIcon = false }
    fun withItems(items: List<String>) = apply { this.items = items }
    fun withItemsChecked(checkedItems: List<Boolean>) = apply { this.checkedItems = checkedItems.toMutableList() }
    fun withItemChecked(checkedItem: Int) = apply { this.checkedItem = checkedItem }
    fun withThrowable(throwable: Throwable) = apply { this.throwable = throwable }

    companion object {
        val parcelTag = AlertDialogFragmentBuilder::class.java.simpleName
    }

    fun show(activity: FragmentActivity, requestCode: Int) {
        show(activity, requestCode, activity.supportFragmentManager.fragments[0], activity.supportFragmentManager)
    }

    fun show(currentFragment: Fragment, requestCode: Int) {
        show(currentFragment.requireContext(), requestCode, currentFragment, currentFragment.parentFragmentManager)
    }

    private fun show(context: Context, requestCode: Int, targetFragment: Fragment?, fragmentManager: FragmentManager) {
        titleId?.let { title = context.getString(it) }
        messageId?.let { message = context.getString(it) }
        positiveButtonId?.let { positiveButton = context.getString(it) }
        negativeButtonId?.let { negativeButton = context.getString(it) }
        neutralButtonId?.let { neutralButton = context.getString(it) }

        val dialog = AlertDialogFragment()
        dialog.arguments = Bundle().also { it.putParcelable(parcelTag, this@AlertDialogFragmentBuilder) }
        targetFragment!!.apply { dialog.setTargetFragment(this, requestCode) }
        dialog.show(fragmentManager, AlertDialogFragment::class.java.simpleName)
    }

    class AlertDialogFragment : DialogFragment() {
        companion object {
            val EXTRA_SINGLE_CHOISE = "EXTRA_SINGLE_CHOISE"
            val EXTRA_MULTI_CHOISE = "EXTRA_MULTI_CHOISE"
        }

        override fun onStart() {
            super.onStart()
            Theming.themeAlertDialog(requireDialog() as AlertDialog)
        }

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val args: Bundle = requireArguments()
            val b1 = args.getParcelable<AlertDialogFragmentBuilder>(
                parcelTag
            )

            val builder = AlertDialog.Builder(requireContext())

            b1?.apply {
                builder.setTitle(title ?: requireContext().getString(android.R.string.dialog_alert_title))

                if (errorIcon == true || (errorIcon == null && throwable != null)) {
                    getErrorIconId().let { builder.setIcon(it) }
                }

                val currMessage = getExceptionText(message, throwable)

                if (items != null) {
                    if (checkedItems != null) {
                        val clickListener = DialogInterface.OnMultiChoiceClickListener { dialog, which, isChecked ->
                            checkedItems!![which] = isChecked
                        }
                        builder.setMultiChoiceItems(items!!.toTypedArray(), checkedItems!!.toBooleanArray(), clickListener)
                    } else {
                        builder.setSingleChoiceItems(items!!.toTypedArray(), checkedItem) { dialog, which ->
                            targetFragment!!.onActivityResult(
                                targetRequestCode,
                                DialogInterface.BUTTON_POSITIVE,
                                Intent().putExtra(EXTRA_SINGLE_CHOISE, which)
                            )
                        }
                    }
                } else {
                    builder.setMessage(currMessage)
                }

                val positiveButtonText = if (items == null && positiveButton.isNullOrEmpty()) {
                    requireContext().getString(android.R.string.ok)
                } else {
                    positiveButton
                }
                if (!positiveButtonText.isNullOrEmpty()) {
                    builder.setPositiveButton(positiveButtonText) { dialog, id ->
                        targetFragment!!.onActivityResult(
                            targetRequestCode,
                            DialogInterface.BUTTON_POSITIVE,
                            if (checkedItems == null) {
                                null
                            } else {
                                Intent().putExtra(EXTRA_MULTI_CHOISE, checkedItems!!.toBooleanArray())
                            }
                        )
                    }
                }

                if (!negativeButton.isNullOrEmpty()) {
                    builder.setNegativeButton(negativeButton) { dialog, id ->
                        targetFragment!!.onActivityResult(targetRequestCode, DialogInterface.BUTTON_NEGATIVE, null)
                    }
                }

                if (!neutralButton.isNullOrEmpty()) {
                    builder.setNeutralButton(neutralButton) { dialog, id ->
                        targetFragment!!.onActivityResult(targetRequestCode, DialogInterface.BUTTON_NEUTRAL, null)
                    }
                }

                if (cancellable == false) {
                    builder.setCancelable(false)
                } else {
                    builder.setCancelable(true)
                    builder.setOnCancelListener { dialog: DialogInterface ->
                        targetFragment!!.onActivityResult(targetRequestCode, Activity.RESULT_CANCELED, null)
                    }
                    builder.setOnKeyListener { arg0, keyCode, event ->
                        if (keyCode == KeyEvent.KEYCODE_BACK) {
                            targetFragment!!.onActivityResult(targetRequestCode, Activity.RESULT_CANCELED, null)
                        }
                        true
                    }
                }
            }
            return builder.create()
        }

        open fun getExceptionText(exMessage: String?, ex: Throwable?): String {
            return arrayOf(exMessage, ex?.message).filter { !it.isNullOrBlank() }.joinToString("\n")
        }

        @DrawableRes
        open fun getErrorIconId(): Int {
            return context?.theme?.let {
                val typedValueAttr = TypedValue()
                it.resolveAttribute(android.R.attr.alertDialogIcon, typedValueAttr, true)
                typedValueAttr.resourceId
            } ?: android.R.drawable.ic_dialog_alert
        }
    }
}
