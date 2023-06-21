package id.ac.umn.storyapplication.activity


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import id.ac.umn.storyapplication.api.RetrofitClient
import id.ac.umn.storyapplication.databinding.ActivityLoginBinding
import id.ac.umn.storyapplication.model.LoginResponse
import id.ac.umn.storyapplication.storage.SharedPrefManager
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()


        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.buttonLogin.setOnClickListener{
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if(email.isEmpty()){
                binding.etEmail.error = "Email Required"
                binding.etEmail.requestFocus()
                return@setOnClickListener
            } else
            {
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(this, "Enter a valid email", Toast.LENGTH_LONG).show()
                }
            }

            if(password.isEmpty() || password.length < 8){
                binding.etPassword.error = "Password Required"
                binding.etPassword.requestFocus()
                return@setOnClickListener
            }


            RetrofitClient.instance.userLogin(email, password)
                .enqueue(object: Callback<LoginResponse>{
                    override fun onResponse(
                        call: retrofit2.Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        if(response.body()?.error == false){

                            SharedPrefManager.getInstance(applicationContext).saveUser(response.body()?.loginResult!!)

                            val token = response.body()?.loginResult!!.token

                            val intent = Intent(applicationContext, StoryActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            intent.putExtra("loginToken", token)
                            startActivity(intent)
                        }else{
                            Toast.makeText(
                                applicationContext,
                                "Email or Password is wrong. Please try again.",
                                Toast.LENGTH_LONG
                            ).show()
                            val intent = Intent(applicationContext, LoginActivity::class.java)
                            startActivity(intent)
                        }
                    }

                    override fun onFailure(call: retrofit2.Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                    }
                })
        }
    }



    override fun onStart() {
        super.onStart()

        if(SharedPrefManager.getInstance(this).isLoggedIn){
            val intent = Intent(applicationContext, StoryActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            val token = SharedPrefManager.getInstance(this).loginResult.token
            intent.putExtra("loginToken", token)

            startActivity(intent)
        }
    }
}