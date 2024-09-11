package com.training.whatsthenews

import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.training.whatsthenews.databinding.FragmentSignupBinding
import com.training.whatsthenews.databinding.FragmentSplashBinding


class SplashFragment : Fragment() {
   private lateinit var binding: FragmentSplashBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        firebaseAuth = FirebaseAuth.getInstance()

        binding = FragmentSplashBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rotation = ObjectAnimator.ofFloat(binding.newsLogo, "rotationY", 0f, 360f)
        rotation.duration = 3000
        rotation.start()

        val navHostFragment =
           requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController


        Handler(Looper.getMainLooper()).postDelayed({
            if(isAdded){
                if(firebaseAuth.currentUser == null){
                    navController.navigate(R.id.signupFragment)
                }
                else{
                    findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
                }

        }
        },3000)





    }


}