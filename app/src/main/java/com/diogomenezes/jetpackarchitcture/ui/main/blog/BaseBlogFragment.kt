package com.diogomenezes.jetpackarchitcture.ui.main.blog

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.bumptech.glide.RequestManager
import com.diogomenezes.jetpackarchitcture.R
import com.diogomenezes.jetpackarchitcture.models.BlogPost
import com.diogomenezes.jetpackarchitcture.ui.DataStateChangeListener
import com.diogomenezes.jetpackarchitcture.ui.UICommunicationListener
import com.diogomenezes.jetpackarchitcture.ui.main.blog.viewmodel.BlogViewModel
import com.diogomenezes.jetpackarchitcture.ui.main.blog.viewmodel.setBlogListData
import com.diogomenezes.jetpackarchitcture.util.Constants
import com.diogomenezes.jetpackarchitcture.viewmodel.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseBlogFragment : DaggerFragment() {

    lateinit var stateChangeListener: DataStateChangeListener

    lateinit var uiCommunicationListener: UICommunicationListener

    @Inject
    lateinit var requestManager: RequestManager

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    lateinit var viewModel: BlogViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = activity?.run {
            ViewModelProvider(this, viewModelProviderFactory).get(BlogViewModel::class.java)
        } ?: throw Exception("Invalid activity")

        savedInstanceState?.let { bundle ->
            bundle["blog_list"]?.let { blogList ->
                viewModel.setBlogListData(blogList as ArrayList<BlogPost>)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //In production should save the query
        outState.putParcelableArrayList(
            Constants.BLOG_LIST,
            (viewModel.viewState.value?.blogFields?.blogList as ArrayList<BlogPost>)
        )
    }

    fun setupActionBarWithNavController(fragmentId: Int, activity: AppCompatActivity) {
        val appBarConfiguration = AppBarConfiguration(setOf(fragmentId))
        NavigationUI.setupActionBarWithNavController(
            activity,
            findNavController(),
            appBarConfiguration
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupActionBarWithNavController(R.id.blogFragment, activity as AppCompatActivity)
        cancelActiveJobs()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            stateChangeListener = context as DataStateChangeListener
        } catch (e: ClassCastException) {
            Log.e("BaseBlogFragment", "onAttach : must implement  stateChangeListener")
        }

        try {
            uiCommunicationListener = context as UICommunicationListener
        } catch (e: ClassCastException) {
            Log.e("BaseBlogFragment", "onAttach : must implement  uiCommunicationListener")
        }
    }

    fun cancelActiveJobs() {
        viewModel.cancelActiveJobs()
    }
}