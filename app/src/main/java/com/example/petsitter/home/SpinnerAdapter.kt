package com.example.petsitter.home

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Spinner
import android.widget.TextView
import com.example.petsitter.R

class SpinnerAdapter {
    fun View.dpToPx(dp: Float): Int = context.dpToPx(dp)
    fun Context.dpToPx(dp: Float): Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()
    fun View.setHeight(context : Context, value: Int) {
        val lp = layoutParams
        lp?.let {
            lp.height = dpToPx(value.toFloat())
            layoutParams = lp
        }
    }

    fun Spinner.setCustomAdapter(context : Context, lst : MutableList<String>, unselectedTitle : String = "" ){
        class CustomSpnAdapter : BaseAdapter {
            var lst : MutableList<String> = mutableListOf<String>()
            var context : Context
            var unselectedTitle : String

            constructor (context: Context, lst: MutableList<String>, unselectedTitle: String){
                this.context = context
                this.lst     = lst
                this.unselectedTitle = unselectedTitle
            }

            override fun getCount(): Int {
                return lst.size
            }

            override fun getItemId(p0: Int): Long {
                return 0
            }

            override fun getItem(n: Int): String {
                return lst[n]
            }

            override fun getView(n: Int, p1: View?, p2: ViewGroup?): View {
                val v = LayoutInflater.from(context).inflate(R.layout.custom_spinner, null)
                v.findViewById<TextView>(R.id.text_first)?.apply {
                    text = lst[n]


                    // ** 이 부분을 처리하지 않으면
                    // ** spinner background가 커스텀 item으로 치환된다.
                    // ** XML에서도 background를 같게 지정해주어야 한다.
                    if(p2 is Spinner){

                        background = context.getDrawable(R.drawable.bg_spinner)
                        if(p2.selectedItemPosition < 0 ){
                            p2.setHeight(context, 40)
                            setTextColor(Color.parseColor("#626466"))
                            text = unselectedTitle
                        }
                    }
                }
                return v
            }
        }

        adapter = CustomSpnAdapter(context, lst, unselectedTitle)
        this.setSelection(-1)
    }

}