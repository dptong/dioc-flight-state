package com.flywin.utils;

import com.flywin.core.exception.BusinessException;
import com.github.tobato.fastdfs.domain.fdfs.FileInfo;
import com.github.tobato.fastdfs.domain.fdfs.MetaData;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.fdfs.ThumbImageConfig;
import com.github.tobato.fastdfs.domain.proto.ErrorCodeConstants;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.exception.FdfsServerException;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;

/**
 * @Description FastDfs工具类
 * @Author wumin
 * @Date 2020-07-15 16:14
 * @Version 1.0
 */
@Slf4j
@Component
public class FastDfsUtil {

    private FastFileStorageClient storageClient;

    private ThumbImageConfig thumbImageConfig;

    @Autowired
    public void setStorageClient(FastFileStorageClient storageClient) {
        this.storageClient = storageClient;
    }

    @Autowired
    public void setThumbImageConfig(ThumbImageConfig thumbImageConfig) {
        this.thumbImageConfig = thumbImageConfig;
    }

    /**
     * @param multipartFile 文件
     * @return java.lang.String
     * @throws IOException
     * @Description 上传文件
     * @Author wumin
     * @Date 2020-07-16 8:33
     */
    public String uploadFile(MultipartFile multipartFile) throws IOException {

        return this.uploadFile(multipartFile, null);
    }

    /**
     * @param multipartFile 文件
     * @param metaDataSet   元数据
     * @return java.lang.String
     * @Description 上传带元数据
     * @Author wumin
     * @Date 2020-07-16 10:10
     */
    public String uploadFile(MultipartFile multipartFile, Set<MetaData> metaDataSet) throws IOException {

        String originalFilename = multipartFile.getOriginalFilename();
        String fileExtName = StringUtils.substringAfterLast(originalFilename, ".");

        StorePath storePath = this.storageClient.uploadFile(multipartFile.getInputStream(), multipartFile.getSize(),
                fileExtName, metaDataSet);

        return storePath.getFullPath();
    }

    /**
     * @param multipartFile 文件
     * @return java.lang.String
     * @Description 上传图片并生成缩略图
     * @Author wumin
     * @Date 2020-07-16 10:17
     */
    public String uploadImageAndCrtThumbImage(MultipartFile multipartFile) throws IOException {

        return this.uploadImageAndCrtThumbImage(multipartFile, null);
    }

    /**
     * @param multipartFile 文件
     * @param metaDataSet   元数据
     * @return java.lang.String
     * @Description 上传图片并生成缩略图 带元数据
     * @Author wumin
     * @Date 2020-07-16 10:17
     */
    public String uploadImageAndCrtThumbImage(MultipartFile multipartFile, Set<MetaData> metaDataSet)
            throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();
        String fileExtName = StringUtils.substringAfterLast(originalFilename, ".");

        StorePath storePath = this.storageClient.uploadImageAndCrtThumbImage(multipartFile.getInputStream(), multipartFile.getSize(),
                fileExtName, metaDataSet);

        return storePath.getFullPath();
    }

    /**
     * @param fileUrl 文件地址
     * @return byte[] 字节数组
     * @Description 下载文件
     * @Author wumin
     * @Date 2020-07-16 8:33
     */
    public byte[] downloadFile(String fileUrl) {

        StorePath storePath = StorePath.parseFromUrl(fileUrl);
        DownloadByteArray downloadByteArray = new DownloadByteArray();

        return this.storageClient.downloadFile(storePath.getGroup(), storePath.getPath(), downloadByteArray);
    }

    /**
     * @param fileUrl 文件地址
     * @return byte[]
     * @Description 下载缩略图
     * @Author wumin
     * @Date 2020-07-16 10:23
     */
    public byte[] downloadThumbImage(String fileUrl) {
        StorePath storePath = StorePath.parseFromUrl(fileUrl);
        // 获取缩略图地址
        String thumbImagePath = thumbImageConfig.getThumbImagePath(storePath.getPath());
        DownloadByteArray downloadByteArray = new DownloadByteArray();

        return this.storageClient.downloadFile(storePath.getGroup(), thumbImagePath, downloadByteArray);
    }

    /**
     * @param fileUrl 文件地址
     * @return void
     * @throws BusinessException
     * @Description 删除文件
     * @Author wumin
     * @Date 2020-07-16 8:33
     */
    public void deleteFile(String fileUrl) throws BusinessException {
        if (StringUtils.isEmpty(fileUrl)) {
            log.info("fileUrl == >>文件路径为空...");
            return;
        }
        StorePath storePath = StorePath.parseFromUrl(fileUrl);
		/*try {
			// 删除缩略图
			String thumbImagePath = thumbImageConfig.getThumbImagePath(storePath.getPath());
			storageClient.deleteFile(storePath.getGroup(), thumbImagePath);

			log.info("delete thumbImage success:{}", fileUrl);
		} catch (FdfsServerException e) {
			// 文件不存在则不抛出异常
			if (ErrorCodeConstants.ERR_NO_ENOENT != e.getErrorCode()) {
				throw new BusinessException(e.getMessage());
			} else {
				log.info("thumbImage fileUrl:{}, message:{}", fileUrl, e.getMessage());
			}
		}*/

        try {
            // 删除文件
            storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
            log.info("delete success:{}", fileUrl);
        } catch (FdfsServerException e) {
            // 文件不存在则不抛出异常
            if (ErrorCodeConstants.ERR_NO_ENOENT != e.getErrorCode()) {
                throw new BusinessException(e.getMessage());
            } else {
                log.info("fileUrl:{}, message:{}", fileUrl, e.getMessage());
            }
        }
    }

    /**
     * @param fileUrl 文件地址
     * @return java.util.Set<com.github.tobato.fastdfs.domain.fdfs.MetaData>
     * @Description 获取文件元数据
     * @Author wumin
     * @Date 2020-07-16 8:32
     */
    public Set<MetaData> getMetadata(String fileUrl) {
        StorePath storePath = StorePath.parseFromUrl(fileUrl);
        return storageClient.getMetadata(storePath.getGroup(), storePath.getPath());
    }

    /**
     * @param fileUrl 文件地址
     * @return com.github.tobato.fastdfs.domain.fdfs.FileInfo
     * @Description 查询文件信息
     * @Author wumin
     * @Date 2020-07-16 8:32
     */
    public FileInfo queryFileInfo(String fileUrl) {
        StorePath storePath = StorePath.parseFromUrl(fileUrl);

        return storageClient.queryFileInfo(storePath.getGroup(), storePath.getPath());
    }

}
