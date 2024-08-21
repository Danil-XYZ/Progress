package com.example.progress

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.progress.database.ListModel

class ListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private val adapter = ListAdapter(arrayListOf())
    private lateinit var createFragment: CreateFragment

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(view.context, 2)
        recyclerView.adapter = adapter

        createFragment = CreateFragment()

        val fragmentCreateContainer: FrameLayout = view.findViewById(R.id.fragment_create_container)

        val createButton : ImageButton = view.findViewById(R.id.create_button)
        createButton.setOnClickListener {
            childFragmentManager.commit{
                add(R.id.fragment_create_container, createFragment)
                addToBackStack(null)
                fragmentCreateContainer.isClickable = true
                fragmentCreateContainer.isFocusable = true
            }

            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    this.isEnabled = false
                    childFragmentManager.commit{
                        remove(createFragment)
                    }
                    fragmentCreateContainer.isClickable = false
                    fragmentCreateContainer.isFocusable = false
                }
            })
        }

        return view
    }

    fun updateData(dataList: List<ListModel>) {
        adapter.updateData(dataList)
    }


}