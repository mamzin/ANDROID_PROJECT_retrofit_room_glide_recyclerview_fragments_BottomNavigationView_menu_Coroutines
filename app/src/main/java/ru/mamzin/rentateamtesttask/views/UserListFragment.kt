package ru.mamzin.rentateamtesttask.views

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.mamzin.rentateamtesttask.R
import ru.mamzin.rentateamtesttask.model.DataAdapter
import ru.mamzin.rentateamtesttask.model.User
import ru.mamzin.rentateamtesttask.net.RetrofitService
import ru.mamzin.rentateamtesttask.viewmodel.DBViewModel
import ru.mamzin.rentateamtesttask.viewmodel.NetViewModel
import ru.mamzin.rentateamtesttask.repository.NetRepository
import ru.mamzin.rentateamtesttask.viewmodel.ViewModelFactory


class UserListFragment : Fragment(), DataAdapter.CellClickListener {

    private val adapter = DataAdapter(this)
    lateinit var recyclerView: RecyclerView
    lateinit var pbLoading: ProgressBar
    lateinit var netViewModel: NetViewModel

    val dbviewmodel: DBViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_user_list, container, false)

        if (!checkForInternet(requireContext())) {
            ifNotInternet()
        }

        recyclerView = root.findViewById(R.id.rvData)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        pbLoading = root.findViewById(R.id.pbLoading)

        val mainRepository = NetRepository(RetrofitService.getInstance())
        recyclerView.adapter = adapter


        netViewModel = ViewModelProvider(this, ViewModelFactory(mainRepository))[NetViewModel::class.java]

        netViewModel.dataList.observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.setUserList(it)
                dbviewmodel.addUser(it)
            }
        }

        netViewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        netViewModel.loading.observe(viewLifecycleOwner, Observer {
            if (it) {
                pbLoading.visibility = View.VISIBLE
            } else {
                pbLoading.visibility = View.GONE
            }
        })

        netViewModel.getAllUsers()

        return root
    }

    private fun ifNotInternet() {
        val builder = AlertDialog.Builder(requireContext())
        with(builder)
        {
            setTitle("Ошибка загрузки данных с сайта")
            setMessage("Данные будут загружены из БД")
            show()
        }
        dbviewmodel.allUsers.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                adapter.setUserList(it)
            }

        })
    }

    private fun checkForInternet(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION")
            val networkInfo = connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = UserListFragment()
    }

    override fun onCellClickListener(data: User) {
        val bundle = Bundle()
        bundle.putString("first_name", data.first_name)
        bundle.putString("last_name", data.last_name)
        bundle.putString("mail", data.email)
        bundle.putString("urlphoto", data.avatar)
        parentFragmentManager.setFragmentResult("userdata", bundle)

        var navigationView: BottomNavigationView? =
            activity?.findViewById(R.id.bNav) as BottomNavigationView?
        if (navigationView != null) {
            navigationView.selectedItemId = R.id.user_menuitem
        }
    }
}
