package com.shoptbdddatn.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shoptbdddatn.model.Comment;
import com.shoptbdddatn.model.Reply;
import com.shoptbdddatn.repository.ICommentRepository;
import com.shoptbdddatn.repository.IReplyRepository;

@CrossOrigin
@RestController
public class ReplyController {
    @Autowired
	IReplyRepository replyRepository;
	
	@Autowired
	ICommentRepository commentRepository;
	
	@PostMapping("/reply/{commentId}")
	// @PreAuthorize("hasAnyRole('ADMIN', 'MANGER','EMPLOYEE')")
	public ResponseEntity<?> createReplyByCommentId(@PathVariable("commentId") Integer commentId, @RequestBody Reply reply){
		try {
			Optional<Comment> commentData = commentRepository.findById(commentId);
			if(commentData.isPresent()) {
				Reply newReply = new Reply();
				newReply.setReplyDetail(reply.getReplyDetail());
				newReply.setReplyName(reply.getReplyName());
				newReply.setCreatedAt(reply.getCreatedAt());
				newReply.setComment(commentData.get());
				
				Reply createReply = replyRepository.save(newReply);
				return new ResponseEntity<>(createReply, HttpStatus.OK);
			}
			else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
		}catch(Exception e) {
			return new ResponseEntity<>(e.getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/reply")
	public ResponseEntity<?> getReplyByCommentId(@RequestParam("commentId") Integer commentId){
		try {
			return new ResponseEntity<>(replyRepository.getReplyByCommentId(commentId), HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/reply/{replyId}")
	@PreAuthorize("hasAnyRole('ADMIN', 'MANGER','EMPLOYEE')")
	public ResponseEntity<?> deleteReplyId(@PathVariable("replyId") Integer replyId){
		try {
			replyRepository.deleteById(replyId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}catch(Exception e) {
			return new ResponseEntity<>(e.getCause().getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
}
