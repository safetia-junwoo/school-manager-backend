package com.base.boilerplate.api.sample.controller;

import com.base.boilerplate.api.sample.dto.SamplePaginationDTO;
import com.base.boilerplate.api.sample.dto.SampleRequestDTO;
import com.base.boilerplate.api.sample.dto.SampleSearchCondition;
import com.base.boilerplate.api.sample.service.SampleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "샘플")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sample")
public class SampleController {

    private final SampleService sampleService;

    @Operation(description = "리스트 조회")
    @GetMapping("")
    public ResponseEntity<?> retrieveList(@ModelAttribute SampleSearchCondition searchCondition, Pageable pageable) throws Exception{
        SamplePaginationDTO dto = sampleService.retrievePage(searchCondition, pageable);
        return ResponseEntity.ok().body(dto);
    }

    @Operation(description = "아이템 저장")
    @PostMapping("")
    public ResponseEntity<?> createItem(@RequestBody SampleRequestDTO requestDto) {
        Integer id = sampleService.upsertItem(requestDto);
        return ResponseEntity.ok().body(id);
    }

    @Operation(description = "아이템 수정")
    @PutMapping("")
    public ResponseEntity<?> updateItem(@RequestBody SampleRequestDTO requestDto) throws Exception{
        Integer id = sampleService.upsertItem(requestDto);
        return ResponseEntity.ok().body(id);
    }
    @Operation(description = "아이템 삭제")
    @PutMapping("/remove")
    public ResponseEntity<?> removeItem(@RequestBody SampleRequestDTO requestDto) throws Exception{
        Integer id = sampleService.removeItem(requestDto);
        return ResponseEntity.ok().body(id);
    }

    @Operation(description = "전체 조회")
    @GetMapping("/all")
    public ResponseEntity<?> retrieveAll(@ModelAttribute SampleSearchCondition searchCondition) throws Exception{
        SamplePaginationDTO dto = sampleService.retrieveAll(searchCondition);
        return ResponseEntity.ok().body(dto);
    }
//
//
//
//    @Operation(description = "상세조회")
//    @GetMapping("/{id}")
//    public ResponseEntity<?> retrieveDetail(@PathVariable ("id") Integer id) throws Exception {
//        return ResponseEntity.ok().body(agreementMasterService.retrieveDetail(id));
//    }

//    @Operation(description = "상세조회 type 으로 찾기 RESERVATION,")
//    @GetMapping("/{type}")
//    public ResponseEntity<?> retrieveDetail(@PathVariable ("type") String type) throws Exception {
//        return ResponseEntity.ok().body(agreementMasterService.retrieveDetail(type));
//    }

}
