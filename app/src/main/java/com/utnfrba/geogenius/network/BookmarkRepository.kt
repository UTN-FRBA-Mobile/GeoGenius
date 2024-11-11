import com.utnfrba.geogenius.model.BookmarkDTO
import com.utnfrba.geogenius.network.BookmarkApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BookmarkRepository {

    private var cachedBookmarks: List<BookmarkDTO>? = null
    private var lastFetchTime: Long = 0
    private val cacheExpiryTime = 60 * 1000

    suspend fun getBookmarks(): Result<List<BookmarkDTO>> {
        val currentTime = System.currentTimeMillis()

        if (cachedBookmarks == null || currentTime - lastFetchTime > cacheExpiryTime) {
            return try {
                val bookmarksFromApi = withContext(Dispatchers.IO) {
                    BookmarkApi.retrofitService.getBookmarks()
                }

                cachedBookmarks = bookmarksFromApi
                lastFetchTime = currentTime
                Result.success(bookmarksFromApi)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

        return Result.success(cachedBookmarks ?: emptyList())
    }

    fun filterByRating(minRating: Double): List<BookmarkDTO> {
        return cachedBookmarks?.filter { it.rating >= minRating } ?: emptyList()
    }

    fun filterByType(type: String): List<BookmarkDTO> {
        return cachedBookmarks?.filter { it.type.equals(type, ignoreCase = true) } ?: emptyList()
    }

    fun filterByName(keyword: String): List<BookmarkDTO> {
        return cachedBookmarks?.filter { it.name.contains(keyword, ignoreCase = true) } ?: emptyList()
    }
}
