package com.example.androidassignment.adapter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidassignment.date.DateFormatter
import com.example.androidassignment.views.activities.DetailsScreen
import com.example.androidassignment.views.activities.LoginActivity.Companion.THREAD_DETAILS
import com.example.androidassignment.R
import com.example.androidassignment.model.Message
import com.example.androidassignment.model.ThreadDetails
import kotlinx.android.synthetic.main.message_thread_view.view.*
import java.util.*


class MessageListAdapter(
    private val messages: List<Message>
) :
    RecyclerView.Adapter<MessageListAdapter.ViewHolder>() {

    init {
        Collections.sort(
            messages
        ) { o1, o2 ->
            o2.timestamp.compareTo(
                o1.timestamp
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.message_thread_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = messages.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message: Message = messages[position]
        holder.userId.text =
            holder.itemView.context.getString(R.string.user_id_label, message.userId)
        if (message.agentId == null) {
            holder.agentLayout.visibility = View.GONE
        } else {
            holder.agentId.text =
                holder.itemView.context.getString(R.string.agent_id_label, message.agentId)
        }
        holder.body.text = message.body
        holder.time.text = DateFormatter.outputDateFormat().format(message.timestamp)
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailsScreen::class.java)
            val threadDetails = ThreadDetails(message.threadId, message.body)
            val bundle = Bundle()
            bundle.putSerializable(THREAD_DETAILS, threadDetails)
            intent.putExtras(bundle)
            holder.itemView.context.startActivity(intent)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val body: TextView = itemView.body_text
        val userId: TextView = itemView.user_id
        val agentId: TextView = itemView.agent_id
        val agentLayout: LinearLayout = itemView.agent_layout
        val time: TextView = itemView.time_stamp
    }

}