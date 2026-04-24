package com.example.dapuribuproject.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.dapuribuproject.GantiPasswordActivity
import com.example.dapuribuproject.Helper.DatabaseHelper
import com.example.dapuribuproject.LoginActivity
import com.example.dapuribuproject.R
import com.example.dapuribuproject.RegistrasiActivity

class Profile_Fragment : Fragment() {

    private lateinit var db: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnLogout = view.findViewById<Button>(R.id.btnLogout)
        val btnChangePassword = view.findViewById<Button>(R.id.btnChangePassword)


        //Ambil Field Username | Role | Email | Tanggal Lahir | Password
        val tvRole = view.findViewById<TextView>(R.id.tvRole)
        val tvUsername = view.findViewById<TextView>(R.id.tvUsName)
        val tvEmail = view.findViewById<TextView>(R.id.tvEmail)
        val tvtanggallahir = view.findViewById<TextView>(R.id.tvtanggallahir)
        val tvpassword = view.findViewById<TextView>(R.id.tvpassword)

        //Ambil Data Dari Intent
        val username = activity?.intent?.getStringExtra("username") ?: "User"
        tvUsername.text = username

        //Ambil Role | Email | Tempat Tanggal Lahir | Password
        db = DatabaseHelper(requireContext())
        val role = db.getRoles(username)
        val email = db.getEmail(username)
        val tanggallahir = db.getTanggalLahir(username)
        val password = db.getPassword(username)


        //Isi TV
        tvRole.text = role
        tvEmail.text = email
        tvtanggallahir.text = tanggallahir
        tvpassword.text = "*".repeat(password.length)

        btnLogout.setOnClickListener {
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            startActivity(intent)
        }

        btnChangePassword.setOnClickListener {
            val intent = Intent(requireActivity(), GantiPasswordActivity::class.java)
            intent.putExtra("username", username)
            startActivity(intent)
        }

    }
}