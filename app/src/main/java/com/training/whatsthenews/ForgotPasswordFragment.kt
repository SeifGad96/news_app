package com.training.whatsthenews

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.training.whatsthenews.databinding.FragmentForgotPasswordBinding


class ForgotPasswordFragment : Fragment() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: FragmentForgotPasswordBinding
    private lateinit var mContext: Context


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mContext = requireContext()
        binding = FragmentForgotPasswordBinding.inflate(layoutInflater, container, false)
        firebaseAuth = FirebaseAuth.getInstance()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSendemail.setOnClickListener {
            val email = binding.emailEt.text.toString().trim()
            if (email.isNotEmpty()) {
                // reset password
                firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(
                            mContext,
                            "Reset Password link has been sent to your registered Email",
                            Toast.LENGTH_SHORT
                        ).show()
                        findNavController().navigate(R.id.action_forgotPasswordFragment_to_loginFragment)

                    } else {
                        Toast.makeText(mContext, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }

            } else {
                Toast.makeText(mContext, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT)
                    .show()
            }


        }
    }


}