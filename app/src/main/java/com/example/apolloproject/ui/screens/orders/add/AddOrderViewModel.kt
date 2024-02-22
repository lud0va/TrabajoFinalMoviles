package com.example.apolloproject.ui.screens.orders.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apolloproject.R
import com.example.apolloproject.domain.usecases.customers.GetCustomersUseCase
import com.example.apolloproject.domain.usecases.orders.AddOrderUseCase
import com.example.apolloproject.ui.screens.orders.detalle.OrdersDetalleContract
import com.example.apolloproject.utils.NetworkResult
import com.example.apolloproject.utils.StringProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddOrderViewModel @Inject constructor(
    private  val stringProvider: StringProvider,
    private val  addOrderUseCase: AddOrderUseCase,
    private val getCustomersUseCase: GetCustomersUseCase

):ViewModel() {
    private val _uiState= MutableStateFlow(AddOrderContract.State())
    val uiState: StateFlow<AddOrderContract.State> = _uiState.asStateFlow()

    fun event(event:AddOrderContract.Event){
        when(event){
            AddOrderContract.Event.Addorder -> addOrder()
            is AddOrderContract.Event.CambiarCustomerId -> cambiarCustomerId(event.id)
            is AddOrderContract.Event.GetCustomers -> getCustomers()
        }
    }


    fun cambiarCustomerId(id:String) {
        try {
            _uiState.update {
                it.copy(
                    orderGraph = it.orderGraph?.copy(customerId = Integer.parseInt(id))
                )
            }
        }catch (s: NumberFormatException ){
            _uiState.update {
                it.copy(
                    message = stringProvider.getString(R.string.soloNumeros)
                    )
            }

        }

    }

    fun getCustomers(){
        viewModelScope.launch {

            getCustomersUseCase.invoke()
                .collect { result ->
                    when (result) {
                        is NetworkResult.Error -> {
                            _uiState.update {
                                it.copy(
                                    message = result.message,

                                    )
                            }
                        }

                        is NetworkResult.Loading -> _uiState.update { it.copy(isLoading = true) }
                        is NetworkResult.Success -> _uiState.update {
                            it.copy(
                                customers = result.data
                                    ?: emptyList(), isLoading = false
                            )
                        }

                    }


                }

        }
    }
    fun addOrder(){
        viewModelScope.launch{
            uiState.value.orderGraph?.let {
                addOrderUseCase.invoke(it)
                    .collect { result ->
                        when (result) {
                            is NetworkResult.Error -> {
                                _uiState.update {
                                    it.copy(
                                        message = result.message,

                                        )
                                }
                            }

                            is NetworkResult.Loading -> _uiState.update { it.copy(isLoading = true) }
                            is NetworkResult.Success -> _uiState.update {
                                it.copy(
                                  message = stringProvider.getString(R.string.OrderAdded), isLoading = false
                                )
                            }

                        }


                    }









            }



        }
    }

}