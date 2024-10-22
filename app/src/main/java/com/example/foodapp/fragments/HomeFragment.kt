package com.example.foodapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.foodapp.activites.CategoryMealsActivity
import com.example.foodapp.activites.MainActivity
import com.example.foodapp.activites.MealActivity
import com.example.foodapp.adapter.CategoriesAdapter
import com.example.foodapp.adapter.MostPopularAdapter
import com.example.foodapp.databinding.FragmentHomeBinding
import com.example.foodapp.pojo.Popular
import com.example.foodapp.pojo.Meal
import com.example.foodapp.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel:HomeViewModel
    private lateinit var randonMeal:Meal
    private lateinit var popularAdapter: MostPopularAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter

    companion object{
        const val MEAL_ID = "com.example.foodapp.fragments.idMeal"
        const val MEAL_NAME = "com.example.foodapp.fragments.nameMeal"
        const val MEAL_THUMB = "com.example.foodapp.fragments.thumbMeal"
        const val CATEGORY_NAME = "com.example.foodapp.fragments.categoryName"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        popularAdapter = MostPopularAdapter()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preparePopularRecyclerView()
        prepareCategoryRecyclerView()
        viewModel.getRandomMeal()
        observeRandomMeal()
        onRandomMealClick()

        viewModel.getPopularItem()
        observePopularMeal()
        onPopularClick()


        viewModel.getCategories()
        observeCategory()
        onCategoryClick()



    }

    private fun onCategoryClick() {
        categoriesAdapter.onItemClick = {
            category ->
            val intent = Intent(activity, CategoryMealsActivity::class.java)
            intent.putExtra(CATEGORY_NAME, category.strCategory)
            startActivity(intent)
        }
    }

    private fun observeCategory() {
        viewModel.observeCategoryLiveData().observe(viewLifecycleOwner){
            categories ->
                categoriesAdapter.setCategoryList(categories)
        }
    }

    private fun onPopularClick() {
        popularAdapter.onItemClick = {
            meal->
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, meal.idMeal)
            intent.putExtra(MEAL_NAME, meal.strMeal)
            intent.putExtra(MEAL_THUMB, meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun preparePopularRecyclerView() {
        binding.recViewMealsPopular.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularAdapter

        }
    }

    private fun prepareCategoryRecyclerView() {
        categoriesAdapter = CategoriesAdapter()
        binding.recViewCategories.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoriesAdapter

        }
    }

    private fun observePopularMeal() {
        viewModel.observePopularMealLiveData().observe(viewLifecycleOwner
        ) {mealList ->
            popularAdapter.setMeals(mealList= mealList as ArrayList<Popular>)
        }
    }

    private fun onRandomMealClick() {
        binding.randomMealCard.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, randonMeal.idMeal)
            intent.putExtra(MEAL_NAME, randonMeal.strMeal)
            intent.putExtra(MEAL_THUMB, randonMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observeRandomMeal() {
        viewModel.observeRandomMealLiveData().observe(viewLifecycleOwner
        ) { value ->
            Glide.with(this@HomeFragment)
                .load(value.strMealThumb)
                .into(binding.imgRandomMeal)
            this.randonMeal = value
        }
    }


}