package com.training.whatsthenews

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.training.whatsthenews.databinding.ActivitySettingBinding
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class setting : AppCompatActivity() {
    //private val settingsViewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val binding = ActivitySettingBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val toolbar = findViewById<Toolbar>(R.id.app_tool_bar)
        setSupportActionBar(toolbar)
// Disable default title
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val title = toolbar.findViewById<TextView>(R.id.toolbar_title)
        val backBtn = toolbar.findViewById<ImageView>(R.id.back_btn)
        backBtn.isVisible = false
        title.text = "Settings"

        val radioGroup: RadioGroup = binding.radiogrouo
        val btn: Button = binding.aplly

        btn.setOnClickListener {
            val selectedOption = radioGroup.checkedRadioButtonId
            val radioButton = findViewById<RadioButton>(selectedOption)

            val selectedCountry = when (radioButton.text.toString()) {
                "eg" -> "Egypt"
                "us" -> "US"
                "France" -> "fr"
                "Germany" -> "de"  // Corrected to "de" for Germany
                "England" -> "gb"
                else -> "us"
            }

            // Save to SharedPreferences as well
            val sharedPref = getSharedPreferences("newsAppPref", Context.MODE_PRIVATE)


            with(sharedPref.edit()) {
                putString("selectedCountry", selectedCountry)
                apply()
            }

            val country=when(selectedCountry){
                "eg" -> "Egypt"
                "us" -> "US"
                "fr" -> "France"
                "de" -> "Germany"
                "gb" -> "England"
                else -> "us"
            }


            Toast.makeText(this, "Selected Country: $country", Toast.LENGTH_SHORT).show()
        }
    }
}