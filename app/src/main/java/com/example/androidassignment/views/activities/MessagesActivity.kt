package com.example.androidassignment.views.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidassignment.R
import com.example.androidassignment.adapter.MessageListAdapter
import com.example.androidassignment.model.AuthToken
import com.example.androidassignment.model.Message
import com.example.androidassignment.network.NetworkConnection
import com.example.androidassignment.preferences.ModelPreferencesManager
import com.example.androidassignment.presenter.MessagePresenter
import com.example.androidassignment.views.MessageViewPresenter
import kotlinx.android.synthetic.main.messages_activity.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class MessagesActivity : AppCompatActivity(), MessageViewPresenter.MessageView {

    private lateinit var messagePresenter: MessageViewPresenter.MessagePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.messages_activity)
        setUpToolbar()
        messagePresenter = MessagePresenter()
        messagePresenter.attachView(this)
    }

    override fun initViews(authToken: AuthToken?) {
        recycler_view.setHasFixedSize(true)
        recycler_view.itemAnimator = DefaultItemAnimator()
        recycler_view.fitsSystemWindows = true
        recycler_view.layoutManager = LinearLayoutManager(this)
        retry_button.setOnClickListener {
            finish()
        }
        authToken?.let {
            messagePresenter.fetchData(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.logout_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.logout_action) {
            showLogoutPopUp()
            return true
        }

        return super.onOptionsItemSelected(item)
    }


    override fun updateData(posts: List<Message>) {
        progress_bar.visibility = View.GONE
        error_layout.visibility = View.GONE
        recycler_view.visibility = View.VISIBLE
        val adapter = MessageListAdapter(posts)
        recycler_view.adapter = adapter
    }

    override fun onError() {
        progress_bar.visibility = View.GONE
        error_layout.visibility = View.VISIBLE
    }

    override fun isInternetConnected(): Boolean = NetworkConnection.isNetworkConnected(this)

    override fun noInternet() {
        Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_LONG).show()
    }

    override fun launchLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun setUpToolbar() {
        val toolbar = tool_bar.findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        tool_bar_title.text = getString(R.string.messages_text)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun loading() {
        recycler_view.visibility = View.GONE
        error_layout.visibility = View.GONE
        progress_bar.visibility = View.VISIBLE
    }

    private fun showLogoutPopUp() {
        val dialogBuilder = AlertDialog.Builder(this)

        dialogBuilder
            .setCancelable(false)
            .setPositiveButton(
                getString(R.string.yes_text)
            ) { dialog, id ->
                ModelPreferencesManager.preferences.edit().clear().apply()
                launchLogin()
            }
            .setNegativeButton(
                getString(R.string.cancel_text)
            ) { dialog, id ->
                dialog.cancel()
            }

        val alert = dialogBuilder.create()
        alert.setTitle(getString(R.string.logout))
        alert.show()
    }

    override fun onDestroy() {
        messagePresenter.onDestroy()
        super.onDestroy()
    }

}