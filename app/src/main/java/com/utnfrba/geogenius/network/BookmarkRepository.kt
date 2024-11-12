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

    suspend fun getFilteredBookmarksByRating(minRating: Double): Result<List<BookmarkDTO>> {
        return getBookmarks().map { bookmarks ->
            bookmarks.filter { it.rating >= minRating }
        }
    }

    suspend fun getFilteredBookmarksByType(type: String): Result<List<BookmarkDTO>> {
        return getBookmarks().map { bookmarks ->
            bookmarks.filter { it.type.equals(type, ignoreCase = true) }
        }
    }

    suspend fun getFilteredBookmarksByName(keyword: String): Result<List<BookmarkDTO>> {
        return getBookmarks().map { bookmarks ->
            bookmarks.filter { it.name.contains(keyword, ignoreCase = true) }
        }
    }
}
