//package com.example.pokedex.main.paging
//
//import androidx.paging.PagingSource
//import androidx.paging.PagingState
//import com.example.pokedex.main.api.PokemonRepository
//import com.example.pokedex.main.api.PokemonService
//import com.example.pokedex.main.dto.PokemonResponseDTO
//import retrofit2.HttpException
//import java.lang.Exception
//
//class PokemonsPagingSource(
//    private val repository: PokemonRepository) :
//    PagingSource<Int, PokemonResponseDTO>() {
//
//    override fun getRefreshKey(state: PagingState<Int, PokemonResponseDTO>): Int? {
//        return null
//    }
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonResponseDTO> {
//       return try {
//           val currentPage = params.key ?: 1
//           val response = repository.getSinglePokemon(currentPage)
//           val data = response.body()
//           val responseData = mutableListOf<PokemonResponseDTO>()
//           responseData.addAll(data)
//
//           LoadResult.Page(
//               data = responseData,
//               prevKey = if(currentPage == 1) null else -1,
//               nextKey = currentPage.plus(1)
//           )
//       } catch (e: Exception) {
//           LoadResult.Error(e)
//       } catch (httpE : HttpException) {
//           LoadResult.Error(httpE)
//       }
//    }
//}