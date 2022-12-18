package com.example.contact_book.ui.view.fragments
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contact_book.R
import com.example.contact_book.databinding.FragmentListBinding
import com.example.contact_book.domain.model.Session
import com.example.contact_book.data.database.user.User
import com.example.contact_book.domain.model.Alert
import com.example.contact_book.ui.view.activitys.AuthActivity
import com.example.contact_book.adapters.ListAdapter
import com.example.contact_book.viewmodel.AuthViewModel
import com.example.contact_book.viewmodel.TokenViewModel
import com.example.contact_book.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var binding: FragmentListBinding
    private lateinit var adapter: ListAdapter
    private lateinit var menuHost: MenuHost
    private var userList = mutableListOf<User>()
    private val userViewModel by viewModels<UserViewModel>()
    private val authViewModel by viewModels<AuthViewModel>()
    private val tokenViewModel by viewModels<TokenViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (requireActivity() as AppCompatActivity).supportActionBar?.show()

        menuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {

               menuInflater.inflate(R.menu.delete_all_menu, menu)

               val search = menu.findItem(R.id.menuSearch)

               val searchView = search.actionView as? SearchView

               searchView?.isSubmitButtonEnabled = true

               searchView?.setOnQueryTextListener(this@ListFragment)

            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {

               return when(menuItem.itemId){

                    R.id.menuAllDelete ->{

                        deleteAllDatabase()

                        true

                    }

                    else -> false

                }

            }

        },viewLifecycleOwner, Lifecycle.State.RESUMED)

        return inflater.inflate(R.layout.fragment_list,container,false)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentListBinding.bind(view)

        binding.tvCurrentUser.text = authViewModel.currentUser?.displayName.toString()

        binding.btAdd.setOnClickListener(listener)

        binding.btExit.setOnClickListener(listener)

        initRecycler()

        observeData()

    }

    val listener = View.OnClickListener {  view ->

        when(view.id){

            R.id.btAdd -> {

                findNavController().navigate(R.id.action_listFragment_to_addFragment)

            }

            R.id.btExit -> {

                authViewModel.logout()

                tokenViewModel.deleteAll()

                segueOnBack()
            }

        }

    }


    private fun initRecycler(){

        adapter = ListAdapter(userList){ currentUser ->

            onClickListener(currentUser)

        }

        val manager = LinearLayoutManager(requireContext())

        val recycler = binding.rvRecycler

        recycler.layoutManager = manager

        recycler.adapter = adapter


    }


    private fun observeData(){

        userViewModel.readAllData.observe(viewLifecycleOwner, Observer { listUser ->

          userList.clear()

          userList.addAll(listUser)

          adapter.notifyDataSetChanged()

        })

    }

    private fun onClickListener(user: User){

        val action = ListFragmentDirections.actionListFragmentToUpdateFragment(user)

        findNavController().navigate(action)

    }

    private fun deleteAllDatabase() {

        val title = getString(R.string.deleteAll)

        val message = getString(R.string.deleteContact)

        Alert.showDelete( context = requireContext(), message = message, title = title ) { result ->

            userViewModel.deleteAllUsers()

            val message = getString(R.string.deleteAllUsers)

            Alert.showSuccess( context = requireContext(), message = message ){}

        }

    }

    private fun searchUser(query: String){

        val searchQuery = "%$query%"

            userViewModel.searchUser(searchQuery).observe(viewLifecycleOwner, Observer { listSearch ->

                userList.clear()

                userList.addAll(listSearch)

                adapter.notifyDataSetChanged()


            })


    }


    private fun segueOnBack(){

        val session = Session(activity= requireActivity())

        session.close()

        val intent = Intent(requireContext(), AuthActivity::class.java)

        startActivity(intent)

    }

    override fun onQueryTextSubmit(query: String?): Boolean {

        if(query != null){

            searchUser(query)
        }

        return true

    }

    override fun onQueryTextChange(query: String?): Boolean {

        if(query != null){

            searchUser(query)
        }

        return true

    }


}







