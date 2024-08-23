package com.glucode.about_you.engineers

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.glucode.about_you.R
import com.glucode.about_you.databinding.FragmentEngineersBinding
import com.glucode.about_you.engineers.models.Engineer
import com.glucode.about_you.mockdata.MockData
import com.google.android.material.snackbar.Snackbar

class EngineersFragment : Fragment() {
    private lateinit var binding: FragmentEngineersBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEngineersBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        setUpEngineersList(MockData.engineers)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_engineers, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_years -> {
                orderEngineers(MockData.engineers,"years")
                Snackbar.make(binding.root,"Ordered by years", Snackbar.LENGTH_SHORT).show()
                return true
            }
            R.id.action_coffees -> {
                orderEngineers(MockData.engineers,"coffees")
                Snackbar.make(binding.root,"Ordered by coffees", Snackbar.LENGTH_SHORT).show()
                return true
            }
            R.id.action_bugs -> {
                orderEngineers(MockData.engineers,"bugs")
                Snackbar.make(binding.root,"Ordered by bugs", Snackbar.LENGTH_SHORT).show()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun orderEngineers(engineers: List<Engineer>, order: String){
        val engineersByCoffee = engineers.sortedWith(compareBy { it.quickStats.coffees })
        val engineersByYears = engineers.sortedWith(compareBy { it.quickStats.years })
        val engineersByBugs = engineers.sortedWith(compareBy { it.quickStats.bugs })
        when (order) {
            "years" -> {
                setUpEngineersList(engineersByYears)
            }
            "coffees" -> {
                setUpEngineersList(engineersByCoffee)
            }
            "bugs" -> {
                setUpEngineersList(engineersByBugs)
            }
        }
    }

    private fun setUpEngineersList(engineers: List<Engineer>) {
        binding.list.adapter = EngineersRecyclerViewAdapter(engineers) {
            goToAbout(it)
        }
        val dividerItemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding.list.addItemDecoration(dividerItemDecoration)
    }

    private fun goToAbout(engineer: Engineer) {
        val bundle = Bundle().apply {
            putString("name", engineer.name)
        }
        findNavController().navigate(R.id.action_engineersFragment_to_aboutFragment, bundle)
    }
}