//package com.example.vn008xw.carbeat.ui.cast
//
//import android.arch.lifecycle.Observer
//import android.arch.lifecycle.ViewModelProvider
//import android.arch.lifecycle.ViewModelProviders
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import android.widget.Toast
//import com.example.vn008xw.carbeat.AppComponent
//import com.example.vn008xw.carbeat.base.BaseView
//import com.example.vn008xw.carbeat.data.vo.Status
//import com.example.vn008xw.carbeat.databinding.FragmentCastBinding
//import com.example.vn008xw.carbeat.utils.AutoClearedValue
//import javax.inject.Inject
//
//class CastFragment : BaseView() {
//
//  @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
//  private lateinit var binding: AutoClearedValue<FragmentCastBinding>
//  private lateinit var castAdapter: AutoClearedValue<CastListAdapter>
//  private lateinit var viewModel: CastViewModel
//
//  override fun inject(appComponent: AppComponent) {
//    appComponent.inject(this)
//  }
//
//  override fun onCreate(savedInstanceState: Bundle?) {
//    super.onCreate(savedInstanceState)
//  }
//
//  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
//                            savedInstanceState: Bundle?) =
//      FragmentCastBinding.inflate(inflater!!, container, false).apply {
//        loading = true
//        binding = AutoClearedValue(this@CastFragment, this)
//        setHasOptionsMenu(true)
//      }.root
//
//  override fun onActivityCreated(savedInstanceState: Bundle?) {
//    super.onActivityCreated(savedInstanceState)
//    viewModel = ViewModelProviders.of(this, viewModelFactory).get(CastViewModel::class.java)
//    val adapter = CastListAdapter()
//    castAdapter = AutoClearedValue(this@CastFragment, adapter)
//    binding.get().recyclerView.adapter = adapter
//
//    viewModel.getCast().observe(this@CastFragment, Observer {
//      when(it?.status) {
//        Status.SUCCESS -> {
//          castAdapter.get().replace(it?.data)
//        }
//        Status.ERROR -> {
//          Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
//        }
//      }
//    })
//  }
//
//  companion object {
//    fun newInstance() = CastFragment()
//  }
//}// Required empty public constructor
