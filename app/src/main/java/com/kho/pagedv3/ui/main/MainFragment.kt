package com.kho.pagedv3.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.kho.pagedv3.core.model.DataUser
import com.kho.pagedv3.databinding.MainFragmentBinding
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {
    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: MainFragmentBinding
    private val pagedAdapter: UserListPagedAdapter by lazy { UserListPagedAdapter(this::onClickItem) }

    companion object {
        const val STATE_DATA_EMPTY = 0
        const val STATE_DATA_ERROR = 1
        const val STATE_HAS_DATA = 2
        fun newInstance() = MainFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setState(STATE_DATA_EMPTY)
        initRecyclerView()
        initRefresh()
        loadData()
    }

    private fun setState(state: Int, messageError: String = "") {
        binding.flipState.displayedChild = state
        if (state == STATE_DATA_ERROR) {
            binding.tvInitialError.text = "Something has error\n${messageError}"
        }
    }

    private fun initRefresh() {
        binding.swRoRefresh.setOnRefreshListener {
            pagedAdapter.refresh()
        }

        lifecycleScope.launchWhenCreated {
            pagedAdapter.loadStateFlow.collectLatest { loadStates ->
                binding.swRoRefresh.isRefreshing = loadStates.refresh is LoadState.Loading
            }
        }
    }

    private fun initRecyclerView() {

        binding.rvRoList.apply {
            adapter = pagedAdapter.withLoadStateHeaderAndFooter(
                header = PagingLoadStateAdapter(pagedAdapter),
                footer = PagingLoadStateAdapter(pagedAdapter)
            )
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun loadData() {
        lifecycleScope.launchWhenCreated {
            viewModel.fetchData().collectLatest {
                setState(STATE_HAS_DATA)
                pagedAdapter.submitData(it)
            }
        }

        viewModel.liveDataInitialLoadData.observe(viewLifecycleOwner) {
            setState(it.first,it.second)
        }

    }

    private fun onClickItem(data: DataUser) {
        //TODO: Do something when click item.
    }

}