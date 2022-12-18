package uz.xsoft.blog.domain.repositories.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.xsoft.blog.data.models.common.*
import uz.xsoft.blog.data.models.response.toEntity
import uz.xsoft.blog.data.source.local.daos.PostDao
import uz.xsoft.blog.data.source.local.entities.toData
import uz.xsoft.blog.data.source.local.entities.toRequest
import uz.xsoft.blog.data.source.remote.api.PostApi
import uz.xsoft.blog.domain.repositories.PostRepository
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val postDao: PostDao,
    private val postApi: PostApi
) : PostRepository {


    override fun loadAllPosts(): Flow<Result<List<PostData>>> = flow<Result<List<PostData>>> {
        val response = postApi.getPosts()

        if (response.isSuccessful) {
            val posts = response.body() ?: listOf()
            postDao.deletePost()
            val postEntities = posts.map { it.toEntity(State.SYNCED) }
            postDao.insertAllNotChanged(postEntities)
            val postData = postEntities.map { it.toData() }
            emit(Result.success(postData))
        } else {
            emit(Result.failure(Exception(Messages.requestError)))
        }
    }
        .catch { emit(Result.failure(Exception(Messages.networkError))) }
        .flowOn(Dispatchers.IO)


    override fun updatePost(postData: PostData): Flow<Result<String>> = flow {
        val postRequest = postData.toRequest()
        val response = postApi.updatePost(postData.id, postRequest)

        when (response.code()) {
            in 200..299 -> {
                val entity = postData.toEntity()
                postDao.insert(entity)
                emit(Result.success(Messages.updateSuccess))
            }

            404 -> {
                val entity = postData.toEntity()
                postDao.insert(entity)
                emit(Result.success(Messages.updatedOnlyLocal))
            }
            else -> emit(Result.failure(Exception(Messages.requestError)))
        }
    }
        .catch { emit(Result.failure(Exception(Messages.networkError))) }
        .flowOn(Dispatchers.IO)


    override fun getPagedPost(): Flow<PagingData<PostData>> = Pager(config = PagingConfig(pageSize = 10, prefetchDistance = 2), pagingSourceFactory = { postDao.getPosts() }).flow

    override fun deletePost(postData: PostData): Flow<Result<String>> = flow<Result<String>> {
        val response = postApi.deletePost(postData.id)
        when (response.code()) {
            in 200..299 -> {
                val entity = postData.toEntity()
                postDao.delete(entity)
                emit(Result.success(Messages.deleteSuccess))
            }

            404 -> {
                val entity = postData.toEntity()
                postDao.delete(entity)
                emit(Result.success(Messages.deletedOnlyLocal))
            }
            else -> emit(Result.failure(Exception(Messages.requestError)))
        }
    }
        .catch { emit(Result.failure(Exception(Messages.networkError))) }
        .flowOn(Dispatchers.IO)


    override fun createPost(postData: PostData): Flow<Result<String>> = flow {
        val response = postApi.addPost(postData.toRequest())

        if (response.isSuccessful) {
            val posts = response.body() ?: return@flow
            val postEntity = posts.toEntity(State.SYNCED)
            postDao.insert(postEntity)
            emit(Result.success(Messages.createdSuccess))
        } else {
            val postEntity = postData.toEntity()
            postDao.insert(postEntity)
            emit(Result.failure(Exception(Messages.requestError)))
        }

    }
        .catch {
            val postEntity = postData.toEntity()
            postDao.insert(postEntity)
            emit(Result.failure(Exception(Messages.networkError)))
        }
        .flowOn(Dispatchers.IO)

    override fun syncLocalPosts(): Flow<Result<String>> = flow {
        val entities = postDao.getPostsByState(State.LOCAL)
        entities.forEach {
            val response = postApi.addPost(it.toRequest())
            postDao.changeState(State.LOADING, it.postId)
            if (response.isSuccessful) {
                val posts = response.body() ?: return@flow
                postDao.changeState(State.SYNCED, it.postId, posts.id)
            } else {
                postDao.changeState(State.LOCAL, it.postId)
            }
            emit(Result.success(Messages.localDataUpdated))
        }
    }
        .catch { emit(Result.failure(Exception(Messages.networkError))) }
        .flowOn(Dispatchers.IO)


    override suspend fun changeFav(isFav: Boolean, postId: Int) {
        postDao.changeFav(isFav, postId)
    }

    override fun getFavPosts(): Flow<PagingData<PostData>> = Pager(config = PagingConfig(pageSize = 10, prefetchDistance = 2), pagingSourceFactory = { postDao.getFavPosts(true) }).flow
}