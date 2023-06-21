package id.ac.umn.storyapplication.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import androidx.appcompat.widget.AppCompatEditText

class EmailView : AppCompatEditText {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }


    private fun init(){
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing.
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
              error = if(s.isNotEmpty()){
                   if(Patterns.EMAIL_ADDRESS.matcher(s).matches()){
                       null
                   }else{
                       "Email is invalid"
                   }
              }else{
                  "Please input email"
              }
            }
            override fun afterTextChanged(s: Editable) {
                // Do nothing.
            }
        })
    }

}