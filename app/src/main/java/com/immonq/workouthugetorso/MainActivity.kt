package com.immonq.workouthugetorso

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.CheckedTextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val checkList = arrayOf(
            firstStep,
            secondStep,
            thirdStep,
            fourthStep
        )

        var isRunning: Boolean = false

        val timer = object: CountDownTimer(15100, 1000) {

            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                viewTimer.text =
                    "${(millisUntilFinished / 1000 / 60).toString().padStart(2, '0')}:" +
                            "${(millisUntilFinished / 1000 % 60).toString().padStart(2, '0')} "
                isRunning = true
                Log.wtf("devx", millisUntilFinished.toString())
            }
            override fun onFinish() {
                viewTimer.text = ("Вы справились!")
                isRunning = false
            }

        }

        startButton.setOnClickListener {

            timer.start()

            startButton.isEnabled = false
            startButton.text = "Тренировка в процессе..."

            Handler().postDelayed(object : Runnable {
                override fun run() {
                    checkChecked(checkList, isRunning, timer)
                    Handler().postDelayed(this, 16500)
                }
            }, 0)

        }

    }


    private fun checkChecked(checkList: Array<CheckedTextView>, isRunning: Boolean, timer: CountDownTimer) {

        for (i in checkList.indices) {
            if (!checkList[i].isChecked) {
                if (!isRunning)
                    checkList[i].isChecked = true
                if (!fourthStep.isChecked) {
                    timer.cancel()
                    timer.start()
                    progressBar.visibility = View.VISIBLE
                } else {
                    progressBar.visibility = View.GONE
                    viewTimer.text = "Тренировка завершена!"
                    startButton.text = "Возвращайтесь завтра!"
                }
                break
            }
        }

    }


}