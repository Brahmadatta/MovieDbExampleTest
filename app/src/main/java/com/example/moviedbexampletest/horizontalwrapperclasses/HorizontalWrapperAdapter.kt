package com.example.moviedbexampletest.horizontalwrapperclasses

//class HorizontalWrapperAdapter(
//    private val adapter: MoviesPopularAdapter
//) : RecyclerView.Adapter<HorizontalWrapperViewHolder>() {
//    private var lastScrollX = 0
//
//    companion object {
//        private const val KEY_SCROLL_X = "horizontal.wrapper.adapter.key_scroll_x"
//        const val VIEW_TYPE = 3333
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalWrapperViewHolder {
//        return HorizontalWrapperViewHolder(
//            ActivityMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        )
//    }
//
//    override fun getItemViewType(position: Int): Int {
//        return VIEW_TYPE
//    }
//
//    override fun onBindViewHolder(holder: HorizontalWrapperViewHolder, position: Int) {
//        holder.bind(adapter, lastScrollX) { x ->
//            lastScrollX = x
//        }
//    }
//
//    override fun getItemCount(): Int = 1
//
//    fun onSaveState(outState: Bundle) {
//        outState.putInt(KEY_SCROLL_X, lastScrollX)
//    }
//
//    fun onRestoreState(savedState: Bundle) {
//        lastScrollX = savedState.getInt(KEY_SCROLL_X)
//    }
//}
