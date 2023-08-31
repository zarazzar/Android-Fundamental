package com.dicoding.myflexiblefragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.dicoding.myflexiblefragment.databinding.FragmentOptionDialogBinding

class OptionDialogFragment : DialogFragment() {

    private var optionDialogListener: OnOptionDialogListener? = null

    private lateinit var binding: FragmentOptionDialogBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOptionDialogBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnChoose.setOnClickListener {
            val checkedRadioButtonId = binding.rgOption.checkedRadioButtonId
            if (checkedRadioButtonId != -1) {
                var coach: String? = when (checkedRadioButtonId) {
                    R.id.rb_saf -> binding.rbSaf.text.toString().trim()
                    R.id.rb_mou -> binding.rbMou.text.toString().trim()
                    R.id.rb_lvg -> binding.rbLvg.text.toString().trim()
                    R.id.rb_moyes -> binding.rbMoyes.text.toString().trim()
                    else -> null
                }
                optionDialogListener?.onOptionChosen(coach)
                dialog?.cancel()
            }
        }
    }

    interface OnOptionDialogListener {
        fun onOptionChosen(text: String?)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val fragment = parentFragment

        if (fragment is DetailCategoryFragment) {
            this.optionDialogListener = fragment.optionDialogListener
        }
    }

    override fun onDetach() {
        super.onDetach()
        this.optionDialogListener = null
    }

}