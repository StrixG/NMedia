package com.obrekht.nmedia.posts.ui

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.obrekht.nmedia.R
import com.obrekht.nmedia.databinding.FragmentAddEditPostBinding
import com.obrekht.nmedia.utils.focusAndShowKeyboard
import com.obrekht.nmedia.utils.viewBinding

class AddEditPostFragment : Fragment(R.layout.fragment_add_edit_post) {
    private val binding by viewBinding(FragmentAddEditPostBinding::bind)

    private val viewModel: PostsViewModel by activityViewModels()
    private val args: AddEditPostFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            tryQuit()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            toolbar.setNavigationOnClickListener {
                tryQuit(true)
            }

            buttonSave.setOnClickListener {
                val text = binding.inputField.text.toString()
                viewModel.save(text)
                findNavController().navigateUp()
            }

            inputField.setText(args.text)
            inputField.focusAndShowKeyboard()
        }
    }

    override fun onStart() {
        super.onStart()
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    override fun onStop() {
        super.onStop()
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }

    private fun tryQuit(shouldNavigateUp: Boolean = false) {
        if (binding.inputField.text.isNotBlank()) {
            showExitConfirmation(shouldNavigateUp)
        } else {
            cancelEditAndExit(shouldNavigateUp)
        }
    }

    private fun showExitConfirmation(shouldNavigateUp: Boolean = false) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.cancel_edit_post_title))
            .setMessage(resources.getString(R.string.cancel_edit_post_message))
            .setNegativeButton(resources.getString(R.string.cancel)) { _, _ -> }
            .setPositiveButton(resources.getString(R.string.delete)) { _, _ ->
                cancelEditAndExit(shouldNavigateUp)
            }
            .show()
    }

    private fun cancelEditAndExit(shouldNavigateUp: Boolean = false) {
        viewModel.cancelEdit()
        with(findNavController()) {
            if (shouldNavigateUp) {
                navigateUp()
            } else {
                popBackStack()
            }
        }
    }
}
