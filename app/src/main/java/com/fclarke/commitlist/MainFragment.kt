package com.fclarke.commitlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.fclarke.commitlist.pojo.ExampleModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainFragment : Fragment(), OnItemClickListener, BottomSheetEx.BottomSheetListener  {
    data class Commit(
        var message: String,
        var dateIn: String,
        val loginOut: String,
        var sha: String,
        var htmlUrl: String,
        var avatarUrl: String,
    )

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    override fun onItemClicked(user: Commit) { //this is the important onClick
        val bundle = Bundle()
        bundle.putString("avatarUrl", user.avatarUrl)
        bundle.putString("dateIn", user.dateIn)
        bundle.putString("loginOut", user.loginOut)
        bundle.putString("htmlUrl", user.htmlUrl)
        bundle.putString("sha", user.sha)
        bundle.putString("message", user.message)

        val bottomSheet = BottomSheetEx()
        bottomSheet.setArguments(bundle)
        this.fragmentManager?.let { bottomSheet.show(it, "BottomSheetEx") }
        val state =
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED)
                BottomSheetBehavior.STATE_COLLAPSED
            else
                BottomSheetBehavior.STATE_EXPANDED
        bottomSheetBehavior.state = state
    }

    var mCommitters = mutableListOf<Commit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_main, container, false)
        parseJson()
        return view
    }

    val myAdapter = RecyclerAdapter(mCommitters, this)

    /*
        The View has a LayoutManager
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = myAdapter
        }

        myAdapter.notifyDataSetChanged()

        bottomSheetBehavior = BottomSheetBehavior()
        bottomSheetBehavior.isHideable = true
        bottomSheetBehavior.peekHeight = 16

    }


    companion object {
        fun newInstance(): MainFragment = MainFragment()
    }

    var gson: Gson? = GsonBuilder()
        .setLenient()
        .create()

    private fun parseJson() {
        val BaseUrl = "https://api.github.com/repos/definitelytyped/definitelytyped/"
        val retrofit = Retrofit.Builder()
            .baseUrl(BaseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        val service = retrofit.create(CommitService::class.java)
        val call1: Call<List<ExampleModel>> = service.getExampleJSON()

        call1.enqueue(object : Callback<List<ExampleModel>?> {
            override fun onResponse(
                call: Call<List<ExampleModel>?>,
                response: Response<List<ExampleModel>?>
            ) {
                mCommitters.clear()

                if (response.isSuccessful && response.body() != null) {
                    commitModels = ArrayList(response.body())

                    for (item: ExampleModel in commitModels) {
                        mCommitters.add(
                            Commit(
                                item.commit.message,
                                item.commit.committer.date,
                                item.author.login,
                                item.sha,
                                item.htmlUrl,
                                item.author.avatarUrl
                            )
                        )
                    }

                    myAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<List<ExampleModel>?>, t: Throwable) {
                //mProgressBar.setVisibility(View.GONE)
                Toast.makeText(context, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    override fun onOptionClick(text: String) {
       // TODO("Not yet implemented")
    }

}