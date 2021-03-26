package com.example.thesisresearchplayground.ui.main.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thesisresearchplayground.R
import com.example.thesisresearchplayground.data.model.User
import com.example.thesisresearchplayground.ui.main.adapter.MainAdapter
import com.example.thesisresearchplayground.ui.main.viewmodel.LandingViewModel
import com.example.thesisresearchplayground.ui.main.viewmodel.MainActivityViewModel
import com.example.thesisresearchplayground.utils.Status
import org.koin.android.viewmodel.ext.android.viewModel

class LandingFragment : Fragment() {

    private val viewModel: LandingViewModel by viewModel()
    private val mainViewModel: MainActivityViewModel by viewModel()

    private lateinit var adapter: MainAdapter

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root: View =  inflater.inflate(R.layout.fragment_landing, container, false)
        recyclerView = root.findViewById(R.id.recyclerView)
        progressBar = root.findViewById(R.id.progressBar)

        setupUI()
        setupObserver()

        return root
    }

    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = MainAdapter(arrayListOf())
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
    }

    private fun setupObserver() {
        mainViewModel.users.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    it.data?.let {
                            users -> renderList(users)
                    }
                    recyclerView.visibility = View.VISIBLE
                }

                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }

                Status.ERROR -> {
                    progressBar.visibility = View.GONE
                    Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }


    private fun renderList(users: List<User>) {
        adapter.addData(users)
        adapter.notifyDataSetChanged()
    }
}