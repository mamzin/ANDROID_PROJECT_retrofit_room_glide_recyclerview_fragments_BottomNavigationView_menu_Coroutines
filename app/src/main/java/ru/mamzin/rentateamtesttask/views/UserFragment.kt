package ru.mamzin.rentateamtesttask.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import ru.mamzin.rentateamtesttask.R

class UserFragment : Fragment() {

    lateinit var tvname: TextView
    lateinit var tvfam: TextView
    lateinit var tvemail: TextView
    lateinit var tv_emptydata: TextView
    lateinit var photo: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_user, container, false)
        tvname = root.findViewById(R.id.tv_name)
        tvfam = root.findViewById(R.id.tv_secondname)
        tvemail = root.findViewById(R.id.tvmail)
        tv_emptydata = root.findViewById(R.id.tv_emptydata)
        photo = root.findViewById(R.id.ivPhoto)

        parentFragmentManager.setFragmentResultListener("userdata", this) { key, bundle ->
            if (bundle.isEmpty) {
                tvname.visibility = View.GONE
                tvfam.visibility = View.GONE
                tvemail.visibility = View.GONE
                tv_emptydata.visibility = View.VISIBLE
            }
            else {
                tv_emptydata.visibility = View.GONE
                tvname.visibility = View.VISIBLE
                tvfam.visibility = View.VISIBLE
                tvemail.visibility = View.VISIBLE
                tvname!!.text = bundle.getString("first_name")
                tvfam!!.text = bundle.getString("last_name")
                tvemail!!.text = bundle.getString("mail")
                Glide.with(this)
                    .load(bundle.getString("urlphoto"))
                    .centerCrop()
                    .into(photo)
            }
        }
        return root
    }

    companion object {
        @JvmStatic
        fun newInstance() = UserFragment()
    }
}