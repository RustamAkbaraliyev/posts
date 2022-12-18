package uz.xsoft.blog.data.source.local.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import uz.xsoft.blog.data.models.common.PostData
import uz.xsoft.blog.data.models.common.State
import uz.xsoft.blog.data.source.local.entities.PostEntity

@Dao
interface PostDao : BaseDao<PostEntity> {

    @Query("SELECT * FROM PostEntity ORDER BY postId")
    fun getPosts(): PagingSource<Int, PostData>


    @Query("SELECT * FROM PostEntity WHERE fav = :isFav ORDER BY postId")
    fun getFavPosts(isFav: Boolean): PagingSource<Int, PostData>

    @Query("DELETE FROM PostEntity WHERE id < 101 AND  fav = :isFav")
    fun deletePost(isFav: Boolean = false)

    @Query("SELECT * FROM PostEntity WHERE state =:state")
    fun getPostsByState(state: State): List<PostEntity>

    @Query("UPDATE PostEntity SET state = :state, id = :id  WHERE postId = :postId ")
    fun changeState(state: State, postId: Int, id: Int = 0)

    @Query("UPDATE PostEntity SET fav= :isFav WHERE postId= :postId")
    fun changeFav(isFav: Boolean, postId: Int)
}

