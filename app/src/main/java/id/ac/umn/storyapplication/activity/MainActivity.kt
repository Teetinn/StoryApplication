package id.ac.umn.storyapplication.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import id.ac.umn.storyapplication.api.RetrofitClient
import id.ac.umn.storyapplication.databinding.ActivityMainBinding
import id.ac.umn.storyapplication.model.DefaultResponse
import id.ac.umn.storyapplication.storage.SharedPrefManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        playAnimation()

        binding.tvLogin.setOnClickListener{
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        }

        binding.buttonSignup.setOnClickListener{
            val name = binding.etName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if(name.isEmpty()){
                binding.etName.error = "Name Required"
                binding.etName.requestFocus()
                return@setOnClickListener
            }

            if(email.isEmpty()){
                binding.etEmail.error = "Email Required"
                binding.etEmail.requestFocus()
                return@setOnClickListener
            }

            if(password.isEmpty() || password.length < 8){
                binding.etPassword.error = "Password Required"
                binding.etPassword.requestFocus()
                return@setOnClickListener
            }

            RetrofitClient.instance.register(
                name,
                email,
                password
            ).enqueue(object: Callback<DefaultResponse>{
                override fun onResponse(
                    call: Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ){
                    if(response.body()?.error == false){
                        Toast.makeText(applicationContext, response.body()?.message, Toast.LENGTH_LONG).show()
                        val intent = Intent(applicationContext, LoginActivity::class.java)
                        startActivity(intent)
                    }else {
                        Toast.makeText(
                            applicationContext,
                            "Account Creation Error",
                            Toast.LENGTH_LONG
                        ).show()
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                    }
                }

                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }

            })

        }
    }

    private fun playAnimation(){
        val namecard = ObjectAnimator.ofFloat(binding.nameCard, View.ALPHA, 1f).setDuration(500)
        val emailcard = ObjectAnimator.ofFloat(binding.emailCard, View.ALPHA, 1f).setDuration(500)
        val passcard = ObjectAnimator.ofFloat(binding.passwordCard, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(namecard, emailcard, passcard)
            start()
        }
    }

    override fun onResume(){
        super.onResume()
        playAnimation()
    }

    override fun onStart() {
        super.onStart()

        if(SharedPrefManager.getInstance(this).isLoggedIn){
            val intent = Intent(applicationContext, StoryActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
        }
    }
}