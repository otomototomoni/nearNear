package com.example.nearnear.Gps

import androidx.fragment.app.DialogFragment
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings

class MyDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val title = arguments?.getString(ARG_TITLE) ?: "タイトル"
        val message = arguments?.getString(ARG_MESSAGE) ?: "メッセージ"
        val action = arguments?.getString(ARG_ACTION) ?: Settings.ACTION_SETTINGS

        return AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("設定") { _, _ ->
                val intent = if (action == Settings.ACTION_APPLICATION_DETAILS_SETTINGS) {
                    Intent(action).apply {
                        data = Uri.fromParts("package", requireContext().packageName, null)
                    }
                } else {
                    Intent(action)
                }
                startActivity(intent)
            }
            .setNegativeButton("キャンセル") { _, _ ->
                dismiss()
            }
            .create()
    }

    companion object {
        private const val ARG_TITLE = "title"
        private const val ARG_MESSAGE = "message"
        private const val ARG_ACTION = "action"

        fun newInstance(title: String, message: String, action: String): MyDialogFragment {
            return MyDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE, title)
                    putString(ARG_MESSAGE, message)
                    putString(ARG_ACTION, action)
                }
            }
        }
    }

    fun showMyDialogFragment(){
        val dialogFragment = MyDialogFragment.newInstance(
            "",
            "",
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        )
        dialogFragment.show(childFragmentManager, "my_dialog")
    }
}