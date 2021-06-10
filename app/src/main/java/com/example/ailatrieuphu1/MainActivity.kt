package com.example.ailatrieuphu1

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.ailatrieuphu1.data.DBManager
import com.example.ailatrieuphu1.databinding.ActivityMainBinding
import com.example.ailatrieuphu1.model.Question
import org.eazegraph.lib.charts.BarChart
import org.eazegraph.lib.models.BarModel
import java.util.*

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        const val TIME = 30000
    }

    private var mTime = TIME
    private lateinit var mCountdownTime: CountDownTimer
    private lateinit var binding: ActivityMainBinding
    private lateinit var mListQuestion: MutableList<Question>
    private var currentQuestion = 0
    private lateinit var mQuestion: Question
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val dbManager = DBManager(this)
        mListQuestion = dbManager.getData()
        if (mListQuestion.isEmpty()) return
        setDataQuestion(mListQuestion[currentQuestion])
        startTimer()

    }

    @SuppressLint("SetTextI18n")
    private fun setDataQuestion(get: Question) {
        mQuestion = get
        binding.tvQuestion.text = getString(R.string.Question).plus(" ${get.levelQuestion}")
        binding.textViewDiem.text = (get.levelQuestion.toInt() - 1).toString()
        binding.tvContentQuestion.text = get.content
        binding.tvAnswer1.text = get.caseA
        binding.tvAnswer2.text = get.caseB
        binding.tvAnswer3.text = get.caseC
        binding.tvAnswer4.text = get.caseD

        binding.tvAnswer1.setBackgroundResource(R.drawable.bg_blue_conner_30)
        binding.tvAnswer2.setBackgroundResource(R.drawable.bg_blue_conner_30)
        binding.tvAnswer3.setBackgroundResource(R.drawable.bg_blue_conner_30)
        binding.tvAnswer4.setBackgroundResource(R.drawable.bg_blue_conner_30)


        binding.tvAnswer1.setOnClickListener(this)
        binding.tvAnswer2.setOnClickListener(this)
        binding.tvAnswer3.setOnClickListener(this)
        binding.tvAnswer4.setOnClickListener(this)

    }


    override fun onClick(v: View?) {
        if (v != null) when (v.id) {
            R.id.tvAnswer1 -> {
                binding.tvAnswer1.setBackgroundResource(R.drawable.bg_orange_conner_30)
                checkAnswer(binding.tvAnswer1, mQuestion, mQuestion.caseA)
            }
            R.id.tvAnswer2 -> {
                binding.tvAnswer2.setBackgroundResource(R.drawable.bg_orange_conner_30)
                checkAnswer(binding.tvAnswer2, mQuestion, mQuestion.caseB)
            }
            R.id.tvAnswer3 -> {
                binding.tvAnswer3.setBackgroundResource(R.drawable.bg_orange_conner_30)
                checkAnswer(binding.tvAnswer3, mQuestion, mQuestion.caseC)
            }
            R.id.tvAnswer4 -> {
                binding.tvAnswer4.setBackgroundResource(R.drawable.bg_orange_conner_30)
                checkAnswer(binding.tvAnswer4, mQuestion, mQuestion.caseD)
            }
            R.id.ivHelp1 -> {

                help5050()


            }
            R.id.ivHelp2 -> {
                binding.ivHelp2.setBackgroundResource(R.drawable.atp__activity_player_button_image_help_audience_x)
                helpAudience()
            }
            R.id.ivHelp3 -> {
                binding.ivHelp3.setBackgroundResource(R.drawable.atp__activity_player_button_image_help_call_x)
                helpCall()
            }
            R.id.ivHelp4 -> {
                helpStop()
            }


        }
    }

    private fun helpStop() {
        binding.ivHelp4.setBackgroundResource(R.drawable.atp__activity_player_button_image_help_stop)
        showDialog("${getString(R.string.congratulations)} $currentQuestion ${getString(R.string.Question)}")

    }

    private fun helpCall() {
        binding.layoutHelp.visibility = View.VISIBLE
        binding.ivHelp3.setBackgroundResource(R.drawable.atp__activity_player_button_image_help_call_x)
        binding.ivHelp3.isEnabled = false
        val rd = (1..4).random()

        val answer: String = mQuestion.answer()


        when (rd) {
            1 -> {
                binding.peopleHelp.setBackgroundResource(R.drawable.ic_call_relav_billgate)
                binding.tvCallHelp.text = getString(R.string.answerBillgate).plus(" $answer")
            }
            2 -> {
                binding.peopleHelp.setBackgroundResource(R.drawable.ic_call_relav_obama)
                binding.tvCallHelp.text = getString(R.string.answerObama).plus(" $answer")
            }
            3 -> {
                binding.peopleHelp.setBackgroundResource(R.drawable.ic_call_relav_stever)
                binding.tvCallHelp.text = getString(R.string.answerStever).plus(" $answer")
            }
            4 -> {
                when ((1..4).random()) {

                    1 -> {
                        binding.peopleHelp.setBackgroundResource(R.drawable.ic_call_relav_troll1)
                        binding.tvCallHelp.text = getString(R.string.answerTroll1)
                    }
                    2 -> {
                        binding.peopleHelp.setBackgroundResource(R.drawable.ic_call_relav_troll2)
                        binding.tvCallHelp.text = getString(R.string.answerTroll2)
                    }
                    3 -> {
                        binding.peopleHelp.setBackgroundResource(R.drawable.ic_call_relav_troll3)
                        binding.tvCallHelp.text = getString(R.string.answerTroll3)

                    }
                    4 -> {
                        binding.peopleHelp.setBackgroundResource(R.drawable.ic_call_relav_troll4)
                        binding.tvCallHelp.text = getString(R.string.answerTroll4)

                    }
                }
            }
        }
        Handler().postDelayed({
            binding.peopleHelp.setBackgroundResource(0)
            binding.tvCallHelp.text = ""

        }, 6000)
    }

    private fun helpAudience() {
        initDialogAudience()
    }
    private fun help5050() {
        binding.ivHelp1.setBackgroundResource(R.drawable.atp__activity_player_button_image_help_5050_x)
        binding.ivHelp1.isEnabled = false
        if (mQuestion.caseA == mQuestion.answer()) {
            binding.tvAnswer2.text = ""
            binding.tvAnswer3.text = ""
            binding.tvAnswer2.isEnabled = false
            binding.tvAnswer3.isEnabled = false
        } else {
            if (mQuestion.caseB == mQuestion.answer()) {
                binding.tvAnswer3.text = ""
                binding.tvAnswer1.text = ""
                binding.tvAnswer1.isEnabled = false
                binding.tvAnswer3.isEnabled = false
            } else {
                if (mQuestion.caseC == mQuestion.answer()) {
                    binding.tvAnswer1.text = ""
                    binding.tvAnswer2.text = ""
                    binding.tvAnswer1.isEnabled = false
                    binding.tvAnswer2.isEnabled = false
                } else {
                    if (mQuestion.caseD == mQuestion.answer()) {
                        binding.tvAnswer2.text = ""
                        binding.tvAnswer1.text = ""
                        binding.tvAnswer1.isEnabled = false
                        binding.tvAnswer2.isEnabled = false
                    }
                }
            }
        }
    }

    private fun checkAnswer(textView: TextView, question: Question, trueCase: String) {
        Handler().postDelayed({
            if (question.answer() == trueCase) {
                textView.setBackgroundResource(R.drawable.bg_green_conner_30)
                nextQuestion()
            } else {
                binding.avi.hide()
                mCountdownTime.cancel()
                textView.setBackgroundResource(R.drawable.bg_red_conner_30)
                showAnswerCorrect()
                gameOver()
            }
        }, 1000)
    }

    private fun gameOver() {

        mCountdownTime.cancel()
        Handler().postDelayed({
            showDialog("Game Over")
        }, 1000)
    }

    private fun showDialog(mess: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage(mess)
        builder.setCancelable(false)
        builder.apply {
            setPositiveButton(R.string.button_yes) { dialog, _ ->
                run {
                    currentQuestion = 0
                    setDataQuestion(mListQuestion[currentQuestion])
                    newGame()
                    val intent = Intent(this.context, LoginActivity::class.java)
                    startActivity(intent)
                    dialog.dismiss()
                }
            }
        }

        val alertDialog = builder.create()
        alertDialog.show()


    }

    private fun showAnswerCorrect() {
        when (mQuestion.answer()) {
            mQuestion.caseA -> {
                binding.tvAnswer1.setBackgroundResource(R.drawable.bg_green_conner_30)
            }
            mQuestion.caseB -> {
                binding.tvAnswer2.setBackgroundResource(R.drawable.bg_green_conner_30)
            }
            mQuestion.caseC -> {
                binding.tvAnswer3.setBackgroundResource(R.drawable.bg_green_conner_30)
            }
            else -> {
                binding.tvAnswer4.setBackgroundResource(R.drawable.bg_green_conner_30)
            }
        }
    }

    private fun nextQuestion() {
        if (currentQuestion == mListQuestion.size - 1)
            showDialog("you win !!")
        else {
            binding.tvAnswer1.isEnabled = true
            binding.tvAnswer2.isEnabled = true
            binding.tvAnswer3.isEnabled = true
            binding.tvAnswer4.isEnabled = true
            currentQuestion++
            Handler().postDelayed({
                reset()
                setDataQuestion(mListQuestion[currentQuestion])
            }, 1000)

        }

    }

    private fun newGame() {
        binding.tvAnswer1.isEnabled = true
        binding.tvAnswer2.isEnabled = true
        binding.tvAnswer3.isEnabled = true
        binding.tvAnswer4.isEnabled = true
        binding.ivHelp4.isEnabled = true
        binding.ivHelp3.isEnabled = true
        binding.ivHelp2.isEnabled = true
        binding.ivHelp1.isEnabled = true
        binding.layoutHelp.visibility = View.GONE
        binding.ivHelp1.setBackgroundResource(R.drawable.atp__activity_player_button_image_help_5050)
        binding.ivHelp3.setBackgroundResource(R.drawable.atp__activity_player_button_image_help_call)
        binding.ivHelp2.setBackgroundResource(R.drawable.atp__activity_player_button_image_help_audience)
    }

    private fun startTimer() {
        var text = mTime / 1000
        mCountdownTime = object : CountDownTimer(mTime.toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                text--
                binding.textViewTg.text = text.toString()
            }

            override fun onFinish() {
                showDialog("Time out")
                cancel()
            }
        }.start()

    }

    private fun reset() {
        mTime = TIME
        mCountdownTime.cancel()
        startTimer()
    }

    // hiển thị dialog khi chức năng hỏi ý kiến
    private fun initDialogAudience() {
        mCountdownTime.cancel()
        val dialog = Dialog(this)
        dialog.setTitle(getString(R.string.AudienceHelp))
        dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_box)
        dialog.setContentView(R.layout.ykien_dialog)
        //LayoutInflater layoutInflater = LayoutInflater.from(this);
        // View view = layoutInflater.inflate(R.layout.ykien_dialog, null);
        val mBarChart: BarChart
        val btnOk: Button = dialog.findViewById(R.id.cancelButton) as Button
        val random = Random()
        var rd1True: Int = random.nextInt(10) + 40
        var rdFalse1: Int = random.nextInt(10) + 5
        var rdFalse2: Int = random.nextInt(10) + 5
        var rdFalse3 = 100 - rd1True - rdFalse1 - rdFalse2


        // nếu chọn 50-50 trước và sau đó chọn hỏi ý kiến khán giả
        if (!binding.ivHelp1.isEnabled) {
            when (mQuestion.answer()) {
                mQuestion.caseA -> {
                    mBarChart = dialog.findViewById(R.id.barchart) as BarChart
                    rd1True = random.nextInt(10) + 50
                    rdFalse3 = 100 - rd1True
                    mBarChart.addBar(BarModel("A", rd1True.toFloat(), -0xa9cbaa))
                    mBarChart.addBar(BarModel("B", 0F, -0xa9cbaa))
                    mBarChart.addBar(BarModel("C", 0F, -0xa9cbaa))
                    mBarChart.addBar(BarModel("D", rdFalse3.toFloat(), -0xa9cbaa))
                    mBarChart.startAnimation()
                }
                mQuestion.caseB -> {
                    mBarChart = dialog.findViewById(R.id.barchart) as BarChart
                    rd1True = random.nextInt(10) + 50
                    rdFalse2 = 100 - rd1True
                    mBarChart.addBar(BarModel("A", 0F, -0xa9cbaa))
                    mBarChart.addBar(BarModel("B", rd1True.toFloat(), -0xa9cbaa))
                    mBarChart.addBar(BarModel("C", 0F, -0xa9cbaa))
                    mBarChart.addBar(BarModel("D", rdFalse2.toFloat(), -0xa9cbaa))
                    mBarChart.startAnimation()
                }
                mQuestion.caseC -> {
                    mBarChart = dialog.findViewById(R.id.barchart) as BarChart
                    rd1True = random.nextInt(10) + 50
                    rdFalse1 = 100 - rd1True
                    mBarChart.addBar(BarModel("A", 0F, -0xa9cbaa))
                    mBarChart.addBar(BarModel("B", 0F, -0xa9cbaa))
                    mBarChart.addBar(BarModel("C", rd1True.toFloat(), -0xa9cbaa))
                    mBarChart.addBar(BarModel("D", rdFalse1.toFloat(), -0xa9cbaa))
                    mBarChart.startAnimation()
                }
                mQuestion.caseD -> {
                    mBarChart = dialog.findViewById(R.id.barchart) as BarChart
                    rd1True = random.nextInt(10) + 50
                    rdFalse2 = 100 - rd1True
                    mBarChart.addBar(BarModel("A", 0F, -0xa9cbaa))
                    mBarChart.addBar(BarModel("B", 0F, -0xa9cbaa))
                    mBarChart.addBar(BarModel("C", rdFalse2.toFloat(), -0xa9cbaa))
                    mBarChart.addBar(BarModel("D", rd1True.toFloat(), -0xa9cbaa))
                    mBarChart.startAnimation()

                }


            }
        } else {
            when (mQuestion.answer()) {
                mQuestion.caseA -> {
                    mBarChart = dialog.findViewById(R.id.barchart) as BarChart
                    mBarChart.addBar(BarModel("A", rd1True.toFloat(), -0xa9cbaa))
                    mBarChart.addBar(BarModel("B", rdFalse1.toFloat(), -0xa9cbaa))
                    mBarChart.addBar(BarModel("C", rdFalse2.toFloat(), -0xa9cbaa))
                    mBarChart.addBar(BarModel("D", rdFalse3.toFloat(), -0xa9cbaa))
                    mBarChart.startAnimation()
                }
                mQuestion.caseB -> {
                    mBarChart = dialog.findViewById(R.id.barchart) as BarChart
                    mBarChart.addBar(BarModel("A", rdFalse1.toFloat(), -0xa9cbaa))
                    mBarChart.addBar(BarModel("B", rd1True.toFloat(), -0xa9cbaa))
                    mBarChart.addBar(BarModel("C", rdFalse2.toFloat(), -0xa9cbaa))
                    mBarChart.addBar(BarModel("D", rdFalse3.toFloat(), -0xa9cbaa))
                    mBarChart.startAnimation()
                }
                mQuestion.caseC -> {
                    mBarChart = dialog.findViewById(R.id.barchart) as BarChart
                    mBarChart.addBar(BarModel("A", rdFalse2.toFloat(), -0xa9cbaa))
                    mBarChart.addBar(BarModel("B", rdFalse1.toFloat(), -0xa9cbaa))
                    mBarChart.addBar(BarModel("C", rd1True.toFloat(), -0xa9cbaa))
                    mBarChart.addBar(BarModel("D", rdFalse3.toFloat(), -0xa9cbaa))
                    mBarChart.startAnimation()
                }
                mQuestion.caseD -> {
                    mBarChart = dialog.findViewById(R.id.barchart) as BarChart
                    mBarChart.addBar(BarModel("A", rdFalse3.toFloat(), -0xa9cbaa))
                    mBarChart.addBar(BarModel("B", rdFalse1.toFloat(), -0xa9cbaa))
                    mBarChart.addBar(BarModel("C", rdFalse2.toFloat(), -0xa9cbaa))
                    mBarChart.addBar(BarModel("D", rd1True.toFloat(), -0xa9cbaa))
                    mBarChart.startAnimation()

                }

            }
        }
        btnOk.setOnClickListener {
            dialog.dismiss()
            mCountdownTime.start()
        }
        dialog.setCancelable(false)
        dialog.show()
    }
}
