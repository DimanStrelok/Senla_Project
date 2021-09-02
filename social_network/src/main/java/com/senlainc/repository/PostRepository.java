package com.senlainc.repository;

import com.senlainc.entity.Post;
import com.senlainc.entity.PostComment;

import java.util.List;

public interface PostRepository extends CrudRepository<Post> {
    List<PostComment> getComments(Post post);
}
