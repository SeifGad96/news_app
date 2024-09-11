package com.training.whatsthenews

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.training.whatsthenews.databinding.ActivitySettingBinding


class setting : AppCompatActivity() {
    //private val settingsViewModel: SettingsViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySettingBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("newsAppPref", Context.MODE_PRIVATE)

        val toolbar = findViewById<Toolbar>(R.id.app_tool_bar)
        setSupportActionBar(toolbar)

// Disable default title

        supportActionBar?.setDisplayShowTitleEnabled(false)
        val title = toolbar.findViewById<TextView>(R.id.toolbar_title)
        val backBtn = toolbar.findViewById<ImageView>(R.id.back_btn)
        backBtn.isVisible = false
        title.text = "Settings"

        val chosenCountryFromPref: String =
            sharedPreferences.getString("selectedCountry", "us").toString()
        var chosenCountry: String = chosenCountryFromPref

        when (chosenCountryFromPref) {
            "fr" -> binding.frRb.isChecked=true//R.id.fr_rb
            "gb" -> binding.englandRb.isChecked=true//R.id.england_rb
            "eg" -> binding.egRb.isChecked=true//R.id.eg_rb
            "de" -> binding.germanyRb.isChecked=true//R.id.germany_rb
            else -> binding.usRb.isChecked=true//R.id.us_rb
        }

        val radioGroup: RadioGroup = binding.radiogrouo
        val btn: Button = binding.aplly

        btn.setOnClickListener {
            val selectedOption = radioGroup.checkedRadioButtonId
            val radioButton = findViewById<RadioButton>(selectedOption)

            chosenCountry = when (radioButton.text.toString()) {
                "eg" -> "Egypt"
                "France" -> "fr"
                "Germany" -> "de"  // Corrected to "de" for Germany
                "England" -> "gb"
                else -> "us"
            }
            saveCountryPreference(chosenCountry)
            Toast.makeText(
                this,
                "Country:$chosenCountry saved successfully!! ",
                Toast.LENGTH_SHORT
            ).show()


        }
    }

    private fun saveCountryPreference(chosenCountry: String) {
        with(sharedPreferences.edit()) {
            putString("selectedCountry", chosenCountry)
            apply()
        }
    }

}
/*//            val selectedCountry = when (radioButton.text.toString()) {
//                "eg" -> "Egypt"
//                "us" -> "US"
//                "France" -> "fr"
//                "Germany" -> "de"  // Corrected to "de" for Germany
//                "England" -> "gb"
//                else -> "us"
//            }

            // Save to SharedPreferences as well
//            val sharedPreferences = getSharedPreferences("newsAppPref", Context.MODE_PRIVATE)

//
//            with(sharedPreferences.edit()) {
//                putString("selectedCountry", selectedCountry)
//                apply()
//            }
//            saveCountryPreference(selectedCountry)
//            Toast.makeText(
//                this,
//                "Country: $selectedCountry saved successfully!!",
//                Toast.LENGTH_SHORT
//            ).show()

//            val country=when(selectedCountry){
//                "eg" -> "Egypt"
//                "us" -> "US"
//                "fr" -> "France"
//                "de" -> "Germany"
//                "gb" -> "England"
//                else -> "us"
//            }
//            saveCountryPreference(selectedCountry)
//            Toast.makeText(
//                this,
//                "Country:$selectedCountry saved successfully!! ",
//                Toast.LENGTH_SHORT
//            ).show()
//
//
//
//            Toast.makeText(this, "Selected Country: $country", Toast.LENGTH_SHORT).show()
//        }*/