package com.example.poc

import com.example.poc.Base.Contextor

enum class test(val sit: String) {
    TEXT(Contextor.getInstance().context!!.getString(R.string.test_scanner))
}