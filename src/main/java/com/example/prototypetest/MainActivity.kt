package com.example.prototypetest

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.Serializable

// Data class representing a question
data class Question(
    val text: String,
    val correctAnswer: Boolean,
    var userAnswer: Boolean? = null
) : Serializable

class MainActivity : AppCompatActivity() {

    private var score = 0
    private var currentQuestionIndex = 0
    private var answered = false

    private lateinit var tvQuestion: TextView
    private lateinit var tvResult: TextView
    private lateinit var btnYes: Button
    private lateinit var btnNo: Button
    private lateinit var btnNext: Button
    private lateinit var btnShowScore: Button

    // Current New York Fashion themed questions
    private val questions: MutableList<Question> = mutableListOf(
        Question("Lil Baby is a major hip hop artist from Atlanta.", true),
        Question("21 Savage is originally from California.", false),
        Question("The group Migos originated in Atlanta.", true),
        Question("Atlanta has no influence on the trap music genre.", false),
        Question("Future released new music in 2024.", true),
        Question("Latto is an R&B artist from New York.", false),
        Question("Young Thug is known for his unique vocal style and is from Atlanta.", true)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        tvQuestion = findViewById(R.id.tvQuestion)
        tvResult = findViewById(R.id.tvResult)
        btnYes = findViewById(R.id.btnYes)
        btnNo = findViewById(R.id.btnNo)
        btnNext = findViewById(R.id.btnNext)
        btnShowScore = findViewById(R.id.btnShowScore)

        btnShowScore.isEnabled = false
        btnNext.isEnabled = false

        showNextQuestion()

        btnYes.setOnClickListener {
            if (!answered) handleAnswer(true)
        }

        btnNo.setOnClickListener {
            if (!answered) handleAnswer(false)
        }

        btnNext.setOnClickListener {
            if (answered) {
                currentQuestionIndex++
                showNextQuestion()
                answered = false
                btnNext.isEnabled = false
            }
        }

        btnShowScore.setOnClickListener {
            val intent = Intent(this, ScoreActivity::class.java)
            intent.putExtra("SCORE", score)
            intent.putExtra("QUESTIONS", ArrayList(questions))
            startActivity(intent)
        }
    }

    private fun showNextQuestion() {
        if (currentQuestionIndex < questions.size) {
            val question = questions[currentQuestionIndex]
            tvQuestion.text = question.text
            tvResult.text = ""
            btnYes.isEnabled = true
            btnNo.isEnabled = true
        } else {
            tvQuestion.text = "Quiz Over!"
            tvResult.text = "Your Score: $score"
            btnYes.isEnabled = false
            btnNo.isEnabled = false
            btnNext.isEnabled = false
            btnShowScore.isEnabled = true
        }
    }

    private fun handleAnswer(answer: Boolean) {
        val currentQuestion = questions[currentQuestionIndex]
        currentQuestion.userAnswer = answer

        answered = true
        btnYes.isEnabled = false
        btnNo.isEnabled = false
        btnNext.isEnabled = true

        if (currentQuestion.correctAnswer == answer) {
            score++
            tvResult.text = "Correct! (+1)"
            tvResult.setTextColor(resources.getColor(R.color.correctAnswer, theme))
        } else {
            tvResult.text = "Incorrect!"
            tvResult.setTextColor(resources.getColor(R.color.wrongAnswer, theme))
        }
    }
}
