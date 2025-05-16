package com.example.prototypetest

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ScoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        val tvFinalScore: TextView = findViewById(R.id.tvFinalScore)
        val tvCorrections: TextView = findViewById(R.id.tvCorrections)
        val btnRestartQuiz: Button = findViewById(R.id.btnRestartQuiz)

        // Get score and question list from intent
        val score = intent.getIntExtra("SCORE", 0)
        val questions = intent.getSerializableExtra("QUESTIONS") as? ArrayList<Question>

        tvFinalScore.text = "Final Score: $score"

        val btnExitApp: Button = findViewById(R.id.btnExitApp)
        btnExitApp.setOnClickListener {
            finishAffinity() // Closes all activities
        }


        val correctionsText = StringBuilder()
        questions?.forEachIndexed { index, question ->
            if (question.userAnswer != question.correctAnswer) {
                val result = if (question.userAnswer == question.correctAnswer) "Correct" else "Incorrect"
                correctionsText.append("${index + 1}. ${question.text}\n")
                correctionsText.append("Your answer: ${if (question.userAnswer == true) "Yes" else "No"}\n")
                correctionsText.append("Correct answer: ${if (question.correctAnswer) "Yes" else "No"}\n")
                correctionsText.append("Result: $result\n\n")
            }
        }

        tvCorrections.text = if (correctionsText.isNotEmpty()) correctionsText.toString()
        else "All answers were correct. Well done!"

        btnRestartQuiz.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }
}
