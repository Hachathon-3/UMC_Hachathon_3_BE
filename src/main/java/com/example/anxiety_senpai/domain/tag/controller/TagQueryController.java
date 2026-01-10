package com.example.anxiety_senpai.domain.tag.controller;

import com.example.anxiety_senpai.domain.tag.dto.TagListResponse;
import com.example.anxiety_senpai.domain.tag.service.TagQueryService;
import com.example.anxiety_senpai.global.apiPayload.handler.ApiResponse;
import com.example.anxiety_senpai.global.apiPayload.code.GeneralSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tags")
public class TagQueryController {

    private final TagQueryService tagQueryService;

    @GetMapping
    public ApiResponse<TagListResponse> getTags(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "sort", defaultValue = "name_asc") String sort
    ) {
        TagListResponse response = tagQueryService.getTags(keyword, page, size, sort);
        return ApiResponse.onSuccess(GeneralSuccessCode.FOUND, response);
    }
}

