package com.example.tripplanner.ui.fragments.menu_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplanner.databinding.FragmentFilterTripsListBinding
import com.example.tripplanner.db.entities.CategoryEntity
import com.example.tripplanner.db.entities.CityEntity
import com.example.tripplanner.ui.activities.MenuActivity
import com.example.tripplanner.ui.adapters.CategoriesFilterAdapter
import com.example.tripplanner.ui.adapters.CityFilterAdapter
import com.example.tripplanner.view_models.FilterTripsListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
        initCitiesRecyclerView()
        initCategoriesRecyclerView()
        getFilters()
        menuActivityInstance.hideMenu()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initClearBtn()
        initCitiesSearchView()
        initCategoriesSearchView()
        return viewBinding.root
    }

    private fun initCitiesSearchView(){
        viewBinding.svCity.setOnQueryTextListener(object : OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                lifecycleScope.launch(Dispatchers.IO) {
                    filtersViewModel.getCityFilterByName(newText?:"").let {
                        withContext(Dispatchers.Main) {
                            viewBinding.tvEmpty1.isVisible=it.isEmpty()
                            cityAdapter?.addData(it)
                        }
                    }
                }
                return true
            }
        })
    }

    private fun initCategoriesSearchView(){
        viewBinding.svCategory.setOnQueryTextListener(object : OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                lifecycleScope.launch(Dispatchers.IO) {
                    filtersViewModel.getCategoryFilterByName(newText?:"").let {
                        withContext(Dispatchers.Main) {
                            viewBinding.tvEmpty2.isVisible=it.isEmpty()
                            categoryAdapter?.addData(it)
                        }
                    }
                }
                return true
            }
        })
    }

    private fun initClearBtn(){
        viewBinding.btnClear.setOnClickListener {
            filtersViewModel.clearDatabase()
        }
    }

    private fun getFilters() {
        lifecycleScope.launch(Dispatchers.Main) {
            filtersViewModel.getCategoryFilterFromDB().collect {
                if(viewBinding.svCategory.query.isNullOrBlank()) {
                    viewBinding.tvEmpty2.isVisible=it.isEmpty()
                    categoryAdapter?.addData(it)
                }
            }
        }
        lifecycleScope.launch(Dispatchers.Main){
            filtersViewModel.getCityFilterFromDB().collect{
                viewBinding.tvEmpty1.isVisible=it.isEmpty()
                cityAdapter?.addData(it)
            }
        }
    }

    private fun initCitiesRecyclerView() {
        cityAdapter = CityFilterAdapter(::updateCityDB, ::clear)
        val llm = LinearLayoutManager(activity?.baseContext)
        llm.orientation = RecyclerView.VERTICAL
        viewBinding.rvCitiesFilters.adapter = cityAdapter
        viewBinding.rvCitiesFilters.isNestedScrollingEnabled = false
        viewBinding.rvCitiesFilters.addItemDecoration(DividerItemDecoration(this.context,DividerItemDecoration.VERTICAL))
        viewBinding.rvCitiesFilters.layoutManager = llm
    }

    private fun initCategoriesRecyclerView() {
        categoryAdapter = CategoriesFilterAdapter(::updateCategoryDB)
        val llm = LinearLayoutManager(activity?.baseContext)
        llm.orientation = RecyclerView.VERTICAL
        viewBinding.rvCategoryFilters.adapter = categoryAdapter
        viewBinding.rvCategoryFilters.isNestedScrollingEnabled = false
        viewBinding.rvCategoryFilters.addItemDecoration(DividerItemDecoration(this.context,DividerItemDecoration.VERTICAL))
        viewBinding.rvCategoryFilters.layoutManager = llm
    }

    private fun clear() {
        lifecycleScope.launch(Dispatchers.IO){
            filtersViewModel.clearCity()
        }
    }

    private fun updateCategoryDB(categoryEntity: CategoryEntity) {
        lifecycleScope.launch(Dispatchers.IO) {
                filtersViewModel.updateCategoryFilterToDB(categoryEntity)
        }}

    private fun updateCityDB(city: CityEntity) {
        lifecycleScope.launch(Dispatchers.IO) {
            async {
            filtersViewModel.clearCity()
            filtersViewModel.updateCityFilterToDB(city)
            }.await()
        }}

    private fun initViewBinding() {
        viewBinding = FragmentFilterTripsListBinding.inflate(layoutInflater)
    }
}