package com.diogomenezes.jetpackarchitcture.ui.main.blog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.diogomenezes.jetpackarchitcture.R
import com.diogomenezes.jetpackarchitcture.models.BlogPost
import com.diogomenezes.jetpackarchitcture.util.DateUtil
import com.diogomenezes.jetpackarchitcture.util.GenericViewHolder
import kotlinx.android.synthetic.main.layout_blog_list_item.view.*

class BlogListAdapter(
    private val interaction: Interaction? = null,
    private val requestManager: RequestManager
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TAG: String = "AppDebug"
    private val NO_MORE_RESULTS = -1
    private val BLOG_ITEM = 0
    private val NO_MORE_RESULTS_BLOG_MARKER = BlogPost(
        NO_MORE_RESULTS,
        "",
        "",
        "",
        "",
        0,
        ""
    )


    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<BlogPost>() {

        override fun areItemsTheSame(oldItem: BlogPost, newItem: BlogPost): Boolean {
            return oldItem.pk == newItem.pk
        }

        override fun areContentsTheSame(oldItem: BlogPost, newItem: BlogPost): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(
        BlogRecyclerChangeCallback(this),
        AsyncDifferConfig.Builder(DIFF_CALLBACK).build()
    )

    internal inner class BlogRecyclerChangeCallback(
        private val adapter: BlogListAdapter
    ) : ListUpdateCallback {
        override fun onChanged(position: Int, count: Int, payload: Any?) {
            adapter.notifyItemRangeChanged(position, count, payload)
        }

        override fun onMoved(fromPosition: Int, toPosition: Int) {
            adapter.notifyDataSetChanged()
        }

        override fun onInserted(position: Int, count: Int) {
            adapter.notifyItemRangeChanged(position, count)
        }

        override fun onRemoved(position: Int, count: Int) {
            adapter.notifyDataSetChanged()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        when (viewType) {

            NO_MORE_RESULTS -> return GenericViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(
                        R.layout.layout_no_more_results,
                        parent,
                        false
                    )
            )

            BLOG_ITEM -> return BlogViewHolder.from(parent, requestManager, interaction)

            else -> return BlogViewHolder.from(parent, requestManager, interaction)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BlogViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun getItemViewType(position: Int): Int {
        if (differ.currentList.get(position).pk > -1) {
            return BLOG_ITEM
        }
        return differ.currentList.get(position).pk //-1
    }

    fun submitList(list: List<BlogPost>?, isQueryExhausted: Boolean) {
        val newList = list?.toMutableList()
        if (isQueryExhausted) {
            newList?.add(NO_MORE_RESULTS_BLOG_MARKER)
        }
        differ.submitList(list)
    }

    fun preloadGlideImages(
        requestManager: RequestManager,
        list: List<BlogPost>
    ) {
        for (blogPost in list) {
            requestManager
                .load(blogPost.image)
                .preload()
        }
    }

    class BlogViewHolder
    constructor(
        private val requestManager: RequestManager,
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: BlogPost) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
            requestManager
                .load(item.image)
                .transition(withCrossFade())
                .into(itemView.create_blog_image)
            itemView.create_blog_title.text = item.title
            itemView.blog_author.text = item.username
            itemView.blog_update_date.text = DateUtil.convertLongToStringDate(item.date_updated)

        }

        companion object {
            fun from(
                parent: ViewGroup,
                requestManager: RequestManager,
                interaction: Interaction?
            ): BlogViewHolder {
                return BlogViewHolder(
                    requestManager,
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.layout_blog_list_item,
                        parent,
                        false
                    ),
                    interaction = interaction
                )
            }
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: BlogPost)
    }


}

