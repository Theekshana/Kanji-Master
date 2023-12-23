package com.akiyama.kanjimaster.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.akiyama.kanjimaster.databinding.FragmentKanjiQuizBinding
import com.akiyama.kanjimaster.ui.viewmodels.StaticViewModel
import timber.log.Timber

class KanjiQuizFragment : Fragment() {

    init {
        Timber.tag("KanjiQuiz")
    }

    private var binding: FragmentKanjiQuizBinding? = null

    //private lateinit var viewModel: QuestionViewModel
    private lateinit var viewModel: StaticViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentKanjiQuizBinding.inflate(inflater, container, false)
        //viewModel = ViewModelProvider(requireActivity())[QuestionViewModel::class.java]
        viewModel = ViewModelProvider(requireActivity())[StaticViewModel::class.java]

        binding?.kanjiMaster = viewModel
        binding?.lifecycleOwner = this

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.shuffleQuiz()

        viewModel.currentQuiz.observe(viewLifecycleOwner) { quiz ->
            if (quiz.size >= 5) {
                binding?.txtQuestion?.text = quiz[0]
                Timber.d("Data $quiz")

                binding?.btnCorrectAnswer?.text = quiz[1] ?: "" // Right answer
                binding?.btnAnswerTwo?.text = quiz[2] ?: "" // Choice 1
                binding?.btnAnswerThree?.text = quiz[3] ?: "" // Choice 2
                binding?.btnAnswerFour?.text = quiz[4] ?: "" // Choice 3
            } else {
                // Handle the case when the quiz list doesn't have the expected size
                // You might want to log an error or display a message to the user.
            }
        }

        binding?.btnNext?.setOnClickListener {
            viewModel.shuffleQuiz()
        }

        binding?.btnCorrectAnswer?.setOnClickListener { handleAnswerClick(binding?.btnCorrectAnswer?.text.toString()) }
        binding?.btnAnswerTwo?.setOnClickListener { handleAnswerClick(binding?.btnAnswerTwo?.text.toString()) }
        binding?.btnAnswerThree?.setOnClickListener { handleAnswerClick(binding?.btnAnswerThree?.text.toString()) }
        binding?.btnAnswerFour?.setOnClickListener { handleAnswerClick(binding?.btnAnswerFour?.text.toString()) }




        /*viewModel.dataText.observe(viewLifecycleOwner) { data ->
            // Update your TextView with the data
            //binding?.txtQuestion?.text = data
            Timber.d("Data: $data")
        }

        viewModel.question.observe(viewLifecycleOwner){ question ->



            if (question.isNotEmpty()) {

                val currentQuestion = question[0]
                binding?.txtQuestion?.text = currentQuestion.kanji

                binding?.btnCorrectAnswer?.text = currentQuestion.rightAnswer
                binding?.btnAnswerTwo?.text = currentQuestion.choices[0]
                binding?.btnAnswerThree?.text = currentQuestion.choices[1]
                binding?.btnAnswerFour?.text = currentQuestion.choices[2]
            }

        }*/


        /*  binding?.btnCorrectAnswer?.setOnClickListener { onAnswerButtonClick(0) }
          binding?.btnAnswerTwo?.setOnClickListener { onAnswerButtonClick(1) }
          binding?.btnAnswerThree?.setOnClickListener { onAnswerButtonClick(2) }
          binding?.btnAnswerFour?.setOnClickListener { onAnswerButtonClick(3) }*/

        // Observe the toast message and display a toast when it changes
        /* viewModel.toastMessage.observe(viewLifecycleOwner) { message ->
             Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
         }*/


    }

    // Function to handle answer clicks
    private fun handleAnswerClick(selectedAnswer: String) {
        viewModel.currentQuiz.observe(viewLifecycleOwner) { quiz ->
            if (quiz.size >= 6) {
                // Extract the correct index from the last item of the quiz
                val correctIndex = quiz[5]?.toInt()

                // Check if the selected answer is correct
                if (correctIndex != null) {
                    if (selectedAnswer == quiz[correctIndex + 1]) {
                        Toast.makeText(requireContext(), "Correct", Toast.LENGTH_LONG).show()
                        // Handle correct answer logic here
                    } else {
                        Toast.makeText(requireContext(), "InCorrect", Toast.LENGTH_LONG).show()
                        // Handle incorrect answer logic here
                    }
                }

                // Optionally, you can update the UI or perform any other actions here
            } else {
                // Handle the case when the quiz list doesn't have the expected size
                // You might want to log an error or display a message to the user.
            }
        }
    }




    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}