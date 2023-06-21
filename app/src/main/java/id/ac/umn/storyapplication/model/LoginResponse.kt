package id.ac.umn.storyapplication.model

data class LoginResponse(val error: Boolean, val message: String, val loginResult: User)
