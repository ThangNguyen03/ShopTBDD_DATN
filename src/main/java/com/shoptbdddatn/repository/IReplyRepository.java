package com.shoptbdddatn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shoptbdddatn.model.Reply;

public interface IReplyRepository extends JpaRepository<Reply,Integer> {
    @Query(value = "SELECT*FROM replies WHERE comment_id LIKE :commentId",nativeQuery =true)
    List<Reply> getReplyByCommentId(@Param("commentId") Integer commentId);
}
