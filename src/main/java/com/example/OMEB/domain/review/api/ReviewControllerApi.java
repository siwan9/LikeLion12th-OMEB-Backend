package com.example.OMEB.domain.review.api;

import static com.example.OMEB.global.base.dto.SuccessResponseBody.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.OMEB.domain.review.presentation.dto.request.ReviewCreateRequest;
import com.example.OMEB.domain.review.presentation.dto.request.ReviewUpdateRequest;
import com.example.OMEB.domain.review.presentation.dto.response.ReviewInfoResponse;
import com.example.OMEB.domain.review.presentation.dto.response.ReviewPageResponse;
import com.example.OMEB.global.aop.AssignUserId;
import com.example.OMEB.global.base.dto.ResponseBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Review API", description = "리뷰 관련 API")
public interface ReviewControllerApi {

	@Operation(summary = "리뷰 생성", description = "책에 리뷰를 생성하는 API.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
			content = {@Content(schema = @Schema(implementation = ReviewInfoResponse.class),mediaType = "application/json")}),
		@ApiResponse(responseCode = "USER_0001", description = "사용자를 찾을 수 없습니다.", content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "BOOK_0001", description = "책을 찾을 수 없습니다.", content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "TAG_0001", description = "태그를 찾을 수 없습니다.", content = @Content(mediaType = "application/json"))
	})
	ResponseEntity<ResponseBody<ReviewInfoResponse>> createReview(@Schema(hidden = true) Long userId,
		@PathVariable @Schema(description = "책 id", example = "1") Long bookId,
		@RequestBody ReviewCreateRequest reviewCreateRequest);


	@Operation(summary = "리뷰 수정", description = "책에 리뷰를 수정하는 API.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
			content = {@Content(schema = @Schema(implementation = ReviewInfoResponse.class),mediaType = "application/json")}),
		@ApiResponse(responseCode = "USER_0001", description = "사용자를 찾을 수 없습니다.", content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "REVIEW_0001", description = "리뷰를 찾을 수 없습니다.", content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "REVIEW_0002", description = "해당 사용자는 해당 리뷰에 대한 권한이 없습니다.", content = @Content(mediaType = "application/json"))
	})
	ResponseEntity<ResponseBody<ReviewInfoResponse>> updateReview(@Schema(hidden = true) Long userId,
		@PathVariable @Schema(description = "리뷰 id", example = "1")Long reviewId,
		@RequestBody ReviewUpdateRequest reviewUpdateRequest) ;


	@Operation(summary = "리뷰 페이징 조회", description = "특정 책에 대한 리뷰를 페이징 조회하는 API.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
			content = {@Content(schema = @Schema(implementation = ReviewPageResponse.class),mediaType = "application/json")}),
		@ApiResponse(responseCode = "USER_0001", description = "사용자를 찾을 수 없습니다.", content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "REVIEW_0001", description = "리뷰를 찾을 수 없습니다.", content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "REVIEW_0002", description = "해당 사용자는 해당 리뷰에 대한 권한이 없습니다.", content = @Content(mediaType = "application/json"))
	})
	ResponseEntity<ResponseBody<ReviewPageResponse>> getReviews(@PathVariable @Schema(description = "책 id", example = "1") Long bookId,
		@RequestParam(defaultValue = "1") @Schema(description = "조회할 페이지 넘버(가장 작은 수 1)", example = "1") int page,
		@RequestParam(defaultValue = "10") @Schema(description = "한 페이지의 조회 될 책 수",example = "10") int size,
		@RequestParam(defaultValue = "DESC") @Schema(description = "정렬 방법" , example = "DESC") String sortDirection,
		@RequestParam(defaultValue = "createdAt") @Schema(description = "정렬 기준",example = "createdAt") String sortBy);


	@Operation(summary = "리뷰 좋아요 증가", description = "리뷰에 좋아요를 증가하는 API.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
			content = {@Content(schema = @Schema(implementation = Void.class),mediaType = "application/json")}),
		@ApiResponse(responseCode = "USER_0001", description = "사용자를 찾을 수 없습니다.", content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "REVIEW_0001", description = "리뷰를 찾을 수 없습니다.", content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "REVIEW_0003", description = "이미 좋아요를 누른 리뷰입니다.", content = @Content(mediaType = "application/json"))
	})
	ResponseEntity<ResponseBody<Void>> likeReview(@Schema(hidden = true) Long userId, @PathVariable @Schema(description = "리뷰 id", example = "1")Long reviewId);


	@Operation(summary = "리뷰 삭제", description = "리뷰를 삭제하는 API.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
			content = {@Content(schema = @Schema(implementation = Void.class),mediaType = "application/json")}),
		@ApiResponse(responseCode = "USER_0001", description = "사용자를 찾을 수 없습니다.", content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "REVIEW_0001", description = "리뷰를 찾을 수 없습니다.", content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "REVIEW_0002", description = "해당 사용자는 해당 리뷰에 대한 권한이 없습니다.", content = @Content(mediaType = "application/json"))
	})
	ResponseEntity<ResponseBody<Void>> deleteReview(@Schema(hidden = true) Long userId, @PathVariable @Schema(description = "리뷰 id", example = "1")Long reviewId);
}
