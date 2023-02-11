package com.example.tripplanner.ui.fragments.menu_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplanner.databinding.FragmentFilterTripsListBinding
import com.example.tripplanner.db.entities.CategoryEntity
import com.example.tripplanner.domain.Resource
import com.example.tripplanner.ui.activities.MenuActivity
import com.example.tripplanner.ui.adapters.CategoriesFilterAdapter
import com.example.tripplanner.ui.adapters.CityFilterAdapter
import com.example.tripplanner.view_models.FilterTripsListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FilterTripsListFragment : Fragment() {

    private val menuActivityInstance by lazy { activity as MenuActivity }
    private lateinit var viewBinding: FragmentFilterTripsListBinding
    private val filtersViewModel: FilterTripsListViewModel by viewModels()
    private var cityAdapter: CityFilterAdapter? = null
    private var categoryAdapter: CategoriesFilterAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewBinding()
        menuActivityInstance.hideMenu()
        initCitiesRecyclerView()
        initCategoriesRecyclerView()
        collectFilters()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        getFilters()
        initClearBtn()
        return viewBinding.root
    }

    private fun initClearBtn(){
        viewBinding.btnClear.setOnClickListener {
            filtersViewModel.clearDatabase()
            getFilters()
        }
    }

    private fun getFilters() {
        filtersViewModel.getFilters()
    }

    private fun collectFilters() {
        lifecycleScope.launch {
            filtersViewModel.response.collect {
                when (it) {
                    is Resource.Success -> {
                            cityAdapter?.addData(it.data.cities)
                        lifecycleScope.launch(Dispatchers.IO) {
                            filtersViewModel.getCategoryFilterFromDB().let { dbCategories ->
                                categoryAdapter?.addData(it.data.categories, dbCategories)
                            }
                        }
                    }
                    else -> {}
                }
            }
        }
    }

    private fun initCitiesRecyclerView() {
        cityAdapter = CityFilterAdapter()
        val llm = LinearLayoutManager(activity?.baseContext)
        llm.orientation = RecyclerView.VERTICAL
        viewBinding.rvCitiesFilters.adapter = cityAdapter
        viewBinding.rvCitiesFilters.isNestedScrollingEnabled = false
        viewBinding.rvCitiesFilters.addItemDecoration(DividerItemDecoration(this.context,DividerItemDecoration.VERTICAL))
        viewBinding.rvCitiesFilters.layoutManager = llm
    }

    private fun addCategoryToDB(categoryEntity: CategoryEntity) {
        lifecycleScope.launch(Dispatchers.IO) {
            filtersViewModel.addCategoryFilterToDB(categoryEntity)
        }}

    private fun removeCategoryToDB(categoryName: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            filtersViewModel.removeCategoryFilterToDB(categoryName)
        }}

    private fun initCategoriesRecyclerView() {
        categoryAdapter = CategoriesFilterAdapter(::addCategoryToDB, ::removeCategoryToDB)
        val llm = LinearLayoutManager(activity?.baseContext)
        llm.orientation = RecyclerView.VERTICAL
        viewBinding.rvCategoryFilters.adapter = categoryAdapter
        viewBinding.rvCategoryFilters.isNestedScrollingEnabled = false
        viewBinding.rvCategoryFilters.addItemDecoration(DividerItemDecoration(this.context,DividerItemDecoration.VERTICAL))
        viewBinding.rvCategoryFilters.layoutManager = llm
    }

    private fun initViewBinding() {
        viewBinding = FragmentFilterTripsListBinding.inflate(layoutInflater)
    }
}