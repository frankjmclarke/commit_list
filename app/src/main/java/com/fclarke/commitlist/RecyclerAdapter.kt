package com.fclarke.commitlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

/*
The Adapter has a ViewHolder
 */

class RecyclerAdapter(
    private val list: List<MainFragment.Commit>, val itemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<CommitViewHolder>() {

    private var selectedItem = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommitViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CommitViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: CommitViewHolder, position: Int) {
        val commit: MainFragment.Commit = list[position]
        holder.itemView.setSelected(selectedItem == position)
        holder.itemView?.isFocusable=true;
        holder.itemView?.isClickable=true;
        holder.bind(commit,itemClickListener)
        holder.itemView?.setOnClickListener {
            var mImage:ImageView = holder.itemView.findViewById(R.id.icon)
            Picasso.get().load(commit.avatarUrl).into(mImage);
            notifyItemChanged(selectedItem)
            selectedItem = holder.layoutPosition
            notifyItemChanged(selectedItem)
            itemClickListener.onItemClicked(commit)
        }
    }


    override fun onViewAttachedToWindow(holder: CommitViewHolder) {
        super.onViewAttachedToWindow(holder)
        var itemView = holder.itemView

        var mImage:ImageView = itemView.findViewById(R.id.icon)

        var animCrossFadeIn = AnimationUtils.loadAnimation(
            itemView.context,
            R.anim.fade_in
        )
        var animCrossFadeOut = AnimationUtils.loadAnimation(
            itemView.context,
            R.anim.fade_out
        )
        val txtOut = itemView.findViewById(R.id.txt_out) as TextView
        val txtIn = itemView.findViewById(R.id.txt_in) as TextView
        txtOut.visibility = View.VISIBLE
        txtIn.startAnimation(animCrossFadeIn)
        txtOut.startAnimation(animCrossFadeOut)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            val lm = recyclerView.layoutManager
            false
        })
    }

    override fun getItemCount(): Int = list.size
}


class CommitViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.commit_detail, parent, false)) {
    private var mTitleView: TextView? = null
    private var mTxtIn: TextView? = null
    private var mTxtOut: TextView? = null
    private var mImage: ImageView? = null

    init {
        mTitleView = itemView.findViewById(R.id.title)
        mTxtIn = itemView.findViewById(R.id.txt_in)
        mTxtOut = itemView.findViewById(R.id.txt_out)
        mImage = itemView.findViewById(R.id.icon)
    }

    fun bind(commit: MainFragment.Commit,clickListener: OnItemClickListener) {
        mTitleView?.text = commit.message
        mTxtIn?.text = commit.dateIn
        mTxtOut?.text = commit.loginOut
        mImage?.setImageResource(R.drawable.user)
        mTxtOut?.isFocusable = true
        mTxtIn?.isFocusable = true
        mTitleView?.isFocusable = true

        Picasso.get().load(commit.avatarUrl).into(mImage);

    }

}

interface OnItemClickListener{
    fun onItemClicked(commit: MainFragment.Commit)
}

