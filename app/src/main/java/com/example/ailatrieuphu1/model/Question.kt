package com.example.ailatrieuphu1.model

import com.example.ailatrieuphu1.model.Answer

class Question(
    val content: String,
    val idQuestion: Int,
    val levelQuestion: String,
    private val trueCase: Int,
    val caseA : String,
    val caseB : String,
    val caseC : String,
    val caseD : String

) {
    fun answer() : String{
        when(trueCase){
            1 -> return caseA
            2-> return caseB
            3-> return  caseC
            4 -> return caseD
           else->""
        }
        return ""
    }

}