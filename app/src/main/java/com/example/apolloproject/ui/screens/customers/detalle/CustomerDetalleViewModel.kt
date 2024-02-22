package com.example.apolloproject.ui.screens.customers.detalle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apolloproject.R
import com.example.apolloproject.domain.model.CustomerGraphDetail
import com.example.apolloproject.domain.usecases.customers.GetCustomerUseCase
import com.example.apolloproject.domain.usecases.customers.UpdateCustamerUseCase
import com.example.apolloproject.domain.usecases.restauranttables.GetAllTablesByCustUseCase
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
class CustomerDetalleViewModel @Inject constructor(
    private val stringProvider: StringProvider,
    private val getCustomerUseCase: GetCustomerUseCase,
   private val getAllTablesByCustUseCase: GetAllTablesByCustUseCase,
    private val updateCustamerUseCase: UpdateCustamerUseCase



) :ViewModel(){
    private val _uiState: MutableStateFlow<CustomerDetalleContract.State> =
        MutableStateFlow(CustomerDetalleContract.State())

    val uiState: StateFlow<CustomerDetalleContract.State> = _uiState.asStateFlow()


    fun event(event:CustomerDetalleContract.Event){
        when(event){
            is CustomerDetalleContract.Event.GetCustomer -> getCustomer(event.customerId)
            is CustomerDetalleContract.Event.GetTables ->getTables(event.customerId)
            is CustomerDetalleContract.Event.UpdateCustomer -> updateCustomer(event.customer)
            is CustomerDetalleContract.Event.CambiarEmail -> cambiarEmail(event.email)
            is CustomerDetalleContract.Event.CambiarFirstName ->cambiarFirst(event.first)
            is CustomerDetalleContract.Event.CambiarLastName -> cambiarLast(event.last)
            is CustomerDetalleContract.Event.CambiarPhone -> cambiarPhone(event.phone)
        }
    }


    fun cambiarFirst(name:String){
        _uiState.update {
            it.copy(customer = it.customer?.copy(firstName = name))
        }
    }

    fun cambiarLast(name:String){
        _uiState.update {
            it.copy(customer = it.customer?.copy(lastName = name))
        }
    }

    fun cambiarEmail(email:String){
        _uiState.update {
            it.copy(customer = it.customer?.copy(email = email))
        }
    }
    fun cambiarPhone(phone:String){
        _uiState.update {
            it.copy(customer = it.customer?.copy(phone = phone))
        }
    }
    fun updateCustomer(customerGraphDetail: CustomerGraphDetail?){
        viewModelScope.launch {
            customerGraphDetail?.let {
                updateCustamerUseCase.invoke(it)
                    .collect { result ->
                        when(result){
                            is NetworkResult.Error -> {
                                _uiState.update {
                                    it.copy(
                                        message = result.message,

                                        )
                                }
                            }
                            is NetworkResult.Loading -> _uiState.update { it.copy(isLoading = true) }
                            is NetworkResult.Success -> _uiState.update {
                                it.copy( message = stringProvider.getString(R.string.custAct),
                                    customer= result.data , isLoading = false
                                )
                            }
                        }


                    }
            }


        }

    }


    fun getTables(idCust:Int){
        viewModelScope.launch {

              getAllTablesByCustUseCase.invoke(idCust)
                  .collect{result->
                      when(result){
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
                                  tables = result.data
                                      ?: emptyList(), isLoading = false
                              )
                          }
                      }

                  }

        }
    }
    fun getCustomer(idCust:Int){
        viewModelScope.launch {

               getCustomerUseCase.invoke(idCust)
                   .collect{result->
                       when(result){
                           is NetworkResult.Error ->  {
                           _uiState.update {
                               it.copy(
                                   message = result.message,

                                   )
                           }
                       }
                           is NetworkResult.Loading -> _uiState.update { it.copy(isLoading = true) }
                           is NetworkResult.Success -> _uiState.update {
                               it.copy(
                                   customer = result.data
                                      , isLoading = false
                               )
                           }
                       }
                   }

        }
    }
}