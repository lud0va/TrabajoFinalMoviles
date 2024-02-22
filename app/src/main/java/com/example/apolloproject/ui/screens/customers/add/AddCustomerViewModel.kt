package com.example.apolloproject.ui.screens.customers.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apolloproject.R
import com.example.apolloproject.domain.model.CustomerGraphDetail
import com.example.apolloproject.domain.usecases.customers.AddCustomerUseCase
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
class AddCustomerViewModel @Inject constructor(
    private  val stringProvider: StringProvider,
    private   val addCustomerUseCase: AddCustomerUseCase) :
    ViewModel() {
    private val _uiState: MutableStateFlow<AddCustomerContract.State> =
        MutableStateFlow(AddCustomerContract.State())

    val uiState: StateFlow<AddCustomerContract.State> = _uiState.asStateFlow()


    fun event(event: AddCustomerContract.Event) {
        when (event) {
            is AddCustomerContract.Event.AddCustomer -> addCustomer(event.customer)
            is AddCustomerContract.Event.CambiarEmail -> cambiarEmail(event.email)
            is AddCustomerContract.Event.CambiarFirstName -> cambiarFirst(event.first)
            is AddCustomerContract.Event.CambiarLastName -> cambiarLast(event.last)
            is AddCustomerContract.Event.CambiarPhone -> cambiarPhone(event.phone)
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
    fun addCustomer(customerGraphDetail: CustomerGraphDetail?) {
        viewModelScope.launch {
            customerGraphDetail?.let {
                addCustomerUseCase.invoke(it)
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
                                    message = stringProvider.getString(R.string.addCust),
                                    customer= result.data , isLoading = false
                                )
                            }
                        }






                    }

            }



        }
    }
}