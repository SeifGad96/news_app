package com.training.whatsthenews

import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.training.whatsthenews.databinding.FragmentSignupBinding
import com.training.whatsthenews.databinding.FragmentSplashBinding


class SplashFragment : Fragment() {
   private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSplashBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            if(isAdded){
         findNavController().navigate(R.id.action_splashFragment_to_homeFragment)}
        },3000)

       val rotation = ObjectAnimator.ofFloat(binding.newsLogo, "rotationY", 0f, 360f)
        rotation.duration = 3000
        rotation.start()



    }


}