package com.gallosalocin.notepadkotlin

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class ConfirmDeleteNoteDialogFragment (val noteTitle: String? = "") : DialogFragment(){

    interface ConfirmDeleteDialogListener{
        fun onDialogPositiveClick()
        fun onDialogNegativeClick()
    }

    var listener: ConfirmDeleteDialogListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(activity)

        builder.setMessage("Êtes-vous sûr de vouloir supprimer la note :  \n\"$noteTitle\" ?")
            .setPositiveButton("Supprimer", {dialog, id -> listener?.onDialogPositiveClick() })
            .setNegativeButton("Annuler", {dialog, id -> listener?.onDialogNegativeClick() })

        return builder.create()
    }
}