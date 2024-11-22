import com.utnfrba.geogenius.model.BookmarkDTO
import com.utnfrba.geogenius.model.Coordinate
import com.utnfrba.geogenius.network.BookmarkApi
import com.utnfrba.geogenius.screens.filters.FiltersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object BookmarkRepository {

    private var cachedBookmarks: List<BookmarkDTO>? = null
    private var lastFetchTime: Long = 0
    private var cacheExpiryTime = 6 * 1000 * 10

    fun getBookmarks(): Result<List<BookmarkDTO>> {
//        val currentTime = System.currentTimeMillis()

//        if (cachedBookmarks == null || currentTime - lastFetchTime > cacheExpiryTime) {
//            return try {
//                lastFetchTime = currentTime
//                cacheExpiryTime = 6 * 1000 * 1000
//                val bookmarksFromApi = withContext(Dispatchers.IO) {
//                    BookmarkApi.retrofitService.getBookmarks()
//                }
//                cachedBookmarks = bookmarksFromApi
//                Result.success(bookmarksFromApi)
//            } catch (e: Exception) {
//                cacheExpiryTime = 5 * 1000
//                Result.failure(e)
//            }
//        }
        sampleList.forEach {
            bookmark -> FiltersRepository.addFilterById(bookmark.type)
        }

        return Result.success(sampleList)
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


val samplePlace = BookmarkDTO(
    id = "1",
    name = "Casa Simpsons",
    address = "Avenida Siempreviva 123",
    rating = 4.5,
    description = "Una casa icónica de la serie animada Los Simpsons.",
    longDescription = "Una casa icónica de la serie animada Los Simpsons.",
    images = listOf(
        "https://via.placeholder.com/400x200/FF5733/FFFFFF?text=Imagen+1",
        "https://via.placeholder.com/400x200/33FF57/FFFFFF?text=Imagen+2",
        "https://via.placeholder.com/400x200/3357FF/FFFFFF?text=Imagen+3"
    ),
    coordinates = Coordinate(longitude = 10.0, latitude = 10.0),
    type = "Casa",
)
val samplePlace2 = BookmarkDTO(
    id = "2",
    name = "Casa Lol",
    address = "Avenida Sas 123",
    rating = 4.5,
    description = "Una casa para nada icónica",
    longDescription = "Una casa icónica de la serie animada Los Simpsons.",
    images = listOf(
        "https://via.placeholder.com/400x200/FF5733/FFFFFF?text=Imagen+1",
        "https://via.placeholder.com/400x200/33FF57/FFFFFF?text=Imagen+2",
        "https://via.placeholder.com/400x200/3357FF/FFFFFF?text=Imagen+3"
    ),
    coordinates = Coordinate(longitude = 20.0, latitude = 20.0),
    type = "cafe",
)
val samplePlace3 = BookmarkDTO(
    id = "2",
    name = "Casa Lol",
    address = "Avenida Sas 123",
    rating = 4.5,
    description = "Una casa para nada icónica",
    longDescription = "Una casa icónica de la serie animada Los Simpsons.",
    images = listOf(
        "https://via.placeholder.com/400x200/FF5733/FFFFFF?text=Imagen+1",
        "https://via.placeholder.com/400x200/33FF57/FFFFFF?text=Imagen+2",
        "https://via.placeholder.com/400x200/3357FF/FFFFFF?text=Imagen+3"
    ),
    coordinates = Coordinate(longitude = 30.0, latitude = 30.0),
    type = "museo",
)
val sampleList = listOf(samplePlace, samplePlace2, samplePlace3)