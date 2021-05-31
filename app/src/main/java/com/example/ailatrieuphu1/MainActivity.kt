package com.example.ailatrieuphu1

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.TextView
import com.example.ailatrieuphu1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() ,View.OnClickListener {


    private  lateinit var binding: ActivityMainBinding
    private lateinit var mListQuestion : MutableList<QUestion>
    private var currentQuestion  = 0
    private lateinit var mQuestion: QUestion
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mListQuestion = getListQuestion()
        Log.d("AAA",mListQuestion[2].content)
        if (mListQuestion.isEmpty()) return
        setDataQuestion(mListQuestion[currentQuestion])
    }

    private fun setDataQuestion(get: QUestion) {
        mQuestion = get

        binding.tvQuestion.text = "Question ${get.number}"
        binding.tvContentQuestion.text = get.content

        binding.tvAnswer1.text = get.listAnswer[0].content
        binding.tvAnswer2.text = get.listAnswer[1].content
        binding.tvAnswer3.text = get.listAnswer[2].content
        binding.tvAnswer4.text = get.listAnswer[3].content

        binding.tvAnswer1.setBackgroundResource(R.drawable.bg_blue_conner_30)
        binding.tvAnswer2.setBackgroundResource(R.drawable.bg_blue_conner_30)
        binding.tvAnswer3.setBackgroundResource(R.drawable.bg_blue_conner_30)
        binding.tvAnswer4.setBackgroundResource(R.drawable.bg_blue_conner_30)


        binding.tvAnswer1.setOnClickListener(this)
        binding.tvAnswer2.setOnClickListener(this)
        binding.tvAnswer3.setOnClickListener(this)
        binding.tvAnswer4.setOnClickListener(this)

    }

    private fun getListQuestion() : MutableList<QUestion>{
        val list : MutableList<QUestion> = mutableListOf()

        val answerList1 : MutableList<Answer> = mutableListOf()
        answerList1.add(Answer("Gà",true))
        answerList1.add(Answer("Chó",false))
        answerList1.add(Answer("Vịt",false))
        answerList1.add(Answer("Mèo",false))
        list.add(QUestion(1,"Meò mả ... đồng?",answerList1))

        val answerList2 : MutableList<Answer> = mutableListOf()
        answerList2.add(Answer("Đa",true))
        answerList2.add(Answer("Đệm",false))
        answerList2.add(Answer("Tay",false))
        answerList2.add(Answer("Nước",false))
        list.add(QUestion(2,"Chân cứng ... Mền?",answerList2))

        val answerList3 : MutableList<Answer> = mutableListOf()
        answerList3.add(Answer("Nghệ An",false))
        answerList3.add(Answer("Hồ Chí Minh",false))
        answerList3.add(Answer("Hà Nội",true))
        answerList3.add(Answer("ĐÀ Nẵng",false))
        list.add(QUestion(3,"Thủ Đô của Việt Nam?",answerList3))

        val answerList4 : MutableList<Answer> = mutableListOf()
        answerList4.add(Answer("Gà",true))
        answerList4.add(Answer("Chó",false))
        answerList4.add(Answer("Vịt",false))
        answerList4.add(Answer("Mèo",false))
        list.add(QUestion(4,"Meò mả ... đồng?",answerList4))
        return list
    }

    override fun onClick(v: View?) {
        if (v != null) when(v.id){
            R.id.tvAnswer1 -> {
                binding.tvAnswer1.setBackgroundResource(R.drawable.bg_orange_conner_30)
                checkAnswer(binding.tvAnswer1,mQuestion,mQuestion.listAnswer[0])
            }
            R.id.tvAnswer2 ->{
                binding.tvAnswer2.setBackgroundResource(R.drawable.bg_orange_conner_30)
                checkAnswer(binding.tvAnswer2,mQuestion,mQuestion.listAnswer[1])
            }
            R.id.tvAnswer3 ->{
                binding.tvAnswer3.setBackgroundResource(R.drawable.bg_orange_conner_30)
                checkAnswer(binding.tvAnswer3,mQuestion,mQuestion.listAnswer[2])
            }
            else  ->{
                binding.tvAnswer4.setBackgroundResource(R.drawable.bg_orange_conner_30)
                checkAnswer(binding.tvAnswer4,mQuestion,mQuestion.listAnswer[3])
            }

        }
    }
    private fun checkAnswer(textView: TextView, question : QUestion, answer: Answer ){
        Handler().postDelayed({
            if (answer.isCorrect){
                textView.setBackgroundResource(R.drawable.bg_green_conner_30)
                nextQuestion()
            }
            else {
                textView.setBackgroundResource(R.drawable.bg_red_conner_30)
                showAnswerCorrect(question)
                gameOver()
            }
        },1000)
    }

    private fun gameOver() {
        Handler().postDelayed({
            showDialog("GameOver")
        },1000)
    }

    private fun showDialog(mess : String) {
        val builder : AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage(mess)
        builder.setCancelable(false)
        builder.apply {
            setPositiveButton(R.string.button_yes) { dialog, _ ->
                run {
                    currentQuestion = 0
                    setDataQuestion(mListQuestion[currentQuestion])
                    dialog.dismiss()
                }
            }
        }
        val alertDialog = builder.create()
         alertDialog.show()


    }


    private fun showAnswerCorrect(question: QUestion) {
        if(question.listAnswer.isEmpty()) return
        when {
            question.listAnswer[0].isCorrect -> {
                binding.tvAnswer1.setBackgroundResource(R.drawable.bg_green_conner_30)
            }
            question.listAnswer[1].isCorrect -> {
                binding.tvAnswer2.setBackgroundResource(R.drawable.bg_green_conner_30)
            }
            question.listAnswer[2].isCorrect -> {
                binding.tvAnswer3.setBackgroundResource(R.drawable.bg_green_conner_30)
            }
            else -> {
                binding.tvAnswer4.setBackgroundResource(R.drawable.bg_green_conner_30)
            }
        }
    }

    private fun nextQuestion() {
        if(currentQuestion == mListQuestion.size - 1)
            showDialog("you win !!")
        else{
            currentQuestion++
            Handler().postDelayed({
                Log.d("AAA","${mListQuestion[currentQuestion].listAnswer}")
                setDataQuestion(mListQuestion[currentQuestion])
            },1000)

        }

    }
}
