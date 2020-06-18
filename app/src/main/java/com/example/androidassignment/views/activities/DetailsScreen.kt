package com.example.androidassignment.views.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.androidassignment.date.DateFormatter
import com.example.androidassignment.R
import com.example.androidassignment.views.activities.LoginActivity.Companion.THREAD_DETAILS
import com.example.androidassignment.model.Message
import com.example.androidassignment.model.ThreadDetails
import com.example.androidassignment.network.NetworkConnection
import com.example.androidassignment.presenter.DetailPresenter
import com.example.androidassignment.views.DetailViewPresenter
import kotlinx.android.synthetic.main.thread_details_view.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class DetailsScreen : AppCompatActivity(), DetailViewPresenter.DetailView {

    lateinit var detailViewPresenter: DetailViewPresenter.DetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.thread_details_view)
        setUpToolbar()
        detailViewPresenter = DetailPresenter()
        detailViewPresenter.attachView(this)
    }

    override fun init() {
        val threadDetails = intent?.extras?.let {
            it.getSerializable(THREAD_DETAILS) as? ThreadDetails
        }
        threadDetails?.let {
            detailViewPresenter.fetchData(it)
        }

        reply_button.setOnClickListener {
            reply.setText("")
            reply.clearFocus()
            Toast.makeText(this, getString(R.string.replied_text), Toast.LENGTH_LONG).show()
        }
    }

    override fun updateData(message: Message) {
        thread_id.text = getString(R.string.thread_id_label, message.threadId)
        user_id.text = message.userId.toString()
        agent_id.text = message.agentId.toString()
        message_body.text = message.body
        date.text = DateFormatter.outputDateFormat()
            .format(message.timestamp)

        progress_circular.visibility = View.GONE
        details_layout.visibility = View.VISIBLE
    }

    override fun onError() {
        Toast.makeText(this, getString(R.string.server_error), Toast.LENGTH_LONG).show()
    }

    override fun isInternetConnected(): Boolean = NetworkConnection.isNetworkConnected(this)

    override fun noInternet() {
        Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_LONG).show()
    }

    override fun loading() {
        progress_circular.visibility = View.VISIBLE
        details_layout.visibility = View.GONE
    }

    private fun setUpToolbar() {
        val toolbar = tool_bar.findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        tool_bar_title.text = getString(R.string.details_text)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onDestroy() {
        detailViewPresenter.onDestroy()
        super.onDestroy()
    }

}