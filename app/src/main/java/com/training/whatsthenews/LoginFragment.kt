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
import com.training.whatsthenews.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {
    private lateinit var firebaseAuth: FirebaseAuth

  private lateinit var binding:FragmentLoginBinding
    private lateinit var mContext: Context


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mContext = requireContext()
        binding=FragmentLoginBinding.inflate(layoutInflater,container,false)

        // Inflate the layout for this fragment
        firebaseAuth = FirebaseAuth.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.goToSignup.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }

        binding.btnLogin.setOnClickListener {

                val email = binding.emailEt.text.toString().trim()
                val pass = binding.passET.text.toString()

                if (email.isNotEmpty() && pass.isNotEmpty()) {

                    firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful) {
                            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                        } else {
                            Toast.makeText(mContext, it.exception.toString(), Toast.LENGTH_SHORT).show()

                        }
                    }
                } else {
                    Toast.makeText(mContext, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()

                }

        }
        binding.btnForgotPass.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)

        }



    }






}
