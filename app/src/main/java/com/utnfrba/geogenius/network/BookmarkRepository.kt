import com.utnfrba.geogenius.model.BookmarkDTO
import com.utnfrba.geogenius.network.BookmarkApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object BookmarkRepository {

    private var cachedBookmarks: List<BookmarkDTO>? = null
    private var lastFetchTime: Long = 0
    private var cacheExpiryTime = 6 * 1000 * 10

    suspend fun getBookmarks(): Result<List<BookmarkDTO>> {
        val currentTime = System.currentTimeMillis()

        if (cachedBookmarks == null || currentTime - lastFetchTime > cacheExpiryTime) {
            return try {
                lastFetchTime = currentTime
                cacheExpiryTime = 6 * 1000 * 1000
                val bookmarksFromApi = withContext(Dispatchers.IO) {
                    BookmarkApi.retrofitService.getBookmarks()
                }
                cachedBookmarks = bookmarksFromApi
                Result.success(bookmarksFromApi)
            } catch (e: Exception) {
                cacheExpiryTime = 5 * 1000
                Result.failure(e)
            }
        }

        return Result.success(cachedBookmarks ?: emptyList())
    }

    suspend fun getBookmarksByRating(minRating: Double): Result<List<BookmarkDTO>> {
        return getBookmarks().map { bookmarks ->
            bookmarks.filter { it.rating >= minRating }
        }
    }

    suspend fun getBookmarksByType(type: String): Result<List<BookmarkDTO>> {
        return getBookmarks().map { bookmarks ->
            bookmarks.filter { it.type.equals(type, ignoreCase = true) }
        }
    }

    suspend fun getBookmarksById(id: String): Result<BookmarkDTO> {
        return getBookmarks().map { bookmarks ->
            bookmarks.filter { it.id.equals(id, ignoreCase = true) }[0]
        }
    }

    fun getCachedBookmarks(): List<BookmarkDTO>? {
        return this.cachedBookmarks
    }
}
