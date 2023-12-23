package com.akiyama.kanjimaster.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.akiyama.kanjimaster.R
import com.akiyama.kanjimaster.databinding.FragmentHomeBinding
import com.akiyama.kanjimaster.db.QuestionDao
import com.akiyama.kanjimaster.db.QuestionDatabase
import com.akiyama.kanjimaster.model.QuestionEntity
import com.akiyama.kanjimaster.ui.viewmodels.QuestionViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeFragment : Fragment() {


    init {
        Timber.tag("Home")
    }

    private var binding: FragmentHomeBinding? = null
    private lateinit var navController: NavController
    private lateinit var viewModel: QuestionViewModel

    //Insert
    private lateinit var questionDao: QuestionDao


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        //viewModel = ViewModelProvider(this)[QuestionViewModel::class.java]

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding?.btnKanjiQuiz?.setOnClickListener {
            navigateToKanjiQuizFragment()
            Timber.d("Success")
        }

        //Insert
        binding?.btnInsert?.setOnClickListener {

            lifecycleScope.launch(Dispatchers.IO) {
                insertData()
                Timber.d("Data Inserted")
            }

        }

        binding?.btnDelete?.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                deleteAll()
                Timber.d("Data Deleted")
            }
        }

        //Insert
        questionDao = QuestionDatabase.getInstance(requireContext()).questionDao()


    }

    private fun deleteAll() {
        questionDao.deleteAllQuestions()
    }


    //Insert
    private fun insertData() {
        val questionOne =
            QuestionEntity("日", "にち", "やま", "ほん", "かわ" )
        val questionTwo =
            QuestionEntity("月", "つき", "かね", "ほん", "かわ" )
        val questionThree =
            QuestionEntity("水", "みず", "にち", "かね", "いち" )

        questionDao.apply {
            insertQuestion(questionOne)
            insertQuestion(questionTwo)
            insertQuestion(questionThree)
            Timber.d("Data: $questionOne")
            Timber.d("Data: $questionTwo")
            Timber.d("Data: $questionThree")
        }


    }

    private fun navigateToKanjiQuizFragment() {
        navController = findNavController()
        navController.navigate(R.id.action_homeFragment_to_kanjiQuizFragment)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}