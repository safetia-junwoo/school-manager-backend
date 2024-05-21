package com.base.boilerplate.file.controller;

import com.base.boilerplate.auth.exception.CustomFileException;
import com.base.boilerplate.file.dto.FileDownloadDTO;
import com.base.boilerplate.file.dto.FileResponseDTO;
import com.base.boilerplate.file.dto.RelationshipFileRequestDTO;
import com.base.boilerplate.file.service.ExcelReader;
import com.base.boilerplate.file.service.FileService;
import com.base.boilerplate.util.FileUtils;
import com.base.boilerplate.util.StringUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Tag(name = "파일 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class FileController {

    private final FileService fileService;
    private final StringUtils stringUtils;
    private final FileUtils fileUtils;
//    @Operation(description = "섬네일 이미지 조회")
//    @Parameter(name = "fileId", description = "파일 번호", example = "1")
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", description = "섬네일 이미지 조회 성공"),
//            @ApiResponse(responseCode = "400", description = "요청 리소스 오류", content = @Content(schema = @Schema(hidden = true)))
//    })
//    @GetMapping(value = "/api/files/thumbnail/{fileId}", produces = MediaType.IMAGE_JPEG_VALUE)
//    public byte[] getThumbnailImage(@PathVariable(value = "fileId",required = false) Integer fileId) throws Exception {
//        byte[] thumbnailImage = fileService.getThumbnailImage(fileId);
//        return thumbnailImage;
//    }
//    @Operation(description = "원본 이미지 조회")
//    @Parameter(name = "fileId", description = "파일 번호", example = "1")
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", description = "원본 이미지 조회 성공"),
//            @ApiResponse(responseCode = "400", description = "요청 리소스 오류", content = @Content(schema = @Schema(hidden = true)))
//    })
//    @GetMapping(value = "/api/files/images/{fileId}", produces = MediaType.IMAGE_JPEG_VALUE)
//    public byte[] getOriginImage(@PathVariable(value = "fileId",required = false) Integer fileId) throws Exception {
//        byte[] originImage = fileService.getOriginImage(fileId);
//        return originImage;
//    }

    @PostMapping("/private/files")
    public ResponseEntity<?> createFiles(@ModelAttribute RelationshipFileRequestDTO relationshipFileRequestDTO, @RequestParam(value = "files") MultipartFile[] files) throws Exception {
        HashMap<String, Object> fileInfo = fileService.createFiles(relationshipFileRequestDTO, fileUtils.getFileList(files));
        return ResponseEntity.ok().body(fileInfo);
    }


//    @PostMapping("/private/files/decrypt")
//    public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file) throws Exception {
//        MultipartFile decryptedFile = fileService.decryptFile(file);
//        // 파일 처리 로직
//        ExcelReader excelReader = new ExcelReader();
//        List<List<String>> data = excelReader.readExcelFile(decryptedFile);
//
//        return ResponseEntity.ok(data);
//    }
//    public ResponseEntity<?> decryptFiles(@RequestParam(value = "files") MultipartFile[] files) throws Exception {
//        FileDownloadDTO fileDownloadDTO  = fileService.decryptFiles(fileUtils.getFileList(files));
//        return ResponseEntity.ok().body(fileDownloadDTO);
//    }

    @GetMapping("/public/files")
    public ResponseEntity<?> retrieveFiles(@ModelAttribute RelationshipFileRequestDTO relationshipFileRequestDTO)throws Exception{
        FileResponseDTO fileRequestDTO = fileService.retrieveFiles(relationshipFileRequestDTO);
        return ResponseEntity.ok().body(fileRequestDTO);
    }

    @PutMapping("/private/files/relationship")
    public ResponseEntity<?> updateRelationship(@RequestBody RelationshipFileRequestDTO relationshipFileRequestDTO) throws CustomFileException {
        fileService.updateRelationshipFile(relationshipFileRequestDTO.getTaskId(), relationshipFileRequestDTO.getFileGroupCode());
        return ResponseEntity.ok().body(null);
    }

    @Operation(description = "파일다운로드")
    @Parameter(name = "fileId", description = "파일 번호", example = "1")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "파일 다운로드 성공",  headers = {@Header(name = "content-disposition", schema = @Schema(type = "attachment"), description = "파일 즉시 다운로드")}),
            @ApiResponse(responseCode = "400", description = "요청 리소스 오류", content = @Content(schema = @Schema(hidden = true)))
    })
    @GetMapping("/public/files/{id}")
    public ResponseEntity<?> fileDownload(@PathVariable("id") Integer id) throws Exception {
        FileDownloadDTO fileDownloadDTO = fileService.fileDownload(id);

        String originFileName = stringUtils.encodeFileName(fileDownloadDTO.getOriginName());
        Resource resource = fileDownloadDTO.getFileResource();

        String disposition = "attachment; filename=\""+originFileName+"\"";

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION , disposition).
                body(resource);
    }
    @Operation(description = "파일삭제")
    @Parameter(name = "fileId", description = "파일 번호", example = "2")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "파일 삭제 성공", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "파일 삭제", value = "2", description = "2번 파일 삭제"))),
            @ApiResponse(responseCode = "400", description = "요청 리소스 오류", content = @Content(schema = @Schema(hidden = true)))
    })
    @DeleteMapping("/private/files/{fileIds}")
    public ResponseEntity<?> deleteFileByIds(@PathVariable("fileIds")List<Integer> fileIds) throws Exception {
        List<Integer> cloneIds = new ArrayList<>(fileIds);
        return ResponseEntity.ok().body(fileService.deleteFileByIds(cloneIds));
    }


    @GetMapping(value = "/public/files/thumbnail/{fileId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getThumbnailImage(@PathVariable(value = "fileId",required = false) Integer fileId) throws Exception {
        byte[] thumbnailImage = fileService.getThumbnailImage(fileId);
        return thumbnailImage;
    }

}