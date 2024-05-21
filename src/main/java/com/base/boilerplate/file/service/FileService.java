package com.base.boilerplate.file.service;

import com.base.boilerplate.auth.exception.CustomFileException;
import com.base.boilerplate.config.GlobalSettings;
import com.base.boilerplate.file.domain.model.ComFile;
import com.base.boilerplate.file.domain.model.ComRelationshipFile;
import com.base.boilerplate.file.domain.repository.FileRepository;
import com.base.boilerplate.file.domain.repository.RelationshipFileRepository;
import com.base.boilerplate.file.dto.*;
import com.base.boilerplate.util.CacheType;
import com.base.boilerplate.util.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
public class FileService {

    private final GlobalSettings globalSettings;
    private final FileStorageService fileStorageService;
    private final FileRepository fileRepository;
    private final FileUtils fileUtils;
    private final RelationshipFileRepository relationshipFileRepository;

//    private final DrmService drmService;

//    public MultipartFile decryptFile(MultipartFile file) throws Exception {
//        String path = fileStorageService.getFilePath("before-decrypt");
//        System.out.println("path in decryptFile= " + path);
//        String originName = file.getOriginalFilename();
//        String uuidName = java.util.UUID.randomUUID().toString();
//        String ext = fileUtils.getExtension(originName);
//        String newFileName = uuidName+"."+ext;
//        System.out.println("newFileName in decryptFile= " + newFileName);
//        String fullPath = path+globalSettings.getFileDelimiter()+newFileName;
//        System.out.println("fullPath in decryptFile= " + fullPath);
//        File localSaveFile = new File(fullPath);
//        file.transferTo(localSaveFile);
//        MultipartFile decryptFile = drmService.getDecryptFile(file, path,newFileName);
//
//        return decryptFile;
//    }
    public List<FileDTO> saveFile(RelationshipFileRequestDTO relationshipFileRequestDTO, List<MultipartFile> files) throws Exception {
        fileUtils.checkExt(files);
        
        List<FileDTO> fileDTOS = new ArrayList<>();
        String path = fileStorageService.getFilePath(relationshipFileRequestDTO.getTaskName());
        for(MultipartFile saveFile : files){
            String originName = saveFile.getOriginalFilename();
            String uuidName = java.util.UUID.randomUUID().toString();
            String ext = fileUtils.getExtension(originName);
            String fullPath = path+globalSettings.getFileDelimiter()+uuidName+"."+ext;

            File localSaveFile = new File(fullPath);

            saveFile.transferTo(localSaveFile);

            FileDTO fileDTO = FileDTO.builder()
                    .originName(originName)
                    .uuidName(uuidName)
                    .ext(ext)
                    .path(path)
                    .fullPath(fullPath)
                    .fileGroupCode(relationshipFileRequestDTO.getFileGroupCode())
                    .build();

//            String thumbnailFileFullPath = this.saveThumbnailFileFullPath(fileDTO);
//            fileDTO.setThumbnailFileFullPath(thumbnailFileFullPath);
            fileDTOS.add(fileDTO);
        }

        checkedFileDownload(files, fileDTOS);

        for(FileDTO fileDTO : fileDTOS){
            ComFile comFile = FileDTO.convertToEntity(fileDTO);
            ComFile saveFile = fileRepository.save(comFile);
            fileDTO.setId(saveFile.getId());
        }

        return fileDTOS;
    }
    public void checkedFileDownload(List<MultipartFile> files, List<FileDTO> fileDTOS) throws Exception {
        if(files.size()!=fileDTOS.size()){
            this.fileDownloadFailed(fileDTOS);
        }
    }

    public void fileDownloadFailed(List<FileDTO> fileDTOS)throws Exception{
        for(FileDTO fileDTO : fileDTOS){
            this.deleteFile(fileDTO.getFullPath());
        }
        throw new CustomFileException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 저장에 실패 했습니다. 다시 시도해주세요.");
    }

    public void deleteFile(String filePath) throws CustomFileException {
        File file = new File(filePath);
        if(!file.exists()){
            throw new CustomFileException(HttpStatus.BAD_REQUEST, "삭제할 파일이 없습니다.");
        }

        file.delete();
    }

//    public String saveThumbnailFileFullPath(FileDTO fileDTO) throws IOException {
//        String fileExt = fileDTO.getFileExt();
//        if(fileExt.equals("pdf")){
//            return null;
//        }
//        String thumbnailFileFullPath = fileDTO.getFilePath()+"\\s_"+fileDTO.getFileName()+"."+fileDTO.getFileExt();
//        int width = 300;
//        int height = 300;
//        // 생성한 파일
//        File file = new File(fileDTO.getFileFullPath());
//
//        // 생성할 파일
//        File newFile = new File(thumbnailFileFullPath);
//
//        // 쓸 이미지 생성
//        BufferedImage bufferedImageInput  = ImageIO.read(file);
//
//        // 이미지 설정 준비
//        BufferedImage bufferedImageOutput = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
//        Graphics2D graphics2D = bufferedImageOutput.createGraphics();
//
//        // 이미지 그리기
//        graphics2D.drawImage(bufferedImageInput, 0, 0, width, height, null);
//        ImageIO.write(bufferedImageOutput, fileDTO.getFileExt(), newFile);
//
//        // 생성한 이미지 주소 리턴
//        return thumbnailFileFullPath;
//    }

    // codeId 와 dataId로 모든 파일 조회
//    public List<ComFile> findAllByCodeIdAndDataId(ComCode codeId, Integer dataId){
//        return fileRepository.findAllByCodeIdAndDataId(codeId, dataId);
//    }

//    public byte[] getThumbnailImage(Integer fileId) throws IOException, CustomFileException {
//        String filePath = globalSettings.getDefaultFileImagePath();
//        ComFile findFile = fileRepository.findById(fileId).orElseThrow(()->new CustomFileException(HttpStatus.BAD_REQUEST, "존재하지 않는 파일 번호입니다."));
//        if(findFile.getThumbnailFileFullPath()!=null){
//                filePath = findFile.getThumbnailFileFullPath();
//        }
//        File file = new File(filePath);
//        return Files.readAllBytes(file.toPath());
//    }

//    public void deleteFileByCodeIdAndDataId(ComCode codeId, Integer dataId) throws CustomFileException {
//        List<ComFile> findFileList = fileRepository.findAllByCodeIdAndDataId(codeId, dataId);
//        if(!findFileList.isEmpty()) {
//            for (ComFile file : findFileList) {
//                fileRepository.deleteById(file.getFileId());
//                this.deleteFile(file.getFileFullPath());
//                this.deleteFile(file.getThumbnailFileFullPath());
//            }
//        }
//    }
//    @Cacheable(cacheNames = CacheType.NAMES.FILE, key = "#id")
    public FileDownloadDTO fileDownload(Integer id) throws CustomFileException {
        ComFile findFile = fileRepository.findById(id).orElseThrow(()->new CustomFileException(HttpStatus.BAD_REQUEST, "존재하지 않는 파일입니다."));
        UrlResource resource;
        try {
            resource = new UrlResource("file:" + findFile.getFullPath());
        }catch (Exception e){
            throw new CustomFileException(HttpStatus.BAD_REQUEST, "파일 리소스를 읽을 수 없습니다.");
        }

        return FileDownloadDTO.convertToDTO(findFile,resource);
    }


    public FileDownloadDTO decryptFiles(List<MultipartFile> files) throws CustomFileException {

//        UrlResource resource;
//        try {
//            resource = new UrlResource("file:" + findFile.getFullPath());
//        }catch (Exception e){
//            throw new CustomFileException(HttpStatus.BAD_REQUEST, "파일 리소스를 읽을 수 없습니다.");
//        }

        // return FileDownloadDTO.convertToDTO(findFile,resource);
        return null;
    }

    public Integer deleteFileByIds(List<Integer> fileIds) throws Exception {
        List<Integer> cloneFileIds = new ArrayList<>(fileIds);
        List<String> fileFullPaths = new ArrayList<>();
        for(Integer id : fileIds){
            ComFile findFile = fileRepository.findById(id).orElseThrow(()->new CustomFileException(HttpStatus.BAD_REQUEST, "존재하지 않는 파일입니다."));
            fileFullPaths.add(findFile.getFullPath());
        }

        Integer index = 0;
        String errorLog = "";

        fileRepository.deleteByIds(cloneFileIds);
        for (String fileFullPath : fileFullPaths) {
            try {
                this.deleteFile(fileFullPath);
            }catch (Exception e){
                errorLog += "["+LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))+"]file.delete.error.filepath = id : "+cloneFileIds.get(index)+" | fileFullPath : " + fileFullPath +"\n";
            }
            index++;
        }
        if(StringUtils.hasText(errorLog)){
            writeTextFile("file_delete_error", "file_delete_error.txt", errorLog);
            throw new CustomFileException(HttpStatus.BAD_REQUEST, "파일이 정상적으로 삭제되지 않았습니다.");
        }else{
            for(Integer id : cloneFileIds){
                deleteCacheFile(id);
            }
        }
//        this.deleteFile(findFile.getThumbnailFileFullPath());

        return fileFullPaths.size();
    }

    public HashMap<String, Object> createFiles(RelationshipFileRequestDTO relationshipFileRequestDTO, List<MultipartFile> files) throws Exception {
        String fileGroupCode = relationshipFileRequestDTO.getFileGroupCode();
        if(fileGroupCode==null){
            fileGroupCode = java.util.UUID.randomUUID().toString();
            relationshipFileRequestDTO.setFileGroupCode(fileGroupCode);
            relationshipFileRepository.save(RelationshipFileRequestDTO.convertToEntity(relationshipFileRequestDTO));
        }
        List<FileDTO> fileDTOS = this.saveFile(relationshipFileRequestDTO,files);
        HashMap<String, Object> fileInfo = new HashMap<>();
        fileInfo.put("fileGroupCode", fileGroupCode);
        fileInfo.put("fileIds", fileDTOS.stream().map(file->file.getId()));

        return fileInfo;
    }

    public void updateRelationshipFile(Integer taskId, String groupCode) throws CustomFileException {
    ComRelationshipFile findComRelationshipFile = relationshipFileRepository.findByFileGroupCode(groupCode).orElseThrow(()-> new CustomFileException(HttpStatus.BAD_REQUEST, "잘못된 파일그룹코드 입니다."));
    findComRelationshipFile.setTaskId(taskId);
    }
    public FileResponseDTO retrieveFiles(RelationshipFileRequestDTO relationshipFileRequestDTO) {
        List<ComFile> findComFiles = fileRepository.findAllByTaskIdAndTaskName(relationshipFileRequestDTO.getTaskId(), relationshipFileRequestDTO.getTaskName());
        List<FileInfoDTO> findInfoDTOS = findComFiles.stream().map(item -> new FileInfoDTO(item.getId(), item.getOriginName(), item.getCreateDate())).collect(Collectors.toList());

        List<FileInfoDTO> cloneFileInfoDTOS = new ArrayList<>(findInfoDTOS);
        List<ComFile> cloneFindComFiles = new ArrayList<>(findComFiles);

        if(findComFiles.size()!=0){
            String fileGroupCode = cloneFindComFiles.get(0).getFileGroupCode();
            return new FileResponseDTO(cloneFileInfoDTOS, fileGroupCode);
        }
        return new FileResponseDTO(cloneFileInfoDTOS, null);
    }
    @Cacheable(cacheNames = CacheType.NAMES.THUMBNAIL_FILE, key = "#fileId")
    public byte[] getThumbnailImage(Integer fileId) throws IOException, CustomFileException {
        String filePath = globalSettings.getDefaultFileImagePath();
        ComFile findFile = fileRepository.findById(fileId).orElseThrow(()->new CustomFileException(HttpStatus.BAD_REQUEST, "존재하지 않는 파일 번호입니다."));
//        if(findFile.getThumbnailFileFullPath()!=null){
//            filePath = findFile.getThumbnailFileFullPath();
//        }
        File file = new File(findFile.getFullPath());
        return Files.readAllBytes(file.toPath());
    }


//    public byte[] getOriginImage(Integer fileId) throws CustomFileException, IOException {
//        String filePath = globalSettings.getDefaultFileImagePath();
//        ComFile findFile = fileRepository.findById(fileId).orElseThrow(()->new CustomFileException(HttpStatus.BAD_REQUEST, "존재하지 않는 파일 번호입니다."));
//        if(findFile.getExt().equals("jpg")||findFile.getExt().equals("png")||findFile.getExt().equals("jpeg")){
//            filePath = findFile.getFullPath();
//        }
//        File file = new File(filePath);
//        return Files.readAllBytes(file.toPath());
//    }

    public void periodicFileDeletion() throws Exception {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime threeDaysAgo = currentTime.minusDays(3);

        List<Integer> relationshipFileIds = relationshipFileRepository.findAllByTaskIdIsNullAndCreateDate(threeDaysAgo);
        List<Integer> fileIds = fileRepository.findAllByTaskIdIsNullAndCreateDate(threeDaysAgo);

        relationshipFileRepository.deleteAllById(relationshipFileIds);
        this.deleteFileByIds(fileIds);
    }

    public void writeTextFile(String pathName, String fileName, String content) throws Exception {
        String filePath = fileStorageService.getFilePath(pathName);
        File textFile = new File(filePath + globalSettings.getFileDelimiter() + fileName);
//        File textFile = new File(filePath+"/"+fileName);
        if (textFile.exists()) {
            textFile.createNewFile();
        }
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(textFile, true));
            writer.write(content);
            writer.flush();
        } catch (Exception e) {
            throw new CustomFileException(HttpStatus.BAD_REQUEST, "잘못된 파일입니다.");
        } finally {
            if(writer!=null){
                writer.close();
            }
        }


    }
    @CacheEvict(cacheNames = CacheType.NAMES.FILE, key = "#id")
    public void deleteCacheFile(Integer id){
    }
}