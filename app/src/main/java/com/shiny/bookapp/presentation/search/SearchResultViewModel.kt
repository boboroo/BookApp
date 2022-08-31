package com.shiny.bookapp.presentation.main

import androidx.lifecycle.*
import com.shiny.bookapp.MyApplication
import com.shiny.bookapp.R
import com.shiny.bookapp.domain.network.NetworkConnectionException
import com.shiny.bookapp.domain.network.ResultData
import com.shiny.bookapp.domain.network.UnknownException
import com.shiny.bookapp.domain.usecases.GetSearchedBooksUseCase
import com.shiny.bookapp.presentation.base.AbstractViewModel
import com.shiny.bookapp.presentation.mapper.toBookUIModelList
import com.shiny.bookapp.presentation.search.BookUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getSearchedBooksUseCase: GetSearchedBooksUseCase
): AbstractViewModel() {

    var keyword: String = ""
        private set(value) {
            field = getGeneralKeyword(value)
        }

    private val savedLastKeyword: MutableLiveData<String> = savedStateHandle.getLiveData(LAST_API_KEYWORD_KEY)

    val searchResults: LiveData<UiEvent<List<BookUIModel>>> =
        savedLastKeyword.switchMap { keyword ->
            _progressBar.value = true

            liveData(Dispatchers.IO) {
                val resultData = getSearchedBooksUseCase(keyword)

                val uiEvent = when (resultData) {
                    is ResultData.Success -> {
                        UiEvent.DataUpdate(resultData.data.toBookUIModelList())
                    }
                    is ResultData.Fail -> {
                        val message = when (resultData.exception) {
                            is UnknownException -> MyApplication.context.resources.getString(R.string.search_fail)
                            else -> resultData.exception.message
                        }
                        UiEvent.Error(
                            message = message,
                            retry = resultData.exception is NetworkConnectionException
                        )
                    }
                }

                _progressBar.postValue(false)

                emit(uiEvent)
            }
        }

    companion object {
        const val LAST_API_KEYWORD_KEY = "keyword"
    }


    fun setQuery(keyword: String, force: Boolean = false) {
        if (!force && !shouldShowSearchResults(keyword)) {
            return
        }
        setLastKeyword(keyword)
    }


    private fun shouldShowSearchResults(newKeyword: String): Boolean {
        if (newKeyword.isBlank()) {
            return false
        }
        return savedStateHandle.get<String>(LAST_API_KEYWORD_KEY) != getGeneralKeyword(newKeyword)
    }


    private fun setLastKeyword(keyword: String) {
        this.keyword = keyword
        savedStateHandle.set(LAST_API_KEYWORD_KEY, getGeneralKeyword(keyword))
    }


    private fun getGeneralKeyword(keyword: String) = keyword.lowercase()


    override fun loadApi() {
        val keyword: String = savedStateHandle.get<String>(LAST_API_KEYWORD_KEY) ?: return
        setQuery(keyword, !keyword.isBlank())
    }

}
