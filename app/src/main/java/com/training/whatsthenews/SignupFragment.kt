package com.training.whatsthenews

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.training.whatsthenews.databinding.FragmentLoginBinding
import com.training.whatsthenews.databinding.FragmentSignupBinding


class SignupFragment : Fragment() {
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var binding: FragmentSignupBinding
    private lateinit var mContext: Context

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mContext = requireContext()
        binding = FragmentSignupBinding.inflate(layoutInflater, container, false)


        firebaseAuth = FirebaseAuth.getInstance()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Apply 2D animations to views
        val bounceAnim = AnimationUtils.loadAnimation(context, R.anim.scale)
        val fadeInAnim = AnimationUtils.loadAnimation(context, R.anim.alpha)

//        binding.tvWelcome.startAnimation(bounceAnim)
//        binding.buttonSignup.startAnimation(fadeInAnim)

        binding.tvHaveAccount.setOnClickListener {
            findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
        }
        binding.buttonSignup.setOnClickListener {
            val email = binding.emailEt.text.toString().trim()
            val pass = binding.passET.text.toString()
            val confirmPass = binding.confirmPassEt.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()) {
                var valid = true
                if (!isValidEmail(email)) {
                    binding.emailEt.error = "Invalid email format"
                    valid = false
                } else {
                    binding.emailEt.error = null
                }

                if (!isValidPassword(pass)) {
                    binding.passET.error = "Password should contain capital , small letters and numberst"
                    valid = false
                } else {
                    binding.passET.error = null
                }
                if (pass == confirmPass) {
                    if (valid) {

                        firebaseAuth.createUserWithEmailAndPassword(email, pass)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    val user = firebaseAuth.currentUser
                                    user?.sendEmailVerification()?.addOnCompleteListener {
                                        if (it.isSuccessful) {
                                            Toast.makeText(
                                                mContext,
                                                "Verification email sent. Please check your email.",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
                                        } else {
                                            Toast.makeText(
                                                mContext,
                                                it.exception?.message,
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }


                                } else {
                                    Toast.makeText(
                                        mContext,
                                        it.exception?.message,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    } else {
                        Toast.makeText(mContext, "Password not match", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }else {
                Toast.makeText(mContext, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT)
                    .show()

            }

        }

//        binding.motionLayout.progress = 0f
//        binding.motionLayout.transitionToEnd()



    }

    override fun onStart() {
        super.onStart()
        val navHostFragment =
            requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        if (firebaseAuth.currentUser != null) {
            findNavController().navigate(R.id.action_signupFragment_to_homeFragment)
        }
    }



    private fun isValidPassword(password: String): Boolean {
        return password.length >= 8 &&
                password.contains(Regex("[A-Z]")) &&
                password.contains(Regex("[a-z]")) &&
                password.contains(Regex("[0-9]"))
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }


}


